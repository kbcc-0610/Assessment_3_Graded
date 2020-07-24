package com.example.assignment_3.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.assignment_3.Database.UserDB;
import com.example.assignment_3.Model.User;
import com.example.assignment_3.R;
import com.example.assignment_3.Utilities.GmailSender;

import java.util.Random;

public class BecomeVIPPage extends AppCompatActivity implements EnterCode.EnterVertifyCode {
    private Button btnsend;
    private final String codeStringAllow = "0123456789qwertyuiopasdfghjklzxcvbnm";
    private AlertDialog confirm;
    String codeTosend = GenerateCode();
    private ProgressDialog progressDialog;
    private User userLogined = Login_page.getLoginUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_v_i_p);
        getSupportActionBar().setTitle("Become A VIP");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnsend = findViewById(R.id.btn_vip_send);

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendEmail();
                DisplayCodeDialog();
            }
        });
    }

    //auto generate the code send to user
    private String GenerateCode() {
        final Random random = new Random();
        final int sizeofString = 6;
        final StringBuilder sb = new StringBuilder(sizeofString);
        for (int i = 0; i < sizeofString; i++) {
            sb.append(codeStringAllow.charAt(random.nextInt(codeStringAllow.length())));
        }
        return sb.toString();
    }

    @Override
    public void applyCode(String code) {
        String codeEnter = code;
        CheckEnteredCode(codeEnter);
    }

    private void DisplayCodeDialog(){
        EnterCode enterVertifyCode = new EnterCode();
        enterVertifyCode.show(getSupportFragmentManager(),"Enter Code");
    }

    private void SendEmail(){
        progressDialog = new ProgressDialog(BecomeVIPPage.this);
        String message = "Your Verification Code is : " + codeTosend;
        String subject = "Verfication Code From Three in a row Gaming";
        String email = userLogined.getEmail();
        progressDialog.setTitle("Sending Verification Code");
        progressDialog.setMessage("PLEASE WAIT...");
        progressDialog.show();
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    GmailSender gmailSender = new GmailSender("kobezhangcc@gmail.com", "ztw661288");
                    gmailSender.sendMail(subject,message,"kobezhangcc@gmail.com",email);
                    progressDialog.dismiss();
                } catch (Exception e) {
                    Log.e("mylog", "Error" + e.getMessage());
                }
            }
        });
        sender.start();
    }

    private void CheckEnteredCode(String theCode){
        if(theCode.equals(codeTosend)){
            Toast.makeText(this,"The code is matched!!!!",Toast.LENGTH_SHORT).show();
            userLogined.setMembership(2);
            UserDB.getInstance(getApplicationContext()).userDao().update(userLogined);
        }else{
            Toast.makeText(this,"The code is not matched!!!!",Toast.LENGTH_SHORT).show();
        }
    }
}

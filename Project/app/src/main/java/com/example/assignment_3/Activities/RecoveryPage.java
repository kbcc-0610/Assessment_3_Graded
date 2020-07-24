package com.example.assignment_3.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.assignment_3.Database.UserDB;
import com.example.assignment_3.Model.User;
import com.example.assignment_3.R;
import com.example.assignment_3.Utilities.GmailSender;

import java.util.ArrayList;

public class RecoveryPage extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText editEmail;
    private Button btnSend;
    private Boolean found = false;
    private ArrayList<User> userArrayList;
    private String emailEnter;
    private String userName = "";
    private String password = "";
    private ProgressDialog dialog;
    private AlertDialog.Builder confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_page);
        userArrayList = (ArrayList<User>) UserDB.getInstance(this).userDao().getAllUsers();
        radioGroup = findViewById(R.id.radioGroup);
        btnSend = findViewById(R.id.btn_recover_recov);
        editEmail = findViewById(R.id.edit_recover_email);
        confirm = new AlertDialog.Builder(this);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailEnter = editEmail.getText().toString();
                if (CheckEmail(emailEnter)) {
                    int radioID = radioGroup.getCheckedRadioButtonId();
                    radioButton = findViewById(radioID);
                    SendEmail(radioButton.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Email does not Exists", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void SendEmail(String UserChoice) {
        String messageToSend = "";
        String subject = "Recover Login Details in Three in a row Game";
        if (UserChoice.equals("USER NAME")) {
            messageToSend = "This Is the Recover Email From Three In A Row Game. Your User Name is: " + userName;
        }

        if (UserChoice.equals("Password")) {
            messageToSend = "This Is the Recover Email From Three In A Row Game. Your Password is: " + password;
        }
        final String msg = messageToSend;
        dialog = new ProgressDialog(RecoveryPage.this);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please Wait");
        dialog.show();
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GmailSender sender = new GmailSender("kobezhangcc@gmail.com", "ztw661288");
                    sender.sendMail(subject, msg, "kobezhangcc@gmail.com", emailEnter);
                    dialog.dismiss();
                    confirm.setTitle("Sent");
                    confirm.setMessage("Email with your login details sent successfully. " +
                            "Please check your email and log in again.");
                    confirm.setNegativeButton("Continue to Login", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();
                        }
                    });
                    confirm.create();
                    confirm.show();
                } catch (Exception e) {
                    Log.e("mylog", "Error" + e.getMessage());
                }
            }
        });
        sender.start();
    }

    private Boolean CheckEmail(String email) {
        found = false;
        for (User u : userArrayList) {
            if (email.equals(u.getEmail())) {
                found = true;
                password = u.getPassword();
                userName = u.getPassword();
            }
        }
        return found;
    }

}

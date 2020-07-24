package com.example.assignment_3.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment_3.Dao.UserDao;
import com.example.assignment_3.Database.UserDB;
import com.example.assignment_3.Model.User;
import com.example.assignment_3.R;

import java.util.ArrayList;

public class Login_page extends AppCompatActivity {

    private Button btnLogin;
    private Button btnregister;
    private EditText editUserName;
    private EditText editPassword;
    private static Boolean logined = false;
    private static String loginUserName;
    private static User LoginedUser;
    private TextView txtError;
    private Boolean found = false;
    private AlertDialog.Builder confirm;
    private TextView txtRecover;
    private int tryTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        getSupportActionBar().setTitle("Log in");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtError = findViewById(R.id.txt_logIn_error);
        txtError.setVisibility(View.GONE);
        txtRecover = findViewById(R.id.txt_Login_Recover);
        txtRecover.setVisibility(View.GONE);
        UserDB.initData(getApplicationContext());
        confirm = new AlertDialog.Builder(this);
        Register();
        Login();
        txtRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecovery();
            }
        });
    }

    private void Register() {
        btnregister = findViewById(R.id.btn_login_register);
        txtError.setVisibility(View.GONE);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_page.this, RegisterPage.class);
                startActivity(intent);
            }
        });
    }

    private void Login() {
        btnLogin = findViewById(R.id.btn_login_login);
        editUserName = findViewById(R.id.edit_login_UserName);
        editPassword = findViewById(R.id.edit_login_pass);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                found = false;
                String username = editUserName.getText().toString();
                String password = editPassword.getText().toString();
                ArrayList<User> userArrayList = (ArrayList<User>) UserDB.getInstance(getApplicationContext()).userDao().getAllUsers();
                txtError.setVisibility(View.VISIBLE);
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your user name and password first", Toast.LENGTH_SHORT);
                    txtError.setText("Please enter your user name and password first");
                    txtError.setTextColor(Color.RED);
                    tryTime++;
                } else {
                    for (User u : userArrayList) {
                        //if the username is correct check the password
                        if (username.equals(u.getUserName())) {
                            found = true;
                            if (password.equals(u.getPassword())) {
                                logined = true;
                                loginUserName = username;
                                LoginedUser = u;
                                Toast.makeText(getApplicationContext(), "Log in successfully " + loginUserName, Toast.LENGTH_SHORT);
                                txtError.setText("Log IN Successfully");
                                txtError.setTextColor(Color.GREEN);

                            } else {
                                Toast.makeText(getApplicationContext(), "Password is not Correct", Toast.LENGTH_SHORT);
                                txtError.setText("Password is not Correct");
                                txtError.setTextColor(Color.RED);
                                tryTime++;
                            }
                        }
                    }
                    if (!found) {
                        txtError.setText("The user name does not exist");
                        txtError.setTextColor(Color.RED);
                        tryTime++;
                    }
                }
                if(tryTime>=3){
                    txtRecover.setVisibility(View.VISIBLE);
                }

                if (logined) {
                    // if login then ...
                    confirm.setTitle("Successful");
                    confirm.setMessage("You have successfully login, Press button to continue ");
                    confirm.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //finish();
                            Intent intent = new Intent(Login_page.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    confirm.create();
                    confirm.show();
                }
            }
        });

    }

    //click on the recovery text start the recovery page
    private void startRecovery(){
        Intent intent = new Intent(Login_page.this,RecoveryPage.class);
        startActivity(intent);
    }

    //get the login username from other page
    public static String getUsername() {
        return loginUserName;
    }

    public static User getLoginUser(){
        return LoginedUser;
    }

    //get the username from other page
    public static boolean getLogin() {
        return logined;
    }

    public static void setLogin(boolean login){
        logined = login;
    }

    public static void signoffLoginUserName(){
        loginUserName = "";
    }

    public static void signoffLoginUser(){
        LoginedUser =null;
    }
}

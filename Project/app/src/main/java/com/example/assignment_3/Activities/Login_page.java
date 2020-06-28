package com.example.assignment_3.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment_3.Database.UserDB;
import com.example.assignment_3.Model.User;
import com.example.assignment_3.R;

import java.util.ArrayList;

public class Login_page extends AppCompatActivity {

    private Button btnLogin;
    private Button btnregister;
    private EditText editUserName;
    private EditText editPassword;
    public static Boolean logined = false;
    public static String loginUserName;
    private TextView txtError;
    private Boolean found = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        getSupportActionBar().setTitle("Log in");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtError = findViewById(R.id.txt_logIn_error);
        txtError.setVisibility(View.GONE);
        UserDB.initData(getApplicationContext());
        Register();
        Login();
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
                found =false;
                String username = editUserName.getText().toString();
                String password = editPassword.getText().toString();
                ArrayList<User> userArrayList = (ArrayList<User>) UserDB.getInstance(getApplicationContext()).userDao().getAllUsers();
                txtError.setVisibility(View.VISIBLE);
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your user name and password first", Toast.LENGTH_SHORT);
                    txtError.setText("Please enter your user name and password first");
                    txtError.setTextColor(Color.RED);
                } else {
                    for (User u : userArrayList) {
                        //if the username is correct check the password
                        if (username.equals(u.getUserName()) ) {
                            found = true;
                            if (password.equals(u.getPassword())) {
                                logined = true;
                                loginUserName = username;
                                Toast.makeText(getApplicationContext(), "Log in successfully " + loginUserName, Toast.LENGTH_SHORT);
                                txtError.setText("Log IN Successfully");
                                txtError.setTextColor(Color.GREEN);

                            } else {
                                Toast.makeText(getApplicationContext(), "Password is not Correct", Toast.LENGTH_SHORT);
                                txtError.setText("Password is not Correct");
                                txtError.setTextColor(Color.RED);
                            }
                        }
                    }
                    if (!found) {
                            txtError.setText("The user name does not exist");
                            txtError.setTextColor(Color.RED);
                    }
                }
                if(logined){
                    // if login then ...
                }
            }
        });

    }

    public static String getUsername(){
        return loginUserName;
    }

    public static boolean getLogin(){
        return logined;
    }
}

package com.example.assignment_3.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.MediaCodec;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.assignment_3.Database.UserDB;
import com.example.assignment_3.Model.User;
import com.example.assignment_3.R;

import java.util.ArrayList;

public class RegisterPage extends AppCompatActivity {

    private Button btnRegister;
    private EditText editUserName;
    private EditText editPass;
    private EditText editEmail;
    private Boolean found;
    private String userName;
    private String password;
    private String email;
    private TextView txtError;
    private AlertDialog.Builder confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registerNewUser();
        txtError.setVisibility(View.GONE);
        confirm = new AlertDialog.Builder(this);
    }

    private void registerNewUser() {
        txtError = findViewById(R.id.txt_reg_error);
        editUserName = findViewById(R.id.edit_register_userName);
        editPass = findViewById(R.id.edit_regis_pass);
        btnRegister = findViewById(R.id.btn_reg_register);
        editEmail = findViewById(R.id.edit_reg_email);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                found = false;
                txtError.setVisibility(View.GONE);
                userName = editUserName.getText().toString();
                password = editPass.getText().toString();
                email = editEmail.getText().toString();
                if (userName.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    //when user did not enter anything
                    txtError.setText("Please Enter User Name password and email address");
                    txtError.setVisibility(View.VISIBLE);
                    editEmail.setError("Field can't be empty");
                } else {
                    if (checkUserName(userName)) {
                        //when there is duplicate user name exist ask user to enter a new one
                        txtError.setText("User Name Already Exists, Try Another One");
                        txtError.setVisibility(View.VISIBLE);
                    } else if (checkEmailAddress(email)) {
                        //when there is a duplicate email address exist ask user to enter a new email address
                        txtError.setText("Email is Already Exists, Try a new One");
                        txtError.setVisibility(View.VISIBLE);
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        txtError.setText("Invalid Email Address");
                        txtError.setVisibility(View.VISIBLE);
                    } else {
                        // when there is no same username and email address exists in the database
                        User userToAdd = new User(userName, password, email);
                        UserDB.getInstance(getApplicationContext()).userDao().insert(userToAdd);
                        SuccessfullyRegister();

                    }
                }
            }
        });
    }

    // check if the user name already exists
    private boolean checkUserName(String uName) {
        ArrayList<User> userArrayList = (ArrayList<User>) UserDB.getInstance(getApplicationContext()).userDao().getAllUsers();
        for (User u : userArrayList) {
            if (uName.equals(u.getUserName())) {
                found = true;
            }
        }
        return found;
    }

    private boolean checkEmailAddress(String email) {
        found = false;
        ArrayList<User> userArrayList = (ArrayList<User>) UserDB.getInstance(getApplicationContext()).userDao().getAllUsers();
        for (User u : userArrayList) {
            if (email.equals(u.getEmail())) {
                found = true;
            }
        }
        return found;
    }

    private void SuccessfullyRegister() {
        confirm.setTitle("Successful");
        confirm.setMessage("You have successfully sign up new account, Press button to log in");
        confirm.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        confirm.create();
        confirm.show();
    }
}

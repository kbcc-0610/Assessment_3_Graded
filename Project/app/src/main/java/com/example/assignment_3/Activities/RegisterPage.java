package com.example.assignment_3.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    private Boolean found;
    private String userName;
    private String password;
    private TextView txtError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registerNewUser();
        txtError.setVisibility(View.GONE);
    }

    private void registerNewUser() {
        txtError = findViewById(R.id.txt_reg_error);
        editUserName = findViewById(R.id.edit_register_userName);
        editPass = findViewById(R.id.edit_regis_pass);
        btnRegister = findViewById(R.id.btn_reg_register);

        found = false;

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtError.setVisibility(View.GONE);
                userName = editUserName.getText().toString();
                password = editPass.getText().toString();
                if (userName.isEmpty() || password.isEmpty()) {
                    //when user did not enter anything
                    txtError.setText("Please Enter User Name and Password");
                    txtError.setVisibility(View.VISIBLE);
                } else {
                    if (checkUserName(userName)) {
                        //when there is duplicate user name exist ask user to enter a new one
                        txtError.setText("User Name Already Exits, Try Another One");
                        txtError.setVisibility(View.VISIBLE);
                    } else {
                        // when there is no same username exists in the database
                        User userToAdd = new User(userName, password);
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

    private void SuccessfullyRegister() {
        finish();
    }
}

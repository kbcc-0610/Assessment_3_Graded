package com.example.assignment_3.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.assignment_3.Database.RecordDB;
import com.example.assignment_3.Database.RetrofitServices;
import com.example.assignment_3.Model.Record;
import com.example.assignment_3.Model.Setting;
import com.example.assignment_3.Model.User;
import com.example.assignment_3.R;

import java.util.ArrayList;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private Button btnStart;
    private Button btnSetting;
    private Button btnhighScore;
    private TextView txtLogIn;
    private Button btnBackUpSet;
    private Button btnBackupRecords;
    private String name = Login_page.getUsername();
    private boolean logined = Login_page.getLogin();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Welcome");
        txtLogIn = findViewById(R.id.txt_login);

        //call the function for clicking on different button
        startGame();
        setting();
        highScore();
        goToLoginPage();
        backupHighScore();
        RecordDB.initData(getApplicationContext());
        backupSetting();
        if(null != name){
            txtLogIn.setText("Hello, " + name);
        }

        if(logined){
            txtLogIn.setClickable(false);
        }
    }

    private void startGame() {
        btnStart = findViewById(R.id.btn_wel_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GamePage.class);
                startActivity(intent);
            }
        });
    }

    private void setting() {
        btnSetting = findViewById(R.id.btn_wel_setting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingPage.class);
                startActivity(intent);
            }
        });
    }

    private void highScore() {
        btnhighScore = findViewById(R.id.btn_wel_highScore);
        btnhighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HighScorePage.class);
                startActivity(intent);
            }
        });
    }

    private void goToLoginPage() {
        txtLogIn = findViewById(R.id.txt_login);
        txtLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login_page.class);
                startActivity(intent);
            }
        });
    }

    private void backupHighScore() {
        btnBackupRecords = findViewById(R.id.btn_home_backupRec);
        btnBackupRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Record> records = (ArrayList<Record>) RecordDB.getInstance(getApplicationContext()).recordDao().getAllRecords();
                for (Record r : records) {
                    RetrofitServices.getInstance().BackUpRecords(r);
                }
            }
        });

    }

    private void backupSetting() {
        btnBackUpSet = findViewById(R.id.btn_home_backupSet);
        btnBackUpSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences result = getSharedPreferences("SavedSizedata", Context.MODE_PRIVATE);
                SharedPreferences color1Result = getSharedPreferences("Color1data", Context.MODE_PRIVATE);
                SharedPreferences color2Result = getSharedPreferences("Color2data", Context.MODE_PRIVATE);
                String theresult = result.getString("value", "4");
                String resultForColor1 = color1Result.getString("value", String.valueOf(R.color.colorBlue));
                String resultForColor2 = color2Result.getString("value", String.valueOf(R.color.colorRed));

                Setting setting = new Setting(resultForColor1, resultForColor2, theresult);
                RetrofitServices.getInstance().BackUpdSetting(setting);
            }
        });
    }

}

package com.example.assignment_3.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment_3.Database.RecordDB;
import com.example.assignment_3.Database.RetrofitServices;
import com.example.assignment_3.Model.Record;
import com.example.assignment_3.Model.Setting;
import com.example.assignment_3.Model.User;
import com.example.assignment_3.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements RetrofitServices.ResultsHandler {
    private Button btnStart;
    private Button btnSetting;
    private Button btnhighScore;
    private TextView txtLogIn;
    private Button btnBackUpSet;
    private Button btnBackupRecords;
    private TextView txtSighoff;
    private String name = Login_page.getUsername();
    private boolean logined = Login_page.getLogin();
    private TextView txtVip;
    private User loginedUser = Login_page.getLoginUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Welcome");
        txtLogIn = findViewById(R.id.txt_login);
        btnBackupRecords = findViewById(R.id.btn_home_backupRec);
        btnBackUpSet = findViewById(R.id.btn_home_backupSet);
        txtSighoff = findViewById(R.id.txt_main_signoff);
        txtVip = findViewById(R.id.txt_main_vip);
        txtSighoff.setVisibility(View.GONE);
        //call the function for clicking on different button
        startGame();
        setting();
        highScore();
        gotoVipPage();
        goToLoginPage();
        RecordDB.initData(getApplicationContext());
        if (null != name) {
            txtLogIn.setText("Hello, " + name);
        }
        if (logined) {
            txtLogIn.setClickable(false);
            txtSighoff.setVisibility(View.VISIBLE);
            if(loginedUser.getMembership() == 2){
                txtVip.setText("VIP 1");
                txtVip.setTextColor(Color.RED);
                txtVip.setClickable(false);
            }
        }
        btnBackupRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backupHighScore();
            }
        });
        btnBackUpSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backupSetting();
            }
        });

        txtSighoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signoff();
            }
        });
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
        txtLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login_page.class);
                startActivity(intent);
            }
        });
    }

    private void gotoVipPage(){
        txtVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(logined){
                    Intent intent = new Intent(MainActivity.this,BecomeVIPPage.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Please Login First To Active the VIP",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void backupHighScore() {
        RetrofitServices.getInstance().HighScoreReadAll(this);
        ArrayList<Record> records = (ArrayList<Record>) RecordDB.getInstance(getApplicationContext()).recordDao().getAllRecords();
        for (Record r : records) {
            RetrofitServices.getInstance().BackUpRecords(r);
        }
    }

    private void backupSetting() {
        //clear the previous back up data
        RetrofitServices.getInstance().SettingReadAll(this);
        //get the user setting
        SharedPreferences result = getSharedPreferences("SavedSizedata", Context.MODE_PRIVATE);
        SharedPreferences color1Result = getSharedPreferences("Color1data", Context.MODE_PRIVATE);
        SharedPreferences color2Result = getSharedPreferences("Color2data", Context.MODE_PRIVATE);
        String theresult = result.getString("value", "4");
        String resultForColor1 = color1Result.getString("value", String.valueOf(R.color.colorBlue));
        String resultForColor2 = color2Result.getString("value", String.valueOf(R.color.colorRed));

        //assign the data to the setting object
        Setting setting = new Setting(resultForColor1, resultForColor2, theresult);
        //backup the setting data to the server
        RetrofitServices.getInstance().BackUpdSetting(setting);

    }

    //disable the back press in home page
    @Override
    public void onBackPressed() {

    }

    private void signoff(){
        Login_page.setLogin(false);
        Login_page.signoffLoginUserName();
        Login_page.signoffLoginUser();
        txtLogIn.setClickable(true);
        txtLogIn.setText("Login");
        txtVip.setText("GetVIP");
        txtVip.setTextColor(Color.parseColor("#03DAC5"));
        txtSighoff.setVisibility(View.GONE);
    }
    @Override
    public void ReadAllHighScore(List<Record> recordList) {
        for (Record r : recordList) {
            RetrofitServices.getInstance().DeleteHighScore(r.getId());
        }
    }

    @Override
    public void ReadAllSetting(List<Setting> settingList) {
        for (Setting s : settingList) {
            RetrofitServices.getInstance().DeleteSetting(s.getId());
        }
    }

}

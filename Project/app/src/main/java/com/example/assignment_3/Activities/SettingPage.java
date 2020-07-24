package com.example.assignment_3.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.assignment_3.Model.User;
import com.example.assignment_3.R;

public class SettingPage extends AppCompatActivity {

    private ImageView imgfirstColor;
    private ImageView imgSecondColor;
    private TextView txtSize;
    private SharedPreferences sharedPreferences;
    public static final String getSavedcolor1 = "Color1data";
    public static final String getSavedcolor2 = "Color2data";
    public static final String getSavedsize = "SavedSizedata";
    private User logineduser = Login_page.getLoginUser();
    private boolean logined = Login_page.getLogin();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);
        getSupportActionBar().setTitle("Setting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imgfirstColor = findViewById(R.id.img_setting_color1);
        imgSecondColor = findViewById(R.id.img_setting_color2);
        txtSize = findViewById(R.id.txt_setting_size);
        SharedPreferences result = getSharedPreferences("SavedSizedata", Context.MODE_PRIVATE);
        String theresult = result.getString("value", "4");
        txtSize.setText(theresult + " * " + theresult);
        int color1, color2;
        SharedPreferences color1Result = getSharedPreferences("Color1data", Context.MODE_PRIVATE);
        SharedPreferences color2Result = getSharedPreferences("Color2data", Context.MODE_PRIVATE);
        String resultForColor1 = color1Result.getString("value", String.valueOf(R.color.colorBlue));
        String resultForColor2 = color2Result.getString("value", String.valueOf(R.color.colorRed));
        color1 = Integer.parseInt(resultForColor1);
        color2 = Integer.parseInt(resultForColor2);
        imgfirstColor.setImageResource(color1);
        imgSecondColor.setImageResource(color2);
    }

    public void selectSize(View view) {
        registerForContextMenu(view);
        openContextMenu(view);
    }

    public void selectFirstColor(View view) {
        registerForContextMenu(view);
        openContextMenu(view);
    }

    public void selectSecondColor(View view) {
        registerForContextMenu(view);
        openContextMenu(view);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        int id = v.getId();
        if (id == R.id.txt_setting_size) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.popup_size, menu);
        } else if (id == R.id.img_setting_color1) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.color_1, menu);
            if (logined) {
                if (logineduser.getMembership() == 2) {
                    menu.findItem(R.id.checkvip1).setEnabled(true);
                }
            }
        } else {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.color_2, menu);
            if (logined) {
                if (logineduser.getMembership() == 2) {
                    menu.findItem(R.id.checkvip2).setEnabled(true);
                }
            }
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.check44:
                // do something here
                txtSize.setText("4 * 4");
                saveToSharePreference(1, "4");
                return true;
            case R.id.check55:
                //do something here
                txtSize.setText("5 * 5");
                saveToSharePreference(1, "5");
                return true;
            case R.id.check66:
                //do something here
                saveToSharePreference(1, "6");
                txtSize.setText("6 * 6");
                return true;
            case R.id.checkblue:
                imgfirstColor.setImageResource(R.color.colorBlue);
                saveToSharePreference(2, String.valueOf(R.color.colorBlue));
                return true;
            case R.id.checkvip1:
                imgfirstColor.setImageResource(R.drawable.ic_image_sec);
                saveToSharePreference(2, String.valueOf(R.drawable.ic_image_sec));
            case R.id.checkred:
                imgfirstColor.setImageResource(R.color.colorRed);
                saveToSharePreference(2, String.valueOf(R.color.colorRed));
                return true;
            case R.id.checkgreen:
                imgfirstColor.setImageResource(R.color.colorGreen);
                saveToSharePreference(2, String.valueOf(R.color.colorGreen));
                return true;
            case R.id.checkpink:
                imgSecondColor.setImageResource(R.color.colorPink);
                saveToSharePreference(3, String.valueOf(R.color.colorPink));
                return true;
            case R.id.checkvip2:
                imgSecondColor.setImageResource(R.drawable.ic_image);
                saveToSharePreference(3, String.valueOf(R.drawable.ic_image));
            case R.id.checkpurple:
                imgSecondColor.setImageResource(R.color.colorPurple);
                saveToSharePreference(3, String.valueOf(R.color.colorPurple));
                return true;
            case R.id.checkyellow:
                imgSecondColor.setImageResource(R.color.colorYellow);
                saveToSharePreference(3, String.valueOf(R.color.colorYellow));
                return true;
        }
        return super.onContextItemSelected(item);
    }

    public void saveToSharePreference(int a, String valuetoSaved) {
        if (a == 1) {
            sharedPreferences = getSharedPreferences(getSavedsize, Context.MODE_PRIVATE);
        } else if (a == 2) {
            sharedPreferences = getSharedPreferences(getSavedcolor1, Context.MODE_PRIVATE);
        } else {
            sharedPreferences = getSharedPreferences(getSavedcolor2, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("value", valuetoSaved);
        editor.apply();

    }

}

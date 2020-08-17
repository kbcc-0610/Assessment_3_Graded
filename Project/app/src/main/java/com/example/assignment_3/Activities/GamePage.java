package com.example.assignment_3.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment_3.Adapter.ImageAdapter;
import com.example.assignment_3.Adapter.RecyclerViewAdapter;
import com.example.assignment_3.Database.RecordDB;
import com.example.assignment_3.Model.Box;
import com.example.assignment_3.Model.Record;
import com.example.assignment_3.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class GamePage extends AppCompatActivity implements EnterUserName.UserNameDialogListener {

    private Button btnStart;
    private GridView gridView;
    private int num = 1;
    private ImageView nextColor;
    private int columncount = 4;
    private Box[] gridArr;
    private ImageAdapter iAdapter;
    private Button btnRestart;
    private Chronometer chronometer;
    private boolean gameProcessing;
    private long pauseOffset;
    private Toast toastmsg;
    private TextView txtResult;
    private boolean win = true;
    private long gamingTime;
    private int color1 = R.color.colorYellow;
    private int color2 = R.color.colorPink;
    private RecyclerView recyclerViewGame;
    private RecyclerViewAdapter adapter;
    private String logineduserName = Login_page.getUsername();
    private boolean logined = Login_page.getLogin();
    private AlertDialog.Builder confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);
        getSupportActionBar().setTitle("Play");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnRestart = findViewById(R.id.btn_restart);
        btnRestart.setVisibility(View.GONE);
        SharedPreferences result = getSharedPreferences("SavedSizedata", Context.MODE_PRIVATE);
        SharedPreferences color1Result = getSharedPreferences("Color1data", Context.MODE_PRIVATE);
        SharedPreferences color2Result = getSharedPreferences("Color2data", Context.MODE_PRIVATE);
        String theresult = result.getString("value", "4");
        String resultForColor1 = color1Result.getString("value", String.valueOf(R.color.colorBlue));
        String resultForColor2 = color2Result.getString("value", String.valueOf(R.color.colorRed));
        color1 = Integer.parseInt(resultForColor1);
        color2 = Integer.parseInt(resultForColor2);
        columncount = Integer.parseInt(theresult);
        txtResult = findViewById(R.id.txt_Result);
        txtResult.setVisibility(View.GONE);
        confirm = new AlertDialog.Builder(this);
        ArrayList<Record> recordArrayList = (ArrayList<Record>) RecordDB.getInstance(this).recordDao().getAllRecords();
        Collections.sort(recordArrayList);
        recyclerViewGame = findViewById(R.id.recycler_game);
        recyclerViewGame.getAdapter();
        adapter = new RecyclerViewAdapter(recordArrayList);
        recyclerViewGame.setAdapter(adapter);
        recyclerViewGame.setLayoutManager(new LinearLayoutManager(this));

        populateGrid();
        startGame();
        restartGame();
        RecordDB.initData(this);
    }

    private void restartGame() {
        chronometer.setFormat("Time Spent:  %s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGame();
            }
        });
    }

    private void startNewGame() {
        txtResult.setVisibility(View.GONE);
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        gridArr = new Box[columncount * columncount];
        populateGrid();
        gridView.setEnabled(true);
        chronometer.start();
        num = 1;
    }

    private void startGame() {
        btnStart = findViewById(R.id.btn_StartGame);
        chronometer = findViewById(R.id.game_time);
        chronometer.setFormat("Time Spent:  %s");
        chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!gameProcessing) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    pauseOffset = 0;
                    chronometer.start();
                    gameProcessing = true;
                }
                gridView.setEnabled(true);
                btnStart.setVisibility(View.GONE);
                btnRestart.setVisibility(View.VISIBLE);
            }
        });
    }

    private void populateGrid() {
        gridArr = new Box[columncount * columncount];
        for (int i = 0; i < gridArr.length; i++) {
            gridArr[i] = new Box(R.color.colorGrey, "Grey", i);
        }

        int imgSize = getSize(columncount);
        gridView = findViewById(R.id.grid_game);
        gridView.setColumnWidth(imgSize);
//        ImageView iv = gridView.findViewById(R.id.img_gridItem);
//        iv.setLayoutParams(new LinearLayout.LayoutParams(imgSize,imgSize));
        gridView.setEnabled(false);
        iAdapter = new ImageAdapter(this, gridArr, imgSize);
        gridView.setAdapter(iAdapter);
        nextColor = findViewById(R.id.img_nextColor);
        nextColor.setImageResource(color1);

        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView iv = view.findViewById(R.id.img_gridItem);
                if (iv.getTag() == "Grey") {
                    view.setClickable(false);
                    view.setEnabled(false);
                    view.setFocusable(false);
                    gameEnd();
                    int x = position / columncount;
                    int y = position % columncount;
                    //number to get the present color
                    int c = gridArr[position].changeColor(num, color1, color2);
                    int d;
                    //number to get the next color
                    if (c == color1) {
                        d = color2;
                    } else {
                        d = color1;
                    }
//if the color is grey enable to change color, or disable the color change
                    iv.setImageResource(c);
                    iv.setTag("Changed");
                    nextColor.setImageResource(d);
                    num++;
                    checkWin();
                }
            }
        });
    }

    private void checkWin() {
        long timeused = (SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000;
        win = true;
        gamingTime = timeused;

        //check the horizontal
        for (int i = 0; i < columncount * columncount - 2; i++) {
            if (gridArr[i].getColorSource() == color1 && gridArr[i + 1].getColorSource() == color1 && gridArr[i + 2].getColorSource() == color1) {
                toastmsg = Toast.makeText(getApplicationContext(), "Player Lose in Hor" + " " + timeused + "s", Toast.LENGTH_SHORT);
                toastmsg.show();
                gameLose();
                win = false;
            }

            if (gridArr[i].getColorSource() == color2 && gridArr[i + 1].getColorSource() == color2 && gridArr[i + 2].getColorSource() == color2) {
                nextColor = findViewById(R.id.img_nextColor);
                nextColor.setImageResource(R.color.colorAccent);
                toastmsg = Toast.makeText(getApplicationContext(), "Player Lose in Hor" + " " + timeused + "s", Toast.LENGTH_SHORT);
                toastmsg.show();
                gameLose();
                win = false;
            }
        }
        //check for the Vertical
        for (int i = 0; i < columncount * columncount - columncount * 2; i++) {
            if (gridArr[i].getColorSource() == color1 && gridArr[i + columncount].getColorSource() == color1 && gridArr[i + columncount * 2].getColorSource() == color1) {
                Toast.makeText(getApplicationContext(), "Player Lose in Ver" + " " + timeused + "s", Toast.LENGTH_SHORT).show();
                gameLose();
                win = false;
            }

            if (gridArr[i].getColorSource() == color2 && gridArr[i + columncount].getColorSource() == color2 && gridArr[i + columncount * 2].getColorSource() == color2) {
                Toast.makeText(getApplicationContext(), "Player Lose in Ver" + " " + timeused + "s", Toast.LENGTH_SHORT).show();
                gameLose();
                win = false;
            }
        }

        if (win && num > columncount * columncount) {
            txtResult.setText("Win");
            txtResult.setTextColor(Color.GREEN);
            txtResult.setVisibility(View.VISIBLE);
            if (newRecord(gamingTime)) {
                if (logined) {
                    String theDate = getDateInformat();
                    Record recordToAdd = new Record(logineduserName, gamingTime, theDate);
                    insertNewRecord(recordToAdd);
                    showDialog(logineduserName);
                } else {
                    // update if the user already login
                    EnterUserName userNamedialog = new EnterUserName();
                    userNamedialog.show(getSupportFragmentManager(), "Enter user Name");
                }

            }
        }
    }

    private void gameEnd() {
        if (num >= columncount * columncount) {
            gridView.setEnabled(false);
            gameProcessing = false;
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            checkWin();
        }
    }

    private void gameLose() {
        gridView.setEnabled(false);
        chronometer.stop();
        gameProcessing = false;
        pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
        txtResult.setText("Lose");
        txtResult.setTextColor(Color.RED);
        txtResult.setVisibility(View.VISIBLE);
    }

    //update the table in SQLite room
    @Override
    public void applyName(String name) {
        String username = name;
        String theDate = getDateInformat();
        Record recordToAdd = new Record(username, gamingTime, theDate);
        insertNewRecord(recordToAdd);
        showDialog(username);
    }

    //check if the record can be a new top 10 highest record
    private Boolean newRecord(long newRecord) {
        Record lastRecord = RecordDB.getInstance(getApplicationContext()).recordDao().getLastRecordS();
        if (lastRecord.getTime() > newRecord) {
            return true;
        } else {
            return false;
        }
    }

    //get the size width and height
    private int getSize(int x) {
        int size = 0;
        if (x == 4) {
            size = 240;
        }

        if (x == 5) {
            size = 190;
        }

        if (x == 6) {
            size = 160;
        }
        return size;
    }

    private void insertNewRecord(Record recordToAdd) {
        ArrayList<Record> oldList = new ArrayList<>();
        oldList = (ArrayList<Record>) RecordDB.getInstance(getApplicationContext()).recordDao().getAllRecords();
        if (oldList.size() >= 10) {
            oldList.add(recordToAdd);
            Collections.sort(oldList);
            oldList.remove(10);
            //delete all the present record
            RecordDB.getInstance(getApplicationContext()).recordDao().clearTable();

            for (Record r : oldList) {
                RecordDB.getInstance(getApplicationContext()).recordDao().insert(r);
            }
        } else {
            RecordDB.getInstance(getApplicationContext()).recordDao().insert(recordToAdd);
        }
    }

    private String getDateInformat() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String theDate = dateFormat.format(date);
        return theDate;
    }

    private void showDialog(String name) {
        confirm.setTitle("New Record Saved!");
        confirm.setMessage("Congratulation " + name + " ! You have reach the top 10 highest scores! Your record has been saved successfully!!");
        confirm.setNegativeButton("Restart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startNewGame();
            }
        });
        confirm.setPositiveButton("Check High Score", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(GamePage.this, HighScorePage.class);
                startActivity(intent);
            }
        });
        confirm.create();
        confirm.show();
    }
}


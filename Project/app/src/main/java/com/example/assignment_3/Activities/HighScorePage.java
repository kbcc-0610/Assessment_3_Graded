package com.example.assignment_3.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_3.Database.RecordDB;
import com.example.assignment_3.Model.Record;
import com.example.assignment_3.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.ArrayList;
import java.util.Collections;

public class HighScorePage extends AppCompatActivity implements RecyclerViewAdapterWithButton.ButtonClick {
    private RecyclerViewAdapterWithButton adapter;
    private RecyclerView recyclerView;
    private CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score_page);
        getSupportActionBar().setTitle("High Scores");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecordDB.initData(this);

        ArrayList<Record> recordArrayList = (ArrayList<Record>) RecordDB.getInstance(this).recordDao().getAllRecords();
        Collections.sort(recordArrayList);
        recyclerView = findViewById(R.id.HighScor_recyclerView);
        recyclerView.getAdapter();
        adapter = new RecyclerViewAdapterWithButton(recordArrayList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void buttonOnclick(int position) {
        //after click the button
        FacebookSdk.sdkInitialize(this);

        ArrayList<Record> recordArrayList = (ArrayList<Record>) RecordDB.getInstance(this).recordDao().getAllRecords();
        Collections.sort(recordArrayList);
        Record record = recordArrayList.get(position);
        //Init Facebook
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        ShareLinkContent linkContent = new ShareLinkContent.Builder().setQuote("I am " + record.getName()
                + ", i finish this game in " + record.getTime() + "S,  wanna try with me ?").
                setContentUrl(Uri.parse("https://github.com/kbcc-0610/Assessment_3_Graded")).build();

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            shareDialog.show(linkContent);
        }

        Log.d("CheckOnclick", String.valueOf(position));
    }
}

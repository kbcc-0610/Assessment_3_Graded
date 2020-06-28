package com.example.assignment_3.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.assignment_3.Database.RecordDB;
import com.example.assignment_3.Model.Record;
import com.example.assignment_3.R;

import java.util.ArrayList;
import java.util.Collections;

public class HighScorePage extends AppCompatActivity {
    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

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
        adapter = new RecyclerViewAdapter(recordArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

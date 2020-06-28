package com.example.assignment_3.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.assignment_3.Model.Record;

import java.util.List;

@Dao
public interface RecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Record... records);

    // update
    @Update
    public void update(Record... records);

    // delete
    @Delete
    public void deleteRecord(Record... records);

    // delete all
    @Query("DELETE FROM record")
    public void clearTable();

    // read all
    @Query("SELECT * FROM record")
    public List<Record> getAllRecords();

    //read top 10
    @Query("SELECT  * FROM record order by Time Desc Limit 1 ")
    public Record getLastRecordS();

    // read one by id
    @Query("SELECT * FROM record WHERE id = :recordid")
    public Record getRecordById(int recordid);
}

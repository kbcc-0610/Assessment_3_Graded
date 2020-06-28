package com.example.assignment_3.Database;

import android.content.Context;
import android.provider.ContactsContract;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.assignment_3.Dao.RecordDao;
import com.example.assignment_3.Model.Record;

import java.util.ArrayList;

@Database(
        entities = {Record.class},
        version = 1,
        exportSchema = false
)
public abstract class RecordDB extends RoomDatabase {

    public abstract RecordDao recordDao();

    private static RecordDB recordDB;

    private ArrayList<Record> records;

    public static RecordDB getInstance(final Context context) {
        if (recordDB == null) {
            recordDB = Room.databaseBuilder(context.getApplicationContext(), RecordDB.class, "Record_room.db").allowMainThreadQueries().build();


        }
        return recordDB;
    }

    public static int initData(final Context context) {
        RecordDB db = getInstance(context);
        if (db.recordDao().getAllRecords().size() == 0) {
            db.recordDao().insert(new Record("Joe", 35, "2011-11-11"),
                    new Record("Thomas", 24, "2011-10-11"),
                    new Record("Jon", 15, "2011-12-11"));
        }
        return db.recordDao().getAllRecords().size();
    }

    public Record get(int id) {
        Record r = null;
        if (id >= 0 && id < records.size()) {
            r = records.get(id);
        }
        return r;
    }
}

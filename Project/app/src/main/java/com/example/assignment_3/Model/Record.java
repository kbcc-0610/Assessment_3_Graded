package com.example.assignment_3.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "record")
public class Record implements Parcelable ,Comparable<Record>{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "Name")
    private String name;

    @ColumnInfo(name = "Time")
    private long time;

    @ColumnInfo(name = "Date")
    private String date;

    public Record() {
    }

    public Record(int id, String name, long time, String date) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.date = date;
    }

    @Ignore
    public Record(String name, long time, String date) {
        this.name = name;
        this.time = time;
        this.date = date;
    }

    @Ignore
    protected Record(Parcel in) {
        name = in.readString();
        time = in.readLong();
        date = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static final Parcelable.Creator<Record> CREATOR = new Parcelable.Creator<Record>() {
        @Override
        public Record createFromParcel(Parcel source) {
            return new Record(source);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeLong(time);
        dest.writeString(date);
    }

    @Override
    public int compareTo(Record record) {
        return (this.getTime() <record.getTime() ? -1 : (this.getTime() == record.getTime()? 0:1));
    }
}

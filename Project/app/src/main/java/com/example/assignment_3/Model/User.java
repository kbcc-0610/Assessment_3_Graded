package com.example.assignment_3.Model;

import android.app.Application;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User extends Application implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "username")
    private String userName;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "Email")
    private String email;

    @ColumnInfo(name = "membership")
    private int membership;
    // 1 ====>normal user
    // 2 ====>VIP

    public User(int id, String userName, String password,String email,int membership) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.membership = membership;
    }

    @Ignore
    public User(String userName, String password,int membership) {
        this.userName = userName;
        this.password = password;
        this.email = "kobezhangcc@gmail.com";
        this.membership = membership;
    }
    @Ignore
    public User(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.membership = 1;
    }

    protected User(Parcel in) {
        userName = in.readString();
        password = in.readString();
        email = in.readString();
        membership = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMembership() {
        return membership;
    }

    public void setMembership(int membership) {
        this.membership = membership;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(password);
        dest.writeString(email);
        dest.writeInt(membership);
    }
}

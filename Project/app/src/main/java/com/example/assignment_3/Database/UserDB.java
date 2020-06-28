package com.example.assignment_3.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.assignment_3.Dao.UserDao;
import com.example.assignment_3.Model.User;

import java.util.ArrayList;

@Database(
        entities = {User.class},
        version = 1,
        exportSchema = false

)
public abstract class UserDB extends RoomDatabase {
    public abstract UserDao userDao();

    private static UserDB userDB;

    private ArrayList<User> userList;

    public static UserDB getInstance(final Context context){
        if(userDB == null){
            userDB = Room.databaseBuilder(context.getApplicationContext(),UserDB.class,"User_room.db").allowMainThreadQueries().build();
        }
        return userDB;
    }

    public static int initData(final Context context){
        UserDB db = getInstance(context);
        if(db.userDao().getAllUsers().size() ==0){
            db.userDao().insert(
                    new User("Larry","94182391238"),
                    new User("Mike","1410519031"),
                    new User("Nicky","dasdk1391"),
                    new User("Kbc13","dkanzm14142"),
                    new User("test","test")
            );
        }
        return db.userDao().getAllUsers().size();
    }

    public User get(int id){
        User u = null;
        if(id>=0 && id<userList.size()){
            u = userList.get(id);
        }
        return u;
    }

}

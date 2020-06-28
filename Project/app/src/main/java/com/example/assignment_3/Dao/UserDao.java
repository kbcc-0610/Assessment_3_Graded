package com.example.assignment_3.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.assignment_3.Model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(User... users);

    // update
    @Update
    public void update(User... users);

    // delete
    @Delete
    public void deleteUser(User... users);

    // delete all
    @Query("DELETE FROM user")
    public void clearTable();

    // read all
    @Query("SELECT * FROM user")
    public List<User> getAllUsers();

    // read one by id
    @Query("SELECT * FROM user WHERE id = :userID")
    public User getUserById(int userID);

}

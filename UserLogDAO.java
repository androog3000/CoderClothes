package com.daclink.coderclothes.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.daclink.coderclothes.UserLog;

import java.util.List;

@Dao
public interface UserLogDAO {

    @Insert
    void insert(UserLog... userLogs);

    @Update
    void update(UserLog... userLogs);

    @Delete
    void delete(UserLog userlog);

    @Query("SELECT * FROM " + AppDatabase.USERLOG_TABLE + " WHERE mUserId = :userId")
    UserLog getUserLogsById(int userId);

    @Query("SELECT * FROM " + AppDatabase.USERLOG_TABLE + " WHERE mUsername = :username")
    UserLog getUserByUsername(String username);

    //if we want to get everything from the table
    @Query("SELECT * FROM " + AppDatabase.USERLOG_TABLE)
    List<UserLog> getAllUsers();


}

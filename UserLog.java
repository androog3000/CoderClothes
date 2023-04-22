package com.daclink.coderclothes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.daclink.coderclothes.db.AppDatabase;

@Entity(tableName = AppDatabase.USERLOG_TABLE)
public class UserLog {

    @PrimaryKey(autoGenerate = true)
    private int mUserId;

    private String mUsername;
    private String mPassword;
    private boolean mIsAdmin;

    public UserLog(String mUsername, String mPassword, boolean mIsAdmin) {
        this.mUsername = mUsername;
        this.mPassword = mPassword;
        this.mIsAdmin = mIsAdmin;

        //removed id from constructor following along with "Adding users to GymLog" - 43:00
        //mUserId = userId;
    }


    public int getMUserId() {
        return mUserId;
    }

    public void setMUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public String getMUsername() {
        return mUsername;
    }

    public void setMUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getMPassword() {
        return mPassword;
    }

    public void setMPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public boolean getMIsAdmin() {
        return mIsAdmin;
    }

    public void setMIsAdmin(boolean mIsAdmin) {
        this.mIsAdmin = mIsAdmin;
    }

    @Override
    public String toString() {
        return "UserLog{" +
                "UserId =" + mUserId +
                ", Username ='" + mUsername + '\'' +
                ", Password ='" + mPassword + '\'' +
                ", IsAdmin? =" + mIsAdmin +
                '}';
    }
}

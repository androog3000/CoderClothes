package com.daclink.coderclothes.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.daclink.coderclothes.UserLog;

@Database(entities = {UserLog.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{

    //previously named "com.daclink.coderclothes.USERLOG_DATABASE"
    public static final String DB_NAME = "CODERCLOTHES_DATABASE";

    //previously named "com.daclink.coderclothes.USERLOG_TABLE"
    public static final String USERLOG_TABLE = "USERLOG_TABLE";

    public static final String PRODUCT_TABLE = "PRODUCT_TABLE";

    public static final String CART_TABLE = "CART_TABLE";

    public abstract UserLogDAO getUserLogDAO();
}

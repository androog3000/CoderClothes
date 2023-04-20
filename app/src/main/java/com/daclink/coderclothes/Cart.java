package com.daclink.coderclothes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.daclink.coderclothes.db.AppDatabase;

@Entity(tableName = AppDatabase.CART_TABLE)
public class Cart {

    @PrimaryKey(autoGenerate = true)
    private int mCartId;

    public int getCartId() {
        return mCartId;
    }

    public void setCartId(int mCartId) {
        this.mCartId = mCartId;
    }

    //private int altId;

}

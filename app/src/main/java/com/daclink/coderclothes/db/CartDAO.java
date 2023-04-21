package com.daclink.coderclothes.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.daclink.coderclothes.Cart;
import com.daclink.coderclothes.UserLog;

import java.util.List;

@Dao
public interface CartDAO {

    @Insert
    void insert(Cart... carts);

    @Update
    void update(Cart... carts);

    @Delete
    void delete(Cart cart);

    @Query("SELECT * FROM " + AppDatabase.CART_TABLE + " WHERE mCartId = :cartId")
    Cart getCartById(int cartId);

    @Query("SELECT * FROM " + AppDatabase.CART_TABLE + " WHERE cartName = :name")
    Cart getCartByName(String name);

    @Query("SELECT * FROM " + AppDatabase.CART_TABLE)
    List<Cart> getAllCarts();
}

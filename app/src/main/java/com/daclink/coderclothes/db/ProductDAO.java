package com.daclink.coderclothes.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.daclink.coderclothes.Product;

import java.util.List;

@Dao
public interface ProductDAO {

    @Insert
    void insert(Product... products);

    @Update
    void update(Product... products);

    @Delete
    void delete(Product product);

    @Query("SELECT * FROM " + AppDatabase.PRODUCT_TABLE + " WHERE mProductId = :productId")
    Product getProductById(int productId);

    @Query("SELECT * FROM " + AppDatabase.PRODUCT_TABLE + " WHERE mProductName = :productName")
    Product getProductByName(String productName);

    //if we want to get everything from the table
    @Query("SELECT * FROM " + AppDatabase.PRODUCT_TABLE)
    List<Product> getAllProducts();

}
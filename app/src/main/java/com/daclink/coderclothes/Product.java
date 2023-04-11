package com.daclink.coderclothes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.daclink.coderclothes.db.AppDatabase;

@Entity(tableName = AppDatabase.PRODUCT_TABLE)
public class Product {

    @PrimaryKey(autoGenerate = true)
    private int mProductId;

    private String mProductName;
    private String mProductDescription;
    private int mProductQuantity;

    public Product(String productName, String productDescription, int productQuantity) {
        this.mProductName = productName;
        this.mProductDescription = productDescription;
        this.mProductQuantity = productQuantity;

    }

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String mProductName) {
        this.mProductName = mProductName;
    }

    public String getProductDescription() {
        return mProductDescription;
    }

    public void setProductDescription(String mProductDescription) {
        this.mProductDescription = mProductDescription;
    }

    public int getProductQuantity() {
        return mProductQuantity;
    }

    public void setProductQuantity(int mProductQuantity) {
        this.mProductQuantity = mProductQuantity;
    }
}

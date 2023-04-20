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
    private double mProductPrice;

    //note: not worrying about this for now
    //private int mProductQuantity;

    public Product(String productName, String productDescription, double productPrice) {
        this.mProductName = productName;
        this.mProductDescription = productDescription;
        //note: not worrying about quantity of products in stock for now, consider adding back later
        //this.mProductQuantity = productQuantity;
        this.mProductPrice = productPrice;
    }

    public int getProductId() {
        return mProductId;
    }

    public void setProductId(int mProductId) {
        this.mProductId = mProductId;
    }

    public double getProductPrice() {
        return mProductPrice;
    }

    public void setProductPrice(double mProductPrice) {
        this.mProductPrice = mProductPrice;
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

    @Override
    public String toString() {
        return  mProductName + " - $" + mProductPrice + "\n" + mProductDescription;
    }

    //    public int getProductQuantity() {
//        return mProductQuantity;
//    }
//
//    public void setProductQuantity(int mProductQuantity) {
//        this.mProductQuantity = mProductQuantity;
//    }
}

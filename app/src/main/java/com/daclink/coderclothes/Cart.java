package com.daclink.coderclothes;

import androidx.room.Entity;

import androidx.room.PrimaryKey;

import com.daclink.coderclothes.db.AppDatabase;

import java.util.ArrayList;
import java.util.HashMap;

@Entity(tableName = AppDatabase.CART_TABLE)
public class Cart {

    @PrimaryKey(autoGenerate = true)
    private int mCartId;

    private String pajamasName = "Programma's Pajamas";
    private double pajamasPrice = 19.99;
    private int pajamasQuantity = 0;

    private String pantsName = "Coderoy Pants";
    private double pantsPrice = 14.99;
    private int pantsQuantity = 0;

    private String glassesName = "See-Sharp Blue-Lens Glasses";
    private double glassesPrice = 24.99;
    private int glassesQuantity = 0;

    private String beverageName = "Memory Leak Alkaline Beverage";
    private double beveragePrice = 4.99;
    private int beverageQuantity = 0;

    //public Cart(){}

    public String getPajamasName() {
        return pajamasName;
    }

    public void setPajamasName(String pajamasName) {
        this.pajamasName = pajamasName;
    }

    public double getPajamasPrice() {
        return pajamasPrice;
    }

    public void setPajamasPrice(double pajamasPrice) {
        this.pajamasPrice = pajamasPrice;
    }

    public int getPajamasQuantity() {
        return pajamasQuantity;
    }

    public void setPajamasQuantity(int pajamasQuantity) {
        this.pajamasQuantity = pajamasQuantity;
    }

    public String getPantsName() {
        return pantsName;
    }

    public void setPantsName(String pantsName) {
        this.pantsName = pantsName;
    }

    public double getPantsPrice() {
        return pantsPrice;
    }

    public void setPantsPrice(double pantsPrice) {
        this.pantsPrice = pantsPrice;
    }

    public int getPantsQuantity() {
        return pantsQuantity;
    }

    public void setPantsQuantity(int pantsQuantity) {
        this.pantsQuantity = pantsQuantity;
    }

    public String getGlassesName() {
        return glassesName;
    }

    public void setGlassesName(String glassesName) {
        this.glassesName = glassesName;
    }

    public double getGlassesPrice() {
        return glassesPrice;
    }

    public void setGlassesPrice(double glassesPrice) {
        this.glassesPrice = glassesPrice;
    }

    public int getGlassesQuantity() {
        return glassesQuantity;
    }

    public void setGlassesQuantity(int glassesQuantity) {
        this.glassesQuantity = glassesQuantity;
    }

    public String getBeverageName() {
        return beverageName;
    }

    public void setBeverageName(String beverageName) {
        this.beverageName = beverageName;
    }

    public double getBeveragePrice() {
        return beveragePrice;
    }

    public void setBeveragePrice(double beveragePrice) {
        this.beveragePrice = beveragePrice;
    }

    public int getBeverageQuantity() {
        return beverageQuantity;
    }

    public void setBeverageQuantity(int beverageQuantity) {
        this.beverageQuantity = beverageQuantity;
    }

    public int getCartId() {
        return mCartId;
    }

    public void setCartId(int mCartId) {
        this.mCartId = mCartId;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "mCartId=" + mCartId +
                ", pajamasName='" + pajamasName + '\'' +
                ", pajamasPrice=" + pajamasPrice +
                ", pajamasQuantity=" + pajamasQuantity +
                ", pantsName='" + pantsName + '\'' +
                ", pantsPrice=" + pantsPrice +
                ", pantsQuantity=" + pantsQuantity +
                ", glassesName='" + glassesName + '\'' +
                ", glassesPrice=" + glassesPrice +
                ", glassesQuantity=" + glassesQuantity +
                ", beverageName='" + beverageName + '\'' +
                ", beveragePrice=" + beveragePrice +
                ", beverageQuantity=" + beverageQuantity +
                '}';
    }
}

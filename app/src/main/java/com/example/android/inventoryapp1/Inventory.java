package com.example.android.inventoryapp1;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_table")
public class Inventory {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    byte [] image;

    private String name;

    private int quantity;

    private double price;

    private String supplier;

    public Inventory(String name, byte[] image, int quantity, double price, String supplier) {
        this.name = name;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.supplier = supplier;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public byte[] getImage(){
        return image; }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getSupplier() {
        return supplier;
    }
}

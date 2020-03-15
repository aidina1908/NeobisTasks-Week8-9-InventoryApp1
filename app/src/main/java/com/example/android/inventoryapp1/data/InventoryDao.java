package com.example.android.inventoryapp1.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.android.inventoryapp1.Inventory;
import java.util.List;

@Dao
public interface InventoryDao {

    @Insert
    void insert(Inventory inventory);

    @Update
    void update(Inventory inventory);

    @Delete
    void delete(Inventory inventory);

    @Query("DELETE FROM product_table WHERE id = :id")
    void delete(long id);

    @Query("DELETE FROM product_table WHERE name = :name  AND price=:price AND quantity = :quantity AND supplier = :supplier")
    void deleteInventory(String name, double price, int quantity, String supplier);


    @Query("DELETE FROM product_table")
    void deleteAllInventories();

    @Query("SELECT * FROM product_table ORDER BY quantity DESC")
    LiveData<List<Inventory>>getAllInventories();
}

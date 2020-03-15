package com.example.android.inventoryapp1.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.android.inventoryapp1.Inventory;

@Database(entities = {Inventory.class}, version = 2)
public abstract class InventoryDatabase extends RoomDatabase {

    private static InventoryDatabase instance;

    public abstract InventoryDao inventoryDao();

    public static synchronized InventoryDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    InventoryDatabase.class, "inventory_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;

    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private InventoryDao inventoryDao;

        private PopulateDbAsyncTask(InventoryDatabase db){
            inventoryDao = db.inventoryDao();
        }

        @Override
        protected Void doInBackground(Void... voids){
           /*  inventoryDao.insert(new Inventory("Mist",15,18,  "Victoria's Secret"));
             inventoryDao.insert(new Inventory("Lotion",15,18,  "Victoria's Secret"));
             inventoryDao.insert(new Inventory("Shampoo",6,5,  "Wella"));*/
         return null;
        }
    }
}

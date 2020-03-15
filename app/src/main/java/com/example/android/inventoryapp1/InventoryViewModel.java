package com.example.android.inventoryapp1;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.android.inventoryapp1.data.InventoryRepository;
import java.util.List;

public class InventoryViewModel extends AndroidViewModel {
    private InventoryRepository repository;
    private LiveData<List<Inventory>> allInventories;

    public InventoryViewModel(@NonNull Application application){
        super(application);
        repository = new InventoryRepository(application);
        allInventories = repository.getAllInventories();

    }
    public void insert(Inventory inventory){
        repository.insert(inventory);
    }
    public void update(Inventory inventory){
        repository.update(inventory);
    }
    public void delete(Inventory inventory) { repository.delete(inventory);}
    public void deleteAllInventories() { repository.deleteAllInventories();
    }
    public LiveData<List<Inventory>> getAllInventories(){
        return allInventories;
    }

}

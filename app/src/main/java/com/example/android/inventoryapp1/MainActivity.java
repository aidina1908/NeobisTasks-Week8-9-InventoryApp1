package com.example.android.inventoryapp1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.example.android.inventoryapp1.data.InventoryRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Delete;

import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_INVENTORY_REQUEST = 1;
    public static final int EDIT_INVENTORY_REQUEST = 2;

    private InventoryViewModel inventoryViewModel;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final InventoryAdapter adapter = new InventoryAdapter();
        recyclerView.setAdapter(adapter);

        inventoryViewModel = ViewModelProviders.of(this).get(InventoryViewModel.class);
        inventoryViewModel.getAllInventories().observe(this, new Observer<List<Inventory>>() {
            @Override
            public void onChanged(@Nullable List<Inventory> inventories) {
                adapter.srtInventories(inventories);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                inventoryViewModel.delete(adapter.getInventoryAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Inventory deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivityForResult(intent,  ADD_INVENTORY_REQUEST);
            }
        });

        adapter.setOnItemClickListener(new InventoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Inventory inventory) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra(EditActivity.EXTRA_ID,inventory.getId());
                intent.putExtra(EditActivity.EXTRA_IMAGE,inventory.getImage());
                intent.putExtra(EditActivity.EXTRA_NAME,inventory.getName());
                intent.putExtra(EditActivity.EXTRA_PRICE,inventory.getPrice());
                intent.putExtra(EditActivity.EXTRA_QUANTITY,inventory.getQuantity());
                intent.putExtra(EditActivity.EXTRA_SUPPLIER,inventory.getSupplier());
                startActivityForResult(intent, EDIT_INVENTORY_REQUEST);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_INVENTORY_REQUEST && resultCode == RESULT_OK){
            String name = data.getStringExtra(EditActivity.EXTRA_NAME);
            byte [] image = data.getByteArrayExtra(EditActivity.EXTRA_IMAGE);
            double price = data.getDoubleExtra(EditActivity.EXTRA_PRICE, 1);
            int quantity = data.getIntExtra(EditActivity.EXTRA_QUANTITY,1);
            String supplier = data.getStringExtra(EditActivity.EXTRA_SUPPLIER);

            Inventory inventory = new Inventory(name, image, quantity, price, supplier);
            inventoryViewModel.insert(inventory);

            Toast.makeText(this, "Product saved", Toast.LENGTH_SHORT).show();
        }else  if(requestCode == EDIT_INVENTORY_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(EditActivity.EXTRA_ID,-1);

            if (id == -1){
                Toast.makeText(this,"Inventory can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = data.getStringExtra(EditActivity.EXTRA_NAME);
            byte [] image = data.getByteArrayExtra(EditActivity.EXTRA_IMAGE);
            double price = data.getDoubleExtra(EditActivity.EXTRA_PRICE, 1);
            int quantity = data.getIntExtra(EditActivity.EXTRA_QUANTITY,1);
            String supplier = data.getStringExtra(EditActivity.EXTRA_SUPPLIER);

            Inventory inventory = new Inventory(name, image, quantity, price, supplier);
            inventory.setId(id);
            inventoryViewModel.update(inventory);

        }else {
            Toast.makeText(this, "Product not saved", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.delete_all:
                showAlertDialog();
                return true;
                default: return super.onOptionsItemSelected(item);

        }
    }

    public void showAlertDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete");
        alert.setMessage("Do you want delete all products?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                    inventoryViewModel.deleteAllInventories();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.create().show();
    }
}

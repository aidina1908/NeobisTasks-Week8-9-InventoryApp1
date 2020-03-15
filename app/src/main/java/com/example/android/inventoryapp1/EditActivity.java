package com.example.android.inventoryapp1;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.inventoryapp1.data.InventoryDao;

public class EditActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.android.inventoryapp1.EXTRA_ID";
    public static final String EXTRA_NAME =
            "com.example.android.inventoryapp1.EXTRA_NAME";
    public static final String EXTRA_IMAGE =
            "com.example.android.inventoryapp1.EXTRA_IMAGE";
    public static final String EXTRA_PRICE =
            "com.example.android.inventoryapp1.EXTRA_PRICE";
    public static final String EXTRA_QUANTITY =
            "com.example.android.inventoryapp1.EXTRA_QUANTITY";
    public static final String EXTRA_SUPPLIER =
            "com.example.android.inventoryapp1.EXTRA_SUPPLIER";

    private InventoryViewModel inventoryViewModel;

    private ImageView productImageView;
    Button button;
    Bitmap bmpImage;
    private EditText editTextName;
    private EditText editTextPrice;
    private EditText editTextSupplier;
    private NumberPicker numberPickerQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);

        productImageView = findViewById(R.id.image);
        button = findViewById(R.id.button_camera);
        bmpImage = null;
        editTextName = findViewById(R.id.edit_text_name);
        editTextPrice = findViewById(R.id.edit_text_price);
        editTextSupplier = findViewById(R.id.edit_text_supplier);
        numberPickerQuantity = findViewById(R.id.number_picker_quantity);

        numberPickerQuantity.setMinValue(1);
        numberPickerQuantity.setMaxValue(100);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Product");
            editTextName.setText(intent.getStringExtra(EXTRA_NAME));
            //   productImageView.setImage(DataConverter.convertImage2ByteArray(bmpImage));
            // productImageView.setImageBitmap(bmpImage);
            editTextPrice.setText(String.valueOf(intent.getDoubleExtra(EXTRA_PRICE, 1)));
            numberPickerQuantity.setValue(intent.getIntExtra(EXTRA_QUANTITY, 1));
            editTextSupplier.setText((intent.getStringExtra(EXTRA_SUPPLIER)));
        } else {
            setTitle("Add Product");

        }

    }

    private void saveInventory() {
        String name = editTextName.getText().toString();
       // byte[] image = productImageView.
       // Bitmap bitmap = ((BitmapDrawable)productImageView.getDrawable()).getBitmap();
       // Bitmap bitmap = productImageView.getDrawingCache();
        double price = Double.parseDouble(editTextPrice.getText().toString());
        int quantity = numberPickerQuantity.getValue();
        String supplier = editTextSupplier.getText().toString();

        if (name.trim().isEmpty() ||  supplier.trim().isEmpty()) {
            Toast.makeText(this, "Please insert all data", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
       // data.putExtra(EXTRA_IMAGE,image);
        data.putExtra(EXTRA_PRICE, price);
        data.putExtra(EXTRA_QUANTITY, quantity);
        data.putExtra(EXTRA_SUPPLIER, supplier);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveInventory();
                return true;
            case R.id.action_delete:
                showAlertDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void showAlertDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete");
        alert.setMessage("Do you want delete product?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int i) {
            // inventoryViewModel.delete(editTextName,editTextPrice,numberPickerQuantity,editTextSupplier);
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }

            ;

        });
        alert.create().show();
    }

    final int CAMERA_INTENT = 51;

    public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_INTENT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_INTENT:
                bmpImage = (Bitmap) data.getExtras().get("data");
                if (bmpImage != null) {
                    productImageView.setImageBitmap(bmpImage);
                } else {
                    Toast.makeText(this, "Bitmap is null", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }
}




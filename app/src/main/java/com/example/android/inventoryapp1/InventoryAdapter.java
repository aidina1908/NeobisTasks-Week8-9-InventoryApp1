package com.example.android.inventoryapp1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryHolder> {
    private List<Inventory> inventories = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public InventoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new InventoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryHolder holder, int position) {
        Inventory currentInventory = inventories.get(position);
       //holder.imageViewImage.setImageResource(R.id.p);
        holder.textViewName.setText(currentInventory.getName());
        holder.textViewPrice.setText(String.valueOf(currentInventory.getPrice()));
        holder.textViewQuantity.setText(String.valueOf(currentInventory.getQuantity()));
        holder.textViewSupplier.setText(currentInventory.getSupplier());

    }

    @Override
    public int getItemCount() {
        return inventories.size();
    }

    public void srtInventories(List<Inventory> inventories) {
        this.inventories = inventories;
        notifyDataSetChanged();
    }
    public Inventory getInventoryAt(int position){
        return inventories.get(position);
    }

    class InventoryHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewImage;
        private TextView textViewName;
        private TextView textViewPrice;
        private TextView textViewQuantity;
        private TextView textViewSupplier;

        public InventoryHolder(@NonNull View itemView) {
            super(itemView);
            imageViewImage = itemView.findViewById(R.id.product_image);
            textViewName = itemView.findViewById(R.id.product_name);
            textViewPrice = itemView.findViewById(R.id.product_price);
            textViewQuantity = itemView.findViewById(R.id.product_quantity);
            textViewSupplier = itemView.findViewById(R.id.product_supplier);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(inventories.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Inventory inventory);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

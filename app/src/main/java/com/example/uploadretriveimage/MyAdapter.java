package com.example.uploadretriveimage;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private List<DataClass> dataList;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Images");

    public MyAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView captionText;
        public ImageButton editButton;
        public ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            captionText = itemView.findViewById(R.id.captionText);
            editButton = itemView.findViewById(R.id.editButton);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataClass data = dataList.get(position);
        holder.captionText.setText(data.getCaption());
        Picasso.get().load(data.getImageUrl()).into(holder.imageView);

        holder.editButton.setOnClickListener(v -> showEditDialog(data.getKey(), data.getCaption()));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private void showEditDialog(String captionKey, String currentCaption) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Caption");

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(currentCaption);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String newCaption = input.getText().toString();
            updateCaption(captionKey, newCaption);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void updateCaption(String captionKey, String newCaption) {
        if (captionKey == null) {
            Toast.makeText(context, "Error: Key is null", Toast.LENGTH_SHORT).show();
            return;
        }
        databaseReference.child(captionKey).child("caption").setValue(newCaption)
                .addOnSuccessListener(aVoid -> Toast.makeText(context, "Caption updated", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(context, "Failed to update caption", Toast.LENGTH_SHORT).show());
    }
}

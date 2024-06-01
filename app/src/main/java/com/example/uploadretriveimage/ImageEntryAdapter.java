package com.example.uploadretriveimage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;

public class ImageEntryAdapter extends RecyclerView.Adapter<ImageEntryAdapter.ImageEntryViewHolder> {
    private List<ImageEntry> entries;
    private Context context;

    public ImageEntryAdapter(List<ImageEntry> entries) {
        this.entries = entries;
    }

    @NonNull
    @Override
    public ImageEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ImageEntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageEntryViewHolder holder, int position) {
        ImageEntry entry = entries.get(position);
        holder.recyclerCaption.setText(entry.getCaption());
        Glide.with(context).load(entry.getImageUrl()).into(holder.recyclerImage);

        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditCaptionActivity.class);
            intent.putExtra("documentId", entry.getId());
            intent.putExtra("caption", entry.getCaption());
            context.startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(v -> {
            // Get the position of the item to be deleted
            int adapterPosition = holder.getAdapterPosition();
            // Get the entry to be deleted
            ImageEntry entryToDelete = entries.get(adapterPosition);
            // Remove the entry from the list
            entries.remove(adapterPosition);
            // Notify adapter of the removal
            notifyItemRemoved(adapterPosition);
            // Delete the entry from Firebase
            deleteEntryFromFirebase(entryToDelete.getId());
        });
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    static class ImageEntryViewHolder extends RecyclerView.ViewHolder {
        ImageView recyclerImage;
        TextView recyclerCaption;
        ImageButton editButton;
        ImageButton deleteButton;

        public ImageEntryViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerImage = itemView.findViewById(R.id.recyclerImage);
            recyclerCaption = itemView.findViewById(R.id.recyclerCaption);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton); // Initialize deleteButton here
        }
    }

    private static void deleteEntryFromFirebase(String entryId) {
        // Reference to the entry in Firebase
        DatabaseReference entryRef = FirebaseDatabase.getInstance().getReference("Images").child(entryId);
        // Remove the entry from Firebase
        entryRef.removeValue();
    }
}

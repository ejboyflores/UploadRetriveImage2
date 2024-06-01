package com.example.uploadretriveimage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditCaptionActivity extends AppCompatActivity {
    private EditText editCaption;
    private Button saveButton;
    private String documentId;
    private String uid;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_caption);

        editCaption = findViewById(R.id.editCaption);
        saveButton = findViewById(R.id.saveButton);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(EditCaptionActivity.this, AuthActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        uid = currentUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Images").child(uid);

        Intent intent = getIntent();
        if (intent != null) {
            documentId = intent.getStringExtra("documentId");
            String caption = intent.getStringExtra("caption");
            editCaption.setText(caption);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCaption();
            }
        });
    }

    private void saveCaption() {
        String caption = editCaption.getText().toString();
        if (caption.isEmpty()) {
            Toast.makeText(this, "Caption cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update the caption in Firebase
        databaseReference.child(documentId).child("caption").setValue(caption)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditCaptionActivity.this, "Caption updated successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditCaptionActivity.this, "Failed to update caption", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}



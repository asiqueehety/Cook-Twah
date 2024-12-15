package com.example.cooktwah;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MakeRcp extends AppCompatActivity {

    EditText title;
    EditText ingredients;
    EditText details;
    Button submitRcp;
    private DatabaseReference databaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_make_rcp);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Get the current user's UID
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Set up the database reference under the user's UID
        databaseRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("recipes");


        title = findViewById(R.id.editText3);
        ingredients = findViewById(R.id.editText);
        details = findViewById(R.id.editText2);
        submitRcp = findViewById(R.id.submitRcp);

        submitRcp.setOnClickListener(v -> saveRecipe());
    }

    private void saveRecipe() {
        String titleText = title.getText().toString();
        String ingredientsText = ingredients.getText().toString();
        String detailsText = details.getText().toString();

        if (titleText.isEmpty() || ingredientsText.isEmpty() || detailsText.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        // Create a Recipe object
        MyRecipe recipe = new MyRecipe(titleText, ingredientsText, detailsText);

        // Generate a unique key for the recipe
        String recipeId = databaseRef.push().getKey();
        if (recipeId != null) {
            databaseRef.child(recipeId).setValue(recipe).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Recipe saved successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to save recipe", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
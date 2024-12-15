package com.example.cooktwah;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class YourRcp extends AppCompatActivity {

    private RecyclerView recyclerView;
    private YourRecipeAdapter adapter;
    private List<MyRecipe> recipeList;
    private FirebaseDatabase database;
    private DatabaseReference recipesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_rcp);

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        recipesRef = database.getReference("users").child(userId).child("recipes");

        // Initialize RecyclerView and set up adapter
        recyclerView = findViewById(R.id.yourRcpRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeList = new ArrayList<>();
        adapter = new YourRecipeAdapter(this, recipeList);
        recyclerView.setAdapter(adapter);

        // Fetch recipes from Firebase
        fetchRecipesFromDatabase();
    }

    private void fetchRecipesFromDatabase() {
        recipesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recipeList.clear();  // Clear the list to avoid duplicates
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MyRecipe recipe = snapshot.getValue(MyRecipe.class);
                    if (recipe != null) {
                        Log.d("YourRcp", "Loaded recipe: " + recipe.getTitle());
                        recipeList.add(recipe);  // Add recipe to list
                    }
                }
                adapter.notifyDataSetChanged();  // Notify adapter of data changes
                Toast.makeText(YourRcp.this, "Recipes loaded successfully: "+recipeList.size(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(YourRcp.this, "Failed to load recipes", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

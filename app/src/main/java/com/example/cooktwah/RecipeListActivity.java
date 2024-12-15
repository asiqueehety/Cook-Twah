package com.example.cooktwah;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cooktwah.databinding.ActivityRecipeListBinding;

import java.util.List;

public class RecipeListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize ViewBinding
        ActivityRecipeListBinding binding = ActivityRecipeListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the recipes passed from the previous activity
        recipes = (List<Recipe>) getIntent().getSerializableExtra("recipes");

        // Initialize RecyclerView
        recyclerView = binding.recyclerViewRecipes;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (recipes != null) {
            recipeAdapter = new RecipeAdapter(recipes, this::onRecipeClicked);
            recyclerView.setAdapter(recipeAdapter);
        } else {
            Toast.makeText(this, "No recipes available", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle clicking on a recipe
    private void onRecipeClicked(Recipe recipe) {

        // Pass the full recipe to the next activity
        Intent intent = new Intent(RecipeListActivity.this, RecipeDetailActivity.class);
        intent.putExtra("recipe", recipe);
        startActivity(intent);
    }
}



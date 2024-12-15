package com.example.cooktwah;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide; // Ensure Glide is added to your dependencies

import java.util.List;

import retrofit2.Call;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView recipeTitle, recipeDetails;
    private ImageView recipeImage;
    private CheckBox fav;
    private TextView recipeIng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Initialize views
        recipeTitle = findViewById(R.id.recipe_title);
        recipeDetails = findViewById(R.id.recipe_details);
        recipeImage = findViewById(R.id.recipeImg);
        fav = findViewById(R.id.fav);
        recipeIng = findViewById(R.id.recipe_ing);

        // Get the selected recipe from the intent
        Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        if (recipe != null) {
            Recipe firstRecipe = recipe;
            fetchRecipeSteps(firstRecipe);
            recipeTitle.setText(recipe.getTitle());

            // Load image using Glide
            Glide.with(this).load(recipe.getImage()).into(recipeImage);
        }

    }
    // Method to fetch recipe steps
    private void fetchRecipeSteps(Recipe recipe) {
        RecipeDetailAPI detailAPI = RetrofitClient.getInstance().create(RecipeDetailAPI.class);
        detailAPI.getRecipeDetails(recipe.getId(), "75f8612744cb45718bf3f99a154ed709").enqueue(new retrofit2.Callback<List<RecipeDetail>>() {
            @Override
            public void onResponse(Call<List<RecipeDetail>> call, retrofit2.Response<List<RecipeDetail>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    List<RecipeDetail.Step> steps = response.body().get(0).getSteps();

                    // Display steps or pass them to another activity
                    displayRecipeSteps(steps);
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to fetch recipe details", Toast.LENGTH_SHORT).show();}
            }

            @Override
            public void onFailure(Call<List<RecipeDetail>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void displayRecipeSteps(List<RecipeDetail.Step> steps) {
        StringBuilder stepsDisplay = new StringBuilder("Recipe Steps:\n");
        for (RecipeDetail.Step step : steps) {
            stepsDisplay.append(step.getStep()).append("\n");
            recipeDetails.setText(stepsDisplay.toString());// Log each step");
        }
    }

}

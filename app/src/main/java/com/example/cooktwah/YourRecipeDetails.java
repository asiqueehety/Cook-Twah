package com.example.cooktwah;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class YourRecipeDetails extends AppCompatActivity {

    private TextView titleTextView, ingredientsTextView, detailsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_recipe_details);

        titleTextView = findViewById(R.id.titleTextView);
        ingredientsTextView = findViewById(R.id.ingredientsTextView);
        detailsTextView = findViewById(R.id.detailsTextView);

        // Get the recipe data from Intent
        String title = getIntent().getStringExtra("title");
        String ingredients = getIntent().getStringExtra("ingredients");
        String details = getIntent().getStringExtra("details");

        // Set the data to the TextViews
        titleTextView.setText(title);
        ingredientsTextView.setText(ingredients);
        detailsTextView.setText(details);
    }
}

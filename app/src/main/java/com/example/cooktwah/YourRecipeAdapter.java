package com.example.cooktwah;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class YourRecipeAdapter extends RecyclerView.Adapter<YourRecipeAdapter.RecipeViewHolder> {
    private List<MyRecipe> recipeList;
    private Context context;

    public YourRecipeAdapter(Context context, List<MyRecipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        MyRecipe recipe = recipeList.get(position);
        holder.recipeTitle.setText(recipe.getTitle());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, YourRecipeDetails.class);
            intent.putExtra("title", recipe.getTitle());
            intent.putExtra("ingredients", recipe.getIngredients());
            intent.putExtra("details", recipe.getDetails());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView recipeTitle;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeTitle = itemView.findViewById(R.id.recipe_title);

        }
    }
}

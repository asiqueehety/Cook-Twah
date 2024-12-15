package com.example.cooktwah;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private final ArrayList<String> ingredients;
    private final RemoveIngredientListener removeListener;

    public IngredientAdapter(ArrayList<String> ingredients, RemoveIngredientListener removeListener) {
        this.ingredients = ingredients;
        this.removeListener = removeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String ingredient = ingredients.get(position);
        holder.ingredientName.setText(ingredient);
        holder.removeButton.setOnClickListener(v -> removeListener.onRemove(ingredient));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientName;
        Button removeButton;

        ViewHolder(View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.ingredient_name);
            removeButton = itemView.findViewById(R.id.remove_button);
        }
    }

    interface RemoveIngredientListener {
        void onRemove(String ingredient);
    }
}

package com.example.cooktwah;

public class MyRecipe {
    private String title;
    private String ingredients;
    private String details;

    public MyRecipe() {
        // Default constructor required for Firebase DataSnapshot
    }

    public MyRecipe(String title, String ingredients, String details) {
        this.title = title;
        this.ingredients = ingredients;
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getDetails() {
        return details;
    }
}

package com.example.cooktwah;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

// RetrofitClient class for Spoonacular API
class RetrofitClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.spoonacular.com/";

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

// Spoonacular RecipeAPI interface
interface RecipeAPI {
    @GET("recipes/findByIngredients")
    Call<List<Recipe>> searchRecipes(
            @Query("ingredients") String ingredients,
            @Query("number") int number,  // Limit the number of results
            @Query("apiKey") String apiKey// Add your API key here
    );

}

interface RecipeDetailAPI {
    @GET("recipes/{id}/analyzedInstructions")
    Call<List<RecipeDetail>> getRecipeDetails(
            @Path("id") int recipeId,
            @Query("apiKey") String apiKey
    );
}

class RecipeDetail {
    @SerializedName("steps")
    private List<Step> steps; // List of individual steps

    public List<Step> getSteps() {
        return steps;
    }

    // Step class to represent individual steps in the recipe
    class Step {
        @SerializedName("step")
        private String step;

        public String getStep() {
            return step;
        }
    }
}


// Recipe model class
class Recipe implements Serializable {
    private int id;
    private String title;
    private String image;
    private String instructions; // To store the instructions
    private List<RecipeDetail.Step> steps;

    public List<RecipeDetail.Step> getSteps() {
        return steps;
    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInstructions()
    {
        Log.d("Recipe", "Instructions: " + instructions);  // Log to verify if instructions are set properly
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}


// CookFragment class
public class CookFragment extends Fragment {

    String[] item = {"Select available ingredients","Almond","Anchovies","Apple","Artichokes","Avocado","Bacon","Banana","Barley","Basil","Beef","Beet","Black Beans","Blue Cheese","Blueberry","Bread","Brie","Butter","Cabbage","Canola Oil","Cashew","Cauliflower","Cayenne","Celery","Cheddar","Cheese","Chicken","Chickpeas","Chocolate","Cinnamon","Coconut","Corn","Cornmeal","Couscous","Cream","Cream Cheese","Cranberries","Cucumber","Dates","Duck","Egg","Eggplant","Fennel","Figs","Flaxseeds","Fish","Feta","Garlic","Garam Masala","Ginger","Gouda","Grape","Green Beans","Guava","Hazelnut","Honey","Horseradish","Jam","Jelly","Kale","Ketchup","Kiwi","Lettuce","Lemon","Lentils","Lime","Lobster","Lychee","Mango","Maple Syrup","Marmalade","Mirin","Miso","Milk","Mushroom","Mustard","Noodles","Nutmeg","Okra","Olive","Olive Oil","Oats","Oregano","Paprika","Peach","Peanut","Pecan","Pepper","Pepperoni","Pickles","Pineapple","Plum","Polenta","Pork","Potato","Prunes","Pumpkin","Pomegranate","Prosciutto","Quinoa","Raisins","Radish","Rosemary","Salami","Salmon","Sausage","Scallop","Shrimp","Soy Milk","Soy Sauce","Spinach","Squash","Star Fruit","Sriracha","Strawberry","Sunflower Seeds","Sun-Dried Tomatoes","Tabasco","Tahini","Tamarind","Thyme","Tomato","Tofu","Turnip Greens","Turkey","Vanilla","Wasabi","Watermelon","White Beans","Whipping Cream","Yogurt","Zucchini","Artichokes","Chili","Cilantro","Cabbage","Cauliflower","Cinnamon","Chocolate","Chickpeas","Curry Powder","Coconut","Corn","Curry Leaves","Cabbage","Cloves","Clams","Couscous","Cinnamon","Chili","Cabbage","Cardamom","Carrot","Chia Seeds","Coconut Milk","Cheddar","Cream Cheese","Chickpeas","Cheese","Chocolate","Cashew","Cinnamon","Curry Powder","Chili","Cabbage","Canola Oil","Cloves","Chocolate","Chili","Chicken","Cornmeal"};

    Spinner ing;
    RecyclerView recyclerView;
    IngredientAdapter ingredientAdapter;
    ArrayList<String> selectedIngredients;
    Button searchRec;

    public CookFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cook, container, false);

        // Initialize Spinner
        ing = view.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, item);
        ing.setAdapter(adapter);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        selectedIngredients = new ArrayList<>();
        ingredientAdapter = new IngredientAdapter(selectedIngredients, this::removeIngredient);
        recyclerView.setAdapter(ingredientAdapter);

        // Initialize Button
        searchRec = view.findViewById(R.id.button2);
        searchRec.setVisibility(View.INVISIBLE);

        // Spinner selection listener
        ing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (!selectedItem.equals("Select available ingredients") && !selectedIngredients.contains(selectedItem)) {
                    selectedIngredients.add(selectedItem);
                    ingredientAdapter.notifyItemInserted(selectedIngredients.size() - 1);
                    if (!selectedIngredients.isEmpty()) {
                        searchRec.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(requireContext(), "No ingredient selected", Toast.LENGTH_SHORT).show();
            }
        });

        // Button click listener
        searchRec.setOnClickListener(view1 -> {
            String ingredients = String.join(",", selectedIngredients);

            // Retrofit instance
            RecipeAPI recipeAPI = RetrofitClient.getInstance().create(RecipeAPI.class);

            // API call
            recipeAPI.searchRecipes(ingredients, 10, "75f8612744cb45718bf3f99a154ed709").enqueue(new retrofit2.Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, retrofit2.Response<List<Recipe>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Recipe> recipes = response.body();


                        // Pass the recipes to the RecipeListActivity or update UI
                        Intent intent = new Intent(requireContext(), RecipeListActivity.class);
                        intent.putExtra("recipes", (ArrayList<Recipe>) recipes);
                        startActivity(intent);
                    } else {
                        Toast.makeText(requireContext(), "Failed to fetch recipes", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        return view;
    }

//    // Method to fetch recipe steps
//    private void fetchRecipeSteps(Recipe recipe) {
//        RecipeDetailAPI detailAPI = RetrofitClient.getInstance().create(RecipeDetailAPI.class);
//        detailAPI.getRecipeDetails(recipe.getId(), "75f8612744cb45718bf3f99a154ed709").enqueue(new retrofit2.Callback<List<RecipeDetail>>() {
//            @Override
//            public void onResponse(Call<List<RecipeDetail>> call, retrofit2.Response<List<RecipeDetail>> response) {
//                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
//                    List<RecipeDetail.Step> steps = response.body().get(0).getSteps();
//
//                    // Display steps or pass them to another activity
//                    displayRecipeSteps(steps);
//                } else {
//                    Toast.makeText(requireContext(), "Failed to fetch recipe steps", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<RecipeDetail>> call, Throwable t) {
//                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }



    // Remove ingredient from list
    private void removeIngredient(String ingredient) {
        int index = selectedIngredients.indexOf(ingredient);
        if (index != -1) {
            selectedIngredients.remove(index);
            ingredientAdapter.notifyItemRemoved(index);
            if (selectedIngredients.isEmpty()) {
                searchRec.setVisibility(View.INVISIBLE);
            }
        }
    }
//    private void displayRecipeSteps(List<RecipeDetail.Step> steps) {
//        StringBuilder stepsDisplay = new StringBuilder("Recipe Steps:\n");
//        for (RecipeDetail.Step step : steps) {
//            stepsDisplay.append(step.getStep()).append("\n");
//            Log.d("RecipeDetail", "Step: " + step.getStep()); // Log each step");
//        }
//    }
}

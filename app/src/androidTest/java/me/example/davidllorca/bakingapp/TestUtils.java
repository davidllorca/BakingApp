package me.example.davidllorca.bakingapp;

import java.util.Arrays;

import me.example.davidllorca.bakingapp.data.Ingredient;
import me.example.davidllorca.bakingapp.data.Recipe;
import me.example.davidllorca.bakingapp.data.Step;

/**
 * Created by David Llorca <davidllorcabaron@gmail.com> on 6/05/18.
 */

public class TestUtils {

    public static Recipe generateRecipe(){
        Recipe recipe = new Recipe();
        recipe.setId(1);
        recipe.setName("Recipe name");
        recipe.setServings(3);
        Ingredient ingredient = new Ingredient();
        recipe.setIngredients(Arrays.asList(ingredient, ingredient, ingredient));
        Step step = new Step();
        recipe.setSteps(Arrays.asList(step, step, step, step));
        return recipe;
    }
}

package me.example.davidllorca.bakingapp.data;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import me.example.davidllorca.bakingapp.R;

/**
 * Helper class for providing data.
 *
 */
public class DataSource {

    public static List<Recipe> generateFakeData(Context context) {
        String json = getDataFromResources(context);
        try {
            JSONArray array = new JSONArray(json);
            List<Recipe> recipes = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                Recipe recipe = parseRecipe(array.optString(i));
                if (recipe != null) {
                    recipes.add(recipe);
                }

            }
            return recipes;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Recipe parseRecipe(String json) {
        try {
            JSONObject object = new JSONObject(json);
            Recipe recipe = new Recipe();
            if (object.has(Recipe.Columns.ID)) {
                recipe.setId(object.optInt(Recipe.Columns.ID));
            }
            if (object.has(Recipe.Columns.NAME)) {
                recipe.setName(object.optString(Recipe.Columns.NAME));
            }
            if (object.has(Recipe.Columns.INGREDIENTS)) {
                List<Ingredient> ingredients = new ArrayList<>();
                JSONArray array = object.getJSONArray(Recipe.Columns.INGREDIENTS);
                for (int i = 0; i < array.length(); i++) {
                    Ingredient ingredient = parseIngredient(array.optString(i));
                    if (ingredient != null) ingredients.add(ingredient);
                }
                recipe.setIngredients(ingredients);
            }
            if (object.has(Recipe.Columns.STEPS)) {
                List<Step> steps = new ArrayList<>();
                JSONArray array = object.getJSONArray(Recipe.Columns.STEPS);
                for (int i = 0; i < array.length(); i++) {
                    Step step = parseStep(array.optString(i));
                    if (step != null) steps.add(step);
                }
                recipe.setSteps(steps);
            }
            if (object.has(Recipe.Columns.SERVINGS)) {
                recipe.setServings(object.optInt(Recipe.Columns.SERVINGS));
            }
            if (object.has(Recipe.Columns.IMAGE)) {
                recipe.setImage(object.optString(Recipe.Columns.IMAGE));
            }
            return recipe;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Step parseStep(String json) {
        try {
            JSONObject object = new JSONObject(json);
            Step step = new Step();
            if (object.has(Step.Columns.ID)) {
                step.setId(object.optInt(Step.Columns.ID));
            }
            if (object.has(Step.Columns.SHORT_DESCRIPTION)) {
                step.setShortDescription(object.optString(Step.Columns.SHORT_DESCRIPTION));
            }
            if (object.has(Step.Columns.DESCRIPTION)) {
                step.setDescription(object.optString(Step.Columns.DESCRIPTION));
            }
            if (object.has(Step.Columns.VIDEO_URL)) {
                step.setVideoURL(object.optString(Step.Columns.VIDEO_URL));
            }
            if (object.has(Step.Columns.THUMBNAIL_URL)) {
                step.setThumbnail(object.optString(Step.Columns.THUMBNAIL_URL));
            }
            return step;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Ingredient parseIngredient(String json) {
        try {
            JSONObject object = new JSONObject(json);
            Ingredient ingredient = new Ingredient();
            if (object.has(Ingredient.Columns.QUANTITY)) {
                ingredient.setQuantity(object.optInt(Ingredient.Columns.QUANTITY));
            }
            if (object.has(Ingredient.Columns.INGREDIENT)) {
                ingredient.setIngredient(object.optString(Ingredient.Columns.INGREDIENT));
            }
            if (object.has(Ingredient.Columns.MEASURE)) {
                ingredient.setMeasure(object.optString(Ingredient.Columns.MEASURE));
            }
            return ingredient;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getDataFromResources(Context context) {
        InputStream is = context.getResources().openRawResource(R.raw.data);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return writer.toString();
    }

}

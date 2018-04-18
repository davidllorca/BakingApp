package me.example.davidllorca.bakingapp.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing data.
 *
 */
public class DataSource {

    /**
     * An array of sample recipes.
     */
    public static final List<Recipe> ITEMS = new ArrayList<Recipe>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Recipe> ITEM_MAP = new HashMap<String, Recipe>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(Recipe item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.image, item);
    }

    private static Recipe createDummyItem(int position) {
        return new Recipe(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * Model representing a Recipe data.
     */
    public static class Recipe {
        public final int id;
        public final String name;
        public final List<Ingredient> ingredients;
        public final List<Step> steps;
        public final int servings;
        public final String image;


        public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps, int servings, String image) {
            this.id = id;
            this.name = name;
            this.ingredients = ingredients;
            this.steps = steps;
            this.servings = servings;
            this.image = image;
        }
    }

    public class Step {
        public final int id;
        public final String shortDescription;
        public final String description;
        public final String videoURL;
        public final String thumbnail;


        public Step(int id, String shortDescription, String description, String videoURL, String thumbnail) {
            this.id = id;
            this.shortDescription = shortDescription;
            this.description = description;
            this.videoURL = videoURL;
            this.thumbnail = thumbnail;
        }
    }

    public class Ingredient {
        public final int quantity;
        public final String measure;
        public final String ingredient;

        public Ingredient(int quantity, String measure, String ingredient) {
            this.quantity = quantity;
            this.measure = measure;
            this.ingredient = ingredient;
        }
    }
}

package me.example.davidllorca.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Model representing a Recipe data.
 */
public class Recipe implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
    public int id;
    public String name;
    public List<Ingredient> ingredients;
    public List<Step> steps;
    public int servings;
    public String image;

    Recipe() {
    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        if (in.readByte() == 0x01) {
            ingredients = new ArrayList<Ingredient>();
            in.readList(ingredients, Ingredient.class.getClassLoader());
        } else {
            ingredients = null;
        }
        if (in.readByte() == 0x01) {
            steps = new ArrayList<Step>();
            in.readList(steps, Step.class.getClassLoader());
        } else {
            steps = null;
        }
        servings = in.readInt();
        image = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        if (ingredients == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ingredients);
        }
        if (steps == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(steps);
        }
        dest.writeInt(servings);
        dest.writeString(image);
    }

    class Columns {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String INGREDIENTS = "ingredients";
        public static final String STEPS = "steps";
        public static final String SERVINGS = "servings";
        public static final String IMAGE = "image";
    }
}

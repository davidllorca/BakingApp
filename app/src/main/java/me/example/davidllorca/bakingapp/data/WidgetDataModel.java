package me.example.davidllorca.bakingapp.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by David Llorca <davidllorcabaron@gmail.com> on 14/06/18.
 */

public class WidgetDataModel {

    public String ingredientName = "";

    public static ArrayList<WidgetDataModel> getDataFromSharedPrefs(Context context) {
        ArrayList<WidgetDataModel> list = new ArrayList<>();
        if (list.isEmpty()) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
            String recipeName = sharedPref.getString("recipe_name", "Recipe");
            Set<String> ingredients = sharedPref.getStringSet("ingredients", new HashSet<String>());

            Iterator<String> iterator = ingredients.iterator();
            while (iterator.hasNext()) {
                WidgetDataModel model = new WidgetDataModel();
                model.ingredientName = iterator.next();
                list.add(model);
            }
        }
        return list;
    }

}
package me.example.davidllorca.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.List;

import me.example.davidllorca.bakingapp.data.Ingredient;
import me.example.davidllorca.bakingapp.data.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId, Recipe recipe) {

        // Create an Intent to launch MainActivity when clicked
        Intent intent = new Intent(context, RecipeListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        if (recipe != null) {
            List<Ingredient> ingredients = recipe.getIngredients();
            String out = "";
            for (int i = 0; i < ingredients.size(); i++) {
                out += "- ";
                out += ingredients.get(i).getIngredient();
                out += "\n";
            }

            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout
                    .recipe_ingredients_widget);

            views.setTextViewText(R.id.tv_widget_recipe_name, recipe.getName());
            views.setTextViewText(R.id.tv_widget_ingredients, out);
            views.setOnClickPendingIntent(R.id.tv_widget_ingredients, pendingIntent);

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }


    }

    public static void updateRecipeWidget(Context context, AppWidgetManager appWidgetManager,
                                          int[] appWidgetIds, Recipe recipe) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipe);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

}


package me.example.davidllorca.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import me.example.davidllorca.bakingapp.data.AsyncTaskListener;
import me.example.davidllorca.bakingapp.data.GetRecipesAsyncTask;
import me.example.davidllorca.bakingapp.data.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider implements
        AsyncTaskListener<List<Recipe>> {

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {

        // Create an Intent to launch MainActivity when clicked
        Intent intent = new Intent(context, RecipeListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        new GetRecipesAsyncTask(this).execute();

//        List<Recipe> recipes = ParseUtils.generateFakeData(context);
//        Recipe recipe = recipes.get(0);
//        List<Ingredient> ingredients = recipe.getIngredients();
//        String out = "";
//        for (int i = 0; i < ingredients.size(); i++) {
//            out += "- ";
//            out += ingredients.get(i).getIngredient();
//            out += "\n";
//        }
//
//        // Construct the RemoteViews object
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout
//                .recipe_ingredients_widget);
//
//        views.setTextViewText(R.id.tv_widget_recipe_name, recipe.getName());
//        views.setTextViewText(R.id.tv_widget_ingredients, out);
//        views.setOnClickPendingIntent(R.id.tv_widget_ingredients, pendingIntent);
//
//        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
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

    @Override
    public void onTaskCompleted(List<Recipe> result) {

    }
}


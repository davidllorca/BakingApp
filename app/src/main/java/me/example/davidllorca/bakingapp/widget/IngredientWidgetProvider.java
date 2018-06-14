package me.example.davidllorca.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.RemoteViews;

import me.example.davidllorca.bakingapp.R;
import me.example.davidllorca.bakingapp.RecipeListActivity;

import static me.example.davidllorca.bakingapp.RecipeDetailActivity.PREF_KEY_RECIPE_NAME;

/**
 * Created by David Llorca <davidllorcabaron@gmail.com> on 14/06/18.
 */

public class IngredientWidgetProvider extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        RemoteViews remoteView;
        if (width < 300) {
            remoteView = getViewForSmallerWidget(context);
        } else {
            remoteView = getViewForBiggerWidget(context, options);
        }
        appWidgetManager.updateAppWidget(appWidgetId, remoteView);

    }

    private static RemoteViews getViewForBiggerWidget(Context context, Bundle options) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_widget_list);
        int minHeight = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        if (minHeight < 100) {
            views.setViewVisibility(R.id.tv_recipe_name, View.GONE);
        } else {
            views.setViewVisibility(R.id.tv_recipe_name, View.VISIBLE);
            String recipeName = PreferenceManager.getDefaultSharedPreferences(context).getString
                    (PREF_KEY_RECIPE_NAME, "Recipe name");
            views.setTextViewText(R.id.tv_recipe_name, recipeName);

            Intent intent = new Intent(context, RecipeListActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            views.setOnClickPendingIntent(R.id.tv_ingredient_name, pendingIntent);
        }

        Intent intent = new Intent(context, ListViewWidgetService.class);
        views.setRemoteAdapter(R.id.listView, intent);

        Intent appIntent = new Intent(context, RecipeListActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.listView, appPendingIntent);

        return views;
    }

    private static RemoteViews getViewForSmallerWidget(Context context) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout
                .layout_widget_simple);

        String recipeName = PreferenceManager.getDefaultSharedPreferences(context).getString
                (PREF_KEY_RECIPE_NAME, "Recipe name");
        views.setTextViewText(R.id.tv_last_recipe_name, recipeName);

        Intent intent2 = new Intent(context, RecipeListActivity.class);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 0, intent2, 0);
        views.setOnClickPendingIntent(R.id.tv_last_recipe_name, pendingIntent2);

        return views;
    }

    public static void updateAllAppWidget(Context context, AppWidgetManager appWidgetManager,
                                          int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        WidgetUpdateService.startActionUpdateAppWidgets(context, false);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId, Bundle newOptions) {

        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        if (width < 300) {
            WidgetUpdateService.startActionUpdateAppWidgets(context, false);
        } else {
            WidgetUpdateService.startActionUpdateAppWidgets(context, true);
        }
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }


}

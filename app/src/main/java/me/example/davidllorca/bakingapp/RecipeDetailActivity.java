package me.example.davidllorca.bakingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.HashSet;
import java.util.List;

import me.example.davidllorca.bakingapp.adapter.RecipeInfoRecyclerViewAdapter;
import me.example.davidllorca.bakingapp.data.Ingredient;
import me.example.davidllorca.bakingapp.data.Recipe;
import me.example.davidllorca.bakingapp.data.Step;
import me.example.davidllorca.bakingapp.widget.WidgetUpdateService;

/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeDetailActivity extends AppCompatActivity implements RecipeInfoRecyclerViewAdapter.RecipeStepListener {

    public static final String RECIPE_KEY = "recipe";

    public static final String PREF_KEY_RECIPE_NAME = "recipe_name";
    public static final String PREF_KEY_INGREDIENTS = "ingredients";

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Toolbar toolbar = findViewById(R.id.toolbar_recipe_detail);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(!getResources().getBoolean(R.bool
                .is_tablet));

        mRecipe = getIntent().getParcelableExtra(RECIPE_KEY);
        if(mRecipe == null){
            finish();
        }

        getSupportActionBar().setTitle(mRecipe.getName());

        if (findViewById(R.id.step_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        View ingredientRecyclerView = findViewById(R.id.rv_ingredients);
        View stepRecyclerView = findViewById(R.id.rv_steps);
        assert ingredientRecyclerView != null;
        assert stepRecyclerView != null;
        setupRecyclerView((RecyclerView) ingredientRecyclerView, mRecipe.getIngredients());
        setupRecyclerView((RecyclerView) stepRecyclerView, mRecipe.getSteps());

        saveLastRecipeVisualized();
    }

    private void saveLastRecipeVisualized() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putString(PREF_KEY_RECIPE_NAME, mRecipe.getName()).apply();
        HashSet ingredients = new HashSet();
        for (Ingredient i : mRecipe.getIngredients()) {
            ingredients.add(i.getIngredient());
        }

        preferences.edit().putStringSet(PREF_KEY_INGREDIENTS, ingredients).apply();
        WidgetUpdateService.startActionUpdateAppWidgets(this, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, List items) {
        recyclerView.setAdapter(new RecipeInfoRecyclerViewAdapter(this, items, this
        ));
    }

    @Override
    public void onClickStep(Step step) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(StepDetailFragment.STEP_KEY, step);
            arguments.putParcelable(RECIPE_KEY, mRecipe);
            StepDetailFragment fragment = new StepDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra(StepDetailFragment.STEP_KEY, step);
            intent.putExtra(RECIPE_KEY, mRecipe);
            startActivity(intent);
        }
    }

}

package me.example.davidllorca.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import me.example.davidllorca.bakingapp.adapter.RecipeRecyclerViewAdapter;
import me.example.davidllorca.bakingapp.data.AsyncTaskListener;
import me.example.davidllorca.bakingapp.data.GetRecipesAsyncTask;
import me.example.davidllorca.bakingapp.data.Recipe;

import static me.example.davidllorca.bakingapp.RecipeDetailActivity.RECIPE_KEY;

public class RecipeListActivity extends AppCompatActivity
        implements RecipeRecyclerViewAdapter.RecipeListener, AsyncTaskListener<List<Recipe>> {

    private RecipeRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetRecipesAsyncTask(this).execute();
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rv_recipes);
        boolean isTablet = getResources().getBoolean(R.bool.is_tablet);
        if (isTablet) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        mAdapter = new RecipeRecyclerViewAdapter(this, this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClickRecipe(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RECIPE_KEY, recipe);
        startActivity(intent);
    }

    @Override
    public void onTaskCompleted(List<Recipe> result) {
        mAdapter.initDataSet(result);
    }
}


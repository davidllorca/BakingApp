package me.example.davidllorca.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import me.example.davidllorca.bakingapp.adapter.RecipeRecyclerViewAdapter;
import me.example.davidllorca.bakingapp.data.DataSource;
import me.example.davidllorca.bakingapp.data.Recipe;

import static me.example.davidllorca.bakingapp.RecipeDetailActivity.RECIPE_KEY;

public class RecipeListActivity extends AppCompatActivity implements RecipeRecyclerViewAdapter.RecipeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rv_recipes);
        boolean isTablet = getResources().getBoolean(R.bool.is_tablet);
        if (isTablet) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        recyclerView.setAdapter(new RecipeRecyclerViewAdapter(this, DataSource.generateFakeData(this), this));
    }

    @Override
    public void onClickRecipe(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RECIPE_KEY, recipe);
        startActivity(intent);
    }
}


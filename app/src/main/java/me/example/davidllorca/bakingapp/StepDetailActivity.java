package me.example.davidllorca.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.List;

import me.example.davidllorca.bakingapp.data.Recipe;
import me.example.davidllorca.bakingapp.data.Step;

public class StepDetailActivity extends AppCompatActivity implements PlayControl {

    private Step targetStep;
    private Recipe targetRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        Toolbar toolbar = findViewById(R.id.toolbar_step_detail);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            targetRecipe = getIntent().getParcelableExtra(RecipeDetailActivity.RECIPE_KEY);
            targetStep = getIntent().getParcelableExtra(StepDetailFragment.STEP_KEY);

            StepDetailFragment fragment = StepDetailFragment.newInstance(targetStep, hasPrevStep(targetStep, targetRecipe), hasNextStep(targetStep, targetRecipe));
            attachFragment(fragment);
        }
    }

    private boolean hasNextStep(Step targetStep, Recipe targetRecipe) {
        List<Step> steps = targetRecipe.getSteps();
        int nextPos = steps.indexOf(targetStep) + 1;
        return nextPos < steps.size();
    }

    private boolean hasPrevStep(Step targetStep, Recipe targetRecipe) {
        List<Step> steps = targetRecipe.getSteps();
        int prevPos = steps.indexOf(targetStep) - 1;
        return prevPos >= 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent backIntent = new Intent(this, RecipeDetailActivity.class);
            backIntent.putExtra(RecipeDetailActivity.RECIPE_KEY, targetRecipe);
            backIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(backIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPreviousClick(Step currentStep) {
        boolean hasPreviousStep = hasPrevStep(currentStep, targetRecipe);
        if (hasPreviousStep) {
            int prevPos = targetRecipe.getSteps().indexOf(currentStep) - 1;
            Step previousStep = targetRecipe.getSteps().get(prevPos);
            boolean hasNextStep = hasNextStep(previousStep, targetRecipe);
            hasPreviousStep = hasPrevStep(previousStep, targetRecipe);
            StepDetailFragment fragment = StepDetailFragment.newInstance(previousStep, hasPreviousStep,
                    hasNextStep);
            attachFragment(fragment);
        }
    }

    @Override
    public void onNextClick(Step currentStep) {
        boolean hasNextStep = hasNextStep(currentStep, targetRecipe);
        if (hasNextStep) {
            int nextPos = targetRecipe.getSteps().indexOf(currentStep) + 1;
            Step nextStep = targetRecipe.getSteps().get(nextPos);
            boolean hasPreviousStep = hasPrevStep(nextStep, targetRecipe);
            hasNextStep = hasNextStep(nextStep, targetRecipe);
            StepDetailFragment fragment = StepDetailFragment.newInstance(nextStep,
                    hasPreviousStep, hasNextStep);
            attachFragment(fragment);
        }
    }

    private void attachFragment(StepDetailFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.step_detail_container, fragment)
                .commit();
    }
}

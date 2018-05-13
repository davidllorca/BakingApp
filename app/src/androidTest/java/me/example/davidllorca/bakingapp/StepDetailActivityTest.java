package me.example.davidllorca.bakingapp;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import me.example.davidllorca.bakingapp.data.Recipe;
import me.example.davidllorca.bakingapp.data.Step;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by David Llorca <davidllorcabaron@gmail.com> on 12/05/18.
 */
@RunWith(AndroidJUnit4.class)
public class StepDetailActivityTest {

    @Rule
    public IntentsTestRule<StepDetailActivity> mActivityTestRule = new IntentsTestRule
            <StepDetailActivity>(StepDetailActivity.class, false, false);

    private Recipe recipe;
    private Step step;

    @Test
    public void whenLaunchScreenThenStepInfoIsDisplayed() {
        launchActivity();

        onView(withId(R.id.tv_step_detail_description)).check(matches(withText(step.getDescription())));

        boolean isTablet = mActivityTestRule.getActivity().getResources().getBoolean(R.bool.is_tablet);

        if(!isTablet){
            onView(withId(R.id.layout_step_detail_controls)).check(matches(isDisplayed()));
        }
    }

    private void launchActivity() {
        Intent intent = new Intent();
        recipe = TestUtils.generateRecipe();
        step = recipe.getSteps().get(0);
        intent.putExtra(RecipeDetailActivity.RECIPE_KEY, recipe);
        intent.putExtra(StepDetailFragment.STEP_KEY, step);
        mActivityTestRule.launchActivity(intent);
    }
}

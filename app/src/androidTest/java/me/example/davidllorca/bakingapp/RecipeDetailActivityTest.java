package me.example.davidllorca.bakingapp;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import me.example.davidllorca.bakingapp.data.DataSource;
import me.example.davidllorca.bakingapp.data.Recipe;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by David Llorca <davidllorcabaron@gmail.com> on 6/05/18.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {

    private static final String RECIPE_NAME = "Recipe name";
    @Rule
    public IntentsTestRule<RecipeDetailActivity> mActivityTestRule
            = new IntentsTestRule(RecipeDetailActivity.class, false, false);

    @Test
    public void launchState() {
        Intent intent = new Intent();
        Recipe recipe = TestUtils.generateRecipe();
        intent.putExtra(RecipeDetailActivity.RECIPE_KEY, recipe);
        mActivityTestRule.launchActivity(intent);

        onView(withId(R.id.toolbar_recipe_detail)).check(matches(withText(recipe.getName())));
    }
}

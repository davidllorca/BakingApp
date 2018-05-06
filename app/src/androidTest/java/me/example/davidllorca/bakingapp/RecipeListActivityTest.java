package me.example.davidllorca.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by David Llorca <davidllorcabaron@gmail.com> on 6/05/18.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest {

    @Rule
    public IntentsTestRule<RecipeListActivity> mActivityTestRule
            = new IntentsTestRule<>(RecipeListActivity.class);

    @Test
    public void whenClickItemThenOpenDetailActivity() {
        onView(withId(R.id.rv_recipes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(allOf(
                hasExtraWithKey(RecipeDetailActivity.RECIPE_KEY)));
    }
}

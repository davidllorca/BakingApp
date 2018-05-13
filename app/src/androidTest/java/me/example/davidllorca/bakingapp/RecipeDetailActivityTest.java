package me.example.davidllorca.bakingapp;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import me.example.davidllorca.bakingapp.data.Recipe;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by David Llorca <davidllorcabaron@gmail.com> on 6/05/18.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {

    @Rule
    public IntentsTestRule<RecipeDetailActivity> mActivityTestRule
            = new IntentsTestRule(RecipeDetailActivity.class, false, false);

    private Recipe recipe;

    @Test
    public void whenLaunchScreenThenRecipeInfoIsDisplayed() {
        launchActivity();

        onView(withId(R.id.toolbar_recipe_detail)).check(matches(withToolbarTitle(is(recipe.getName()))));

        onView(withId(R.id.rv_ingredients)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_steps)).check(matches(isDisplayed()));
    }

    @Test
    public void whenClickStepThenIntentToStepDetailIsLaunched(){
        launchActivity();

        onView(withId(R.id.rv_steps)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(allOf(
                hasExtraWithKey(RecipeDetailActivity.RECIPE_KEY),
                hasExtraWithKey(StepDetailFragment.STEP_KEY)));
    }

    private void launchActivity() {
        Intent intent = new Intent();
        recipe = TestUtils.generateRecipe();
        intent.putExtra(RecipeDetailActivity.RECIPE_KEY, recipe);
        mActivityTestRule.launchActivity(intent);
    }


    private static Matcher<Object> withToolbarTitle(final Matcher<String> textMatcher) {
        return new BoundedMatcher<Object, Toolbar>(Toolbar.class) {
            @Override public boolean matchesSafely(Toolbar toolbar) {
                return textMatcher.matches(toolbar.getTitle());
            }
            @Override public void describeTo(Description description) {
                description.appendText("with toolbar title: ");
                textMatcher.describeTo(description);
            }
        };
    }


}

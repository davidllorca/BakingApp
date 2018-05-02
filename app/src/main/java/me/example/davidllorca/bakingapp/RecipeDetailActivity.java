package me.example.davidllorca.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import me.example.davidllorca.bakingapp.adapter.RecipeInfoRecyclerViewAdapter;
import me.example.davidllorca.bakingapp.data.Recipe;
import me.example.davidllorca.bakingapp.data.Step;

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
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        mRecipe = getIntent().getParcelableExtra(RECIPE_KEY);
        if(mRecipe == null){
            finish();
        }

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

    // TODO DELETE IF NOT NEEDED
//    public static class SimpleItemRecyclerViewAdapter
//            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {
//
//        private final RecipeDetailActivity mParentActivity;
//        private final List<Recipe> mValues;
//        private final boolean mTwoPane;
//        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Recipe item = (Recipe) view.getTag();
//                if (mTwoPane) {
//                    Bundle arguments = new Bundle();
//                    arguments.putParcelable(StepDetailFragment.STEP_KEY, item);
//                    StepDetailFragment fragment = new StepDetailFragment();
//                    fragment.setArguments(arguments);
//                    mParentActivity.getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.step_detail_container, fragment)
//                            .commit();
//                } else {
//                    Context context = view.getContext();
//                    Intent intent = new Intent(context, StepDetailActivity.class);
//                    intent.putExtra(StepDetailFragment.STEP_KEY, item);
//
//                    context.startActivity(intent);
//                }
//            }
//        };
//
//        SimpleItemRecyclerViewAdapter(RecipeDetailActivity parent,
//                                      List<Recipe> items,
//                                      boolean twoPane) {
//            mValues = items;
//            mParentActivity = parent;
//            mTwoPane = twoPane;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.recipe_list_content, parent, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(final ViewHolder holder, int position) {
//            holder.mIdView.setText(mValues.get(position).image);
//            holder.mContentView.setText(mValues.get(position).name);
//
//            holder.itemView.setTag(mValues.get(position));
//            holder.itemView.setOnClickListener(mOnClickListener);
//        }
//
//        @Override
//        public int getItemCount() {
//            return mValues.size();
//        }
//
//        class ViewHolder extends RecyclerView.ViewHolder {
//            final TextView mIdView;
//            final TextView mContentView;
//
//            ViewHolder(View view) {
//                super(view);
//                mIdView = view.findViewById(R.id.id_text);
//                mContentView = view.findViewById(R.id.name);
//            }
//        }
//    }
}

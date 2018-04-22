package me.example.davidllorca.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.example.davidllorca.bakingapp.data.DataSource;
import me.example.davidllorca.bakingapp.data.Recipe;

public class ListActivity extends AppCompatActivity implements RecipeRecyclerViewAdapter.RecipeListener {

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
        Intent intent = new Intent(this, RecipeListActivity.class);
        // TODO put arguments
        startActivity(intent);
    }
}

class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder> {

    private final Context mContext;
    private List<Recipe> mDataSet;
    private RecipeListener mListener;


    RecipeRecyclerViewAdapter(Context context, List<Recipe> dataSet, RecipeListener listener) {
        super();
        mContext = context;
        mDataSet = dataSet;
        mListener = listener;
    }

    @Override
    public RecipeRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);
        return new RecipeRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeRecyclerViewAdapter.ViewHolder holder, int position) {
        final Recipe recipe = mDataSet.get(position);

        if (!TextUtils.isEmpty(recipe.getImage())) {
            Picasso.with(mContext)
                    .load(recipe.getImage())
                    .into(holder.mImageView);
        }
        holder.mNameView.setText(recipe.getName());

        holder.itemView.setTag(recipe);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickRecipe(recipe);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    interface RecipeListener {
        void onClickRecipe(Recipe recipe);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView mImageView;
        final TextView mNameView;

        ViewHolder(View view) {
            super(view);
            mImageView = view.findViewById(R.id.iv_recipe_image);
            mNameView = view.findViewById(R.id.tv_recipe_name);
        }
    }
}

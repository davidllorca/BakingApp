package me.example.davidllorca.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.example.davidllorca.bakingapp.R;
import me.example.davidllorca.bakingapp.data.Recipe;

/**
 * Created by David Llorca <davidllorcabaron@gmail.com> on 22/04/18.
 */
public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter
        .ViewHolder> {

    private final Context mContext;
    private List<Recipe> mDataSet;
    private RecipeRecyclerViewAdapter.RecipeListener mListener;


    public RecipeRecyclerViewAdapter(Context context, List<Recipe> dataSet,
                                     RecipeRecyclerViewAdapter.RecipeListener listener) {
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

    public interface RecipeListener {
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

package me.example.davidllorca.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.example.davidllorca.bakingapp.R;
import me.example.davidllorca.bakingapp.data.Ingredient;
import me.example.davidllorca.bakingapp.data.Step;

/**
 * Created by David Llorca <davidllorcabaron@gmail.com> on 22/04/18.
 */

public class RecipeInfoRecyclerViewAdapter<T> extends RecyclerView
        .Adapter<RecyclerView.ViewHolder> {

    private static final int INGREDIENT_TYPE = 0;
    private static final int STEP_TYPE = 1;

    private final Context mContext;
    private final List mDataSet;
    private RecipeInfoRecyclerViewAdapter.RecipeStepListener mListener;


    public RecipeInfoRecyclerViewAdapter(Context context, List<T> items, RecipeStepListener
            listener) {
        super();
        mContext = context;
        mDataSet = items;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case INGREDIENT_TYPE:
                view = inflater.inflate(R.layout.item_ingredient, parent, false);
                return new RecipeInfoRecyclerViewAdapter.IngredientViewHolder(view);
            case STEP_TYPE:
                view = inflater.inflate(R.layout.item_step, parent, false);
                return new RecipeInfoRecyclerViewAdapter.StepViewHolder(view);
            default:
                throw new IllegalArgumentException("ViewType unexpected");
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case INGREDIENT_TYPE:
                Ingredient ingredient = (Ingredient) mDataSet.get(position);
                ((IngredientViewHolder) holder).mIngredientView.setText(ingredient.getIngredient());
                break;
            case STEP_TYPE:
                final Step step = (Step) mDataSet.get(position);
                ((StepViewHolder) holder).mShortDescriptionView.setText(step.getShortDescription());
                if (!step.getThumbnail().isEmpty()) {
                    Picasso.with(mContext)
                            .load(step.getThumbnail())
                            .into((ImageView) ((StepViewHolder) holder).mThumbnailView);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onClickStep(step);
                    }
                });
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mDataSet.get(position);
        if (item instanceof Ingredient) {
            return INGREDIENT_TYPE;
        }
        if (item instanceof Step) {
            return STEP_TYPE;
        }
        throw new IllegalArgumentException("Item must be a instance of " + Ingredient.class
                .getSimpleName() + " or " + Step.class.getSimpleName());
    }

    public interface RecipeStepListener {
        void onClickStep(Step step);
    }

    class StepViewHolder extends RecyclerView.ViewHolder {

        final TextView mShortDescriptionView;
        final View mThumbnailView;

        StepViewHolder(View view) {
            super(view);
            mShortDescriptionView = view.findViewById(R.id.tv_step_short_description);
            mThumbnailView = view.findViewById(R.id.iv_step_thumbnail);
        }
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        final TextView mIngredientView;

        IngredientViewHolder(View view) {
            super(view);
            mIngredientView = view.findViewById(R.id.tv_ingredient_name);
        }
    }
}
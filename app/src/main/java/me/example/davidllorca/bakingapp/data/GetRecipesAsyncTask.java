package me.example.davidllorca.bakingapp.data;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David Llorca <davidllorcabaron@gmail.com> on 14/05/18.
 */

public class GetRecipesAsyncTask extends AsyncTask<Void, Void, List<Recipe>> {

    private AsyncTaskListener<List<Recipe>> listener;

    public GetRecipesAsyncTask(AsyncTaskListener<List<Recipe>> listener) {
        this.listener = listener;
    }

    @Override
    protected List<Recipe> doInBackground(Void... voids) {
        URL url = NetworkUtils.buildGetRecipesUrl();
        try {
            String response = NetworkUtils.getResponseFromHttpUrl(url);
            return ParseUtils.parseGetRecipesResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Recipe> recipes) {
        listener.onTaskCompleted(recipes != null ? recipes : new ArrayList<Recipe>());
    }
}

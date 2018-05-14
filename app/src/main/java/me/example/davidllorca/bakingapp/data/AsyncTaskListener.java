package me.example.davidllorca.bakingapp.data;

/**
 * Created by David Llorca <davidllorcabaron@gmail.com> on 14/05/18.
 */

public interface AsyncTaskListener<T> {
    void onTaskCompleted(T result);
}

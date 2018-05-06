package me.example.davidllorca.bakingapp;

import me.example.davidllorca.bakingapp.data.Step;

/**
 * Created by david on 5/5/18.
 */

interface PlayControl {
    void onPreviousClick(Step step);

    void onNextClick(Step step);
}

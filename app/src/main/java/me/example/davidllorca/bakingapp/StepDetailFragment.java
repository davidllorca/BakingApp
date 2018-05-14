package me.example.davidllorca.bakingapp;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import me.example.davidllorca.bakingapp.data.Step;

public class StepDetailFragment extends Fragment {

    public static final String STEP_KEY = "step";
    private static final String HAS_PREVIOUS_KEY = "hasPrevious";
    private static final String HAS_NEXT_KEY = "hasNext";

    private Step step;
    private SimpleExoPlayerView playerView;
    private boolean playWhenReady;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private SimpleExoPlayer player;
    private View prevNextControls;
    private boolean isTablet;
    private Button prevBtn;
    private Button nextBtn;
    private PlayControl mListener;
    private boolean hasPreviousStep;
    private boolean hasNextStep;

    public StepDetailFragment() {
    }

    public static StepDetailFragment newInstance(Step step, boolean hasPrevStep, boolean
            hasNextStep) {
        Bundle args = new Bundle();
        args.putParcelable(STEP_KEY, step);
        args.putBoolean(HAS_PREVIOUS_KEY, hasPrevStep);
        args.putBoolean(HAS_NEXT_KEY, hasNextStep);
        StepDetailFragment fragment = new StepDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState == null) {
            if (getArguments().containsKey(STEP_KEY)) {
                step = getArguments().getParcelable(STEP_KEY);
                hasPreviousStep = getArguments().getBoolean(HAS_PREVIOUS_KEY);
                hasNextStep = getArguments().getBoolean(HAS_NEXT_KEY);
            }
        } else {
            step = savedInstanceState.getParcelable(STEP_KEY);
            hasPreviousStep = savedInstanceState.getBoolean(HAS_PREVIOUS_KEY);
            hasNextStep = savedInstanceState.getBoolean(HAS_NEXT_KEY);
            playbackPosition = savedInstanceState.getLong("pos");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity parentActivity = getActivity();
        if (parentActivity instanceof StepDetailActivity) {
            ((StepDetailActivity) parentActivity).getSupportActionBar().setTitle(step
                    .getShortDescription());
            mListener = (PlayControl) getActivity();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(STEP_KEY, step);
        outState.putBoolean(HAS_PREVIOUS_KEY, hasPreviousStep);
        outState.putBoolean(HAS_NEXT_KEY, hasNextStep);
        outState.putLong("pos", player.getCurrentPosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail, container, false);

        // Show the dummy name as text in a TextView.
        if (step != null) {
            ((TextView) rootView.findViewById(R.id.tv_step_detail_description)).setText(step
                    .description);
            playerView = rootView.findViewById(R.id.player_step_detail_video);
            prevNextControls = rootView.findViewById(R.id.layout_step_detail_controls);

            isTablet = getResources().getBoolean(R.bool.is_tablet);
            if (isTablet) {
                prevNextControls.setVisibility(View.GONE);
            } else {
                prevBtn = rootView.findViewById(R.id.btn_step_detail_prev);
                if (hasPreviousStep) {
                    prevBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.onPreviousClick(step);
                        }
                    });
                } else {
                    prevBtn.setVisibility(View.INVISIBLE);
                }

                nextBtn = rootView.findViewById(R.id.btn_step_detail_next);
                if (hasNextStep) {
                    nextBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.onNextClick(step);
                        }
                    });
                } else {
                    nextBtn.setVisibility(View.INVISIBLE);
                }
            }
        }

        return rootView;
    }

    private void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        playerView.setPlayer(player);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);

        Uri uri = Uri.parse(step.getVideoURL());
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("baking-app"))
                .createMediaSource(uri);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
//            releasePlayer();
            // TODO seguir aqui https://github
            // .com/google/ExoPlayer/blob/release-v2/demos/main/src/main/java/com/google/android
            // /exoplayer2/demo/PlayerActivity.java
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }
}

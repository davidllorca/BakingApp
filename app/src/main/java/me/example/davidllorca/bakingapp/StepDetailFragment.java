package me.example.davidllorca.bakingapp;

import android.app.Dialog;
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
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import me.example.davidllorca.bakingapp.data.Step;

public class StepDetailFragment extends Fragment {

    public static final String STEP_KEY = "step";
    private static final String HAS_PREVIOUS_KEY = "hasPrevious";
    private static final String HAS_NEXT_KEY = "hasNext";
    private final String STATE_PLAY_WHEN_READY = "playWhenReady";
    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";

    private Step step;
    private SimpleExoPlayerView mPlayerView;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private SimpleExoPlayer player;
    private boolean isTablet;
    private PlayControl mListener;
    private boolean hasPreviousStep;
    private boolean hasNextStep;

    private Dialog mFullScreenDialog;
    private boolean mPlayerFullscreen = false;
    private int mResumeWindow;
    private long mResumePosition;
    private boolean mPlayWhenReady;

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
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mPlayWhenReady = savedInstanceState.getBoolean(STATE_PLAY_WHEN_READY);
            mPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
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
        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
        outState.putBoolean(STATE_PLAY_WHEN_READY, mPlayWhenReady);
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mPlayerFullscreen);
        super.onSaveInstanceState(outState);
    }

    private void initFullscreenDialog() {

        mFullScreenDialog = new Dialog(getContext(), android.R.style
                .Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }

    private void openFullscreenDialog() {
        ((ViewGroup) mPlayerView.getParent()).removeView(mPlayerView);
        if (mFullScreenDialog == null) {
            initFullscreenDialog();
        }
        mFullScreenDialog.addContentView(mPlayerView, new ViewGroup.LayoutParams(ViewGroup
                .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mPlayerFullscreen = true;
        mFullScreenDialog.show();
    }


    private void closeFullscreenDialog() {
        ((ViewGroup) mPlayerView.getParent()).removeView(mPlayerView);
        ((FrameLayout) getView().findViewById(R.id.container_player_step_detail_video)).addView
                (mPlayerView);
        mPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (!isTablet) {
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                closeFullscreenDialog();
            } else {
                if (!mPlayerFullscreen)
                    openFullscreenDialog();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail, container, false);

        if (step != null) {
            TextView description = rootView.findViewById(R.id
                    .tv_step_detail_description);
            description.setText(step.description);
            mPlayerView = rootView.findViewById(R.id.player_step_detail_video);
            View prevNextControls = rootView.findViewById(R.id.layout_step_detail_controls);

            isTablet = getResources().getBoolean(R.bool.is_tablet);
            if (isTablet) {
                prevNextControls.setVisibility(View.GONE);
            } else {
                Button prevBtn = rootView.findViewById(R.id.btn_step_detail_prev);
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

                Button nextBtn = rootView.findViewById(R.id.btn_step_detail_next);
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

        mPlayerView.setPlayer(player);

        boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;
        if (haveResumePosition) {
            player.seekTo(mResumeWindow, mResumePosition);
        } else {
            player.seekTo(currentWindow, playbackPosition);
        }
        Uri uri = Uri.parse(step.getVideoURL());
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
        player.setPlayWhenReady(mPlayWhenReady);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("baking-app"))
                .createMediaSource(uri);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPlayerView == null) {
            initFullscreenDialog();
        }
        initializePlayer();

        if (mPlayerFullscreen) {
            ((ViewGroup) mPlayerView.getParent()).removeView(mPlayerView);
            mFullScreenDialog.addContentView(mPlayerView, new ViewGroup.LayoutParams(ViewGroup
                    .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mFullScreenDialog.show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPlayerView != null && mPlayerView.getPlayer() != null) {
            mResumeWindow = mPlayerView.getPlayer().getCurrentWindowIndex();
            mResumePosition = Math.max(0, mPlayerView.getPlayer().getContentPosition());
            mPlayWhenReady = mPlayerView.getPlayer().getPlayWhenReady();
            mPlayerView.getPlayer().release();
        }

        if (mFullScreenDialog != null)
            mFullScreenDialog.dismiss();
    }

}

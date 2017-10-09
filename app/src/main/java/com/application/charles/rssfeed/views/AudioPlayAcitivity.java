package com.application.charles.rssfeed.views;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.charles.rssfeed.R;

import java.io.IOException;

public class AudioPlayAcitivity extends BaseActivity {

    private MediaPlayer mMediaPlayer;
    private ImageView mPlayButton;
    private TextView mAudioTitle;
    private boolean isClicked = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlayButton = (ImageView) findViewById(R.id.audio_play_button);
        mAudioTitle = (TextView) findViewById(R.id.audio_play_title);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.audio_play_activity_layout;
    }

    @Override
    protected boolean showBackButton() {
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        final String url = getIntent().getExtras().getString("url");
        final String title = getIntent().getExtras().getString("title");
        mAudioTitle.setText(title);
        mMediaPlayer = new MediaPlayer();
        initialAudio(url);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClicked) {
                    mMediaPlayer.start();
                    mPlayButton.setImageResource(R.drawable.pause_circle);
                } else {
                    mMediaPlayer.pause();
                    mPlayButton.setImageResource(R.drawable.play_circle);
                }
                isClicked = !isClicked;
            }
        });
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                isClicked = false;
                mPlayButton.setImageResource(R.drawable.play_circle);
                try {
                    mp.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initialAudio(String url) {
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mMediaPlayer.setDataSource(this, Uri.parse(url));
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mMediaPlayer.stop();
    }
}

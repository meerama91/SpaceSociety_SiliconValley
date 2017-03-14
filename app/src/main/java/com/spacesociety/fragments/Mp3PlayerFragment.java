package com.spacesociety.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.spacesociety.R;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by julep on 9/22/15.
 */
public class Mp3PlayerFragment extends Fragment {
    private MediaPlayer mediaPlayer;
    public TextView duration;
    public TextView name;
    private Uri myUri;
    private double timeElapsed = 0, finalTime = 0;
    private Handler durationHandler = new Handler();
    private SeekBar seekbar;

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("MP3 Player");

        View rootView = inflater.inflate(R.layout.fragment_player, container, false);
        String fileName = getArguments().getString("Filename");
        String folder = getArguments().getString("Folder");
        initializeViews(rootView, folder, fileName);

        ImageView playButton = (ImageView) rootView.findViewById(R.id.media_play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });

        ImageView pauseButton = (ImageView) rootView.findViewById(R.id.media_pause);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
            }
        });

        return rootView;
    }

    public void initializeViews(View root, String folder, String fileName) {
        myUri = Uri.parse(Environment.getExternalStorageDirectory() + folder + fileName);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(getActivity(), myUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finalTime = mediaPlayer.getDuration();
        duration = (TextView) root.findViewById(R.id.songDuration);
        name = (TextView) root.findViewById(R.id.songname);
        name.setText(fileName);
        seekbar = (SeekBar) root.findViewById(R.id.seekBar);
        seekbar.setMax((int) finalTime);
        seekbar.setClickable(true);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                               @Override
                                               public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                                   if (mediaPlayer != null && fromUser) {
                                                       mediaPlayer.seekTo(progress);
                                                   }
                                               }

                                               @Override
                                               public void onStartTrackingTouch(SeekBar seekBar) {

                                               }

                                               @Override
                                               public void onStopTrackingTouch(SeekBar seekBar) {

                                               }
                                           }

        );
    }

    public void play() {
        mediaPlayer.start();
        timeElapsed = mediaPlayer.getCurrentPosition();
        seekbar.setProgress((int) timeElapsed);
        durationHandler.postDelayed(updateSeekBarTime, 100);
    }


    private Runnable updateSeekBarTime = new Runnable() {
        public void run() {
            timeElapsed = mediaPlayer.getCurrentPosition();
            seekbar.setProgress((int) timeElapsed);
            double timeRemaining = finalTime - timeElapsed;
            duration.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));
            durationHandler.postDelayed(this, 100);
        }
    };

    public void pause() {
        mediaPlayer.pause();
    }
}

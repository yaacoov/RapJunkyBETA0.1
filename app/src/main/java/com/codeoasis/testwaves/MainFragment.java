package com.codeoasis.testwaves;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.codeoasis.testwaves.model.Word;
import com.codeoasis.testwaves.utils.AudioView.AudioVisualization;
import com.codeoasis.testwaves.utils.AudioView.DbmHandler;
import com.codeoasis.testwaves.utils.AudioView.GLAudioVisualizationView;
import com.codeoasis.testwaves.utils.AudioView.VisualizerDbmHandler;
import com.codeoasis.testwaves.utils.BeatPlayerView.BeatPlayerView;
import com.codeoasis.testwaves.utils.SongsManager;
import com.codeoasis.testwaves.utils.Utilities;
import com.codeoasis.testwaves.utils.WordsManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class MainFragment extends Fragment implements MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener,WordSettignsListener {

    private TextView textview;
    Animation textAnimation;
    private MediaPlayer mediaplayer;
    //waves animation from sound
    private com.codeoasis.testwaves.utils.AudioView.AudioVisualization audioVisualization;
    private GLAudioVisualizationView glAudioVisualizationView;




    //Managers
    private WordsManager wordManager;


    //forwardMusicButton
    private ImageView forwardMusicButton;

    //backMusicButton
    private ImageView backMusicButton;

    //circle view
    private BeatPlayerView mpv;
    // Handler to update UI timer, progress bar etc,.
    private Handler mHandler = new Handler();
    ;
    private SongsManager songManager;
    private Utilities utils;
   /* private int seekForwardTime = 5000; // 5000 milliseconds
    private int seekBackwardTime = 5000; // 5000 milliseconds*/
    private int currentSongIndex = 0;
    private boolean isShuffle = false;
    private boolean isRepeat = false;
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

    //TODO: pop word, circle with progress, circle button to change words, arrows
    public MainFragment() {
        // Required empty public constructor
    }
    @Override
    public void onResume() {
        super.onResume();
        audioVisualization.onResume();
    }

    @Override
    public void onPause() {
        audioVisualization.onPause();
        super.onPause();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentSongIndex = getArguments().getInt("songId");



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        initSoundsManager();
        initWordsManager();
        initViews(v);



        return v;
    }




    private void initViews(View v) {
        //textview
        textview = (TextView) v.findViewById(R.id.textView);
        textAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.textanimation);
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchWord();
            }
        });


        glAudioVisualizationView = (GLAudioVisualizationView)v.findViewById(R.id.visualizer_view);
        audioVisualization = (AudioVisualization) glAudioVisualizationView;
        // set audio visualization handler. This will REPLACE previously set speech recognizer handler
        VisualizerDbmHandler vizualizerHandler = DbmHandler.Factory.newVisualizerHandler(getContext(), 0);
        audioVisualization.linkTo(vizualizerHandler);

        Log.d("Initview","Start building the View");
        mpv = (BeatPlayerView) v.findViewById(R.id.mpv);
        Log.d("Initview","mpv built");

        mpv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("mpv","mpv clicked");
                if (!mpv.isRotating()) {
                    if (mediaplayer != null) {
                            mediaplayer.start();
                        mpv.start();
                        Log.d("mpv","is rotating");

                    } ;

                }
                else {
                    mpv.stop();
                    if(mediaplayer!=null) {
                        mediaplayer.pause();
                        Log.d("mpv","has stopped rotating");
                    }

                }
            }
        });
        backMusicButton = (ImageView) v.findViewById(R.id.back_music_button);
        backMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("BackMusicButton","is clicked");
                if(currentSongIndex > 0){
                    playSong(currentSongIndex - 1);
                    currentSongIndex = currentSongIndex - 1;
                }else{
                    // play last song
                    playSong(songsList.size() - 1);
                    currentSongIndex = songsList.size() - 1;
                }
                if (!mpv.isRotating()) {
                    mpv.start();
                    Log.d("mpv","is rotating");
                }
            }
        });


        forwardMusicButton = (ImageView) v.findViewById(R.id.forward_music_button);
        forwardMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ForwardMusicButton","is clicked");
                // check if next song is there or not
                if(currentSongIndex < (songsList.size() - 1)){
                    playSong(currentSongIndex + 1);
                    currentSongIndex = currentSongIndex + 1;
                }else{
                    // play first song
                    playSong(0);
                    currentSongIndex = 0;
                }
                if (!mpv.isRotating()) {
                    mpv.start();
                    Log.d("mpv","is rotating");
                }
            }
        });


      /*  //change word button and RippleBackground
        final RippleBackground rippleBackground = (RippleBackground) v.findViewById(R.id.content);
        ImageView button = (ImageView) v.findViewById(R.id.switch_button);
        button.bringToFront();
        rippleBackground.bringToFront();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:switch word
                SwitchWord();
                rippleBackground.startRippleAnimation();

                new CountDownTimer(500, 100) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        rippleBackground.stopRippleAnimation();

                    }
                }.start();
            }


        });*/
        // Listeners

        mediaplayer.setOnCompletionListener(this); // Important
    }


    private void SwitchWord() {


        textview.setText(wordManager.getRandomWord());
        textview.startAnimation(textAnimation);

    }

    private void initWordsManager() {
        //create list in wordsmanager

        //init list


        //adding words to the list

       ArrayList<Word> words=WordsManager.CreateList();
        //create word manager with the arraylist passed
        wordManager = new WordsManager(words);

    }

    private void initSoundsManager() {
        // Mediaplayer
        mediaplayer = new MediaPlayer();
        songManager = new SongsManager();
        utils = new Utilities();
        // Getting all songs list from file :default"
        songsList = songManager.getPlayList("default");
        Log.d("MediaPlayer","has been built with soundManager");

    }
    public void  playSong(int songIndex){
        // Play song
        Log.d("PlaySong","is playing the song");
        try {
            mediaplayer.reset();
            mediaplayer.setDataSource(songsList.get(songIndex).get("songPath"));
            mediaplayer.prepare();
            mediaplayer.start();
            // Displaying Song title
            mpv.setProgress(0);
            mpv.setMax( mediaplayer.getDuration()/1000);

            // Updating progress bar
          //  updateProgressBar();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  /*  public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mediaplayer.getDuration();
            long currentDuration = mediaplayer.getCurrentPosition();
*//*

            // Displaying Total Duration time
            songTotalDurationLabel.setText(""+utils.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            songCurrentDurationLabel.setText(""+utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int)(utils.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            songProgressBar.setProgress(progress);
*//*// Updating progress bar
            int progress = (int)(utils.getProgressPercentage(currentDuration, totalDuration));
mpv.setProgress(progress);
            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };
*/


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

        // check for repeat is ON or OFF
        if(isRepeat){
            // repeat is on play same song again
            playSong(currentSongIndex);
        } else if(isShuffle){
            // shuffle is on - play a random song
            Random rand = new Random();
            currentSongIndex = rand.nextInt((songsList.size() - 1) - 0 + 1) + 0;
            playSong(currentSongIndex);
        } else{
            // no repeat or shuffle ON - play next song
            if(currentSongIndex < (songsList.size() - 1)){
                playSong(currentSongIndex + 1);
                currentSongIndex = currentSongIndex + 1;
            }else{
                // play first song
                playSong(0);
                currentSongIndex = 0;
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // remove message Handler from updating progress bar
      //  mHandler.removeCallbacks(mUpdateTimeTask);

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
       // mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mediaplayer.getDuration();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

        // forward or backward to certain seconds
        mediaplayer.seekTo(currentPosition);

        // update timer progress again
        //updateProgressBar();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        mediaplayer.release();

    }

    @Override
    public void onDestroyView(){
        audioVisualization.release();

        super.onDestroyView();

    }

    @Override
    public void changeList(int i) {
        ArrayList<Word> words=WordsManager.ListByDifficulty(i);
        wordManager = new WordsManager(words);

    }



}


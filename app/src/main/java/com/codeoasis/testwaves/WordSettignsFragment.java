package com.codeoasis.testwaves;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.triggertrap.seekarc.SeekArc;

/**
 * Created by USER on 8/23/2016.
 */
public class WordSettignsFragment extends Fragment {
    private WordSettignsListener WordSettignsListener;
    private SeekArc mSeekArc;
    private TextView mSeekArcProgress;
    private Button button;
    static int i;


    public WordSettignsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.seekbar_circle, container, false);
        initViews(v);
        return v;

    }

    private void initViews(View v) {


        mSeekArc = (SeekArc) v.findViewById(R.id.seekArc);
        mSeekArc.setProgressColor(Color.RED);
        mSeekArcProgress = (TextView) v.findViewById(R.id.seekArcProgress);
        mSeekArcProgress.setTextColor(Color.WHITE);


        mSeekArc.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {
            }

            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {
            }

            @Override
            public void onProgressChanged(SeekArc seekArc, int progress,
                                          boolean fromUser) {
                if (progress <= 33 && progress > 0) {
                    mSeekArcProgress.setText(R.string.Novice);
                }
                if (progress > 33 && progress <= 66) {
                    mSeekArcProgress.setText(R.string.Advanced);
                }
                if (progress > 66 && progress < 100) {
                    mSeekArcProgress.setText(R.string.Professional);
                }
                if (progress == 0 || progress == 100) {
                    mSeekArcProgress.setText(R.string.Random_All);

                }

            }
        });
        button = (Button) v.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSeekArcProgress.getText() == "Novice") {


                    WordSettignsListener.changeList(1);


                }
                if (mSeekArcProgress.getText() == "Advanced") {

                    WordSettignsListener.changeList(2);

                }
                if (mSeekArcProgress.getText() == "Professional") {

                    WordSettignsListener.changeList(3);

                }
                if (mSeekArcProgress.getText() == "Random_All") {

                    WordSettignsListener.changeList(0);
                }
            }
        });





    }



}





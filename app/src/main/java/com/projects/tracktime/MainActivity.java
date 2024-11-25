package com.projects.tracktime;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


public class MainActivity extends Activity {

    private boolean running;
    private int seconds = 0;
    private boolean wasrunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState != null)
        {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasrunning = savedInstanceState.getBoolean("wasrunning");
        }
        runTimer();
    }

    public void onClickStart(View view)
    {
        running = true;
    }

    public void onClickStop(View view)
    {
        running = false;
    }

    public void onClickRestart(View view)
    {
        running = false;
        seconds = 0;
    }

    @Override
    protected void onStop() {
        super.onStop();
        wasrunning = true;
        running = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(wasrunning)
            running = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasrunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wasrunning)
            running = true;
    }

    private void runTimer()
    {
        final TextView timeView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run()
            {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int sec = seconds%60;
                String time = String.format("%d:%02d:%02d",hours,minutes,sec);
                timeView.setText(time);
                if(running)
                    seconds++;
                handler.postDelayed(this,1000);
            }
        });
    }

    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putBoolean("running",running);
        savedInstanceState.putInt("seconds",seconds);
        savedInstanceState.putBoolean("wasrunning",wasrunning);
    }

}
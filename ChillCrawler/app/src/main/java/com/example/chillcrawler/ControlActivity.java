package com.example.chillcrawler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import com.example.chillcrawler.JoystickView;

public class ControlActivity extends AppCompatActivity implements JoystickView.JoystickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //JoystickView test = new JoystickView(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
    }

    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int id) {
        Log.d("Main Mehtod", "X percent: " + xPercent + "Y percent: " + yPercent);

    }
}

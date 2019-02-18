package com.example.chillcrawler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.view.Menu;


import com.example.chillcrawler.JoystickView;

public class ControlActivity extends AppCompatActivity implements JoystickView.JoystickListener {


    private int BUTTON_STATE = 0;
    private int BUTTON_STATE_ONCE = 0;
    private int BUTTON_STATE_TWICE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //JoystickView test = new JoystickView(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        final Button flex = (Button) findViewById(R.id.flex);

        flex.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (BUTTON_STATE == BUTTON_STATE_ONCE) {
                    flex.setTextColor(getResources().getColor(R.color.colorBlue));
                    flex.setText("FLEX\nON");
                    BUTTON_STATE = BUTTON_STATE_TWICE;
                } else if (BUTTON_STATE == BUTTON_STATE_TWICE) {
                    flex.setTextColor(getResources().getColor(R.color.colorRed));
                    flex.setText("FLEX\nOFF");
                    BUTTON_STATE = BUTTON_STATE_ONCE;
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int id) {
        Log.d("Main Mehtod", "X percent: " + xPercent + "Y percent: " + yPercent);

    }
}

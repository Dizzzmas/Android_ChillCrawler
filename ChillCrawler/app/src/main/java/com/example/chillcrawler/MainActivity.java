package com.example.chillcrawler;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    Button connect;
    boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        connect = findViewById(R.id.connect);

        connect.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {

                final Intent intent = new Intent(Intent.ACTION_MAIN, null);   //move to bluetooth menu
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName cn = new ComponentName("com.android.settings",
                        "com.android.settings.bluetooth.BluetoothSettings");
                intent.setComponent(cn);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                flag = true;
                startActivity(intent);

            }

        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (flag == true) {
            Intent toControl = new Intent(MainActivity.this, ControlActivity.class);

            startActivity(toControl);
        }
        flag = false;

    }

}




package com.example.chillcrawler;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.chillcrawler.JoystickView;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;

import java.io.IOException;
import java.util.UUID;


public class ControlActivity extends AppCompatActivity implements JoystickView.JoystickListener {

    ImageButton sound;

    private int BUTTON_STATE = 0;
    private int BUTTON_STATE_ONCE = 0;
    private int BUTTON_STATE_TWICE = 1;

    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //JoystickView test = new JoystickView(this);
        super.onCreate(savedInstanceState);
        Intent newint = getIntent();
        address = newint.getStringExtra("EXTRA_ADDRESS");
        setContentView(R.layout.activity_control);
        ControlActivity.this.new ConnectBT().execute();



        BoomMenuButton bmb = findViewById(R.id.bmb);

//BOOM MENU
        TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder()
                .normalImageRes(R.drawable.ic_octicons_arrow_up)
                .normalTextRes(R.string.stand)
                .rippleEffect(true)
                .imagePadding(new Rect(0, 0, 0, 10))
                .normalColorRes(R.color.colorGreen)
                .pieceColor(Color.BLACK)
                .highlightedTextColor(Color.BLACK)
                .highlightedColor(R.color.colorGreen)
                .normalTextColor(Color.BLACK)
                .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            // When the boom-button corresponding this builder is clicked.
                            Toast.makeText(ControlActivity.this, "Clicked " + index, Toast.LENGTH_SHORT).show();
                            sendStand();
                        }
                    });
        bmb.addBuilder(builder);
        TextInsideCircleButton.Builder builder1 = new TextInsideCircleButton.Builder()
                .normalImageRes(R.drawable.ic_octicons_arrow_down)
                .normalTextRes(R.string.sit)
                .rippleEffect(true)
                .normalColorRes(R.color.colorGreen)
                .highlightedColor(R.color.colorGreen)
                .pieceColor(Color.BLACK)
                .highlightedTextColor(Color.BLACK)
                .imagePadding(new Rect(0, 0, 0, 10))
                .normalTextColor(Color.BLACK)
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
                        Toast.makeText(ControlActivity.this, "Clicked " + index, Toast.LENGTH_SHORT).show();
                        sendSit();
                    }
                });
        bmb.addBuilder(builder1);
        TextInsideCircleButton.Builder builder2 = new TextInsideCircleButton.Builder()
                .normalImageRes(R.drawable.ic_waving_hand)
                .normalColorRes(R.color.colorGreen)
                .highlightedColor(R.color.colorGreen)
                .rippleEffect(true)
                .highlightedTextColor(Color.BLACK)
                .imagePadding(new Rect(10, 5, 0, 0))
                .pieceColor(Color.BLACK)
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
                        Toast.makeText(ControlActivity.this, "Clicked " + index, Toast.LENGTH_SHORT).show();
                        sendWave();
                    }
                });
        bmb.addBuilder(builder2);




        final Button flex = findViewById(R.id.flex);

        flex.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //sendOff();
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


    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }


    private void sendStand() {
        if (btSocket != null) {
            try {
                btSocket.getOutputStream().write("STAND".getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    private void sendSit() {
        if (btSocket != null) {
            try {
                btSocket.getOutputStream().write("SIT".getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    private void sendWave() {
        if (btSocket != null) {
            try {
                btSocket.getOutputStream().write("WAVE".getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int id) {
        Log.d("Main Mehtod", "X percent: " + xPercent + "Y percent: " + yPercent);   //JOYSTICK

        String buff = "STANDBY";
        if (xPercent > 0.90) {
            buff = "RIGHT\n";
            Log.d("Main Mehtod", "RIGHT SENT");
        } else if (xPercent < -0.90) {
            buff = "LEFT\n";
            Log.d("Main Mehtod", "LEFT SENT");
        }

        if (yPercent < -0.90) {
            buff = "FORWARD\n";
            Log.d("Main Mehtod", "FORWARD SENT");
        } else if (yPercent > 0.90) {
            buff = "BACK\n";
            Log.d("Main Mehtod", "BACK SENT");
        }

        if (buff != "STANDBY") {
            try {
                btSocket.getOutputStream().write(buff.toString().getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }

    }


    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(ControlActivity.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            } catch (IOException e) {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            } else {
                msg("Connected");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

}


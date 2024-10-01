package com.example.broadcastreceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private BatteryReceiver batteryReceiver;
    private TextView batteryStatusTextView;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        batteryStatusTextView = findViewById(R.id.batteryStatusTextView);
        Button updateButton = findViewById(R.id.updateButton);

        batteryReceiver = new BatteryReceiver(batteryStatusTextView);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                Intent batteryStatus = registerReceiver(null, filter);
                if (batteryStatus != null) {
                    batteryReceiver.onReceive(MainActivity.this, batteryStatus);
                    Log.d(TAG, "Botón presionado: estado de la batería actualizado.");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
                                                        //obtener bateria
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);

        Log.d(TAG, "BroadcastReceiver registrado satisfactoriamente.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(batteryReceiver);

        Log.d(TAG, "BroadcastReceiver desregistrado satisfactoriamente.");
    }

}

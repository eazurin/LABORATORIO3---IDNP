package com.example.pendingintent;

import android.app.PendingIntent;
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
    private PendingIntent pendingIntent;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        batteryStatusTextView = findViewById(R.id.batteryStatusTextView);
        Button updateButton = findViewById(R.id.updateButton);

        batteryReceiver = new BatteryReceiver();
        batteryReceiver.setBatteryStatusTextView(batteryStatusTextView); // Establece el TextView

        Intent intent = new Intent(this, BatteryReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                Intent batteryStatus = registerReceiver(null, filter);
                if (batteryStatus != null) {
                    try {
                        pendingIntent.send(MainActivity.this, 0, batteryStatus);
                        Log.d(TAG, "Botón presionado: estado de la batería actualizado.");
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
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

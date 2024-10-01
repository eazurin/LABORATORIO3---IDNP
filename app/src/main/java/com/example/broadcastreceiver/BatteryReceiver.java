package com.example.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

public class BatteryReceiver extends BroadcastReceiver {

    private TextView batteryStatusTextView;
    private static final String TAG = "BatteryReceiver";

    public BatteryReceiver(TextView batteryStatusTextView) {
        this.batteryStatusTextView = batteryStatusTextView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra("level", -1);
        int scale = intent.getIntExtra("scale", -1);

        int batteryPct = (int) ((level / (float) scale) * 100);

        batteryStatusTextView.setText("Estado de la batería: " + batteryPct + "%");

        Log.d(TAG, "Nivel de batería recibido: " + batteryPct + "%");
    }
}

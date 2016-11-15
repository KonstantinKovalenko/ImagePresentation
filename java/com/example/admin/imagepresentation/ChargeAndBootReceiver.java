package com.example.admin.imagepresentation;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.preference.PreferenceManager;

public class ChargeAndBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean autorunIsChecked = sharedPreferences.getBoolean("chbPref_programAutorun", false);
        boolean chargingIsChecked = sharedPreferences.getBoolean("chbPref_startWhenCharging", false);
        if (!autorunIsChecked && !chargingIsChecked) return;
        switch (intent.getAction()) {
            case Intent.ACTION_BOOT_COMPLETED:
                if (!autorunIsChecked) return;
                break;
            case Intent.ACTION_POWER_CONNECTED:
                if (!chargingIsChecked) return;
                Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                int batteryStatus = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                boolean isNotCharging = batteryStatus != BatteryManager.BATTERY_STATUS_CHARGING;
                if (isNotCharging) return;
                break;
        }
        Intent tempIntent = new Intent(context, MainActivity.class);
        tempIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(tempIntent);
    }
}

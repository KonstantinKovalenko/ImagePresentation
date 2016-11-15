package com.example.admin.imagepresentation;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            runChecked(sharedPreferences, context);
        }
    }

    private void runChecked(SharedPreferences sharedPreferences, Context context) {
        boolean serviceIsEnabled = sharedPreferences.getBoolean("chbPref_service", false);
        boolean serviceAutorun = sharedPreferences.getBoolean("chbPref_serviceAutorun", false);
        boolean programAutorun = sharedPreferences.getBoolean("chbPref_programAutorun", false);
        boolean programStartWhenChanging = sharedPreferences.getBoolean("chbPref_startWhenCharging", false);
        if (!serviceIsEnabled) return;
        if (!serviceAutorun && !programAutorun && !programStartWhenChanging) {
            Log.d("myTag", "все галочки отключены - броадкаст");
            return;
        }
        if (serviceAutorun && programAutorun) {
            context.startActivity(new Intent(context, MainActivity.class));
        } else if (serviceAutorun && !programAutorun) {
            Intent intent = new Intent("android.service.example.impresentservice");
            intent.putExtra("serviceAutorun", serviceAutorun);
            intent.putExtra("programAutorun", programAutorun);
            intent.putExtra("programStartWhenChanging", programStartWhenChanging);
            context.startService(intent);
        }
        Log.d("myTag", serviceIsEnabled + ", " + serviceAutorun + ", " + programAutorun + ", " + programStartWhenChanging);
    }
}

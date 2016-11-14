package com.example.admin.imagepresentation;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class ImPresentService extends Service {

    boolean serviceAutoload;
    boolean programAutoload;
    boolean programStartWhenChanging;
    NotificationManager notificationManager;
    MyNotification notification;

    private String LOG_TAG = "myTag";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind()");
        return null;
    }

    @Override
    public void onCreate() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification = new MyNotification(notificationManager, this);
        super.onCreate();
        Log.d(LOG_TAG, "Service onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand() imPresentService");
        serviceAutoload = intent.getBooleanExtra("serviceAutorun", false);
        programAutoload = intent.getBooleanExtra("programAutoload", false);
        programStartWhenChanging = intent.getBooleanExtra("programStartWhenChanging", false);
        Log.d(LOG_TAG, "Service Autoload: " + serviceAutoload + ", " +
                "Program Autoload: " + programAutoload + ", " +
                "Start program when charging: " + programStartWhenChanging);
        checkForCharging();
        return START_NOT_STICKY;
    }

    private void checkForCharging() {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isCharging = false;
                IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                while (!isCharging) {
                    Intent batteryIntent = registerReceiver(null, iFilter);
                    int batteryStatus = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                    if (batteryStatus == BatteryManager.BATTERY_STATUS_CHARGING) {
                        isCharging = true;
                        Intent intent = new Intent(ImPresentService.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        notification.sendNotification("presentation started", "Notification", "Device charging is ON, image presentation started", "123");
                        Log.d(LOG_TAG, "Thread stop, battery status is: charging");
                        stopSelf();
                    }
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        Log.d(LOG_TAG, "Nothing Happens");
                    } catch (InterruptedException ie) {
                        Log.d(LOG_TAG, "Service error");
                    }
                }

            }
        });
        t.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "Service onDestroy()");
    }
}

package com.example.admin.imagepresentation;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyNotification {

    private String LOG_TAG = "myTag";

    NotificationManager notificationManager;
    Context context;

    public MyNotification(NotificationManager notificationManager, Context context) {
        this.notificationManager = notificationManager;
        this.context = context;
    }

    public void sendNotification(String ticker, String title, String text, String subtext) {

        Notification.Builder notificationBuilder = new Notification.Builder(context);
        notificationBuilder.setSmallIcon(android.R.drawable.ic_dialog_info);
        notificationBuilder.setTicker(ticker);
        notificationBuilder.setWhen(System.currentTimeMillis());

        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(text);
        notificationBuilder.setSubText(subtext);
        notificationBuilder.setContent(null);

        notificationBuilder.setAutoCancel(true);
        notificationBuilder.build();
        Notification notification = notificationBuilder.getNotification();
        notificationManager.notify(1, notification);
    }
}

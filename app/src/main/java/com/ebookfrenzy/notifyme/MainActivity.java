package com.ebookfrenzy.notifyme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
{
    NotificationManager notificationManager;
    final static String NEWS_CHANNEL_ID = "com.ebookfrenzy.notifyme.news";
    final static String MESSAGE_CHANNEL_ID = "com.ebookfrenzy.notifyme.message";
    final static String GROUP_ID = "whatever, man";
    final static String REPLY_KEY = "reply_key";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        initNotificationChannel(
                NEWS_CHANNEL_ID,
                "Notifyme news",
                "News notifications channel"
        );
        initNotificationChannel(
                MESSAGE_CHANNEL_ID,
                "Notifyme messages",
                "Messages notification channel"
        );
    }

    protected void initNotificationChannel(String id, String name, String description)
    {
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel notificationChannel = new NotificationChannel(id, name, importance);
        notificationChannel.setDescription(description);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.GREEN);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 300, 200, 400});
        notificationManager.createNotificationChannel(notificationChannel);
    }

    public void sendNewsNotification(View view)
    {
        int notificationId = 101;

        Intent intent = new Intent(this, ResultActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Icon icon = Icon.createWithResource(MainActivity.this, R.drawable.ic_launcher_foreground);
        Notification.Action action = new Notification.Action.Builder(icon, "Open", pendingIntent).build();

        Notification notificationGroup = new Notification.Builder(this, NEWS_CHANNEL_ID)
                .setContentTitle("Upcoming alarm")
                .setContentText(returnCurrentDate() + " DRINK EVEN MORE WATER")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setChannelId(NEWS_CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setGroup(GROUP_ID)
                .setGroupSummary(true)
                .build();
        notificationManager.notify(102, buildNotification("Jenny"));
        notificationManager.notify(103, buildNotification("Promise"));
        notificationManager.notify(104, buildNotification("Hacchi"));
        notificationManager.notify(105, buildNotification("Chisom"));
        notificationManager.notify(105, buildNotification("Power"));
        notificationManager.notify(105, buildNotification("Klinton"));
        notificationManager.notify(notificationId, notificationGroup);
    }

    public void sendNotification(View view)
    {
        String replyHint = "Enter your reply here";
        RemoteInput remoteInput = new RemoteInput.Builder(REPLY_KEY)
                .setLabel(replyHint)
                .build();
    }

    public void sendMessageNotification(View view)
    {
        int notificationId = 201;

        Notification notification = new Notification.Builder(this, NEWS_CHANNEL_ID)
                .setContentTitle("New message")
                .setContentText("You have a new message from Jenny")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true)
                .build();
    }

    private String returnCurrentDate()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("DD HH:mm", Locale.US);
        return dateFormat.format(new Date());
    }

    private PendingIntent createPendingIntent(Class activity)
    {
        Intent intent = new Intent(this, activity);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    private Notification.Action buildNotificationAction(PendingIntent pendingIntent)
    {
        Icon icon = Icon.createWithResource(MainActivity.this, R.drawable.ic_launcher_foreground);
        Notification.Action action = new Notification.Action.Builder(icon, "Open", pendingIntent).build();
        return action;
    }

    private Notification buildNotification(String sender)
    {
        Notification notification = new Notification.Builder(this, NEWS_CHANNEL_ID)
                .setContentText("New message")
                .setContentText(sender + " sent you a message")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setChannelId(NEWS_CHANNEL_ID)
                .setAutoCancel(true)
                .setGroup(GROUP_ID)
                .build();
        return notification;
    }
}
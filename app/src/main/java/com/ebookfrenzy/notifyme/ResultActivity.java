package com.ebookfrenzy.notifyme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ResultActivity extends AppCompatActivity
{
    static final String SPORTS_CHANNEL_ID = "com.ebookfrenzy.notifyme.sports";
    static final String POWER_CHANNEL_ID = "com.ebookfrenzy.notifyme.power";
    NotificationManager notificationManager;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                sendNotification();
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                notifyPowerStatus();
                return true;
            }
        });

        initNotificationChannel(
                SPORTS_CHANNEL_ID,
                "Sports",
                "Channel for sports notifications"
        );
        initNotificationChannel(
                POWER_CHANNEL_ID,
                "Power",
                "Notifications of power connection status"
        );
    }

    private void initNotificationChannel(String id, String name, String description)
    {
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        channel.setDescription(description);
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        notificationManager.createNotificationChannel(channel);
    }

    private void sendNotification()
    {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        Notification notification = new Notification.Builder(this, SPORTS_CHANNEL_ID)
                .setContentTitle("ManUtd wins the league")
                .setContentText("You thought this was actually possible? LOL. April fool, nigga.")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notificationManager.notify(101, notification);
    }

    public void notifyPowerStatus()
    {
        Notification notification = new Notification.Builder(this, POWER_CHANNEL_ID)
                .setContentTitle("Power")
                .setContentText("Cheers to the freaking weekend")
                .setSmallIcon(R.drawable.battery_icon)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(102, notification);
    }

    public void notifyPowerStatus(String title, String description)
    {
        Notification notification = new Notification.Builder(this, POWER_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(description)
                .setSmallIcon(R.drawable.battery_icon)
                .setAutoCancel(true)
                .build();
        notificationManager.notify(102, notification);
    }
}
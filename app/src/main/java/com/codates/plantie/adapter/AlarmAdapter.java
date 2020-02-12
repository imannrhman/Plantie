package com.codates.plantie.adapter;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Person;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;

import com.codates.plantie.R;
import com.codates.plantie.view.DetailTanaman;
import com.codates.plantie.view.Laporan;
import com.codates.plantie.view.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmAdapter extends BroadcastReceiver {
    private String CHANNEL_ID;

    @Override
    public void onReceive(Context context, Intent intent){
        MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_NOTIFICATION_URI);
        mediaPlayer.start();

//        private void showNotif() {
            String NOTIFICATION_CHANNEL_ID = "codates_plantie";
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                String channelName = "PLANTIE";
                int importance = NotificationManager.IMPORTANCE_HIGH;

                NotificationChannel mChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance);
                mChannel.enableLights(true);
                mChannel.setLightColor(Color.GREEN);
                mChannel.setShowBadge(true);
                mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                notificationManager.createNotificationChannel(mChannel);
            }

            Intent mIntent = new Intent(context, DetailTanaman.class);
            Bundle bundle = new Bundle();
            bundle.putString("fromnotif", "notif");
            mIntent.putExtras(bundle);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context,NOTIFICATION_CHANNEL_ID);
            builder.setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_logo_round)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_alarm_black_24dp))
                    .setTicker("menyiram")
                    .setAutoCancel(true)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setLights(Color.GREEN, 3000, 3000)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setContentTitle("Waktunya Menyiram Tanaman :)")
                    .setContentText("klik, untuk menceklis tugas menyiram ");

//            notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(115, builder.build());
//        }

    }

//    private void simple(){
//        Person person = new Person.Builder()
//                            .setName("PLANTIE")
////                            .setIcon(IconCompat.createWithResource(this, R.mipmap.ic_logo_round))
//                            .setImportant(true)
//                            .build();
//
//        new NotificationCompat.MessagingStyle(person)
//
//    }

}

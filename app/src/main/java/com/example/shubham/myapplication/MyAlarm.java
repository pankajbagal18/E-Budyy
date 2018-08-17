package com.example.shubham.myapplication;



import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class MyAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
        mediaPlayer.start();
        Toast.makeText(context,"Reminder..!!",Toast.LENGTH_LONG).show();
        //Notification(context, "Alarm");
    }

//    public void Notification(Context context, String message) {
//        // Set Notification Title
//        String strtitle = context.getString(R.string.notificationtitle);
//        // Open NotificationView Class on Notification Click
//        Intent intent = new Intent(context, NotificationView.class);
//        // Send data to NotificationView Class
//        intent.putExtra("title", strtitle);
//        intent.putExtra("text", message);
//        // Open NotificationView.java Activity
//        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//
//        // Create Notification using NotificationCompat.Builder
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(
//                context,"com.example.panks.reminderactivity")
//                // Set Icon
//                .setSmallIcon(R.drawable.ic_turned_in_not_black_24dp)
//                // Set Ticker Message
//                .setTicker(message)
//                // Set Title
//                .setContentTitle(context.getString(R.string.notificationtitle))
//                // Set Text
//                .setContentText(message)
//                // Add an Action Button below Notification
//                .addAction(R.drawable.ic_launcher_foreground, "Action Button", pIntent)
//                // Set PendingIntent into Notification
//                .setContentIntent(pIntent)
//                // Dismiss Notification
//                .setAutoCancel(true);
//
//        // Create Notification Manager
//        NotificationManager notificationmanager = (NotificationManager) context
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//        // Build Notification with Notification Manager
//        notificationmanager.notify(0, builder.build());
//    }

    /**
     Intent notificationIntent = new Intent(context, MainActivity.class);
     TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
     stackBuilder.addParentStack(MainActivity.class);
     stackBuilder.addNextIntent(notificationIntent);

     PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

     NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
     DatabaseHelper mydb = new DatabaseHelper(context);
     Cursor res = mydb.getAllData();
     res.moveToNext();
     Notification notification = builder.setContentTitle(res.getString(1))
     .setContentText(res.getString(2)+res.getString(3)+res.getString(4))
     .setTicker("REMINDER!")
     .setSmallIcon(R.mipmap.ic_launcher)
     .setContentIntent(pendingIntent).build();
     NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
     notificationManager.notify(0, notification);
     */

}

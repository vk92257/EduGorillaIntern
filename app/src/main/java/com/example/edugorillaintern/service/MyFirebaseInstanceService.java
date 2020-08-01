package com.example.edugorillaintern.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.edugorillaintern.MainActivity;
import com.example.edugorillaintern.MainActivity2;
import com.example.edugorillaintern.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseInstanceService extends FirebaseMessagingService {
   public static  int NOTIFICATION_ID = 1;
private String title,message;

        // to handle the FCM notification we have to override the onMessageReceived method
    @SuppressLint("LongLogTag")
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");

           String clickAction = remoteMessage.getNotification().getClickAction();
            Intent intent = new Intent(clickAction);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(body)
                .build();
        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        manager.notify(/*notification id*/0, notification);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,
                PendingIntent.FLAG_ONE_SHOT);



//        if (remoteMessage.getData()!=null && remoteMessage.getData().size() > 0) {
//            Log.d("MyFirebaseInstanceService", "Message data payload: " + remoteMessage.getData());
//            String title = remoteMessage.getData().get("title").toString();
//        }

//        // ...
//
//        // TODO(developer): Handle FCM messages here.
//        Log.d("Message", "From: " + remoteMessage.getFrom());
//
//        // Check if message contains a data payload.
//        if (remoteMessage.getData().size() > 0) {
//            Log.d("data", "Message data payload: " + remoteMessage.getData());
//
//            Map<String, String> data = remoteMessage.getData();
//
//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//               // scheduleJob();
//            } else {
//                // Handle message within 10 seconds
//                // calling my own created method with body and titel to better handle it
//
//             generatNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());
//            }
//
//        }
//
//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.d("data", "Message Notification Body: " + remoteMessage.getNotification().getBody());
//
//
//        }


    }

    private void generatNotification(String body, String title) {

  Intent intent = new Intent(MyFirebaseInstanceService.this, MainActivity.class);
  intent.putExtra("title",title);
   intent.putExtra("body",body);

        Log.i("", "generatNotification: =====================message is "+message+"   ==================  "+title);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


    // after clicking the notification this code will help to show and open and manupulate
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,
                PendingIntent.FLAG_ONE_SHOT);

        // for the notification sound
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //creating or generating the notification
        NotificationCompat.Builder notBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(uri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(NOTIFICATION_ID>1073741824){
            NOTIFICATION_ID=0;
        }
        notificationManager.notify(NOTIFICATION_ID++,notBuilder.build());

    }
}

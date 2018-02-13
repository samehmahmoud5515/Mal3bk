package com.logicdesigns.mohammed.mal3bklast.PushNotification;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.logicdesigns.mohammed.mal3bklast.LoginActivity;
import com.logicdesigns.mohammed.mal3bklast.MainContainer;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.StartActivity;

import org.json.JSONException;
import org.json.JSONObject;




/**
 * Created by logicDesigns on 10/4/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    int badgeCount = 0;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
           // handleNotification(remoteMessage.getNotification().getBody());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

  /*  private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
          //  Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
          //  pushNotification.putExtra("message", message);
         //   LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            //NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
          //  notificationUtils.playNotificationSound();
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(getApplicationContext())
                            .setSmallIcon(R.drawable.playgroundphoto)
                            .setContentTitle(message)
                            .setContentText(message);
            mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))   ;

            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            if (mPrefs.getString("userObject", "").length() > 0){
                Intent intent = new Intent(this, MainContainer.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(contentIntent);}

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, mBuilder.build());
        }else{
            // If the app is in background, firebase itself handles the notification
        }
    }*/

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");

            String type = data.getString("type");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);

            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);


            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("title",title);
                pushNotification.putExtra("timestamp",timestamp);
                pushNotification.putExtra("message", message);
                pushNotification.putExtra("type", type);

                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);


                // play notification sound
               // NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
               // notificationUtils.playNotificationSound();
            } else {

                // app is in background, show the notification in notification tray
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                if (mPrefs.getString("userObject", "").length() > 0) {
                 /*   NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(getApplicationContext())
                                    .setSmallIcon(R.drawable.playgroundphoto)
                                    .setContentTitle(message)
                                    .setContentText(message);
                    mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))   ;
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(1, mBuilder.build());*/

                    Intent viewIntent;
                    PendingIntent viewPendingIntent ;
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                        if (preferences.getString("userObject", "").length() > 0)
                        {
                            viewIntent = new Intent(getApplicationContext(), MainContainer.class);
                            viewPendingIntent =
                                    PendingIntent.getActivity(getApplicationContext(), 0, viewIntent, 0);
                        }
                        else {
                            viewIntent = new Intent(getApplicationContext(), LoginActivity.class);
                            viewPendingIntent =
                                    PendingIntent.getActivity(getApplicationContext(), 0, viewIntent, 0);
                        }

                    RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_push_layout);
                    contentView.setImageViewResource(R.id.image, R.drawable.playgroundphoto);
                    contentView.setTextViewText(R.id.title, title);
                    contentView.setTextViewText(R.id.text, message);
                    contentView.setTextViewText(R.id.time_stamp_tv,timestamp);

                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                            .setSmallIcon(R.drawable.playgroundphoto)
                            .setContent(contentView).setContentIntent(viewPendingIntent)
                            ;

                    Notification notification = mBuilder.build();
                    notification.flags |= Notification.FLAG_AUTO_CANCEL;
                    notification.defaults |= Notification.DEFAULT_SOUND;
                    notification.defaults |= Notification.DEFAULT_VIBRATE;
                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    notificationManager.notify(1, notification);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}
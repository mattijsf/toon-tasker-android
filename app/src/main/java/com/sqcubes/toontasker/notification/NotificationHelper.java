package com.sqcubes.toontasker.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import com.sqcubes.toon.api.exception.ToonLoginFailedException;
import com.sqcubes.toontasker.R;
import com.sqcubes.toontasker.activity.ToonLoginActivity;

public class NotificationHelper {
    public static final int LOGIN_REQUIRED_NOTIFICATION_ID = 1;
    public static final int LOGIN_FAILED_NOTIFICATION_ID = 2;

    public static void notifyForLoginRequired(Context context) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Toon login required!")
                        .setContentText("Tap here to setup your Toon device");


        android.content.Intent resultIntent = new android.content.Intent(context, ToonLoginActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(ToonLoginActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        notify(context, NotificationHelper.LOGIN_REQUIRED_NOTIFICATION_ID, mBuilder.build());
        toast(context, R.string.toast_message_login_required);
    }

    public static void notifyForLoginFailed(Context context, ToonLoginFailedException e) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(context.getString(R.string.app_name))
                        .setContentText(context.getString(R.string.notification_text_toon_login_failed))
                        .setSubText(e.getMessage());

        notify(context, NotificationHelper.LOGIN_FAILED_NOTIFICATION_ID, mBuilder.build());
        toast(context, R.string.toast_message_login_failed);
    }

    private static void notify(Context context, int notificationId, Notification notification) {
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notificationId, notification);
    }

    public static void clear(Context context, int notificationId) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationId);
    }

    private static void toast(Context context, int messageId){
        toast(context, context.getString(messageId));
    }
    private static void toast(Context context, String message){
        StringBuilder sb = new StringBuilder();
        sb.append(context.getString(R.string.app_name)).append(": ");
        sb.append(message);
        Toast.makeText(context, sb.toString(), Toast.LENGTH_LONG).show();
    }
}

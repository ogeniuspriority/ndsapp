package com.ogeniuspriority.nds.nds;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;

/**
 * Created by USER on 9/12/2017.
 */
public class generatePictureStyleNotification_BigText extends AsyncTask<String, Void, Bitmap> {

    private Context mContext;
    private String title, message, imageUrl, BigText;
    private int notifNber;

    public generatePictureStyleNotification_BigText(Context context,int notifNber, String title, String message, String BigText) {
        super();
        this.mContext = context;
        this.title = title;
        this.message = message;
        this.imageUrl = imageUrl;
        this.BigText = BigText;
        this.notifNber=notifNber;

    }

    @Override
    protected Bitmap doInBackground(String... params) {


        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);

        Intent intent = new Intent(mContext, NDS_messages.class);
        intent.putExtra("key", "value");
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 100, intent, PendingIntent.FLAG_ONE_SHOT);
        final Notification.Builder builder = new Notification.Builder(mContext);

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notif = new Notification.Builder(mContext)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.neza_000_we)
                .setStyle(new Notification.BigTextStyle(builder)
                        .bigText(BigText))
                .build();
        notif.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(notifNber, notif);
        //-----------
        /*
        Notification notif = new Notification.Builder(mContext)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.neza_000_we)
                .setLargeIcon(result)
                .setStyle(new Notification.BigPictureStyle().bigPicture(result)).
                        setStyle(new Notification.BigTextStyle(builder)
                                .bigText(BigText))
                                */
    }
}
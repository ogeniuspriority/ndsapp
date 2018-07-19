package com.ogeniuspriority.nds.nds;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by USER on 9/12/2017.
 */
 class sendNotification extends AsyncTask<String, Void, Bitmap> {

    Context ctx;
    String message;

    public sendNotification(Context context) {
        super();
        this.ctx = context;
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        InputStream in;
        message = params[0] + params[1];
        try {

            URL url = new URL(params[2]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            in = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(in);
            return myBitmap;




        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {

        super.onPostExecute(result);
        try {
            NotificationManager notificationManager = (NotificationManager) ctx
                    .getSystemService(Context.NOTIFICATION_SERVICE);

            Intent intent = new Intent(ctx, NDS_messages.class);
            intent.putExtra("isFromBadge", false);


            Notification notification = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                notification = new Notification.Builder(ctx)
                        .setContentTitle(
                                ctx.getResources().getString(R.string.app_name))
                        .setContentText(message)
                        .setSmallIcon(R.mipmap.neza_000_we)
                        .setLargeIcon(result).build();
            }

            // hide the notification after its selected
            notification.flags |= Notification.FLAG_AUTO_CANCEL;

            notificationManager.notify(1, notification);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
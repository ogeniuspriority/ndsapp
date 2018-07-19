package com.ogeniuspriority.nds.nds;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by USER on 9/12/2017.
 *
 *  new generatePictureStyleNotification(NDS_main.this,"Cyuma is the best","iafiafas fsihfsf ",Config.IP_ADDRESS.toString()+"/nds_ogenius/uploaded_images/20170626124833.png","ushfu8sfsfs sfiusgfs fsibf sihfbvs fibs fsbivf sfuvsf").execute();
 new generatePictureStyleNotification_BigText(NDS_main.this,"Big text is the best","iafiafas fsihfsf ","ushfu8sfsfs sfiusgfs fsibf sihfbvs fibs fsbivf sfuvsf").execute();

 */
public class generatePictureStyleNotification extends AsyncTask<String, Void, Bitmap> {

    private Context mContext;
    private String title, message, imageUrl, BigText;
    private int notifNber;

    public generatePictureStyleNotification(Context context,int notifNber, String title, String message, String imageUrl, String BigText) {
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

        InputStream in;
        try {
            URL url = new URL(this.imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            in = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(in);
            Log.w("Image fail","failed "+myBitmap.toString());
            return myBitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }
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
                .setLargeIcon(result)
                .setStyle(new Notification.BigPictureStyle().bigPicture(result))
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
package com.ogeniuspriority.nds.nds;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by USER on 9/12/2017.
 * <p/>
 * new generatePictureStyleNotification(NDS_main.this,"Cyuma is the best","iafiafas fsihfsf ",Config.IP_ADDRESS.toString()+"/nds_ogenius/uploaded_images/20170626124833.png","ushfu8sfsfs sfiusgfs fsibf sihfbvs fibs fsbivf sfuvsf").execute();
 * new generatePictureStyleNotification_BigText(NDS_main.this,"Big text is the best","iafiafas fsihfsf ","ushfu8sfsfs sfiusgfs fsibf sihfbvs fibs fsbivf sfuvsf").execute();
 */
public class generatePictureStyleNotification_big_text_with_large_icon extends AsyncTask<String, Void, Bitmap> {

    private Context mContext;
    private String title, message, imageUrl, BigText;
    private int notifNber;
    private Class cd;
    private String keyTag, keyValue;

    public generatePictureStyleNotification_big_text_with_large_icon(Context context, int notifNber, String title, String message, String imageUrl, String BigText, Class cd, String keyTag, String keyValue) {
        super();
        this.mContext = context;
        this.title = title;
        this.message = message;
        this.imageUrl = imageUrl;
        this.BigText = BigText;
        this.notifNber = notifNber;
        this.cd = cd;
        this.keyTag = keyTag;
        this.keyValue = keyValue;
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
//            Log.w("Image fail", "failed " + myBitmap.toString());
            return myBitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Bitmap bm = BitmapFactory.decodeResource(Resources.getSystem(), R.mipmap.neza_000_we);
            return bm;

        } catch (IOException e) {
            e.printStackTrace();
            Bitmap bm = BitmapFactory.decodeResource(Resources.getSystem(), R.mipmap.neza_000_we);
            return bm;

        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);

        Intent intent = new Intent(mContext, cd);
        intent.putExtra("trick", notifNber);
        intent.putExtra(keyTag, keyValue);


        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, notifNber, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        final Notification.Builder builder = new Notification.Builder(mContext);

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notif = new Notification.Builder(mContext)
                .setContentIntent(pendingIntent)
                .setContentTitle("NDS: \""+title+"\"")
                .setContentText(message)
                .setSmallIcon(R.mipmap.neza_000_we)
                .setLargeIcon(result)
                .setStyle(new Notification.BigTextStyle(builder)
                        .bigText(BigText))
                .build();
        notif.sound = Uri.parse("android.resource://com.ogeniuspriority.nds.nds/raw/msgreceived");
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
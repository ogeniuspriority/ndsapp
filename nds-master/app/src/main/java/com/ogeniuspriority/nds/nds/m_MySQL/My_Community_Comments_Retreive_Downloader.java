package com.ogeniuspriority.nds.nds.m_MySQL;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

/**
 * Created by USER on 2/6/2017.
 */
public class My_Community_Comments_Retreive_Downloader extends AsyncTask<Void, Void, String> {
    Context c;
    String urlAddress;
    ListView forum_whisper;
    SwipeRefreshLayout swipe_view;
    //----------------
    String PostID;
    String LastCommentId;
    String Orientation;

    public My_Community_Comments_Retreive_Downloader(Context c, String urlAddress, ListView forum_whisper, SwipeRefreshLayout swipe_view,
                                                     String PostID,String LastCommentId,String Orientation  ) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.forum_whisper = forum_whisper;
        this.swipe_view = swipe_view;
        //---------------
        this.PostID=PostID;
        this.LastCommentId=LastCommentId;
        this.Orientation=Orientation;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (!swipe_view.isRefreshing()) {
            swipe_view.setRefreshing(true);
            Log.w("URl", "" + this.urlAddress);
        }
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {
            return this.downloadData();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String jsonData) {
        super.onPostExecute(jsonData);

        if (jsonData == null) {
            swipe_view.setRefreshing(false);
            Toast.makeText(c, "Unsuccessful, No Comments retreived!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(c, "Loaded!", Toast.LENGTH_SHORT).show();
            //parse the json--
            new My_Community_Comments_DataParser(c, jsonData, forum_whisper, swipe_view).execute();

        }
    }

    private String downloadData() throws UnsupportedEncodingException {

        HttpURLConnection con = My_Community_Comments_Retreive_Connector.connect(urlAddress,PostID,LastCommentId,Orientation);
        if (con == null) {
            return null;
        }
        try {
            InputStream is = new BufferedInputStream(con.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            StringBuffer jsonData = new StringBuffer();
            while ((line = br.readLine()) != null) {
                jsonData.append(line + "\n");
            }
            // Send post request

            br.close();
            is.close();


            return jsonData.toString();


        } catch (IOException e) {
            e.printStackTrace();


        }


        return null;
    }
}

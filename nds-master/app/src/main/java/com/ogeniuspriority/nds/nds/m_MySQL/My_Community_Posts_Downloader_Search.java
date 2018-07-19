package com.ogeniuspriority.nds.nds.m_MySQL;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ogeniuspriority.nds.nds.Urubuga_Services;

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
public class My_Community_Posts_Downloader_Search extends AsyncTask<Void, Void, String> {
    Context c;
    String urlAddress;
    ListView forum_whisper;
    SwipeRefreshLayout swipe_view;
    String keyword;
    String posterId;
    EditText myedit;

    public My_Community_Posts_Downloader_Search(Context c, String urlAddress, ListView forum_whisper, SwipeRefreshLayout swipe_view, String keyword, String posterId,EditText myedit) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.forum_whisper = forum_whisper;
        this.swipe_view = swipe_view;
        this.keyword=keyword;
        this.posterId=posterId;
        this.myedit=myedit;
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

        //----------
        if (jsonData == null) {
            swipe_view.setRefreshing(false);
            Toast.makeText(c, "Unsuccessful, No posts retreived!", Toast.LENGTH_SHORT).show();
        } else {
            //parse the json--
           // Toast.makeText(c, ""+jsonData, Toast.LENGTH_LONG).show();
            //--hide keyboard
            Urubuga_Services.lastId_Of_post_Retreived = 0;
            Urubuga_Services.firstId_Of_post_Retreived = 0;

            new My_Community_Posts_DataParser(c, jsonData, forum_whisper, swipe_view).execute();

        }
    }

    private String downloadData() throws UnsupportedEncodingException {

        HttpURLConnection con = My_Community_Posts_Connector_Search.connect(urlAddress,keyword,posterId);
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

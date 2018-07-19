package com.ogeniuspriority.nds.nds.m_MySQL;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.ogeniuspriority.nds.nds.NDS_db_adapter;

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
public class My_Community_Single_Post_Comment_Downloader extends AsyncTask<Void, Void, String> {
    Context c;
    String urlAddress;
    ListView forum_whisper;
    String firstId;
    String posterId;
    NDS_db_adapter db;
    ProgressDialog progress;
    String parentPostId;

    public My_Community_Single_Post_Comment_Downloader(Context c, String urlAddress, ListView forum_whisper, String firstId, String posterId, ProgressDialog progress, String parentPostId) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.forum_whisper = forum_whisper;
        this.firstId = firstId;
        this.posterId = posterId;
        this.progress = progress;
        this.parentPostId = parentPostId;
        db = new NDS_db_adapter(c);
        db.openToWrite();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

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
            Toast.makeText(c, "Failed! No internet!", Toast.LENGTH_SHORT).show();
            progress.dismiss();

        } else {
            progress.dismiss();
            new My_Community_Single_Post_DataParser(c, jsonData, forum_whisper).execute();

        }
    }

    private String downloadData() throws UnsupportedEncodingException {

        HttpURLConnection con = My_Community_Single_Post_Connector.connect(urlAddress, firstId, posterId,parentPostId);
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

package com.ogeniuspriority.nds.nds.m_MySQL;

import android.content.Context;
import android.os.AsyncTask;

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
public class My_COMMENTS_TAGS_Downloader extends AsyncTask<Void, Void, String> {
    Context c;
    String streamOfIds_queries;
    String streamOfOrients_queries;
    String streamOfSummary_queries;
    String MYRemoteId;
    String magicUrl;

    public My_COMMENTS_TAGS_Downloader(Context c, String magicUrl , String streamOfIds_queries, String streamOfOrients_queries, String streamOfSummary_queries, String MYRemoteId) {
        this.c = c;
        this.streamOfIds_queries = streamOfIds_queries;
        this.streamOfOrients_queries = streamOfOrients_queries;
        this.streamOfSummary_queries = streamOfSummary_queries;
        this.MYRemoteId = MYRemoteId;
        this.magicUrl=magicUrl;
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

        } else {
            //parse the json--
            new My_COMMENTS_TAGS_data_parser(c, jsonData).execute();

        }
    }

    private String downloadData() throws UnsupportedEncodingException {

        HttpURLConnection con = My_COMMENTS_TAGS_Connector.connect(magicUrl,streamOfIds_queries,streamOfOrients_queries,streamOfSummary_queries,MYRemoteId);
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

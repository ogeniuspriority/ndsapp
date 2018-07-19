package com.ogeniuspriority.nds.nds.m_MySQL;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

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
public class My_Community_Posts_UpdateViews extends AsyncTask<Void, Void, String> {
    Context c;
    String urlAddress;
    String postId;

    //-----------------
    View v;
    int position;

    public My_Community_Posts_UpdateViews(Context c, String urlAddress, String postId) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.postId=postId;


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

        //Toast.makeText(c,"Done!"+jsonData,Toast.LENGTH_SHORT).show();

        if (jsonData == null) {

        } else {


        }
    }

    private String downloadData() throws UnsupportedEncodingException {

        HttpURLConnection con = My_Community_Posts_Connector_UpdateViews.connect(urlAddress,postId);
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

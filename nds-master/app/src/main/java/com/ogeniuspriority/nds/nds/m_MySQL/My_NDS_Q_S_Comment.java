package com.ogeniuspriority.nds.nds.m_MySQL;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListView;

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
public class My_NDS_Q_S_Comment extends AsyncTask<Void, Void, String> {
    Context c;
    String urlAddress;
    String comment;
    String Commentator_id;
    String Parent_Post_id;
    String QueryOrientation;
    String Last_id;
    //---
    ListView forum_whisper;
    SwipeRefreshLayout swipe_view;


    public My_NDS_Q_S_Comment(Context c, String urlAddress, String comment, String Commentator_id, String Parent_Post_id, ListView forum_whisper,String QueryOrientation,String Last_id) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.comment = comment;
        this.Commentator_id = Commentator_id;
        this.Parent_Post_id = Parent_Post_id;
        this.QueryOrientation=QueryOrientation;
        this.Last_id=Last_id;
        //-------------
        this.forum_whisper = forum_whisper;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(Void... voids) {

        try {
            return this.sendMYComment();
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
            //------------------Load new Comments---
            new My_NDS_Q_S_DataParser(c,jsonData).execute();
             Log.w("jsonData",""+jsonData);

        }
        //Toast.makeText(c, "Comment sent!"+jsonData, Toast.LENGTH_SHORT).show();
    }

    private String sendMYComment() throws UnsupportedEncodingException {

        HttpURLConnection con = My_NDS_Q_S_Comment_Connector.connect(urlAddress, this.comment, this.Commentator_id, this.Parent_Post_id,this.QueryOrientation,this.Last_id);
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

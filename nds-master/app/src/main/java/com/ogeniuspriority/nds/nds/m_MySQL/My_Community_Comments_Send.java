package com.ogeniuspriority.nds.nds.m_MySQL;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;

import com.ogeniuspriority.nds.nds.Config;
import com.ogeniuspriority.nds.nds.NDS_posts_comments;

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
public class My_Community_Comments_Send extends AsyncTask<Void, Void, String> {
    Context c;
    String urlAddress;
    String comment;
    String Commentator_id;
    String Parent_Post_id;
    //---
    ListView forum_whisper;
    SwipeRefreshLayout swipe_view;


    public My_Community_Comments_Send(Context c, String urlAddress, String comment, String Commentator_id, String Parent_Post_id, ListView forum_whisper, SwipeRefreshLayout swipe_view) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.comment = comment;
        this.Commentator_id = Commentator_id;
        this.Parent_Post_id = Parent_Post_id;
        //-------------
        this.forum_whisper = forum_whisper;
        this.swipe_view = swipe_view;
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
            Toast.makeText(c, "Failed! No internet!", Toast.LENGTH_SHORT).show();
        } else {
            //parse the json--
            Toast.makeText(c, "Comment sent!", Toast.LENGTH_SHORT).show();
            NDS_posts_comments.comment_field.setText("");
            InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(NDS_posts_comments.comment_field.getWindowToken(), 0);
            //------------------Load new Comments---
            new My_Community_Comments_Retreive_Downloader(c, Config.NDS_LOAD_ALLL_RELATED_COMMENTS, forum_whisper, swipe_view, Parent_Post_id, "", "up").execute();


        }
        //Toast.makeText(c, "Comment sent!"+jsonData, Toast.LENGTH_SHORT).show();
    }

    private String sendMYComment() throws UnsupportedEncodingException {

        HttpURLConnection con = My_Community_Comments_Connector.connect(urlAddress, this.comment, this.Commentator_id, this.Parent_Post_id);
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

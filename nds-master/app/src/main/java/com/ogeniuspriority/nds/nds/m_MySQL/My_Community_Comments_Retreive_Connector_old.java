package com.ogeniuspriority.nds.nds.m_MySQL;

import com.ogeniuspriority.nds.nds.NDS_posts_comments;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by USER on 2/6/2017.
 */
public class My_Community_Comments_Retreive_Connector_old {
    public static HttpURLConnection connect(String urlAddress,String PostId) {

        try {

            URL url = new URL(urlAddress);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //--con properties-
            String data = "";
            data = URLEncoder.encode("PostId", "UTF-8")
                    + "=" + URLEncoder.encode(PostId, "UTF-8");
            data += "&" + URLEncoder.encode("LastComment_id", "UTF-8") + "="
                    + URLEncoder.encode(""+NDS_posts_comments.lastId_Of_post_comment_Retreived, "UTF-8");
            data += "&" + URLEncoder.encode("NewCOmmentId", "UTF-8") + "="
                    + URLEncoder.encode(""+NDS_posts_comments.firstId_Of_post_comment_Retreived, "UTF-8");
            String lastResort = data;

            String urlParameters = lastResort;
            con.setRequestMethod("POST");
            con.setConnectTimeout(15000);
            con.setReadTimeout(15000);
            con.setDoInput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();


            return con;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

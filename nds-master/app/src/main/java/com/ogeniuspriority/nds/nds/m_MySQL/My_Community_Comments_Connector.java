package com.ogeniuspriority.nds.nds.m_MySQL;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by USER on 2/6/2017.
 */
public class My_Community_Comments_Connector {
    public static HttpURLConnection connect(String urlAddress,String comment,String Commentator_id,String Parent_Post_id) {

        try {

            URL url = new URL(urlAddress);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //--con properties-
            String data = "";
            data = URLEncoder.encode("comment", "UTF-8")
                    + "=" + URLEncoder.encode(comment, "UTF-8");
            data += "&" + URLEncoder.encode("Commentator_id", "UTF-8") + "="
                    + URLEncoder.encode(Commentator_id, "UTF-8");
            data += "&" + URLEncoder.encode("Parent_Post_id", "UTF-8") + "="
                    + URLEncoder.encode(Parent_Post_id, "UTF-8");
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

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
public class My_Community_Suggestion_Boxes_Delete_Intruder_Connector {
    public static HttpURLConnection connect(String urlAddress,String RemoteId) {

        try {

            URL url = new URL(urlAddress);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //--con properties-
            String data = "";
            data = URLEncoder.encode("oka", "UTF-8")
                    + "=" + URLEncoder.encode("", "UTF-8");
            data += "&" + URLEncoder.encode("RemoteId", "UTF-8") + "="
                    + URLEncoder.encode(RemoteId, "UTF-8");

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

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
public class My_Community_ChangePhoneNumber_Connector {
    public static HttpURLConnection connect(String urlAddress,String MyRemoteId, String theNumber, String theCode) {

        try {

            URL url = new URL(urlAddress);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //--con properties-
            String data = "";
            data = URLEncoder.encode("MyRemoteId", "UTF-8")
                    + "=" + URLEncoder.encode(MyRemoteId, "UTF-8");
            data += "&" + URLEncoder.encode("theNumber", "UTF-8") + "="
                    + URLEncoder.encode(theNumber, "UTF-8");
            data += "&" + URLEncoder.encode("theCode", "UTF-8") + "="
                    + URLEncoder.encode(theCode, "UTF-8");
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

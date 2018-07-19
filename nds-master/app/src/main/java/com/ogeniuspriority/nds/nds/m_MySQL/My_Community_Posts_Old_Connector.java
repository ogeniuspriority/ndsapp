package com.ogeniuspriority.nds.nds.m_MySQL;

import com.ogeniuspriority.nds.nds.Urubuga_Services;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by USER on 2/6/2017.
 */
public class My_Community_Posts_Old_Connector {
    public static HttpURLConnection connect(String urlAddress,String OldId) {

        try {

            URL url = new URL(urlAddress);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //--con properties-
            String data = "";
            data = URLEncoder.encode("select_directive", "UTF-8")
                    + "=" + URLEncoder.encode(Urubuga_Services.whichTypeOfData_Keys[Urubuga_Services.whichTypeOfData-1], "UTF-8");
            data += "&" + URLEncoder.encode("OldId", "UTF-8") + "="
                    + URLEncoder.encode(""+Urubuga_Services.lastId_Of_post_Retreived, "UTF-8");
            data += "&" + URLEncoder.encode("NewId", "UTF-8") + "="
                    + URLEncoder.encode(""+Urubuga_Services.firstId_Of_post_Retreived, "UTF-8");
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

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
public class My_Q_S_Comments_Connector {
    public static HttpURLConnection connect(String magicUrl, String streamOfIds_queries, String streamOfOrients_queries, String streamOfSummary_queries, String MYRemoteId) {

        try {

            URL url = new URL(magicUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            // Log.w("0000",""+Urubuga_Services.whichTypeOfData_Keys[Urubuga_Services.whichTypeOfData-1]);
            //--con properties-
            String data = "";
            data = URLEncoder.encode("streamOfIds_queries", "UTF-8")
                    + "=" + URLEncoder.encode(streamOfIds_queries, "UTF-8");
            data = data +"&"+ URLEncoder.encode("streamOfOrients_queries", "UTF-8")
                    + "=" + URLEncoder.encode(streamOfOrients_queries, "UTF-8");
            data = data +"&"+ URLEncoder.encode("streamOfSummary_queries", "UTF-8")
                    + "=" + URLEncoder.encode(streamOfSummary_queries, "UTF-8");
            data = data +"&"+ URLEncoder.encode("MYRemoteId", "UTF-8")
                    + "=" + URLEncoder.encode(MYRemoteId, "UTF-8");


            String lastResort = data;
            String urlParameters = lastResort;
            con.setRequestMethod("POST");
            con.setConnectTimeout(15000);
            con.setReadTimeout(15000);
            con.setDoInput(true);
            DataOutputStream wr;
            try {
                 wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();
            }catch (RuntimeException h){

            }



            return con;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

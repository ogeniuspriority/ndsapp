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
public class My_Community_Messages_LOad_My_Remote_suggestions_Connector {
    public static HttpURLConnection connect(String urlAddress, String myRemoTeId, String theLastSuggestRemoteId) {

        try {

            URL url = new URL(urlAddress);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //--con properties-
            String data = "";
            data = URLEncoder.encode("myRemoTeId", "UTF-8")
                    + "=" + URLEncoder.encode(myRemoTeId, "UTF-8");

            data += "&" + URLEncoder.encode("theLastSuggestRemoteId", "UTF-8") + "="
                    + URLEncoder.encode(theLastSuggestRemoteId, "UTF-8");
            /*

            data += "&" + URLEncoder.encode("Parent_Post_id", "UTF-8") + "="
                    + URLEncoder.encode(Parent_Post_id, "UTF-8");
                      */

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
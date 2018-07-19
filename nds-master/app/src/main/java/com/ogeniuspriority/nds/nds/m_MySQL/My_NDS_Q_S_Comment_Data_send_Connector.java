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
public class My_NDS_Q_S_Comment_Data_send_Connector {
    public static HttpURLConnection connect(String urlAddress,String comment_data,String commentator_id,String parent_post_id,String query_orientation,String last_id) {

        try {

            URL url = new URL(urlAddress);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            //Log.w("0000",""+Urubuga_Services.whichTypeOfData_Keys[Urubuga_Services.whichTypeOfData-1]);

            //--con properties-
            String data = "";
            data = URLEncoder.encode("comment_data", "UTF-8")
                    + "=" + URLEncoder.encode(comment_data, "UTF-8");
            data += "&" + URLEncoder.encode("commentator_id", "UTF-8") + "="
                    + URLEncoder.encode(commentator_id, "UTF-8");
            data += "&" + URLEncoder.encode("parent_post_id", "UTF-8") + "="
                    + URLEncoder.encode(parent_post_id, "UTF-8");
            data += "&" + URLEncoder.encode("query_orientation", "UTF-8") + "="
                    + URLEncoder.encode(query_orientation, "UTF-8");
            data += "&" + URLEncoder.encode("last_id", "UTF-8") + "="
                    + URLEncoder.encode(last_id, "UTF-8");

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

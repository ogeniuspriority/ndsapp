package com.ogeniuspriority.nds.nds.m_MySQL;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

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
public class My_Community_App_Get_Avatar extends AsyncTask<Void, Void, String> {
    Context c;
    String urlAddress;
    String notif_originator_remote_id;
    String notif_type_orient;


    public My_Community_App_Get_Avatar(Context c, String urlAddress, String notif_originator_remote_id, String notif_type_orient) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.notif_originator_remote_id = notif_originator_remote_id;
        this.notif_type_orient = notif_type_orient;


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
            try {
                Toast.makeText(c, "Failed! No internet!", Toast.LENGTH_SHORT).show();
            }catch(RuntimeException didi){

            }
        } else {


        }
        //Toast.makeText(c, "Comment sent!"+jsonData, Toast.LENGTH_SHORT).show();
    }

    private String sendMYComment() throws UnsupportedEncodingException {

        HttpURLConnection con = My_Community_App_Get_Avatar_Connector.connect(urlAddress, notif_originator_remote_id, notif_type_orient);
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

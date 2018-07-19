package com.ogeniuspriority.nds.nds.m_MySQL;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
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
public class My_Community_Creds_GetACtivarionCode extends AsyncTask<Void, Void, String> {
    Context c;
    String urlAddress;
    String MyRemoteId;
    String number_field;
    View bnm;




    public My_Community_Creds_GetACtivarionCode(Context c, String urlAddress, String MyRemoteId, String number_field,View bnm) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.MyRemoteId=MyRemoteId;
        this.number_field=number_field;
        this.bnm=bnm;


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
           String myTr = jsonData.replaceAll("\\s", "");
            if(myTr.equalsIgnoreCase("1000")){
                bnm.setVisibility(View.GONE);
                Toast.makeText(c,"Find the code in the inbox!",Toast.LENGTH_SHORT).show();

            }else{
                Log.w("wwww",""+jsonData);
            }


        }
        //Toast.makeText(c, "Comment sent!"+jsonData, Toast.LENGTH_SHORT).show();
    }

    private String sendMYComment() throws UnsupportedEncodingException {

        HttpURLConnection con = My_Community_GetActivationCode_Connector.connect(urlAddress, MyRemoteId, number_field);
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

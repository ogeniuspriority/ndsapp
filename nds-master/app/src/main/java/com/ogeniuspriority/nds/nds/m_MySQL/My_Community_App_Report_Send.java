package com.ogeniuspriority.nds.nds.m_MySQL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ogeniuspriority.nds.nds.NDS_main;

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
public class My_Community_App_Report_Send extends AsyncTask<Void, Void, String> {
    Context c;
    String urlAddress;
    String MyRemoteId;
    String reportData;
    Activity act;



    public My_Community_App_Report_Send(Context c, String urlAddress, String MyRemoteId, String reportData,Activity act) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.MyRemoteId=MyRemoteId;
        this.reportData=reportData;
        this.act=act;


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
                Toast.makeText(c,"Report received successfully!",Toast.LENGTH_SHORT).show();
                //act.finish();
                Intent intent = new Intent(c, NDS_main.class);
                //intent.putExtra("keyId", "0");
                act.startActivity(intent);
            }else{
                Log.w("wwww",""+jsonData);
            }


        }
        //Toast.makeText(c, "Comment sent!"+jsonData, Toast.LENGTH_SHORT).show();
    }

    private String sendMYComment() throws UnsupportedEncodingException {

        HttpURLConnection con = My_Community_App_report_send_Connector.connect(urlAddress, MyRemoteId, reportData);
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

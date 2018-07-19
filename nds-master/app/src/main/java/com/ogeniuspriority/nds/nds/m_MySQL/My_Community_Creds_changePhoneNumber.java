package com.ogeniuspriority.nds.nds.m_MySQL;

import android.app.Activity;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.ogeniuspriority.nds.nds.NDS_db_adapter;

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
public class My_Community_Creds_changePhoneNumber extends AsyncTask<Void, Void, String> {
    Context c;
    String urlAddress;
    String MyRemoteId;
    String theNumber;
    String theCode;
    Activity act;
    AlertDialog pmI;
    NDS_db_adapter db;


    public My_Community_Creds_changePhoneNumber(Context c, String urlAddress, String MyRemoteId, String theNumber, String theCode, AlertDialog pmI, Activity act) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.MyRemoteId = MyRemoteId;
        this.theNumber = theNumber;
        this.theCode = theCode;
        this.act = act;
        this.pmI=pmI;
        db = new NDS_db_adapter(c);
        db.openToWrite();


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
            if (myTr.equalsIgnoreCase("itisDoneNum")) {
                if (db.Update_NDS_USER_CRED(MyRemoteId, "tel_no", theNumber)) {
                    Toast.makeText(c, "Phone number updated successfully!", Toast.LENGTH_SHORT).show();
                    pmI.dismiss();
                    act.recreate();
                }
            } else {
                Log.w("wwww", "" + jsonData);
            }


        }
        //Toast.makeText(c, "Comment sent!"+jsonData, Toast.LENGTH_SHORT).show();
    }

    private String sendMYComment() throws UnsupportedEncodingException {

        HttpURLConnection con = My_Community_ChangePhoneNumber_Connector.connect(urlAddress, MyRemoteId, theNumber, theCode);
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

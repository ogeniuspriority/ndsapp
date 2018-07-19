package com.ogeniuspriority.nds.nds.m_MySQL;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

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
public class My_Community_Messages_Load_My_Remote_suggestions extends AsyncTask<Void, Void, String> {
    Context c;
    String urlAddress;
    //----------------
    String myRemoTeId;
    String theLastSuggestRemoteId;
    ListView message_answers;


    public My_Community_Messages_Load_My_Remote_suggestions(Context c, String urlAddress,String myRemoTeId,String theLastSuggestRemoteId,ListView message_answers) {
        this.c = c;
        this.urlAddress = urlAddress;
        //---------------
        this.myRemoTeId=myRemoTeId;
        this.theLastSuggestRemoteId=theLastSuggestRemoteId;
        this.message_answers=message_answers;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(Void... voids) {

        try {
            return this.downloadData();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String jsonData) {
        super.onPostExecute(jsonData);

        if (jsonData == null) {

        } else {
            Log.w("AllMyRemoteSuggestions",""+jsonData);
            //------Supply them in array---------------
            new My_NDS_suggestions_data_parser(c,jsonData,message_answers).execute();

        }
    }

    private String downloadData() throws UnsupportedEncodingException {

        HttpURLConnection con = My_Community_Messages_LOad_My_Remote_suggestions_Connector.connect(urlAddress,myRemoTeId,theLastSuggestRemoteId);
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

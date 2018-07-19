package com.ogeniuspriority.nds.nds.m_MySQL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.ogeniuspriority.nds.nds.NDS_db_adapter;
import com.ogeniuspriority.nds.nds.NDS_main;
import com.ogeniuspriority.nds.nds.NDS_registration_activation;
import com.ogeniuspriority.nds.nds.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
public class NDS_log_in_using_creds extends AsyncTask<Void, Void, String> {
    Context c;
    String urlAddress;
    String MyRemoteId;
    String NewNames;
    NDS_db_adapter db;
    String username;
    String password;
    private ProgressDialog dialog;
    PopupWindow odi0weie;

    String NDS_userId[];
    String NDS_userEmail[];
    String NDS_userTel[];
    String NDS_userAvatar[];
    String NDS_userRegdate[];
    String NDS_userNames[];


    public NDS_log_in_using_creds(Context c, String urlAddress, String username, String password, PopupWindow odi0weie) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.username = username;
        this.password = password;
        db = new NDS_db_adapter(c);
        db.openToWrite();
        dialog = new ProgressDialog(c);
        this.odi0weie = odi0weie;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        odi0weie.dismiss();
        dialog.setMessage("Signing in...");
        dialog.show();


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
        dialog.dismiss();
        if (jsonData == null) {
            Toast.makeText(c, "Failed! No internet!", Toast.LENGTH_SHORT).show();
        } else {

            Log.w("jsonDatabb", "" + jsonData);
            if (jsonData.equalsIgnoreCase("failed~")) {
                Toast.makeText(c, "Invalid credentials", Toast.LENGTH_SHORT).show();


            } else {
                //----------------------
                try {
                    JSONObject jsonMsgRead = new JSONObject(
                            jsonData);

                    //-------------------------------------

                    //--------
                    if (jsonMsgRead != null) {
                        JSONArray dataJsonArr1 = null;
                        try {
                            dataJsonArr1 = (JSONArray) jsonMsgRead
                                    .get("NDS_data_reg");
                            //--------------------------
                            NDS_userId = new String[dataJsonArr1.length()];
                            NDS_userEmail = new String[dataJsonArr1.length()];
                            NDS_userTel = new String[dataJsonArr1.length()];
                            NDS_userAvatar= new String[dataJsonArr1.length()];
                            NDS_userRegdate = new String[dataJsonArr1.length()];
                            NDS_userNames = new String[dataJsonArr1.length()];
                            // String NDS_userNames[] = new String[dataJsonArr1.length()];
                            //------------------
                            for (int i = 0; i < dataJsonArr1.length(); i++) {
                                //--------------------------
                                NDS_userId[i] = null;
                                try {
                                    NDS_userId[i] = dataJsonArr1
                                            .getJSONObject(
                                                    i)
                                            .getString(
                                                    "ogenius_nds_db_normal_users_id")
                                            .toString();
                                } catch (JSONException e) {
                                    // TODO Auto-generated
                                    // catch block
                                    e.printStackTrace();
                                }
                                NDS_userEmail[i] = null;
                                try {
                                    NDS_userEmail[i] = dataJsonArr1
                                            .getJSONObject(
                                                    i)
                                            .getString(
                                                    "ogenius_nds_db_normal_users_email")
                                            .toString();
                                } catch (JSONException e) {
                                    // TODO Auto-generated
                                    // catch block
                                    e.printStackTrace();
                                }
                                NDS_userTel[i] = null;
                                try {
                                    NDS_userTel[i] = dataJsonArr1
                                            .getJSONObject(
                                                    i)
                                            .getString(
                                                    "ogenius_nds_db_normal_users_tel")
                                            .toString();
                                } catch (JSONException e) {
                                    // TODO Auto-generated
                                    // catch block
                                    e.printStackTrace();
                                }
                                NDS_userAvatar[i] = null;
                                try {
                                    NDS_userAvatar[i] = dataJsonArr1
                                            .getJSONObject(
                                                    i)
                                            .getString(
                                                    "ogenius_nds_db_normal_users_avatar")
                                            .toString();
                                } catch (JSONException e) {
                                    // TODO Auto-generated
                                    // catch block
                                    e.printStackTrace();
                                }
                                NDS_userRegdate[i] = null;
                                try {
                                    NDS_userRegdate[i] = dataJsonArr1
                                            .getJSONObject(
                                                    i)
                                            .getString(
                                                    "ogenius_nds_db_normal_users_regdate")
                                            .toString();
                                } catch (JSONException e) {
                                    // TODO Auto-generated
                                    // catch block
                                    e.printStackTrace();
                                }
                                NDS_userNames[i] = null;
                                try {
                                    NDS_userNames[i] = dataJsonArr1
                                            .getJSONObject(
                                                    i)
                                            .getString(
                                                    "ogenius_nds_db_normal_users_names")
                                            .toString();
                                } catch (JSONException e) {
                                    // TODO Auto-generated
                                    // catch block
                                    e.printStackTrace();
                                }


                            }

                            ((Activity) c).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                    db.openToRead();
                                    //----------------------
                                    if (db.save_cred_locally(NDS_userNames[0], NDS_userTel[0], NDS_userAvatar[0], NDS_userRegdate[0], "", "", NDS_userId[0])) {
                                        Intent myIntent = new Intent(c, NDS_main.class);
                                        //myIntent.putExtra("key", value); //Optional parameters
                                        c.startActivity(myIntent);


                                    }
                                }
                            });


                            //--------------------
                        } catch (JSONException e) {
                            // TODO Auto-generated catch
                            // block
                            e.printStackTrace();

                        }

                    }


                    //--------------------------
                } catch (JSONException e1) {
                    e1.printStackTrace();


                }
            }


        }
        //Toast.makeText(c, "Comment sent!"+jsonData, Toast.LENGTH_SHORT).show();
    }

    private String sendMYComment() throws UnsupportedEncodingException {

        HttpURLConnection con = NDS_log_in_using_creds_coonect.connect(urlAddress, username,
                password);
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

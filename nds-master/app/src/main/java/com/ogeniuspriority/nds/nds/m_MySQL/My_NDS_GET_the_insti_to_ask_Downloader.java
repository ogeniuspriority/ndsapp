package com.ogeniuspriority.nds.nds.m_MySQL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ogeniuspriority.nds.nds.Config;
import com.ogeniuspriority.nds.nds.NDS_db_adapter;
import com.ogeniuspriority.nds.nds.NDS_main;
import com.ogeniuspriority.nds.nds.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by USER on 2/6/2017.
 */
public class My_NDS_GET_the_insti_to_ask_Downloader extends AsyncTask<Void, Void, String> {
    Context c;
    String magicUrl;
    View view;
    String popUpOrient;
    NDS_db_adapter db;
    StringBuffer response;
    private static volatile String theLastResponse = "";
    private ProgressDialog dialog;

    public My_NDS_GET_the_insti_to_ask_Downloader(Context c, String magicUrl, View view, String popUpOrient) {
        this.c = c;
        this.magicUrl = magicUrl;
        this.view = view;
        this.popUpOrient = popUpOrient;
        db = new NDS_db_adapter(c);
        dialog = new ProgressDialog(c);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Loading institutions...");
        dialog.show();

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
    protected void onPostExecute(final String jsonData) {
        super.onPostExecute(jsonData);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (jsonData == null) {
            Toast.makeText(c, "No Internet!", Toast.LENGTH_SHORT).show();

        } else {
            Log.w("jsonData",jsonData);
            //parse the json--
            ((Activity) c).runOnUiThread(new Runnable() {
                public void run() {
                    showPopup(view, popUpOrient, jsonData);
                }
            });


        }
    }

    private String downloadData() throws UnsupportedEncodingException {

        HttpURLConnection con = My_Get_The_insti_to_ask_Connector.connect(magicUrl);
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


    String titble_anchor_brief = "Andika ikibazo kijyanye na";

    public void showPopup(final View v, final String title, String theSeverResponse) {

        //---------
        final String[] array = theSeverResponse.split("~");


        LayoutInflater layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.pop_up_slider, null);
        //-----------
        final EditText ubutumwa_content = (EditText) popupView.findViewById(R.id.ubutumwa_content);
        TextView pop_up_title = (TextView) popupView.findViewById(R.id.pop_up_title);
        //-----------------------------
        final Spinner the_blessed_institutions = (Spinner) popupView.findViewById(R.id.the_blessed_institutions);
        //--------------

        pop_up_title.setText(titble_anchor_brief + "\n" + title);


        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //TODO do sth here on dismiss
            }
        });


        //--------------
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        //------------------
        ubutumwa_content.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
        Button ubutumwa_cancel = (Button) popupView.findViewById(R.id.ubutumwa_cancel);
        ubutumwa_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                Toast.makeText(c, "Cancelled!", Toast.LENGTH_SHORT).show();
            }
        });
        Button ubutumwa_send = (Button) popupView.findViewById(R.id.ubutumwa_send);
        ubutumwa_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //--find my local id--
                ((Activity) c).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        popupWindow.setTouchable(false);
                        if (!ubutumwa_content.getText().toString().equalsIgnoreCase("")) {
                            db.openToWrite();
                            Cursor UserCreds = db.queueAllFromCreds();

                            if (UserCreds.moveToLast()) {
                                final String myRemoteId = UserCreds.getString(9);
                                //-----------------
                                db.openToRead();
                                if (db.save_NDS_RGB_QUERIES_locally("", ubutumwa_content.getText().toString(), "", "", myRemoteId, title)) {
                                    db.openToWrite();
                                    Cursor myLastQuery = db.queueAllFromNDS_RGB_QUERIES();
                                    if (myLastQuery.moveToLast()) {
                                        final String local_query_Id = myLastQuery.getString(0);
                                        final String myQuery = myLastQuery.getString(2);
                                        final String myQueryOrientation = myLastQuery.getString(6);
                                        final String myID = myLastQuery.getString(2);
                                        // Log.w("IKIBAZO_QUERY", "..." + myQuery.toString()+"--"+ubutumwa_content.getText().toString());


                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {

                                                    final String localResponse = deposit_my_complaint_toRGB(myQuery, myRemoteId, myQueryOrientation, local_query_Id, the_blessed_institutions.getSelectedItem().toString());
                                                    //------------------------&& !theLastResponse.toString().equalsIgnoreCase(localResponse)
                                                    Log.w("IKIBAZO_UPDATE", " Updated" + localResponse);
                                                    if (localResponse != null && !localResponse.equalsIgnoreCase(theLastResponse)) {
                                                        //-------------------------
                                                        //----------------------
                                                        try {
                                                            JSONObject jsonMsgRead = new JSONObject(
                                                                    localResponse);
                                                            //--------
                                                            if (jsonMsgRead != null) {
                                                                JSONArray dataJsonArr1 = null;
                                                                try {
                                                                    dataJsonArr1 = (JSONArray) jsonMsgRead
                                                                            .get("NDS_data_query_send");
                                                                    //--------------------------
                                                                    final String NDS_query_remote_id[] = new String[dataJsonArr1.length()];
                                                                    final String NDS_query_local_id[] = new String[dataJsonArr1.length()];
                                                                    jsonMsgRead = null;
                                                                    //------------------
                                                                    for (int i = 0; i < dataJsonArr1.length(); i++) {
                                                                        //--------------------------
                                                                        NDS_query_remote_id[i] = null;
                                                                        try {
                                                                            NDS_query_remote_id[i] = dataJsonArr1
                                                                                    .getJSONObject(
                                                                                            i)
                                                                                    .getString(
                                                                                            "ogenius_nds_db_rgb_query_id")
                                                                                    .toString();
                                                                        } catch (JSONException e) {
                                                                            // TODO Auto-generated
                                                                            // catch block
                                                                            e.printStackTrace();
                                                                        }
                                                                        NDS_query_local_id[i] = null;
                                                                        try {
                                                                            NDS_query_local_id[i] = dataJsonArr1
                                                                                    .getJSONObject(
                                                                                            i)
                                                                                    .getString(
                                                                                            "ogenius_nds_db_rgb_query_local_query_id")
                                                                                    .toString();
                                                                        } catch (JSONException e) {
                                                                            // TODO Auto-generated
                                                                            // catch block
                                                                            e.printStackTrace();
                                                                        }

                                                                        //----------------Save to Local Database
                                                                        final int finalI = i;

                                                                        ((Activity) c).runOnUiThread(new Runnable() {
                                                                            @Override
                                                                            public void run() {

                                                                                popupWindow.dismiss();
                                                                                db.openToRead();
                                                                                //--
                                                                                //----------------------
                                                                                if (db.Update_NDS_RGB_QUERIES_RESPONSES(NDS_query_remote_id[finalI], NDS_query_local_id[finalI])) {
                                                                                    Log.w("IKIBAZO_UPDATE", " Updated");
                                                                                    showConfirmationMessage("OK");
                                                                                    ubutumwa_content.setText("");
                                                                                    theLastResponse = localResponse;


                                                                                    //--------


                                                                                }

                                                                            }
                                                                        });


                                                                        //------------


                                                                    }


                                                                    //--------------------
                                                                } catch (JSONException e) {
                                                                    // TODO Auto-generated catch
                                                                    // block
                                                                    e.printStackTrace();
                                                                    ((Activity) c).runOnUiThread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            popupWindow.dismiss();
                                                                            showConfirmationMessage("jjjjj");
                                                                            ubutumwa_content.setText("");
                                                                        }
                                                                    });

                                                                }

                                                            }


                                                            //--------------------------
                                                        } catch (JSONException e1) {
                                                            e1.printStackTrace();
                                                            ((Activity) c).runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    popupWindow.dismiss();
                                                                    showConfirmationMessage("jjjjj");
                                                                    ubutumwa_content.setText("");
                                                                }

                                                            });

                                                        }


                                                    } else {
                                                        ((Activity) c).runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                popupWindow.dismiss();
                                                                showConfirmationMessage("jjjjj");
                                                                ubutumwa_content.setText("");
                                                            }

                                                        });

                                                    }


                                                    //-----------------
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                    ((Activity) c).runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            popupWindow.dismiss();
                                                            showConfirmationMessage("jjjjj");
                                                            ubutumwa_content.setText("");
                                                        }
                                                    });
                                                }

                                            }
                                        }).start();

                                    } else {
                                        ((Activity) c).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                popupWindow.dismiss();
                                                showConfirmationMessage("jjjjj");
                                                ubutumwa_content.setText("");
                                            }
                                        });
                                    }


                                } else {
                                    ((Activity) c).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            popupWindow.dismiss();
                                            showConfirmationMessage("jjjjj");
                                            ubutumwa_content.setText("");
                                        }
                                    });
                                }


                            } else {
                                ((Activity) c).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        popupWindow.dismiss();
                                        showConfirmationMessage("jjjjj");
                                        ubutumwa_content.setText("");
                                    }
                                });

                            }


                        } else {
                            Toast.makeText(c, "Empty Message!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        // popupWindow.showAsDropDown(v);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                popupWindow.showAtLocation(v, Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
                popupWindow.setAnimationStyle(android.R.style.TextAppearance_DeviceDefault_Small_Inverse);
                popupWindow.setFocusable(true);
                popupWindow.update();
                //--
                ArrayAdapter<String> gameKindArray = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, array);
                gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                the_blessed_institutions.setAdapter(gameKindArray);
            }
        }, 100);

    }

    public void showConfirmationMessage(String title) {


        LayoutInflater layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.pop_up_thank_you_msg, null);
        TextView ubutumwa_content = (TextView) popupView.findViewById(R.id.ubutumwa_content);
        Button ok_sent = (Button) popupView.findViewById(R.id.ok_sent);
        if (title.toString().equalsIgnoreCase("OK")) {
            ubutumwa_content.setText("Murakoze gukoresha uru rubuga!\n Ikibazo cyanyu cyakiriwe n'inzego zibishinzwe kandi ziragikurikirana                 mu gihe cya vuba!                  ");
        } else {
            ubutumwa_content.setText("Ntago ubutumwa bwagiye,\n muze kongera kugerageza!\nMurabusanga aho ubundi butumwa bubitse!");
        }


        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //TODO do sth here on dismiss
            }
        });

        // popupWindow.showAsDropDown(v);
        popupWindow.showAtLocation(NDS_main.forum_trigger, Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
        popupWindow.setAnimationStyle(android.R.style.TextAppearance_DeviceDefault_Small_Inverse);
        popupWindow.setFocusable(true);
        popupWindow.update();
        ok_sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });


    }

    private String deposit_my_complaint_toRGB(String query, String myId, String queryOrientation, String local_query_id, String theSelectedItem) throws Exception {
        //---------------- Stage 1----------------------------------------------------
        //---------------------------------

        //----------------------------------------------------------------
        String data = "";
        data = URLEncoder.encode("query", "UTF-8")
                + "=" + URLEncoder.encode(query, "UTF-8");
        data += "&" + URLEncoder.encode("queryOrientation", "UTF-8") + "="
                + URLEncoder.encode(queryOrientation, "UTF-8");
        data += "&" + URLEncoder.encode("myId", "UTF-8") + "="
                + URLEncoder.encode(myId, "UTF-8");
        data += "&" + URLEncoder.encode("local_query_id", "UTF-8") + "="
                + URLEncoder.encode(local_query_id, "UTF-8");
        data += "&" + URLEncoder.encode("theSelectedItem", "UTF-8") + "="
                + URLEncoder.encode(theSelectedItem, "UTF-8");


        String lastResort = data;
        //==============================
        try {
            String url = Config.DEPOSIT_MYQUERY_TO_THE_RGB;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            //-----------------------------------------------------------------------
            String urlParameters = lastResort;

            // Send post request
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            //--------------------------Thread sleeps-----------

            int responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();


        } catch (ProtocolException e) {
            e.printStackTrace();
            Log.w("NDS", "Now we can talk=> " + e.toString());


        } catch (MalformedURLException e) {
            // e.printStackTrace();
            Log.w("NDS", "Now we can talk=> " + e.toString());

        } catch (IOException e) {
            Log.w("NDS", "Now we can talk=> " + e.toString());

            e.printStackTrace();
        }


        return response.toString();

        //---------------------------------------------------------------
    }

}

package com.ogeniuspriority.nds.nds;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class Forum_post extends AppCompatActivity {
    NDS_db_adapter db;
    private static String MYRemoteId = "";
    private static String MYAvatar = "";
    private static String MYNames = "";
    StringBuffer response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_post);
        getSupportActionBar().hide();
        //----------------------------------
        db = new NDS_db_adapter(this);
        //-------------------
        db.openToWrite();
        Cursor UserCreds = db.queueAllFromCreds();

        if (UserCreds.moveToLast()) {
            MYRemoteId = UserCreds.getString(9);
            MYAvatar = UserCreds.getString(3);
            MYNames = UserCreds.getString(1);
        } else {


        }
        ImageView poster_avatar = (ImageView) findViewById(R.id.poster_avatar);
        ImageLoader imageLoader = new ImageLoader(getApplicationContext());
        if (MYAvatar != null) {
            //imageLoader.DisplayImage(Config.LOAD_MY_AVATAR.toString() + MYAvatar, poster_avatar);
            //Picasso.with(getBaseContext()).load(Config.LOAD_MY_AVATAR.toString() + MYAvatar).placeholder(R.mipmap.neza_default_avatar).into(poster_avatar);
            imageLoader.DisplayImage(Config.LOAD_MY_AVATAR.toString() + MYAvatar, poster_avatar);

        } else {
          //  Picasso.with(getBaseContext()).load(Config.LOAD_MY_AVATAR.toString() + MYAvatar).placeholder(R.mipmap.neza_default_avatar).into(poster_avatar);
            imageLoader.DisplayImage(Config.LOAD_MY_AVATAR.toString() + MYAvatar, poster_avatar);
        }
        Button send_post = (Button) findViewById(R.id.send_post);
        final EditText post_content = (EditText) findViewById(R.id.post_content);
        //--------------------------
        send_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (post_content.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), "Empty post!", Toast.LENGTH_SHORT).show();
                    //-----------------------

                } else {
                    //----------------first save the post locally-----
                    if (db.save_NEZA_COMMUNITY_POSTS_locally("", "", post_content.getText().toString(), MYNames, MYRemoteId)) {
                        //-------------------------------------
                        Log.w("SAved","okay");
                        Cursor latestNoTSentPost = db.GET_ALL_UN_SENT_NEZA_COMMUNITY_POSTS();
                        if (latestNoTSentPost.moveToLast()) {
                            Log.w("SAved","Loaded!");
                            final String localID=latestNoTSentPost.getString(0);
                            final String postData=latestNoTSentPost.getString(3);
                            final String IDposter=latestNoTSentPost.getString(6);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String response_POST = deposit_my_complaint_toRGB(postData,IDposter,localID);
                                        Log.w("SAved","res="+response_POST);
                                        //-------------
                                        if (response_POST != null) {
                                            if (!response_POST.equalsIgnoreCase("Resend")) {
                                                //-------------------------
                                                //----------------------
                                                try {
                                                    JSONObject jsonMsgRead = new JSONObject(
                                                            response_POST);
                                                    //-------------------------------------
                                                    //--------
                                                    if (jsonMsgRead != null) {
                                                        JSONArray dataJsonArr1 = null;
                                                        try {
                                                            dataJsonArr1 = (JSONArray) jsonMsgRead
                                                                    .get("NDS_posts_commune");
                                                            //--------------------------
                                                            final String RemoteId[] = new String[dataJsonArr1.length()];
                                                            final String LocalId[] = new String[dataJsonArr1.length()];

                                                            //------------------
                                                            for (int i = 0; i < dataJsonArr1.length(); i++) {
                                                                //--------------------------
                                                                RemoteId[i] = null;
                                                                try {
                                                                    RemoteId[i] = dataJsonArr1
                                                                            .getJSONObject(
                                                                                    i)
                                                                            .getString(
                                                                                    "ogenius_nds_db_community_posts_id")
                                                                            .toString();
                                                                } catch (JSONException e) {
                                                                    // TODO Auto-generated
                                                                    // catch block
                                                                    e.printStackTrace();
                                                                }
                                                                LocalId[i]=null;
                                                                try {
                                                                    LocalId[i] = dataJsonArr1
                                                                            .getJSONObject(
                                                                                    i)
                                                                            .getString(
                                                                                    "ogenius_nds_db_community_posts_local_id")
                                                                            .toString();
                                                                } catch (JSONException e) {
                                                                    // TODO Auto-generated
                                                                    // catch block
                                                                    e.printStackTrace();
                                                                }

                                                                //----------------Save to Local Database
                                                                final int finalI = i;
                                                                final int finalI1 = i;
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {

                                                                        db.openToRead();
                                                                        //----------------------
                                                                        if (db.Update_NEZA_COMMUNITY_POSTS_locally(RemoteId[finalI1],LocalId[finalI1])){
                                                                            Toast.makeText(getBaseContext(),"Your post was sent",Toast.LENGTH_SHORT).show();
                                                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                                                            imm.hideSoftInputFromWindow(post_content.getWindowToken(), 0);
                                                                            finish();



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

                                                        }

                                                    }


                                                    //--------------------------
                                                } catch (JSONException e1) {
                                                    e1.printStackTrace();


                                                }


                                            } else {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(getBaseContext(), "Resend Code.", Toast.LENGTH_SHORT).show();

                                                    }
                                                });
                                            }

                                        }


                                        //--------------
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            }).start();
                        }else{
                            Log.w("SAved","THe select is rubbish!");
                        }

                    } else {
                        Toast.makeText(getBaseContext(), "Not saved!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        //----Autosave the post data in the local database--------------db.
        //---


    }

    private String deposit_my_complaint_toRGB(String postData, String Id_poster, String old_id) throws Exception {
        //---------------- Stage 1----------------------------------------------------
        //---------------------------------

        //----------------------------------------------------------------
        String data = "";
        data = URLEncoder.encode("postData", "UTF-8")
                + "=" + URLEncoder.encode(postData, "UTF-8");
        data += "&" + URLEncoder.encode("Id_poster", "UTF-8") + "="
                + URLEncoder.encode(Id_poster, "UTF-8");
        data += "&" + URLEncoder.encode("old_id", "UTF-8") + "="
                + URLEncoder.encode(old_id, "UTF-8");
        //-------------


        String lastResort = data;
        //==============================
        try {
            String url = Config.NDS_CREATE_A_NEW_COMMUNITY_POST;
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

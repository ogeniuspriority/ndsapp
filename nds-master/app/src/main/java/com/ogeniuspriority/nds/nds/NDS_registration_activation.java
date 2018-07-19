package com.ogeniuspriority.nds.nds;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class NDS_registration_activation extends AppCompatActivity {
    Handler m_handler;
    Runnable m_handlerTask;
    int _count = 0;
    StringBuffer response;
    NDS_db_adapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nds_registration_activation);
        //getSupportActionBar().hide();
        //-----------------
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(_count);

        final TextView progressText = (TextView) findViewById(R.id.progressText);
        final EditText number_field = (EditText) findViewById(R.id.number_field);
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", ValueAnimator.INFINITE, 10); // see this max value coming back here, we animale towards that value
        animation.setDuration(60000); //in milliseconds
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
        //-------------------------
        db = new NDS_db_adapter(this);
        //-----------------
        Button back = (Button) findViewById(R.id.back);
        final EditText thecode = (EditText) findViewById(R.id.thecode);
        Button Continue = (Button) findViewById(R.id.Continue);
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (thecode.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getBaseContext(), "Verfication code please!", Toast.LENGTH_SHORT).show();
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String Response_ = Confirm_The_number(thecode.getText().toString(), number_field.getText().toString());
                                Log.w("Boo Register=>-json-", "..." + Response_.toString());
                                if (Response_ != null) {
                                    if (!Response_.equalsIgnoreCase("Resend")) {
                                        //-------------------------

                                        //----------------------
                                        try {
                                            JSONObject jsonMsgRead = new JSONObject(
                                                    Response_);

                                            //-------------------------------------

                                            //--------
                                            if (jsonMsgRead != null) {
                                                JSONArray dataJsonArr1 = null;
                                                try {
                                                    dataJsonArr1 = (JSONArray) jsonMsgRead
                                                            .get("NDS_data_reg");
                                                    //--------------------------
                                                    final String NDS_userId[] = new String[dataJsonArr1.length()];
                                                    String NDS_userEmail[] = new String[dataJsonArr1.length()];
                                                    final String NDS_userTel[] = new String[dataJsonArr1.length()];
                                                    final String NDS_userAvatar[] = new String[dataJsonArr1.length()];
                                                    final String NDS_userRegdate[] = new String[dataJsonArr1.length()];
                                                    final String NDS_userNames[] = new String[dataJsonArr1.length()];
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
                                                        //----------------Save to Local Database
                                                        final int finalI = i;
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {


                                                                db.openToRead();
                                                                //----------------------
                                                                if (db.save_cred_locally(NDS_userNames[finalI], NDS_userTel[finalI], NDS_userAvatar[finalI], NDS_userRegdate[finalI], "", "", NDS_userId[finalI])) {
                                                                    Intent myIntent = new Intent(NDS_registration_activation.this, NDS_main.class);
                                                                    //myIntent.putExtra("key", value); //Optional parameters
                                                                    startActivity(myIntent);


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
                                                    m_handler.removeCallbacks(m_handlerTask);
                                                    LinearLayout resend_code_now = (LinearLayout) findViewById(R.id.resend_code_now);
                                                    resend_code_now.setVisibility(View.VISIBLE);
                                                    progressText.setText(" ");

                                                }

                                            }


                                            //--------------------------
                                        } catch (JSONException e1) {
                                            e1.printStackTrace();
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    m_handler.removeCallbacks(m_handlerTask);
                                                    LinearLayout resend_code_now = (LinearLayout) findViewById(R.id.resend_code_now);
                                                    resend_code_now.setVisibility(View.VISIBLE);
                                                    progressText.setText(" ");
                                                }

                                            });

                                        }


                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getBaseContext(), "Resend Code.", Toast.LENGTH_SHORT).show();

                                                m_handler.removeCallbacks(m_handlerTask);
                                                LinearLayout resend_code_now = (LinearLayout) findViewById(R.id.resend_code_now);
                                                resend_code_now.setVisibility(View.VISIBLE);
                                                progressText.setText(" ");
                                            }
                                        });
                                    }

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();

                }

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //----------
        String tel_;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //------------------------
        tel_ = bundle.getString("telephone");
        number_field.setText(tel_);


        //-----Timer task
        m_handler = new Handler();
        m_handlerTask = new Runnable() {
            @Override
            public void run() {
                if (_count <= 300) {
                    int remaining = 300 - _count;
                    int minutes = (int) remaining / 60;
                    int sec = remaining - (minutes * 60);
                    progressText.setText("Waiting " + minutes + ":" + sec);
                    _count++;
                    progressBar.setProgress(_count);


                } else {
                    m_handler.removeCallbacks(m_handlerTask);
                    RelativeLayout resend_code_now = (RelativeLayout) findViewById(R.id.resend_code_now);
                    resend_code_now.setVisibility(View.VISIBLE);
                    progressText.setText(" ");
                }
                m_handler.postDelayed(m_handlerTask, 1000);
            }
        };
        m_handlerTask.run();

    }

    //---------------------
    public void Resend_me(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                EditText number_field = (EditText) findViewById(R.id.number_field);                //----------------------
                LinearLayout resend_code_now = (LinearLayout) findViewById(R.id.resend_code_now);
                String data = "";
                try {
                    data = URLEncoder.encode("number_field", "UTF-8")
                            + "=" + URLEncoder.encode(number_field.getText().toString(), "UTF-8");


                    String lastResort = data;
                    //==============================
                    try {
                        String url = Config.ACCOUNT_TYPE_ONE_REGISTRATION_RESEND_CODE;
                        URL obj = new URL(url);
                        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                        //add reuqest header
                        con.setRequestMethod("POST");
                        //-----------------------------------------------------------------------
                        String urlParameters = lastResort;

                        // Send post request
                        con.setDoOutput(true);
                        con.setDoInput(true);
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
                        if (responseCode == 200) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    _count=0;
                                    LinearLayout resend_code_now = (LinearLayout) findViewById(R.id.resend_code_now);
                                    resend_code_now.setVisibility(View.INVISIBLE);
                                    m_handlerTask.run();


                                }
                            });

                        }


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
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }).start();



    }
    public void Resend_code(View v) throws Exception {
        //--------------------
        Resend_me();

    }

    private String Confirm_The_number(String theCode, String theNumber) throws Exception {
        //---------------- Stage 1----------------------------------------------------

        String data = "";
        data = URLEncoder.encode("theCode", "UTF-8")
                + "=" + URLEncoder.encode(theCode, "UTF-8");
        data += "&" + URLEncoder.encode("theNumber", "UTF-8") + "="
                + URLEncoder.encode(theNumber, "UTF-8");


        String lastResort = data;
        //==============================
        try {
            String url = Config.ACCOUNT_TYPE_ONE_REGISTRATION_CONFIRM;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            //-----------------------------------------------------------------------
            String urlParameters = lastResort;

            // Send post request
            con.setDoOutput(true);
            con.setDoInput(true);
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

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}

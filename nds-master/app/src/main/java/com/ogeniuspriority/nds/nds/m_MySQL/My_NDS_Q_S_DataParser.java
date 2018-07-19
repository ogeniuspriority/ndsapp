package com.ogeniuspriority.nds.nds.m_MySQL;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.ogeniuspriority.nds.nds.NDS_db_adapter;
import com.ogeniuspriority.nds.nds.m_DataObject.My_Community_Posts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by USER on 2/6/2017.
 */
public class My_NDS_Q_S_DataParser extends AsyncTask<Void, Void, Boolean> {
    Context c;
    String jsonData;
    ListView forum_whisper;
    SwipeRefreshLayout swipe_view;
    static ArrayList<My_Community_Posts> My_Community_Posts_Array = new ArrayList<>();
    static String[] official_id;
    static String[] query_id;
    static String[] response;
    static String[] official_names;
    static String[] remote_id;
    static String[] query_oriention;
    static String[] query_regdate;
    NDS_db_adapter db;




    public My_NDS_Q_S_DataParser(Context c, String jsonData) {
        this.c = c;
        this.jsonData = jsonData;
        db = new NDS_db_adapter(c);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        return this.parseData();
    }

    @Override
    protected void onPostExecute(Boolean isParsed) {
        super.onPostExecute(isParsed);
       // swipe_view.setRefreshing(false);
        if (isParsed) {
            //--------------
            //Log.w("isParsed",""+remote_id[6]);
            db.openToWrite();
            for(int i=0;i<official_id.length;i++){
              if(db.save_NDS_RGB_QUERIES_RESPONSES_locally(official_id[i], query_id[i],
                       response[i],  remote_id[i],
                      query_regdate[i], official_names[i],query_oriention[i])){
                  //Log.w("isParsed_passed","isaved"+remote_id[i]);
                  Activity thisAvtr=(Activity)c;
                  //--------
                  thisAvtr.recreate();

              }else{
                 // Log.w("isParsed_passed","no");
              }
                //-------------


            }

        } else {
            Toast.makeText(c, "Failed to load posts!", Toast.LENGTH_SHORT).show();
            JSONObject jo;
        }
    }

    private Boolean parseData() {
        try {
            //-------------------------------------------
            //------------
            JSONObject posts_JSON = new JSONObject(
                    jsonData);
            //Log.w("josn_", "" + jsonData);
            //------------------
            JSONArray posts_Comments_Array = (JSONArray) posts_JSON
                    .get("Comments");
            //---
            official_id = new String[posts_Comments_Array.length()];
            query_id = new String[posts_Comments_Array.length()];
            response = new String[posts_Comments_Array.length()];
            official_names = new String[posts_Comments_Array.length()];
            remote_id = new String[posts_Comments_Array.length()];
            query_oriention = new String[posts_Comments_Array.length()];
            query_regdate= new String[posts_Comments_Array.length()];
            //----

            for (int counter = 0; counter < posts_Comments_Array.length(); counter++) {
                official_id[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_rgb_query_comment_sender_id")
                        .toString();


                query_id[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_rgb_query_comment_query_id")
                        .toString();
                //----
                //--
                response[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_rgb_query_comment_comment")
                        .toString();
                official_names[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_normal_users_names")
                        .toString();
                remote_id[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_rgb_query_comment_id")
                        .toString();
                query_oriention[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_rgb_query_comment_suggestOrQuestion")
                        .toString();
                query_regdate[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_rgb_query_comment_regdate")
                        .toString();


            }

            return true;


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}

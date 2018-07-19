package com.ogeniuspriority.nds.nds.m_MySQL;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListView;

import com.ogeniuspriority.nds.nds.NDS_db_adapter;
import com.ogeniuspriority.nds.nds.NDS_posts_comments;
import com.ogeniuspriority.nds.nds.m_DataObject.My_Community_Posts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by USER on 2/6/2017.
 */
public class My_NDS_suggestions_data_parser extends AsyncTask<Void, Void, Boolean> {
    Context c;
    String jsonData;
    ListView forum_whisper;
    SwipeRefreshLayout swipe_view;
    static ArrayList<My_Community_Posts> My_Community_Posts_Array = new ArrayList<>();
    static String[] sugg_remote_id;
    static String[] sugg_remote_data;
    static String[] sugg_remote_data_type;
    static String[] sugg_remote_data_regdate;
    static String[] sugg_remote_data_sender;

    NDS_db_adapter db;


    public My_NDS_suggestions_data_parser(Context c, String jsonData, ListView forum_whisper) {
        this.c = c;
        this.jsonData = jsonData;
        this.forum_whisper = forum_whisper;
        this.swipe_view = swipe_view;
        db = new NDS_db_adapter(c);
        //-----------------------
        db.openToWrite();
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
            forum_whisper.removeFooterView(NDS_posts_comments.loadMoreView);
            if (sugg_remote_id.length > 0) {
                //forum_whisper.setAdapter(new LazyAdapter_messages_igisubizo_trend((Activity)c, Images, Names, Time, Query, QueryOrientation, Query_Provider,
                // Query_Remote_id, Query_ID, Query_sent));
                //------------Save the new loaded suggestions----------
                for (int i = 0; i < sugg_remote_id.length; i++) {
                    try {
                        if(sugg_remote_id[i].isEmpty()){
                            continue;
                        }
                        if (db.save_NDS_SUGGESTIONS_locally(sugg_remote_id[i], sugg_remote_data[i], sugg_remote_data_regdate[i], sugg_remote_data_type[i], sugg_remote_data_sender[i], "suggestion")) {

                        }
                    } catch (RuntimeException djd) {
                        Log.w("error2017",""+djd);

                    }

                }
                Activity err=(Activity)c;
                err.recreate();
                Log.w("test2017", "done right");
            } else {

            }
           // forum_whisper.addFooterView(NDS_posts_comments.loadMoreView);
        } else {
            // Toast.makeText(c, "Failed to load posts!", Toast.LENGTH_SHORT).show();
            JSONObject jo;
        }
    }

    private Boolean parseData() {
        try {
            //-------------------------------------------
            //------------
            JSONObject posts_JSON = new JSONObject(
                    jsonData);
            Log.w("josn_", "" + jsonData);
            //------------------
            JSONArray posts_Comments_Array = (JSONArray) posts_JSON
                    .get("Nds Sugg");
            //---
            sugg_remote_id = new String[posts_Comments_Array.length()];
            sugg_remote_data = new String[posts_Comments_Array.length()];
            sugg_remote_data_type = new String[posts_Comments_Array.length()];
            sugg_remote_data_regdate = new String[posts_Comments_Array.length()];
            sugg_remote_data_sender = new String[posts_Comments_Array.length()];
            //----
            //---
            for (int counter = 0; counter < posts_Comments_Array.length(); counter++) {
                sugg_remote_id[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_suggestions_id")
                        .toString();


                sugg_remote_data[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_suggestions_data")
                        .toString();
                //----
                //--
                sugg_remote_data_type[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_suggestions_type")
                        .toString();
                sugg_remote_data_regdate[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_suggestions_regdate")
                        .toString();
                sugg_remote_data_sender[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_suggestions_provider_id")
                        .toString();


            }

            return true;


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}

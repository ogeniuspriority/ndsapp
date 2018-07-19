package com.ogeniuspriority.nds.nds.m_MySQL;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;

import com.ogeniuspriority.nds.nds.Config;
import com.ogeniuspriority.nds.nds.NDS_db_adapter;
import com.ogeniuspriority.nds.nds.NDS_main;
import com.ogeniuspriority.nds.nds.View_NDS_posts_comments;
import com.ogeniuspriority.nds.nds.generatePictureStyleNotification_big_text_with_large_icon;
import com.ogeniuspriority.nds.nds.m_DataObject.My_Community_Posts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by USER on 2/6/2017.
 */
public class My_POSTS_KEYWORD_data_parser extends AsyncTask<Void, Void, Boolean> {
    Context c;
    String jsonData;
    static ArrayList<My_Community_Posts> My_Community_Posts_Array = new ArrayList<>();

    static String[] Notif_number_queries;
    static String[] Notif_title_queries;
    static String[] origin_remote_id_queries;
    static String[] imageUrl_queries;
    static String[] bigText_queries;
    static String[] regDate_queries;
    static String[] sumMary_queries;
    static String[] activationTag_queries;
    static String[] notifType_queries;
    static String[] Orient_queries;

    NDS_db_adapter db;


    public My_POSTS_KEYWORD_data_parser(Context c, String jsonData) {
        this.c = c;
        this.jsonData = jsonData;
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
            if (Notif_number_queries.length > 0) {
                //forum_whisper.setAdapter(new LazyAdapter_messages_igisubizo_trend((Activity)c, Images, Names, Time, Query, QueryOrientation, Query_Provider,
                // Query_Remote_id, Query_ID, Query_sent));
                //------------Save the new loaded suggestions----------
                for (int i = 0; i < Notif_number_queries.length; i++) {
                    try {
                        if(origin_remote_id_queries[i].isEmpty()){
                            continue;
                        }
                        //---------notification id----------------
                        long millis = System.currentTimeMillis() % 1000;
                        final String uniqueID =""+millis;
                        if (db.save_A_NOTIFICATION(uniqueID, Notif_title_queries[i],
                                bigText_queries[i] ,
                                imageUrl_queries[i] , bigText_queries[i] ,
                                regDate_queries[i] , sumMary_queries[i] ,
                                origin_remote_id_queries[i],"keyword_on_posts",Orient_queries[i]
                               )) {
                            final int finalI = i;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Looper.prepare();


                                    final String theAvatarValue = new NDS_main(). FindThisTrickyAvatar(origin_remote_id_queries[finalI], "notif_using_keyword_on_posts");
                                    Log.w("theAvatarValue", "" + theAvatarValue);
                                    if (new NDS_main(). renderThisAvatarUrlData(theAvatarValue)) {
                                        ((Activity) c).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                new generatePictureStyleNotification_big_text_with_large_icon(c, Integer.parseInt(uniqueID), Notif_title_queries[finalI], bigText_queries[finalI], theAvatarValue, bigText_queries[finalI], View_NDS_posts_comments.class, "the_post_id", origin_remote_id_queries[finalI] + "-" + "notif_using_keyword_on_posts").execute();

                                            }
                                        });
                                    } else {
                                        ((Activity) c).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                new generatePictureStyleNotification_big_text_with_large_icon(c, Integer.parseInt(uniqueID), Notif_title_queries[finalI], bigText_queries[finalI], Config.LOAD_MY_AVATAR.toString() + "/" + theAvatarValue, bigText_queries[finalI], View_NDS_posts_comments.class, "the_post_id", origin_remote_id_queries[finalI] + "-" + "notif_using_keyword_on_posts").execute();

                                            }
                                        });
                                    }
                                }
                            }).start();

                        }
                    } catch (RuntimeException djd) {
                        Log.w("error2017",""+djd);

                    }

                }

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
            Log.w("josn_", "" + jsonData);
            //------------
            JSONObject posts_JSON = new JSONObject(
                    jsonData);

            //------------------
            JSONArray posts_Comments_Array = (JSONArray) posts_JSON
                    .get("Nds Notif P A");
            //---
            Notif_number_queries = new String[posts_Comments_Array.length()];
            Notif_title_queries = new String[posts_Comments_Array.length()];
            origin_remote_id_queries = new String[posts_Comments_Array.length()];
            imageUrl_queries = new String[posts_Comments_Array.length()];
            bigText_queries = new String[posts_Comments_Array.length()];
            regDate_queries = new String[posts_Comments_Array.length()];
            sumMary_queries = new String[posts_Comments_Array.length()];
            activationTag_queries = new String[posts_Comments_Array.length()];
            notifType_queries = new String[posts_Comments_Array.length()];
            Orient_queries = new String[posts_Comments_Array.length()];
            //----
            //---
            for (int counter = 0; counter < posts_Comments_Array.length(); counter++) {
                Notif_number_queries[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_community_posts_id")
                        .toString();


                Notif_title_queries[counter] = "Keyword in post";
                //----
                //--
                origin_remote_id_queries[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_community_posts_id")
                        .toString();
                imageUrl_queries[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_community_posts_id")
                        .toString();
                bigText_queries[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_community_posts_postdata")
                        .toString();
                regDate_queries[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_community_posts_regdate")
                        .toString();
                sumMary_queries[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_community_posts_postdata")
                        .toString();
                activationTag_queries[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_community_posts_postdata")
                        .toString();
                notifType_queries[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_community_posts_postdata")
                        .toString();
                Orient_queries[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_community_posts_postdata")
                        .toString();

            }

            return true;


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}

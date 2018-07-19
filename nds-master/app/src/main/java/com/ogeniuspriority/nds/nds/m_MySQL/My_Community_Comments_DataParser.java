package com.ogeniuspriority.nds.nds.m_MySQL;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.ogeniuspriority.nds.nds.LazyAdapter_post_comments;
import com.ogeniuspriority.nds.nds.NDS_posts_comments;
import com.ogeniuspriority.nds.nds.m_DataObject.My_Community_Posts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by USER on 2/6/2017.
 */
public class My_Community_Comments_DataParser extends AsyncTask<Void, Void, Boolean> {
    Context c;
    String jsonData;
    ListView forum_whisper;
    SwipeRefreshLayout swipe_view;
    static ArrayList<My_Community_Posts> My_Community_Posts_Array = new ArrayList<>();
    static String[] commentP_data;
    static String[] commentP_avatar;
    static String[] commentP_commentator_names;
    static String[] commentP_regadate;
    static String[] commentP_Posts_id;

    public My_Community_Comments_DataParser(Context c, String jsonData, ListView forum_whisper, SwipeRefreshLayout swipe_view) {
        this.c = c;
        this.jsonData = jsonData;
        this.forum_whisper = forum_whisper;
        this.swipe_view = swipe_view;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (!swipe_view.isRefreshing()) {
            swipe_view.setRefreshing(true);
        }
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        return this.parseData();
    }

    @Override
    protected void onPostExecute(Boolean isParsed) {
        super.onPostExecute(isParsed);
        swipe_view.setRefreshing(false);
        if (isParsed) {
            try {
                forum_whisper.removeFooterView(NDS_posts_comments.loadMoreView);
            } catch (RuntimeException dsjf) {

            }
            if (commentP_avatar.length > 0) {
                NDS_posts_comments.comments_nber.setText("" + commentP_avatar.length + " Cmnts");
                forum_whisper.setAdapter(new LazyAdapter_post_comments((Activity) c, commentP_avatar, commentP_commentator_names, commentP_regadate, commentP_data, commentP_Posts_id));
                forum_whisper.setSelection(NDS_posts_comments.new_post_comment_available_reserve);
            } else {

            }
            forum_whisper.addFooterView(NDS_posts_comments.loadMoreView);
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
            Log.w("josn_", "" + jsonData);
            //------------------
            JSONArray posts_Comments_Array = (JSONArray) posts_JSON
                    .get("Comments");
            //---
            commentP_data = new String[posts_Comments_Array.length()];
            commentP_avatar = new String[posts_Comments_Array.length()];
            commentP_commentator_names = new String[posts_Comments_Array.length()];
            commentP_regadate = new String[posts_Comments_Array.length()];
            commentP_Posts_id = new String[posts_Comments_Array.length()];
            //----
            NDS_posts_comments.lastId_Of_post_comment_Retreived = Integer.parseInt(posts_Comments_Array
                    .getJSONObject(
                            posts_Comments_Array.length() - 1)
                    .getString(
                            "ogenius_nds_db_community_posts_comments_id")
                    .toString());

            NDS_posts_comments.firstId_Of_post_comment_Retreived = Integer.parseInt(posts_Comments_Array
                    .getJSONObject(
                            0)
                    .getString(
                            "ogenius_nds_db_community_posts_comments_id")
                    .toString());
            //---

            for (int counter = 0; counter < posts_Comments_Array.length(); counter++) {
                commentP_Posts_id[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_community_posts_comments_posts_post_id")
                        .toString();


                commentP_data[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_community_posts_comments_comment")
                        .toString();
                //----
                //--
                commentP_avatar[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_normal_users_avatar")
                        .toString();
                commentP_commentator_names[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_normal_users_names")
                        .toString();
                commentP_regadate[counter] = posts_Comments_Array
                        .getJSONObject(
                                counter)
                        .getString(
                                "ogenius_nds_db_community_posts_comments_regdate")
                        .toString();

            }

            return true;


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}

package com.ogeniuspriority.nds.nds.m_MySQL;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;

import com.ogeniuspriority.nds.nds.Config;
import com.ogeniuspriority.nds.nds.NDS_db_adapter;
import com.ogeniuspriority.nds.nds.Urubuga_Services;
import com.ogeniuspriority.nds.nds.m_DataObject.My_Community_Posts;
import com.ogeniuspriority.nds.nds.m_UI.My_Community_Posts_CustomAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by USER on 2/6/2017.
 */
public class My_Community_Posts_DataParser extends AsyncTask<Void, Void, Boolean> {
    Context c;
    String jsonData;
    ListView forum_whisper;
    SwipeRefreshLayout swipe_view;
    static ArrayList<My_Community_Posts> My_Community_Posts_Array = new ArrayList<>();
    NDS_db_adapter db;

    public My_Community_Posts_DataParser(Context c, String jsonData, ListView forum_whisper, SwipeRefreshLayout swipe_view) {
        this.c = c;
        this.jsonData = jsonData;
        this.forum_whisper = forum_whisper;
        this.swipe_view = swipe_view;
        db = new NDS_db_adapter(c);
        db.openToWrite();
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
       // forum_whisper.removeFooterView(Urubuga_Services.PlaceholderFragment.loadMoreView);
        if (isParsed) {
            if (My_Community_Posts_Array.size() == 0) {
                Toast.makeText(c, "No more posts available!", Toast.LENGTH_SHORT).show();
            } else {
                forum_whisper.addFooterView(Urubuga_Services.PlaceholderFragment.loadMoreView);


                InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(Urubuga_Services.PlaceholderFragment.Search.getWindowToken(), 0);
                Urubuga_Services.my_list_ok = My_Community_Posts_Array;
                forum_whisper.setAdapter(new My_Community_Posts_CustomAdapter(c, My_Community_Posts_Array));
               // forum_whisper.addFooterView(Urubuga_Services.PlaceholderFragment.loadMoreView);
                forum_whisper.setSelection(Urubuga_Services.PlaceholderFragment.new_post_available_reserve-1);
            }


        } else {


            Toast.makeText(c, "Failed to load posts!", Toast.LENGTH_SHORT).show();
            JSONObject jo;
        }
    }

    private Boolean parseData() {
        try {
            //-------------------------------------------
            My_Community_Posts_Array = new ArrayList<>();
            JSONObject jo = new JSONObject(jsonData);
            My_Community_Posts_Array.clear();

            //---------------
            String[] allResults = jsonData.split("--cyuma2017--");

            String posts_0 = allResults[0];
            String Comments = allResults[1];
            String CmmentsCount = allResults[2];
            String LikesCount = allResults[3];
            String UnlikesCount = allResults[4];
            String UserCreds = allResults[5];

            //------------
            JSONObject posts_JSON = new JSONObject(
                    posts_0);
            JSONObject Comments_JSON = new JSONObject(
                    Comments);
            JSONObject CmmentsCount_JSON = new JSONObject(
                    CmmentsCount);
            JSONObject LikesCount_JSON = new JSONObject(
                    LikesCount);
            JSONObject UnlikesCount_JSON = new JSONObject(
                    UnlikesCount);
            JSONObject UserCreds_JSON = new JSONObject(
                    UserCreds);
            //------------------
            JSONArray posts_JSON_Array = (JSONArray) posts_JSON
                    .get("posts");
            JSONArray Comments_JSON_Array = (JSONArray) Comments_JSON
                    .get("Comments");
            JSONArray CmmentsCount_JSON_Array = (JSONArray) CmmentsCount_JSON
                    .get("CmmentsCount");
            JSONArray LikesCount_JSON_JSON_Array = (JSONArray) LikesCount_JSON
                    .get("LikesCount");
            JSONArray UnlikesCount_JSON_Array = (JSONArray) UnlikesCount_JSON
                    .get("UnlikesCount");
            JSONArray UserCreds_JSON_Array = (JSONArray) UserCreds_JSON
                    .get("UserCreds");
            //---
           // Log.w("werrt",""+jsonData.toString());

            String My_Posts_Posts_Comments_ForOtherUse = "";
            My_Community_Posts m;
            //------------
            if (posts_JSON_Array.length() > 0) {

                Urubuga_Services.lastId_Of_post_Retreived = posts_JSON_Array
                        .getJSONObject(
                                posts_JSON_Array.length() - 1)
                        .getInt(
                                "ogenius_nds_db_community_posts_id");
                Urubuga_Services.firstId_Of_post_Retreived = posts_JSON_Array
                        .getJSONObject(0)
                        .getInt(
                                "ogenius_nds_db_community_posts_id");
            }

            //---------
            final int numberOfItemsInResp = posts_JSON_Array.length();
            Log.w("numberOfItemsInResp",""+numberOfItemsInResp);

            for (int i = 0; i < numberOfItemsInResp; i++) {
                //-------get Data From Server----My_Posts_Post_id
                if (Comments_JSON_Array.length() != 0) {
                    My_Posts_Posts_Comments_ForOtherUse = Comments_JSON_Array.toString();
                } else {
                    My_Posts_Posts_Comments_ForOtherUse = Comments_JSON_Array.toString();
                }
                //------------------
                String My_Posts_Avatar_URLs = Config.LOAD_MY_AVATAR + UserCreds_JSON_Array.getJSONObject(i).getString("ogenius_nds_db_normal_users_avatar");

                String My_Posts_Poster_name = UserCreds_JSON_Array
                        .getJSONObject(
                                i)
                        .getString(
                                "ogenius_nds_db_normal_users_names")
                        .toString();
                String My_Posts_Poster_Post = posts_JSON_Array
                        .getJSONObject(
                                i)
                        .getString(
                                "ogenius_nds_db_community_posts_postdata")
                        .toString();
                String My_Posts_Poster_Regadate = posts_JSON_Array
                        .getJSONObject(
                                i)
                        .getString(
                                "ogenius_nds_db_community_posts_regdate")
                        .toString();
                String My_Posts_Post_Comments = CmmentsCount_JSON_Array
                        .getJSONObject(
                                i)
                        .getString(
                                "All_Attached_Comments")
                        .toString();

                //---------------
                String My_My_Avatars = UserCreds_JSON_Array
                        .getJSONObject(
                                i)
                        .getString(
                                "ogenius_nds_db_normal_users_avatar")
                        .toString();
                //-------
                int My_Posts_like_status = LikesCount_JSON_JSON_Array
                        .getJSONObject(
                                i)
                        .getInt(
                                "LikeCompilations");
                int My_Posts_unlike_status = UnlikesCount_JSON_Array
                        .getJSONObject(
                                i)
                        .getInt(
                                "UnLikeCompilations");
                int My_Posts_Views = posts_JSON_Array
                        .getJSONObject(
                                i)
                        .getInt(
                                "ogenius_nds_db_community_posts_views");
                int My_Posts_Post_id = posts_JSON_Array
                        .getJSONObject(
                                i)
                        .getInt(
                                "ogenius_nds_db_community_posts_id");
                int My_Posts_Poster_id = posts_JSON_Array
                        .getJSONObject(
                                i)
                        .getInt(
                                "ogenius_nds_db_community_posts_poster_id");
                //--------------------
                //-----------------
                m = new My_Community_Posts();
                m.setMy_Posts_Regdate(My_Posts_Poster_Regadate);
                m.setMy_Posts_Avatar_URLs(My_Posts_Avatar_URLs);
                m.setMy_Posts_Poster_name(My_Posts_Poster_name);
                m.setMy_Posts_Poster_Post(My_Posts_Poster_Post);
                m.setMy_Posts_Post_Comments(My_Posts_Post_Comments);
                m.setMy_Posts_like_status(My_Posts_like_status);
                m.setMy_Posts_unlike_status(My_Posts_unlike_status);
                m.setMy_Posts_Views(My_Posts_Views);
                m.setMy_Posts_Post_id(My_Posts_Post_id);
                m.setMy_Posts_Poster_id(My_Posts_Poster_id);
                m.setMy_Posts_Post_Comments_ForOtherUse(My_Posts_Posts_Comments_ForOtherUse);
                //------------
                m.setMy_Avatars(My_My_Avatars);
                //-
                My_Community_Posts_Array.add(m);
                //-------Save  to local--all 50 posts-----------------
                String wee = Urubuga_Services.AllComments + "}]";
                //----
                if(db.save_NEZA_COMMUNITY_POSTS_locally_NO_DATA(""+My_Posts_Post_id,My_Posts_Poster_Regadate,
                        My_Posts_Poster_Post, My_Posts_Poster_name,
                        ""+My_Posts_Poster_id, My_Posts_Posts_Comments_ForOtherUse,
                       ""+My_Posts_Views, ""+My_Posts_unlike_status,
                        ""+My_Posts_like_status, My_Posts_Avatar_URLs,My_Posts_Post_Comments)){





                }



            }

            return true;


        } catch (JSONException e) {
            Log.w("jsonData-",e.toString());
            e.printStackTrace();
        }
        return false;
    }
}

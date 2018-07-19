package com.ogeniuspriority.nds.nds.m_MySQL;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.ogeniuspriority.nds.nds.Config;
import com.ogeniuspriority.nds.nds.NDS_db_adapter;
import com.ogeniuspriority.nds.nds.m_DataObject.My_Community_Posts;
import com.ogeniuspriority.nds.nds.m_UI.My_Community_Posts_CustomAdapter_local;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by USER on 2/6/2017.
 */
public class My_Community_Posts_Local_DataParser extends AsyncTask<Void, Void, Boolean> {
    Context c;
    ListView forum_whisper;
    SwipeRefreshLayout swipe_view;
    static ArrayList<My_Community_Posts> My_Community_Posts_Array = new ArrayList<>();
    NDS_db_adapter db;

    public My_Community_Posts_Local_DataParser(Context c, ListView forum_whisper, SwipeRefreshLayout swipe_view) {
        this.c = c;
        db = new NDS_db_adapter(c);
        db.openToWrite();
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
            forum_whisper.setAdapter(new My_Community_Posts_CustomAdapter_local(c, My_Community_Posts_Array));
            Toast.makeText(c, "Loaded!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(c, "Failed to load posts!", Toast.LENGTH_SHORT).show();
            JSONObject jo;
        }
    }

    private Boolean parseData() {

        //-------------------------------------------
        My_Community_Posts_Array = new ArrayList<>();
        My_Community_Posts_Array.clear();


        String My_Posts_Posts_Comments_ForOtherUse = "";
        My_Community_Posts m;


        //-------------------
        Cursor myPosyts = db.GET_ALL_NEZA_COMMUNITY_POSTS();
        myPosyts.moveToFirst();
        for (int i = 0; i < myPosyts.getCount(); i++) {

            My_Posts_Posts_Comments_ForOtherUse = myPosyts.getString(7);
            //------------------
            String My_Posts_Avatar_URLs = Config.LOAD_MY_AVATAR + myPosyts.getString(11);

            String My_Posts_Poster_name = myPosyts.getString(5);
            String My_Posts_Poster_Post = myPosyts.getString(3);
            String My_Posts_Poster_Regadate = myPosyts.getString(2);
            String My_Posts_Post_Comments;
            if (!(myPosyts.getString(12)==null)) {
                My_Posts_Post_Comments = myPosyts.getString(12).split("Cmnts")[0].split(" ")[0];
            } else {
                My_Posts_Post_Comments = "0";
            }
            //---------------
            String My_My_Avatars = myPosyts.getString(11);
            //-------

            int My_Posts_like_status;
            if (!(myPosyts.getString(10)==null)) {
                My_Posts_like_status = Integer.parseInt(myPosyts.getString(10).split("likes")[0].split(" ")[0]);
            } else {
                My_Posts_like_status = 0;
            }
            int My_Posts_unlike_status;
            if (!(myPosyts.getString(9)==null)) {
                My_Posts_unlike_status = Integer.parseInt(myPosyts.getString(9).split("unlikes")[0].split(" ")[0]);
            } else {
                My_Posts_unlike_status = 0;
            }
            int My_Posts_Views;
            if (!(myPosyts.getString(8)==null)) {

                My_Posts_Views = Integer.parseInt(myPosyts.getString(8).split("Views")[0].split(" ")[0]);
            } else {
                My_Posts_Views = 0;
            }
            int My_Posts_Post_id;
            if (!(myPosyts.getString(1)==null)) {
                My_Posts_Post_id = Integer.parseInt(myPosyts.getString(1));
            } else {
                My_Posts_Post_id = 0;
            }
            int My_Posts_Poster_id;
            if (!(myPosyts.getString(1)==null)) {
                My_Posts_Poster_id = Integer.parseInt(myPosyts.getString(1));
            } else {
                My_Posts_Poster_id = 0;
            }
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


            //----------------


            myPosyts.moveToNext();

        }


        //--------------

        return true;


    }
}

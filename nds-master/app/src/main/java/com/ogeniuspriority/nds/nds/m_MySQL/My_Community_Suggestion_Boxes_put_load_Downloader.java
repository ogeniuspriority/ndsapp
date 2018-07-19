package com.ogeniuspriority.nds.nds.m_MySQL;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.ogeniuspriority.nds.nds.LazyAdapter_suggest;
import com.ogeniuspriority.nds.nds.NDS_db_adapter;

/**
 * Created by USER on 2/6/2017.
 */
public class My_Community_Suggestion_Boxes_put_load_Downloader extends AsyncTask<Void, Void, String> {
    Context c;
    String urlAddress;
    ListView forum_whisper;
    SwipeRefreshLayout swipe_view;
    NDS_db_adapter db;


    public My_Community_Suggestion_Boxes_put_load_Downloader(Context c, ListView forum_whisper) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.forum_whisper = forum_whisper;
        this.swipe_view = swipe_view;
        //===================================================
        db = new NDS_db_adapter(c);
        try {
            db.openToWrite();
        }catch (SQLiteException fif){

        }

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(Void... voids) {


            return "cyuma";

    }

    @Override
    protected void onPostExecute(String jsonData) {
        super.onPostExecute(jsonData);

        if (jsonData == null) {

        } else {
            //parse the json--
            if (forum_whisper != null) {
                Cursor MyLocalSuggestionBoxes = db.GET_ALL_NEZA_COMMUNITY_SUGGESTION_BOXES();
                MyLocalSuggestionBoxes.moveToFirst();
                String[] remoteId_o = new String[MyLocalSuggestionBoxes.getCount()];
                String[] avatars_o = new String[MyLocalSuggestionBoxes.getCount()];
                String[] institution_names_o = new String[MyLocalSuggestionBoxes.getCount()];
                String[] institution_motto_o = new String[MyLocalSuggestionBoxes.getCount()];
                for (int i = 0; i < MyLocalSuggestionBoxes.getCount(); i++) {

                        remoteId_o[i] = MyLocalSuggestionBoxes.getString(0);
                        avatars_o[i] = MyLocalSuggestionBoxes.getString(2);
                        institution_names_o[i] = MyLocalSuggestionBoxes.getString(3);
                        institution_motto_o[i] = MyLocalSuggestionBoxes.getString(4);

                    MyLocalSuggestionBoxes.moveToNext();
                }
                //--populate
                if (forum_whisper != null) {
                    forum_whisper.setAdapter(new LazyAdapter_suggest((Activity)c, avatars_o, institution_names_o, institution_names_o, institution_motto_o,remoteId_o));
                }

            }
        }
    }


}

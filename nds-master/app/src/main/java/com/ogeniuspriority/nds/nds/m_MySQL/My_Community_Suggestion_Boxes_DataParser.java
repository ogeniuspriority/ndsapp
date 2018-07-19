package com.ogeniuspriority.nds.nds.m_MySQL;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.widget.ListView;

import com.ogeniuspriority.nds.nds.NDS_db_adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by USER on 2/6/2017.
 */
public class My_Community_Suggestion_Boxes_DataParser extends AsyncTask<Void, Void, Boolean> {
    Context c;
    String jsonData;
    ListView forum_whisper;

    static String[] remoteId;
    static String[] avatars;
    static String[] institution_names;
    static String[] institution_motto;
    NDS_db_adapter db;


    public My_Community_Suggestion_Boxes_DataParser(Context c, String jsonData, ListView forum_whisper) {
        this.c = c;
        this.jsonData = jsonData;
        db = new NDS_db_adapter(c);
        this.forum_whisper = forum_whisper;
        try {
            db.openToRead();
        } catch (RuntimeException ddf) {

        }

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

        if (isParsed) {

            //------------------
            for (int i = 0; i < remoteId.length; i++) {
                // Log.w("asave", "Okay" + remoteId[i] + "->" + avatars[i] + "->" + institution_names[i] + "->" + institution_motto[i]);

                try {
                    if (remoteId != null && remoteId.length > 0 && db != null) {
                        try {
                            if (db.save_NEZA_COMMUNITY_SUGGESTION_BOX_locally(remoteId[i], avatars[i], institution_names[i], institution_motto[i])) {
                                // Log.w("asave0", "Okay" + remoteId[i] + "->" + avatars[i] + "->" + institution_names[i] + "->" + institution_motto[i]);
                                new My_Community_Suggestion_Boxes_put_load_Downloader(c, forum_whisper).execute();

                            } else {
                                // Log.w("asave1", "Okay" + remoteId[i] + "->" + avatars[i] + "->" + institution_names[i] + "->" + institution_motto[i]);

                            }
                        } catch (RuntimeException fofk) {

                        }
                    }

                } catch (SQLiteException dvdg) {
                    //Log.w("asaveerror", "Okay" + remoteId[i] + "->" + avatars[i] + "->" + institution_names[i] + "->" + institution_motto[i]);

                }
            }


        } else {

        }
    }

    private Boolean parseData() {
        try {
            //-------------------------------------------
            //------------
            //Log.w("josn_", "" + jsonData);
            JSONObject posts_JSON = new JSONObject(
                    jsonData);

            //------------------
            JSONArray SuggestionBoxesPool = (JSONArray) posts_JSON
                    .get("SuggestionBoxes");
            remoteId = new String[SuggestionBoxesPool.length()];
            avatars = new String[SuggestionBoxesPool.length()];
            institution_names = new String[SuggestionBoxesPool.length()];
            institution_motto = new String[SuggestionBoxesPool.length()];
            //---
            for (int i = 0; i < SuggestionBoxesPool.length(); i++) {

                remoteId[i] = "" + SuggestionBoxesPool.getJSONObject(i).getInt("ogenius_nds_db_community_id");
                avatars[i] = "" + SuggestionBoxesPool.getJSONObject(i).getString("ogenius_nds_db_community_avatar");
                institution_names[i] = "" + SuggestionBoxesPool.getJSONObject(i).getString("ogenius_nds_db_community_institution_name");
                institution_motto[i] = "" + SuggestionBoxesPool.getJSONObject(i).getString("ogenius_nds_db_community_motto");
                //Log.w("asave", "Okay" + remoteId[i]+"->"+avatars[i]+"->"+institution_names[i]+"->"+ institution_motto[i]);


            }


            return true;


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}

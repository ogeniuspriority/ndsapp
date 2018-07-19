package com.ogeniuspriority.nds.nds.m_MySQL;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.ogeniuspriority.nds.nds.LazyAdapter_suggest_search;
import com.ogeniuspriority.nds.nds.NDS_db_adapter;
import com.ogeniuspriority.nds.nds.R;

/**
 * Created by USER on 2/6/2017.
 */
public class My_Community_Search_In_Boxes_Downloader extends AsyncTask<Void, Void, String> {
    Context c;

    static String[] remoteId;
    static String[] avatars;
    static String[] institution_names;
    static String[] institution_motto;
    NDS_db_adapter db;
    String keyword;
    ListView mySearchList;
    View anchor;
    static ListView search_res_list;
    static PopupWindow popupWindow;

    public My_Community_Search_In_Boxes_Downloader(Context c, String keyword, View anchor) {
        this.c = c;
        db = new NDS_db_adapter(c);
        db.openToWrite();
        this.keyword = keyword;
        this.anchor = anchor;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(Void... voids) {

        return this.downloadData();

    }

    @Override
    protected void onPostExecute(String jsonData) {
        super.onPostExecute(jsonData);

        if (jsonData == null) {
            if (popupWindow != null) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        } else {
            showSearchRes(anchor);
        }
    }

    private String downloadData() {

        return null;


    }

    public void showSearchRes(final View v) {

        LayoutInflater layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.suggestion_box_search_reslt_anchor, null);
        popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //TODO do sth here on dismiss
            }
        });
        Cursor MyLocalSuggestionBoxes_00 = db.GET_ALL_NEZA_COMMUNITY_SUGGESTION_BOXES_INSTITUTE_LIKE(keyword);
        MyLocalSuggestionBoxes_00.moveToFirst();
        remoteId = new String[MyLocalSuggestionBoxes_00.getCount()];
        avatars = new String[MyLocalSuggestionBoxes_00.getCount()];
        institution_names = new String[MyLocalSuggestionBoxes_00.getCount()];
        String[] institution_motto_o = new String[MyLocalSuggestionBoxes_00.getCount()];
        for (int i = 0; i < MyLocalSuggestionBoxes_00.getCount(); i++) {
            remoteId[i] = MyLocalSuggestionBoxes_00.getString(0);
            avatars[i] = MyLocalSuggestionBoxes_00.getString(2);
            institution_names[i] = MyLocalSuggestionBoxes_00.getString(3);
            institution_motto[i] = MyLocalSuggestionBoxes_00.getString(4);
            MyLocalSuggestionBoxes_00.moveToNext();
        }       //--populate
        //----
        if (avatars != null) {
            if (avatars.length > 0) {
                popupWindow.showAsDropDown(v);
                //popupWindow.showAtLocation(v, Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
                popupWindow.setAnimationStyle(android.R.style.TextAppearance_DeviceDefault_Small_Inverse);
                popupWindow.setFocusable(true);
                popupWindow.update();
                //-----------
                search_res_list = (ListView) popupView.findViewById(R.id.search_res_list);
                search_res_list.setAdapter(new LazyAdapter_suggest_search((Activity) c, avatars, institution_names, institution_names, institution_motto,remoteId));
            }
        }

    }
}

package com.ogeniuspriority.nds.nds;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ogeniuspriority.nds.nds.m_MySQL.My_suggesrion_boxes_find_avatars;

public class LazyAdapter_suggest extends BaseAdapter {

    private Activity activity;
    private String[] Images_local;
    private String[] Names_local;
    private String[] message;
    private String[] Time_local;
    private String[] remoteId_local;
    private String[] Institution_local;
    String[] mottos_local;
    private static LayoutInflater inflater = null;
    public ImageLoader imageLoader;

    public LazyAdapter_suggest(Activity a, String[] Images, String[] Names, String[] Institution, String[] mottos, String[] remoteId) {
        activity = a;
        Images_local = Images;
        Names_local = Names;
        mottos_local = mottos;
        Institution_local = Institution;
        remoteId_local = remoteId;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return Images_local.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) {

            vi = inflater.inflate(R.layout.forum_main_feed_suggest, null);
            TextView Name_of_institution = (TextView) vi.findViewById(R.id.Name_of_institution);
            Name_of_institution.setText(Institution_local[position]);
            TextView motto_ = (TextView) vi.findViewById(R.id.motto_);
            motto_.setText(mottos_local[position]);
            ImageView institution_avatar = (ImageView) vi.findViewById(R.id.institution_avatar);
            //My_Community_Posts_PicassoClient_box.downloadImage(activity.getBaseContext(), Config.LOAD_MY_SUGGESTION_BOXES + Institution_local[position], institution_avatar);

             imageLoader.DisplayImage(Config.LOAD_MY_SUGGESTION_BOXES_RENDER + Images_local[position], institution_avatar);
            //new My_suggesrion_boxes_find_avatars(activity.getBaseContext(), Config.LOAD_MY_SUGGESTION_BOXES_RENDER, Institution_local[position], institution_avatar).execute();

        } else {

            TextView Name_of_institution = (TextView) vi.findViewById(R.id.Name_of_institution);
            Name_of_institution.setText(Institution_local[position]);
            TextView motto_ = (TextView) vi.findViewById(R.id.motto_);
            motto_.setText(mottos_local[position]);
            ImageView institution_avatar = (ImageView) vi.findViewById(R.id.institution_avatar);
            //My_Community_Posts_PicassoClient_box.downloadImage(activity.getBaseContext(), Config.LOAD_MY_SUGGESTION_BOXES + Institution_local[position], institution_avatar);
            //new My_suggesrion_boxes_find_avatars(activity.getBaseContext(), Config.LOAD_MY_SUGGESTION_BOXES_RENDER, Institution_local[position], institution_avatar).execute();
            imageLoader.DisplayImage(Config.LOAD_MY_SUGGESTION_BOXES_RENDER + Images_local[position], institution_avatar);

        }
        //Log.w("img",Config.LOAD_MY_SUGGESTION_BOXES + Images_local[position]);
        return vi;
    }
}

package com.ogeniuspriority.nds.nds;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LazyAdapter_post_comments extends BaseAdapter {

    private Activity activity;
    private static String[] Images_local;
    private static String[] Names_local;
    private static String[] message;
    private static String[] Time_local;
    private static String[] Posts_local;
    private static String[] Posts_ids_local;
    private static LayoutInflater inflater = null;
    public ImageLoader imageLoader;

    public LazyAdapter_post_comments(Activity a, String[] Images, String[] Names, String[] Time, String[] Posts, String[] Posts_ids) {
        activity = a;
        Images_local = Images;
        Names_local = Names;
        Time_local = Time;
        Posts_ids_local = Posts_ids;
        Posts_local = Posts;
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
            vi = inflater.inflate(R.layout.posts_comments, null);
            // ---textId---------------------------
            TextView commentator_name = (TextView) vi.findViewById(R.id.commentator_name);
            TextView comment_date = (TextView) vi.findViewById(R.id.comment_date);
            TextView comment_dta = (TextView) vi.findViewById(R.id.comment_dta);
            ImageView commentator_avatar = (ImageView) vi.findViewById(R.id.commentator_avatar);
            commentator_name.setText(Names_local[position]);
            comment_date.setText(Time_local[position]);
            //--
            CharSequence modifiedText = Posts_local[position];
            List<String> allMatches_hash = new ArrayList<String>();
            Matcher m_hash = Pattern.compile("#(\\S+)")
                    .matcher(modifiedText);
            while (m_hash.find()) {
                allMatches_hash.add(m_hash.group());
            }
            //--------------
            List<String> allMatches_at = new ArrayList<String>();
            Matcher m_at = Pattern.compile("(\\s|\\A)@(\\w+)")
                    .matcher(modifiedText);
            while (m_at.find()) {
                allMatches_at.add(m_at.group());
            }
            //-----------------

            for (int i = allMatches_hash.size() - 1; i >= 0; i--) {
                //---------
                modifiedText = Replacer_posts.replace(modifiedText, allMatches_hash.get(i),
                        Html.fromHtml("<a href='" + allMatches_hash.get(i) + "' style='text-decoration:none;'><font color=\"red\">" + allMatches_hash.get(i) + "</font></a>"));
            }
            for (int i = allMatches_at.size() - 1; i >= 0; i--) {
                //---------
                modifiedText = Replacer_posts.replace(modifiedText, allMatches_at.get(i),
                        Html.fromHtml("<a href='" + allMatches_at.get(i) + "' style='text-decoration:none;color:green' color=\"#6666FF\"><font color=\"#6666FF\">" + allMatches_at.get(i) + "</font></a>"));
            }
            comment_dta.setLinksClickable(true);
            comment_dta.setClickable(true);
            comment_dta.setLongClickable(true);
            comment_dta.setMovementMethod(CustomLinkMovementMethod.getInstance(activity.getBaseContext()));
            comment_dta.setText(modifiedText);

            //comment_dta.setText(Posts_local[position]);


            Picasso.with(activity.getBaseContext()).load(Config.LOAD_MY_AVATAR + Images_local[position]).placeholder(R.mipmap.neza_default_avatar).into(commentator_avatar);
            return vi;
        } else {
            TextView commentator_name = (TextView) vi.findViewById(R.id.commentator_name);
            TextView comment_date = (TextView) vi.findViewById(R.id.comment_date);
            TextView comment_dta = (TextView) vi.findViewById(R.id.comment_dta);
            ImageView commentator_avatar = (ImageView) vi.findViewById(R.id.commentator_avatar);
            commentator_name.setText(Names_local[position]);
            comment_date.setText(Time_local[position]);
            //------------------------------------
            //--
            CharSequence modifiedText = Posts_local[position];
            List<String> allMatches_hash = new ArrayList<String>();
            Matcher m_hash = Pattern.compile("#(\\S+)")
                    .matcher(modifiedText);
            while (m_hash.find()) {
                allMatches_hash.add(m_hash.group());
            }
            //--------------
            List<String> allMatches_at = new ArrayList<String>();
            Matcher m_at = Pattern.compile("(\\s|\\A)@(\\w+)")
                    .matcher(modifiedText);
            while (m_at.find()) {
                allMatches_at.add(m_at.group());
            }
            //-----------------

            for (int i = allMatches_hash.size() - 1; i >= 0; i--) {
                //---------
                modifiedText = Replacer_posts.replace(modifiedText, allMatches_hash.get(i),
                        Html.fromHtml("<a href='" + allMatches_hash.get(i) + "' style='text-decoration:none;'><font color=\"red\">" + allMatches_hash.get(i) + "</font></a>"));
            }
            for (int i = allMatches_at.size() - 1; i >= 0; i--) {
                //---------
                modifiedText = Replacer_posts.replace(modifiedText, allMatches_at.get(i),
                        Html.fromHtml("<a href='" + allMatches_at.get(i) + "' style='text-decoration:none;' color=\"#6666FF\"><font color=\"#6666FF\">" + allMatches_at.get(i) + "</font></a>"));
            }
            comment_dta.setLinksClickable(true);
            comment_dta.setClickable(true);
            comment_dta.setLongClickable(true);
            comment_dta.setMovementMethod(CustomLinkMovementMethod.getInstance(activity.getBaseContext()));
            comment_dta.setText(modifiedText);


            // comment_dta.setText(Posts_local[position]);

            Picasso.with(activity.getBaseContext()).load(Config.LOAD_MY_AVATAR + Images_local[position]).placeholder(R.mipmap.neza_default_avatar).into(commentator_avatar);
            return vi;
        }
    }
}

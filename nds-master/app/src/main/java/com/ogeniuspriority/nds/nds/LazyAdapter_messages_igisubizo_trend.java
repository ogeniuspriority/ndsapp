package com.ogeniuspriority.nds.nds;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LazyAdapter_messages_igisubizo_trend extends BaseAdapter {

    private Activity activity;
    private String[] Images_local;
    private String[] Names_local;
    private String[] message;
    private String[] Time_local;
    private String[] Query_local;
    private String[] Query_Orientation_local;
    private String[] QueryProvider_local;
    private String[] QueryRemoteId_local;
    private String[] QueryId_local;
    private String[] Query_sent_local;
    private static LayoutInflater inflater = null;
    public ImageLoader imageLoader;
    public static int myRows = 0;
    NDS_db_adapter db;
    //--
    List<String> allMatches_hash;
    List<String> allMatches_at;

    public LazyAdapter_messages_igisubizo_trend(Activity a, String[] Images, String[] Names, String[] Time, String[] Query,
                                                String[] Query_Orientation, String[] QueryProvider, String[] QueryRemoteId, String[] QueryId,
                                                String[] Query_sent) {
        activity = a;
        Images_local = Images;
        Names_local = Names;
        Time_local = Time;
        Query_local = Query;
        Query_Orientation_local = Query_Orientation;
        QueryProvider_local = QueryProvider;
        QueryRemoteId_local = QueryRemoteId;
        QueryId_local = QueryId;
        Query_sent_local = Query_sent;
        //--------------------
        myRows = Query_sent.length;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity.getApplicationContext());
        db = new NDS_db_adapter(a);
        //-------------------
        db.openToWrite();
        //--
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
        if (convertView == null)
            vi = inflater.inflate(R.layout.message_answers, null);
        //---
        // ---textId---------------------------
        TextView sender = (TextView) vi.findViewById(R.id.sender);
        sender.setText(Names_local[position]);
        TextView subject = (TextView) vi.findViewById(R.id.subject);
        subject.setText("Subject:" + Query_Orientation_local[position]);
        TextView time = (TextView) vi.findViewById(R.id.time);
        time.setText(Time_local[position]);
        TextView query_body = (TextView) vi.findViewById(R.id.query_body);
        //--------------------------------------------------------------
        CharSequence modifiedText = Query_local[position];
        allMatches_hash = new ArrayList<String>();
        Matcher m_hash = Pattern.compile("#\\w+")
                .matcher(modifiedText);
        while (m_hash.find()) {
            allMatches_hash.add(m_hash.group());
        }
        //--------------
        allMatches_at = new ArrayList<String>();
        Matcher m_at = Pattern.compile("(\\s|\\A)@(\\w+)")
                .matcher(modifiedText);
        while (m_at.find()) {
            allMatches_at.add(m_at.group());
        }
        //-----------------

        for (int i = allMatches_hash.size() - 1; i >= 0; i--) {

            //---------
            modifiedText = Replacer.replace(modifiedText, allMatches_hash.get(i),
                    Html.fromHtml("<a href='" + allMatches_hash.get(i) + "' style='text-decoration:none;'><font color=\"red\">" + allMatches_hash.get(i) + "</font></a>"));
        }
        for (int i = allMatches_at.size() - 1; i >= 0; i--) {

            //---------
            modifiedText = Replacer.replace(modifiedText, allMatches_at.get(i),
                    Html.fromHtml("<a href='" + allMatches_at.get(i) + "' style='text-decoration:none;color:green;' color=\"#6666FF\"><font color=\"#6666FF\">" + allMatches_at.get(i) + "</font></a>"));
        }
        query_body.setLinksClickable(true);
        query_body.setClickable(true);
        query_body.setLongClickable(true);
        query_body.setMovementMethod(CustomLinkMovementMethod.getInstance(activity.getBaseContext()));
        query_body.setText(modifiedText);
        //----------


        //--------------------------------------------------------------
        // query_body.setText(Query_local[position]);


        //---------------------
        ImageView photo = (ImageView) vi.findViewById(R.id.photo);
        TextView repliesNber = (TextView) vi.findViewById(R.id.repliesNber);
        LinearLayout my_reply = (LinearLayout) vi.findViewById(R.id.my_reply);
        //---
        TextView Follow_up = (TextView) vi.findViewById(R.id.Follow_up);
        TextView Finalize = (TextView) vi.findViewById(R.id.Finalize);
        //--
        if (Query_sent_local[position].equalsIgnoreCase("0")) {
            ImageView error = (ImageView) vi.findViewById(R.id.error);
            error.setBackgroundResource(R.drawable.error);
            Log.w("empty---000", "" + Query_sent_local[position]);

        } else if (Query_sent_local[position].equalsIgnoreCase("1")) {
            ImageView error = (ImageView) vi.findViewById(R.id.error);
            error.setBackgroundResource(R.drawable.ok);
            Log.w("empty---111", "" + Query_sent_local[position]);
        }
        //-------------------Check the number of replies--
        db.openToWrite();
        Cursor Creds = db.queueAllQueries_responses(QueryId_local[position],Query_Orientation_local[position]);
        //--------
        if (Creds.moveToLast()) {
            my_reply.setVisibility(View.VISIBLE);
            Follow_up.setVisibility(View.VISIBLE);
            Finalize.setVisibility(View.VISIBLE);
            int repl = Creds.getCount();
            if (repl == 1) {
                repliesNber.setText(repl + "1 Reply");
            } else {
                repliesNber.setText(repl + " Replies");
            }


        } else {
            my_reply.setVisibility(View.GONE);
            repliesNber.setText(" No Reply");
            Follow_up.setVisibility(View.GONE);
            Finalize.setVisibility(View.GONE);

        }


        //-----------------------
        if (Images_local[position] != null) {
            imageLoader.DisplayImage(Config.LOAD_MY_AVATAR.toString() + Images_local[position], photo);
        } else {
            imageLoader.DisplayImage(Config.LOAD_MY_AVATAR.toString() + "avatar_default.png", photo);

        }

        //imageLoader.DisplayImage(data[position], image);
        return vi;
    }


}


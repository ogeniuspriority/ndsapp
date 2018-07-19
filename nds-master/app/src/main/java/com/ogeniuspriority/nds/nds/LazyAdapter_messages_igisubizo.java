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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LazyAdapter_messages_igisubizo extends BaseAdapter {

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
    String[] Query_data_type_local;
    private static LayoutInflater inflater = null;
    public ImageLoader imageLoader;
    public static int myRows = 0;
    NDS_db_adapter db;
    //--
    List<String> allMatches_hash;
    List<String> allMatches_at;

    public LazyAdapter_messages_igisubizo(Activity a, String[] Images, String[] Names, String[] Time, String[] Query,
                                          String[] Query_Orientation, String[] QueryProvider, String[] QueryRemoteId, String[] QueryId,
                                          String[] Query_sent, String[] Query_data_type) {
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
        Query_data_type_local = Query_data_type;
        QueryRemoteId_local = QueryRemoteId;
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
        if (!Query_local[position].isEmpty()) {
            //---------------
            TextView sender = (TextView) vi.findViewById(R.id.sender);
            ImageView settings = (ImageView) vi.findViewById(R.id.settings);
            settings.setTag(QueryRemoteId_local[position]);
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
            Finalize.setTag(QueryRemoteId_local[position]);
            Follow_up.setTag(QueryRemoteId_local[position]);
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
            Cursor Creds = db.queueAllQueries_responses(QueryRemoteId_local[position], Query_Orientation_local[position]);
            //--------
            if (Creds.moveToLast()) {
                my_reply.setVisibility(View.VISIBLE);
                Follow_up.setVisibility(View.VISIBLE);
                Finalize.setVisibility(View.VISIBLE);
                int repl = Creds.getCount();
                if (repl == 1) {
                    repliesNber.setText(repl + " Reply");
                    String theData = (Query_Orientation_local[position].equalsIgnoreCase("suggestion")) ? "suggestion" : "query";
                    repliesNber.setTag(QueryRemoteId_local[position] + "$" + "" + theData);
                } else {
                    repliesNber.setText(repl + " Replies");
                    String theData = (Query_Orientation_local[position].equalsIgnoreCase("suggestion")) ? "suggestion" : "query";
                    repliesNber.setTag(QueryRemoteId_local[position] + "$" + "" + theData);
                }
                //=---------------------append some data to the feed----------------------------
                TextView comment_send_name = (TextView) vi.findViewById(R.id.bon4);
                TextView receipt_time_stamp = (TextView) vi.findViewById(R.id.iubimsg);
                TextView actual_message = (TextView) vi.findViewById(R.id.tView5);
                //--------------------------------------------------
                comment_send_name.setText("" + Creds.getString(5));
                receipt_time_stamp.setText("" + Creds.getString(6));
                actual_message.setText("" + Creds.getString(3));
                //=================
                String theData = (Query_Orientation_local[position].equalsIgnoreCase("suggestion")) ? "suggestion" : "query";
                repliesNber.setTag(QueryRemoteId_local[position] + "-" + "" + theData);


            } else {
                my_reply.setVisibility(View.GONE);
                repliesNber.setText(" No Reply");
                String theData = (Query_Orientation_local[position].equalsIgnoreCase("suggestion")) ? "suggestion" : "query";
                repliesNber.setTag(QueryRemoteId_local[position] + "-" + "" + theData);
                Follow_up.setVisibility(View.GONE);
                Finalize.setVisibility(View.GONE);

            }
            //-----------------
            TextView query_body_url_data = (TextView) vi.findViewById(R.id.query_body_url_data);
            TextView theBtnView = (TextView) vi.findViewById(R.id.theBtnView);
            TextView query_body_act = (TextView) vi.findViewById(R.id.query_body);
            //-------------QueryRemoteId_local[position]
            if (Query_Orientation_local[position].equalsIgnoreCase("suggestion")) {
                switch (Query_data_type_local[position]) {
                    case "image":
                        query_body_act.setText("Image Suggestion");
                        theBtnView.setVisibility(View.VISIBLE);
                        query_body_url_data.setVisibility(View.VISIBLE);
                        theBtnView.setTag(QueryRemoteId_local[position]);
                        break;
                    case "text":
                        theBtnView.setVisibility(View.GONE);
                        query_body_url_data.setVisibility(View.GONE);
                        break;
                    case "audio":
                        query_body_act.setText("Audio Suggestion");
                        theBtnView.setVisibility(View.VISIBLE);
                        query_body_url_data.setVisibility(View.VISIBLE);
                        theBtnView.setTag(QueryRemoteId_local[position]);
                        break;
                    case "video":
                        query_body_act.setText("Video Suggestion");
                        theBtnView.setVisibility(View.VISIBLE);
                        query_body_url_data.setVisibility(View.VISIBLE);
                        theBtnView.setTag(QueryRemoteId_local[position]);
                        break;
                    default:
                        theBtnView.setVisibility(View.GONE);
                        query_body_url_data.setVisibility(View.GONE);
                        break;

                }
            } else {
                theBtnView.setVisibility(View.GONE);
                query_body_url_data.setVisibility(View.GONE);
            }


            //-----------------------
            if (Images_local[position] != null) {
                imageLoader.DisplayImage(Config.LOAD_MY_AVATAR.toString() + Images_local[position], photo);
            } else {
                imageLoader.DisplayImage(Config.LOAD_MY_AVATAR.toString() + "avatar_default.png", photo);

            }

            //imageLoader.DisplayImage(data[position], image);
        }
        return vi;
    }


}


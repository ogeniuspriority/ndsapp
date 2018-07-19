package com.ogeniuspriority.nds.nds;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_App_finalize_query;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Messages_Load_My_Remote_suggestions;

public class NDS_messages extends AppCompatActivity {
    NDS_db_adapter db;
    private static String MYRemoteId = "";
    private static String MYAvatar = "";
    private static String MYNames = "";
    ListView message_answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nds_messages);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //---------------------------
        //getSupportActionBar().hide();
        //---------------------


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView Toggle_me = (ImageView) findViewById(R.id.toggle_me);
        toolbar.setLogo(R.mipmap.neza_000_we);
       // toolbar.setCollapsible(true);
        //LazyAdapter_messages_igisubizo
        message_answers = (ListView) findViewById(R.id.message_answers);
        //=================================================
        db = new NDS_db_adapter(this);
        //-----------------------
        db.openToWrite();
        Cursor UserCreds = db.queueAllFromCreds();

        if (UserCreds.moveToLast()) {
            MYRemoteId = UserCreds.getString(9);
            MYAvatar = UserCreds.getString(3);
            MYNames = UserCreds.getString(1);
        } else {


        }
        //-------------------
        db.openToWrite();
        Cursor Queries_ = db.queueAllFromNDS_RGB_QUERIES();
        //------------------------------------------------------------
        //----------------------------------------
        String[] Images = new String[Queries_.getCount()];
        String[] Names = new String[Queries_.getCount()];
        String[] Time = new String[Queries_.getCount()];
        String[] Query = new String[Queries_.getCount()];
        String[] QueryOrientation = new String[Queries_.getCount()];
        String[] Query_ID = new String[Queries_.getCount()];
        String[] Query_Provider = new String[Queries_.getCount()];
        String[] Query_Remote_id = new String[Queries_.getCount()];
        String[] Query_sent = new String[Queries_.getCount()];
        String[] Query_data_type = new String[Queries_.getCount()];
        if (Queries_.moveToLast()) {
            for (int i = 0; i < Queries_.getCount(); i++) {

                String query_id = Queries_.getString(0);
                Query_ID[i] = query_id;
                String query_remote_id = Queries_.getString(1);
                Query_Remote_id[i] = query_remote_id;
                String query = Queries_.getString(2);
                Query[i] = query;
                String query_provider = Queries_.getString(5);
                Query_Provider[i] = query_provider;
                String query_time = Queries_.getString(3);
                Time[i] = query_time;
                String query_orientation = Queries_.getString(6);
                QueryOrientation[i] = query_orientation;
                Query_sent[i] = Queries_.getString(7);
                Query_data_type[i] = Queries_.getString(4);
                //--------------------------------------------------------------queueAllFromCreds_where
                db.openToWrite();
                Cursor Creds = db.queueAllFromCreds_where(query_provider);
                //--------
                if (Creds.moveToLast()) {
                    String query_Sender = Creds.getString(1);
                    Names[i] = query_Sender;
                    String query_Avatar = Creds.getString(3);
                    Images[i] = query_Avatar;
                    //--------------------------------------------
                }
                //Queries_.moveToNext();
                Queries_.moveToPrevious();
                //--------------------------------------------------------------
            }
            Log.w("number", "" + Images.length);
            //------------------------------
            message_answers.setAdapter(new LazyAdapter_messages_igisubizo(NDS_messages.this, Images, Names, Time, Query, QueryOrientation, Query_Provider,
                    Query_Remote_id, Query_ID, Query_sent, Query_data_type
            ));
            //---------------
            message_answers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(NDS_messages.this, NDS_messages_respond.class);
                    //intent.putExtra("keyId", "0");
                    startActivity(intent);
                    Toast.makeText(getBaseContext(), "ererer", Toast.LENGTH_SHORT).show();

                }
            });


        } else {


        }
        //-----------------------------------------------------------find all remote queries and suggestions made by me and save them locally
        //---update the local preview of the messages
       //Log.w("suggest", "" + MYRemoteId);
        //--------Loop the remote suggestion tracking--
        handlerRecycleActivity.postDelayed(runnableRecycleActivity, 1000);
        //-----test the notifications number---
         /*
        Cursor MSGS_FROM_INSTI_A = db.queueNOTIFICATIONS_FOR_THE_TAG("messages_from_institutions", "none", "query");
        Log.w("notif_text_msg_insti_0", "" + MSGS_FROM_INSTI_A.getCount());
        Cursor MSGS_FROM_INSTI_A_1 = db.queueNOTIFICATIONS_FOR_THE_TAG("messages_from_institutions", "none", "suggestion");
        Log.w("notif_text_msg_insti_1", "" + MSGS_FROM_INSTI_A_1.getCount());
        Cursor CMNTS_ON_POSTS_A = db.queueNOTIFICATIONS_FOR_THE_TAG_NO_ORIENT("comments_on_posts", "none");
        Log.w("notif_text_com_post", "" + CMNTS_ON_POSTS_A.getCount());
        Cursor TAG_IN_POSTS_A = db.queueNOTIFICATIONS_FOR_THE_TAG_NO_ORIENT("tags_on_posts", "none");
        Log.w("notif_text_tag_post", "" + TAG_IN_POSTS_A.getCount());
        Cursor TAG_IN_CMNTS_A = db.queueNOTIFICATIONS_FOR_THE_TAG_NO_ORIENT("tags_on_comments", "none");
        Log.w("notif_text_tag_com", "" + TAG_IN_CMNTS_A.getCount());
        Cursor KEYWORD_IN_POSTS_A = db.queueNOTIFICATIONS_FOR_THE_TAG_NO_ORIENT("keyword_on_posts", "none");
        Log.w("notif_text_key_post", "" + KEYWORD_IN_POSTS_A.getCount());
        Cursor KEYWORD_IN_CMNTS_A = db.queueNOTIFICATIONS_FOR_THE_TAG_NO_ORIENT("keyword_on_comments", "none");
        Log.w("notif_text_key_cm", "" + KEYWORD_IN_CMNTS_A.getCount());
        */


    }

    Handler handlerRecycleActivity = new Handler();
    Runnable runnableRecycleActivity = new Runnable() {
        public void run() {
            //----------find the lastid to be inserted--
            new Thread(new Runnable() {
                @Override
                public void run() {


                    db.openToWrite();
                    Cursor MyRemoteSuggestions = db.queueAllFromNDS_RGB_QUERIES_suggestions();
                    int local_suggestion = MyRemoteSuggestions.getCount();
                    if (local_suggestion > 0) {
                        if (MyRemoteSuggestions.moveToLast()) {
                            String theLastSuggestRemoteId = MyRemoteSuggestions.getString(1);
                            //----------------------
                            new My_Community_Messages_Load_My_Remote_suggestions(NDS_messages.this, Config.NDS_FIND_MY_REMOTE_SUGGESTION, MYRemoteId, theLastSuggestRemoteId, message_answers).execute();

                            //-----------------------
                        } else {


                        }
                    } else {
                        new My_Community_Messages_Load_My_Remote_suggestions(NDS_messages.this, Config.NDS_FIND_MY_REMOTE_SUGGESTION, MYRemoteId, "0", message_answers).execute();

                    }
                }
            }).start();
            handlerRecycleActivity.postDelayed(this, 1000);

        }
    };


    public void openComments(View v) {
        Intent intent = new Intent(NDS_messages.this, NDS_messages_respond.class);
        intent.putExtra("queryOrSuggId", "" + v.getTag().toString());
        //Toast.makeText(getBaseContext(),""+v.getTag().toString(),Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void open_ext_url_media(View v) {
        // Toast.makeText(getBaseContext(),"Trick me!"+v.getTag().toString(),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Config.NDS_FIND_THIS_MEDIA + v.getTag().toString()));
        startActivity(intent);
    }

    public void delete_Q_S(final View v) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        //--------------DELETE QUERY
                        if (db.DELETE_A_QUERY_FROM_LOCAL_DATABASE(v.getTag().toString())) {
                            Toast.makeText(NDS_messages.this, "Deleted! ", Toast.LENGTH_SHORT).show();
                            NDS_messages.this.recreate();
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(NDS_messages.this);
        builder.setMessage("Are you sure to delete this?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    //-------Follow up and finalize
    //-------------------follow up and finalize--------------
    public void Q_S_follow_up(View v) {
        // Toast.makeText(NDS_messages.this,""+v.getTag().toString(),Toast.LENGTH_SHORT).show();
        //-------------------
       /* Intent intent = new Intent(NDS_messages.this, follow_up_this_query.class);
        intent.putExtra("theQueryIdAndUserId", v.getTag().toString()+"-"+MYRemoteId);
        startActivity(intent);
        */
        //---------------
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Config.NDS_LOAD_COMMENTATORS_AND_THEIR_COMMENTS.toString() + "?common_user_settings_the_id=" + MYRemoteId + "&queryId_34=" + v.getTag().toString()));
        startActivity(browserIntent);

    }

    public void Q_S_finalize(final View v) {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.pop_up_finalize_this_query, null);


        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //TODO do sth here on dismiss
            }
        });

        // popupWindow.showAsDropDown(v);
        popupWindow.showAtLocation(message_answers, Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
        popupWindow.setAnimationStyle(android.R.style.TextAppearance_DeviceDefault_Small_Inverse);
        popupWindow.setFocusable(true);
        popupWindow.update();
        //--------------------------
        RadioGroup group = (RadioGroup) popupView.findViewById(R.id.radioGroup);
        final EditText editText3 = (EditText) popupView.findViewById(R.id.editText3);
        final Button button2 = (Button) popupView.findViewById(R.id.button2);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(radioButton);
                //------------------
                switch (index) {
                    case 0:
                        new My_Community_App_finalize_query(NDS_messages.this, Config.NDS_FINALIZE_THE_RGB_QUERY.toString(), "", v.getTag().toString(), "" + index, "", popupWindow).execute();
                        break;
                    case 1:
                        new My_Community_App_finalize_query(NDS_messages.this, Config.NDS_FINALIZE_THE_RGB_QUERY.toString(), "", v.getTag().toString(), "" + index, "", popupWindow).execute();
                        break;
                    case 2:
                        new My_Community_App_finalize_query(NDS_messages.this, Config.NDS_FINALIZE_THE_RGB_QUERY.toString(), "", v.getTag().toString(), "" + index, "", popupWindow).execute();
                        break;
                    case 3:


                        break;

                }

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText3.getText().length() > 2) {
                    new My_Community_App_finalize_query(NDS_messages.this, Config.NDS_FINALIZE_THE_RGB_QUERY.toString(), "", v.getTag().toString(), "3", "" + editText3.getText().toString(), popupWindow).execute();

                }
            }
        });


    }

    public void getTheCheckedOnDude(View v) {

    }


}

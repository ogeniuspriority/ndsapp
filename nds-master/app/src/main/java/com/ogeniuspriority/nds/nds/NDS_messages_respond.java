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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_App_finalize_query;
import com.ogeniuspriority.nds.nds.m_MySQL.My_NDS_Q_S_Comment;
import com.ogeniuspriority.nds.nds.m_MySQL.My_NDS_Q_S_Comment_send_data;
import com.ogeniuspriority.nds.nds.m_UI.My_Community_Posts_PicassoClient;

public class NDS_messages_respond extends AppCompatActivity {
    NDS_db_adapter db;
    public static String MYRemoteId = "";
    private static String MYAvatar = "";
    private static String MYNames = "";
    ListView message_answers;
    String[] theIdOfSuggOrQuery;
    EditText comment_2017_send;
    ImageView comment_send_2017;
    //--
    TextView igisubizo_msg;
    TextView button4;
    String suggest_or_query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nds_messages_respond);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        igisubizo_msg = (TextView) findViewById(R.id.igisubizo_msg);
        button4 = (TextView) findViewById(R.id.button4);

        //getSupportActionBar().hide();


        ImageView Toggle_me = (ImageView) findViewById(R.id.toggle_me);
        toolbar.setLogo(R.mipmap.neza_000_we);
        toolbar.setCollapsible(true);

        //LazyAdapter_messages_igisubizo
        //--------------------------
        Bundle extras = getIntent().getExtras();
        String MyPostData = extras.getString("queryOrSuggId");
        Log.w("MyPostData", "" + MyPostData);
        theIdOfSuggOrQuery = MyPostData.split("-");

        //Toast.makeText(getBaseContext(), "" + theIdOfSuggOrQuery[0] + "---" + theIdOfSuggOrQuery[1], Toast.LENGTH_SHORT).show();

        message_answers = (ListView) findViewById(R.id.message_answers);
        //---------------
        db = new NDS_db_adapter(this);
        //-----------------------
        db.openToWrite();
        //----------------------------------------
        //-----------------------
        db.openToWrite();
        Cursor UserCreds = db.queueAllFromCreds();

        if (UserCreds.moveToLast()) {
            MYRemoteId = UserCreds.getString(9);
            MYAvatar = UserCreds.getString(3);
            MYNames = UserCreds.getString(1);
        } else {


        }

        //------------------------------------
        //-------The upper part haha!------
        ImageView photo_0 = (ImageView) findViewById(R.id.photo);
        TextView sender = (TextView) findViewById(R.id.sender);
        TextView subject = (TextView) findViewById(R.id.subject);
        TextView time_ = (TextView) findViewById(R.id.time);
        TextView query_data = (TextView) findViewById(R.id.textView5);
        TextView theBtnView = (TextView) findViewById(R.id.theBtnView);
        TextView query_body_url_data = (TextView) findViewById(R.id.query_body_url_data);

        //-------------------------------
        db.openToWrite();
        Cursor Queries_For_upper_header = db.queueAllFromNDS_RGB_QUERIES_Where_query_id(theIdOfSuggOrQuery[0]);
        if (Queries_For_upper_header.moveToFirst()) {
            igisubizo_msg.setTag(theIdOfSuggOrQuery[0]);
            button4.setTag(theIdOfSuggOrQuery[0]);
            sender.setText(MYNames);
            subject.setText(Queries_For_upper_header.getString(6));
            time_.setText(Queries_For_upper_header.getString(3));
            query_data.setText(Queries_For_upper_header.getString(2));
            //---------Image Avatar-----------
            ImageLoader imageLoader;
            imageLoader = new ImageLoader(NDS_messages_respond.this.getApplicationContext());
            Log.w("MYAvatar", "" + MYAvatar);
            /*if (MYAvatar != null) {
                imageLoader.DisplayImage(Config.LOAD_MY_AVATAR.toString() + MYAvatar, photo_0);
            } else {
                imageLoader.DisplayImage(Config.LOAD_MY_AVATAR.toString() + "avatar_default.png", photo_0);

            }*/
            //---------------------
            suggest_or_query = Queries_For_upper_header.getString(6);
            if (Queries_For_upper_header.getString(6).equalsIgnoreCase("suggestion")) {
                switch (Queries_For_upper_header.getString(4)) {
                    case "image":
                        query_data.setText("Image Suggestion");
                        theBtnView.setVisibility(View.VISIBLE);
                        query_body_url_data.setVisibility(View.VISIBLE);
                        theBtnView.setTag(Queries_For_upper_header.getString(1));
                        break;
                    case "text":
                        theBtnView.setVisibility(View.GONE);
                        query_body_url_data.setVisibility(View.GONE);
                        break;
                    case "audio":
                        query_data.setText("Audio Suggestion");
                        theBtnView.setVisibility(View.VISIBLE);
                        query_body_url_data.setVisibility(View.VISIBLE);
                        theBtnView.setTag(Queries_For_upper_header.getString(1));
                        break;
                    case "video":
                        query_data.setText("Video Suggestion");
                        theBtnView.setVisibility(View.VISIBLE);
                        query_body_url_data.setVisibility(View.VISIBLE);
                        theBtnView.setTag(Queries_For_upper_header.getString(1));
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
            //------------------
            My_Community_Posts_PicassoClient.downloadImage(NDS_messages_respond.this, Config.LOAD_MY_AVATAR.toString() + MYAvatar, photo_0);
            //-----------send the comment------------
            comment_2017_send = (EditText) findViewById(R.id.comment_2017_send);
            comment_send_2017 = (ImageView) findViewById(R.id.comment_send_2017);
            //---------------
            comment_send_2017.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (comment_2017_send.getText().toString().length() > 1) {
                        new My_NDS_Q_S_Comment_send_data(NDS_messages_respond.this, Config.NDS_SEND_THIS_QUERY_SUGG_COMMENT_SEND.toString(), comment_2017_send.getText().toString(), MYRemoteId, theIdOfSuggOrQuery[0], comment_2017_send, theIdOfSuggOrQuery[1], "0").execute();

                    }

                }
            });

        }
        //----------------------------------------

        //-----------------------------------
        db.openToWrite();
        Cursor Queries_response = db.queueAllQueries_responses(theIdOfSuggOrQuery[0], theIdOfSuggOrQuery[1]);
        //------------------------------------------------------------
        String[] sender_names = new String[Queries_response.getCount()];
        String[] reg_dates = new String[Queries_response.getCount()];
        String[] Query_response_data = new String[Queries_response.getCount()];
        String[] sender_ids = new String[Queries_response.getCount()];
        String[] query_reponse_local_id = new String[Queries_response.getCount()];
        String[] query_reponse_remote_id = new String[Queries_response.getCount()];

        if (Queries_response.moveToLast()) {
            for (int i = 0; i < Queries_response.getCount(); i++) {
                sender_names[i] = Queries_response.getString(5);
                reg_dates[i] = Queries_response.getString(6);
                Query_response_data[i] = Queries_response.getString(3);
                sender_ids[i] = Queries_response.getString(1);
                query_reponse_local_id[i] = Queries_response.getString(0);
                query_reponse_remote_id[i] = Queries_response.getString(7);
                Queries_response.moveToPrevious();
            }
        }
        //------------------------------
        message_answers.setAdapter(new LazyAdapter_messages_igisubizo_repond(NDS_messages_respond.this, sender_names, reg_dates, Query_response_data, sender_ids, query_reponse_local_id, query_reponse_remote_id));
        //---------------
        message_answers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        handlerRecycleActivity.postDelayed(runnableRecycleActivity, 1000);
        //-----------hide soft keyboard

        InputMethodManager imm = (InputMethodManager) NDS_messages_respond.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (comment_2017_send != null) {
            imm.hideSoftInputFromWindow(comment_2017_send.getWindowToken(), 0);
        }

//--------------------Update the notification table----------
        ///---------------Special approach-----------
        //--------------------------
        try {
           // Bundle extras_sp_approach = getIntent().getExtras();
            String My_notif_id_sp_approach = ""+extras.getInt("trick");
            db.openToWrite();
            if (db.Update_APP_NOTIFICATION_TO_SEEN_FOR_QUERIES_AND_SUGGESTS(My_notif_id_sp_approach, theIdOfSuggOrQuery[1])) {
                Log.w("DOneDeal", My_notif_id_sp_approach+"" + theIdOfSuggOrQuery[1]);

            } else {
                Log.w("DOneDeal", My_notif_id_sp_approach+"failed==" + theIdOfSuggOrQuery[1] + "--" + theIdOfSuggOrQuery[0]);
            }
        } catch (RuntimeException exc) {
            Log.w("DOneDeal", ""+exc.toString());
        }
        //---------------------------


    }

    Handler handlerRecycleActivity = new Handler();
    Runnable runnableRecycleActivity = new Runnable() {
        public void run() {
            //---------Send a query comment online and --------------------------------
            db.openToWrite();
            Cursor MyRemoteSuggestions = db.queueAllQueries_responses(theIdOfSuggOrQuery[0], theIdOfSuggOrQuery[1]);
            int local_suggestion = MyRemoteSuggestions.getCount();

            if (local_suggestion > 0) {
                if (MyRemoteSuggestions.moveToLast()) {
                    String theLastSuggestRemoteId = MyRemoteSuggestions.getString(7);
                    String theLastSuggestOrQuery = theIdOfSuggOrQuery[1];
                    //----------------------
                    Log.w("isParsed_passed", "isaved" + local_suggestion + "==" + theLastSuggestRemoteId);
                    new My_NDS_Q_S_Comment(NDS_messages_respond.this, Config.NDS_SEND_THIS_QUERY_SUGG_COMMENT, MYRemoteId, theLastSuggestRemoteId, theIdOfSuggOrQuery[0], message_answers, theIdOfSuggOrQuery[1], theLastSuggestRemoteId).execute();

                    //-----------------------
                } else {


                }
            } else {
                new My_NDS_Q_S_Comment(NDS_messages_respond.this, Config.NDS_SEND_THIS_QUERY_SUGG_COMMENT, MYRemoteId, "0", theIdOfSuggOrQuery[0], message_answers, theIdOfSuggOrQuery[1], "0").execute();

            }


            //----------------------------
            handlerRecycleActivity.postDelayed(this, 1000);

        }
    };

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

    public void delete_Q_S_response(final View v) {
        //--------------DELETE QUERY
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        if (db.DELETE_A_QUERY_RESPONSE_FROM_LOCAL_DATABASE(v.getTag().toString())) {
                            Toast.makeText(NDS_messages_respond.this, "Deleted!", Toast.LENGTH_SHORT).show();
                            NDS_messages_respond.this.recreate();
                        }
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(NDS_messages_respond.this);
        builder.setMessage("Are you sure to delete this?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    //-------------------follow up and finalize--------------
    public void Q_S_res_follow_up(View v) {
       /* Intent intent = new Intent(NDS_messages_respond.this, follow_up_this_query.class);
        intent.putExtra("theQueryIdAndUserId", v.getTag().toString()+"-"+MYRemoteId);
        startActivity(intent);
        */
        //--------------------------browser intent
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Config.NDS_LOAD_COMMENTATORS_AND_THEIR_COMMENTS.toString() + "?common_user_settings_the_id=" + MYRemoteId + "&queryId_34=" + v.getTag().toString()));
        startActivity(browserIntent);

    }

    public void Q_S_res_finalize(final View v) {
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
        //------------
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
                        new My_Community_App_finalize_query(NDS_messages_respond.this, Config.NDS_FINALIZE_THE_RGB_QUERY.toString(), "", v.getTag().toString(), "" + index, "", popupWindow).execute();
                        break;
                    case 1:
                        new My_Community_App_finalize_query(NDS_messages_respond.this, Config.NDS_FINALIZE_THE_RGB_QUERY.toString(), "", v.getTag().toString(), "" + index, "", popupWindow).execute();
                        break;
                    case 2:
                        new My_Community_App_finalize_query(NDS_messages_respond.this, Config.NDS_FINALIZE_THE_RGB_QUERY.toString(), "", v.getTag().toString(), "" + index, "", popupWindow).execute();
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
                    new My_Community_App_finalize_query(NDS_messages_respond.this, Config.NDS_FINALIZE_THE_RGB_QUERY.toString(), "", v.getTag().toString(), "3", "" + editText3.getText().toString(), popupWindow).execute();

                }
            }
        });


    }

}

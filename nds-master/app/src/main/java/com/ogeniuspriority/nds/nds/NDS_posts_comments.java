package com.ogeniuspriority.nds.nds;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Comments_Retreive_Downloader;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Comments_Retreive_Downloader_count;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Comments_Retreive_Downloader_old;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Comments_Send;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Posts_UpdateViews;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class NDS_posts_comments extends AppCompatActivity {
    NDS_db_adapter db;
    private static String MYRemoteId = "";
    private static String MYAvatar = "";
    private static String MYNames = "";
    //-------------
    static String posts_post_id = "";
    static String posts_post_Comments;
    static String posts_post_Comments_main_post_id;
    static String posts_post_views;
    static String posts_post_likes;
    static String posts_post_unlikes;
    static String Avatar_;
    static String CommentsNumber;
    //-------
    SwipeRefreshLayout swipeView;
    ListView message_answers;
    public static EditText comment_field;
    //---------------
    public static TextView comments_nber;
    //-------

    Handler m_handler;
    Runnable m_handlerTask;
    static TextView infoText;
    static TextView infoLoadNew;
    public static volatile int new_post_comment_available = 0;
    public static volatile int new_post_comment_available_reserve = 0;
    //--
    public static volatile int lastId_Of_post_comment_Retreived = 0;
    public static volatile int firstId_Of_post_comment_Retreived = 0;
    public static View loadMoreView;
    static volatile ConnectivityManager cm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nds_posts_comments);

        //-------------------
        loadMoreView = ((LayoutInflater) NDS_posts_comments.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.forum_main_feed_load_more, null, false);
        //-------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new NDS_db_adapter(this);
        //-------------------
        db.openToWrite();
        Cursor UserCreds = db.queueAllFromCreds();

        if (UserCreds.moveToLast()) {
            MYRemoteId = UserCreds.getString(9);
            MYAvatar = UserCreds.getString(3);
            MYNames = UserCreds.getString(1);
        } else {


        }
        //---------------
        ImageView post_comment_avatar = (ImageView) findViewById(R.id.post_comment_avatar);
        TextView poster_poster_name = (TextView) findViewById(R.id.poster_poster_name);
        TextView post_post_timestamp = (TextView) findViewById(R.id.post_post_timestamp);
        TextView post_post = (TextView) findViewById(R.id.post_post);
        comments_nber = (TextView) findViewById(R.id.comments_nber);


        ImageView Toggle_me = (ImageView) findViewById(R.id.toggle_me);
        toolbar.setLogo(R.mipmap.neza_000_we);
        toolbar.setCollapsible(true);
        //LazyAdapter_messages_igisubizo
        message_answers = (ListView) findViewById(R.id.message_answers);
        //------------------------------
        //message_answers.setAdapter(new LazyAdapter_post_comments(NDS_posts_comments.this, Images, Names, Time, Posts));
        //---------------
        message_answers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        //---------
        Bundle extras = getIntent().getExtras();
        String[] MyPostData = extras.getStringArray("MyPostData");

        //String Dislikes=extras.get("Dislikes").toString();
        //byte[] byteArray=extras.getByteArray("imgProfileByteArray");
        //---------
        try {
            //------------
            //Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            // post_comment_avatar.setImageBitmap(bmp);
            //----add zooming gestures ---

        } catch (RuntimeException fs) {

        }

        poster_poster_name.setText(MyPostData[0]);
        post_post_timestamp.setText(MyPostData[1]);
        post_post.setText(MyPostData[2]);
        //-------------------------
        posts_post_id = MyPostData[7];
        posts_post_Comments = MyPostData[8];
        comments_nber.setText(MyPostData[10]);
        CommentsNumber = MyPostData[10];
        posts_post_Comments_main_post_id = MyPostData[3];
        posts_post_views = MyPostData[6];
        posts_post_likes = MyPostData[4];
        posts_post_unlikes = MyPostData[5];
        Avatar_ = MyPostData[9];
        Picasso.with(getBaseContext()).load(Config.LOAD_MY_AVATAR + MyPostData[9]).placeholder(R.mipmap.neza_default_avatar).into(post_comment_avatar);
//------------------
        swipeView = (SwipeRefreshLayout) findViewById(R.id.swipe_view);

        //-----------------------------------------------------
        swipeView.setColorSchemeColors(Color.GRAY, Color.GREEN, Color.BLUE,
                Color.RED, Color.CYAN);
        swipeView.setDistanceToTriggerSync(20);// in dips
        swipeView.setSize(SwipeRefreshLayout.LARGE);// LARGE also can be used
        swipeView.canChildScrollUp();
        //-----------------------------
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //------------
                new My_Community_Comments_Retreive_Downloader(NDS_posts_comments.this, Config.NDS_LOAD_ALLL_RELATED_COMMENTS, message_answers, swipeView, posts_post_id, "", "up").execute();

                if (!swipeView.isRefreshing()) {
                    swipeView.setRefreshing(true);
                    //new My_Community_Comments_Retreive_Downloader(NDS_posts_comments.this, Config.NDS_LOAD_ALLL_RELATED_COMMENTS, message_answers, swipeView, posts_post_id, "", "up").execute();
                } else {
                    swipeView.setRefreshing(false);
                }

            }
        });
        message_answers.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                int topRowVerticalPosition = (message_answers == null ||
                        message_answers.getChildCount() == 0) ? 0 : message_answers.getChildAt(0).getTop();

                swipeView.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
                //-------------
                swipeView.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
                //-------------------------------

                //----------------------


            }
        });
        //---------------------Send comments to the server--
        comment_field = (EditText) findViewById(R.id.comment_field);
        ImageView send_cmment_btn = (ImageView) findViewById(R.id.send_cmment_btn);
        //---
        send_cmment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (comment_field.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), "Comment please!?", Toast.LENGTH_SHORT).show();
                } else {
                    new My_Community_Comments_Send(NDS_posts_comments.this, Config.NDS_CREATE_A_NEW_COMMENT_POST, comment_field.getText().toString(), MYRemoteId, posts_post_id, message_answers, swipeView).execute();

                }
            }
        });
        //----------------Try to Parse Comments Json String--
        //comment_field.setText(posts_post_Comments);
        //---populate the comments after
        //------------------
        int counter = 0;
        try {
            String wee = Urubuga_Services.AllComments + "}]";
            JSONArray posts_Comments_Array = new JSONArray(wee);
            //---
            int myComments = Integer.parseInt(posts_post_Comments_main_post_id.split("Cmnts")[0].split(" ")[0]);
            String[] commentP_data = new String[myComments];
            String[] commentP_avatar = new String[myComments];
            String[] commentP_commentator_names = new String[myComments];
            String[] commentP_regadate = new String[myComments];
            String[] commentP_Posts_id = new String[myComments];
            //-------------
            NDS_posts_comments.lastId_Of_post_comment_Retreived = Integer.parseInt(posts_Comments_Array
                    .getJSONObject(
                            posts_Comments_Array.length() - 1)
                    .getString(
                            "ogenius_nds_db_community_posts_comments_id")
                    .toString());

            NDS_posts_comments.firstId_Of_post_comment_Retreived = Integer.parseInt(posts_Comments_Array
                    .getJSONObject(
                            0)
                    .getString(
                            "ogenius_nds_db_community_posts_comments_id")
                    .toString());

            // comment_field.setText(""+posts_Comments_Array.length());

            for (int i = 0; i < posts_Comments_Array.length(); i++) {

                //---------
                if (posts_Comments_Array
                        .getJSONObject(
                                i)
                        .getString(
                                "ogenius_nds_db_community_posts_comments_posts_post_id")
                        .toString().equalsIgnoreCase(posts_post_id)) {

                    if (counter < commentP_data.length) {


                        commentP_Posts_id[counter] = posts_Comments_Array
                                .getJSONObject(
                                        i)
                                .getString(
                                        "ogenius_nds_db_community_posts_comments_posts_post_id")
                                .toString();


                        commentP_data[counter] = posts_Comments_Array
                                .getJSONObject(
                                        i)
                                .getString(
                                        "ogenius_nds_db_community_posts_comments_comment")
                                .toString();
                        //----


                        //--
                        commentP_avatar[counter] = posts_Comments_Array
                                .getJSONObject(
                                        i)
                                .getString(
                                        "ogenius_nds_db_normal_users_avatar")
                                .toString();
                        commentP_commentator_names[counter] = posts_Comments_Array
                                .getJSONObject(
                                        i)
                                .getString(
                                        "ogenius_nds_db_normal_users_names")
                                .toString();
                        commentP_regadate[counter] = posts_Comments_Array
                                .getJSONObject(
                                        i)
                                .getString(
                                        "ogenius_nds_db_community_posts_comments_regdate")
                                .toString();
                    }


                    counter++;
                }
            }

            //---

            for (int i = 0; i < commentP_Posts_id.length; i++) {
                //Log.w("yyyy",""+commentP_data[i]+"--"+commentP_Posts_id[i]);
            }

            message_answers.setAdapter(null);
            message_answers.setAdapter(new LazyAdapter_post_comments(NDS_posts_comments.this, commentP_avatar, commentP_commentator_names, commentP_regadate, commentP_data, commentP_Posts_id));
            //----------------------
            // message_answers.deferNotifyDataSetChanged();


        } catch (JSONException e) {
            e.printStackTrace();
        }
        //------------
        new My_Community_Posts_UpdateViews(NDS_posts_comments.this, Config.NDS_UPDATE_POST_VIEWS, posts_post_id).execute();
        //----------------auto save posts---to library--
        //----delete residue posts
        if (db.DELETE_All_incomplete_POST_FROM_NEZA_COMMUNITY_POSTS()) {
            //------------------------
            Cursor allPostsAccount = db.GET_ALL_NEZA_COMMUNITY_POSTS();
            if (allPostsAccount.getCount() > 30) {
                if (allPostsAccount.moveToLast()) {
                    int lastId = allPostsAccount.getInt(0);
                    int remoteId = allPostsAccount.getInt(1);
                    //---delete the first in-
                    if (db.DELETE_A_POST_FROM_NEZA_COMMUNITY_POSTS("" + remoteId)) {
                        //----------------save the new post locally now
                        Cursor postsCheckRedudancy = db.GET_A_PARTICULA_NEZA_COMMUNITY_POSTS(posts_post_id);
                        if (postsCheckRedudancy.getCount() == 0) {
                            if (db.save_NEZA_COMMUNITY_POSTS_locally(posts_post_id, post_post_timestamp.getText().toString(), post_post.getText().toString(), poster_poster_name.getText().toString(), "", Urubuga_Services.AllComments, posts_post_views, posts_post_unlikes, posts_post_likes, Avatar_, CommentsNumber)) {

                            }
                        } else {
                            if (db.Update_NEZA_COMMUNITY_POSTS_LOCALLY(posts_post_id, post_post_timestamp.getText().toString(), post_post.getText().toString(), poster_poster_name.getText().toString(), "", Urubuga_Services.AllComments, posts_post_views, posts_post_unlikes, posts_post_likes, Avatar_, CommentsNumber)) {

                            }
                        }

                    }
                }

            } else {
                Cursor postsCheckRedudancy = db.GET_A_PARTICULA_NEZA_COMMUNITY_POSTS(posts_post_id);
                if (postsCheckRedudancy.getCount() == 0) {
                    if (db.save_NEZA_COMMUNITY_POSTS_locally(posts_post_id, post_post_timestamp.getText().toString(), post_post.getText().toString(), poster_poster_name.getText().toString(), "", Urubuga_Services.AllComments, posts_post_views, posts_post_unlikes, posts_post_likes, Avatar_, CommentsNumber)) {

                    }
                } else {
                    if (db.Update_NEZA_COMMUNITY_POSTS_LOCALLY(posts_post_id, post_post_timestamp.getText().toString(), post_post.getText().toString(), poster_poster_name.getText().toString(), "", Urubuga_Services.AllComments, posts_post_views, posts_post_unlikes, posts_post_likes, Avatar_, CommentsNumber)) {

                    }
                }

            }

        } else {
            Cursor allPostsAccount = db.GET_ALL_NEZA_COMMUNITY_POSTS();
            if (allPostsAccount.getCount() > 30) {
                if (allPostsAccount.moveToLast()) {
                    int lastId = allPostsAccount.getInt(0);
                    int remoteId = allPostsAccount.getInt(1);
                    //---delete the first in-
                    if (db.DELETE_A_POST_FROM_NEZA_COMMUNITY_POSTS("" + remoteId)) {
                        //----------------save the new post locally now
                        Cursor postsCheckRedudancy = db.GET_A_PARTICULA_NEZA_COMMUNITY_POSTS(posts_post_id);
                        if (postsCheckRedudancy.getCount() == 0) {
                            if (db.save_NEZA_COMMUNITY_POSTS_locally(posts_post_id, post_post_timestamp.getText().toString(), post_post.getText().toString(), poster_poster_name.getText().toString(), "", Urubuga_Services.AllComments, posts_post_views, posts_post_unlikes, posts_post_likes, Avatar_, CommentsNumber)) {

                            }
                        } else {
                            if (db.Update_NEZA_COMMUNITY_POSTS_LOCALLY(posts_post_id, post_post_timestamp.getText().toString(), post_post.getText().toString(), poster_poster_name.getText().toString(), "", Urubuga_Services.AllComments, posts_post_views, posts_post_unlikes, posts_post_likes, Avatar_, CommentsNumber)) {

                            }
                        }

                    }
                }

            } else {
                Cursor postsCheckRedudancy = db.GET_A_PARTICULA_NEZA_COMMUNITY_POSTS(posts_post_id);
                if (postsCheckRedudancy.getCount() == 0) {
                    if (db.save_NEZA_COMMUNITY_POSTS_locally(posts_post_id, post_post_timestamp.getText().toString(), post_post.getText().toString(), poster_poster_name.getText().toString(), "", Urubuga_Services.AllComments, posts_post_views, posts_post_unlikes, posts_post_likes, Avatar_, CommentsNumber)) {

                    }
                } else {
                    if (db.Update_NEZA_COMMUNITY_POSTS_LOCALLY(posts_post_id, post_post_timestamp.getText().toString(), post_post.getText().toString(), poster_poster_name.getText().toString(), "", Urubuga_Services.AllComments, posts_post_views, posts_post_unlikes, posts_post_likes, Avatar_, CommentsNumber)) {

                    }
                }

            }

        }
        infoText = (TextView) findViewById(R.id.infoText);
        infoLoadNew = (TextView) findViewById(R.id.infoLoadNew);
        m_handler = new Handler();
        m_handlerTask = new Runnable() {
            @Override
            public void run() {
                //-------------------------
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {


                            cm = (ConnectivityManager) NDS_posts_comments.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo netInfo = cm.getActiveNetworkInfo();
                            if (netInfo != null && netInfo.isConnected()) {
                                try {
                                    URL url = new URL(Config.IP_ADDRESS);   // Change to "http://google.com" for www  test.
                                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                                    urlc.setConnectTimeout(10 * 1000);          // 10 s.
                                    urlc.connect();
                                    if (urlc.getResponseCode() == 200) {        // 200 = "OK" code (http connection is fine).
                                        Log.wtf("Connection", "Success !");


                                        try {
                                            if (posts_post_id != null) {
                                                String reslt_new_posts_comments = new My_Community_Comments_Retreive_Downloader_count(NDS_posts_comments.this, Config.NDS_LOAD_ALLL_RELATED_COMMENTS_COUNT, posts_post_id).execute().get();
                                                //-----------
                                                if (reslt_new_posts_comments != null) {
                                                    reslt_new_posts_comments = reslt_new_posts_comments.replaceAll("\\s", "");

                                                    new_post_comment_available = Integer.parseInt(reslt_new_posts_comments);
                                                    Log.w("2017", "" + new_post_comment_available);
                                                    if (new_post_comment_available != 0) {
                                                        new_post_comment_available_reserve = new_post_comment_available;
                                                    }

                                                    if (infoText != null) {
                                                        if (new_post_comment_available_reserve == 0) {
                                                            infoText.setVisibility(View.VISIBLE);
                                                            infoLoadNew.setVisibility(View.GONE);
                                                        } else if (new_post_comment_available_reserve > 1 && new_post_comment_available_reserve<84) {
                                                            infoText.setVisibility(View.GONE);
                                                            infoLoadNew.setVisibility(View.VISIBLE);
                                                            infoLoadNew.setText(new_post_comment_available_reserve + " new Comments");
                                                            new_post_comment_available = 0;
                                                            new_post_comment_available_reserve = 0;
                                                        }
                                                    }
                                                }
                                            }

                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        } catch (ExecutionException e) {
                                            e.printStackTrace();
                                        }

                                    } else {

                                    }
                                } catch (MalformedURLException e1) {

                                } catch (IOException e) {

                                }
                            }
                        } catch (RuntimeException sdbsgd) {

                        }
                    }
                }).start();
                //-----------------
                m_handler.postDelayed(m_handlerTask, 7000);
            }
        };
        m_handlerTask.run();

        //----------------------------------------


    }

    public void loadMorePreviewsPosts(View v) {
        new My_Community_Comments_Retreive_Downloader_old(NDS_posts_comments.this, Config.NDS_LOAD_ALL_RELATED_COMMENTS_OLD, message_answers, swipeView, posts_post_id, "" + lastId_Of_post_comment_Retreived, "up").execute();

    }

    public void refreshAndScroll(View v) throws ExecutionException, InterruptedException {
        String res = new My_Community_Comments_Retreive_Downloader(NDS_posts_comments.this, Config.NDS_LOAD_ALLL_RELATED_COMMENTS, message_answers, swipeView, posts_post_id, "" + firstId_Of_post_comment_Retreived, "up").execute().get();

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
}

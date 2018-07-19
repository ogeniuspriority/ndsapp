package com.ogeniuspriority.nds.nds;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Single_Post_Downloader;

public class View_NDS_posts_comments extends AppCompatActivity {
    NDS_db_adapter db;
    public static String MYRemoteId = "";
    private static String MYAvatar = "";
    private static String MYNames = "";
    //-----------
    ListView list_k_in_post;
    ListView list_k_in_comment;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_view__nds_posts_comments);
        ///---locate the listviews
        list_k_in_post = (ListView) findViewById(R.id.list_k_in_post);
        list_k_in_comment = (ListView) findViewById(R.id.list_k_in_comment);
        progress = new ProgressDialog(this);
        Button back_finish = (Button) findViewById(R.id.back_finish);
        back_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
        //----------------------------------
        //--------------------------
        Bundle extras = getIntent().getExtras();
        String MyPostData = extras.getString("the_post_id");
        Log.w("MyPostData", "" + MyPostData);
        String[] idOfPosts = MyPostData.split("-");
        String thePostId = idOfPosts[0];
        db.openToWrite();


        //---------------------------
        //----------case data observer--
        switch (idOfPosts[1]) {
            case "comments":
                //--------------------Update the notification table----------
                try {

                    String My_notif_id_sp_approach = "" + extras.getInt("trick");
                    db.openToWrite();
                    if (db.Update_APP_NOTIFICATION_TO_SEEN_FOR_QUERIES_AND_SUGGESTS(My_notif_id_sp_approach, "comments_on_posts")) {
                        Log.w("DOneDeal", "okay" + thePostId);
                        //------show progress bar and continue--
                        progress = new ProgressDialog(this);
                        progress.setMessage("loading...");
                        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progress.setIndeterminate(true);
                        progress.setProgress(0);
                        progress.show();

                        final int totalProgressTime = 100;
                        final Thread t = new Thread() {
                            @Override
                            public void run() {
                                int jumpTime = 0;

                                while (jumpTime < totalProgressTime) {
                                    try {
                                        sleep(200);
                                        jumpTime += 5;
                                        progress.setProgress(jumpTime);
                                    } catch (InterruptedException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }
                        };
                        t.start();
                        //-------------------then load the posts
                        new My_Community_Single_Post_Downloader(View_NDS_posts_comments.this, Config.NDS_COMMUNITY_LOAD_SINGLE_POST, list_k_in_post, ""  , MYRemoteId,progress,thePostId).execute();


                    } else {
                        Log.w("DOneDeal", "failed==");
                    }
                } catch (RuntimeException exc) {
                    Log.w("DOneDeal", "" + exc.toString());
                }
                break;
            case "tags_on_posts":
                try {

                    String My_notif_id_sp_approach = "" + extras.getInt("trick");
                    db.openToWrite();
                    if (db.Update_APP_NOTIFICATION_TO_SEEN_FOR_QUERIES_AND_SUGGESTS(My_notif_id_sp_approach, "tags_on_posts")) {
                        Log.w("DOneDeal", "okay" + thePostId);
                        //------show progress bar and continue--
                        progress = new ProgressDialog(this);
                        progress.setMessage("loading...");
                        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progress.setIndeterminate(true);
                        progress.setProgress(0);
                        progress.show();

                        final int totalProgressTime = 100;
                        final Thread t = new Thread() {
                            @Override
                            public void run() {
                                int jumpTime = 0;

                                while (jumpTime < totalProgressTime) {
                                    try {
                                        sleep(200);
                                        jumpTime += 5;
                                        progress.setProgress(jumpTime);
                                    } catch (InterruptedException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }
                        };
                        t.start();
                        //-------------------then load the posts
                        new My_Community_Single_Post_Downloader(View_NDS_posts_comments.this, Config.NDS_COMMUNITY_LOAD_SINGLE_POST, list_k_in_post, ""  , MYRemoteId,progress,thePostId).execute();


                    } else {
                        Log.w("DOneDeal", "failed==");
                    }
                } catch (RuntimeException exc) {
                    Log.w("DOneDeal", "" + exc.toString());
                }

                break;
            case "notif_using_tags_on_comments":

                break;
            case "notif_using_keyword_on_posts":
                //--------------------Update the notification table----------
                try {

                    String My_notif_id_sp_approach = "" + extras.getInt("trick");
                    db.openToWrite();
                    if (db.Update_APP_NOTIFICATION_TO_SEEN_FOR_QUERIES_AND_SUGGESTS(My_notif_id_sp_approach, "keyword_on_posts")) {
                        Log.w("DOneDeal", "okay" + thePostId);
                        //------show progress bar and continue--
                        progress = new ProgressDialog(this);
                        progress.setMessage("loading...");
                        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progress.setIndeterminate(true);
                        progress.setProgress(0);
                        progress.show();

                        final int totalProgressTime = 100;
                        final Thread t = new Thread() {
                            @Override
                            public void run() {
                                int jumpTime = 0;

                                while (jumpTime < totalProgressTime) {
                                    try {
                                        sleep(200);
                                        jumpTime += 5;
                                        progress.setProgress(jumpTime);
                                    } catch (InterruptedException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }
                        };
                        t.start();
                        //-------------------then load the posts
                        new My_Community_Single_Post_Downloader(View_NDS_posts_comments.this, Config.NDS_COMMUNITY_LOAD_SINGLE_POST, list_k_in_post, ""  , MYRemoteId,progress,thePostId).execute();


                    } else {
                        Log.w("DOneDeal", "failed==");
                    }
                } catch (RuntimeException exc) {
                    Log.w("DOneDeal", "" + exc.toString());
                }
                break;
            case "notif_using_keyword_on_comments":
                try {

                    String My_notif_id_sp_approach = "" + extras.getInt("trick");
                    db.openToWrite();
                    if (db.Update_APP_NOTIFICATION_TO_SEEN_FOR_QUERIES_AND_SUGGESTS(My_notif_id_sp_approach, "keyword_on_comments")) {
                        Log.w("DOneDeal", "okay" + thePostId);
                        //------show progress bar and continue--
                        progress = new ProgressDialog(this);
                        progress.setMessage("loading...");
                        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progress.setIndeterminate(true);
                        progress.setProgress(0);
                        progress.show();

                        final int totalProgressTime = 100;
                        final Thread t = new Thread() {
                            @Override
                            public void run() {
                                int jumpTime = 0;

                                while (jumpTime < totalProgressTime) {
                                    try {
                                        sleep(200);
                                        jumpTime += 5;
                                        progress.setProgress(jumpTime);
                                    } catch (InterruptedException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }
                        };
                        t.start();
                        //------
                        //-------------------then load the posts
                        new My_Community_Single_Post_Downloader(View_NDS_posts_comments.this, Config.NDS_COMMUNITY_LOAD_SINGLE_POST_COMMENT, list_k_in_comment, ""  , MYRemoteId,progress,thePostId).execute();


                    } else {
                        Log.w("DOneDeal", "failed==");
                    }
                } catch (RuntimeException exc) {
                    Log.w("DOneDeal", "" + exc.toString());
                }
                break;
        }
    }
}

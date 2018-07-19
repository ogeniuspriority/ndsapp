package com.ogeniuspriority.nds.nds;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ogeniuspriority.nds.nds.m_MySQL.My_COMMENTS_KEYWORD_Downloader;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_App_Get_Avatar;
import com.ogeniuspriority.nds.nds.m_MySQL.My_NDS_GET_the_insti_to_ask_Downloader;
import com.ogeniuspriority.nds.nds.m_MySQL.My_POSTS_Comments_Downloader_A;
import com.ogeniuspriority.nds.nds.m_MySQL.My_POSTS_KEYWORD_Downloader;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Q_S_Comments_Downloader;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Q_S_Comments_Downloader_B;
import com.ogeniuspriority.nds.nds.m_UI.My_Community_Posts_PicassoClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class NDS_main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    FloatingActionButton fab;
    DrawerLayout drawer;
    NavigationView navigationView;
    private static String MYRemoteId = "";
    private static String MYAvatar = "";
    private static String MYNames = "";
    NDS_db_adapter db;
    //------------
    StringBuffer response;
    //--
    public static  ImageView forum_trigger;
    private static volatile String theLastResponse = "";
    static ImageView pool;
    Spinner the_blessed_institutions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nds_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        pool = (ImageView) findViewById(R.id.toggle_me);
        setSupportActionBar(toolbar);
        //--find my remote id---
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
        //----find if settings are made as promised
        Cursor settings_ = db.GET_ALL_NDS_APP_LOCAL_SETTINGS();
        if (settings_.getCount() > 0) {


        } else {
            //----First time registration
            // settings_.moveToFirst();
            if (db.save_ALL_NDS_APP_LOCAL_SETTINGS("0", "playsound", "1")) {
                Intent intent = new Intent(NDS_main.this, Profile_edit.class);
                //intent.putExtra("keyId", "0");
                startActivity(intent);
            }

        }

        //--------------------
        ImageView Toggle_me = (ImageView) findViewById(R.id.toggle_me);
       // toolbar.setLogo(R.mipmap.neza_000_we);
        //------------------------
        View avatar_profile = getLayoutInflater().inflate(R.layout.remote_up_nav, null);
        ImageView img = (ImageView) avatar_profile.findViewById(R.id.img_avatar);


        //---------------
        BitmapDrawable ball = (BitmapDrawable) getBaseContext().getResources().getDrawable(R.mipmap.box_0000);
        Bitmap b_ball_1 = ((BitmapDrawable) ball).getBitmap();
        Bitmap bm_89 = Bitmap.createScaledBitmap(b_ball_1, (int) 120, (int) 120, false);
        //-------------------
        ImageLoader imageLoader = new ImageLoader(getApplicationContext());
        if (MYAvatar != null) {
            // imageLoader.DisplayImage(Config.LOAD_MY_AVATAR.toString() + MYAvatar, img);
            // Picasso.with(getBaseContext()).load(Config.LOAD_MY_AVATAR.toString() + MYAvatar).placeholder(R.mipmap.neza_default_avatar).into(img);
            //My_Community_Posts_PicassoClient.downloadImage(NDS_main.this, Config.LOAD_MY_AVATAR.toString() + MYAvatar, img);
            imageLoader.DisplayImage(Config.LOAD_MY_AVATAR.toString() + MYAvatar, img);
        } else {
            // imageLoader.DisplayImage(Config.LOAD_MY_AVATAR.toString() + "avatar_default.png", img);
            //Picasso.with(getBaseContext()).load(Config.LOAD_MY_AVATAR.toString() + MYAvatar).placeholder(R.mipmap.neza_default_avatar).into(img);
            My_Community_Posts_PicassoClient.downloadImage(NDS_main.this, Config.LOAD_MY_AVATAR.toString() + MYAvatar, img);
            imageLoader.DisplayImage(Config.LOAD_MY_AVATAR.toString() + MYAvatar, img);
        }
        //img.setImageBitmap(bm_89);
        //-------------
        toolbar.setTitle("Neza Digital Service");
        toolbar.addView(avatar_profile);

        //-------------------------
        forum_trigger = (ImageView) findViewById(R.id.forum_trigger);
        forum_trigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the camera action
                Intent intent = new Intent(NDS_main.this, Urubuga_Services.class);
                intent.putExtra("pageId", "2");
                startActivity(intent);
            }
        });
        ImageView suggestion_trigger = (ImageView) findViewById(R.id.suggestion_trigger);
        suggestion_trigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the camera action
                Intent intent = new Intent(NDS_main.this, Urubuga_Services.class);
                intent.putExtra("pageId", "1");
                startActivity(intent);
            }
        });


        //----

        fab = (FloatingActionButton)

                findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener()

                               {
                                   @Override
                                   public void onClick(View view) {
                                       Intent intent = new Intent(NDS_main.this, NDS_messages.class);
                                       //intent.putExtra("keyId", "0");
                                       startActivity(intent);
                                   }
                               }

        );

        drawer = (DrawerLayout)

                findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView)

                findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        //---------------------------------------------------
        View hView = navigationView.getHeaderView(0);
        ImageView header_upper_avatar = (ImageView) hView.findViewById(R.id.header_upper_avatar);
        ImageView back_ = (ImageView) hView.findViewById(R.id.back_);
        back_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //---------
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        ImageView edit_image = (ImageView) hView.findViewById(R.id.edit_image);
        //-----------------------
        edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NDS_main.this, Profile_edit.class);

                startActivity(intent);
            }
        });
        //---MYNames
        TextView header_names = (TextView) hView.findViewById(R.id.header_names);
        header_names.setText(MYNames);
        ImageLoader imageLoaderr = new ImageLoader(NDS_main.this);
        if (MYAvatar != null) {
            // Picasso.with(getBaseContext()).load(Config.LOAD_MY_AVATAR.toString() + MYAvatar).placeholder(R.mipmap.neza_default_avatar).into(header_upper_avatar);
           // My_Community_Posts_PicassoClient.downloadImage(NDS_main.this, Config.LOAD_MY_AVATAR.toString() + MYAvatar, header_upper_avatar);
            imageLoaderr.DisplayImage(Config.LOAD_MY_AVATAR.toString() + MYAvatar, header_upper_avatar);

            // My_Community_Posts_PicassoClient.downloadImage(NDS_main.this, Config.LOAD_MY_AVATAR.toString() + MYAvatar, header_upper_avatar);
            imageLoaderr.DisplayImage(Config.LOAD_MY_AVATAR.toString() + MYAvatar, header_upper_avatar);
        }

        Toggle_me.setOnClickListener(new View.OnClickListener()

                                     {
                                         @Override
                                         public void onClick(View view) {
                                             //-----------------

                                             // showPopup(view);

                                             //---------
                                             DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                                             if (drawer.isDrawerOpen(GravityCompat.START)) {
                                                 drawer.closeDrawer(GravityCompat.START);
                                             } else {
                                                 drawer.openDrawer(GravityCompat.START);
                                             }
                                         }
                                     }

        );
        //-------------------------
        Button girinka = (Button) findViewById(R.id.girinka);
        girinka.setOnClickListener(new View.OnClickListener()

                                   {
                                       @Override
                                       public void onClick(View view) {

                                           new My_NDS_GET_the_insti_to_ask_Downloader(NDS_main.this,Config.NDS_LOAD_INSTITUTION_TO_SEND_TO.toString(),view,"Girinka").execute();
                                           //showPopup(view, "Girinka");
                                       }
                                   }

        );
        Button vup = (Button) findViewById(R.id.vup);
        vup.setOnClickListener(new View.OnClickListener()

                               {
                                   @Override
                                   public void onClick(View view) {
                                       new My_NDS_GET_the_insti_to_ask_Downloader(NDS_main.this,Config.NDS_LOAD_INSTITUTION_TO_SEND_TO.toString(),view,"VUP").execute();

                                       //showPopup(view, "VUP");
                                   }
                               }

        );
        Button mutuelle = (Button) findViewById(R.id.mutuelle);
        mutuelle.setOnClickListener(new View.OnClickListener()

                                    {
                                        @Override
                                        public void onClick(View view) {
                                            new My_NDS_GET_the_insti_to_ask_Downloader(NDS_main.this,Config.NDS_LOAD_INSTITUTION_TO_SEND_TO.toString(),view,"MUTUELLE DE SANTE").execute();
                                            //showPopup(view, "MUTUELLE DE SANTE");
                                        }
                                    }

        );
        Button umurenge_sacco = (Button) findViewById(R.id.umurenge_sacco);
        umurenge_sacco.setOnClickListener(new View.OnClickListener()

                                          {
                                              @Override
                                              public void onClick(View view) {
                                                  //showPopup(view, "UMURENGE SACCO");
                                                  new My_NDS_GET_the_insti_to_ask_Downloader(NDS_main.this,Config.NDS_LOAD_INSTITUTION_TO_SEND_TO.toString(),view,"UMURENGE SACCO").execute();
                                              }
                                          }

        );
        Button ibibazo = (Button) findViewById(R.id.ibibazo);
        ibibazo.setOnClickListener(new View.OnClickListener()

                                   {
                                       @Override
                                       public void onClick(View view) {
                                           //showPopup(view, "IBIBAZO BY'ABATURAGE");
                                           new My_NDS_GET_the_insti_to_ask_Downloader(NDS_main.this,Config.NDS_LOAD_INSTITUTION_TO_SEND_TO.toString(),view,"IBIBAZO BY'ABATURAGE").execute();
                                       }
                                   }

        );
        renderTheNotifs();
        handlerRecycleActivity.postDelayed(runnableRecycleActivity, 1000);


    }

    public void renderTheNotifs() {
        ///-------------------------Show the notifications on app launch that are active---
        //--------------
        //------------launch the messages from institutions notifs query and suggestion-GROUP BY THE SUMMARY
        //--query--
        Cursor myQueriesActiveNotifs = db.queueNOTIFICATIONS_FOR_THE_TAG_GROUP_BY("messages_from_institutions", "1", "query");
        final String[] Notif_id_nber_myQueriesActiveNotifs = new String[myQueriesActiveNotifs.getCount()];
        final String[] Notif_title_myQueriesActiveNotifs = new String[myQueriesActiveNotifs.getCount()];
        final String[] origin_remote_id_myQueriesActiveNotifs = new String[myQueriesActiveNotifs.getCount()];
        String[] imageUrl_myQueriesActiveNotifs = new String[myQueriesActiveNotifs.getCount()];
        final String[] bigText_myQueriesActiveNotifs = new String[myQueriesActiveNotifs.getCount()];
        String[] regDate_myQueriesActiveNotifs = new String[myQueriesActiveNotifs.getCount()];
        final String[] sumMary_myQueriesActiveNotifs = new String[myQueriesActiveNotifs.getCount()];
        final String[] message_myQueriesActiveNotifs = new String[myQueriesActiveNotifs.getCount()];
        String[] activationTag_myQueriesActiveNotifs = new String[myQueriesActiveNotifs.getCount()];
        String[] notifType_myQueriesActiveNotifs = new String[myQueriesActiveNotifs.getCount()];
        String[] Orient_myQueriesActiveNotifs = new String[myQueriesActiveNotifs.getCount()];
        //----------------------------

        myQueriesActiveNotifs.moveToLast();
        for (int i = 0; i < myQueriesActiveNotifs.getCount(); i++) {
            Notif_id_nber_myQueriesActiveNotifs[i] = myQueriesActiveNotifs.getString(1);
            Notif_title_myQueriesActiveNotifs[i] = myQueriesActiveNotifs.getString(2);
            origin_remote_id_myQueriesActiveNotifs[i] = myQueriesActiveNotifs.getString(4);
            message_myQueriesActiveNotifs[i] = myQueriesActiveNotifs.getString(3);
            imageUrl_myQueriesActiveNotifs[i] = myQueriesActiveNotifs.getString(5);
            bigText_myQueriesActiveNotifs[i] = myQueriesActiveNotifs.getString(6);
            regDate_myQueriesActiveNotifs[i] = myQueriesActiveNotifs.getString(7);
            sumMary_myQueriesActiveNotifs[i] = myQueriesActiveNotifs.getString(8);
            activationTag_myQueriesActiveNotifs[i] = myQueriesActiveNotifs.getString(9);
            notifType_myQueriesActiveNotifs[i] = myQueriesActiveNotifs.getString(10);
            Orient_myQueriesActiveNotifs[i] = myQueriesActiveNotifs.getString(11);
            //---------------------Display the notifs link to queries--
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {


                    final String theAvatarValue = FindThisTrickyAvatar(origin_remote_id_myQueriesActiveNotifs[finalI], "notif_using_query");
                    //Log.w("theAvatarValue", theAvatarValue);
                    if (renderThisAvatarUrlData(theAvatarValue)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new generatePictureStyleNotification_big_text_with_large_icon(NDS_main.this, Integer.parseInt(Notif_id_nber_myQueriesActiveNotifs[finalI]), Notif_title_myQueriesActiveNotifs[finalI], message_myQueriesActiveNotifs[finalI], theAvatarValue, bigText_myQueriesActiveNotifs[finalI], NDS_messages_respond.class, "queryOrSuggId", sumMary_myQueriesActiveNotifs[finalI] + "-" + "query").execute();

                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new generatePictureStyleNotification_big_text_with_large_icon(NDS_main.this, Integer.parseInt(Notif_id_nber_myQueriesActiveNotifs[finalI]), Notif_title_myQueriesActiveNotifs[finalI], message_myQueriesActiveNotifs[finalI], Config.LOAD_MY_AVATAR.toString() + "/" + theAvatarValue, bigText_myQueriesActiveNotifs[finalI], NDS_messages_respond.class, "queryOrSuggId", sumMary_myQueriesActiveNotifs[finalI] + "-" + "query").execute();

                            }
                        });

                    }
                }
            }).start();


            myQueriesActiveNotifs.moveToPrevious();
        }
        //--------suggestion---
        Cursor mySuggestionsActiveNotifs = db.queueNOTIFICATIONS_FOR_THE_TAG_GROUP_BY("messages_from_institutions", "1", "suggestion");
        final String[] Notif_id_nber_mySuggestionsActiveNotifs = new String[mySuggestionsActiveNotifs.getCount()];
        final String[] Notif_title_mySuggestionsActiveNotifs = new String[mySuggestionsActiveNotifs.getCount()];
        final String[] origin_remote_id_mySuggestionsActiveNotifs = new String[mySuggestionsActiveNotifs.getCount()];
        String[] imageUrl_mySuggestionsActiveNotifs = new String[mySuggestionsActiveNotifs.getCount()];
        final String[] bigText_mySuggestionsActiveNotifs = new String[mySuggestionsActiveNotifs.getCount()];
        String[] regDate_mySuggestionsActiveNotifs = new String[mySuggestionsActiveNotifs.getCount()];
        final String[] sumMary_mySuggestionsActiveNotifs = new String[mySuggestionsActiveNotifs.getCount()];
        final String[] message_mySuggestionsActiveNotifs = new String[mySuggestionsActiveNotifs.getCount()];
        String[] activationTag_mySuggestionsActiveNotifs = new String[mySuggestionsActiveNotifs.getCount()];
        String[] notifType_mySuggestionsActiveNotifs = new String[mySuggestionsActiveNotifs.getCount()];
        String[] Orient_mySuggestionsActiveNotifs = new String[mySuggestionsActiveNotifs.getCount()];
        //----------------------------

        mySuggestionsActiveNotifs.moveToLast();
        for (int i = 0; i < mySuggestionsActiveNotifs.getCount(); i++) {
            Notif_id_nber_mySuggestionsActiveNotifs[i] = mySuggestionsActiveNotifs.getString(1);
            Notif_title_mySuggestionsActiveNotifs[i] = mySuggestionsActiveNotifs.getString(2);
            origin_remote_id_mySuggestionsActiveNotifs[i] = mySuggestionsActiveNotifs.getString(4);
            message_mySuggestionsActiveNotifs[i] = mySuggestionsActiveNotifs.getString(3);
            imageUrl_mySuggestionsActiveNotifs[i] = mySuggestionsActiveNotifs.getString(5);
            bigText_mySuggestionsActiveNotifs[i] = mySuggestionsActiveNotifs.getString(6);
            regDate_mySuggestionsActiveNotifs[i] = mySuggestionsActiveNotifs.getString(7);
            sumMary_mySuggestionsActiveNotifs[i] = mySuggestionsActiveNotifs.getString(8);
            activationTag_mySuggestionsActiveNotifs[i] = mySuggestionsActiveNotifs.getString(9);
            notifType_mySuggestionsActiveNotifs[i] = mySuggestionsActiveNotifs.getString(10);
            Orient_mySuggestionsActiveNotifs[i] = mySuggestionsActiveNotifs.getString(11);
            //---------------------Display the notifs link to queries--
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {


                    final String theAvatarValue = FindThisTrickyAvatar(origin_remote_id_mySuggestionsActiveNotifs[finalI], "notif_using_suggestion");
                    if (renderThisAvatarUrlData(theAvatarValue)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new generatePictureStyleNotification_big_text_with_large_icon(NDS_main.this, Integer.parseInt(Notif_id_nber_mySuggestionsActiveNotifs[finalI]), Notif_title_mySuggestionsActiveNotifs[finalI], message_mySuggestionsActiveNotifs[finalI], theAvatarValue, bigText_mySuggestionsActiveNotifs[finalI], NDS_messages_respond.class, "queryOrSuggId", sumMary_mySuggestionsActiveNotifs[finalI] + "-" + "suggestion").execute();

                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new generatePictureStyleNotification_big_text_with_large_icon(NDS_main.this, Integer.parseInt(Notif_id_nber_mySuggestionsActiveNotifs[finalI]), Notif_title_mySuggestionsActiveNotifs[finalI], message_mySuggestionsActiveNotifs[finalI], Config.LOAD_MY_AVATAR.toString() + "/" + theAvatarValue, bigText_mySuggestionsActiveNotifs[finalI], NDS_messages_respond.class, "queryOrSuggId", sumMary_mySuggestionsActiveNotifs[finalI] + "-" + "suggestion").execute();

                            }
                        });

                    }
                }
            }).start();
            mySuggestionsActiveNotifs.moveToPrevious();
        }
        //-------------------launch the comments on my posts notifs-GROUP BY THE SUMMARY
        Cursor myCommentsOnPosts = db.queueNOTIFICATIONS_FOR_THE_TAG_NO_ORIENT_GROUP_BY("comments_on_posts", "1");
        final String[] Notif_id_nber_myCommentsOnPosts = new String[myCommentsOnPosts.getCount()];
        final String[] Notif_title_myCommentsOnPosts = new String[myCommentsOnPosts.getCount()];
        final String[] origin_remote_id_myCommentsOnPosts = new String[myCommentsOnPosts.getCount()];
        String[] imageUrl_myCommentsOnPosts = new String[myCommentsOnPosts.getCount()];
        final String[] bigText_myCommentsOnPosts = new String[myCommentsOnPosts.getCount()];
        String[] regDate_myCommentsOnPosts = new String[myCommentsOnPosts.getCount()];
        final String[] sumMary_myCommentsOnPosts = new String[myCommentsOnPosts.getCount()];
        final String[] message_myCommentsOnPosts = new String[myCommentsOnPosts.getCount()];
        String[] activationTag_myCommentsOnPosts = new String[myCommentsOnPosts.getCount()];
        String[] notifType_myCommentsOnPosts = new String[myCommentsOnPosts.getCount()];
        String[] Orient_myCommentsOnPosts = new String[myCommentsOnPosts.getCount()];
        //----------------------------

        myCommentsOnPosts.moveToLast();
        for (int i = 0; i < myCommentsOnPosts.getCount(); i++) {
            Notif_id_nber_myCommentsOnPosts[i] = myCommentsOnPosts.getString(1);
            Notif_title_myCommentsOnPosts[i] = myCommentsOnPosts.getString(2);
            origin_remote_id_myCommentsOnPosts[i] = myCommentsOnPosts.getString(4);
            message_myCommentsOnPosts[i] = myCommentsOnPosts.getString(3);
            imageUrl_myCommentsOnPosts[i] = myCommentsOnPosts.getString(5);
            bigText_myCommentsOnPosts[i] = myCommentsOnPosts.getString(6);
            regDate_myCommentsOnPosts[i] = myCommentsOnPosts.getString(7);
            sumMary_myCommentsOnPosts[i] = myCommentsOnPosts.getString(8);
            activationTag_myCommentsOnPosts[i] = myCommentsOnPosts.getString(9);
            notifType_myCommentsOnPosts[i] = myCommentsOnPosts.getString(10);
            Orient_myCommentsOnPosts[i] = myCommentsOnPosts.getString(11);
            //---------------------Display the notifs link to queries--
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {


                    final String theAvatarValue = FindThisTrickyAvatar(origin_remote_id_myCommentsOnPosts[finalI], "notif_using_cmnts_on_posts");
                    if (renderThisAvatarUrlData(theAvatarValue)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new generatePictureStyleNotification_big_text_with_large_icon(NDS_main.this, Integer.parseInt(Notif_id_nber_myCommentsOnPosts[finalI]), Notif_title_myCommentsOnPosts[finalI], message_myCommentsOnPosts[finalI], theAvatarValue, bigText_myCommentsOnPosts[finalI], View_NDS_posts_comments.class, "the_post_id", sumMary_myCommentsOnPosts[finalI] + "-" + "comments").execute();

                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new generatePictureStyleNotification_big_text_with_large_icon(NDS_main.this, Integer.parseInt(Notif_id_nber_myCommentsOnPosts[finalI]), Notif_title_myCommentsOnPosts[finalI], message_myCommentsOnPosts[finalI], Config.LOAD_MY_AVATAR.toString() + "/" + theAvatarValue, bigText_myCommentsOnPosts[finalI], View_NDS_posts_comments.class, "the_post_id", sumMary_myCommentsOnPosts[finalI] + "-" + "comments").execute();

                            }
                        });

                    }
                }
            }).start();

            myCommentsOnPosts.moveToPrevious();
        }
        //---------------launch the tags on posts
        Cursor myTagsOnPosts = db.queueNOTIFICATIONS_FOR_THE_TAG_NO_ORIENT("tags_on_posts", "1");
        final String[] Notif_id_nber_myTagsOnPosts = new String[myTagsOnPosts.getCount()];
        final String[] Notif_title_myTagsOnPosts = new String[myTagsOnPosts.getCount()];
        final String[] origin_remote_id_myTagsOnPosts = new String[myTagsOnPosts.getCount()];
        String[] imageUrl_myTagsOnPosts = new String[myTagsOnPosts.getCount()];
        final String[] bigText_myTagsOnPosts = new String[myTagsOnPosts.getCount()];
        String[] regDate_myTagsOnPosts = new String[myTagsOnPosts.getCount()];
        String[] sumMary_myTagsOnPosts = new String[myTagsOnPosts.getCount()];
        final String[] message_myTagsOnPosts = new String[myTagsOnPosts.getCount()];
        String[] activationTag_myTagsOnPosts = new String[myTagsOnPosts.getCount()];
        String[] notifType_myTagsOnPosts = new String[myTagsOnPosts.getCount()];
        String[] Orient_myTagsOnPosts = new String[myTagsOnPosts.getCount()];
        //----------------------------
        myTagsOnPosts.moveToLast();
        for (int i = 0; i < myTagsOnPosts.getCount(); i++) {
            Notif_id_nber_myTagsOnPosts[i] = myTagsOnPosts.getString(1);
            Notif_title_myTagsOnPosts[i] = myTagsOnPosts.getString(2);
            origin_remote_id_myTagsOnPosts[i] = myTagsOnPosts.getString(4);
            message_myTagsOnPosts[i] = myTagsOnPosts.getString(3);
            imageUrl_myTagsOnPosts[i] = myTagsOnPosts.getString(5);
            bigText_myTagsOnPosts[i] = myTagsOnPosts.getString(6);
            regDate_myTagsOnPosts[i] = myTagsOnPosts.getString(7);
            sumMary_myTagsOnPosts[i] = myTagsOnPosts.getString(8);
            activationTag_myTagsOnPosts[i] = myTagsOnPosts.getString(9);
            notifType_myTagsOnPosts[i] = myTagsOnPosts.getString(10);
            Orient_myTagsOnPosts[i] = myTagsOnPosts.getString(11);
            //---------------------Display the notifs link to queries--
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {


                    final String theAvatarValue = FindThisTrickyAvatar(origin_remote_id_myTagsOnPosts[finalI], "notif_using_tags_on_posts");
                    if (renderThisAvatarUrlData(theAvatarValue)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new generatePictureStyleNotification_big_text_with_large_icon(NDS_main.this, Integer.parseInt(Notif_id_nber_myTagsOnPosts[finalI]), Notif_title_myTagsOnPosts[finalI], message_myTagsOnPosts[finalI], theAvatarValue, bigText_myTagsOnPosts[finalI], View_NDS_posts_comments.class, "the_post_id", origin_remote_id_myTagsOnPosts[finalI] + "-" + "tags_on_posts").execute();

                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new generatePictureStyleNotification_big_text_with_large_icon(NDS_main.this, Integer.parseInt(Notif_id_nber_myTagsOnPosts[finalI]), Notif_title_myTagsOnPosts[finalI], message_myTagsOnPosts[finalI], Config.LOAD_MY_AVATAR.toString() + "/" + theAvatarValue, bigText_myTagsOnPosts[finalI], View_NDS_posts_comments.class, "the_post_id", origin_remote_id_myTagsOnPosts[finalI] + "-" + "tags_on_posts").execute();

                            }
                        });

                    }
                }
            }).start();

            myTagsOnPosts.moveToPrevious();
        }
        //---launch the tags on comments-----GROUP BY THE SUMMARY
        Cursor myTagsOnComments = db.queueNOTIFICATIONS_FOR_THE_TAG_NO_ORIENT("tags_on_comments", "1");
        final String[] Notif_id_nber_myTagsOnComments = new String[myTagsOnComments.getCount()];
        final String[] Notif_title_myTagsOnComments = new String[myTagsOnComments.getCount()];
        final String[] origin_remote_id_myTagsOnComments = new String[myTagsOnComments.getCount()];
        String[] imageUrl_myTagsOnComments = new String[myTagsOnComments.getCount()];
        final String[] bigText_myTagsOnComments = new String[myTagsOnComments.getCount()];
        String[] regDate_myTagsOnComments = new String[myTagsOnComments.getCount()];
        String[] sumMary_myTagsOnComments = new String[myTagsOnComments.getCount()];
        final String[] message_myTagsOnComments = new String[myTagsOnComments.getCount()];
        String[] activationTag_myTagsOnComments = new String[myTagsOnComments.getCount()];
        String[] notifType_myTagsOnComments = new String[myTagsOnComments.getCount()];
        String[] Orient_myTagsOnComments = new String[myTagsOnComments.getCount()];
        //----------------------------
        myTagsOnComments.moveToLast();
        for (int i = 0; i < myTagsOnComments.getCount(); i++) {
            Notif_id_nber_myTagsOnComments[i] = myTagsOnComments.getString(1);
            Notif_title_myTagsOnComments[i] = myTagsOnComments.getString(2);
            origin_remote_id_myTagsOnComments[i] = myTagsOnComments.getString(4);
            message_myTagsOnComments[i] = myTagsOnComments.getString(3);
            imageUrl_myTagsOnComments[i] = myTagsOnComments.getString(5);
            bigText_myTagsOnComments[i] = myTagsOnComments.getString(6);
            regDate_myTagsOnComments[i] = myTagsOnComments.getString(7);
            sumMary_myTagsOnComments[i] = myTagsOnComments.getString(8);
            activationTag_myTagsOnComments[i] = myTagsOnComments.getString(9);
            notifType_myTagsOnComments[i] = myTagsOnComments.getString(10);
            Orient_myTagsOnComments[i] = myTagsOnComments.getString(11);
            //---------------------Display the notifs link to queries--
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {


                    final String theAvatarValue = FindThisTrickyAvatar(origin_remote_id_myTagsOnComments[finalI], "notif_using_tags_on_comments");
                    if (renderThisAvatarUrlData(theAvatarValue)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new generatePictureStyleNotification_big_text_with_large_icon(NDS_main.this, Integer.parseInt(Notif_id_nber_myTagsOnComments[finalI]), Notif_title_myTagsOnComments[finalI], message_myTagsOnComments[finalI], theAvatarValue, bigText_myTagsOnComments[finalI], View_NDS_posts_comments.class, "the_post_id", origin_remote_id_myTagsOnComments[finalI] + "-" + "notif_using_tags_on_comments").execute();

                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new generatePictureStyleNotification_big_text_with_large_icon(NDS_main.this, Integer.parseInt(Notif_id_nber_myTagsOnComments[finalI]), Notif_title_myTagsOnComments[finalI], message_myTagsOnComments[finalI], Config.LOAD_MY_AVATAR.toString() + "/" + theAvatarValue, bigText_myTagsOnComments[finalI], View_NDS_posts_comments.class, "the_post_id", origin_remote_id_myTagsOnComments[finalI] + "-" + "notif_using_tags_on_comments").execute();

                            }
                        });

                    }
                }
            }).start();
            myTagsOnComments.moveToPrevious();
        }
        //-----------launch the keyword on posts
        Cursor myKeywordOnPosts = db.queueNOTIFICATIONS_FOR_THE_TAG_NO_ORIENT("keyword_on_posts", "1");
        final String[] Notif_id_nber_myKeywordOnPosts = new String[myKeywordOnPosts.getCount()];
        final String[] Notif_title_myKeywordOnPosts = new String[myKeywordOnPosts.getCount()];
        final String[] origin_remote_id_myKeywordOnPosts = new String[myKeywordOnPosts.getCount()];
        String[] imageUrl_myKeywordOnPosts = new String[myKeywordOnPosts.getCount()];
        final String[] bigText_myKeywordOnPosts = new String[myKeywordOnPosts.getCount()];
        String[] regDate_myKeywordOnPosts = new String[myKeywordOnPosts.getCount()];
        String[] sumMary_myKeywordOnPosts = new String[myKeywordOnPosts.getCount()];
        final String[] message_myKeywordOnPosts = new String[myKeywordOnPosts.getCount()];
        String[] activationTag_myKeywordOnPosts = new String[myKeywordOnPosts.getCount()];
        String[] notifType_myKeywordOnPosts = new String[myKeywordOnPosts.getCount()];
        String[] Orient_myKeywordOnPosts = new String[myKeywordOnPosts.getCount()];
        //----------------------------
        myKeywordOnPosts.moveToLast();
        for (int i = 0; i < myKeywordOnPosts.getCount(); i++) {
            Notif_id_nber_myKeywordOnPosts[i] = myKeywordOnPosts.getString(1);
            Notif_title_myKeywordOnPosts[i] = myKeywordOnPosts.getString(2);
            origin_remote_id_myKeywordOnPosts[i] = myKeywordOnPosts.getString(4);
            message_myKeywordOnPosts[i] = myKeywordOnPosts.getString(3);
            imageUrl_myKeywordOnPosts[i] = myKeywordOnPosts.getString(5);
            bigText_myKeywordOnPosts[i] = myKeywordOnPosts.getString(6);
            regDate_myKeywordOnPosts[i] = myKeywordOnPosts.getString(7);
            sumMary_myKeywordOnPosts[i] = myKeywordOnPosts.getString(8);
            activationTag_myKeywordOnPosts[i] = myKeywordOnPosts.getString(9);
            notifType_myKeywordOnPosts[i] = myKeywordOnPosts.getString(10);
            Orient_myKeywordOnPosts[i] = myKeywordOnPosts.getString(11);
            //---------------------Display the notifs link to queries--
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {


                    final String theAvatarValue = FindThisTrickyAvatar(origin_remote_id_myKeywordOnPosts[finalI], "notif_using_keyword_on_posts");
                    Log.w("theAvatarValue", "" + theAvatarValue);
                    if (renderThisAvatarUrlData(theAvatarValue)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new generatePictureStyleNotification_big_text_with_large_icon(NDS_main.this, Integer.parseInt(Notif_id_nber_myKeywordOnPosts[finalI]), Notif_title_myKeywordOnPosts[finalI], message_myKeywordOnPosts[finalI], theAvatarValue, bigText_myKeywordOnPosts[finalI], View_NDS_posts_comments.class, "the_post_id", origin_remote_id_myKeywordOnPosts[finalI] + "-" + "notif_using_keyword_on_posts").execute();

                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new generatePictureStyleNotification_big_text_with_large_icon(NDS_main.this, Integer.parseInt(Notif_id_nber_myKeywordOnPosts[finalI]), Notif_title_myKeywordOnPosts[finalI], message_myKeywordOnPosts[finalI], Config.LOAD_MY_AVATAR.toString() + "/" + theAvatarValue, bigText_myKeywordOnPosts[finalI], View_NDS_posts_comments.class, "the_post_id", origin_remote_id_myKeywordOnPosts[finalI] + "-" + "notif_using_keyword_on_posts").execute();

                            }
                        });
                    }
                }
            }).start();

            myTagsOnComments.moveToPrevious();
        }
        //------launch the keyword on comments-GROUP BY THE SUMMARY
        Cursor myKeywordOnComments = db.queueNOTIFICATIONS_FOR_THE_TAG_NO_ORIENT("keyword_on_comments", "1");
        final String[] Notif_id_nber_myKeywordOnComments = new String[myKeywordOnComments.getCount()];
        final String[] Notif_title_myKeywordOnComments = new String[myKeywordOnComments.getCount()];
        final String[] origin_remote_id_myKeywordOnComments = new String[myKeywordOnComments.getCount()];
        String[] imageUrl_myKeywordOnComments = new String[myKeywordOnComments.getCount()];
        final String[] bigText_myKeywordOnComments = new String[myKeywordOnComments.getCount()];
        String[] regDate_myKeywordOnComments = new String[myKeywordOnComments.getCount()];
        String[] sumMary_myKeywordOnComments = new String[myKeywordOnComments.getCount()];
        final String[] message_myKeywordOnComments = new String[myKeywordOnComments.getCount()];
        String[] activationTag_myKeywordOnComments = new String[myKeywordOnComments.getCount()];
        String[] notifType_myKeywordOnComments = new String[myKeywordOnComments.getCount()];
        String[] Orient_myKeywordOnComments = new String[myKeywordOnComments.getCount()];
        //----------------------------
        myKeywordOnComments.moveToLast();
        for (int i = 0; i < myKeywordOnComments.getCount(); i++) {
            Notif_id_nber_myKeywordOnComments[i] = myKeywordOnComments.getString(1);
            Notif_title_myKeywordOnComments[i] = myKeywordOnComments.getString(2);
            origin_remote_id_myKeywordOnComments[i] = myKeywordOnComments.getString(4);
            message_myKeywordOnComments[i] = myKeywordOnComments.getString(3);
            imageUrl_myKeywordOnComments[i] = myKeywordOnComments.getString(5);
            bigText_myKeywordOnComments[i] = myKeywordOnComments.getString(6);
            regDate_myKeywordOnComments[i] = myKeywordOnComments.getString(7);
            sumMary_myKeywordOnComments[i] = myKeywordOnComments.getString(8);
            activationTag_myKeywordOnComments[i] = myKeywordOnComments.getString(9);
            notifType_myKeywordOnComments[i] = myKeywordOnComments.getString(10);
            Orient_myKeywordOnComments[i] = myKeywordOnComments.getString(11);
            //---------------------Display the notifs link to queries--
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final String theAvatarValue = FindThisTrickyAvatar(origin_remote_id_myKeywordOnComments[finalI], "notif_using_keyword_on_comments");
                    if (renderThisAvatarUrlData(theAvatarValue)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new generatePictureStyleNotification_big_text_with_large_icon(NDS_main.this, Integer.parseInt(Notif_id_nber_myKeywordOnComments[finalI]), Notif_title_myKeywordOnComments[finalI], message_myKeywordOnComments[finalI], theAvatarValue, bigText_myKeywordOnComments[finalI], View_NDS_posts_comments.class, "the_post_id", origin_remote_id_myKeywordOnComments[finalI] + "-" + "notif_using_keyword_on_comments").execute();

                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new generatePictureStyleNotification_big_text_with_large_icon(NDS_main.this, Integer.parseInt(Notif_id_nber_myKeywordOnComments[finalI]), Notif_title_myKeywordOnComments[finalI], message_myKeywordOnComments[finalI], Config.LOAD_MY_AVATAR.toString() + "/" + theAvatarValue, bigText_myKeywordOnComments[finalI], View_NDS_posts_comments.class, "the_post_id", origin_remote_id_myKeywordOnComments[finalI] + "-" + "notif_using_keyword_on_comments").execute();

                            }
                        });
                    }
                }
            }).start();

            myTagsOnComments.moveToPrevious();
        }
    }

    public String FindThisTrickyAvatar(final String notif_originator_remote_id, final String notif_type_orient) {

        String finalAvatarResponse;


        try {
            finalAvatarResponse = new My_Community_App_Get_Avatar(NDS_main.this, Config.NDS_NOTIFICATION_LOAD_THE_NEEDED_AVATAR.toString(), notif_originator_remote_id, notif_type_orient).execute().get();

        } catch (InterruptedException e) {
            finalAvatarResponse = "error";

            e.printStackTrace();
        } catch (ExecutionException e) {
            finalAvatarResponse = "error";
            e.printStackTrace();
        }


        return finalAvatarResponse;

    }

    //-------------function render url api
    public Boolean renderThisAvatarUrlData(String primitive) {
        String primitive_ = primitive;
        if (primitive_ != null) {
            return false;
        } else {

            if (contains(primitive, "http")) {
                return true;
            } else {
                return false;
            }

        }
    }

    public boolean contains(String haystack, String needle) {
        haystack = haystack == null ? "" : haystack;
        needle = needle == null ? "" : needle;

        // Works, but is not the best.
        //return haystack.toLowerCase().indexOf( needle.toLowerCase() ) > -1

        return haystack.toLowerCase().contains(needle.toLowerCase());
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            moveTaskToBack(true);
        }
        moveTaskToBack(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nds_main, menu);

        //----------------------

        return true;
    }

    public void LogOut(View v) {
        final Dialog mProgressDialog = new Dialog(NDS_main.this);
        mProgressDialog.setTitle("Neza Digital Service");
        mProgressDialog.setCanceledOnTouchOutside(true);
        try {
            mProgressDialog.show();
        } catch (RuntimeException fjjbf) {

        }
        //--
        mProgressDialog.setContentView(R.layout.pop_up_logout);
        Button yes_ = (Button) ((Dialog) mProgressDialog).findViewById(R.id.yes_);
        yes_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteAllFromCreds();
                db.deleteAllFrom_neza_community_posts_no_data();
                db.deleteAllFrom_Query();
                db.deleteAllFrom_Query_response();

                finish();
                mProgressDialog.dismiss();
            }
        });
        Button cancel_ = (Button) ((Dialog) mProgressDialog).findViewById(R.id.cancel_);
        cancel_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mProgressDialog.dismiss();
            }
        });


    }


    String titble_anchor_brief = "Andika ikibazo kijyanye na";




    //----
    private String deposit_my_complaint_toRGB(String query, String myId, String queryOrientation, String local_query_id,String theSelectedItem) throws Exception {
        //---------------- Stage 1----------------------------------------------------
        //---------------------------------

        //----------------------------------------------------------------
        String data = "";
        data = URLEncoder.encode("query", "UTF-8")
                + "=" + URLEncoder.encode(query, "UTF-8");
        data += "&" + URLEncoder.encode("queryOrientation", "UTF-8") + "="
                + URLEncoder.encode(queryOrientation, "UTF-8");
        data += "&" + URLEncoder.encode("myId", "UTF-8") + "="
                + URLEncoder.encode(myId, "UTF-8");
        data += "&" + URLEncoder.encode("local_query_id", "UTF-8") + "="
                + URLEncoder.encode(local_query_id, "UTF-8");
        data += "&" + URLEncoder.encode("theSelectedItem", "UTF-8") + "="
                + URLEncoder.encode(theSelectedItem, "UTF-8");


        String lastResort = data;
        //==============================
        try {
            String url = Config.DEPOSIT_MYQUERY_TO_THE_RGB;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            //-----------------------------------------------------------------------
            String urlParameters = lastResort;

            // Send post request
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            //--------------------------Thread sleeps-----------

            int responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();


        } catch (ProtocolException e) {
            e.printStackTrace();
            Log.w("NDS", "Now we can talk=> " + e.toString());


        } catch (MalformedURLException e) {
            // e.printStackTrace();
            Log.w("NDS", "Now we can talk=> " + e.toString());

        } catch (IOException e) {
            Log.w("NDS", "Now we can talk=> " + e.toString());

            e.printStackTrace();
        }


        return response.toString();

        //---------------------------------------------------------------
    }

    public void showConfirmationMessage(String title) {


        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.pop_up_thank_you_msg, null);
        TextView ubutumwa_content = (TextView) popupView.findViewById(R.id.ubutumwa_content);
        Button ok_sent = (Button) popupView.findViewById(R.id.ok_sent);
        if (title.toString().equalsIgnoreCase("OK")) {
            ubutumwa_content.setText("Murakoze gukoresha uru rubuga!\n Ikibazo cyanyu cyakiriwe n'inzego zibishinzwe kandi ziragikurikirana                 mu gihe cya vuba!                  ");
        } else {
            ubutumwa_content.setText("Ntago ubutumwa bwagiye,\n muze kongera kugerageza!\nMurabusanga aho ubundi butumwa bubitse!");
        }


        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
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
        popupWindow.showAtLocation(forum_trigger, Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
        popupWindow.setAnimationStyle(android.R.style.TextAppearance_DeviceDefault_Small_Inverse);
        popupWindow.setFocusable(true);
        popupWindow.update();
        ok_sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });


    }

    Handler handlerRecycleActivity = new Handler();
    Runnable runnableRecycleActivity = new Runnable() {
        public void run() {
            //--------------------(myRemoteId, String.valueOf(theBooId));

            Cursor UserCreds = db.queueAllFromCreds();

            if (UserCreds.moveToLast()) {
                MYRemoteId = UserCreds.getString(9);
                MYAvatar = UserCreds.getString(3);
                MYNames = UserCreds.getString(1);
            } else {


            }
            ImageView Toggle_me = (ImageView) findViewById(R.id.toggle_me);
            toolbar.setLogo(R.mipmap.neza_000_we);
            //------------------------
            View avatar_profile = getLayoutInflater().inflate(R.layout.remote_up_nav, null);
            ImageView img = (ImageView) avatar_profile.findViewById(R.id.img_avatar);


            //---------------
            BitmapDrawable ball = (BitmapDrawable) getBaseContext().getResources().getDrawable(R.mipmap.box_0000);
            Bitmap b_ball_1 = ((BitmapDrawable) ball).getBitmap();
            Bitmap bm_89 = Bitmap.createScaledBitmap(b_ball_1, (int) 120, (int) 120, false);
            //-------------------
            ImageLoader imageLoader = new ImageLoader(getApplicationContext());
            if (MYAvatar != null) {
                // imageLoader.DisplayImage(Config.LOAD_MY_AVATAR.toString() + MYAvatar, img);
                //Picasso.with(getBaseContext()).load(Config.LOAD_MY_AVATAR.toString() + MYAvatar).placeholder(R.mipmap.neza_default_avatar).into(img);
                //My_Community_Posts_PicassoClient.downloadImage(NDS_main.this, Config.LOAD_MY_AVATAR.toString() + MYAvatar, img);
                imageLoader.DisplayImage(Config.LOAD_MY_AVATAR.toString() + MYAvatar, img);
            } else {
                // imageLoader.DisplayImage(Config.LOAD_MY_AVATAR.toString() + "avatar_default.png", img);
                // Picasso.with(getBaseContext()).load(Config.LOAD_MY_AVATAR.toString() + MYAvatar).placeholder(R.mipmap.neza_default_avatar).into(img);
                //My_Community_Posts_PicassoClient.downloadImage(NDS_main.this, Config.LOAD_MY_AVATAR.toString() + MYAvatar, img);
                imageLoader.DisplayImage(Config.LOAD_MY_AVATAR.toString() + MYAvatar, img);
            }
            View hView = navigationView.getHeaderView(0);
            ImageView header_upper_avatar = (ImageView) hView.findViewById(R.id.header_upper_avatar);
            ImageView back_ = (ImageView) hView.findViewById(R.id.back_);
            back_.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //---------
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    } else {
                        drawer.openDrawer(GravityCompat.START);
                    }
                }
            });
            ImageView edit_image = (ImageView) hView.findViewById(R.id.edit_image);
            //-----------------------
            edit_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(NDS_main.this, Profile_edit.class);

                    startActivity(intent);
                }
            });
            //---MYNames
            TextView header_names = (TextView) hView.findViewById(R.id.header_names);
            header_names.setText(MYNames);
            ImageLoader imageLoaderr = new ImageLoader(NDS_main.this);
            if (MYAvatar != null) {
                //Picasso.with(getBaseContext()).load(Config.LOAD_MY_AVATAR.toString() + MYAvatar).placeholder(R.mipmap.neza_default_avatar).into(header_upper_avatar);
                //My_Community_Posts_PicassoClient.downloadImage(NDS_main.this, Config.LOAD_MY_AVATAR.toString() + MYAvatar, header_upper_avatar);
                imageLoaderr.DisplayImage(Config.LOAD_MY_AVATAR.toString() + MYAvatar, header_upper_avatar);

            } else {
                //My_Community_Posts_PicassoClient.downloadImage(NDS_main.this, Config.LOAD_MY_AVATAR.toString() + MYAvatar, header_upper_avatar);
                imageLoaderr.DisplayImage(Config.LOAD_MY_AVATAR.toString() + MYAvatar, header_upper_avatar);
            }
            //------------------bring me  my notifications
            //----------------Queries' Comments-
            new Thread(new Runnable() {
                @Override
                public void run() {


                    db.openToWrite();
                    Cursor MSGS_FROM_INSTI_A = db.queueNOTIFICATIONS_FOR_THE_TAG("messages_from_institutions", "none", "query");
                    String[] Notif_number_queries = new String[MSGS_FROM_INSTI_A.getCount()];
                    String[] Notif_title_queries = new String[MSGS_FROM_INSTI_A.getCount()];
                    String[] origin_remote_id_queries = new String[MSGS_FROM_INSTI_A.getCount()];
                    String[] imageUrl_queries = new String[MSGS_FROM_INSTI_A.getCount()];
                    String[] bigText_queries = new String[MSGS_FROM_INSTI_A.getCount()];
                    String[] regDate_queries = new String[MSGS_FROM_INSTI_A.getCount()];
                    String[] sumMary_queries = new String[MSGS_FROM_INSTI_A.getCount()];
                    String[] activationTag_queries = new String[MSGS_FROM_INSTI_A.getCount()];
                    String[] notifType_queries = new String[MSGS_FROM_INSTI_A.getCount()];
                    String[] Orient_queries = new String[MSGS_FROM_INSTI_A.getCount()];
                    //-------------------
                    String streamOfIds_queries = "";
                    String streamOfOrients_queries = "";
                    String streamOfSummary_queries = "";
                    if (MSGS_FROM_INSTI_A.getCount() > 0) {
                        MSGS_FROM_INSTI_A.moveToLast();
                        for (int i = 0; i < MSGS_FROM_INSTI_A.getCount(); i++) {
                            Notif_number_queries[i] = MSGS_FROM_INSTI_A.getString(1);
                            Notif_title_queries[i] = MSGS_FROM_INSTI_A.getString(2);
                            origin_remote_id_queries[i] = MSGS_FROM_INSTI_A.getString(4);
                            imageUrl_queries[i] = MSGS_FROM_INSTI_A.getString(5);
                            bigText_queries[i] = MSGS_FROM_INSTI_A.getString(6);
                            regDate_queries[i] = MSGS_FROM_INSTI_A.getString(7);
                            sumMary_queries[i] = MSGS_FROM_INSTI_A.getString(8);
                            activationTag_queries[i] = MSGS_FROM_INSTI_A.getString(9);
                            notifType_queries[i] = MSGS_FROM_INSTI_A.getString(10);
                            Orient_queries[i] = MSGS_FROM_INSTI_A.getString(11);
                            //--------------
                            if (i == 0) {
                                streamOfIds_queries = streamOfIds_queries + origin_remote_id_queries[i];
                                streamOfOrients_queries = streamOfOrients_queries + Orient_queries[i];
                                streamOfSummary_queries = streamOfSummary_queries + sumMary_queries[i];
                            } else {
                                streamOfIds_queries = streamOfIds_queries + "~" + origin_remote_id_queries[i];
                                streamOfOrients_queries = streamOfOrients_queries + "~" + Orient_queries[i];
                                streamOfSummary_queries = streamOfSummary_queries + "~" + sumMary_queries[i];
                            }
                            MSGS_FROM_INSTI_A.moveToPrevious();
                        }
                    }
                    //----------go to server to bring more Q_S comments queries inside trick
                    new My_Q_S_Comments_Downloader(NDS_main.this, Config.NDS_NOTIFICATION_LOAD_NOTIF_MSGS_FROM_INSTI_A.toString(), streamOfIds_queries, streamOfOrients_queries, streamOfSummary_queries, MYRemoteId).execute();
                    new My_Q_S_Comments_Downloader(NDS_main.this, Config.NDS_NOTIFICATION_LOAD_NOTIF_MSGS_FROM_INSTI_B.toString(), streamOfIds_queries, streamOfOrients_queries, streamOfSummary_queries, MYRemoteId).execute();
                    //------------------
                    Log.w("test_notif_instiA", streamOfIds_queries);


                    //----------------Queries' Comments-
                    db.openToWrite();
                    Cursor MSGS_FROM_INSTI_A_1 = db.queueNOTIFICATIONS_FOR_THE_TAG("messages_from_institutions", "none", "suggestion");
                    String[] Notif_number_suggestion = new String[MSGS_FROM_INSTI_A_1.getCount()];
                    String[] Notif_title_suggestion = new String[MSGS_FROM_INSTI_A_1.getCount()];
                    String[] origin_remote_id_suggestion = new String[MSGS_FROM_INSTI_A_1.getCount()];
                    String[] imageUrl_suggestion = new String[MSGS_FROM_INSTI_A_1.getCount()];
                    String[] bigText_suggestion = new String[MSGS_FROM_INSTI_A_1.getCount()];
                    String[] regDate_suggestion = new String[MSGS_FROM_INSTI_A_1.getCount()];
                    String[] sumMary_suggestion = new String[MSGS_FROM_INSTI_A_1.getCount()];
                    String[] activationTag_suggestion = new String[MSGS_FROM_INSTI_A_1.getCount()];
                    String[] notifType_suggestion = new String[MSGS_FROM_INSTI_A_1.getCount()];
                    String[] Orient_suggestion = new String[MSGS_FROM_INSTI_A_1.getCount()];
                    //-------------------
                    String streamOfIds_suggestion = "";
                    String streamOfOrients_suggestion = "";
                    String streamOfSummary_suggestion = "";
                    if (MSGS_FROM_INSTI_A_1.getCount() > 0) {
                        MSGS_FROM_INSTI_A_1.moveToLast();
                        for (int i = 0; i < MSGS_FROM_INSTI_A_1.getCount(); i++) {
                            Notif_number_suggestion[i] = MSGS_FROM_INSTI_A_1.getString(1);
                            Notif_title_suggestion[i] = MSGS_FROM_INSTI_A_1.getString(2);
                            origin_remote_id_suggestion[i] = MSGS_FROM_INSTI_A_1.getString(4);
                            imageUrl_suggestion[i] = MSGS_FROM_INSTI_A_1.getString(5);
                            bigText_suggestion[i] = MSGS_FROM_INSTI_A_1.getString(6);
                            regDate_suggestion[i] = MSGS_FROM_INSTI_A_1.getString(7);
                            sumMary_suggestion[i] = MSGS_FROM_INSTI_A_1.getString(8);
                            activationTag_suggestion[i] = MSGS_FROM_INSTI_A_1.getString(9);
                            notifType_suggestion[i] = MSGS_FROM_INSTI_A_1.getString(10);
                            Orient_suggestion[i] = MSGS_FROM_INSTI_A_1.getString(11);
                            //--------------
                            if (i == 0) {
                                streamOfIds_suggestion = streamOfIds_suggestion + origin_remote_id_suggestion[i];
                                streamOfOrients_suggestion = streamOfOrients_suggestion + Orient_suggestion[i];
                                streamOfSummary_suggestion = streamOfSummary_suggestion + sumMary_suggestion[i];
                            } else {
                                streamOfIds_suggestion = streamOfIds_suggestion + "~" + origin_remote_id_suggestion[i];
                                streamOfOrients_suggestion = streamOfOrients_suggestion + "~" + Orient_suggestion[i];
                                streamOfSummary_suggestion = streamOfSummary_suggestion + "~" + sumMary_suggestion[i];
                            }
                            MSGS_FROM_INSTI_A_1.moveToPrevious();
                        }
                    }
                    //----------go to server to bring more Q_S comments queries inside trick
                    new My_Q_S_Comments_Downloader_B(NDS_main.this, Config.NDS_NOTIFICATION_LOAD_NOTIF_MSGS_FROM_INSTI_A_1.toString(), streamOfIds_suggestion, streamOfOrients_suggestion, streamOfSummary_suggestion, MYRemoteId).execute();
                    new My_Q_S_Comments_Downloader_B(NDS_main.this, Config.NDS_NOTIFICATION_LOAD_NOTIF_MSGS_FROM_INSTI_B_1.toString(), streamOfIds_suggestion, streamOfOrients_suggestion, streamOfSummary_suggestion, MYRemoteId).execute();
                    //------------------
                    Log.w("test_notif_instiB", streamOfIds_suggestion);
                    //----------------Posts' Comments-
                    db.openToWrite();
                    Cursor CMNTS_ON_POSTS_A = db.queueNOTIFICATIONS_FOR_THE_TAG_NO_ORIENT("comments_on_posts", "none");
                    String[] Notif_number_comments_on_posts = new String[CMNTS_ON_POSTS_A.getCount()];
                    String[] Notif_title_comments_on_posts = new String[CMNTS_ON_POSTS_A.getCount()];
                    String[] origin_remote_id_comments_on_posts = new String[CMNTS_ON_POSTS_A.getCount()];
                    String[] imageUrl_comments_on_posts = new String[CMNTS_ON_POSTS_A.getCount()];
                    String[] bigText_comments_on_posts = new String[CMNTS_ON_POSTS_A.getCount()];
                    String[] regDate_comments_on_posts = new String[CMNTS_ON_POSTS_A.getCount()];
                    String[] sumMary_comments_on_posts = new String[CMNTS_ON_POSTS_A.getCount()];
                    String[] activationTag_comments_on_posts = new String[CMNTS_ON_POSTS_A.getCount()];
                    String[] notifType_comments_on_posts = new String[CMNTS_ON_POSTS_A.getCount()];
                    String[] Orient_comments_on_posts = new String[CMNTS_ON_POSTS_A.getCount()];
                    //-------------------
                    String streamOfIds_comments_on_posts = "";
                    String streamOfOrients_comments_on_posts = "";
                    String streamOfSummary_comments_on_posts = "";
                    if (CMNTS_ON_POSTS_A.getCount() > 0) {
                        CMNTS_ON_POSTS_A.moveToLast();
                        for (int i = 0; i < CMNTS_ON_POSTS_A.getCount(); i++) {
                            Notif_number_comments_on_posts[i] = CMNTS_ON_POSTS_A.getString(1);
                            Notif_title_comments_on_posts[i] = CMNTS_ON_POSTS_A.getString(2);
                            origin_remote_id_comments_on_posts[i] = CMNTS_ON_POSTS_A.getString(4);
                            imageUrl_comments_on_posts[i] = CMNTS_ON_POSTS_A.getString(5);
                            bigText_comments_on_posts[i] = CMNTS_ON_POSTS_A.getString(6);
                            regDate_comments_on_posts[i] = CMNTS_ON_POSTS_A.getString(7);
                            sumMary_comments_on_posts[i] = CMNTS_ON_POSTS_A.getString(8);
                            activationTag_comments_on_posts[i] = CMNTS_ON_POSTS_A.getString(9);
                            notifType_comments_on_posts[i] = CMNTS_ON_POSTS_A.getString(10);
                            Orient_comments_on_posts[i] = CMNTS_ON_POSTS_A.getString(11);
                            //--------------
                            if (i == 0) {
                                streamOfIds_comments_on_posts = streamOfIds_comments_on_posts + origin_remote_id_comments_on_posts[i];
                                streamOfOrients_comments_on_posts = streamOfOrients_comments_on_posts + Orient_comments_on_posts[i];
                                streamOfSummary_comments_on_posts = streamOfSummary_comments_on_posts + sumMary_comments_on_posts[i];
                            } else {
                                streamOfIds_comments_on_posts = streamOfIds_comments_on_posts + "~" + origin_remote_id_comments_on_posts[i];
                                streamOfOrients_comments_on_posts = streamOfOrients_comments_on_posts + "~" + Orient_comments_on_posts[i];
                                streamOfSummary_comments_on_posts = streamOfSummary_comments_on_posts + "~" + sumMary_comments_on_posts[i];
                            }
                            CMNTS_ON_POSTS_A.moveToPrevious();
                        }
                    }
                    //---send the stream to the server-----------
                    new My_POSTS_Comments_Downloader_A(NDS_main.this, Config.NDS_NOTIFICATION_LOAD_NOTIF_CMNTS_ON_POSTS_A.toString(), streamOfIds_comments_on_posts, streamOfOrients_comments_on_posts, streamOfSummary_comments_on_posts, MYRemoteId).execute();
                    new My_POSTS_Comments_Downloader_A(NDS_main.this, Config.NDS_NOTIFICATION_LOAD_NOTIF_CMNTS_ON_POSTS_B.toString(), streamOfIds_comments_on_posts, streamOfOrients_comments_on_posts, streamOfSummary_comments_on_posts, MYRemoteId).execute();
                    //-------------------------
                    Log.w("test_notif_instiP_C", streamOfIds_comments_on_posts);
                    //----------------Tags in posts -
                    /*
                    db.openToWrite();
                    Cursor TAG_IN_POSTS_A = db.queueNOTIFICATIONS_FOR_THE_TAG_NO_ORIENT("tags_on_posts", "none");
                    String[] Notif_number_tags_on_posts = new String[TAG_IN_POSTS_A.getCount()];
                    String[] Notif_title_tags_on_posts = new String[TAG_IN_POSTS_A.getCount()];
                    String[] origin_remote_id_tags_on_posts = new String[TAG_IN_POSTS_A.getCount()];
                    String[] imageUrl_tags_on_posts = new String[TAG_IN_POSTS_A.getCount()];
                    String[] bigText_tags_on_posts = new String[TAG_IN_POSTS_A.getCount()];
                    String[] regDate_tags_on_posts = new String[TAG_IN_POSTS_A.getCount()];
                    String[] sumMary_tags_on_posts = new String[TAG_IN_POSTS_A.getCount()];
                    String[] activationTag_tags_on_posts = new String[TAG_IN_POSTS_A.getCount()];
                    String[] notifType_tags_on_posts = new String[TAG_IN_POSTS_A.getCount()];
                    String[] Orient_tags_on_posts = new String[TAG_IN_POSTS_A.getCount()];
                    //-------------------
                    String streamOfIds_tags_on_posts = "";
                    String streamOfOrients_tags_on_posts = "";
                    String streamOfSummary_tags_on_posts = "";
                    if (TAG_IN_POSTS_A.getCount() > 0) {
                        TAG_IN_POSTS_A.moveToLast();
                        for (int i = 0; i < TAG_IN_POSTS_A.getCount(); i++) {
                            Notif_number_tags_on_posts[i] = TAG_IN_POSTS_A.getString(1);
                            Notif_title_tags_on_posts[i] = TAG_IN_POSTS_A.getString(2);
                            origin_remote_id_tags_on_posts[i] = TAG_IN_POSTS_A.getString(4);
                            imageUrl_tags_on_posts[i] = TAG_IN_POSTS_A.getString(5);
                            bigText_tags_on_posts[i] = TAG_IN_POSTS_A.getString(6);
                            regDate_tags_on_posts[i] = TAG_IN_POSTS_A.getString(7);
                            sumMary_tags_on_posts[i] = TAG_IN_POSTS_A.getString(8);
                            activationTag_tags_on_posts[i] = TAG_IN_POSTS_A.getString(9);
                            notifType_tags_on_posts[i] = TAG_IN_POSTS_A.getString(10);
                            Orient_tags_on_posts[i] = TAG_IN_POSTS_A.getString(11);
                            //--------------
                            if (i == 0) {
                                streamOfIds_tags_on_posts = streamOfIds_tags_on_posts + origin_remote_id_tags_on_posts[i];
                                streamOfOrients_tags_on_posts = streamOfOrients_tags_on_posts + Orient_tags_on_posts[i];
                                streamOfSummary_tags_on_posts = streamOfSummary_tags_on_posts + sumMary_tags_on_posts[i];
                            } else {
                                streamOfIds_tags_on_posts = streamOfIds_tags_on_posts + "~" + origin_remote_id_tags_on_posts[i];
                                streamOfOrients_tags_on_posts = streamOfOrients_tags_on_posts + "~" + Orient_tags_on_posts[i];
                                streamOfSummary_tags_on_posts = streamOfSummary_tags_on_posts + "~" + sumMary_tags_on_posts[i];
                            }
                            TAG_IN_POSTS_A.moveToPrevious();
                        }
                    }
                    //-----------------stream tag of posts
                    new My_POSTS_TAGS_Downloader(NDS_main.this, Config.NDS_NOTIFICATION_LOAD_NOTIF_TAG_IN_POSTS_A.toString(), streamOfIds_tags_on_posts, streamOfOrients_tags_on_posts, streamOfSummary_tags_on_posts, MYRemoteId).execute();
                    */
                    //---------------------------------
                    //Log.w("test_notif_instiT_P", streamOfIds_tags_on_posts);
                    //----------------Tags in posts' comments -
                   /* db.openToWrite();
                    Cursor TAG_IN_CMNTS_A = db.queueNOTIFICATIONS_FOR_THE_TAG_NO_ORIENT("tags_on_comments", "none");
                    String[] Notif_number_tags_on_comments = new String[TAG_IN_CMNTS_A.getCount()];
                    String[] Notif_title_tags_on_comments = new String[TAG_IN_CMNTS_A.getCount()];
                    String[] origin_remote_id_tags_on_comments = new String[TAG_IN_CMNTS_A.getCount()];
                    String[] imageUrl_tags_on_comments = new String[TAG_IN_CMNTS_A.getCount()];
                    String[] bigText_tags_on_comments = new String[TAG_IN_CMNTS_A.getCount()];
                    String[] regDate_tags_on_comments = new String[TAG_IN_CMNTS_A.getCount()];
                    String[] sumMary_tags_on_comments = new String[TAG_IN_CMNTS_A.getCount()];
                    String[] activationTag_tags_on_comments = new String[TAG_IN_CMNTS_A.getCount()];
                    String[] notifType_tags_on_comments = new String[TAG_IN_CMNTS_A.getCount()];
                    String[] Orient_tags_on_comments = new String[TAG_IN_CMNTS_A.getCount()];
                    //-------------------
                    String streamOfIds_tags_on_comments = "";
                    String streamOfOrients_tags_on_comments = "";
                    String streamOfSummary_tags_on_comments = "";
                    if (TAG_IN_CMNTS_A.getCount() > 0) {
                        TAG_IN_CMNTS_A.moveToLast();
                        for (int i = 0; i < TAG_IN_CMNTS_A.getCount(); i++) {
                            Notif_number_tags_on_comments[i] = TAG_IN_CMNTS_A.getString(1);
                            Notif_title_tags_on_comments[i] = TAG_IN_CMNTS_A.getString(2);
                            origin_remote_id_tags_on_comments[i] = TAG_IN_CMNTS_A.getString(4);
                            imageUrl_tags_on_comments[i] = TAG_IN_CMNTS_A.getString(5);
                            bigText_tags_on_comments[i] = TAG_IN_CMNTS_A.getString(6);
                            regDate_tags_on_comments[i] = TAG_IN_CMNTS_A.getString(7);
                            sumMary_tags_on_comments[i] = TAG_IN_CMNTS_A.getString(8);
                            activationTag_tags_on_comments[i] = TAG_IN_CMNTS_A.getString(9);
                            notifType_tags_on_comments[i] = TAG_IN_CMNTS_A.getString(10);
                            Orient_tags_on_comments[i] = TAG_IN_CMNTS_A.getString(11);
                            //--------------
                            if (i == 0) {
                                streamOfIds_tags_on_comments = streamOfIds_tags_on_comments + origin_remote_id_tags_on_comments[i];
                                streamOfOrients_tags_on_comments = streamOfOrients_tags_on_comments + Orient_tags_on_comments[i];
                                streamOfSummary_tags_on_comments = streamOfSummary_tags_on_comments + sumMary_tags_on_comments[i];
                            } else {
                                streamOfIds_tags_on_comments = streamOfIds_tags_on_comments + "~" + origin_remote_id_tags_on_comments[i];
                                streamOfOrients_tags_on_comments = streamOfOrients_tags_on_comments + "~" + Orient_tags_on_comments[i];
                                streamOfSummary_tags_on_comments = streamOfSummary_tags_on_comments + "~" + sumMary_tags_on_comments[i];
                            }
                            TAG_IN_CMNTS_A.moveToPrevious();
                        }
                    }
                    //-----------------------------
                    //-----------------stream tag of posts' comments
                    new My_COMMENTS_TAGS_Downloader(NDS_main.this, Config.NDS_NOTIFICATION_LOAD_NOTIF_TAG_IN_CMNTS_A.toString(), streamOfIds_tags_on_comments, streamOfOrients_tags_on_comments, streamOfSummary_tags_on_comments, MYRemoteId).execute();
                    */
                    //---------------------------------
                    //Log.w("test_notif_instiT_C", streamOfIds_tags_on_comments);
                    //----------------keyword in posts -
                    db.openToWrite();
                    Cursor KEYWORD_IN_POSTS_A = db.queueNOTIFICATIONS_FOR_THE_TAG_NO_ORIENT("keyword_on_posts", "none");
                    String[] Notif_number_keyword_in_posts = new String[KEYWORD_IN_POSTS_A.getCount()];
                    String[] Notif_title_keyword_in_posts = new String[KEYWORD_IN_POSTS_A.getCount()];
                    String[] origin_remote_id_keyword_in_posts = new String[KEYWORD_IN_POSTS_A.getCount()];
                    String[] imageUrl_keyword_in_posts = new String[KEYWORD_IN_POSTS_A.getCount()];
                    String[] bigText_keyword_in_posts = new String[KEYWORD_IN_POSTS_A.getCount()];
                    String[] regDate_keyword_in_posts = new String[KEYWORD_IN_POSTS_A.getCount()];
                    String[] sumMary_keyword_in_posts = new String[KEYWORD_IN_POSTS_A.getCount()];
                    String[] activationTag_keyword_in_posts = new String[KEYWORD_IN_POSTS_A.getCount()];
                    String[] notifType_keyword_in_posts = new String[KEYWORD_IN_POSTS_A.getCount()];
                    String[] Orient_keyword_in_posts = new String[KEYWORD_IN_POSTS_A.getCount()];
                    //-------------------
                    String streamOfIds_keyword_in_posts = "";
                    String streamOfOrients_keyword_in_posts = "";
                    String streamOfSummary_keyword_in_posts = "";
                    if (KEYWORD_IN_POSTS_A.getCount() > 0) {
                        KEYWORD_IN_POSTS_A.moveToLast();
                        for (int i = 0; i < KEYWORD_IN_POSTS_A.getCount(); i++) {
                            Notif_number_keyword_in_posts[i] = KEYWORD_IN_POSTS_A.getString(1);
                            Notif_title_keyword_in_posts[i] = KEYWORD_IN_POSTS_A.getString(2);
                            origin_remote_id_keyword_in_posts[i] = KEYWORD_IN_POSTS_A.getString(4);
                            imageUrl_keyword_in_posts[i] = KEYWORD_IN_POSTS_A.getString(5);
                            bigText_keyword_in_posts[i] = KEYWORD_IN_POSTS_A.getString(6);
                            regDate_keyword_in_posts[i] = KEYWORD_IN_POSTS_A.getString(7);
                            sumMary_keyword_in_posts[i] = KEYWORD_IN_POSTS_A.getString(8);
                            activationTag_keyword_in_posts[i] = KEYWORD_IN_POSTS_A.getString(9);
                            notifType_keyword_in_posts[i] = KEYWORD_IN_POSTS_A.getString(10);
                            Orient_keyword_in_posts[i] = KEYWORD_IN_POSTS_A.getString(11);
                            //--------------
                            if (i == 0) {
                                streamOfIds_keyword_in_posts = streamOfIds_keyword_in_posts + origin_remote_id_keyword_in_posts[i];
                                streamOfOrients_keyword_in_posts = streamOfOrients_keyword_in_posts + Orient_keyword_in_posts[i];
                                streamOfSummary_keyword_in_posts = streamOfSummary_keyword_in_posts + sumMary_keyword_in_posts[i];
                            } else {
                                streamOfIds_keyword_in_posts = streamOfIds_keyword_in_posts + "~" + origin_remote_id_keyword_in_posts[i];
                                streamOfOrients_keyword_in_posts = streamOfOrients_keyword_in_posts + "~" + Orient_keyword_in_posts[i];
                                streamOfSummary_keyword_in_posts = streamOfSummary_keyword_in_posts + "~" + sumMary_keyword_in_posts[i];
                            }
                            KEYWORD_IN_POSTS_A.moveToPrevious();
                        }
                    }
                    //------------------
                    //-----------------stream tag of posts' comments
                    new My_POSTS_KEYWORD_Downloader(NDS_main.this, Config.NDS_NOTIFICATION_LOAD_NOTIF_KEYWORD_IN_POSTS_A.toString(), streamOfIds_keyword_in_posts, streamOfOrients_keyword_in_posts, streamOfSummary_keyword_in_posts, MYRemoteId).execute();
                    //---------------------------------
                    Log.w("test_notif_instiK_P", streamOfIds_keyword_in_posts);
                    //----------------keyword in posts -
                    db.openToWrite();
                    Cursor KEYWORD_IN_CMNTS_A = db.queueNOTIFICATIONS_FOR_THE_TAG_NO_ORIENT("keyword_on_comments", "none");
                    String[] Notif_number_keyword_in_comments = new String[KEYWORD_IN_CMNTS_A.getCount()];
                    String[] Notif_title_keyword_in_comments = new String[KEYWORD_IN_CMNTS_A.getCount()];
                    String[] origin_remote_id_keyword_in_comments = new String[KEYWORD_IN_CMNTS_A.getCount()];
                    String[] imageUrl_keyword_in_comments = new String[KEYWORD_IN_CMNTS_A.getCount()];
                    String[] bigText_keyword_in_comments = new String[KEYWORD_IN_CMNTS_A.getCount()];
                    String[] regDate_keyword_in_comments = new String[KEYWORD_IN_CMNTS_A.getCount()];
                    String[] sumMary_keyword_in_comments = new String[KEYWORD_IN_CMNTS_A.getCount()];
                    String[] activationTag_keyword_in_comments = new String[KEYWORD_IN_CMNTS_A.getCount()];
                    String[] notifType_keyword_in_comments = new String[KEYWORD_IN_CMNTS_A.getCount()];
                    String[] Orient_keyword_in_comments = new String[KEYWORD_IN_CMNTS_A.getCount()];
                    //-------------------
                    String streamOfIds_keyword_in_comments = "";
                    String streamOfOrients_keyword_in_comments = "";
                    String streamOfSummary_keyword_in_comments = "";
                    if (KEYWORD_IN_CMNTS_A.getCount() > 0) {
                        KEYWORD_IN_CMNTS_A.moveToLast();
                        for (int i = 0; i < KEYWORD_IN_CMNTS_A.getCount(); i++) {
                            Notif_number_keyword_in_comments[i] = KEYWORD_IN_CMNTS_A.getString(1);
                            Notif_title_keyword_in_comments[i] = KEYWORD_IN_CMNTS_A.getString(2);
                            origin_remote_id_keyword_in_comments[i] = KEYWORD_IN_CMNTS_A.getString(4);
                            imageUrl_keyword_in_comments[i] = KEYWORD_IN_CMNTS_A.getString(5);
                            bigText_keyword_in_comments[i] = KEYWORD_IN_CMNTS_A.getString(6);
                            regDate_keyword_in_comments[i] = KEYWORD_IN_CMNTS_A.getString(7);
                            sumMary_keyword_in_comments[i] = KEYWORD_IN_CMNTS_A.getString(8);
                            activationTag_keyword_in_comments[i] = KEYWORD_IN_CMNTS_A.getString(9);
                            notifType_keyword_in_comments[i] = KEYWORD_IN_CMNTS_A.getString(10);
                            Orient_keyword_in_comments[i] = KEYWORD_IN_CMNTS_A.getString(11);
                            //--------------
                            if (i == 0) {
                                streamOfIds_keyword_in_comments = streamOfIds_keyword_in_comments + origin_remote_id_keyword_in_comments[i];
                                streamOfOrients_keyword_in_comments = streamOfOrients_keyword_in_comments + Orient_keyword_in_comments[i];
                                streamOfSummary_keyword_in_comments = streamOfSummary_keyword_in_comments + sumMary_keyword_in_comments[i];
                            } else {
                                streamOfIds_keyword_in_comments = streamOfIds_keyword_in_comments + "~" + origin_remote_id_keyword_in_comments[i];
                                streamOfOrients_keyword_in_comments = streamOfOrients_keyword_in_comments + "~" + Orient_keyword_in_comments[i];
                                streamOfSummary_keyword_in_comments = streamOfSummary_keyword_in_comments + "~" + sumMary_keyword_in_comments[i];
                            }
                            KEYWORD_IN_CMNTS_A.moveToPrevious();
                        }
                    }
                    //-----------------stream tag of posts' comments
                    new My_COMMENTS_KEYWORD_Downloader(NDS_main.this, Config.NDS_NOTIFICATION_LOAD_NOTIF_KEYWORD_IN_CMNTS_A.toString(), streamOfIds_keyword_in_comments, streamOfOrients_keyword_in_comments, streamOfSummary_keyword_in_comments, MYRemoteId).execute();
                    //---------------------------------
                    Log.w("test_notif_instiK_C", streamOfIds_keyword_in_comments);
                }
            }).start();

            handlerRecycleActivity.postDelayed(this, 30000);

        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_urubuga) {
            // Handle the camera action
            Intent intent = new Intent(NDS_main.this, Urubuga_Services.class);
            //intent.putExtra("keyId", "0");
            startActivity(intent);
        } else if (id == R.id.nav_ubutumwa) {
            Intent intent = new Intent(NDS_main.this, NDS_messages.class);
            //intent.putExtra("keyId", "0");
            startActivity(intent);

        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(NDS_main.this, NDS_settings.class);
            //intent.putExtra("keyId", "0");
            startActivity(intent);

        } else if (id == R.id.nav_sohoka) {
            LogOut(pool);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

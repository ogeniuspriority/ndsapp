package com.ogeniuspriority.nds.nds;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ogeniuspriority.nds.nds.m_DataObject.My_Community_Posts;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Posts_Count_New_Posts_Downloader;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Posts_DeleteThisPost;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Posts_Downloader;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Posts_Downloader_Search;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Posts_Local_DataParser;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Posts_Old_Downloader;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Posts_Report;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Suggestion_Boxes_Delete_Intruder_Downloader;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Suggestion_Boxes_Downloader;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Suggestion_Boxes_put_load_Downloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Urubuga_Services extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    static int win_ = 1;
    public static int whichTypeOfData = 3;
    public static int TheLastId = 0;
    public static String[] whichTypeOfData_Keys = new String[]{"myposts", "trendingposts", "allposts", "outbox", "library"};
    public static String AllComments = "";
    public static Boolean flag_loading = false;
    //-------------
    public static int lastId_Of_post_Retreived = 0;
    public static int firstId_Of_post_Retreived = 0;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private static ViewPager mViewPager;
    public static ListView forum_whisper;
    public static ListView suggested_pgds;
    static FloatingActionButton fab;
    public static SwipeRefreshLayout swipeView;
    //---
    private ListAdapter Forum_adapter;
    //---------
    public static ArrayList<My_Community_Posts> my_list_ok;
    public static int lastPos;

    public void refreshAndScroll(View v) throws ExecutionException, InterruptedException, TimeoutException {
        String res = new My_Community_Posts_Downloader(Urubuga_Services.this, Config.NDS_COMMUNITY_LOAD_POSTS, forum_whisper, swipeView, "" + firstId_Of_post_Retreived, PlaceholderFragment.MYRemoteId).execute().get(30000, TimeUnit.MILLISECONDS);


    }

    //---
    public void loadMorePreviewsPosts(View v) {
        new My_Community_Posts_Old_Downloader(Urubuga_Services.this, Config.NDS_LOAD_PREVIOUS_POSTS, forum_whisper, swipeView, Urubuga_Services.lastId_Of_post_Retreived + "", 0, PlaceholderFragment.loadMoreView).execute();

        //  Toast.makeText(getBaseContext(), Urubuga_Services.firstId_Of_post_Retreived+"<--Previous posts!-->"+Urubuga_Services.lastId_Of_post_Retreived, Toast.LENGTH_SHORT).show();


    }

    public void UnlikeThisPost(View v) {
        Toast.makeText(getBaseContext(), "UnLiked!", Toast.LENGTH_SHORT).show();
        //v.setBackgroundColor(Color.parseColor("#dcadda"));
        // v.setBackgroundResource(R.drawable.dislike_active);
        //--

    }

    public void showConfirmationMessage(final View v) {


        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.pop_up_report, null);
        final PopupWindow popupWindow = new PopupWindow(
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
        });       //----
        // popupWindow.showAsDropDown(v);
        popupWindow.showAtLocation(v, Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
        popupWindow.setAnimationStyle(android.R.style.TextAppearance_DeviceDefault_Small_Inverse);
        popupWindow.setFocusable(true);
        popupWindow.update();
        //-----------
        final RadioGroup choiceReportType = (RadioGroup) popupView.findViewById(R.id.choiceReportType);
        final RadioButton radioButton = (RadioButton) popupView.findViewById(R.id.radioButton);
        final RadioButton radioButton2 = (RadioButton) popupView.findViewById(R.id.radioButton2);
        final RadioButton radioButton3 = (RadioButton) popupView.findViewById(R.id.radioButton2);
        final EditText reportText = (EditText) popupView.findViewById(R.id.reportText);
        reportText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        reportText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Do whatever you want here
                    if (!reportText.getText().toString().isEmpty()) {

                        try {
                            String Response = new My_Community_Posts_Report(Urubuga_Services.this, Config.NDS_REPORT_A_POST, v.getTag().toString(), reportText.getText().toString(), PlaceholderFragment.MYRemoteId).execute().get();
                            //-------------------okay2017
                            if (Response != null) {
                                if (Response.length() >= 4) {
                                    Toast.makeText(Urubuga_Services.this, "Report received!", Toast.LENGTH_SHORT).show();
                                    popupWindow.dismiss();
                                }
                            } else {
                                Toast.makeText(Urubuga_Services.this, "Failed! No internet", Toast.LENGTH_SHORT).show();
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(Urubuga_Services.this, "Empty field!", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }
                return false;
            }
        });
        Button cancel = (Button) popupView.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        //----------------
        choiceReportType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id1 = radioButton.getId();
                int id2 = radioButton2.getId();
                int id3 = radioButton3.getId();
                if (choiceReportType.getCheckedRadioButtonId() == id1) {
                    try {
                        String Response = new My_Community_Posts_Report(Urubuga_Services.this, Config.NDS_REPORT_A_POST, v.getTag().toString(), "Insulting", PlaceholderFragment.MYRemoteId).execute().get(30000, TimeUnit.MILLISECONDS);
                        //-------------------okay2017
                        if (Response != null) {
                            if (Response.length() >= 4) {
                                Toast.makeText(Urubuga_Services.this, "Report received!", Toast.LENGTH_SHORT).show();
                                popupWindow.dismiss();
                            }
                        } else {
                            Toast.makeText(Urubuga_Services.this, "Failed! No internet", Toast.LENGTH_SHORT).show();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }

                } else if (choiceReportType.getCheckedRadioButtonId() == id2) {
                    try {
                        String Response = new My_Community_Posts_Report(Urubuga_Services.this, Config.NDS_REPORT_A_POST, v.getTag().toString(), "Inappropriate", PlaceholderFragment.MYRemoteId).execute().get(30000, TimeUnit.MILLISECONDS);
                        //-------------------okay2017
                        if (Response != null) {
                            if (Response.length() >= 4) {
                                Toast.makeText(Urubuga_Services.this, "Report received!", Toast.LENGTH_SHORT).show();
                                popupWindow.dismiss();
                            }
                        } else {
                            Toast.makeText(Urubuga_Services.this, "Failed! No internet", Toast.LENGTH_SHORT).show();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        Button ok_sent = (Button) popupView.findViewById(R.id.ok_sent);
        ok_sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!reportText.getText().toString().isEmpty()) {

                    try {
                        String Response = new My_Community_Posts_Report(Urubuga_Services.this, Config.NDS_REPORT_A_POST, v.getTag().toString(), reportText.getText().toString(), PlaceholderFragment.MYRemoteId).execute().get(30000, TimeUnit.MILLISECONDS);
                        //-------------------okay2017
                        if (Response != null) {
                            if (Response.length() >= 4) {
                                Toast.makeText(Urubuga_Services.this, "Report received!", Toast.LENGTH_SHORT).show();

                                popupWindow.dismiss();
                            }
                        } else {
                            Toast.makeText(Urubuga_Services.this, "Failed! No internet", Toast.LENGTH_SHORT).show();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(Urubuga_Services.this, "Empty field!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void DeletePostFromCloud(final View v) {


        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.remove_this_post_from_cloud, null);
        final PopupWindow popupWindow = new PopupWindow(
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
        });       //----
        // popupWindow.showAsDropDown(v);
        popupWindow.showAtLocation(v, Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
        popupWindow.setAnimationStyle(android.R.style.TextAppearance_DeviceDefault_Small_Inverse);
        popupWindow.setFocusable(true);
        popupWindow.update();
        //-----------
        final RadioGroup choiceReportType = (RadioGroup) popupView.findViewById(R.id.choiceReportType);
        final RadioButton radioButton = (RadioButton) popupView.findViewById(R.id.radioButton);
        final CheckBox checkBox = (CheckBox) popupView.findViewById(R.id.checkBox);


        Button cancel = (Button) popupView.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        //----------------
        choiceReportType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

            }
        });

        Button ok_sent = (Button) popupView.findViewById(R.id.ok_sent);
        ok_sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioButton.isChecked() && checkBox.isChecked()) {

                    try {
                        String Response = new My_Community_Posts_DeleteThisPost(Urubuga_Services.this, Config.NDS_DELETE_THIS_POST, "" + v.getTag()).execute().get(30000, TimeUnit.MILLISECONDS);
                        //-------------------okay2017
                        if (Response != null) {
                            if (Response.length() >= 4) {
                                Toast.makeText(Urubuga_Services.this, "Post Deleted!", Toast.LENGTH_SHORT).show();
                                PlaceholderFragment.new_post_available = 0;
                                PlaceholderFragment.new_post_available_reserve = 0;
                                String res = new My_Community_Posts_Downloader(Urubuga_Services.this, Config.NDS_COMMUNITY_LOAD_POSTS, forum_whisper, swipeView, "" + firstId_Of_post_Retreived, PlaceholderFragment.MYRemoteId).execute().get();


                                popupWindow.dismiss();
                            }
                        } else {
                            Toast.makeText(Urubuga_Services.this, "Failed! No internet", Toast.LENGTH_SHORT).show();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(Urubuga_Services.this, "Check and Confirm Deletion", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urubuga__services);
        lastId_Of_post_Retreived = 0;
        firstId_Of_post_Retreived = 0;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView Toggle_me = (ImageView) findViewById(R.id.toggle_me);
     //   toolbar.setLogo(R.mipmap.neza_000_we);

        //---------------------


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        //----------------


        //------------

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Urubuga_Services.this, Forum_post.class);
                //intent.putExtra("keyId", "0");
                startActivity(intent);
            }
        });

        fab.setImageResource(R.drawable.plus_dark);
        fab.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_urubuga__services, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(Urubuga_Services.this, NDS_settings.class);
            //intent.putExtra("keyId", "0");
            startActivity(intent);
            return true;
        }
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        View rootView;
        TextView textView;
        public static String UserId;
        NDS_db_adapter db;
        public static String MYRemoteId = "";
        private static String MYAvatar = "";
        private static String MYNames = "";
        public static EditText Search;
        //--------
        public static View loadMoreView;
        //--
        Handler m_handler;
        Runnable m_handlerTask;
        static TextView infoText;
        static TextView infoLoadNew;
        public static int new_post_available;
        public static int new_post_available_reserve;
        static int countThis = 0;
        static volatile ConnectivityManager cm;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //===================================================
            db = new NDS_db_adapter(getActivity());
            db.openToWrite();
            Cursor UserCreds = db.queueAllFromCreds();

            if (UserCreds.moveToLast()) {
                MYRemoteId = UserCreds.getString(9);
                MYAvatar = UserCreds.getString(3);
                MYNames = UserCreds.getString(1);
            } else {


            }
            // fab.hide();
            Log.w("0000", "" + getArguments().getInt(ARG_SECTION_NUMBER));
            //----
            String pageId;
            Intent intent = getActivity().getIntent();
            Bundle bundle = intent.getExtras();
            //------------------------

            try {
                pageId = bundle.getString("pageId");
                Log.w("pageId", "" + pageId);

                mViewPager.setCurrentItem(Integer.parseInt(pageId), true);
            } catch (RuntimeException hi) {

            }


            //------------------
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    win_ = 1;
                    //fab.setVisibility(View.INVISIBLE);
                    // fab.hide();
                    rootView = inflater.inflate(R.layout.fragment_urubuga__services_survey, container, false);
                    textView = (TextView) rootView.findViewById(R.id.section_label);
                    textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                    textView.setText("Okay survey 1");
                    //---------------

                    Button load_survey_url = (Button) rootView.findViewById(R.id.load_survey_url);
                    final WebView browse_url = (WebView) rootView.findViewById(R.id.browse_url);
                    //---
                    WebSettings settings = browse_url.getSettings();
                    settings.setJavaScriptEnabled(true);
                    browse_url.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
                    browse_url.setWebViewClient(new WebViewClient() {
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            view.loadUrl(url);
                            return true;
                        }

                        public void onPageFinished(WebView view, String url) {
                        }

                        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                        }
                    });
                    //---------
                    load_survey_url.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            browse_url.loadUrl(Config.NEZA_SURVERY_TRAIL);
                            Toast.makeText(getActivity(), "Loading...", Toast.LENGTH_SHORT);
                        }
                    });


                    break;
                case 2:
                    win_ = 2;
                    rootView = inflater.inflate(R.layout.fragment_urubuga__services_suggestion_new, container, false);

                    suggested_pgds = (ListView) rootView.findViewById(R.id.suggested_pgds);
                    //----------------------------------------------
                    new My_Community_Suggestion_Boxes_put_load_Downloader(getActivity(), suggested_pgds).execute();
                    //---------------------------
                    TextView Add_suggestion = (TextView) rootView.findViewById(R.id.Add_suggestion);
                    //-----------
                    suggested_pgds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            TextView Name_of_institution = (TextView) view.findViewById(R.id.Name_of_institution);
                            TextView motto_ = (TextView) view.findViewById(R.id.motto_);
                            String choiceOne = Name_of_institution.getText().toString();
                            Intent intent = new Intent(getActivity(), Suggestion_box_suggest.class);
                            //---------------------------------------------------------------------------
                            //intent.putExtra("keyId", "0");
                            intent.putExtra("choiceOne", choiceOne);
                            startActivity(intent);
                        }
                    });
                    //----------------
                    Add_suggestion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), Suggestion_box_suggest.class);
                            intent.putExtra("choiceOne", "none");
                            startActivity(intent);

                        }
                    });

                    break;
                case 3:
                    win_ = 3;
//                     fab.show();
//                    fab.setBackgroundColor(Color.CYAN);
//                     fab.setVisibility(View.VISIBLE);
                    rootView = inflater.inflate(R.layout.fragment_urubuga__services_forum, container, false);
                    textView = (TextView) rootView.findViewById(R.id.section_label);
                    textView.setText("Okay forum 3");
                    //StrictMode for smooth list scroll
                    //StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
                    //---------------Search a post


                    //---------------
                    forum_whisper = (ListView) rootView.findViewById(R.id.forum_whisper);
                    //------------------------------
                    loadMoreView = ((LayoutInflater) getActivity()
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                            .inflate(R.layout.forum_main_feed_load_more, null, false);

                    //--

                    //--
                    forum_whisper.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                    forum_whisper.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            view.setSelected(true);
                            //------------------------------
                            ImageView poster_Avatar = (ImageView) view.findViewById(R.id.poster_Avatar);
                            TextView poster_name = (TextView) view.findViewById(R.id.poster_name);
                            TextView TimeStamp = (TextView) view.findViewById(R.id.TimeStamp);
                            TextView post_Data = (TextView) view.findViewById(R.id.post_Data);
                            TextView coments_ = (TextView) view.findViewById(R.id.coments_);
                            TextView likes_ = (TextView) view.findViewById(R.id.likes_);
                            TextView Dislikes = (TextView) view.findViewById(R.id.Dislikes);
                            TextView views_ = (TextView) view.findViewById(R.id.views_);
                            //------------------
                            TextView myID = (TextView) view.findViewById(R.id.myID);
                            TextView myID_Comments = (TextView) view.findViewById(R.id.myID_Comments);
                            TextView myID_Avatar = (TextView) view.findViewById(R.id.myID_Avatar);
                            //-------
                            BitmapDrawable bm_000 = (BitmapDrawable) poster_Avatar.getDrawable();
                            Bitmap bm = bm_000.getBitmap();
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                            byte[] byteArray = stream.toByteArray();
                            //-------------------
                            final Intent intent = new Intent(getActivity(), NDS_posts_comments.class);
                            //----------------------------
                            String[] MyPostData = new String[]{poster_name.getText().toString(),
                                    TimeStamp.getText().toString(), post_Data.getText().toString(),
                                    coments_.getText().toString(), likes_.getText().toString(),
                                    Dislikes.getText().toString(), views_.getText().toString(),
                                    myID.getText().toString(), myID_Comments.getText().toString(),
                                    myID_Avatar.getText().toString(),
                                    coments_.getText().toString()
                            };
                            intent.putExtra("MyPostData", MyPostData);
                            //---------------
                            // intent.putExtra("imgProfileByteArray", byteArray);
                            getActivity().startActivity(intent);
                            //Toast.makeText(getActivity(), "Okay", Toast.LENGTH_SHORT).show();
                            //------------
                            //--------

                        }
                    });
                    //-----------------------
                    swipeView = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_view);
                    swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            //------------
                            if (whichTypeOfData == 1 || whichTypeOfData == 2 || whichTypeOfData == 3) {
                                swipeView.setRefreshing(true);
                                flag_loading = false;
                              //  infoText.setVisibility(View.VISIBLE);
                                infoLoadNew.setVisibility(View.GONE);


                                new My_Community_Posts_Downloader(getActivity(), Config.NDS_COMMUNITY_LOAD_POSTS, forum_whisper, swipeView, "" + firstId_Of_post_Retreived, MYRemoteId).execute();
                            } else {
                                swipeView.setRefreshing(false);
                            }

                        }
                    });
                    swipeView.setColorSchemeColors(Color.GRAY, Color.GREEN, Color.BLUE,
                            Color.RED, Color.CYAN);
                    swipeView.setDistanceToTriggerSync(20);// in dips
                    swipeView.setSize(SwipeRefreshLayout.LARGE);// LARGE also can be used
                    swipeView.canChildScrollUp();
                    //--------------
                    forum_whisper.addFooterView(loadMoreView);
                    forum_whisper.setOnScrollListener(new AbsListView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(AbsListView view, int scrollState) {

                        }

                        @Override
                        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                            int topRowVerticalPosition = (forum_whisper == null ||
                                    forum_whisper.getChildCount() == 0) ? 0 : forum_whisper.getChildAt(0).getTop();

                            swipeView.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
                            //-------------
                            swipeView.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
                            //-------------------------------
                            if (whichTypeOfData != 5) {
                                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0) {
                                    if (flag_loading == false) {
                                        flag_loading = true;


                                        //Toast.makeText(getActivity(), "appending...", Toast.LENGTH_SHORT).show();
                                    }
                                    Urubuga_Services.lastPos = totalItemCount;
                                }

                            }


                            //----------------------


                        }
                    });
                    //------------Message--
                    infoText = (TextView) rootView.findViewById(R.id.infoText);
                    infoLoadNew = (TextView) rootView.findViewById(R.id.infoLoadNew);
                   // infoText.setText("Pull Down to refresh All Posts.");
                    //----
                    Search = (EditText) rootView.findViewById(R.id.Search);
                    //-------------
                    TextWatcher fieldValidatorTextWatcher = new TextWatcher() {
                        @Override
                        public void afterTextChanged(Editable s) {
                        }

                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (filterLongEnough()) {
                                lastId_Of_post_Retreived = 0;
                                firstId_Of_post_Retreived = 0;
                                whichTypeOfData = 4;
                                new My_Community_Posts_Downloader_Search(getActivity(), Config.NDS_SEARCH_A_POST, forum_whisper, swipeView, Search.getText().toString(), MYRemoteId, Search).execute();


                            }
                        }

                        private boolean filterLongEnough() {
                            return Search.getText().toString().trim().length() > 3;
                        }
                    };
                    Search.addTextChangedListener(fieldValidatorTextWatcher);


                    //---------------
                    final Button Library = (Button) rootView.findViewById(R.id.Library);
                    //final Button OutBox = (Button) rootView.findViewById(R.id.OutBox);
                    final Button TrendingPosts = (Button) rootView.findViewById(R.id.TrendingPosts);
                    final Button Myposts = (Button) rootView.findViewById(R.id.Myposts);
                    final Button AllPosts = (Button) rootView.findViewById(R.id.AllPosts);
                    //-------------
                    AllPosts.setTextColor(Color.parseColor("#ffffff"));

                    AllPosts.setBackgroundResource(R.drawable.button_forum);
                    Library.setTextColor(Color.parseColor("#ffffff"));
                    Library.setBackgroundResource(R.drawable.button_forum);
                    // OutBox.setBackgroundColor(Color.parseColor("#E0E0E0"));
                    TrendingPosts.setTextColor(Color.parseColor("#ffffff"));
                    TrendingPosts.setBackgroundResource(R.drawable.button_forum);
                    Myposts.setTextColor(Color.parseColor("#ffffff"));
                    Myposts.setBackgroundResource(R.drawable.button_forum);
                    //----------
                    if (whichTypeOfData == 3) {

                        AllPosts.setBackgroundResource(R.drawable.button_pressed);

                    }
                    Library.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Library.setBackgroundResource(R.drawable.button_pressed);
                            TrendingPosts.setBackgroundResource(R.drawable.button_forum);
                            Myposts.setBackgroundResource(R.drawable.button_forum);
                            AllPosts.setBackgroundResource(R.drawable.button_forum);
                            Myposts.setBackgroundResource(R.drawable.button_forum);
                            ///==
//                            AllPosts.setTextColor(Color.parseColor("#191970"));
//                            Library.setTextColor(Color.parseColor("#006400"));
//                            // OutBox.setBackgroundColor(Color.parseColor("#E0E0E0"));
//                            TrendingPosts.setTextColor(Color.parseColor("#191970"));
//                            Myposts.setTextColor(Color.parseColor("#191970"));
                            //-------
                            infoText.setVisibility(View.VISIBLE);
                            infoLoadNew.setVisibility(View.GONE);
                            lastId_Of_post_Retreived = 0;
                            firstId_Of_post_Retreived = 0;
                            whichTypeOfData = 5;
                            new My_Community_Posts_Local_DataParser(getActivity(), forum_whisper, swipeView).execute();
                        }
                    });

                    TrendingPosts.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Library.setBackgroundResource(R.drawable.button_forum);
                            TrendingPosts.setBackgroundResource(R.drawable.button_pressed);
                            Myposts.setBackgroundResource(R.drawable.button_forum);
                            AllPosts.setBackgroundResource(R.drawable.button_forum);
                            Myposts.setBackgroundResource(R.drawable.button_forum);

//                            AllPosts.setTextColor(Color.parseColor("#191970"));
//                            Library.setTextColor(Color.parseColor("#191970"));
//                            // OutBox.setBackgroundColor(Color.parseColor("#E0E0E0"));
//                            TrendingPosts.setTextColor(Color.parseColor("#006400"));
//                            Myposts.setTextColor(Color.parseColor("#191970"));
                            //----------------
                            infoText.setVisibility(View.VISIBLE);
                            infoLoadNew.setVisibility(View.GONE);
                            lastId_Of_post_Retreived = 0;
                            firstId_Of_post_Retreived = 0;
                            whichTypeOfData = 2;
                            swipeView.setRefreshing(true);
                            new My_Community_Posts_Downloader(getActivity(), Config.NDS_COMMUNITY_LOAD_POSTS, forum_whisper, swipeView, "" + firstId_Of_post_Retreived, MYRemoteId).execute();

                        }
                    });
                    Myposts.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Library.setBackgroundResource(R.drawable.button_forum);
                            TrendingPosts.setBackgroundResource(R.drawable.button_forum);
                            Myposts.setBackgroundResource(R.drawable.button_pressed);
                            AllPosts.setBackgroundResource(R.drawable.button_forum);
                            // Myposts.setBackgroundResource(R.drawable.button_forum);
                            //----------
                            infoText.setVisibility(View.VISIBLE);
                            infoLoadNew.setVisibility(View.GONE);

//                            AllPosts.setTextColor(Color.parseColor("#191970"));
//                            Library.setTextColor(Color.parseColor("#191970"));
//                            // OutBox.setBackgroundColor(Color.parseColor("#E0E0E0"));
//                            TrendingPosts.setTextColor(Color.parseColor("#191970"));
//                            Myposts.setTextColor(Color.parseColor("#006400"));
                            lastId_Of_post_Retreived = 0;
                            firstId_Of_post_Retreived = 0;
                            //--
                            whichTypeOfData = 1;
                            //--------------------
                            swipeView.setRefreshing(true);
                            new My_Community_Posts_Downloader(getActivity(), Config.NDS_COMMUNITY_LOAD_POSTS, forum_whisper, swipeView, "" + firstId_Of_post_Retreived, MYRemoteId).execute();

                        }
                    });
                    //AllPosts.setBackgroundResource(R.drawable.buttonstyle);
                    new My_Community_Posts_Downloader(getActivity(), Config.NDS_COMMUNITY_LOAD_POSTS, forum_whisper, swipeView, "" + firstId_Of_post_Retreived, MYRemoteId).execute();

                    AllPosts.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            lastId_Of_post_Retreived = 0;
                            firstId_Of_post_Retreived = 0;
                            //---
                            Library.setBackgroundResource(R.drawable.button_forum);
                            TrendingPosts.setBackgroundResource(R.drawable.button_forum);
                            Myposts.setBackgroundResource(R.drawable.button_forum);
                            AllPosts.setBackgroundResource(R.drawable.button_pressed);
                            Myposts.setBackgroundResource(R.drawable.button_forum);
                            //--
//                            AllPosts.setTextColor(Color.parseColor("#006400"));
//                            Library.setTextColor(Color.parseColor("#191970"));
//                            // OutBox.setBackgroundColor(Color.parseColor("#E0E0E0"));
//                            TrendingPosts.setTextColor(Color.parseColor("#191970"));
//                            Myposts.setTextColor(Color.parseColor("#191970"));
                            //--
                            infoText.setVisibility(View.VISIBLE);
                            infoLoadNew.setVisibility(View.GONE);
                            whichTypeOfData = 3;
                            //-----
                            swipeView.setRefreshing(true);
                            new My_Community_Posts_Downloader(getActivity(), Config.NDS_COMMUNITY_LOAD_POSTS, forum_whisper, swipeView, "" + firstId_Of_post_Retreived, MYRemoteId).execute();

                        }
                    });
                    break;
                default:
                    //fab.setVisibility(View.INVISIBLE);
                    break;
            }
            //----------------------------Find new posts and show links
            m_handler = new Handler();
            m_handlerTask = new Runnable() {
                @Override
                public void run() {
                    //-------------------------
                    //----detect server availablility---\

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try {


                                cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                                if (netInfo != null && netInfo.isConnected()) {
                                    try {
                                        URL url = new URL(Config.IP_ADDRESS);   // Change to "http://google.com" for www  test.
                                        HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                                        urlc.setConnectTimeout(10 * 1000);          // 10 s.
                                        urlc.connect();
                                        if (urlc.getResponseCode() == 200) {        // 200 = "OK" code (http connection is fine).
                                            Log.wtf("Connection", "Success !");


                                            //==============
                                            try {
                                                String reslt_new_posts = new My_Community_Posts_Count_New_Posts_Downloader(getActivity(), Config.NDS_LOAD_NEW_POSTS_COUNT).execute().get();
                                                //-----------
                                                if (reslt_new_posts != null && whichTypeOfData == 3) {
                                                    reslt_new_posts = reslt_new_posts.replaceAll("\\s", "");

                                                    new_post_available = Integer.parseInt(reslt_new_posts);
                                                    Log.w("2017", "" + new_post_available);
                                                    if (new_post_available != 0) {
                                                        new_post_available_reserve = new_post_available;
                                                    }
                                                    getActivity().runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {


                                                            if (infoText != null && whichTypeOfData == 3) {
                                                                if (new_post_available == 0) {
                                                                    infoText.setVisibility(View.VISIBLE);
                                                                    infoLoadNew.setVisibility(View.GONE);
                                                                } else if (new_post_available > 1 && new_post_available < 84) {
                                                                    infoText.setVisibility(View.GONE);
                                                                    infoLoadNew.setVisibility(View.VISIBLE);
                                                                    infoLoadNew.setText(new_post_available + " new posts");
                                                                }else {
                                                                    infoText.setVisibility(View.VISIBLE);
                                                                    infoLoadNew.setVisibility(View.GONE);

                                                                }
                                                            }
                                                        }
                                                    });
                                                }

                                            } catch (InterruptedException e) {
                                                infoText.setVisibility(View.VISIBLE);
                                                infoLoadNew.setVisibility(View.GONE);
                                                e.printStackTrace();
                                            } catch (ExecutionException e) {
                                                e.printStackTrace();
                                                infoText.setVisibility(View.VISIBLE);
                                                infoLoadNew.setVisibility(View.GONE);
                                            }
                                            //--------------------------------okay---

                                            new My_Community_Suggestion_Boxes_Downloader(getActivity(), Config.NDS_LOAD_COMMUNITY_SUGGESTION_BOXES, suggested_pgds).execute();


                                            //-----check suggestion credibility
                                            Cursor MyLocalSuggestionBoxes = db.GET_ALL_NEZA_COMMUNITY_SUGGESTION_BOXES();
                                            MyLocalSuggestionBoxes.moveToFirst();
                                            String[] remoteId_o = new String[MyLocalSuggestionBoxes.getCount()];
                                            MyLocalSuggestionBoxes.moveToFirst();
                                            //---


                                            for (int i = 0; i < MyLocalSuggestionBoxes.getCount(); i++) {
                                                remoteId_o[i] = MyLocalSuggestionBoxes.getString(0);
                                                try {
                                                    String checkThisId = new My_Community_Suggestion_Boxes_Delete_Intruder_Downloader(getActivity(), Config.NDS_DELETE_A_SUGGESTION_BOX, remoteId_o[i]).execute().get(30000, TimeUnit.MILLISECONDS);
                                                    if (checkThisId != null) {

                                                        int res = Integer.parseInt(checkThisId.replaceAll("\\s", ""));
                                                        //Log.w("check",remoteId_o[i]+"->"+res);
                                                        if (res == 2000) {
                                                            if (db.DELETE_A_POST_FROM_NEZA_COMMUNITY_SUGGESTION_BOX(remoteId_o[i])) {
                                                                if (suggested_pgds != null) {
                                                                    new My_Community_Suggestion_Boxes_put_load_Downloader(getActivity(), suggested_pgds).execute();

                                                                }
                                                            }

                                                        } else {

                                                        }


                                                    }

                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                } catch (ExecutionException e) {
                                                    e.printStackTrace();
                                                } catch (TimeoutException e) {
                                                    e.printStackTrace();
                                                }
                                                MyLocalSuggestionBoxes.moveToNext();
                                            }
                                        } else {

                                        }
                                    } catch (MalformedURLException e1) {

                                    } catch (IOException e) {

                                    }
                                }
                            } catch (RuntimeException sdbsgd) {

                            }
                            //-----------------
                        }
                    }).start();
                    m_handler.postDelayed(m_handlerTask, 7000);
                }
            };


            m_handlerTask.run();

            //--------
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (position == 3) {
                        if (positionOffset == 0) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "Okay", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 0) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // fab.setVisibility(View.INVISIBLE);
                                fab.hide();
                                lastId_Of_post_Retreived = 0;
                                firstId_Of_post_Retreived = 0;
                            }
                        });

                    } else if (position == 1) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //fab.setVisibility(View.INVISIBLE);
                                fab.hide();
                            }
                        });
                    } else if (position == 2) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //fab.setVisibility(View.VISIBLE);
                                fab.show();
                                fab.setBackgroundColor(Color.CYAN);


                            }
                        });
                    }
                    // Log.w("222",""+position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            //--------
            return rootView;
        }
        //---------


        private ListView listView;
        //variable to toggle city values on refresh
        boolean refreshToggle = true;
        Handler handler = new Handler() {
            public void handleMessage(android.os.Message msg) {

                if (refreshToggle) {
                    refreshToggle = false;

                } else {
                    refreshToggle = true;

                }
                //-----populate the data on refreshlistView.setAdapter(adapter);

                swipeView.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(),
                                        "city list refreshed", Toast.LENGTH_SHORT).show();
                                swipeView.setRefreshing(false);
                            }
                        });

                    }
                }, 1000);
            }

            ;
        };
        //------------------------


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 3) {
                fab.show();
                fab.setVisibility(View.VISIBLE);
            } else {
                fab.hide();
            }


            //-----
            return PlaceholderFragment.newInstance(position + 1);
        }


        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Survey";
                case 1:
                    return "Suggestion Box";
                case 2:
                    return "Forum";
            }
            return null;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}

package com.ogeniuspriority.nds.nds;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Suggestion_text_Send;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Upload_audio;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Upload_image;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Upload_video;
import com.ogeniuspriority.nds.nds.m_MySQL.My_suggesrion_boxes_find_avatars;
import com.ogeniuspriority.nds.nds.m_UI.My_Community_Posts_PicassoClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Suggestion_box_suggest extends AppCompatActivity {
    public static final int REQUEST_CROP = 6709;
    public static final int REQUEST_PICK = 9162;
    public static final int RESULT_ERROR = 404;
    static String[] remoteId;
    static String[] avatars;
    static String[] institution_names;
    static String[] institution_motto;
    NDS_db_adapter db;
    String keyword;
    static EditText editText;
    ListView mySearchList;
    View anchor;
    static ListView search_res_list;
    static PopupWindow popupWindow;
    static boolean specialCase = false;
    LinearLayout Holder_box;
    private static int SOME_RANDOM_REQUEST_CODE = 501;
    public static int IT_IS_FOR_GALLERY = 0;
    private static final int SELECT_AUDIO = 2;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static String videoFileName = "";
    String jsonString;
    //------
    private Bitmap cropResulted;
    //---
    private int PICK_IMAGE_REQUEST = 1;
    private int REQUEST_TAKE_GALLERY_VIDEO = 100;
    private PopupWindow popWindow_media;
    //--------
    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    private int position_play_me_now = 0;
    private boolean longClickActive = false;
    //---playback---
    private Button b1, b2, b3, b4;
    private ImageView iv;
    private int forwardTime = 5000;
    long clickDuration = 0;
    private static int downloadedSize;
    String selectedPath = "";
    //-------------
    private int backwardTime = 5000;
    private Handler myHandler = new Handler();
    private Uri picUri;
    //---------------

    //-------
    Handler m_handler;
    Runnable m_handlerTask;
    //--
    int counter_O = 0;
    //--
    public static String MYRemoteId = "";
    private static String MYAvatar = "";
    private static String MYNames = "";
    //------------------------
    private static MediaPlayer mediaPlayer;

    private double startTime = 0;
    private double finalTime = 0;

    private Handler myHandler_IU = new Handler();

    public static int oneTimeOnly = 0;
    //---
    public static boolean play_yes = false;
    //--------------------
    RelativeLayout image_tag;
    RelativeLayout video_tag;
    RelativeLayout audio_tag;
    LinearLayout textSuggestion;
    //----
    EditText Message;
    ImageView edixt_image;
    EditText edixt_label;
    //---
    VideoView edixt_image_video;
    EditText edixt_label_video;
    //-------------------
    TextView audio_name;
    TextView time_stamp;
    ImageView edixt_audio;
    private SeekBar seekBar;
    EditText edixt_label_audio;
    public static String mediaType_000 = "text";
    //--
    Button SendTxtSuggest;
    //-----------------
    String path_000, filename_000, myRemoteId_000, SuggestionBoxId_000;
    //---Time
    static int time_down = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion_box_suggest);
        getSupportActionBar().hide();
        //----------------------------
        Message = (EditText) findViewById(R.id.Message);
        edixt_image = (ImageView) findViewById(R.id.edixt_image);
        edixt_label = (EditText) findViewById(R.id.edixt_label);
        SendTxtSuggest = (Button) findViewById(R.id.SendTxtSuggest);
        //---
        edixt_image_video = (VideoView) findViewById(R.id.edixt_image_video);
        edixt_label_video = (EditText) findViewById(R.id.edixt_label_video);
        //-------------------
        audio_name = (TextView) findViewById(R.id.audio_name);
        time_stamp = (TextView) findViewById(R.id.time_stamp);
        edixt_audio = (ImageView) findViewById(R.id.edixt_audio);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        edixt_label_audio = (EditText) findViewById(R.id.edixt_label_audio);
        //------------------------
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        //------------------
        Bundle extras = getIntent().getExtras();
        TextView Name_of_institution = (TextView) findViewById(R.id.Name_of_institution);
        TextView motto_ = (TextView) findViewById(R.id.motto_);
        editText = (EditText) findViewById(R.id.editText);
        ImageView view_all = (ImageView) findViewById(R.id.view_all);
        ImageView Avatra = (ImageView) findViewById(R.id.Avatra);
        ImageView close_edit = (ImageView) findViewById(R.id.close_edit);
        ImageView closeThis = (ImageView) findViewById(R.id.closeThis);
        closeThis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
                Holder_box.setVisibility(View.GONE);
            }
        });
        close_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editText.setText("");
                editText.setTag("");

            }
        });
        view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchRes_All(editText);
            }
        });
        Message = (EditText) findViewById(R.id.Message);
        Holder_box = (LinearLayout) findViewById(R.id.Holder_box);
        ImageView institution_avatar = (ImageView) findViewById(R.id.institution_avatar);
        String choiceOne = extras.getString("choiceOne");
        //-----GET_ALL_NEZA_COMMUNITY_SUGGESTION_BOXES_
        if (choiceOne.equalsIgnoreCase("none")) {
            db = new NDS_db_adapter(Suggestion_box_suggest.this);
            db.openToWrite();
            Log.w("choiceOne", choiceOne);
            //--------------------------
            Holder_box.setVisibility(View.GONE);
            editText.setTag("0");
            //-------------
        } else {
            Log.w("choiceOne", choiceOne);

            db = new NDS_db_adapter(Suggestion_box_suggest.this);
            db.openToWrite();
            //-------------------------------
            Cursor suggestionBox = db.GET_ALL_NEZA_COMMUNITY_SUGGESTION_BOXES_INSTITUTE(choiceOne);
            suggestionBox.moveToFirst();
            String motto = suggestionBox.getString(4);
            String Avatar_ = suggestionBox.getString(2);
            String id_remote = suggestionBox.getString(0);
            //-----------
            Name_of_institution.setText(choiceOne);
            Name_of_institution.setTag(id_remote);
            editText.setText(choiceOne);
            editText.setTag(id_remote);
            motto_.setText(motto);
            //My_Community_Posts_PicassoClient_box.downloadImage(Suggestion_box_suggest.this, Config.LOAD_MY_SUGGESTION_BOXES + Avatar_, institution_avatar);
            new My_suggesrion_boxes_find_avatars(Suggestion_box_suggest.this, Config.LOAD_MY_SUGGESTION_BOXES_RENDER, choiceOne, institution_avatar).execute();

            //-----------------Sending suggestion to server--


        }


        Cursor UserCreds = db.queueAllFromCreds();

        if (UserCreds.moveToLast()) {
            MYRemoteId = UserCreds.getString(9);
            MYAvatar = UserCreds.getString(3);
            MYNames = UserCreds.getString(1);
            ImageLoader imageLoader = new ImageLoader(getApplicationContext());
            if (MYAvatar != null) {
                // imageLoader.DisplayImage(Config.LOAD_MY_AVATAR.toString() + MYAvatar, img);
                // Picasso.with(getBaseContext()).load(Config.LOAD_MY_AVATAR.toString() + MYAvatar).placeholder(R.mipmap.neza_default_avatar).into(img);
                My_Community_Posts_PicassoClient.downloadImage(Suggestion_box_suggest.this, Config.LOAD_MY_AVATAR.toString() + MYAvatar, Avatra);

                // imageLoader.DisplayImage(Config.LOAD_MY_AVATAR.toString() + MYAvatar, Avatra);
            } else {
                // imageLoader.DisplayImage(Config.LOAD_MY_AVATAR.toString() + "avatar_default.png", img);
                //Picasso.with(getBaseContext()).load(Config.LOAD_MY_AVATAR.toString() + MYAvatar).placeholder(R.mipmap.neza_default_avatar).into(img);
                My_Community_Posts_PicassoClient.downloadImage(Suggestion_box_suggest.this, Config.LOAD_MY_AVATAR.toString() + MYAvatar, Avatra);
                //imageLoader.DisplayImage(Config.LOAD_MY_AVATAR.toString() + MYAvatar, Avatra);
            }
        } else {


        }
        SendTxtSuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRemoteId_000 = MYRemoteId;
                //Toast.makeText(getBaseContext(), "" + (!editText.getTag().toString().isEmpty() ? editText.getTag().toString() : "0"), Toast.LENGTH_SHORT).show();
                if (mediaType_000.equalsIgnoreCase("text")) {
                    if (!editText.getText().toString().isEmpty()) {
                        if (!Message.getText().toString().isEmpty()) {
                            new My_Community_Suggestion_text_Send(Suggestion_box_suggest.this, Config.NDS_SEND_TEXT_SUGGESTION, myRemoteId_000, (!editText.getText().toString().isEmpty() ? editText.getText().toString() : "0"), Message.getText().toString(), Suggestion_box_suggest.this).execute();
                        } else {
                            Toast.makeText(getBaseContext(), "No text!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Receipient Please!", Toast.LENGTH_SHORT).show();
                    }
                } else if (mediaType_000.equalsIgnoreCase("audio")) {
                    new My_Community_Upload_audio(Suggestion_box_suggest.this, path_000, filename_000, myRemoteId_000, editText.getText().toString(), edixt_label_audio.getText().toString(), Suggestion_box_suggest.this, null).execute();


                } else if (mediaType_000.equalsIgnoreCase("video")) {
                    new My_Community_Upload_video(Suggestion_box_suggest.this, path_000, filename_000, myRemoteId_000, editText.getText().toString(), edixt_label_video.getText().toString(), Suggestion_box_suggest.this, null).execute();


                } else if (mediaType_000.equalsIgnoreCase("image")) {
                    new My_Community_Upload_image(Suggestion_box_suggest.this, path_000, filename_000, myRemoteId_000, editText.getText().toString(), edixt_label.getText().toString(), Suggestion_box_suggest.this, null).execute();


                } else {

                }
            }
        });
        //-------------
        TextWatcher fieldValidatorTextWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (filterLongEnough()) {
                    if (!specialCase) {
                        specialCase = false;
                        try {
                            showSearchRes(editText);
                        } catch (RuntimeException uhsdh) {

                        }
                    }
                } else {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                        editText.findFocus();
                    }
                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            private boolean filterLongEnough() {
                return editText.getText().toString().trim().length() > 3;
            }
        };
        editText.addTextChangedListener(fieldValidatorTextWatcher);
        keyword = editText.getText().toString();
        //------------
        image_tag = (RelativeLayout) findViewById(R.id.image_tag);
        video_tag = (RelativeLayout) findViewById(R.id.video_tag);
        audio_tag = (RelativeLayout) findViewById(R.id.audio_tag);
        textSuggestion = (LinearLayout) findViewById(R.id.textSuggestion);
        //----------------------------------------
        image_tag.setVisibility(View.GONE);
        video_tag.setVisibility(View.GONE);
        audio_tag.setVisibility(View.GONE);
        textSuggestion.setVisibility(View.VISIBLE);


    }

    public void ChooseThisOne(View v) {
        //-------------------------
        specialCase = true;
        db.openToWrite();
        Cursor MyLocalSuggestionBoxes_00 = db.GET_ALL_NEZA_COMMUNITY_SUGGESTION_BOXES_INSTITUTE_ID(v.getTag().toString());
        MyLocalSuggestionBoxes_00.moveToFirst();
        String remoteId = MyLocalSuggestionBoxes_00.getString(0);
        String avatars = MyLocalSuggestionBoxes_00.getString(2);
        String institution_names = MyLocalSuggestionBoxes_00.getString(3);
        String institution_motto = MyLocalSuggestionBoxes_00.getString(4);
        editText.setText(institution_names);
        editText.setTag(remoteId);
        Holder_box.setVisibility(View.VISIBLE);
        Message = (EditText) findViewById(R.id.Message);
        //Holder_box = (LinearLayout) findViewById(R.id.Holder_box);
        ImageView institution_avatar = (ImageView) findViewById(R.id.institution_avatar);
        String motto = MyLocalSuggestionBoxes_00.getString(4);
        String Avatar_ = MyLocalSuggestionBoxes_00.getString(2);
        String id_remote = MyLocalSuggestionBoxes_00.getString(0);
        //-----------
        TextView Name_of_institution = (TextView) findViewById(R.id.Name_of_institution);
        TextView motto_ = (TextView) findViewById(R.id.motto_);
        Name_of_institution.setText(institution_names);
        Name_of_institution.setTag(id_remote);
        motto_.setText(motto);
        //My_Community_Posts_PicassoClient_box.downloadImage(Suggestion_box_suggest.this, Config.LOAD_MY_SUGGESTION_BOXES + Avatar_, institution_avatar);
        new My_suggesrion_boxes_find_avatars(Suggestion_box_suggest.this, Config.LOAD_MY_SUGGESTION_BOXES_RENDER, institution_names, institution_avatar).execute();

        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }


    }

    public void showSearchRes_All(final View v) {
        LayoutInflater layoutInflater = (LayoutInflater) Suggestion_box_suggest.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.suggestion_box_search_reslt_anchor, null);
        popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        if (popupWindow != null) {
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {

                    specialCase = false;
                }
            });
            // popupWindow.setOutsideTouchable(true);
            if (editText != null) {
                db.openToWrite();
                Cursor MyLocalSuggestionBoxes_00 = db.GET_ALL_NEZA_COMMUNITY_SUGGESTION_BOXES();
                MyLocalSuggestionBoxes_00.moveToFirst();
                remoteId = new String[MyLocalSuggestionBoxes_00.getCount()];
                avatars = new String[MyLocalSuggestionBoxes_00.getCount()];
                institution_names = new String[MyLocalSuggestionBoxes_00.getCount()];
                institution_motto = new String[MyLocalSuggestionBoxes_00.getCount()];
                for (int i = 0; i < MyLocalSuggestionBoxes_00.getCount(); i++) {
                    remoteId[i] = MyLocalSuggestionBoxes_00.getString(0);
                    avatars[i] = MyLocalSuggestionBoxes_00.getString(2);
                    institution_names[i] = MyLocalSuggestionBoxes_00.getString(3);
                    institution_motto[i] = MyLocalSuggestionBoxes_00.getString(4);
                    MyLocalSuggestionBoxes_00.moveToNext();
                }
            }//--populate
            //----
            if (avatars != null) {
                if (avatars.length > 0) {
                    popupWindow.showAsDropDown(v);
                    //popupWindow.showAtLocation(v, Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
                    popupWindow.setAnimationStyle(android.R.style.TextAppearance_DeviceDefault_Small_Inverse);
                    popupWindow.setFocusable(true);
                    popupWindow.update();
                    //-----------
                    search_res_list = (ListView) popupView.findViewById(R.id.search_res_list);
                    search_res_list.setAdapter(new LazyAdapter_suggest_search((Activity) Suggestion_box_suggest.this, avatars, institution_names, institution_names, institution_motto, remoteId));
                }
            }

        }
    }

    public void showSearchRes(final View v) {


        LayoutInflater layoutInflater = (LayoutInflater) Suggestion_box_suggest.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.suggestion_box_search_reslt_anchor, null);
        popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        if (popupWindow != null) {
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    specialCase = false;
                }
            });
            // popupWindow.setOutsideTouchable(true);
            if (editText != null) {
                db.openToWrite();
                Cursor MyLocalSuggestionBoxes_00 = db.GET_ALL_NEZA_COMMUNITY_SUGGESTION_BOXES_INSTITUTE_LIKE(editText.getText().toString());
                MyLocalSuggestionBoxes_00.moveToFirst();
                remoteId = new String[MyLocalSuggestionBoxes_00.getCount()];
                avatars = new String[MyLocalSuggestionBoxes_00.getCount()];
                institution_names = new String[MyLocalSuggestionBoxes_00.getCount()];
                institution_motto = new String[MyLocalSuggestionBoxes_00.getCount()];
                for (int i = 0; i < MyLocalSuggestionBoxes_00.getCount(); i++) {
                    remoteId[i] = MyLocalSuggestionBoxes_00.getString(0);
                    avatars[i] = MyLocalSuggestionBoxes_00.getString(2);
                    institution_names[i] = MyLocalSuggestionBoxes_00.getString(3);
                    institution_motto[i] = MyLocalSuggestionBoxes_00.getString(4);
                    MyLocalSuggestionBoxes_00.moveToNext();
                }
            }//--populate
            //----
            if (avatars != null) {
                if (avatars.length > 0) {
                    popupWindow.showAsDropDown(v);
                    //popupWindow.showAtLocation(v, Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
                    popupWindow.setAnimationStyle(android.R.style.TextAppearance_DeviceDefault_Small_Inverse);
                    popupWindow.setFocusable(true);
                    popupWindow.update();
                    //-----------
                    search_res_list = (ListView) popupView.findViewById(R.id.search_res_list);
                    search_res_list.setAdapter(new LazyAdapter_suggest_search((Activity) Suggestion_box_suggest.this, avatars, institution_names, institution_names, institution_motto, remoteId));
                }
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //--------------camera okay--
        if (requestCode == 900 && imageTaken == 1) {
            imageTaken = 0;
            //----Save Rotated-photoUri
            Log.w("xcvbn", "in ma nigga" + mCurrentPhotoPath);

            galleryAddPic();
            try {
                //--------------find the last picture taken from the phone--
                String CameraFolder = "Camera";
                File CameraDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/" + CameraFolder + "/");
                File[] files = CameraDirectory.listFiles();
                Arrays.sort(files, Collections.reverseOrder());
                File done_ = null;
                for (File CurlFile : files) {
                    if (!CurlFile.isDirectory()) {

                        Log.w("3553", "folder--" + CurlFile.getName());
                        done_ = CurlFile;
                        break;


                    }

                }
                if (done_ != null) {
                    Uri joom = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", done_);
                    saveImageToLocalStore(rotateBitmap(done_.getPath()));
                    //saveImageToLocalStore(rotateBitmap(getImagePath(photoUri_)));
                }


                ///|||||||||||||| cyuma
            } catch (RuntimeException saha) {
                Toast.makeText(Suggestion_box_suggest.this, "Saving failed", Toast.LENGTH_SHORT).show();
                Log.w("xcvbn", "" + saha.toString());
            }

        } else if (data != null) {
            Uri formCameraSagamba = data.getData();
            //------Rotate to make upright--
            if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {

                if (resultCode == RESULT_OK && requestCode != SELECT_AUDIO) {
                    ///|||||||||||||| cyuma


                    deleteLatestPicture();


                } else if (resultCode == RESULT_CANCELED && requestCode != SELECT_AUDIO) {


                    // User cancelled the video capture
                    Toast.makeText(this, "User cancelled the video capture.",
                            Toast.LENGTH_LONG).show();

                } else if (requestCode != SELECT_AUDIO) {

                    // Video capture failed, advise user
                    Toast.makeText(this, "Video capture failed.",
                            Toast.LENGTH_LONG).show();
                } else if (requestCode == SELECT_AUDIO) {


                    //----------------

                }
            } else {
                //-------------------cropResulted------
                if (data == null) {


                } else if (requestCode != REQUEST_CROP && requestCode != SELECT_AUDIO && requestCode != REQUEST_TAKE_GALLERY_VIDEO && requestCode != PICK_IMAGE_REQUEST) {
                    Bitmap bp = (Bitmap) data.getExtras().get("data");
                    //----Save Rotated-
                    Log.w("xcvbn", "in ma nigga 290");


                    try {
                        Uri contentUri = Uri.fromFile(new File(mCurrentPhotoPath));

                        saveImageToLocalStore(rotateBitmap(getImagePath(contentUri)));


                        ///|||||||||||||| cyuma
                    } catch (RuntimeException saha) {
                        Toast.makeText(Suggestion_box_suggest.this, "Saving failed", Toast.LENGTH_SHORT).show();
                        Log.w("xcvbn", "" + saha.toString());
                    }


                } else if ((requestCode != REQUEST_TAKE_GALLERY_VIDEO && requestCode != PICK_IMAGE_REQUEST)) {

                    Uri selectedAudioUri = data.getData();
                    //-------------------Save the Audio to locale
                    String path = selectedAudioUri.getPath();


                    try {
                        // selectedPath = new File(getPath(selectedAudioUri)).getPath();
                    } catch (RuntimeException ki) {
                        // selectedPath = RealPathUtils.getRealPathFromURI_API_19(this, selectedAudioUri);
                    }


                    //selectedPath =new File(getPath(selectedAudioUri)).getPath();
                    //selectedPath = getRealPathFromURI(selectedAudioUri);
                    // Log.w("SELECT_AUDIO Path : ", selectedPath);
                    //  Toast.makeText(getBaseContext(), "SELECT_AUDIO Path : " + selectedPath, Toast.LENGTH_SHORT).show();
                    //------------Save to Boo Audios and update the audio database--
                    try {
                        InputStream inputStreamImage = getBaseContext().getContentResolver().openInputStream(data.getData());
                        //Log.w("image-00",""+inputStream);
                        //-----------------
                        File SDCardRoot = Environment.getExternalStorageDirectory();
                        //create a new file, to save the downloaded file
                        File directory = new File(SDCardRoot, "/NDS audios/");

                        if (!directory.exists()) {
                            directory.mkdir();
                        }
                        Calendar cal = Calendar.getInstance();
                        //Log.w("00008", "" + selectedPath);

                        //---
                        //String filename = "NDS_audio_" + cal.getTimeInMillis() + "." + selectedPath.substring(selectedPath.lastIndexOf("."));
                        String filename = "NDS_audio_" + cal.getTimeInMillis() + "." + "mp3";

                        File file = new File(directory, filename);

                        FileOutputStream fileOutput = new FileOutputStream(file);
                        //-----------------
                        final String path_0 = Environment.getExternalStorageDirectory() + File.separator
                                + "/NDS audios/" + filename;
                        String filename_to_up = path_0.substring(path_0.lastIndexOf("/") + 1);

                        //Stream used for reading the data from the internet
                        InputStream inputStream = inputStreamImage;

                        byte[] buffer = new byte[20 * 1024];
                        int bufferLength = 0;

                        while ((bufferLength = inputStream.read(buffer)) > 0) {
                            fileOutput.write(buffer, 0, bufferLength);
                            downloadedSize = bufferLength;

                        }
                        //close the output stream when complete //
                        fileOutput.close();
                        //------------
                        try {
                            //addCaptionToSuggestionMedia(path_0, filename_to_up, "audio", MYRemoteId, (editText.getTag().toString().isEmpty()) ? "0" : editText.getTag().toString());
                            path_000 = path_0;
                            filename_000 = filename_to_up;
                            myRemoteId_000 = MYRemoteId;
                            SuggestionBoxId_000 = (editText.getTag().toString().isEmpty()) ? "0" : editText.getTag().toString();
                            //---00===================================================================================
                            mediaType_000 = "audio";
                            image_tag.setVisibility(View.GONE);
                            video_tag.setVisibility(View.GONE);
                            audio_tag.setVisibility(View.VISIBLE);
                            textSuggestion.setVisibility(View.GONE);
                            //-----------------------------------
                            audio_name.setText(filename_to_up);
                            DateFormat df = new SimpleDateFormat("HH:mm");
                            String date = df.format(Calendar.getInstance().getTime());
                            time_stamp.setText("");

                            edixt_audio.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mediaPlayer = new MediaPlayer();
                                    if (mediaPlayer != null) {
                                        if (!play_yes) {
                                            play_yes = true;

                                            try {
                                                mediaPlayer.setDataSource(path_0);
                                                mediaPlayer.prepare();
                                                mediaPlayer.start();

                                                finalTime = mediaPlayer.getDuration();
                                                startTime = mediaPlayer.getCurrentPosition();
                                                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                    public void onCompletion(MediaPlayer mp) {
                                                        mp.release();

                                                    }

                                                    ;
                                                });
                                                //---
                                                seekBar.setProgress((int) startTime);
                                                myHandler_IU.postDelayed(UpdateSongTime, 100);

                                                if (oneTimeOnly == 0) {
                                                    seekBar.setMax((int) finalTime);
                                                    oneTimeOnly = 1;
                                                }
                                            } catch (IllegalArgumentException e) {
                                                e.printStackTrace();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        } else {

                                            try {
                                                play_yes = false;
                                                if (mediaPlayer.isPlaying()) {
                                                    mediaPlayer.stop();
                                                    mediaPlayer.reset();
                                                    mediaPlayer.release();
                                                }
                                                myHandler_IU.removeCallbacks(UpdateSongTime);
                                            } catch (RuntimeException ddfuh) {
                                                ddfuh.printStackTrace();
                                            }
                                        }
                                    }


                                }
                            });


//--------------999===fdjidfdfiodjfodfd fdkfndifdf dfidhf

                        } catch (RuntimeException sdd) {
                            Toast.makeText(Suggestion_box_suggest.this, "Audio file too large", Toast.LENGTH_SHORT).show();
                            Log.w("erty", sdd.toString());
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } else if (requestCode == REQUEST_TAKE_GALLERY_VIDEO && requestCode != PICK_IMAGE_REQUEST) {
                    try {
                        InputStream inputStreamVideo = getBaseContext().getContentResolver().openInputStream(data.getData());
                        //Log.w("image-00",""+inputStream);
                        //-----------------
                        File SDCardRoot = Environment.getExternalStorageDirectory();
                        //create a new file, to save the downloaded file
                        File directory = new File(SDCardRoot, "/NDS videos/");

                        if (!directory.exists()) {
                            directory.mkdir();
                        }
                        Calendar cal = Calendar.getInstance();
                        String filename = "NDS_video_" + cal.getTimeInMillis() + ".mp4";

                        File file = new File(directory, filename);

                        FileOutputStream fileOutput = new FileOutputStream(file);

                        //Stream used for reading the data from the internet
                        InputStream inputStream = inputStreamVideo;
                        final String path_0 = Environment.getExternalStorageDirectory() + File.separator
                                + "NDS videos/" + filename;
                        String filename_to_up = path_0.substring(path_0.lastIndexOf("/") + 1);

                        byte[] buffer = new byte[20 * 1024];
                        int bufferLength = 0;

                        while ((bufferLength = inputStream.read(buffer)) > 0) {
                            fileOutput.write(buffer, 0, bufferLength);
                            downloadedSize = bufferLength;
                            // update the progressbar //

                        }
                        //close the output stream when complete //
                        fileOutput.close();
                        //--------------
                        //addCaptionToSuggestionMedia(path_0, filename, "video", MYRemoteId, (editText.getTag().toString().isEmpty()) ? "0" : editText.getTag().toString());
                        //addCaptionToSuggestionMedia(path_0, filename_to_up, "audio", MYRemoteId, (editText.getTag().toString().isEmpty()) ? "0" : editText.getTag().toString());
                        path_000 = path_0;
                        filename_000 = filename_to_up;
                        myRemoteId_000 = MYRemoteId;
                        SuggestionBoxId_000 = (editText.getTag().toString().isEmpty()) ? "0" : editText.getTag().toString();
//-------0--------------------------the video send--
                        mediaType_000 = "video";
                        image_tag.setVisibility(View.GONE);
                        video_tag.setVisibility(View.VISIBLE);
                        audio_tag.setVisibility(View.GONE);
                        textSuggestion.setVisibility(View.GONE);
                        video_tag.setTag(filename);

                        String videoFile = path_0;
                        Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(videoFile,
                                MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);
//BitmapDrawable bitmapDrawable = new BitmapDrawable(thumbnail);
                        //edixt_image_video.setImageBitmap(thumbnail);

                        edixt_image_video.setVideoURI(Uri.parse(path_0));

                        edixt_image_video.seekTo(100);
///-------------
                        edixt_image_video.setTag(path_0);
//----

                        edixt_image_video.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                                    handler.postDelayed(mLongPressed, 1000);
                                }
                                if ((event.getAction() == MotionEvent.ACTION_MOVE) || (event.getAction() == MotionEvent.ACTION_UP)) {
                                    time_down = 0;
                                    handler.removeCallbacks(mLongPressed);

                                }

                                return false;
                            }
                        });

//------------0000000000000000000000000000000000000000000-dufhhhhhhhhhhhhhhhhhhhhh


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } else if (requestCode == PICK_IMAGE_REQUEST) {
                    try {
                        InputStream inputStreamImage = getBaseContext().getContentResolver().openInputStream(data.getData());
                        //Log.w("image-00",""+inputStream);
                        //-----------------
                        File SDCardRoot = Environment.getExternalStorageDirectory();
                        //create a new file, to save the downloaded file
                        File directory = new File(SDCardRoot, "/NDS images/");

                        if (!directory.exists()) {
                            directory.mkdir();
                        }
                        Calendar cal = Calendar.getInstance();
                        String filename = "NDS_img_" + cal.getTimeInMillis() + ".png";

                        File file = new File(directory, filename);

                        FileOutputStream fileOutput = new FileOutputStream(file);

                        //Stream used for reading the data from the internet
                        InputStream inputStream = inputStreamImage;

                        byte[] buffer = new byte[20 * 1024];
                        int bufferLength = 0;

                        while ((bufferLength = inputStream.read(buffer)) > 0) {
                            fileOutput.write(buffer, 0, bufferLength);
                            downloadedSize = bufferLength;

                        }
                        //close the output stream when complete //
                        fileOutput.close();
                        //------------
                        String path_0 = Environment.getExternalStorageDirectory() + File.separator
                                + "/NDS images/" + filename;
                        String filename_to_up = path_0.substring(path_0.lastIndexOf("/") + 1);
                        try {
                            if (!editText.getText().toString().isEmpty()) {
                                //addCaptionToSuggestionMedia(path_0, filename_to_up, "image", MYRemoteId, (editText.getTag().toString().isEmpty()) ? "0" : editText.getTag().toString());
                                //addCaptionToSuggestionMedia(path_0, filename_to_up, "audio", MYRemoteId, (editText.getTag().toString().isEmpty()) ? "0" : editText.getTag().toString());
                                path_000 = path_0;
                                filename_000 = filename_to_up;
                                myRemoteId_000 = MYRemoteId;
                                SuggestionBoxId_000 = (editText.getTag().toString().isEmpty()) ? "0" : editText.getTag().toString();
                                //-000000000000000000000000-----Image to send-----------------------------------------
                                mediaType_000 = "image";
                                image_tag.setTag(filename_to_up);
                                image_tag.setVisibility(View.VISIBLE);
                                video_tag.setVisibility(View.GONE);
                                audio_tag.setVisibility(View.GONE);
                                textSuggestion.setVisibility(View.GONE);

                                File imgFile = new File(path_0);
                                if (imgFile.exists()) {
                                    try {
                                        final BitmapFactory.Options options = new BitmapFactory.Options();
                                        options.inSampleSize = 2;
                                        Bitmap bm = BitmapFactory.decodeFile(path_0, options);
                                        //Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                                        // Log.w("myPath", path);
                                        //--Adjust imagte before final display haha!---------


                                        //---
                                        Bitmap srcBmp = bm;
                                        Bitmap dstBmp = null;
                                        //---Cropping for small display--
                                        try {
                                            if (srcBmp.getWidth() >= srcBmp.getHeight()) {
                                                try {
                                                    dstBmp = Bitmap.createBitmap(srcBmp, srcBmp.getWidth() - srcBmp.getHeight(), 0, srcBmp.getHeight(), srcBmp.getHeight());
                                                    //Log.w("cropped yes","good");
                                                } catch (RuntimeException shhh) {

                                                }
                                            } else {
                                                try {
                                                    dstBmp = Bitmap.createBitmap(srcBmp, 0, srcBmp.getHeight() - srcBmp.getWidth(), srcBmp.getWidth(), srcBmp.getWidth());
                                                    //Log.w("cropped yes","good");
                                                } catch (RuntimeException shhh) {

                                                }
                                            }


                                            //-----------------
                                            edixt_image.setImageBitmap(dstBmp);
                                            edixt_image.setTag(path_0);
                                        } catch (RuntimeException shhh) {

                                        }


                                    } catch (Exception vb) {

                                    }

                                    //-------------


                                    //----------------
                                }

                                //---------0000000000000000000000000000000000000000000000000000000000000000000000000000000000
                            } else {
                                Toast.makeText(Suggestion_box_suggest.this, "Choose or type in receipient", Toast.LENGTH_SHORT).show();
                            }
                        } catch (RuntimeException e) {
                            Log.w("okayISee", "" + e.toString());
                            Toast.makeText(Suggestion_box_suggest.this, "Image not saved! Not enough space!", Toast.LENGTH_SHORT).show();

                        }


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }


                } else {
                    // Log.w("image-00", "something went wrong" + requestCode + formCameraSagamba);
                }
            }
        }
    }


    private void saveImageToLocalStore(Bitmap finalBitmap) {
        //ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        //File directory = cw.getDir("Boo_images", Context.MODE_PRIVATE);
        Calendar cal = Calendar.getInstance();
        //--------------
        File direct = new File(Environment.getExternalStorageDirectory() + "/NDS images");

        if (!direct.exists()) {
            direct = new File("/NDS images");
            direct.mkdirs();
        }
        final String filename = "NDS_img_" + cal.getTimeInMillis() + ".png";

        File file = new File(Environment.getExternalStorageDirectory() + File.separator
                + "/NDS images/", filename);
        if (file.exists()) {
            file.delete();

        }
        //--

        // File file = new File(directory+"/Boo_images/", filename);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            //---
            String path_0 = Environment.getExternalStorageDirectory() + File.separator
                    + "/NDS images/" + filename;
            String filename_to_up = path_0.substring(path_0.lastIndexOf("/") + 1);
            if (!editText.getText().toString().isEmpty()) {
                //addCaptionToSuggestionMedia(path_0, filename_to_up, "image", MYRemoteId, (editText.getTag().toString().isEmpty()) ? "0" : editText.getTag().toString());
                //addCaptionToSuggestionMedia(path_0, filename_to_up, "audio", MYRemoteId, (editText.getTag().toString().isEmpty()) ? "0" : editText.getTag().toString());
                path_000 = path_0;
                filename_000 = filename_to_up;
                myRemoteId_000 = MYRemoteId;
                SuggestionBoxId_000 = (editText.getTag().toString().isEmpty()) ? "0" : editText.getTag().toString();
                //-000000000000000000000000-----Image to send-----------------------------------------
                mediaType_000 = "image";
                image_tag.setTag(filename_to_up);
                image_tag.setVisibility(View.VISIBLE);
                video_tag.setVisibility(View.GONE);
                audio_tag.setVisibility(View.GONE);
                textSuggestion.setVisibility(View.GONE);
                File imgFile = new File(path_0);
                if (imgFile.exists()) {
                    try {
                        final BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 2;
                        Bitmap bm = BitmapFactory.decodeFile(path_0, options);
                        //Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        // Log.w("myPath", path);
                        //--Adjust imagte before final display haha!---------


                        //---
                        Bitmap srcBmp = bm;
                        Bitmap dstBmp = null;
                        //---Cropping for small display--
                        try {
                            if (srcBmp.getWidth() >= srcBmp.getHeight()) {
                                try {
                                    dstBmp = Bitmap.createBitmap(srcBmp, srcBmp.getWidth() - srcBmp.getHeight(), 0, srcBmp.getHeight(), srcBmp.getHeight());
                                    //Log.w("cropped yes","good");
                                } catch (RuntimeException shhh) {

                                }
                            } else {
                                try {
                                    dstBmp = Bitmap.createBitmap(srcBmp, 0, srcBmp.getHeight() - srcBmp.getWidth(), srcBmp.getWidth(), srcBmp.getWidth());
                                    //Log.w("cropped yes","good");
                                } catch (RuntimeException shhh) {

                                }
                            }


                            //-----------------
                            Drawable d = new BitmapDrawable(getResources(), dstBmp);
                            edixt_image.setImageDrawable(d);

                            // edixt_image.setImageBitmap(dstBmp);
                            edixt_image.setTag(path_0);
                        } catch (RuntimeException shhh) {

                        }


                    } catch (Exception vb) {

                    }

                    //-------------


                    //----------------
                }

                //---------0000000000000000000000000000000000000000000000000000000000000000000000000000000000
            } else {
                Toast.makeText(Suggestion_box_suggest.this, "Choose or type in receipient", Toast.LENGTH_SHORT).show();
            }
            galleryAddPic();
            deleteLatestPicture();
            //-------------------------------------------
        } catch (Exception e) {
            e.printStackTrace();


            Log.w("DirImage", "failed!" + e.toString());
            Toast.makeText(Suggestion_box_suggest.this, "Image not saved! Not enough space!" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    //-------------------------
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first) {
                first = false;
                result.append("&");
            } else {
                result.append("&");
            }

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    private void deleteLatestPicture() {

        File f = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");

        File[] files = f.listFiles();

        Arrays.sort(files, new Comparator<Object>() {
            public int compare(Object o1, Object o2) {

                if (((File) o1).lastModified() > ((File) o2).lastModified()) {
                    return -1;
                } else if (((File) o1).lastModified() < ((File) o2).lastModified()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        try {
            files[0].delete();
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(files[0])));
        } catch (RuntimeException djj) {

        }
    }

    private Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private File getOutputMediaFile(int type) {

        // Check that the SDCard is mounted
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "NDS videos");


        // Create the storage directory(MyCameraVideo) if it does not exist
        if (!mediaStorageDir.exists()) {

            if (!mediaStorageDir.mkdirs()) {

                //output.setText("Failed to create directory MyCameraVideo.");

                Toast.makeText(Suggestion_box_suggest.this, "Failed to create directory Boo_videos.",
                        Toast.LENGTH_LONG).show();

                Log.d("MyCameraVideo", "Failed to create directory Boo_videos.");
                return null;
            }
        }

// For unique file name appending current timeStamp with file name
        java.util.Date date = new java.util.Date();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(date.getTime());

        File mediaFile;

        if (type == MEDIA_TYPE_VIDEO) {

            // For unique video file name appending current timeStamp with file name
            String filename_video = "NDS_video_" + timeStamp + ".mp4";
            videoFileName = filename_video;
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    filename_video);
            final String path_0 = Environment.getExternalStorageDirectory() + File.separator
                    + "NDS videos/" + filename_video;
            String filename_to_up = path_0.substring(path_0.lastIndexOf("/") + 1);
            //addCaptionToSuggestionMedia(path_0, filename_to_up, "video", MYRemoteId, (editText.getTag().toString().isEmpty()) ? "0" : editText.getTag().toString());
            //addCaptionToSuggestionMedia(path_0, filename_to_up, "audio", MYRemoteId, (editText.getTag().toString().isEmpty()) ? "0" : editText.getTag().toString());
            path_000 = path_0;
            filename_000 = filename_to_up;
            myRemoteId_000 = MYRemoteId;
            SuggestionBoxId_000 = (editText.getTag().toString().isEmpty()) ? "0" : editText.getTag().toString();
//-------0--------------------------the video send--
            mediaType_000 = "video";
            image_tag.setVisibility(View.GONE);
            video_tag.setVisibility(View.VISIBLE);
            audio_tag.setVisibility(View.GONE);
            textSuggestion.setVisibility(View.GONE);
            video_tag.setTag(filename_to_up);
            String videoFile = path_0;
            Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(videoFile,
                    MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);
//BitmapDrawable bitmapDrawable = new BitmapDrawable(thumbnail);
            // edixt_image_video.setImageBitmap(thumbnail);
            edixt_image_video.setVideoURI(Uri.parse(path_0));

            edixt_image_video.seekTo(100);
///-------------
            edixt_image_video.setTag(path_0);
///-------------
            edixt_image_video.setTag(path_0);
//----
            edixt_image_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            edixt_image_video.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(path_0));
                    intent.setDataAndType(Uri.parse(path_000), "video/mp4");
                    startActivity(intent);

                    return false;
                }
            });

//------------0000000000000000000000000000000000000000000-dufhhhhhhhhhhhhhhhhhhhhh

            //-------------------

        } else {
            return null;
        }

        return mediaFile;
    }

    //-------------------------opening extra stuff to handle
    public void sendTextSuggestion(View v) {
        // Toast.makeText(Suggestion_box_suggest.this, editText.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    public void ChooseImageFromDevice(View v) {
        if (mySize() > 10) {
            if (!editText.getText().toString().isEmpty()) {
                // Toast.makeText(Suggestion_box_suggest.this, editText.getText().toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                // intent.setType("image/*");
                //intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            } else {
                Toast.makeText(Suggestion_box_suggest.this, "Type receipient of Suggestion please!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getBaseContext(), "Not enough space on phone!", Toast.LENGTH_SHORT).show();
        }

    }

    public void ChooseAudioFromDevice(View v) {
        if (mySize() > 10) {
            if (!editText.getText().toString().isEmpty()) {
                if (!editText.getText().toString().isEmpty()) {
                    //Toast.makeText(Suggestion_box_suggest.this, editText.getText().toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = null;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                        intent = new Intent(Intent.ACTION_GET_CONTENT);
                    } else {
                        intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    }

                    intent.setType("audio/*");
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    String[] mimetypes = {"audio/3gp", "audio/AMR", "audio/mp3"};

                    //intent.setAction(Intent.ACTION_GET_CONTENT);
                    //startActivityForResult(intent, SELECT_AUDIO);
                    startActivityForResult(Intent.createChooser(intent, "Choose Audio"), SELECT_AUDIO);
                } else {
                    Toast.makeText(Suggestion_box_suggest.this, "Type receipient of Suggestion please!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Suggestion_box_suggest.this, "Type receipient of Suggestion please!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getBaseContext(), "Not enough space on phone!", Toast.LENGTH_SHORT).show();
        }

    }

    public void ChooseVideoFromDevice(View v) {
        if (mySize() > 10) {
            if (!editText.getText().toString().isEmpty()) {
                //Toast.makeText(Suggestion_box_suggest.this, editText.getText().toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setType("video/*");
                //fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Video"), REQUEST_TAKE_GALLERY_VIDEO);
            } else {
                Toast.makeText(Suggestion_box_suggest.this, "Type receipient of Suggestion please!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getBaseContext(), "Not enough space on phone!", Toast.LENGTH_SHORT).show();
        }


    }

    public void recordAudio(final String fileName) {
        if (mySize() > 10) {
            if (!editText.getText().toString().isEmpty()) {
                final MediaRecorder recorder = new MediaRecorder();
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                recorder.setOutputFile(fileName);
                recorder.setMaxDuration(14000);
                //-------

                final ProgressDialog mProgressDialog = new ProgressDialog(Suggestion_box_suggest.this);
                mProgressDialog.setTitle("Recording..");

                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setButton("Stop recording", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //mProgressDialog.dismiss();

                        m_handler.removeCallbacks(m_handlerTask);
                        counter_O = 0;

                        try {
                            recorder.stop();
                            recorder.reset();
                            recorder.release();
                            final String path_0 = fileName;
                            String filename_to_up = path_0.substring(path_0.lastIndexOf("/") + 1);
                            // addCaptionToSuggestionMedia(fileName, filename_to_up, "audio", MYRemoteId, (editText.getTag().toString().isEmpty()) ? "0" : editText.getTag().toString());
                            //addCaptionToSuggestionMedia(path_0, filename_to_up, "audio", MYRemoteId, (editText.getTag().toString().isEmpty()) ? "0" : editText.getTag().toString());
                            path_000 = path_0;
                            filename_000 = filename_to_up;
                            myRemoteId_000 = MYRemoteId;
                            SuggestionBoxId_000 = (editText.getTag().toString().isEmpty()) ? "0" : editText.getTag().toString();
//---00===================================================================================
                            mediaType_000 = "audio";
                            audio_tag.setTag(filename_to_up);
                            image_tag.setVisibility(View.GONE);
                            video_tag.setVisibility(View.GONE);
                            audio_tag.setVisibility(View.VISIBLE);
                            textSuggestion.setVisibility(View.GONE);
                            //---

                            audio_name.setText(filename_to_up);
                            DateFormat df = new SimpleDateFormat("HH:mm");
                            String date = df.format(Calendar.getInstance().getTime());
                            //----
                            // load data file
                            MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
                            metaRetriever.setDataSource(path_0);

                            String out = "";
                            // get mp3 info

                            // convert duration to minute:seconds
                            String duration =
                                    metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                            Log.v("time", duration);
                            long dur = Long.parseLong(duration);
                            String seconds = String.valueOf((dur % 60000) / 1000);

                            // close object
                            metaRetriever.release();

                            //--------
                            time_stamp.setText("" + seconds + " s");

                            edixt_audio.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mediaPlayer = new MediaPlayer();
                                    if (mediaPlayer != null) {
                                        if (!play_yes) {
                                            play_yes = true;

                                            try {
                                                mediaPlayer.setDataSource(path_0);
                                                mediaPlayer.prepare();
                                                mediaPlayer.start();

                                                finalTime = mediaPlayer.getDuration();
                                                startTime = mediaPlayer.getCurrentPosition();
                                                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                    public void onCompletion(MediaPlayer mp) {
                                                        mp.release();

                                                    }

                                                    ;
                                                });
                                                //---

                                                seekBar.setProgress((int) startTime);
                                                myHandler_IU.postDelayed(UpdateSongTime, 100);

                                                if (oneTimeOnly == 0) {
                                                    seekBar.setMax((int) finalTime);
                                                    oneTimeOnly = 1;
                                                }

                                            } catch (IllegalArgumentException e) {
                                                e.printStackTrace();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        } else {

                                            try {
                                                play_yes = false;

                                                if (mediaPlayer.isPlaying()) {

                                                    mediaPlayer.stop();
                                                    mediaPlayer.reset();
                                                    mediaPlayer.release();
                                                }
                                                myHandler_IU.removeCallbacks(UpdateSongTime);
                                            } catch (RuntimeException ddfuh) {
                                                ddfuh.printStackTrace();
                                            }
                                        }
                                    }


                                }
                            });


//--------------999===fdjidfdfiodjfodfd fdkfndifdf dfidhf

                        } catch (RuntimeException sdd) {
                            Toast.makeText(Suggestion_box_suggest.this, "Audio file too large", Toast.LENGTH_SHORT).show();
                            Log.w("erty", sdd.toString());
                        }
                    }
                });

                mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface p1) {


                    }
                });
                try {
                    recorder.prepare();
                    recorder.start();
                    mProgressDialog.show();

                    //----------------------------------------
                    m_handler = new Handler();
                    m_handlerTask = new Runnable() {
                        @Override
                        public void run() {
                            //-----------------
                            mProgressDialog.setTitle("Max time=15 seconds \nRecording duration " + counter_O);
                            if (counter_O == 15) {
                                if (recorder != null) {
                                    recorder.stop();
                                    recorder.reset();
                                    recorder.release();
                                }
                                final String path_0 = fileName;
                                String filename_to_up = path_0.substring(path_0.lastIndexOf("/") + 1);
                                //addCaptionToSuggestionMedia(fileName, filename_to_up, "audio", MYRemoteId, (editText.getTag().toString().isEmpty()) ? "0" : editText.getTag().toString());
                                //addCaptionToSuggestionMedia(path_0, filename_to_up, "audio", MYRemoteId, (editText.getTag().toString().isEmpty()) ? "0" : editText.getTag().toString());
                                path_000 = path_0;
                                filename_000 = filename_to_up;
                                myRemoteId_000 = MYRemoteId;
                                SuggestionBoxId_000 = (editText.getTag().toString().isEmpty()) ? "0" : editText.getTag().toString();
//---00===================================================================================
                                mediaType_000 = "audio";
                                audio_tag.setTag(filename_to_up);
                                image_tag.setVisibility(View.GONE);
                                video_tag.setVisibility(View.GONE);
                                audio_tag.setVisibility(View.VISIBLE);
                                textSuggestion.setVisibility(View.GONE);
                                audio_name.setText(filename_to_up);
                                DateFormat df = new SimpleDateFormat("HH:mm");
                                String date = df.format(Calendar.getInstance().getTime());
                                //----
                                // load data file
                                MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
                                metaRetriever.setDataSource(path_0);

                                String out = "";
                                // get mp3 info

                                // convert duration to minute:seconds
                                String duration =
                                        metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                                Log.v("time", duration);
                                long dur = Long.parseLong(duration);
                                String seconds = String.valueOf((dur % 60000) / 1000);

                                // close object
                                metaRetriever.release();

                                //--------
                                time_stamp.setText("" + seconds + " s");

                                edixt_audio.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mediaPlayer = new MediaPlayer();
                                        if (mediaPlayer != null) {
                                            if (!play_yes) {
                                                play_yes = true;

                                                try {
                                                    mediaPlayer.setDataSource(path_0);
                                                    mediaPlayer.prepare();
                                                    mediaPlayer.start();

                                                    finalTime = mediaPlayer.getDuration();
                                                    startTime = mediaPlayer.getCurrentPosition();
                                                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                        public void onCompletion(MediaPlayer mp) {
                                                            mp.release();

                                                        }

                                                        ;
                                                    });
                                                    //---
                                                    seekBar.setProgress((int) startTime);
                                                    myHandler_IU.postDelayed(UpdateSongTime, 100);

                                                    if (oneTimeOnly == 0) {
                                                        seekBar.setMax((int) finalTime);
                                                        oneTimeOnly = 1;
                                                    }
                                                } catch (IllegalArgumentException e) {
                                                    e.printStackTrace();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            } else {

                                                try {
                                                    play_yes = false;

                                                    if (mediaPlayer.isPlaying()) {

                                                        mediaPlayer.stop();
                                                        mediaPlayer.reset();
                                                        mediaPlayer.release();
                                                    }
                                                    myHandler_IU.removeCallbacks(UpdateSongTime);
                                                } catch (RuntimeException ddfuh) {
                                                    ddfuh.printStackTrace();
                                                }
                                            }
                                        }


                                    }
                                });


//--------------999===fdjidfdfiodjfodfd fdkfndifdf dfidhf
                                mProgressDialog.cancel();


                                m_handler.removeCallbacks(m_handlerTask);


                            } else {
                                counter_O++;
                                m_handler.postDelayed(m_handlerTask, 1000);
                            }
                        }
                    };
                    m_handlerTask.run();
                    //----------------
                    Log.w("MikePh", "" + fileName);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.w("MikePh", "" + e.toString());
                }
            } else {
                Toast.makeText(Suggestion_box_suggest.this, "Type receipient of Suggestion please!", Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(getBaseContext(), "Not enough space on phone!", Toast.LENGTH_SHORT).show();
        }


    }

    public void takeAudio(View v) {
        if (mySize() > 10) {
            if (!editText.getText().toString().isEmpty()) {
                //-----------------
                File SDCardRoot = Environment.getExternalStorageDirectory();
                //create a new file, to save the downloaded file
                File directory = new File(SDCardRoot, "/NDS audios/");

                if (!directory.exists()) {
                    directory.mkdir();
                }
                //----------
                Calendar cal = Calendar.getInstance();
                String audio_name = "/NDS audios/NDS_audio_" + cal.getTimeInMillis() + ".3gp";
                String outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + audio_name;
                recordAudio(outputFile);
            } else {
                Toast.makeText(Suggestion_box_suggest.this, "Type receipient of Suggestion please!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getBaseContext(), "Not enough space on phone!", Toast.LENGTH_SHORT).show();
        }


    }

    public void takeVideo(View v) {
        if (mySize() > 10) {
            if (!editText.getText().toString().isEmpty()) {
                //Toast.makeText(Suggestion_box_suggest.this, editText.getText().toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

                // create a file to save the video
                fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);

                // set the image file name
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 12);
                // set the video image quality to high
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

                // start the Video Capture Intent
                startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
            } else {
                Toast.makeText(Suggestion_box_suggest.this, "Type receipient of Suggestion please!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getBaseContext(), "Not enough space on phone!", Toast.LENGTH_SHORT).show();
        }

    }


    private File createImageFile() throws IOException {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "NDS_" + timeStamp + "_";
        //File mediaStorageDir = Environment.getExternalStoragePublicDirectory("/NDS images");
        String CameraFolder = "Camera";
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/" + CameraFolder + "/");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }


        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        //-------------

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                mediaStorageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }


    private Uri fileUri;

    public Uri getOutputMediaFileUriForImage(int type) {
        return Uri.fromFile(getOutputMediaFileForImage(type));
    }

    private static File getOutputMediaFileForImage(int type) {
        Calendar cal = Calendar.getInstance();
        //--------------
        File direct = new File(Environment.getExternalStorageDirectory() + "/NDS images");

        if (!direct.exists()) {
            direct = new File("/NDS images");
            direct.mkdirs();
        }
        final String filename = "NDS_img_" + cal.getTimeInMillis() + ".png";

        File file = new File(Environment.getExternalStorageDirectory() + File.separator
                + "/NDS images/", filename);

        //--


        return file;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "/NDS images");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "NDS_img_" + timeStamp + ".png");
    }

    Uri file;
    //=======To create a file==========================
    String mCurrentPhotoPath;
    //-------------

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }


    Uri photoUri;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub

        outState.putParcelable("file_uri", photoUri);


        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        photoUri = savedInstanceState.getParcelable("file_uri");
    }

    static volatile int imageTaken = 0;

    public void takePicture(View v) {
        if (mySize() > 10) {
            // Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                //---------Create the file where the photo should go--
                File photoFile = null;
                try {
                    photoFile = createImageFile();

                } catch (IOException e) {
                    e.printStackTrace();
                    //--Error occured while creating file--
                    Log.w("pic345", "" + e.toString());
                }
                //--------------Continue only if the was successfully created---
                if (photoFile != null) {


                    photoUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photoFile);
                    MyApplication myApplication = (MyApplication) getApplication();
                    myApplication.setPictUri(photoUri);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    Log.w("pic345", "Image okay!" + mCurrentPhotoPath);
                    //---------
                    imageTaken = 1;

                    startActivityForResult(intent, 900);


                }

            }
            //-----------------------
            /*
            file = Uri.fromFile(getOutputMediaFile());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
            Log.w("xcdf", "Done");

            startActivityForResult(intent, 0);
            */


            //-------------

        } else {
            Toast.makeText(getBaseContext(), "Not enough space on phone!", Toast.LENGTH_SHORT).show();
        }

        //Toast.makeText(Suggestion_box_suggest.this, editText.getText().toString(), Toast.LENGTH_SHORT).show();

    }

    public static Bitmap rotateBitmap(String src) {
        // Bitmap bitmap = BitmapFactory.decodeFile(src);
        int cyuma = 0;

        return decodeFile(src);

    }

    public static Bitmap decodeFile(String path) {
        int orientation;
        try {
            if (path == null) {
                return null;
            }
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 8;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale++;
            }
            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            Bitmap bm = BitmapFactory.decodeFile(path, o2);
            Bitmap bitmap = bm;
            ExifInterface exif = new ExifInterface(path);
            orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            Log.e("orientation", "" + orientation);
            Matrix m = new Matrix();
            if ((orientation == 3)) {
                m.postRotate(180);
                m.postScale((float) bm.getWidth(), (float) bm.getHeight());
                if (m.preRotate(90)) {
                    Log.e("in orientation", "" + orientation);
                }
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
                return bitmap;
            } else if (orientation == 6) {
                m.postRotate(90);
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
                return bitmap;
            } else if (orientation == 8) {
                m.postRotate(270);
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
                return bitmap;
            }
            return bitmap;
        } catch (Exception e) {

        }
        return null;
    }

    public String getImagePath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else {
            return null;
        }
    }

    public void addCaptionToSuggestionMedia(final String path, final String filename, String mediaType, final String myRemoteId, final String SuggestionBoxId) {

        //Log.w("cyuma",""+path);

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.add_supporting_text_to_this_suggeestion, null);
        final PopupWindow popupWindow_000 = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow_000.setBackgroundDrawable(getResources().getDrawable(R.mipmap.box_0000));

        popupWindow_000.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //TODO do sth here on dismiss
                try {
                    if (mediaPlayer.isPlaying()) {

                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.release();
                    }


                } catch (RuntimeException ddfuh) {
                    ddfuh.printStackTrace();
                }
            }
        });

        // popupWindow_000.showAsDropDown(editText);
        popupWindow_000.setAnimationStyle(android.R.style.TextAppearance_DeviceDefault_Small_Inverse);
        popupWindow_000.setFocusable(true);
        popupWindow_000.showAtLocation(editText, 40, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
        // popupWindow_000.setOutsideTouchable(false);


        popupWindow_000.update();
        LinearLayout image_tag = (LinearLayout) popupView.findViewById(R.id.image_tag);
        LinearLayout video_tag = (LinearLayout) popupView.findViewById(R.id.video_tag);
        LinearLayout audio_tag = (LinearLayout) popupView.findViewById(R.id.audio_tag);
        if (mediaType.equalsIgnoreCase("image")) {
            image_tag.setVisibility(View.VISIBLE);
            video_tag.setVisibility(View.GONE);
            audio_tag.setVisibility(View.GONE);
            //-------------
            final EditText edixt_label = (EditText) popupView.findViewById(R.id.edixt_label);
            ImageView edixt_image = (ImageView) popupView.findViewById(R.id.edixt_image);
            //----

            File imgFile = new File(path);
            if (imgFile.exists()) {
                try {
                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    Bitmap bm = BitmapFactory.decodeFile(path, options);
                    //Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    // Log.w("myPath", path);
                    //--Adjust imagte before final display haha!---------


                    //---
                    Bitmap srcBmp = bm;
                    Bitmap dstBmp = null;
                    //---Cropping for small display--
                    try {
                        if (srcBmp.getWidth() >= srcBmp.getHeight()) {
                            try {
                                dstBmp = Bitmap.createBitmap(srcBmp, srcBmp.getWidth() - srcBmp.getHeight(), 0, srcBmp.getHeight(), srcBmp.getHeight());
                                //Log.w("cropped yes","good");
                            } catch (RuntimeException shhh) {

                            }
                        } else {
                            try {
                                dstBmp = Bitmap.createBitmap(srcBmp, 0, srcBmp.getHeight() - srcBmp.getWidth(), srcBmp.getWidth(), srcBmp.getWidth());
                                //Log.w("cropped yes","good");
                            } catch (RuntimeException shhh) {

                            }
                        }


                        //-----------------
                        edixt_image.setImageBitmap(dstBmp);
                        edixt_image.setTag(path);
                    } catch (RuntimeException shhh) {

                    }


                } catch (Exception vb) {

                }

                //-------------


                //----------------
            }
            Button send_photo_suggest = (Button) popupView.findViewById(R.id.send_photo_suggest);
            send_photo_suggest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!edixt_label.getText().toString().isEmpty()) {
                        //popupWindow_000.dismiss();
                        new My_Community_Upload_image(Suggestion_box_suggest.this, path, filename, myRemoteId, SuggestionBoxId, edixt_label.getText().toString(), Suggestion_box_suggest.this, popupWindow_000).execute();


                    } else {
                        Toast.makeText(Suggestion_box_suggest.this, "Caption please!", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } else if (mediaType.equalsIgnoreCase("audio")) {
            image_tag.setVisibility(View.GONE);
            video_tag.setVisibility(View.GONE);
            audio_tag.setVisibility(View.VISIBLE);
            //--------------------edixt_audio
            ImageView edixt_audio = (ImageView) popupView.findViewById(R.id.edixt_audio);
            edixt_audio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayer = new MediaPlayer();
                    if (mediaPlayer != null) {
                        if (!play_yes) {
                            play_yes = true;

                            try {
                                mediaPlayer.setDataSource(path);
                                mediaPlayer.prepare();
                                mediaPlayer.start();

                                finalTime = mediaPlayer.getDuration();
                                startTime = mediaPlayer.getCurrentPosition();
                                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    public void onCompletion(MediaPlayer mp) {
                                        mp.release();

                                    }

                                    ;
                                });
                                //---
                                seekBar.setProgress((int) startTime);
                                myHandler_IU.postDelayed(UpdateSongTime, 100);

                                if (oneTimeOnly == 0) {
                                    seekBar.setMax((int) finalTime);
                                    oneTimeOnly = 1;
                                }
                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {

                            try {
                                play_yes = false;

                                if (mediaPlayer.isPlaying()) {

                                    mediaPlayer.stop();
                                    mediaPlayer.reset();
                                    mediaPlayer.release();
                                }
                                myHandler_IU.removeCallbacks(UpdateSongTime);
                            } catch (RuntimeException ddfuh) {
                                ddfuh.printStackTrace();
                            }
                        }
                    }


                }
            });
            seekBar = (SeekBar) popupView.findViewById(R.id.seekBar);
            final EditText edixt_label_audio = (EditText) popupView.findViewById(R.id.edixt_label_audio);
            Button send_suggest_audio = (Button) popupView.findViewById(R.id.send_suggest_audio);
            send_suggest_audio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!edixt_label_audio.getText().toString().isEmpty()) {
                        //  popupWindow_000.dismiss();
                        new My_Community_Upload_audio(Suggestion_box_suggest.this, path, filename, myRemoteId, SuggestionBoxId, edixt_label_audio.getText().toString(), Suggestion_box_suggest.this, popupWindow_000).execute();
                    } else {
                        Toast.makeText(Suggestion_box_suggest.this, "Caption please!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //--------------------------------------------


            //-------------


        } else if (mediaType.equalsIgnoreCase("video")) {
            image_tag.setVisibility(View.GONE);
            video_tag.setVisibility(View.VISIBLE);
            audio_tag.setVisibility(View.GONE);
            //------------
            //-------------
            final EditText edixt_label_video = (EditText) popupView.findViewById(R.id.edixt_label_video);
            ImageView edixt_image_video = (ImageView) popupView.findViewById(R.id.edixt_image_video);
            Button send_suggest_video = (Button) popupView.findViewById(R.id.send_suggest_video);
            //-----

            //-- handle the video professionally---
            String videoFile = path;
            Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(videoFile,
                    MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);
            //BitmapDrawable bitmapDrawable = new BitmapDrawable(thumbnail);
            edixt_image_video.setImageBitmap(thumbnail);
            ///-------------
            edixt_image_video.setTag(path);
            //----
            edixt_image_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
                    intent.setDataAndType(Uri.parse(path), "video/mp4");
                    startActivity(intent);
                }
            });
            edixt_image_video.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        handler.postDelayed(mLongPressed, 1000);
                    }
                    if ((event.getAction() == MotionEvent.ACTION_MOVE) || (event.getAction() == MotionEvent.ACTION_UP)) {
                        time_down = 0;
                        handler.removeCallbacks(mLongPressed);
                    }

                    return false;
                }
            });


            //-------------


            //---

            send_suggest_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //popupWindow_000.dismiss();
                    if (!edixt_label_video.getText().toString().isEmpty()) {
                        // popupWindow_000.dismiss();
                        new My_Community_Upload_video(Suggestion_box_suggest.this, path, filename, myRemoteId, SuggestionBoxId, edixt_label_video.getText().toString(), Suggestion_box_suggest.this, popupWindow_000).execute();
                    } else {
                        Toast.makeText(Suggestion_box_suggest.this, "Caption please!", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }


    }

    final Handler handler = new Handler();
    Runnable mLongPressed = new Runnable() {
        public void run() {
            if (time_down >= 3) {
                Log.i("", "Long press!");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(path_000));
                intent.setDataAndType(Uri.parse(path_000), "video/mp4");
                startActivity(intent);
                time_down = 0;
            }
            time_down++;
        }
    };

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {

            try {
                startTime = mediaPlayer.getCurrentPosition();

                seekBar.setProgress((int) startTime);

                myHandler_IU.postDelayed(this, 100);
            } catch (RuntimeException ssuhf) {

            }
        }
    };

    private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            try {
                image = Bitmap.createScaledBitmap(image, finalWidth * 14, finalHeight * 10, true);
            } catch (RuntimeException sfsh) {

            }
            return image;
        } else {
            return image;
        }
    }

    private static final long KILOBYTE = 1024;

    public long mySize() {
        StatFs internalStatFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        long internalTotal;
        long internalFree;

        StatFs externalStatFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        long externalTotal;
        long externalFree;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            internalTotal = (internalStatFs.getBlockCountLong() * internalStatFs.getBlockSizeLong()) / (KILOBYTE * KILOBYTE);
            internalFree = (internalStatFs.getAvailableBlocksLong() * internalStatFs.getBlockSizeLong()) / (KILOBYTE * KILOBYTE);
            externalTotal = (externalStatFs.getBlockCountLong() * externalStatFs.getBlockSizeLong()) / (KILOBYTE * KILOBYTE);
            externalFree = (externalStatFs.getAvailableBlocksLong() * externalStatFs.getBlockSizeLong()) / (KILOBYTE * KILOBYTE);
        } else {
            internalTotal = ((long) internalStatFs.getBlockCount() * (long) internalStatFs.getBlockSize()) / (KILOBYTE * KILOBYTE);
            internalFree = ((long) internalStatFs.getAvailableBlocks() * (long) internalStatFs.getBlockSize()) / (KILOBYTE * KILOBYTE);
            externalTotal = ((long) externalStatFs.getBlockCount() * (long) externalStatFs.getBlockSize()) / (KILOBYTE * KILOBYTE);
            externalFree = ((long) externalStatFs.getAvailableBlocks() * (long) externalStatFs.getBlockSize()) / (KILOBYTE * KILOBYTE);
        }

        long total = internalTotal + externalTotal;
        long free = internalFree + externalFree;
        long used = total - free;
        long percentage = (long) 100 * free / total;
        //Toast.makeText(getBaseContext(),"-=="+percentage+"-free-"+free+"--total--"+total,Toast.LENGTH_SHORT).show();
        return (long) (free / 10);
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getBaseContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private String getRealPathFromURI_Audio(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getBaseContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        //int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }


}

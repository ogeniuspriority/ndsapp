package com.ogeniuspriority.nds.nds;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ogeniuspriority.nds.nds.crop.Crop;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Creds_GetACtivarionCode;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Creds_changeLocationInfo;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Creds_changeLoginInfo;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Creds_changeNames;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Creds_changePhoneNumber;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Creds_changeWorkInfo;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Upload_Profile_image;
import com.ogeniuspriority.nds.nds.m_UI.My_Community_Posts_PicassoClient;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Profile_edit extends AppCompatActivity {
    ImageView commentator_avatar;
    TextView name_;
    // Button changeBtn;
    TextView contact_number;
    ImageView edit_contact_number;
    TextView login_info;
    ImageView edit_login_info;
    TextView locationdata;
    ImageView edit_location;
    TextView work_info;
    ImageView edit_work_info;
    TextView edit_names;
    private static String MYRemoteId = "";
    private static String MYAvatar = "";
    private static String MYNames = "";
    //--
    private static String telephone_nber = "";
    private static String workInfo = "";
    private static String locationInfo = "";
    NDS_db_adapter db;
    //---------------
    public static int IT_IS_FOR_GALLERY = 0;
    private static int SOME_RANDOM_REQUEST_CODE = 501;
    private static int cameraIsUsed = 0;
    Handler m_handler;
    Runnable m_handlerTask;
    static int _count = 0;
    static volatile ConnectivityManager cm;
    //---
    public static Activity myAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //----------------
        db = new NDS_db_adapter(this);
        //-------------------
        myAct = this;
        db.openToWrite();
        Cursor UserCreds = db.queueAllFromCreds();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (UserCreds.moveToLast()) {
            MYRemoteId = UserCreds.getString(9);
            MYAvatar = UserCreds.getString(3);
            MYNames = UserCreds.getString(1);
            //---
            telephone_nber = UserCreds.getString(2);
            workInfo = UserCreds.getString(11);
            locationInfo = UserCreds.getString(10);
        } else {


        }
        //---


        //--
        commentator_avatar = (ImageView) findViewById(R.id.commentator_avatar);
        commentator_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile_edit.this, ViewProfile.class);
                intent.putExtra("MyRemoteAvatar", (MYAvatar != null) ? MYAvatar : "");
                startActivity(intent);
            }
        });
        if (MYAvatar != null) {
            // imageLoader.DisplayImage(Config.LOAD_MY_AVATAR.toString() + MYAvatar, img);
            My_Community_Posts_PicassoClient.downloadImage(Profile_edit.this, Config.LOAD_MY_AVATAR.toString() + MYAvatar, commentator_avatar);

        } else {
            // imageLoader.DisplayImage(Config.LOAD_MY_AVATAR.toString() + "avatar_default.png", img);
            //Picasso.with(getBaseContext()).load(Config.LOAD_MY_AVATAR.toString() + MYAvatar).placeholder(R.mipmap.neza_default_avatar).into(commentator_avatar);

            My_Community_Posts_PicassoClient.downloadImage(Profile_edit.this, Config.LOAD_MY_AVATAR.toString() + MYAvatar, commentator_avatar);

        }
        name_ = (TextView) findViewById(R.id.name_);
        name_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editNames(view);
            }
        });
        name_.setText(MYNames);
        // changeBtn = (Button) findViewById(R.id.changeBtn);
        contact_number = (TextView) findViewById(R.id.contact_number);
        contact_number.setText(telephone_nber);
        edit_contact_number = (ImageView) findViewById(R.id.edit_contact_number);
        login_info = (TextView) findViewById(R.id.login_info);
        edit_login_info = (ImageView) findViewById(R.id.edit_login_info);
        locationdata = (TextView) findViewById(R.id.locationdata);
        locationdata.setText(locationInfo);
        edit_location = (ImageView) findViewById(R.id.edit_location);
        work_info = (TextView) findViewById(R.id.work_info);
        work_info.setText(workInfo);
        edit_names = (TextView) findViewById(R.id.edit_names);
        //p--------
        /*changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(Profile_edit.this, view);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.boo_media_outreach, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().toString().equalsIgnoreCase("Profile Image From Gallery")) {

                            //Crop.pickImage(Profile_edit.this);
                            if (mySize() > 10) {
                                *//*Intent i = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(i, Crop.REQUEST_PICK);*//*
                                CropImage.startPickImageActivity(Profile_edit.this);
                                //------------

                            } else {
                                Toast.makeText(getBaseContext(), "Not enough space on phone!", Toast.LENGTH_SHORT).show();
                            }


                        } else if (item.getTitle().toString().equalsIgnoreCase("Profile Image From Camera")) {

                            cameraIsUsed = 90;
                            if (mySize() > 10) {
                                open();
                            } else {
                                Toast.makeText(getBaseContext(), "Not enough space on phone!", Toast.LENGTH_SHORT).show();
                            }

                        }


                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });*/
        edit_names.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //choose from gallery or take from camera
                //Creating the instance of PopupMenu
                editNames(view);

            }
        });
        edit_work_info = (ImageView) findViewById(R.id.edit_work_info);
        edit_login_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfile_login_info(view);
            }
        });
        edit_contact_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfile_contact_number(view);
            }
        });
        edit_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfile_location_info(view);
            }
        });
        edit_work_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editWork_info(view);
            }
        });
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finish();

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
    String mCurrentPhotoPath;

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

    Uri photoUri;
    static volatile int imageTaken = 0;

    final int CAMERA_CAPTURE = 1;
    final int CROP_PIC = 2;
    private static volatile Uri picUri;

    public void open() {
        //CropImage.startPickImageActivity(this);
        try {
            // use standard intent to capture an image
            Intent captureIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            picUri = Uri.fromFile(new File(Environment
                    .getExternalStorageDirectory(), "tmp_avatar_"
                    + String.valueOf(System.currentTimeMillis())
                    + ".jpg"));

            captureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                    picUri);
            captureIntent.putExtra("return-data", true);
            // we will handle the returned data in onActivityResult
            startActivityForResult(captureIntent, CAMERA_CAPTURE);
        } catch (ActivityNotFoundException anfe) {
            Toast toast = Toast.makeText(this, "This device doesn't support the crop action!",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        /*
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
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
        */
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }


    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            //-------Save to the remote storage-----
            saveImageToLocalStore(rotateBitmap(imageUri.getPath()));


            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                // no permissions required or already granted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }


        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_CAPTURE) {
                // get the Uri for the captured image
                //picUri = data.getData();
                try {
                    performCrop();
                } catch (RuntimeException djjd) {
                    saveImageToLocalStore(rotateBitmap(picUri.getPath()));
                }
            }
            // user is returning from cropping the image
            else if (requestCode == CROP_PIC) {
                // get the returned data
                Bundle extras = data.getExtras();
                Uri lastData = data.getData();
                saveImageToLocalStore(rotateBitmap(lastData.getPath()));

            }
        }
    }

    private void performCrop() {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 2);
            cropIntent.putExtra("aspectY", 3);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 200);
            cropIntent.putExtra("outputY", 300);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult


            startActivityForResult(cropIntent, CROP_PIC);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            Toast toast = Toast
                    .makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
            saveImageToLocalStore(rotateBitmap(picUri.getPath()));
        }
    }

    static Uri mCropImageUri;
    static Uri imageUri;

    private void startCropImageActivity() {
        CropImage.activity()
                .start(this);
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .start(this);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // required permissions granted, start crop image activity
                startCropImageActivity(mCropImageUri);
            } else {
                Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
            }
        }
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

    private void saveImageToLocalStore(Bitmap finalBitmap) {

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
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();


            //---
            final String path_0 = Environment.getExternalStorageDirectory() + File.separator
                    + "/NDS images/" + filename;
            final String filename_to_up = path_0.substring(path_0.lastIndexOf("/") + 1);
            Log.w("DirImage", "failed!" + path_0.toString());
            //--------------
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new My_Community_Upload_Profile_image(Profile_edit.this, path_0, filename_to_up, MYRemoteId, Profile_edit.this).execute();
                }
            }).start();


            //-------------------------------------------
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("DirImage", "failed!" + e.toString());
        }

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

    public void editProfile_login_info(View v) {
        //Log.w("cyuma",""+path);
        AlertDialog.Builder mBuilderinfo = new AlertDialog.Builder(Profile_edit.this);
        View mView = getLayoutInflater().inflate(R.layout.edit_login_info, null);
        final EditText email = (EditText) mView.findViewById(R.id.email);
        final EditText password = (EditText) mView.findViewById(R.id.password);
        final EditText passwordretype = (EditText) mView.findViewById(R.id.passwordretype);
        Button email_sign_in_button = (Button) mView.findViewById(R.id.email_sign_in_button);

        mBuilderinfo.setTitle("Edit login info");
        mBuilderinfo.setView(mView);
        final AlertDialog dialog = mBuilderinfo.create();
        dialog.show();

        //-------------------------------------

        email_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty() || passwordretype.getText().toString().isEmpty()) {
                    Toast.makeText(Profile_edit.this, "Empty field!", Toast.LENGTH_SHORT).show();
                } else if (email.getText().toString().equalsIgnoreCase(password.getText().toString())) {
                    Toast.makeText(Profile_edit.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                } else {
                    new My_Community_Creds_changeLoginInfo(Profile_edit.this, Config.NDS_USER_LOGIN_INFO, MYRemoteId, email.getText().toString(), password.getText().toString(), dialog, Profile_edit.this).execute();
                }

            }
        });


    }

    public void editProfile_contact_number(View v) {
        Log.w("cyuma","profile number Edit");
        AlertDialog.Builder mBuilderinfo = new AlertDialog.Builder(Profile_edit.this);
        View mView = getLayoutInflater().inflate(R.layout.edit_contact_number, null);
        final EditText number_field = (EditText) mView.findViewById(R.id.number_field);
        final EditText thecode = (EditText) mView.findViewById(R.id.thecode);
        Button back = (Button) mView.findViewById(R.id.back);
        final ProgressBar progressBar = (ProgressBar) mView.findViewById(R.id.progressBar);
        final TextView progressText = (TextView) mView.findViewById(R.id.progressText);
        final RelativeLayout resend_code_now = (RelativeLayout) mView.findViewById(R.id.resend_code_now);
        final ImageView resend_img = (ImageView) mView.findViewById(R.id.resend_img);
        final Button conf_change = (Button) mView.findViewById(R.id.conf_change);
        mBuilderinfo.setTitle("Edit login info");
        mBuilderinfo.setView(mView);
        final AlertDialog dialog = mBuilderinfo.create();
        dialog.show();

        resend_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (!number_field.getText().toString().isEmpty()) {
                    m_handler.removeCallbacks(m_handlerTask);
                    //----------------------
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                cm = (ConnectivityManager) Profile_edit.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                                if (netInfo != null && netInfo.isConnected()) {
                                    try {
                                        URL url = new URL(Config.IP_ADDRESS);   // Change to "http://google.com" for www  test.
                                        HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                                        urlc.setConnectTimeout(10 * 1000);          // 10 s.
                                        urlc.connect();
                                        if (urlc.getResponseCode() == 200) {        // 200 = "OK" code (http connection is fine).
                                            Log.wtf("Connection", "Success !");
                                            new My_Community_Creds_GetACtivarionCode(Profile_edit.this, Config.NDS_USER_GET_ACTIVATION_CODE, MYRemoteId, "" + number_field.getText().toString(), resend_code_now).execute();

                                            //--------------
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    m_handler = new Handler();
                                                    m_handlerTask = new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if (_count <= 300) {
                                                                int remaining = 300 - _count;
                                                                int minutes = (int) remaining / 60;
                                                                int sec = remaining - (minutes * 60);
                                                                progressText.setText("Waiting " + minutes + ":" + sec);
                                                                _count++;
                                                                progressBar.setProgress(_count);


                                                            } else {
                                                                m_handler.removeCallbacks(m_handlerTask);
                                                                RelativeLayout resend_code_now = (RelativeLayout) findViewById(R.id.resend_code_now);
                                                                resend_code_now.setVisibility(View.VISIBLE);
                                                                progressText.setText(" ");
                                                                _count = 0;
                                                            }
                                                            m_handler.postDelayed(m_handlerTask, 1000);
                                                        }
                                                    };
                                                    m_handlerTask.run();
                                                }
                                            });

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
                } else {
                    Toast.makeText(Profile_edit.this, "Number please", Toast.LENGTH_SHORT).show();
                }
            }
        });
        conf_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (!number_field.getText().toString().isEmpty()) {
                    try {
                        m_handler.removeCallbacks(m_handlerTask);
                    } catch (RuntimeException dfd) {

                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                cm = (ConnectivityManager) Profile_edit.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                                if (netInfo != null && netInfo.isConnected()) {
                                    try {
                                        URL url = new URL(Config.IP_ADDRESS);   // Change to "http://google.com" for www  test.
                                        HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                                        urlc.setConnectTimeout(10 * 1000);          // 10 s.
                                        urlc.connect();
                                        if (urlc.getResponseCode() == 200) {        // 200 = "OK" code (http connection is fine).
                                            Log.wtf("Connection", "Success !");
                                            new My_Community_Creds_GetACtivarionCode(Profile_edit.this, Config.NDS_USER_GET_ACTIVATION_CODE, MYRemoteId, "" + number_field.getText().toString(), conf_change).execute();

                                            //--------------
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    conf_change.setVisibility(View.GONE);
                                                    m_handler = new Handler();
                                                    m_handlerTask = new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if (_count <= 300) {
                                                                int remaining = 300 - _count;
                                                                int minutes = (int) remaining / 60;
                                                                int sec = remaining - (minutes * 60);
                                                                progressText.setText("Waiting " + minutes + ":" + sec);
                                                                _count++;
                                                                progressBar.setProgress(_count);


                                                            } else {
                                                                m_handler.removeCallbacks(m_handlerTask);
                                                                RelativeLayout resend_code_now = (RelativeLayout) findViewById(R.id.resend_code_now);
                                                                resend_code_now.setVisibility(View.VISIBLE);
                                                                progressText.setText(" ");
                                                                _count = 0;
                                                            }
                                                            m_handler.postDelayed(m_handlerTask, 1000);
                                                        }
                                                    };
                                                    m_handlerTask.run();
                                                }
                                            });

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
                } else {
                    Toast.makeText(Profile_edit.this, "Number please", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //------------------
        number_field.setText(telephone_nber);

        //--------------

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                try {
                    m_handler.removeCallbacks(m_handlerTask);
                    _count = 0;
                } catch (RuntimeException vhvyy) {

                }
            }

    });





        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button Continue = (Button) mView.findViewById(R.id.Continue);
        //----------------------
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!thecode.getText().toString().isEmpty()) {
                    m_handler.removeCallbacks(m_handlerTask);
                    LinearLayout resend_code_now = (LinearLayout) mView.findViewById(R.id.resend_code_now);
                    resend_code_now.setVisibility(View.VISIBLE);
                    progressText.setText(" ");
                    _count = 0;

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                cm = (ConnectivityManager) Profile_edit.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                                if (netInfo != null && netInfo.isConnected()) {
                                    try {
                                        URL url = new URL(Config.IP_ADDRESS);   // Change to "http://google.com" for www  test.
                                        HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                                        urlc.setConnectTimeout(10 * 1000);          // 10 s.
                                        urlc.connect();
                                        if (urlc.getResponseCode() == 200) {        // 200 = "OK" code (http connection is fine).
                                            Log.wtf("Connection", "Success !");
                                            new My_Community_Creds_changePhoneNumber(Profile_edit.this, Config.NDS_USER_CONFIRM_NUMBER, MYRemoteId, "" + number_field.getText().toString(), thecode.getText().toString(), dialog, Profile_edit.this).execute();


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

                } else {
                    Toast.makeText(Profile_edit.this, "Verification code please!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void editProfile_location_info(View v) {
        //Log.w("cyuma",""+path);
        AlertDialog.Builder mBuilderinfo = new AlertDialog.Builder(Profile_edit.this);
        View mView = getLayoutInflater().inflate(R.layout.edit_location_location, null);
        final EditText LocationInfo = (EditText) mView.findViewById(R.id.LocationInfo);
        Button email_sign_in_button = (Button) mView.findViewById(R.id.email_sign_in_button);
        LocationInfo.setText(locationInfo);

        LocationInfo.setText(workInfo);
        mBuilderinfo.setTitle("Edit location");
        mBuilderinfo.setView(mView);
        final AlertDialog dialog = mBuilderinfo.create();
        dialog.show();

        email_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!LocationInfo.getText().toString().isEmpty()) {
                    new My_Community_Creds_changeLocationInfo(Profile_edit.this, Config.NDS_USER_LOACTION_INFO, MYRemoteId, LocationInfo.getText().toString(), dialog, Profile_edit.this).execute();
                } else {
                    Toast.makeText(Profile_edit.this, "Empty field!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void editWork_info(View v) {
        //Log.w("cyuma",""+path);
        AlertDialog.Builder mBuilderinfo = new AlertDialog.Builder(Profile_edit.this);
        View mView = getLayoutInflater().inflate(R.layout.edit_location_info, null);

        final EditText WorkInfo = (EditText) mView.findViewById(R.id.WorkInfo);
        Button email_sign_in_button = (Button) mView.findViewById(R.id.email_sign_in_button);
        WorkInfo.setText(workInfo);
        mBuilderinfo.setTitle("Edit work info");
        mBuilderinfo.setView(mView);
        final AlertDialog dialog = mBuilderinfo.create();
        dialog.show();


        //---

        email_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!WorkInfo.getText().toString().isEmpty()) {
                    new My_Community_Creds_changeWorkInfo(Profile_edit.this, Config.NDS_USER_WORK_INFO, MYRemoteId, WorkInfo.getText().toString(), dialog, Profile_edit.this).execute();
                } else {
                    Toast.makeText(Profile_edit.this, "Empty field!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private File getExternalStorageTempStoreFilePath() {
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File file = new File(path, "selected_temp_image.jpg");
        return file;
    }

    public void editNames(View v) {
        //Log.w("cyuma",""+path);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Profile_edit.this);
        View mView = getLayoutInflater().inflate(R.layout.edit_names, null);
        final EditText Names = (EditText) mView.findViewById(R.id.Names);
        Names.setText(MYNames);
        Button email_sign_in_button = (Button) mView.findViewById(R.id.email_sign_in_button);
mBuilder.setTitle("Edit names");
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        email_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Names.getText().toString().isEmpty()) {
                    new My_Community_Creds_changeNames(Profile_edit.this, Config.NDS_USER_NAMES, MYRemoteId, Names.getText().toString(), dialog, Profile_edit.this).execute();
                } else {
                    Toast.makeText(Profile_edit.this, "Empty field!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public static long getAvailableSpaceInMB() {
        final long SIZE_KB = 1024L;
        final long SIZE_MB = SIZE_KB * SIZE_KB;
        long availableSpace = -1L;
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        availableSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();

        return availableSpace / SIZE_MB;
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
        return (long) (free / 10);
    }


}

package com.ogeniuspriority.nds.nds;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.ogeniuspriority.nds.nds.crop.Crop;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Upload_Profile_image_lagoon;
import com.ogeniuspriority.nds.nds.m_UI.My_Community_Posts_Picasso_LOadThisImage;
import com.ogeniuspriority.nds.nds.photoview.PhotoViewAttacher;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ViewProfile extends AppCompatActivity {
    PhotoViewAttacher mAttacher;
    public static int IT_IS_FOR_GALLERY = 0;
    private static int SOME_RANDOM_REQUEST_CODE = 501;
    private static int cameraIsUsed = 0;
    Handler m_handler;
    Runnable m_handlerTask;
    static int _count = 0;
    static volatile ConnectivityManager cm;
    private static String MYRemoteId = "";
    private static String MYAvatar = "";
    private static String MYNames = "";
    //--
    private static String telephone_nber = "";
    private static String workInfo = "";
    private static String locationInfo = "";
    NDS_db_adapter db;
    String UserAvatar;
    ImageView profileView_;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        UserAvatar = extras.getString("MyRemoteAvatar");
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        setContentView(R.layout.activity_view_profile);
        db = new NDS_db_adapter(this);
        db.openToWrite();
        Cursor UserCreds = db.queueAllFromCreds();

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
        //------------
        getSupportActionBar().hide();
        //------------
        profileView_ = (ImageView) findViewById(R.id.profileView_);
        profileView_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAttacher.setZoomable(true);
            }
        });
        ImageView go_back_finish = (ImageView) findViewById(R.id.go_back_finish);
        go_back_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //-----------------------
        profileView_.setImageBitmap(null);
        profileView_.destroyDrawingCache();
        profileView_.setImageResource(0);
        My_Community_Posts_Picasso_LOadThisImage.downloadImage(ViewProfile.this, Config.LOAD_MY_AVATAR.toString() + MYAvatar, profileView_);

        mAttacher = new PhotoViewAttacher(profileView_);
        // Lets attach some listeners, not required though!
        mAttacher.setAllowParentInterceptOnEdge(true);
        mAttacher.setZoomable(false);
        ImageView image_edit = (ImageView) findViewById(R.id.image_edit);
        image_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(ViewProfile.this, view);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.boo_media_outreach, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().toString().equalsIgnoreCase("Profile Image From Gallery")) {

                            //Crop.pickImage(Profile_edit.this);
                            if (mySize() > 10) {
//                                Intent i = new Intent(Intent.ACTION_PICK,
//                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//                                startActivityForResult(i, Crop.REQUEST_PICK);
                                CropImage.startPickImageActivity(ViewProfile.this);
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
        });


    }

    Uri file;

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

    String mCurrentPhotoPath;
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
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            //---------Create the file where the photo should go--
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                //--Error occured while creating file--
//                Log.w("pic345", "" + e.toString());
//            }
//            //--------------Continue only if the was successfully created---
//            if (photoFile != null) {
//
//
//                photoUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photoFile);
//                MyApplication myApplication = (MyApplication) getApplication();
//                myApplication.setPictUri(photoUri);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//                Log.w("pic345", "Image okay!" + mCurrentPhotoPath);
//                //---------
//                imageTaken = 1;
//
//                startActivityForResult(intent, 900);
//
//
//            }
//
//        }
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
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},   CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
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
    static  Uri imageUri;
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
                    new My_Community_Upload_Profile_image_lagoon(ViewProfile.this, path_0, filename_to_up, MYRemoteId, ViewProfile.this).execute();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}

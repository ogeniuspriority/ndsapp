package com.ogeniuspriority.nds.nds;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class Photos_and_media extends AppCompatActivity {
    private static String MYRemoteId = "";
    private static String MYAvatar = "";
    private static String MYNames = "";
    NDS_db_adapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_and_media);
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
        //--
        final Cursor settings_ = db.GET_ALL_NDS_APP_LOCAL_SETTINGS();
        settings_.moveToFirst();
        final String remoteId = settings_.getString(0);
        String saveToGallery = settings_.getString(1);
        String mobileData = settings_.getString(4);
        String Wifi = settings_.getString(5);
        //-----------------
        CheckBox gallery_save = (CheckBox) findViewById(R.id.gallery_save);
        CheckBox wifi_ = (CheckBox) findViewById(R.id.wifi_);
        CheckBox mobile_data = (CheckBox) findViewById(R.id.mobile_data);
        //---
        if (saveToGallery.equalsIgnoreCase("0")) {
            gallery_save.setChecked(false);
        } else {
            gallery_save.setChecked(true);
        }
        if (mobileData.equalsIgnoreCase("0")) {
            mobile_data.setChecked(false);
        } else {
            mobile_data.setChecked(true);
        }
        if (Wifi.equalsIgnoreCase("0")) {
            wifi_.setChecked(false);
        } else {
            wifi_.setChecked(true);
        }

        //----------------------------
        gallery_save.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (db.Update_ALL_NDS_APP_LOCAL_SETTINGS(remoteId, "savetogallery", "1")) {
                        Toast.makeText(getBaseContext(),"Done!",Toast.LENGTH_SHORT).show();

                    }


                } else {
                    if (db.Update_ALL_NDS_APP_LOCAL_SETTINGS(remoteId, "savetogallery", "0")) {
                        Toast.makeText(getBaseContext(),"Done!",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        wifi_.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (db.Update_ALL_NDS_APP_LOCAL_SETTINGS(remoteId, "wifidata", "1")) {
                        Toast.makeText(getBaseContext(),"Done!",Toast.LENGTH_SHORT).show();
                    }


                } else {
                    if (db.Update_ALL_NDS_APP_LOCAL_SETTINGS(remoteId, "wifidata", "0")) {
                        Toast.makeText(getBaseContext(),"Done!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        mobile_data.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (db.Update_ALL_NDS_APP_LOCAL_SETTINGS(remoteId, "mobiledata", "1")) {
                        Toast.makeText(getBaseContext(),"Done!",Toast.LENGTH_SHORT).show();
                    }


                } else {
                    if (db.Update_ALL_NDS_APP_LOCAL_SETTINGS(remoteId, "mobiledata", "0")) {
                        Toast.makeText(getBaseContext(),"Done!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finish();

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

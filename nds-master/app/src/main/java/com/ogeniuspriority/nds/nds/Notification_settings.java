package com.ogeniuspriority.nds.nds;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

public class Notification_settings extends AppCompatActivity {
    private static String MYRemoteId = "";
    private static String MYAvatar = "";
    private static String MYNames = "";
    NDS_db_adapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);
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
        final Cursor settings_ = db.GET_ALL_NDS_APP_LOCAL_SETTINGS();
        settings_.moveToFirst();
        final String remoteId = settings_.getString(0);
        String play_sound = settings_.getString(2);
        String popup_notify = settings_.getString(3);
        CheckBox playSound = (CheckBox) findViewById(R.id.playSound);
        CheckBox Popup_notify = (CheckBox) findViewById(R.id.Popup_notify);
        //------------
        if (play_sound.equalsIgnoreCase("0")) {
            playSound.setChecked(false);
        } else {
            playSound.setChecked(true);
        }
        if (popup_notify.equalsIgnoreCase("0")) {
            Popup_notify.setChecked(false);
        } else {
            Popup_notify.setChecked(true);
        }
        //---
        playSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (db.Update_ALL_NDS_APP_LOCAL_SETTINGS(remoteId, "playsound", "1")) {
                        Toast.makeText(getBaseContext(),"Done!",Toast.LENGTH_SHORT).show();
                    }


                } else {
                    if (db.Update_ALL_NDS_APP_LOCAL_SETTINGS(remoteId, "playsound", "0")) {
                        Toast.makeText(getBaseContext(),"Done!",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        Popup_notify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (db.Update_ALL_NDS_APP_LOCAL_SETTINGS(remoteId, "popupnotify", "1")) {
                        Toast.makeText(getBaseContext(),"Done!",Toast.LENGTH_SHORT).show();
                    }


                } else {
                    if (db.Update_ALL_NDS_APP_LOCAL_SETTINGS(remoteId, "popupnotify", "0")) {
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

package com.ogeniuspriority.nds.nds;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;

/**
 * Created by USER on 10/11/2017.
 */
public class PermissionCheckCyuma {
    Context c;

    public void PermissionCheckCyuma(Context c) {
        this.c = c;
        if (this.hasAndroidPermissions) {
            //-----------all activated----------
        } else {
            //------------Some hanging - not activated--
            alertView();
        }

    }

    private void alertView() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(c, R.style.CardView);

        dialog.setTitle("Permission Denied")
                .setInverseBackgroundForced(true)
                //.setIcon(R.drawable.ic_info_black_24dp)
                .setMessage("Without those permission the app is unable to save your profile. App needs to save profile image in your external storage and also need to get profile image from camera or external storage.Are you sure you want to deny this permission?")

                .setNegativeButton("I'M SURE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.dismiss();
                    }
                })
                .setPositiveButton("RE-TRY", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.dismiss();
                        checkRunTimePermission();

                    }
                }).show();
    }

    private void checkRunTimePermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.hasAndroidPermissions) {
                //-----------all activated----------
            } else {
                //------------Some hanging - not activated--
            }
        } else {
            // if already permition granted
            // PUT YOUR ACTION (Like Open cemara etc..)
        }
    }

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    boolean hasAndroidPermissions = SystemUtils.hasPermissions(c, new String[]{
            android.Manifest.permission.ACCESS_WIFI_STATE,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.VIBRATE,
            Manifest.permission.INTERNET, Manifest.permission.CAMERA
    });


}

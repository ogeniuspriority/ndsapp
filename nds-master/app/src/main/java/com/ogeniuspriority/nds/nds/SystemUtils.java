package com.ogeniuspriority.nds.nds;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 10/11/2017.
 */
public class SystemUtils {

    public static boolean hasPermissions(Context c, String[] ThePerms) {
        int[] perms_0 = new int[ThePerms.length];
        int i = 0;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : ThePerms) {
            perms_0[i] = ContextCompat.checkSelfPermission(c, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

            //-------------------
            if (perms_0[i] != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
            }

        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) c, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        } else {
            return true;
        }


    }

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
}

package com.ogeniuspriority.nds.nds;

import android.os.Build;

/**
 * Created by USER on 10/10/2017.
 */
public class AltruisticBehavior {

    public void AltruisticBehavior() {

    }


    //Current Android version data
    public boolean mashaHambiroMellowCheck() {
        double release = Double.parseDouble(Build.VERSION.RELEASE);
        String codeName = "Unsupported";//below Jelly bean OR above Oreo
        if (release >= 4.1 && release < 4.4) {
            codeName = "Jelly Bean";
            return false;
        } else if (release < 5) {
            codeName = "Kit Kat";
            return false;
        } else if (release < 6) {
            codeName = "Lollipop";
            return false;
        } else if (release < 7) {
            codeName = "Marshmallow";
            return true;
        } else if (release < 8) {
            codeName = "Nougat";
            return false;
        } else if (release < 9) {
            codeName = "Oreo";
            return false;
        } else {
            return false;
        }

    }
    //---------------The permissions' check--------------



}

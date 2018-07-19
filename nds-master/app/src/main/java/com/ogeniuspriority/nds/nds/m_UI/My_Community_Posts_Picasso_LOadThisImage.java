package com.ogeniuspriority.nds.nds.m_UI;

import android.content.Context;
import android.widget.ImageView;

import com.ogeniuspriority.nds.nds.R;
import com.squareup.picasso.Picasso;

/**
 * Created by USER on 2/6/2017.
 */
public class My_Community_Posts_Picasso_LOadThisImage {
    public static void downloadImage(final Context c, final String imageUrl, final ImageView img){
        if(imageUrl!=null && imageUrl.length()>0){
            Picasso.with(c).load(imageUrl).resize(1300,1300).placeholder(R.drawable.neza_default_avatar).into(img, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    //do smth when picture is loaded successfully

                }

                @Override
                public void onError() {
                    //do smth when there is picture loading error
                    Picasso.with(c).load(imageUrl).placeholder(R.drawable.neza_default_avatar).into(img);
                }
            });

        }else{

            Picasso.with(c).load(R.drawable.box0).into(img);
        }
    }
}

package com.ogeniuspriority.nds.nds.m_UI;

import android.content.Context;
import android.widget.ImageView;

import com.ogeniuspriority.nds.nds.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by USER on 2/6/2017.
 */

public class My_Community_Posts_PicassoClient {
    public static void downloadImage(Context c, String imageUrl, ImageView img) {
        if (imageUrl != null && imageUrl.length() > 0) {
            Picasso.with(c).load(imageUrl).fit().placeholder(R.mipmap.neza_default_avatar).into(img, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                }
            });
            //Picasso.with(c).load(imageUrl).fit().placeholder(R.mipmap.neza_default_avatar).into(img);

        } else {

            Picasso.with(c).load(R.mipmap.neza_default_avatar).into(img);
        }
    }
}

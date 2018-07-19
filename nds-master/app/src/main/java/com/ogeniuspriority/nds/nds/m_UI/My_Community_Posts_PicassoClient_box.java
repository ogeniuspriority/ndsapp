package com.ogeniuspriority.nds.nds.m_UI;

import android.content.Context;
import android.widget.ImageView;

import com.ogeniuspriority.nds.nds.R;
import com.squareup.picasso.Picasso;

/**
 * Created by USER on 2/6/2017.
 */
public class My_Community_Posts_PicassoClient_box {
    public static void downloadImage(Context c, String imageUrl, ImageView img){
        if(imageUrl!=null && imageUrl.length()>0){
            Picasso.with(c).load(imageUrl).placeholder(R.drawable.box0).into(img);

        }else{

            Picasso.with(c).load(R.drawable.box0).into(img);
        }
    }
}

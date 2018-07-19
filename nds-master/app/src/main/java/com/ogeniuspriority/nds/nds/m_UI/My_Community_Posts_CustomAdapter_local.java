package com.ogeniuspriority.nds.nds.m_UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ogeniuspriority.nds.nds.Config;
import com.ogeniuspriority.nds.nds.R;
import com.ogeniuspriority.nds.nds.Urubuga_Services;
import com.ogeniuspriority.nds.nds.m_DataObject.My_Community_Posts;
import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_Posts_LikeOrUnlike;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by USER on 2/6/2017.
 */
public class My_Community_Posts_CustomAdapter_local extends BaseAdapter {
    Context c;
    static ArrayList<My_Community_Posts> my_Community_Posts;
    //-----
    int layoutResourceId;
    public List<String> data;
    public List<String> tags;
    public List<String> tags_unlike;


    public My_Community_Posts_CustomAdapter_local(Context c, ArrayList<My_Community_Posts> my_Community_Posts_) {

        this.c = c;
        this.my_Community_Posts = my_Community_Posts_;
        //----------------------------------------------------PlaceholderFragment.MYRemoteId
        // this.layoutResourceId = resource;
        this.c = c;
        this.data = data;
        tags = new ArrayList<String>();
        tags_unlike = new ArrayList<String>();
        int size = my_Community_Posts.size();
        for (int i = 0; i < size; i++) {
            tags.add("tag");
            tags_unlike.add("tag");
        }

    }

    public void AddMoreItemsAtTheBottom(String[] poster_name, String[] TimeStamp, String[] post_Data,
                                        String[] poster_Avatar, String[] coments_,
                                        int[] likes_, int[] Dislikes,
                                        int[] views_, int[] myID, String[] myID_Comments, int topOrBottom) {


        if (topOrBottom == 0) {
            My_Community_Posts m;
            for (int i = 0; i < poster_name.length; i++) {
                m = new My_Community_Posts();
                m.setMy_Posts_Regdate(TimeStamp[i]);
                m.setMy_Posts_Avatar_URLs(poster_Avatar[i]);
                m.setMy_Posts_Poster_name(poster_name[i]);
                m.setMy_Posts_Poster_Post(post_Data[i]);
                m.setMy_Posts_Post_Comments(coments_[i]);
                m.setMy_Posts_like_status(likes_[i]);
                m.setMy_Posts_unlike_status(Dislikes[i]);
                m.setMy_Posts_Views(views_[i]);
                m.setMy_Posts_Post_id(myID[i]);
                m.setMy_Posts_Poster_id(myID[i]);
                m.setMy_Posts_Post_Comments_ForOtherUse(myID_Comments[i]);
                //------------
                m.setMy_Avatars(poster_Avatar[i]);
                my_Community_Posts.add(i, m);
                tags.add("tag");
                tags_unlike.add("tag");
            }

        } else {
            int size = my_Community_Posts.size();
            My_Community_Posts m;
            for (int i = 0; i < poster_name.length; i++) {
                m = new My_Community_Posts();
                m.setMy_Posts_Regdate(TimeStamp[i]);
                m.setMy_Posts_Avatar_URLs(poster_Avatar[i]);
                m.setMy_Posts_Poster_name(poster_name[i]);
                m.setMy_Posts_Poster_Post(post_Data[i]);
                m.setMy_Posts_Post_Comments(coments_[i]);
                m.setMy_Posts_like_status(likes_[i]);
                m.setMy_Posts_unlike_status(Dislikes[i]);
                m.setMy_Posts_Views(views_[i]);
                m.setMy_Posts_Post_id(myID[i]);
                m.setMy_Posts_Poster_id(myID[i]);
                m.setMy_Posts_Post_Comments_ForOtherUse(myID_Comments[i]);
                //------------
                m.setMy_Avatars(poster_Avatar[i]);
                my_Community_Posts.add(size-1+i, m);
                tags.add("tag");
                tags_unlike.add("tag");
                //-----

            }


        }

    }

    @Override
    public int getCount() {

        return my_Community_Posts.size();

    }

    @Override
    public Object getItem(int position) {
        return my_Community_Posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(c).inflate(R.layout.forum_main_feed_mineonly, parent, false);
            TextView poster_name = (TextView) convertView.findViewById(R.id.poster_name);
            TextView TimeStamp = (TextView) convertView.findViewById(R.id.TimeStamp);
            TextView post_Data = (TextView) convertView.findViewById(R.id.post_Data);
            ImageView poster_Avatar = (ImageView) convertView.findViewById(R.id.poster_Avatar);
            TextView coments_ = (TextView) convertView.findViewById(R.id.coments_);
            TextView likes_ = (TextView) convertView.findViewById(R.id.likes_);
            TextView Dislikes = (TextView) convertView.findViewById(R.id.Dislikes);
            TextView views_ = (TextView) convertView.findViewById(R.id.views_);
            ImageView likeme = (ImageView) convertView.findViewById(R.id.likeme);

            likeme.setTag(position);
            //--- //-----------------------------------
            TextView myID = (TextView) convertView.findViewById(R.id.myID);
            TextView myID_Comments = (TextView) convertView.findViewById(R.id.myID_Comments);
            final My_Community_Posts posts = (My_Community_Posts) this.getItem(position);
            ///----------------
            TextView myID_Avatar = (TextView) convertView.findViewById(R.id.myID_Avatar);
            //--------
            myID_Avatar.setText(posts.getMy_Avatars());
            poster_name.setText(posts.getMy_Posts_Poster_name());
            //----------------
            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
            String date = df.format(Calendar.getInstance().getTime());
            TimeStamp.setText(posts.getMy_Posts_Regdate());
            //--------------------------
            post_Data.setText((posts.getMy_Posts_Poster_Post().contains("NDS_posts_img"))?"Image Not Supported on Phones in this version":posts.getMy_Posts_Poster_Post());
            My_Community_Posts_PicassoClient.downloadImage(c, posts.getMy_Posts_Avatar_URLs(), poster_Avatar);
            poster_Avatar.setTag(posts.getMy_Posts_Avatar_URLs());
            coments_.setText(posts.getMy_Posts_Post_Comments() + " Cmnts");
            likes_.setText("" + posts.getMy_Posts_like_status() + " likes");
            Dislikes.setText("" + posts.getMy_Posts_unlike_status() + " unlikes");
            views_.setText("" + posts.getMy_Posts_Views() + " Views");
            //-----------------------------------------------------------------------------
            myID.setText("" + posts.getMy_Posts_Post_id());
            //myID_Comments.setText(posts.getMy_Posts_Post_Comments_ForOtherUse());
            Urubuga_Services.AllComments = posts.getMy_Posts_Post_Comments_ForOtherUse();
            //--My_Posts_Poster_id--
            ImageView delete_this_post=(ImageView)  convertView.findViewById(R.id.delete_this_post);

            if(posts.getMy_Posts_Poster_id()==Integer.parseInt(Urubuga_Services.PlaceholderFragment.MYRemoteId)){
                delete_this_post.setVisibility(View.VISIBLE);
            }else{
                delete_this_post.setVisibility(View.GONE);
            }
            //---------
            ImageView reportUse = (ImageView) convertView.findViewById(R.id.reportUse);
            ImageView reportUse_flag = (ImageView) convertView.findViewById(R.id.reportUse_flag);
            reportUse.setTag(posts.getMy_Posts_Post_id());
            reportUse_flag.setTag(posts.getMy_Posts_Post_id());
            //----------------------------------------
            viewHolder = new ViewHolder();
            viewHolder.position = position;
            convertView.setTag(viewHolder);
            //---------------
            viewHolder.Likeme = (ImageView) convertView.findViewById(R.id.likeme);
            viewHolder.UnlikeMe = (ImageView) convertView.findViewById(R.id.imageView8);
            viewHolder.Likes = (TextView) convertView.findViewById(R.id.likes_);
            viewHolder.Unlikes = (TextView) convertView.findViewById(R.id.Dislikes);
            viewHolder.Likeme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //----------------
                    try {
                        String response = new My_Community_Posts_LikeOrUnlike(c, Config.NDS_LIKE_OR_DISLIKE_POST, "" + posts.getMy_Posts_Post_id(), Urubuga_Services.PlaceholderFragment.MYRemoteId, "liked").execute().get();
                        //-------------------okay2017
                        if (response != null) {
                            if (response.length() >= 4) {
                                //---
                                int Likes = Integer.parseInt(viewHolder.Likes.getText().toString().split("likes")[0].split(" ")[0]) + 1;
                                if (tags.get(position).equalsIgnoreCase("liked")) {

                                } else {
                                    viewHolder.Likes.setText(Likes + " likes");
                                }
                                viewHolder.Likeme.setTag("liked");
                                tags.add(position, "liked");
                                viewHolder.Likeme.setImageResource(R.drawable.like_me_active);
                                Toast.makeText(c, "Liked!", Toast.LENGTH_SHORT).show();
                                //------------
                                viewHolder.UnlikeMe.setImageResource(R.drawable.dislike);
                                //---


                            } else {
                                Toast.makeText(c, "Failed! No internet", Toast.LENGTH_SHORT).show();
                            }
                        }


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    //------------------------

                    //tags_unlike.remove("Unliked");
                }
            });
            viewHolder.UnlikeMe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //----------------
                    try {
                        String response = new My_Community_Posts_LikeOrUnlike(c, Config.NDS_LIKE_OR_DISLIKE_POST, "" + posts.getMy_Posts_Post_id(), Urubuga_Services.PlaceholderFragment.MYRemoteId, "unliked").execute().get();
                        //-------------------okay2017
                        if (response != null) {
                            if (response.length() >= 4) {
                                ///---
                                int UnLikes = Integer.parseInt(viewHolder.Unlikes.getText().toString().split("unlikes")[0].split(" ")[0]) + 1;

                                if (tags_unlike.get(position).equalsIgnoreCase("Unliked")) {
                                } else {
                                    viewHolder.Unlikes.setText(UnLikes + " unlikes");
                                }
                                viewHolder.UnlikeMe.setTag("Unliked");
                                tags_unlike.add(position, "Unliked");
                                viewHolder.UnlikeMe.setImageResource(R.drawable.dislike_active);
                                Toast.makeText(c, "Unliked!!", Toast.LENGTH_SHORT).show();
                                viewHolder.Likeme.setImageResource(R.drawable.like_me);

                            } else {
                                Toast.makeText(c, "Failed! No internet", Toast.LENGTH_SHORT).show();
                            }
                        }


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    //------------------------
                    //--

                    //tags.remove("liked");
                }
            });
            //-----------------
            if (tags.get(position) == "liked") {
                viewHolder.Likeme.setImageResource(R.drawable.like_me_active);
                viewHolder.UnlikeMe.setImageResource(R.drawable.dislike);

            } else {
                viewHolder.Likeme.setImageResource(R.drawable.like_me);
            }
            if (tags_unlike.get(position) == "Unliked") {
                viewHolder.UnlikeMe.setImageResource(R.drawable.dislike_active);
                viewHolder.Likeme.setImageResource(R.drawable.like_me);

            } else {
                viewHolder.UnlikeMe.setImageResource(R.drawable.dislike);
            }
            //--------------------------------------


        } else {
            TextView poster_name = (TextView) convertView.findViewById(R.id.poster_name);
            TextView TimeStamp = (TextView) convertView.findViewById(R.id.TimeStamp);
            TextView post_Data = (TextView) convertView.findViewById(R.id.post_Data);
            ImageView poster_Avatar = (ImageView) convertView.findViewById(R.id.poster_Avatar);
            TextView coments_ = (TextView) convertView.findViewById(R.id.coments_);
            TextView likes_ = (TextView) convertView.findViewById(R.id.likes_);
            TextView Dislikes = (TextView) convertView.findViewById(R.id.Dislikes);
            TextView views_ = (TextView) convertView.findViewById(R.id.views_);
            ImageView likeme = (ImageView) convertView.findViewById(R.id.likeme);
            likeme.setTag(position);
            //--- //-----------------------------------
            TextView myID = (TextView) convertView.findViewById(R.id.myID);
            TextView myID_Comments = (TextView) convertView.findViewById(R.id.myID_Comments);
            final My_Community_Posts posts = (My_Community_Posts) this.getItem(position);
            ///----------------
            TextView myID_Avatar = (TextView) convertView.findViewById(R.id.myID_Avatar);
            //--------
            myID_Avatar.setText(posts.getMy_Avatars());
            poster_name.setText(posts.getMy_Posts_Poster_name());
            //----------------
            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
            String date = df.format(Calendar.getInstance().getTime());
            TimeStamp.setText(posts.getMy_Posts_Regdate());
            //--------------------------
            post_Data.setText(posts.getMy_Posts_Poster_Post());
            My_Community_Posts_PicassoClient.downloadImage(c, posts.getMy_Posts_Avatar_URLs(), poster_Avatar);
            poster_Avatar.setTag(posts.getMy_Posts_Avatar_URLs());
            coments_.setText(posts.getMy_Posts_Post_Comments() + " Cmnts");
            likes_.setText("" + posts.getMy_Posts_like_status() + " likes");
            Dislikes.setText("" + posts.getMy_Posts_unlike_status() + " unlikes");
            views_.setText("" + posts.getMy_Posts_Views() + " Views");
            //-----------------------------------------------------------------------------
            myID.setText("" + posts.getMy_Posts_Post_id());
            //myID_Comments.setText(posts.getMy_Posts_Post_Comments_ForOtherUse());
            Urubuga_Services.AllComments = posts.getMy_Posts_Post_Comments_ForOtherUse();
            //--My_Posts_Poster_id--
            ImageView delete_this_post=(ImageView)  convertView.findViewById(R.id.delete_this_post);

            if(posts.getMy_Posts_Poster_id()==Integer.parseInt(Urubuga_Services.PlaceholderFragment.MYRemoteId)){
                delete_this_post.setVisibility(View.VISIBLE);
            }else{
                delete_this_post.setVisibility(View.GONE);
            }
            //----------------------------------------
            ImageView reportUse = (ImageView) convertView.findViewById(R.id.reportUse);
            ImageView reportUse_flag = (ImageView) convertView.findViewById(R.id.reportUse_flag);
            reportUse.setTag(posts.getMy_Posts_Post_id());
            reportUse_flag.setTag(posts.getMy_Posts_Post_id());
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.Likeme = (ImageView) convertView.findViewById(R.id.likeme);
            viewHolder.UnlikeMe = (ImageView) convertView.findViewById(R.id.imageView8);
            viewHolder.Likes = (TextView) convertView.findViewById(R.id.likes_);
            viewHolder.Unlikes = (TextView) convertView.findViewById(R.id.Dislikes);
            viewHolder.Likeme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //----------------
                    try {
                        String response = new My_Community_Posts_LikeOrUnlike(c, Config.NDS_LIKE_OR_DISLIKE_POST, "" + posts.getMy_Posts_Post_id(), Urubuga_Services.PlaceholderFragment.MYRemoteId, "liked").execute().get();
                        //-------------------okay2017
                        if (response != null) {
                            if (response.length() >= 4) {
                                //---
                                int Likes = Integer.parseInt(viewHolder.Likes.getText().toString().split("likes")[0].split(" ")[0]) + 1;
                                if (tags.get(position).equalsIgnoreCase("liked")) {

                                } else {
                                    viewHolder.Likes.setText(Likes + " likes");
                                }
                                viewHolder.Likeme.setTag("liked");
                                tags.add(position, "liked");
                                viewHolder.Likeme.setImageResource(R.drawable.like_me_active);
                                Toast.makeText(c, "Liked!", Toast.LENGTH_SHORT).show();
                                //------------
                                viewHolder.UnlikeMe.setImageResource(R.drawable.dislike);
                                //---


                            } else {
                                Toast.makeText(c, "Failed! No internet", Toast.LENGTH_SHORT).show();
                            }
                        }


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    //------------------------

                    //tags_unlike.remove("Unliked");
                }
            });
            viewHolder.UnlikeMe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //----------------
                    try {
                        String response = new My_Community_Posts_LikeOrUnlike(c, Config.NDS_LIKE_OR_DISLIKE_POST, "" + posts.getMy_Posts_Post_id(), Urubuga_Services.PlaceholderFragment.MYRemoteId, "unliked").execute().get();
                        //-------------------okay2017
                        if (response != null) {
                            if (response.length() >= 4) {
                                ///---
                                int UnLikes = Integer.parseInt(viewHolder.Unlikes.getText().toString().split("unlikes")[0].split(" ")[0]) + 1;

                                if (tags_unlike.get(position).equalsIgnoreCase("Unliked")) {
                                } else {
                                    viewHolder.Unlikes.setText(UnLikes + " unlikes");
                                }
                                viewHolder.UnlikeMe.setTag("Unliked");
                                tags_unlike.add(position, "Unliked");
                                viewHolder.UnlikeMe.setImageResource(R.drawable.dislike_active);
                                Toast.makeText(c, "Unliked!!", Toast.LENGTH_SHORT).show();
                                viewHolder.Likeme.setImageResource(R.drawable.like_me);

                            } else {
                                Toast.makeText(c, "Failed! No internet", Toast.LENGTH_SHORT).show();
                            }
                        }


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    //------------------------
                    //--

                    //tags.remove("liked");
                }
            });
            //-----------------
            //-----------------
            if (tags.get(position) == "liked") {
                viewHolder.Likeme.setImageResource(R.drawable.like_me_active);
                viewHolder.UnlikeMe.setImageResource(R.drawable.dislike);

            } else {
                viewHolder.Likeme.setImageResource(R.drawable.like_me);
            }
            if (tags_unlike.get(position) == "Unliked") {
                viewHolder.UnlikeMe.setImageResource(R.drawable.dislike_active);
                viewHolder.Likeme.setImageResource(R.drawable.like_me);

            } else {
                viewHolder.UnlikeMe.setImageResource(R.drawable.dislike);
            }
        }
        return convertView;
    }

    static class ViewHolder {
        public ImageView Likeme;
        public TextView Likes;
        public TextView Unlikes;
        public ImageView UnlikeMe;
        public int position;
    }


}

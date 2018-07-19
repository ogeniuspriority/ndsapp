package com.ogeniuspriority.nds.nds;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by USER on 9/10/2016.
 */

public class NDS_db_adapter {

    public static final String MYDATABASE_NDS_NAME = "NDS_internal_2017.db";
    //----USER CRED TABLE
    public static final int MYDATABASE_VERSION = 1;
    public static final String MYDATABASE_NDS_TABLE_USER_CREDS = "NDS_internal_2017_ogenius_nds_db_normal_users";
    public static final String MYDATABASE_NDS_TABLE_USER_CREDS_ID = "NDS_internal_2017_ogenius_nds_db_normal_users_id";
    public static final String MYDATABASE_NDS_TABLE_USER_CREDS_NAMES = "NDS_internal_2017_ogenius_nds_db_normal_users_names";
    public static final String MYDATABASE_NDS_TABLE_USER_CREDS_TEl = "NDS_internal_2017_ogenius_nds_db_normal_users_tel";
    public static final String MYDATABASE_NDS_TABLE_USER_CREDS_PASSWORD = "NDS_internal_2017_ogenius_nds_db_normal_users_password";
    public static final String MYDATABASE_NDS_TABLE_USER_CREDS_AVATAR = "NDS_internal_2017_ogenius_nds_db_normal_users_avatar";
    public static final String MYDATABASE_NDS_TABLE_USER_CREDS_REGDATE = "NDS_internal_2017_ogenius_nds_db_normal_users_regdate";
    public static final String MYDATABASE_NDS_TABLE_USER_CREDS_LONGITUDE = "NDS_internal_2017_ogenius_nds_db_normal_users_longitude";
    public static final String MYDATABASE_NDS_TABLE_USER_CREDS_LATITUDE = "NDS_internal_2017_ogenius_nds_db_normal_users_latitude";
    public static final String MYDATABASE_NDS_TABLE_USER_CREDS_PRIVILEDGE = "NDS_internal_2017_ogenius_nds_db_normal_users_priviledge";
    public static final String MYDATABASE_NDS_TABLE_USER_CREDS_ACTIVATION_CODE = "NDS_internal_2017_ogenius_nds_db_normal_users_activation_code";
    public static final String MYDATABASE_NDS_TABLE_USER_CREDS_REMOTE_ID = "NDS_internal_2017_ogenius_nds_db_normal_users_remote_id";
    public static final String MYDATABASE_NDS_TABLE_USER_CREDS_LOCATION_ADDRESS = "NDS_internal_2017_ogenius_nds_db_normal_users_location_address";
    public static final String MYDATABASE_NDS_TABLE_USER_CREDS_LOCATION_WORK_INFO = "NDS_internal_2017_ogenius_nds_db_normal_users_location_work_info";
    //---------------------

    //--------------------
    //create USERCRED DATABASE----------------
    private static final String SCRIPT_CREATE_NDS_TABLE_USER_CREDS =
            "create table " + MYDATABASE_NDS_TABLE_USER_CREDS + " ("
                    + MYDATABASE_NDS_TABLE_USER_CREDS_ID + " integer primary key autoincrement, "
                    + MYDATABASE_NDS_TABLE_USER_CREDS_NAMES + " text  ,"
                    + MYDATABASE_NDS_TABLE_USER_CREDS_LOCATION_ADDRESS + " text ,"
                    + MYDATABASE_NDS_TABLE_USER_CREDS_LOCATION_WORK_INFO + " text ,"
                    + MYDATABASE_NDS_TABLE_USER_CREDS_TEl + " text ,"
                    + MYDATABASE_NDS_TABLE_USER_CREDS_PASSWORD + " text ,"
                    + MYDATABASE_NDS_TABLE_USER_CREDS_AVATAR + " text ,"
                    + MYDATABASE_NDS_TABLE_USER_CREDS_REGDATE + " text ,"
                    + MYDATABASE_NDS_TABLE_USER_CREDS_LONGITUDE + " text ,"
                    + MYDATABASE_NDS_TABLE_USER_CREDS_LATITUDE + " text ,"
                    + MYDATABASE_NDS_TABLE_USER_CREDS_PRIVILEDGE + " text ,"
                    + MYDATABASE_NDS_TABLE_USER_CREDS_ACTIVATION_CODE + " text ,"
                    + MYDATABASE_NDS_TABLE_USER_CREDS_REMOTE_ID + " text );";
    //----------------Questions ----neza_rgb_query
    public static final String MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY = "NDS_internal_2017_ogenius_nds_db_neza_rgb_query";
    public static final String MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_ID = "NDS_internal_2017_ogenius_nds_db_neza_rgb_query_id";
    public static final String MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_REMOTE_ID = "NDS_internal_2017_ogenius_nds_db_neza_rgb_query_remote_id";
    public static final String MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY = "NDS_internal_2017_ogenius_nds_db_neza_rgb_query_query";
    public static final String MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_REGDATE = "NDS_internal_2017_ogenius_nds_db_neza_rgb_query_regdate";
    public static final String MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_OFFICIAL_ID = "NDS_internal_2017_ogenius_nds_db_neza_rgb_query_official_id";
    public static final String MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY_PROVIDER = "NDS_internal_2017_ogenius_nds_db_neza_rgb_query_provider_id";
    public static final String MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY_ORIENTATION = "NDS_internal_2017_ogenius_nds_db_neza_rgb_query_orientation";
    public static final String MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY_SENT = "NDS_internal_2017_ogenius_nds_db_neza_rgb_query_sent";

    //---------- neza_rgb_query SCRIPT CREATE
    private static final String SCRIPT_CREATE_NDS_TABLE_NEZA_RGB_QUERY =
            "create table " + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY + " ("
                    + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_ID + " integer primary key autoincrement, "
                    + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY_ORIENTATION + " text  ,"
                    + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_REMOTE_ID + " text  ,"
                    + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY_SENT + " text default 0 ,"
                    + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_REGDATE + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_OFFICIAL_ID + " text ,"
                    + MYDATABASE_NDS_TABLE_USER_CREDS_REGDATE + " text  unique ,"
                    + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY_PROVIDER + " text );";
    //----------------------neza_rgb_query_response ------------------------------
    public static final String MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE = "NDS_internal_2017_ogenius_nds_db_neza_rgb_query_response";
    public static final String MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_ID = "NDS_internal_2017_ogenius_nds_db_neza_rgb_query_response_id";
    public static final String MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_OFFICIAL_ID = "NDS_internal_2017_ogenius_nds_db_neza_rgb_query_response_official_id";
    public static final String MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_QUERY_ID = "NDS_internal_2017_ogenius_nds_db_neza_rgb_query_response_query_id";
    public static final String MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_RESPONSE = "NDS_internal_2017_ogenius_nds_db_neza_rgb_query_response_response";
    public static final String MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_OFFICIAL_NAMES = "NDS_internal_2017_ogenius_nds_db_neza_rgb_query_response_official_names";
    public static final String MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_REG_DATE = "NDS_internal_2017_ogenius_nds_db_neza_rgb_query_response_regdate";
    public static final String MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_WHO = "NDS_internal_2017_ogenius_nds_db_neza_rgb_query_response_who";
    public static final String MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_SENT = "NDS_internal_2017_ogenius_nds_db_neza_rgb_query_response_sent";
    public static final String MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_REMOTE_ID = "NDS_internal_2017_ogenius_nds_db_neza_rgb_query_response_remote_id";
    public static final String MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_QUERY_ORIENTATION = "NDS_internal_2017_ogenius_nds_db_neza_rgb_query_response_query_orientation";

    //-------------------neza_rgb_query_response SCRIPT
    private static final String SCRIPT_CREATE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE =
            "create table " + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE + " ("
                    + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_ID + " integer primary key autoincrement, "
                    + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_OFFICIAL_ID + " text  ,"
                    + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_REMOTE_ID + " text  ,"
                    + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_SENT + " text  ,"
                    + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_WHO + " text  ,"
                    + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_QUERY_ID + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_RESPONSE + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_OFFICIAL_ID + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_QUERY_ORIENTATION + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_OFFICIAL_NAMES + " text  ,"
                    + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_REG_DATE + " text unique );";
    //--------------------------------------
    //-------------------------Myposts handling------
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_ID = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_id";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REMOTE_ID = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_remote_id";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REGDATE = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_regdate";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_DATA = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_data";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_ID = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_poster_id";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_NAMES = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_poster_names";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POST_SENT = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_post_sent";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_STREAM = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_coments_stream";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_LIKES_COUNT = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_likes_count";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_UNLIKES_COUNT = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_unlikes_count";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_VIEWS_COUNT = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_views_count";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_AVATAR = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_views_avatar";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_COUNT = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_comments_count";

    //--------------------My posts scripts---
    private static final String SCRIPT_CREATE_NDS_TABLE_NEZA_COMMUNITY_POSTS =
            "create table " + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS + " ("
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_ID + " integer primary key autoincrement, "
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REMOTE_ID + " text default '0'  ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REGDATE + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_LIKES_COUNT + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_UNLIKES_COUNT + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_AVATAR + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_VIEWS_COUNT + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_COUNT + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_DATA + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_STREAM + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POST_SENT + " text default '0' ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_NAMES + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_ID + " text );";
    //--------------------------33333333333333333-----the posts when no network---33333333333333333333333333333333333333333333333333333333
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_NO_DATA = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_NO_DATA";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_ID_NO_DATA = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_id_NO_DATA";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REMOTE_ID_NO_DATA = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_remote_id_NO_DATA";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REGDATE_NO_DATA = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_regdate_NO_DATA";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_DATA_NO_DATA = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_data_NO_DATA";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_ID_NO_DATA = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_poster_id_NO_DATA";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_NAMES_NO_DATA = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_poster_names_NO_DATA";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POST_SENT_NO_DATA = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_post_sent_NO_DATA";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_STREAM_NO_DATA = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_coments_stream_NO_DATA";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_LIKES_COUNT_NO_DATA = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_likes_count_NO_DATA";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_UNLIKES_COUNT_NO_DATA = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_unlikes_count_NO_DATA";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_VIEWS_COUNT_NO_DATA = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_views_count_NO_DATA";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_AVATAR_NO_DATA = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_views_avatar_NO_DATA";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_COUNT_NO_DATA = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_comments_count_NO_DATA";

    //--------------------My posts scripts---
    private static final String SCRIPT_CREATE_NDS_TABLE_NEZA_COMMUNITY_POSTS_NO_DATA =
            "create table " + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_NO_DATA + " ("
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_ID_NO_DATA + " integer primary key autoincrement, "
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REMOTE_ID_NO_DATA + " text default '0'  ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REGDATE_NO_DATA + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_LIKES_COUNT_NO_DATA + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_UNLIKES_COUNT_NO_DATA + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_AVATAR_NO_DATA + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_VIEWS_COUNT_NO_DATA + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_COUNT_NO_DATA + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_DATA_NO_DATA + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_STREAM_NO_DATA + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POST_SENT_NO_DATA + " text default '0' ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_NAMES_NO_DATA + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_ID_NO_DATA + " text );";
    ///-------------------------------comments on community posts
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_comments";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_ID = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_comments_id";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_COMMENTATOR_NAME = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_comments_commentator_name";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_COMMENT = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_comments_comment";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_POSTS_POST_ID = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_comments_posts_post_id";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_REGDATE = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_comments_regdate";
    //----script of of comments on posts-
    private static final String SCRIPT_CREATE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS =
            "create table " + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS + " ("
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_ID + " integer primary key autoincrement, "
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_COMMENTATOR_NAME + " text  ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_COMMENT + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_POSTS_POST_ID + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_REGDATE + " text );";
    //------------------------POSTS FLAGS---------------------------------------------
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_flags";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS_ID = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_flags_id";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS_LIKE_STATUS = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_flags_like_status";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS_REPORT_FLAG = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_flags_report_flag";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS_COMMENT = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_flags_comment";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS_REGDATE = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_flags_regdate";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS_REMOTE_ID = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_flags_remote_id";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS_SENT = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_flags_sent";

    //-------------------------Posts flag--
    private static final String SCRIPT_CREATE_NDS_TABLE_MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS =
            "create table " + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS + " ("
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS_ID + " integer primary key autoincrement, "
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS_LIKE_STATUS + " text  ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS_REPORT_FLAG + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS_REMOTE_ID + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS_SENT + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS_COMMENT + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS_REGDATE + " text );";
    //----------------SUGGETION BOXES
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_suggetion_box";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_ID = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_suggetion_box_id";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_REMOTE_ID = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_suggetion_box_remote_id";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_AVATAR = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_suggetion_box_avatar";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_INSTITUTION_NAME = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_suggetion_box_intitution_name";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_MOTTO = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_posts_suggetion_box_motto";
    //---SUGGESTION BOXES SCRIPT CREATE-
    private static final String SCRIPT_CREATE_MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX =
            "create table " + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX + " ("
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_ID + " integer primary key autoincrement, "
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_REMOTE_ID + " text unique  ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_AVATAR + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_INSTITUTION_NAME + " text ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_MOTTO + " text );";
    //-------------Settings table-------------------
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_local_settings";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_ID = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_local_settings_id";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_SAVE_TO_GALLERY = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_local_settings_save_to_gallery";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_PLAY_SOUND = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_local_settings_play_sound";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_POP_UP_NOTIFY = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_local_settings_pop_up_notify";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_NETWORK_AUTO_DOWNLOAD_PREFERED_WIFI = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_local_settings_network_prefered_wifi";
    public static final String MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_NETWORK_AUTO_DOWNLOAD_PREFERED_MOBILE_DATA = "NDS_internal_2017_ogenius_nds_db_neza_rgb_community_local_settings_network_prefered_mobile_data";
    //--
    private static final String SCRIPT_CREATE_MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS =
            "create table " + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS + " ("
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_ID + " integer primary key autoincrement, "
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_SAVE_TO_GALLERY + " text default '1' ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_PLAY_SOUND + " text default '1',"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_POP_UP_NOTIFY + " text default '1' ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_NETWORK_AUTO_DOWNLOAD_PREFERED_MOBILE_DATA + " text default '1' ,"
                    + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_NETWORK_AUTO_DOWNLOAD_PREFERED_WIFI + " text default '1' );";
    //-------------Settings table-------------------
    public static final String MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE = "NDS_internal_2017_ogenius_nds_db_neza_rgb_notifications";
    public static final String MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_NOTIF_NUMBER = "NDS_internal_2017_ogenius_nds_db_neza_rgb_notifications_notif_number";
    public static final String MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TITLE = "NDS_internal_2017_ogenius_nds_db_neza_rgb_notifications_title";
    public static final String MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_MESSAGE = "NDS_internal_2017_ogenius_nds_db_neza_rgb_notifications_message";
    public static final String MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ORIGIN_REMOTE_ID = "NDS_internal_2017_ogenius_nds_db_neza_rgb_notifications_origin_remote_id";
    public static final String MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_IMAGE_URL = "NDS_internal_2017_ogenius_nds_db_neza_rgb_notifications_image_url";
    public static final String MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_BIG_TEXT = "NDS_internal_2017_ogenius_nds_db_neza_rgb_notifications_big_text";
    public static final String MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_REGDATE = "NDS_internal_2017_ogenius_nds_db_neza_rgb_notifications_regdate";
    public static final String MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_SUMMARY = "NDS_internal_2017_ogenius_nds_db_neza_rgb_notifications_summary";
    public static final String MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ACTIVATION_TAG = "NDS_internal_2017_ogenius_nds_db_neza_rgb_notifications_activation_tag";
    public static final String MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ID = "NDS_internal_2017_ogenius_nds_db_neza_rgb_notifications_id";
    public static final String MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TYPE = "NDS_internal_2017_ogenius_nds_db_neza_rgb_notifications_type";
    public static final String MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ORIENTATION_Q_S = "NDS_internal_2017_ogenius_nds_db_neza_rgb_notifications_orientation_q_s";

    //--
    private static final String SCRIPT_CREATE_MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS =
            "create table " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE + " ("
                    + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ID + " integer primary key autoincrement, "
                    + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_NOTIF_NUMBER + " text  ,"
                    + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TITLE + " text ,"
                    + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_MESSAGE + " text  ,"
                    + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ORIGIN_REMOTE_ID + " text  ,"
                    + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_IMAGE_URL + " text  ,"
                    + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_BIG_TEXT + " text  ,"
                    + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_REGDATE + " text  ,"
                    + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_SUMMARY + " text  ,"
                    + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ACTIVATION_TAG + " text default '1' ,"
                    + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ORIENTATION_Q_S + " text  ,"
                    + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TYPE + " text  );";

    ///-----------------------------------------------------------------------
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;

    private Context context;

    public NDS_db_adapter(Context c) {
        context = c;
    }

    public NDS_db_adapter openToRead() throws android.database.SQLException {
        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NDS_NAME, null, MYDATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
        return this;
    }

    public NDS_db_adapter openToWrite() throws android.database.SQLException {
        try {
            sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NDS_NAME, null, MYDATABASE_VERSION);
            sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        }catch (SQLException ggd){

        }
        return this;
    }

    public void close() {
        sqLiteHelper.close();
    }

    public int deleteAllFromCreds() {
        return sqLiteDatabase.delete(MYDATABASE_NDS_TABLE_USER_CREDS, null, null);
    }

    public int deleteAllFrom_neza_community_posts_no_data() {
        return sqLiteDatabase.delete(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_NO_DATA, null, null);
    }

    public int deleteAllInternal_NOTIFICATIONS() {
        return sqLiteDatabase.delete(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE, null, null);
    }

    public int deleteAllFrom_Query() {
        return sqLiteDatabase.delete(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY, null, null);
    }

    public int deleteAllFrom_Query_response() {
        return sqLiteDatabase.delete(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE, null, null);
    }

    //---delete a suggestion box
    public boolean DELETE_A_POST_FROM_NEZA_COMMUNITY_SUGGESTION_BOX(String remoteId) {

        return sqLiteDatabase.delete(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_REMOTE_ID + "=" + remoteId, null) > 0;
    }
    //-----------------SAVE A SUGGESTION BOX LOCALLY

    public boolean save_NEZA_COMMUNITY_SUGGESTION_BOX_locally(String Remote_suggestion_box_id, String Suggestion_box_avatar,
                                                              String Suggestion_box_institution_name, String Suggestion_box_motto
    ) throws SQLiteDatabaseCorruptException {
        //-------------
        //---
        ContentValues contentValues = new ContentValues();
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_REMOTE_ID, Remote_suggestion_box_id);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_AVATAR, Suggestion_box_avatar);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_INSTITUTION_NAME, Suggestion_box_institution_name);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_MOTTO, Suggestion_box_motto);

        return sqLiteDatabase.insertOrThrow(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX, null, contentValues) > 0;

    }

    //---Save default--settings
    public boolean create_NDS_app_default_settings(String saveToGallery, String Play_sound,
                                                   String popUpNotify, String MobileData,
                                                   String Wifi
    ) throws SQLiteDatabaseCorruptException {
        //-------------
        //---
        ContentValues contentValues = new ContentValues();
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_SAVE_TO_GALLERY, saveToGallery);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_PLAY_SOUND, Play_sound);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_POP_UP_NOTIFY, popUpNotify);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_NETWORK_AUTO_DOWNLOAD_PREFERED_MOBILE_DATA, MobileData);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_NETWORK_AUTO_DOWNLOAD_PREFERED_WIFI, Wifi);

        return sqLiteDatabase.insertOrThrow(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS, null, contentValues) > 0;

    }

    //------------------Get all settings----
    public Cursor GET_ALL_NDS_APP_LOCAL_SETTINGS() {
        String[] columns = new String[]{MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_ID,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_SAVE_TO_GALLERY, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_PLAY_SOUND,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_POP_UP_NOTIFY, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_NETWORK_AUTO_DOWNLOAD_PREFERED_MOBILE_DATA,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_NETWORK_AUTO_DOWNLOAD_PREFERED_WIFI
        };
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS, columns,
                "7>2" + " order by  " + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_ID + " desc limit 1", null, null, null, null);

        return cursor;
    }

    //---Update my local settings good
    public boolean Update_ALL_NDS_APP_LOCAL_SETTINGS(String localSettingsId, String whichField, String value) {
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        ContentValues contentValues = new ContentValues();
        //---
        if (whichField.equalsIgnoreCase("playsound")) {
            contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_PLAY_SOUND, value);
        } else if (whichField.equalsIgnoreCase("popupnotify")) {
            contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_POP_UP_NOTIFY, value);
        } else if (whichField.equalsIgnoreCase("savetogallery")) {
            contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_SAVE_TO_GALLERY, value);
        } else if (whichField.equalsIgnoreCase("wifidata")) {
            contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_NETWORK_AUTO_DOWNLOAD_PREFERED_WIFI, value);
        } else if (whichField.equalsIgnoreCase("mobiledata")) {
            contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_NETWORK_AUTO_DOWNLOAD_PREFERED_MOBILE_DATA, value);
        }


        //sqLiteDatabase.update(MYDATABASE_BOO_TABLE_USER_CREDS, contentValues, MYDATABASE_BOO_TABLE_USER_CREDS_ID + "=" + LastId, null);
        return sqLiteDatabase.update(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS, contentValues, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_ID + "=" + localSettingsId, null) > 0;

    }

    //----
    public boolean save_ALL_NDS_APP_LOCAL_SETTINGS(String localSettingsId, String whichField, String value) {
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        ContentValues contentValues = new ContentValues();
        //---
        if (whichField.equalsIgnoreCase("playsound")) {
            contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_PLAY_SOUND, value);
        }


        //sqLiteDatabase.update(MYDATABASE_BOO_TABLE_USER_CREDS, contentValues, MYDATABASE_BOO_TABLE_USER_CREDS_ID + "=" + LastId, null);
        return sqLiteDatabase.insert(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS, null, contentValues) > 0;
    }


    //-----select a suggestion Box
    public Cursor GET_ALL_NEZA_COMMUNITY_SUGGESTION_BOXES() {
        String[] columns = new String[]{MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_REMOTE_ID,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_ID, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_AVATAR,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_INSTITUTION_NAME, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_MOTTO
        };
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX, columns,
                "" + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_REMOTE_ID + " is not null" + " order by  " + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_ID + " desc", null, null, null, null);

        return cursor;
    }

    //-----------delete query from id=====
    public boolean DELETE_A_QUERY_FROM_LOCAL_DATABASE(String remoteId) {

        return sqLiteDatabase.delete(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY, MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_REMOTE_ID + "=" + remoteId, null) > 0;
    }

    //-----------delete query from id=====
    public boolean DELETE_A_QUERY_RESPONSE_FROM_LOCAL_DATABASE(String remoteId) {
        if (!remoteId.isEmpty()) {
            return sqLiteDatabase.delete(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE
                    , MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_REMOTE_ID + "=" + remoteId, null) > 0;
        } else {
            return false;
        }
    }


    //---------
    public Cursor GET_ALL_NEZA_COMMUNITY_SUGGESTION_BOXES_INSTITUTE(String nameInstiTution) {
        String[] columns = new String[]{MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_REMOTE_ID,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_ID, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_AVATAR,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_INSTITUTION_NAME, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_MOTTO
        };
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX, columns,
                " " + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_INSTITUTION_NAME + " =\"" + nameInstiTution + "\" order by  " + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_ID + " desc", null, null, null, null);

        return cursor;
    }

    public Cursor GET_ALL_NEZA_COMMUNITY_SUGGESTION_BOXES_INSTITUTE_ID(String nameInstiTution) {
        String[] columns = new String[]{MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_REMOTE_ID,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_ID, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_AVATAR,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_INSTITUTION_NAME, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_MOTTO
        };
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX, columns,
                " " + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_REMOTE_ID + " =\"" + nameInstiTution + "\" order by  " + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_ID + " desc", null, null, null, null);

        return cursor;
    }

    public Cursor GET_ALL_NEZA_COMMUNITY_SUGGESTION_BOXES_INSTITUTE_LIKE(String nameInstiTution) {
        String[] columns = new String[]{MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_REMOTE_ID,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_ID, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_AVATAR,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_INSTITUTION_NAME, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_MOTTO
        };
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX, columns,
                " " + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_INSTITUTION_NAME + " like '%" + nameInstiTution + "%' order by  " + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX_ID + " desc", null, null, null, null);

        return cursor;
    }

    //-----------------select my User Creds------
    public Cursor GET_ALL_NEZA_COMMUNITY_POSTS() {
        String[] columns = new String[]{MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_ID, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REMOTE_ID,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REGDATE
                , MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_DATA, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POST_SENT,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_NAMES, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_ID,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_STREAM, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_VIEWS_COUNT,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_UNLIKES_COUNT, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_LIKES_COUNT,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_AVATAR, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_COUNT
        };
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS, columns,
                "" + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REMOTE_ID + " is not null" + " order by  " + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_ID + " desc", null, null, null, null);

        return cursor;
    }

    public Cursor GET_ALL_NEZA_COMMUNITY_POSTS_NO_DATA() {
        String[] columns = new String[]{MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_ID_NO_DATA, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REMOTE_ID_NO_DATA,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REGDATE_NO_DATA
                , MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_DATA_NO_DATA, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POST_SENT_NO_DATA,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_NAMES_NO_DATA, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_ID_NO_DATA,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_STREAM_NO_DATA, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_VIEWS_COUNT_NO_DATA,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_UNLIKES_COUNT_NO_DATA, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_LIKES_COUNT_NO_DATA,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_AVATAR_NO_DATA, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_COUNT_NO_DATA
        };
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_NO_DATA, columns,
                "" + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REMOTE_ID_NO_DATA + " is not null" + " order by  " + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_ID_NO_DATA + " desc", null, null, null, null);

        return cursor;
    }

    public Cursor GET_A_PARTICULA_NEZA_COMMUNITY_POSTS(String remoteId) {
        String[] columns = new String[]{MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_ID, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REMOTE_ID,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REGDATE
                , MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_DATA, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POST_SENT,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_NAMES, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_ID,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_STREAM, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_VIEWS_COUNT,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_UNLIKES_COUNT, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_LIKES_COUNT
        };
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS, columns,
                "" + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REMOTE_ID + "=" + remoteId + " order by  " + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_ID + " desc", null, null, null, null);

        return cursor;
    }


    public boolean DELETE_A_POST_FROM_NEZA_COMMUNITY_POSTS(String remoteId) {

        return sqLiteDatabase.delete(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REMOTE_ID + "=" + remoteId, null) > 0;
    }

    public boolean DELETE_All_incomplete_POST_FROM_NEZA_COMMUNITY_POSTS() {

        return sqLiteDatabase.delete(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REMOTE_ID + " is null", null) > 0;
    }
    ///--------------

    public boolean Update_NEZA_COMMUNITY_POSTS_LOCALLY(String Remote_Post_id, String Post_Regdate,
                                                       String post_data, String poster_names,
                                                       String poster_id, String commentsStream,
                                                       String ViewsCount, String UnlikeCount,
                                                       String likeCount, String Avatar, String commentsCount) {
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());

        ContentValues contentValues = new ContentValues();
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REMOTE_ID, Remote_Post_id);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REGDATE, Post_Regdate);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_DATA, post_data);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_NAMES, poster_names);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_ID, poster_id);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_STREAM, commentsStream);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_VIEWS_COUNT, ViewsCount);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_UNLIKES_COUNT, UnlikeCount);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_LIKES_COUNT, likeCount);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_LIKES_COUNT, likeCount);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_AVATAR, Avatar);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_COUNT, commentsCount);


        //sqLiteDatabase.update(MYDATABASE_BOO_TABLE_USER_CREDS, contentValues, MYDATABASE_BOO_TABLE_USER_CREDS_ID + "=" + LastId, null);
        return sqLiteDatabase.update(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS, contentValues, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REMOTE_ID + "=" + Remote_Post_id, null) > 0;

    }
    //-----


    public boolean save_NEZA_COMMUNITY_POSTS_locally(String Remote_Post_id, String Post_Regdate,
                                                     String post_data, String poster_names,
                                                     String poster_id, String commentsStream,
                                                     String ViewsCount, String UnlikeCount,
                                                     String likeCount, String Avatar, String commentsCount
    ) throws SQLiteDatabaseCorruptException {
        //-------------
        //---
        ContentValues contentValues = new ContentValues();
        //contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_ID, remoteIdAdvert);
        //contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_ID, officialId);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REMOTE_ID, Remote_Post_id);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REGDATE, Post_Regdate);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_DATA, post_data);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_NAMES, poster_names);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_ID, poster_id);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_STREAM, commentsStream);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_VIEWS_COUNT, ViewsCount);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_UNLIKES_COUNT, UnlikeCount);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_LIKES_COUNT, likeCount);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_LIKES_COUNT, likeCount);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_AVATAR, Avatar);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_COUNT, commentsCount);
        return sqLiteDatabase.insert(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS, null, contentValues) > 0;

    }

    public boolean save_NEZA_COMMUNITY_POSTS_locally_NO_DATA(String Remote_Post_id, String Post_Regdate,
                                                             String post_data, String poster_names,
                                                             String poster_id, String commentsStream,
                                                             String ViewsCount, String UnlikeCount,
                                                             String likeCount, String Avatar, String commentsCount
    ) throws SQLiteDatabaseCorruptException {
        //-------------
        //---
        ContentValues contentValues = new ContentValues();
        //contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_ID, remoteIdAdvert);
        //contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_ID, officialId);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REMOTE_ID_NO_DATA, Remote_Post_id);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REGDATE_NO_DATA, Post_Regdate);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_DATA_NO_DATA, post_data);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_NAMES_NO_DATA, poster_names);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_ID_NO_DATA, poster_id);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_STREAM_NO_DATA, commentsStream);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_VIEWS_COUNT_NO_DATA, ViewsCount);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_UNLIKES_COUNT_NO_DATA, UnlikeCount);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_LIKES_COUNT_NO_DATA, likeCount);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_LIKES_COUNT_NO_DATA, likeCount);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_AVATAR_NO_DATA, Avatar);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_COUNT_NO_DATA, commentsCount);
        return sqLiteDatabase.insert(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_NO_DATA, null, contentValues) > 0;

    }


    //------------Update each piece of data from user creds
    public boolean Update_NDS_USER_CRED(String localUserCredRemoteId, String whichField, String value) {
        ContentValues contentValues = new ContentValues();
        //---
        if (whichField.equalsIgnoreCase("avatar")) {
            contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_AVATAR, value);
        } else if (whichField.equalsIgnoreCase("tel_no")) {
            contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_TEl, value);
        } else if (whichField.equalsIgnoreCase("savetogallery")) {
            contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS_SAVE_TO_GALLERY, value);
        } else if (whichField.equalsIgnoreCase("location_info")) {
            contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_LOCATION_ADDRESS, value);
        } else if (whichField.equalsIgnoreCase("work_address")) {
            contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_LOCATION_WORK_INFO, value);
        } else if (whichField.equalsIgnoreCase("names")) {
            contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_NAMES, value);
        } else if (whichField.equalsIgnoreCase("number_00")) {
            contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_NAMES, value);
        }


        //sqLiteDatabase.update(MYDATABASE_BOO_TABLE_USER_CREDS, contentValues, MYDATABASE_BOO_TABLE_USER_CREDS_ID + "=" + LastId, null);
        return sqLiteDatabase.update(MYDATABASE_NDS_TABLE_USER_CREDS, contentValues, MYDATABASE_NDS_TABLE_USER_CREDS_REMOTE_ID + "=" + localUserCredRemoteId, null) > 0;

    }


    public Cursor queueAllFromCreds() {
        String[] columns = new String[]{MYDATABASE_NDS_TABLE_USER_CREDS_ID, MYDATABASE_NDS_TABLE_USER_CREDS_NAMES,
                MYDATABASE_NDS_TABLE_USER_CREDS_TEl
                , MYDATABASE_NDS_TABLE_USER_CREDS_AVATAR, MYDATABASE_NDS_TABLE_USER_CREDS_REGDATE,
                MYDATABASE_NDS_TABLE_USER_CREDS_LONGITUDE, MYDATABASE_NDS_TABLE_USER_CREDS_LATITUDE,
                MYDATABASE_NDS_TABLE_USER_CREDS_PRIVILEDGE, MYDATABASE_NDS_TABLE_USER_CREDS_ACTIVATION_CODE,
                MYDATABASE_NDS_TABLE_USER_CREDS_REMOTE_ID, MYDATABASE_NDS_TABLE_USER_CREDS_LOCATION_ADDRESS,
                MYDATABASE_NDS_TABLE_USER_CREDS_LOCATION_WORK_INFO

        };
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_USER_CREDS, columns,
                " 7>2 order by " + MYDATABASE_NDS_TABLE_USER_CREDS_ID + " desc limit 1", null, null, null, null);

        return cursor;
    }

    //-----------------------------
    public Cursor queueAllFromCreds_where(String whereID) {
        String[] columns = new String[]{MYDATABASE_NDS_TABLE_USER_CREDS_ID, MYDATABASE_NDS_TABLE_USER_CREDS_NAMES,
                MYDATABASE_NDS_TABLE_USER_CREDS_TEl
                , MYDATABASE_NDS_TABLE_USER_CREDS_AVATAR, MYDATABASE_NDS_TABLE_USER_CREDS_REGDATE,
                MYDATABASE_NDS_TABLE_USER_CREDS_LONGITUDE, MYDATABASE_NDS_TABLE_USER_CREDS_LATITUDE,
                MYDATABASE_NDS_TABLE_USER_CREDS_PRIVILEDGE, MYDATABASE_NDS_TABLE_USER_CREDS_ACTIVATION_CODE,
                MYDATABASE_NDS_TABLE_USER_CREDS_REMOTE_ID, MYDATABASE_NDS_TABLE_USER_CREDS_LOCATION_ADDRESS,
                MYDATABASE_NDS_TABLE_USER_CREDS_LOCATION_WORK_INFO
        };
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_USER_CREDS, columns, "  " + MYDATABASE_NDS_TABLE_USER_CREDS_REMOTE_ID + "=" +
                whereID + "  order by " + MYDATABASE_NDS_TABLE_USER_CREDS_ID + " desc limit 1", null, null, null, null);

        return cursor;
    }
    //====SELECT ALL QUERIES RESPONSE--

    public Cursor queueAllQueries_responses(String whereQuery_remoteId, String orient) {
        String[] columns = new String[]{MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_ID, MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_OFFICIAL_ID,
                MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_QUERY_ID
                , MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_RESPONSE, MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_WHO,
                MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_OFFICIAL_NAMES, MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_REG_DATE,
                MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_REMOTE_ID
        };
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE, columns,
                " " + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_QUERY_ID + " = '" + whereQuery_remoteId + "' AND "
                        + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_QUERY_ORIENTATION + " = '" + orient + "' "
                , null, null, null, null);

        return cursor;
    }

    //-----------------------
    public Cursor GET_ALL_SAVED_AND_SENT_NEZA_COMMUNITY_POSTS() {
        String[] columns = new String[]{MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_ID, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REMOTE_ID,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REGDATE
                , MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_DATA, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POST_SENT,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_NAMES, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_ID
        };
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS, columns,
                " " + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POST_SENT + " = '" + 1 + " ", null, null, null, null);

        return cursor;
    }

    //-----------------
    public Cursor GET_ALL_UN_SENT_NEZA_COMMUNITY_POSTS() {
        String[] columns = new String[]{MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_ID, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REMOTE_ID,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REGDATE
                , MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_DATA, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POST_SENT,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_NAMES, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_ID
        };
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS, columns,
                " " + MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POST_SENT + " ='0'  ", null, null, null, null);

        return cursor;
    }

    //---------------select all from queries--
    public Cursor queueAllFromNDS_RGB_QUERIES() {
        String[] columns = new String[]{MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_ID, MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_REMOTE_ID,
                MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY
                , MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_REGDATE, MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_OFFICIAL_ID,
                MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY_PROVIDER, MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY_ORIENTATION,
                MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY_SENT
        };
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY, columns,
                " 1<3 GROUP BY " + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_REGDATE + " ORDER BY " + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_ID + " ASC", null, null, null, null);

        return cursor;
    }

    //-------------where query id is
    public Cursor queueAllFromNDS_RGB_QUERIES_Where_query_id(String query_id) {
        String[] columns = new String[]{MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_ID, MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_REMOTE_ID,
                MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY
                , MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_REGDATE, MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_OFFICIAL_ID,
                MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY_PROVIDER, MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY_ORIENTATION,
                MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY_SENT
        };
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY, columns,
                " " + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_REMOTE_ID + "='" + query_id + "' GROUP BY " + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_REGDATE + " ORDER BY " + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_ID + " ASC", null, null, null, null);

        return cursor;
    }

    //---------------Find the
    public Cursor queueAllFromNDS_RGB_QUERIES_suggestions() {
        String[] columns = new String[]{MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_ID, MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_REMOTE_ID,
                MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY
                , MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_REGDATE, MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_OFFICIAL_ID,
                MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY_PROVIDER, MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY_ORIENTATION,
                MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY_SENT
        };
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY, columns,
                " " + MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY_ORIENTATION + " ='suggestion'  ", null, null, null, null);

        return cursor;
    }

    public Cursor queueAll_NEZA_COMMUNITY_POSTS_COMMENTS() {
        String[] columns = new String[]{MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_ID, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_COMMENTATOR_NAME,
                MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_COMMENT
                , MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_POSTS_POST_ID, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_REGDATE
        };
        Cursor cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS, columns,
                null, null, null, null, null);

        return cursor;
    }

    //----------
    public boolean Update_NEZA_COMMUNITY_POSTS_FLAGS_locally_locally(String queryRemoteId, String local_post_id) {
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());

        ContentValues contentValues = new ContentValues();
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS_LIKE_STATUS, queryRemoteId);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS_REMOTE_ID, queryRemoteId);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS_SENT, "1");


        //sqLiteDatabase.update(MYDATABASE_BOO_TABLE_USER_CREDS, contentValues, MYDATABASE_BOO_TABLE_USER_CREDS_ID + "=" + LastId, null);
        return sqLiteDatabase.update(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS, contentValues, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS_ID + "=" + local_post_id, null) > 0;

    }

    //---posts flags--
    public boolean save_NEZA_COMMUNITY_POSTS_FLAGS_locally(String Like_status, String report_flag,
                                                           String comment, String regdate) throws SQLiteDatabaseCorruptException {      //-------------
        //---
        ContentValues contentValues = new ContentValues();
        //contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_ID, remoteIdAdvert);
        //contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_ID, officialId);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS_LIKE_STATUS, Like_status);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS_REPORT_FLAG, report_flag);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS_COMMENT, comment);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS_REGDATE, regdate);
        return sqLiteDatabase.insert(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS, null, contentValues) > 0;

    }

    //---------------------Postsmenu
    public boolean save_NEZA_COMMUNITY_POSTS_COMMENTS_locally(String Commentator_name, String Comment,
                                                              String post_id, String regdate) throws SQLiteDatabaseCorruptException {
        //-------------
        //---
        ContentValues contentValues = new ContentValues();
        //contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_ID, remoteIdAdvert);
        //contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_ID, officialId);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_COMMENTATOR_NAME, Commentator_name);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_COMMENT, Comment);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_POSTS_POST_ID, post_id);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS_REGDATE, regdate);
        return sqLiteDatabase.insert(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS, null, contentValues) > 0;

    }


    //---save my user RESPONSES locally
    public boolean save_NEZA_COMMUNITY_POSTS_locally(String Remote_Post_id, String Post_Regdate,
                                                     String post_data, String poster_names,
                                                     String poster_id) throws SQLiteDatabaseCorruptException {
        //-------------
        //---
        ContentValues contentValues = new ContentValues();
        //contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_ID, remoteIdAdvert);
        //contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_ID, officialId);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REMOTE_ID, Remote_Post_id);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REGDATE, Post_Regdate);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_DATA, post_data);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_NAMES, poster_names);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POSTER_ID, poster_id);
        return sqLiteDatabase.insert(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS, null, contentValues) > 0;

    }

    //-------------------------
    public boolean Update_NEZA_COMMUNITY_POSTS_locally(String queryRemoteId, String local_post_id) {
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());

        ContentValues contentValues = new ContentValues();
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REMOTE_ID, queryRemoteId);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_REGDATE, date);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_POST_SENT, "1");


        //sqLiteDatabase.update(MYDATABASE_BOO_TABLE_USER_CREDS, contentValues, MYDATABASE_BOO_TABLE_USER_CREDS_ID + "=" + LastId, null);
        return sqLiteDatabase.update(MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS, contentValues, MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_ID + "=" + local_post_id, null) > 0;

    }

    ///------------------------------------------------------------------------
    //---update responses table
    public boolean Update_NDS_RGB_QUERIES_RESPONSES(String queryRemoteId, String queryId) {
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());

        ContentValues contentValues = new ContentValues();
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_REMOTE_ID, queryRemoteId);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_REGDATE, date);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY_SENT, "1");
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY_ORIENTATION, "query");


        //sqLiteDatabase.update(MYDATABASE_BOO_TABLE_USER_CREDS, contentValues, MYDATABASE_BOO_TABLE_USER_CREDS_ID + "=" + LastId, null);
        return sqLiteDatabase.update(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY, contentValues, MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_ID + "=" + queryId, null) > 0;

    }

    //---save my user RESPONSES locally
    public boolean save_NDS_RGB_QUERIES_RESPONSES_locally(String officialId, String query_id,
                                                          String response, String query_remote_id,
                                                          String regdate, String names, String queryOrientation) throws SQLiteDatabaseCorruptException {
        //-------------
        //---
        ContentValues contentValues = new ContentValues();
        //contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_ID, remoteIdAdvert);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_OFFICIAL_ID, officialId);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_QUERY_ID, query_id);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_RESPONSE, response);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_OFFICIAL_NAMES, names);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_REMOTE_ID, query_remote_id);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_REG_DATE, regdate);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_SENT, "1");
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_QUERY_ORIENTATION, queryOrientation);


        return sqLiteDatabase.insert(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE, null, contentValues) > 0;

    }

    //----------------------
    public boolean save_NDS_RGB_QUERIES_RESPONSES_locally_on_send(String officialId, String query_id,
                                                                  String response, String query_remote_id,
                                                                  String regdate, String names, String queryOrientation) throws SQLiteDatabaseCorruptException {
        //-------------
        //---
        ContentValues contentValues = new ContentValues();
        //contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_ID, remoteIdAdvert);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_OFFICIAL_ID, officialId);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_QUERY_ID, query_id);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_RESPONSE, response);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_OFFICIAL_NAMES, names);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_REMOTE_ID, query_remote_id);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_REG_DATE, regdate);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_SENT, "1");
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE_QUERY_ORIENTATION, queryOrientation);


        return sqLiteDatabase.insert(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE, null, contentValues) > 0;

    }
    //-------------------------------

    public boolean save_NDS_RGB_QUERIES_locally(String remote_id, String query,
                                                String regdate, String official_id, String query_provider,
                                                String query_orientation
    ) throws SQLiteDatabaseCorruptException {
        //-------------
        //---
        ContentValues contentValues = new ContentValues();
        //contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_ID, remoteIdAdvert);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_REMOTE_ID, remote_id);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY, query);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_REGDATE, regdate);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_OFFICIAL_ID, official_id);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY_PROVIDER, query_provider);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY_ORIENTATION, query_orientation);

        return sqLiteDatabase.insert(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY, null, contentValues) > 0;

    }

    //------------------The
    public boolean save_NDS_SUGGESTIONS_locally(String remote_id, String query,
                                                String regdate, String official_id, String query_provider,
                                                String query_data_type
    ) throws SQLiteDatabaseCorruptException {
        //-------------
        //---
        ContentValues contentValues = new ContentValues();
        //contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_ID, remoteIdAdvert);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_REMOTE_ID, remote_id);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY, query);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_REGDATE, regdate);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_OFFICIAL_ID, official_id);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY_PROVIDER, query_provider);
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY_ORIENTATION, "suggestion");
        contentValues.put(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY_QUERY_SENT, "1");

        return sqLiteDatabase.insert(MYDATABASE_NDS_TABLE_NEZA_RGB_QUERY, null, contentValues) > 0;

    }

    //---save my user creds locally
    public boolean save_cred_locally(String names, String tel,
                                     String avatar, String regdate,
                                     String priviledge, String activation_code,
                                     String remoteid) throws SQLiteDatabaseCorruptException {
        //-------------
        //---
        ContentValues contentValues = new ContentValues();
        //contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_ID, remoteIdAdvert);
        contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_NAMES, names);
        contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_TEl, tel);
        contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_AVATAR, avatar);
        contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_REGDATE, regdate);
        contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_PRIVILEDGE, priviledge);
        contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_ACTIVATION_CODE, activation_code);
        contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_REMOTE_ID, remoteid);

        return sqLiteDatabase.insert(MYDATABASE_NDS_TABLE_USER_CREDS, null, contentValues) > 0;

    }
    public boolean save_cred_locally_all(String names, String tel,
                                     String avatar, String regdate,
                                     String priviledge, String activation_code,
                                     String remoteid) throws SQLiteDatabaseCorruptException {
        //-------------
        //---
        ContentValues contentValues = new ContentValues();
        //contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_ID, remoteIdAdvert);
        contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_NAMES, names);
        contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_TEl, tel);
        contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_AVATAR, avatar);
        contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_REGDATE, regdate);
        contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_PRIVILEDGE, priviledge);
        contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_ACTIVATION_CODE, activation_code);
        contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_REMOTE_ID, remoteid);

        return sqLiteDatabase.insert(MYDATABASE_NDS_TABLE_USER_CREDS, null, contentValues) > 0;

    }

    //-----------------------------
    public boolean save_A_NOTIFICATION(String notifnber, String notifTitle,
                                       String notifMsg ,
                                       String imageURL, String bigText,
                                       String regdate, String summary
            , String notifOriginRemoteId, String notifType,String notifOrient) throws SQLiteDatabaseCorruptException {
        //-------------
        //---
        ContentValues contentValues = new ContentValues();
        //contentValues.put(MYDATABASE_NDS_TABLE_USER_CREDS_ID, remoteIdAdvert);
        contentValues.put(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_NOTIF_NUMBER, notifnber);
        contentValues.put(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TITLE, notifTitle);
        contentValues.put(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_MESSAGE, notifMsg);
        contentValues.put(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_IMAGE_URL, imageURL);
        contentValues.put(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_BIG_TEXT, bigText);
        contentValues.put(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_REGDATE, regdate);
        contentValues.put(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_SUMMARY, summary);
        contentValues.put(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ORIGIN_REMOTE_ID, notifOriginRemoteId);
        contentValues.put(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TYPE, notifType);
        contentValues.put(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ORIENTATION_Q_S, notifOrient);

        return sqLiteDatabase.insert(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE, null, contentValues) > 0;
    }

    //------------------------------------
    public boolean Update_APP_NOTIFICATION_TO_SEEN(String origin_remote_id, String whichTypeOfNotification) {
        ContentValues contentValues = new ContentValues();
        //---
        contentValues.put(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ACTIVATION_TAG, "0");
        //sqLiteDatabase.update(MYDATABASE_BOO_TABLE_USER_CREDS, contentValues, MYDATABASE_BOO_TABLE_USER_CREDS_ID + "=" + LastId, null);
        return sqLiteDatabase.update(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE, contentValues, MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ORIGIN_REMOTE_ID + "='" + origin_remote_id + "' AND " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TYPE + " LIKE '%" + whichTypeOfNotification + "%' "+ " AND  " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ACTIVATION_TAG + " ='1'  ", null) > 0;

    }
    public boolean Update_APP_NOTIFICATION_TO_SEEN_FOR_QUERIES_AND_SUGGESTS(String origin_remote_id, String whichTypeOfNotification) {
        ContentValues contentValues = new ContentValues();
        //---
        contentValues.put(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ACTIVATION_TAG, "0");
        //sqLiteDatabase.update(MYDATABASE_BOO_TABLE_USER_CREDS, contentValues, MYDATABASE_BOO_TABLE_USER_CREDS_ID + "=" + LastId, null);
        return sqLiteDatabase.update(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE, contentValues, MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_NOTIF_NUMBER + "='" + origin_remote_id + "' AND "  + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ACTIVATION_TAG + " ='1'  ", null) > 0;

    }

    //--------------Select particular notifification using remote id and tag
    public Cursor queueNOTIFICATIONS_FOR_THE_REMOTE_ID(String origin_remote_id, String notif_tag,String ActiveOrNot,String Orient) {

        String[] columns = new String[]{MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ID, MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_NOTIF_NUMBER,
                MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TITLE
                , MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_MESSAGE, MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ORIGIN_REMOTE_ID,
                MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_IMAGE_URL, MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_BIG_TEXT,
                MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_REGDATE, MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_SUMMARY,
                MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ACTIVATION_TAG,MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TYPE,
                MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ORIENTATION_Q_S
 };
        Cursor cursor;
        if(!ActiveOrNot.equalsIgnoreCase("none")) {

             cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE, columns,
                    " " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ORIGIN_REMOTE_ID + " = " + origin_remote_id + " AND  " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TYPE + " LIKE '%" + notif_tag + "%'  " + " AND  " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ACTIVATION_TAG + " '" + ActiveOrNot + "'  " + " AND  " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ORIENTATION_Q_S + " ='" + Orient + "'  ", null, null, null, null);
        }else{
             cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE, columns,
                    " " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ORIGIN_REMOTE_ID + " = " + origin_remote_id + " AND  " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TYPE + " LIKE '%" + notif_tag + "%'  " + " AND  " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ORIENTATION_Q_S + " ='" + Orient + "'  ", null, null, null, null);

        }
        return cursor;
    }
    //-----------------Select all notificatios for thisTypeOfTag
    public Cursor queueNOTIFICATIONS_FOR_THE_TAG(String notif_tag,String ActiveOrNot,String Orient) {
        String[] columns = new String[]{MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ID, MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_NOTIF_NUMBER,
                MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TITLE
                , MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_MESSAGE, MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ORIGIN_REMOTE_ID,
                MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_IMAGE_URL, MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_BIG_TEXT,
                MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_REGDATE, MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_SUMMARY,
                MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ACTIVATION_TAG,MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TYPE,
                MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ORIENTATION_Q_S
        };
        Cursor cursor;
        if(!ActiveOrNot.equalsIgnoreCase("none")) {
             cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE, columns,
                    " " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TYPE + " LIKE '%" + notif_tag + "%' AND  " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ACTIVATION_TAG + " ='" + ActiveOrNot + "'  " + " AND  " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ORIENTATION_Q_S + " ='" + Orient + "'  ", null, null, null, null);
        }else{
            cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE, columns,
                    " " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TYPE + " LIKE '%" + notif_tag + "%' AND  " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ORIENTATION_Q_S + " ='" + Orient + "'  ", null, null, null, null);

        }
        return cursor;
    }
    //-------------group by
    public Cursor queueNOTIFICATIONS_FOR_THE_TAG_GROUP_BY(String notif_tag,String ActiveOrNot,String Orient) {
        String[] columns = new String[]{MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ID, MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_NOTIF_NUMBER,
                MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TITLE
                , MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_MESSAGE, MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ORIGIN_REMOTE_ID,
                MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_IMAGE_URL, MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_BIG_TEXT,
                MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_REGDATE, MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_SUMMARY,
                MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ACTIVATION_TAG,MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TYPE,
                MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ORIENTATION_Q_S
        };
        Cursor cursor;
        if(!ActiveOrNot.equalsIgnoreCase("none")) {
            cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE, columns,
                    " " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TYPE + " LIKE '%" + notif_tag + "%' AND  " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ACTIVATION_TAG + " ='" + ActiveOrNot + "'  " + " AND  " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ORIENTATION_Q_S + " ='" + Orient + "' GROUP BY  "+MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_SUMMARY, null, null, null, null);
        }else{
            cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE, columns,
                    " " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TYPE + " LIKE '%" + notif_tag + "%' AND  " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ORIENTATION_Q_S + " ='" + Orient + "' GROUP BY  "+MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_SUMMARY, null, null, null, null);

        }
        return cursor;
    }
    //----------------------------------
    public Cursor queueNOTIFICATIONS_FOR_THE_TAG_NO_ORIENT(String notif_tag,String ActiveOrNot) {
        String[] columns = new String[]{MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ID, MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_NOTIF_NUMBER,
                MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TITLE
                , MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_MESSAGE, MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ORIGIN_REMOTE_ID,
                MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_IMAGE_URL, MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_BIG_TEXT,
                MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_REGDATE, MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_SUMMARY,
                MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ACTIVATION_TAG,MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TYPE,
                MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ORIENTATION_Q_S
        };
        Cursor cursor;
        if(!ActiveOrNot.equalsIgnoreCase("none")) {
            cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE, columns,
                    " " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TYPE + " LIKE '%" + notif_tag + "%' AND  " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ACTIVATION_TAG + " ='" + ActiveOrNot + "'  " , null, null, null, null);
        }else{
            cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE, columns,
                    " " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TYPE + " LIKE '%" + notif_tag+"%'" , null, null, null, null);

        }
        return cursor;
    }
    //--group by--
    public Cursor queueNOTIFICATIONS_FOR_THE_TAG_NO_ORIENT_GROUP_BY(String notif_tag,String ActiveOrNot) {
        String[] columns = new String[]{MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ID, MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_NOTIF_NUMBER,
                MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TITLE
                , MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_MESSAGE, MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ORIGIN_REMOTE_ID,
                MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_IMAGE_URL, MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_BIG_TEXT,
                MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_REGDATE, MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_SUMMARY,
                MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ACTIVATION_TAG,MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TYPE,
                MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ORIENTATION_Q_S
        };
        Cursor cursor;
        if(!ActiveOrNot.equalsIgnoreCase("none")) {
            cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE, columns,
                    " " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TYPE + " LIKE '%" + notif_tag + "%' AND  " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_ACTIVATION_TAG + " ='" + ActiveOrNot + "'  "+ " GROUP BY  "+MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_SUMMARY , null, null, null, null);
        }else{
            cursor = sqLiteDatabase.query(MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE, columns,
                    " " + MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_TYPE + " LIKE '%" + notif_tag+"%'"+ " GROUP BY  "+MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS_TABLE_SUMMARY , null, null, null, null);

        }
        return cursor;
    }


    public class SQLiteHelper extends SQLiteOpenHelper {

        public SQLiteHelper(Context context, String name,
                            CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            db.execSQL(SCRIPT_CREATE_NDS_TABLE_USER_CREDS);
            db.execSQL(SCRIPT_CREATE_NDS_TABLE_NEZA_RGB_QUERY);
            db.execSQL(SCRIPT_CREATE_NDS_TABLE_NEZA_RGB_QUERY_RESPONSE);
            db.execSQL(SCRIPT_CREATE_NDS_TABLE_NEZA_COMMUNITY_POSTS);
            db.execSQL(SCRIPT_CREATE_NDS_TABLE_NEZA_COMMUNITY_POSTS_COMMENTS);
            db.execSQL(SCRIPT_CREATE_NDS_TABLE_MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_FLAGS);
            db.execSQL(SCRIPT_CREATE_MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_POSTS_SUGGESTION_BOX);
            db.execSQL(SCRIPT_CREATE_MYDATABASE_NDS_TABLE_NEZA_COMMUNITY_LOCAL_SETTINGS);
            db.execSQL(SCRIPT_CREATE_NDS_TABLE_NEZA_COMMUNITY_POSTS_NO_DATA);
            db.execSQL(SCRIPT_CREATE_MYDATABASE_NDS_TABLE_APP_NOTIFICATIONS);
            //------

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub

        }

    }

}


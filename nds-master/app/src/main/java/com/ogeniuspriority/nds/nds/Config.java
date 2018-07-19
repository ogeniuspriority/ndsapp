package com.ogeniuspriority.nds.nds;

/**
 * Created by USER on 9/12/2016.
 */
public  class Config {
    //--boo.ogeniuspriority.com
    //public static String IP_ADDRESS=" http://www.boo.ogeniuspriority.com/";

    //public static  String IP_ADDRESS="http://192.168.137.168/";
    public static  String IP_ADDRESS="http://www.nezaservice.com/";
    public static String ACCOUNT_TYPE_ONE_REGISTRATION=IP_ADDRESS
    +"androidRemoteScripts_neza/NDS_register_new_phone_user.php";
    public static String ACCOUNT_TYPE_ONE_REGISTRATION_RESEND_CODE=IP_ADDRESS
            +"androidRemoteScripts_neza/NDS_register_new_phone_user_resend.php";
    public static String ACCOUNT_TYPE_ONE_REGISTRATION_CONFIRM=IP_ADDRESS
            +"androidRemoteScripts_neza/NDS_register_new_phone_user_confirm_account.php";
    public static String LOAD_MY_AVATAR=IP_ADDRESS
            +"uploaded_images/";
    public static String DEPOSIT_MYQUERY_TO_THE_RGB=IP_ADDRESS
            +"androidRemoteScripts_neza/NDS_sendTHE_RGB_queries_for_responding.php";
    //--community_survey.php
    public static String NEZA_SURVERY_TRAIL=IP_ADDRESS
            +"Community_Admin/community_survey.php?urlset=ok";
    //---------Create a new post here
    //--community_survey.php
    public static String NDS_CREATE_A_NEW_COMMUNITY_POST=IP_ADDRESS
            +"androidRemoteScripts_neza/NDS_create_a_new_post_to_community.php";
    public static String NDS_COMMUNITY_LOAD_POSTS=IP_ADDRESS
            +"androidRemoteScripts_neza/NDS_COMMUNITY_loadAll_Posts.php";
    //--.php
    public static String NDS_CREATE_A_NEW_COMMENT_POST=IP_ADDRESS
            +"androidRemoteScripts_neza/NDS_create_a_new_comment_to_community.php";
    //--------
    public static String NDS_LOAD_ALLL_RELATED_COMMENTS=IP_ADDRESS
            +"androidRemoteScripts_neza/NDS_COMMUNITY_loadAll_Related_Comments.php";
    //--------
    public static String NDS_LIKE_OR_DISLIKE_POST=IP_ADDRESS
            +"androidRemoteScripts_neza/NDS_likeOrDislikeThisPost.php";
    //--
    public static String NDS_UPDATE_POST_VIEWS=IP_ADDRESS
            +"androidRemoteScripts_neza/NDS_UpdateViews.php";
    public static String NDS_REPORT_A_POST=IP_ADDRESS
            +"androidRemoteScripts_neza/NDS_ReportAPost.php";
    public static String NDS_SEARCH_A_POST=IP_ADDRESS
            +"androidRemoteScripts_neza/NDS_COMMUNITY_loadAll_Posts_Search.php";
    public static String NDS_LOAD_PREVIOUS_POSTS=IP_ADDRESS
            +"androidRemoteScripts_neza/NDS_COMMUNITY_loadAll_Posts_previousOld.php";
    public static String NDS_LOAD_NEW_POSTS_COUNT=IP_ADDRESS
            +"androidRemoteScripts_neza/NDS_COMMUNITY_loadAll_CountNewPosts.php";
    //--
    public static String NDS_LOAD_ALLL_RELATED_COMMENTS_COUNT=IP_ADDRESS
            +"androidRemoteScripts_neza/NDS_COMMUNITY_loadAll_Related_Comments_count.php";
    public static String NDS_LOAD_ALL_RELATED_COMMENTS_OLD=IP_ADDRESS
            +"androidRemoteScripts_neza/NDS_COMMUNITY_loadAll_Related_Comments_old.php";
    public static String NDS_DELETE_THIS_POST=IP_ADDRESS
            +"androidRemoteScripts_neza/NDS_DeleteThisPost.php";
    public static String NDS_LOAD_COMMUNITY_SUGGESTION_BOXES=IP_ADDRESS
            +"Community_Admin/community_phone_scripts/load_community_suggestion_boxes.php";
    public static String NDS_DELETE_A_SUGGESTION_BOX=IP_ADDRESS
            +"Community_Admin/community_phone_scripts/delete_a_suggestion_box.php";
    //---------/read_and_render_the_institution_avatar.php--
    public static String LOAD_MY_SUGGESTION_BOXES_RENDER=IP_ADDRESS
            +"additional_mobile_features/read_and_render_the_institution_avatar.php";
    //-------------
    public static String LOAD_MY_SUGGESTION_BOXES=IP_ADDRESS
            +"uploaded_images/";
    public static String UPLOAD_SUGGESTION_IMAGE=IP_ADDRESS
            +"NDS_suggestion_boxes_avatar/";
    //---------------------------------------
    public static  String NDS_COMMUNITY_SUGGEST_IMAGE=IP_ADDRESS
            +"Community_Admin/community_phone_scripts/nds_android_upload_image_suggestion.php";
    public static  String NDS_COMMUNITY_SUGGEST_AUDIO=IP_ADDRESS
            +"Community_Admin/community_phone_scripts/nds_android_upload_audio_suggestion.php";
    public static  String NDS_COMMUNITY_SUGGEST_VIDEO=IP_ADDRESS
            +"Community_Admin/community_phone_scripts/nds_android_upload_videos_suggestion.php";
    public static  String NDS_SEND_TEXT_SUGGESTION=IP_ADDRESS
            +"Community_Admin/community_phone_scripts/nds_android_send_text_suggestion.php";
    public static  String NDS_SEND_APP_REPORT=IP_ADDRESS
            +"Community_Admin/community_phone_scripts/nds_android_send_report_data.php";
    //--
    public static  String NDS_PROFILE_IMAGE=IP_ADDRESS
            +"Community_Admin/community_phone_scripts/nds_android_upload_image_profile.php";
    //----------------
    public static  String NDS_USER_NAMES=IP_ADDRESS
            +"Community_Admin/community_phone_scripts/nds_android_changeUserNames.php";
    //--
    public static  String NDS_USER_WORK_INFO=IP_ADDRESS
            +"Community_Admin/community_phone_scripts/nds_android_changeWorkingInfo.php";
    public static  String NDS_USER_LOACTION_INFO=IP_ADDRESS
            +"Community_Admin/community_phone_scripts/nds_android_changeLocationInfo.php";
    public static  String NDS_USER_LOGIN_INFO=IP_ADDRESS
            +"Community_Admin/community_phone_scripts/nds_android_UpdateLoginInfo.php";
    public static  String NDS_USER_CHANGE_PHONE_NUMBER=IP_ADDRESS
            +"Community_Admin/community_phone_scripts/NDS_register_new_phone.php";
    public static  String NDS_USER_GET_ACTIVATION_CODE=IP_ADDRESS
            +"Community_Admin/community_phone_scripts/NDS_register_new_phone_resend.php";
    public static  String NDS_USER_CONFIRM_NUMBER=IP_ADDRESS
            +"Community_Admin/community_phone_scripts/NDS_register_new_phone_user_confirm_account.php";
    public static  String NDS_FIND_MY_REMOTE_SUGGESTION=IP_ADDRESS
            +"Community_Admin/community_phone_scripts/NDS_get_all_my_remote_suggestions.php";
    public static  String NDS_FIND_THIS_MEDIA=IP_ADDRESS
            +"Community_Admin/community_phone_scripts/NDS_load_this_media_2017.php?id_090=";
    public static  String NDS_SEND_THIS_QUERY_SUGG_COMMENT=IP_ADDRESS
            +"Community_Admin/community_phone_scripts/nds_load_additionaLq_s_comments.php";
    public static  String NDS_SEND_THIS_QUERY_SUGG_COMMENT_SEND=IP_ADDRESS
            +"Community_Admin/community_phone_scripts/nds_send_the_comment_2017_data.php";
    public static  String NDS_LOAD_COMMENTATORS_AND_THEIR_COMMENTS=IP_ADDRESS
            +"Community_Admin/community_phone_scripts/NDS_load_commentators_and_comments_2017.php";
    public static  String NDS_FINALIZE_THE_RGB_QUERY=IP_ADDRESS
            +"Community_Admin/community_phone_scripts/NDS_finalize_this_query.php";

    public static  String NDS_NOTIFICATION_LOAD_NOTIF_MSGS_FROM_INSTI_A=IP_ADDRESS
            +"notif_scripts/NDS_NOTIFICATION_LOAD_NOTIF_MSGS_FROM_INSTI_A.php";
    public static  String NDS_NOTIFICATION_LOAD_NOTIF_MSGS_FROM_INSTI_B=IP_ADDRESS
            +"notif_scripts/NDS_NOTIFICATION_LOAD_NOTIF_MSGS_FROM_INSTI_B.php";
    //---------------FOR SUGGESTIONS--
    public static  String NDS_NOTIFICATION_LOAD_NOTIF_MSGS_FROM_INSTI_A_1=IP_ADDRESS
            +"notif_scripts/NDS_NOTIFICATION_LOAD_NOTIF_MSGS_FROM_INSTI_A_1.php";
    public static  String NDS_NOTIFICATION_LOAD_NOTIF_MSGS_FROM_INSTI_B_1=IP_ADDRESS
            +"notif_scripts/NDS_NOTIFICATION_LOAD_NOTIF_MSGS_FROM_INSTI_B_1.php";

    public static  String NDS_NOTIFICATION_LOAD_NOTIF_CMNTS_ON_POSTS_A=IP_ADDRESS
            +"notif_scripts/NDS_NOTIFICATION_LOAD_NOTIF_CMNTS_ON_POSTS_A.php";
    public static  String NDS_NOTIFICATION_LOAD_NOTIF_CMNTS_ON_POSTS_B=IP_ADDRESS
            +"notif_scripts/NDS_NOTIFICATION_LOAD_NOTIF_CMNTS_ON_POSTS_B.php";

    public static  String NDS_NOTIFICATION_LOAD_NOTIF_TAG_IN_POSTS_A=IP_ADDRESS
            +"notif_scripts/NDS_NOTIFICATION_LOAD_NOTIF_TAG_IN_POSTS_A.php";


    public static  String NDS_NOTIFICATION_LOAD_NOTIF_TAG_IN_CMNTS_A=IP_ADDRESS
            +"notif_scripts/NDS_NOTIFICATION_LOAD_NOTIF_TAG_IN_CMNTS_A.php";

    public static  String NDS_NOTIFICATION_LOAD_NOTIF_KEYWORD_IN_POSTS_A=IP_ADDRESS
            +"notif_scripts/NDS_NOTIFICATION_LOAD_NOTIF_KEYWORD_IN_POSTS_A.php";

    public static  String NDS_NOTIFICATION_LOAD_NOTIF_KEYWORD_IN_CMNTS_A=IP_ADDRESS
            +"notif_scripts/NDS_NOTIFICATION_LOAD_NOTIF_KEYWORD_IN_CMNTS_A.php";
    public static  String NDS_NOTIFICATION_LOAD_THE_NEEDED_AVATAR=IP_ADDRESS
            +"notif_scripts/NDS_NOTIFICATION_LOAD_THE_NEEDED_AVATAR.php";
    public static String NDS_COMMUNITY_LOAD_SINGLE_POST=IP_ADDRESS
            +"androidRemoteScripts_neza/NDS_COMMUNITY_loadAll_Single_Post.php";
    public static String NDS_COMMUNITY_LOAD_SINGLE_POST_COMMENT=IP_ADDRESS
            +"androidRemoteScripts_neza/NDS_COMMUNITY_loadAll_Single_Post_Comment.php";
    public static String NDS_LOAD_INSTITUTION_TO_SEND_TO=IP_ADDRESS
            +"additional_mobile_features/nds_load_institution_to_send_to.php";

    public static String NDS_LOG_IN_USING_CREDS=IP_ADDRESS
            +"additional_mobile_features/log_onto_nds_app.php";







}




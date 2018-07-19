package com.ogeniuspriority.nds.nds;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class follow_up_this_query extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_this_query);
        getSupportActionBar().hide();
        //----------swipe code--
        final WebView webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSaveFormData(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        Intent intent = getIntent();
        String id_op = intent.getStringExtra("theQueryIdAndUserId");
        final String queryId = id_op.split("-")[0];
        final String UserId = id_op.split("-")[1];
        //----
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl(Config.NDS_LOAD_COMMENTATORS_AND_THEIR_COMMENTS.toString() + "?common_user_settings_the_id=" + UserId + "&queryId_34=" + queryId);
            }
        });
        webView.loadUrl(Config.NDS_LOAD_COMMENTATORS_AND_THEIR_COMMENTS.toString() + "?common_user_settings_the_id=" + UserId + "&queryId_34=" + queryId);



    }


}

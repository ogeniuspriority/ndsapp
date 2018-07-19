package com.ogeniuspriority.nds.nds;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class NDS_settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_nds_settings);

        TextView Profile=(TextView)findViewById(R.id.Profile);
        TextView Notifications=(TextView)findViewById(R.id.Notifications);
        TextView photos_media=(TextView)findViewById(R.id.photos_media);
        TextView report_a_problem=(TextView)findViewById(R.id.report_a_problem);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NDS_settings.this, Profile_edit.class);
                //intent.putExtra("keyId", "0");
                startActivity(intent);
            }
        });
        Notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NDS_settings.this, Notification_settings.class);
                //intent.putExtra("keyId", "0");
                startActivity(intent);

            }
        });
        photos_media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NDS_settings.this, Photos_and_media.class);
                //intent.putExtra("keyId", "0");
                startActivity(intent);
            }
        });

        report_a_problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NDS_settings.this, Report_a_problem.class);
                //intent.putExtra("keyId", "0");
                startActivity(intent);

            }
        });

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}

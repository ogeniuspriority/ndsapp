package com.ogeniuspriority.nds.nds;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ogeniuspriority.nds.nds.m_MySQL.My_Community_App_Report_Send;

public class Report_a_problem extends AppCompatActivity {
    private static String MYRemoteId = "";
    private static String MYAvatar = "";
    private static String MYNames = "";
    NDS_db_adapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_a_problem);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = new NDS_db_adapter(this);
        //-------------------
        db.openToWrite();
        Cursor UserCreds = db.queueAllFromCreds();

        if (UserCreds.moveToLast()) {
            MYRemoteId = UserCreds.getString(9);
            MYAvatar = UserCreds.getString(3);
            MYNames = UserCreds.getString(1);
        } else {


        }
        //----------
        final EditText complaint = (EditText) findViewById(R.id.complaint);
        ImageView send_complaint = (ImageView) findViewById(R.id.send_complaint);
        send_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!complaint.getText().toString().isEmpty()) {
                    new My_Community_App_Report_Send(Report_a_problem.this, Config.NDS_SEND_APP_REPORT, MYRemoteId, complaint.getText().toString(),Report_a_problem.this).execute();
                } else {
                    Toast.makeText(getBaseContext(), "Empty field!", Toast.LENGTH_SHORT).show();
                }

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

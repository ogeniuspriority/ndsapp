package com.ogeniuspriority.nds.nds;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ogeniuspriority.nds.nds.m_MySQL.NDS_log_in_using_creds;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class NDS_Login extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    private MarshMallowPermission marshMallowPermission;
    PopupWindow popupWindow;
    Button nds_sign_in;
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    //-----------------------
    private EditText amazinaYombi;
    private EditText email;
    private EditText country_code;
    private EditText telephone;
    private EditText password_;
    private EditText passwordretype;
    //-------------------
    private View mProgressView;
    private View mLoginFormView;
    public static int theCode;
    //------------------
    NDS_db_adapter db;
    //----
    StringBuffer response;
    private static String serverResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nds__login);
        // Set up the login form.
        // The request code used in ActivityCompat.requestPermissions()
// and returned in the Activity's onRequestPermissionsResult()
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.GET_ACCOUNTS, Manifest.permission.READ_CONTACTS, Manifest.permission.VIBRATE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO
                , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET
                , Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.SYSTEM_ALERT_WINDOW};

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        //-------------
        db = new NDS_db_adapter(this);
        //-------------------
        db.openToWrite();

        //-------------------
        getSupportActionBar().hide();
        // getActionBar().hide();
        mEmailView = (EditText) findViewById(R.id.email);
        populateAutoComplete();
        //---------------------------------
        amazinaYombi = (EditText) findViewById(R.id.amazinaYombi);
        email = (EditText) findViewById(R.id.email);
        country_code = (EditText) findViewById(R.id.country_code);
        telephone = (EditText) findViewById(R.id.telephone);
        password_ = (EditText) findViewById(R.id.password);
        passwordretype = (EditText) findViewById(R.id.passwordretype);
        //-----------------------Check user creds emptiness---
        db.openToWrite();
        Cursor UserCreds = db.queueAllFromCreds();

        if (UserCreds.moveToLast()) {
            String myRemoteId = UserCreds.getString(9);
            if (!myRemoteId.toString().equalsIgnoreCase("")) {
                Intent myIntent = new Intent(NDS_Login.this, NDS_main.class);
                //myIntent.putExtra("key", value); //Optional parameters
                startActivity(myIntent);

            }
        } else {


        }
        //-------------login usings creds--
        nds_sign_in = (Button) findViewById(R.id.nds_sign_in);


        //------------------------------

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.password || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        //-------------
        nds_sign_in.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(NDS_Login.this,"sjjs",Toast.LENGTH_SHORT).show();
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.nds_user_login, null);
                //-----------------
                EditText amazinaYombi = (EditText) popupView.findViewById(R.id.amazinaYombi);
                EditText password = (EditText) popupView.findViewById(R.id.password);
                Button email_sign_in_button = (Button) popupView.findViewById(R.id.email_sign_in_button);
                Button nds_sign_in = (Button) popupView.findViewById(R.id.nds_sign_in);


                email_sign_in_button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(amazinaYombi.getText().toString().equalsIgnoreCase("") || password.getText().toString().equalsIgnoreCase("")){
                            Toast.makeText(NDS_Login.this,"Empty field",Toast.LENGTH_SHORT).show();
                        }else{
                            new NDS_log_in_using_creds(NDS_Login.this, Config.NDS_LOG_IN_USING_CREDS.toString(), amazinaYombi.getText().toString(), password.getText().toString(),popupWindow).execute();


                        }
                    }
                });


                popupWindow = new PopupWindow(
                        popupView,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);

                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOutsideTouchable(true);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        //TODO do sth here on dismiss
                    }
                });
                nds_sign_in.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                //---------------------
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        popupWindow.showAtLocation(v, Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
                        popupWindow.setAnimationStyle(android.R.style.TextAppearance_DeviceDefault_Small_Inverse);
                        popupWindow.setFocusable(true);
                        popupWindow.update();
                    }
                }, 100);


            }
        });

    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }

        switch (requestCode) {
            case MarshMallowPermission.EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE_BY_LOAD_PROFILE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //permission granted successfully

                } else {

                    //permission denied

                }
                break;
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            /// Toast.makeText(getBaseContext(), "Cool!", Toast.LENGTH_SHORT).show();

            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        } else if (!password_.getText().toString().equalsIgnoreCase(passwordretype.getText().toString())) {
            mPasswordView.setError("Passwords do not match!?");
            focusView = mPasswordView;
            cancel = true;
        }
        if (country_code.getText().toString().isEmpty()) {
            country_code.setError("Country code?!");
            focusView = country_code;
            cancel = true;
        }
        if (telephone.getText().toString().isEmpty()) {
            telephone.setError("Phone number?!");
            focusView = telephone;
            cancel = true;
        }
        if (password_.getText().toString().isEmpty()) {
            password_.setError("Password ?!");
            focusView = password_;
            cancel = true;
        }
        if (amazinaYombi.getText().toString().isEmpty()) {
            amazinaYombi.setError("Amazina ?!");
            focusView = amazinaYombi;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    serverResponse = "";
                    try {
                        serverResponse = registerToDbStage1();

                        //-------------------------------


                        if (serverResponse != null) {
                            Log.w("2017_NDS", serverResponse);
                            // showProgress(false);
                            //----------

                            Intent intent = new Intent(NDS_Login.this, NDS_registration_activation.class);
                            intent.putExtra("msgcode", "0");
                            String tel = telephone.getText().toString();
                            int tel_ = Integer.parseInt(tel);
                            intent.putExtra("telephone", country_code.getText().toString() + tel_);
                            startActivity(intent);
                            //---------------------
                            showProgress(false);

                        } else {
                            showProgress(false);
                            Toast.makeText(getBaseContext(), "Network error!", Toast.LENGTH_SHORT).show();

                        }


                        //-------------------
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.w("2017_NDS", serverResponse);
                    }

                }
            }).start();
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return new CursorLoader(this,
                    // Retrieve data rows for the device user's 'profile' contact.
                    Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                            ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                    // Select only email addresses.
                    ContactsContract.Contacts.Data.MIMETYPE +
                            " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                    .CONTENT_ITEM_TYPE},

                    // Show primary email addresses first. Note that there won't be
                    // a primary email address if the user hasn't specified one.
                    ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        //   addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

   /* private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(NDS_Login.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }
*/

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    private String registerToDbStage1() throws Exception {
        //---------------- Stage 1----------------------------------------------------
        //---------------------------------
        amazinaYombi = (EditText) findViewById(R.id.amazinaYombi);
        email = (EditText) findViewById(R.id.email);
        country_code = (EditText) findViewById(R.id.country_code);
        telephone = (EditText) findViewById(R.id.telephone);
        password_ = (EditText) findViewById(R.id.password);
        passwordretype = (EditText) findViewById(R.id.passwordretype);
        //-------------Stage 2--------
        int phone = (int) Integer.parseInt(telephone.getText().toString());
        //----------------------------------------------------------------
        String data = "";
        data = URLEncoder.encode("amazinaYombi", "UTF-8")
                + "=" + URLEncoder.encode(amazinaYombi.getText().toString(), "UTF-8");
        data += "&" + URLEncoder.encode("email", "UTF-8") + "="
                + URLEncoder.encode(email.getText().toString(), "UTF-8");
        data += "&" + URLEncoder.encode("phone", "UTF-8") + "="
                + URLEncoder.encode("" + country_code.getText().toString() + phone, "UTF-8");
        data += "&" + URLEncoder.encode("password_", "UTF-8") + "="
                + URLEncoder.encode(password_.getText().toString(), "UTF-8");


        String lastResort = data;
        //==============================
        try {
            String url = Config.ACCOUNT_TYPE_ONE_REGISTRATION;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            //-----------------------------------------------------------------------
            String urlParameters = lastResort;

            // Send post request
            con.setDoOutput(true);
            con.setDoInput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            //--------------------------Thread sleeps-----------

            int responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();


        } catch (ProtocolException e) {
            e.printStackTrace();
            Log.w("NDS", "Now we can talk=> " + e.toString());


        } catch (MalformedURLException e) {
            // e.printStackTrace();
            Log.w("NDS", "Now we can talk=> " + e.toString());

        } catch (IOException e) {
            Log.w("NDS", "Now we can talk=> " + e.toString());

            e.printStackTrace();
        }


        return response.toString();

        //---------------------------------------------------------------
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.


                //Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }


            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                //finish();


                //-------------
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //-----------------------Check user creds emptiness---
        db.openToWrite();
        Cursor UserCreds = db.queueAllFromCreds();

        if (UserCreds.moveToLast()) {
            String myRemoteId = UserCreds.getString(9);
            if (!myRemoteId.toString().equalsIgnoreCase("")) {
                Intent myIntent = new Intent(NDS_Login.this, NDS_main.class);
                //myIntent.putExtra("key", value); //Optional parameters
                startActivity(myIntent);

            }
        } else {


        }

    }
}


package com.example.ruchika.corral_client.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.corral_client.R;
import com.example.ruchika.corral_client.Additional_Features.DrawerActivity;
import com.example.ruchika.corral_client.Chat.Config;
import com.example.ruchika.corral_client.Chat.MessageSender;
import com.example.ruchika.corral_client.Database.UserOperations;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//import com.example.ruchika.corral_client.R;
public class TestActivity extends ActionBarActivity {


    private static final String TAG = "SignUpActivity";
    public static final String REG_ID = "regId";
    private static final String APP_VERSION = "appVersion";
    Button buttonSignUp;
    Button buttonLogin;
    String regId;
    String signUpUser;
    AsyncTask<Void, Void, String> sendTask;
    AtomicInteger ccsMsgId = new AtomicInteger();
    GoogleCloudMessaging gcm;
    Context context;
    private boolean signupFlag = false;
    MessageSender messageSender;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_);
        signUpUser = loadingsharedvariables();
        context = getApplicationContext();
        buttonSignUp = (Button) findViewById(R.id.ButtonSignUp);
        messageSender = new MessageSender();

        if (TextUtils.isEmpty(regId)) {
            regId = registerGCM();

            Log.d(TAG, "GCM RegId: " + regId);

        }
    }

    @Override
    protected void onPostResume() {
        Intent i = new Intent(context,
                DrawerActivity.class);
        startActivity(i);
        super.onPostResume();
    }
    //step 2: register with XMPP App Server
        //if(!regId.isEmpty()) {
            //EditText mUserName = (EditText) findViewById(R.id.userName);


            //signUpUser = mUserName.getText().toString();

        public void call(){
            Bundle dataBundle = new Bundle();
            dataBundle.putString("ACTION", "SIGNUP");
            dataBundle.putString("USER_NAME", signUpUser);
            messageSender.sendMessage(dataBundle,gcm);
            Log.i(TAG,dataBundle.toString());

            UserOperations UserDBoperation;
            UserDBoperation = new UserOperations(getApplicationContext());
            UserDBoperation.open();
            UserDBoperation.addUser(signUpUser,regId);
            List list = UserDBoperation.getAllUsers();
            Log.d("User List",list.toString());
            UserDBoperation.close();


                    /*ContentValues values = new ContentValues();

                    values.put(DatbaseHelper.USER_NAME, signUpUser);
                    values.put(DatbaseHelper.USER_REG_ID,"hello");
                    long userId = database.insert(DatbaseHelper.USERS, null, values);*/

            signupFlag = true;
            Toast.makeText(context,
                    "Sign Up Complete!",
                    Toast.LENGTH_LONG).show();
            Intent i = new Intent(context,
                    DrawerActivity.class);
            startActivity(i);


            Log.d(TAG,
                    "onClick of login: Before starting userlist activity.");
            //startActivity(i);
            //finish();
            Log.d(TAG, "onClick of Login: After finish.");

        } /*else {
            Toast.makeText(context,
                    "Google GCM RegId Not Available!",
                    Toast.LENGTH_LONG).show();
        }*



        /*if (TextUtils.isEmpty(regId)) {
            regId = registerGCM();
            Log.d(TAG, "GCM RegId: " + regId);
        }*/
//step 1: user authentication
//step 2: get user list
        /*Bundle dataBundle = new Bundle();
        dataBundle.putString("ACTION", "USERLIST");
        dataBundle.putString("USER_NAME", signUpUser);
        messageSender.sendMessage(dataBundle,gcm);*/







       /* buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
//step 1: register with Google GCM server
                if (TextUtils.isEmpty(regId)) {
                    regId = registerGCM();

                    Log.d(TAG, "GCM RegId: " + regId);

                }
//step 2: register with XMPP App Server
                if(!regId.isEmpty()) {
                    EditText mUserName = (EditText) findViewById(R.id.userName);


                    signUpUser = mUserName.getText().toString();


                    Bundle dataBundle = new Bundle();
                    dataBundle.putString("ACTION", "SIGNUP");
                    dataBundle.putString("USER_NAME", signUpUser);
                    messageSender.sendMessage(dataBundle,gcm);
                    Log.i(TAG,dataBundle.toString());

                    UserOperations UserDBoperation;
                    UserDBoperation = new UserOperations(getApplicationContext());
                    UserDBoperation.open();
                    UserDBoperation.addUser(signUpUser,regId);
                    List list = UserDBoperation.getAllUsers();
                    Log.d("User List",list.toString());
                    UserDBoperation.close();


                    /*ContentValues values = new ContentValues();

                    values.put(DatbaseHelper.USER_NAME, signUpUser);
                    values.put(DatbaseHelper.USER_REG_ID,"hello");
                    long userId = database.insert(DatbaseHelper.USERS, null, values);*/

                   /* signupFlag = true;
                    Toast.makeText(context,
                            "Sign Up Complete!",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context,
                            "Google GCM RegId Not Available!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        buttonLogin = (Button) findViewById(R.id.ButtonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
//step 0: register with Google GCM server
                if (TextUtils.isEmpty(regId)) {
                    regId = registerGCM();
                    Log.d(TAG, "GCM RegId: " + regId);
                }*/
//step 1: user authentication
//step 2: get user list
                /*Bundle dataBundle = new Bundle();
                dataBundle.putString("ACTION", "USERLIST");
                dataBundle.putString("USER_NAME", signUpUser);
                messageSender.sendMessage(dataBundle,gcm);
                Intent i = new Intent(context,
                        UserListActivity.class);*/
                /*Log.d(TAG,
                        "onClick of login: Before starting userlist activity.");
                startActivity(i);
                finish();
                Log.d(TAG, "onClick of Login: After finish.");
            }
        });*/
    //}



    public String loadingsharedvariables(){

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String rPersonName = sharedPreferences.getString("personName", "");
        String rEmail = sharedPreferences.getString("email", "");
        String rPersonPhotoUrl = sharedPreferences.getString("personPhotoUrl", "");

        return rPersonName;

    }
    public String registerGCM() {
        gcm = GoogleCloudMessaging.getInstance(this);
        regId = getRegistrationId();
        if (TextUtils.isEmpty(regId)) {
            registerInBackground();
            Log.d(TAG,
                    "registerGCM - successfully registered with GCM server - regId: "
                            + regId);
        } else {
            Log.d(TAG,
                    "Regid already available: "
                            + regId
            );
        }
        return regId;
    }
    private String getRegistrationId() {
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String registrationId = prefs.getString(REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        int registeredVersion = prefs.getInt(APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion();
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }
    private int getAppVersion() {
        try {
            PackageInfo packageInfo;
            packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("RegisterActivity",
                    "I never expected this! Going down, going down!" + e);
            throw new RuntimeException(e);
        }
    }
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regId = gcm.register(Config.GOOGLE_PROJECT_ID);
                    Log.d("RegisterActivity", "registerInBackground - regId: "
                            + regId);
                    msg = "Device registered, registration ID=" + regId;
                    storeRegistrationId(regId);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    Log.d(TAG, "Error: " + msg);
                }
                Log.d(TAG, "AsyncTask completed: " + msg);
                //call();
                return msg;
            }
            @Override
            protected void onPostExecute(String msg) {
                Log.d(TAG, "Registered with GCM Server." + msg);
                call();

            }
        }.execute(null, null, null);
    }
    private void storeRegistrationId(String regId) {
        /*final SharedPreferences prefs = getSharedPreferences(
                TestActivity.class.getSimpleName(), Context.MODE_PRIVATE);*/
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int appVersion = getAppVersion();
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REG_ID, regId);
        editor.putInt(APP_VERSION, appVersion);
        editor.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
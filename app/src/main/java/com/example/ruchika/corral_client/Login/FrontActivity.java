
package com.example.ruchika.corral_client.Login;

import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.corral_client.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.io.InputStream;

public class FrontActivity extends FragmentActivity implements OnClickListener, ConnectionCallbacks, OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 0;
    // Google client to communicate with Google
    public GoogleApiClient mGoogleApiClient;
    private String personName;
    private String personPhotoUrl;
    private String email;
    private boolean mIntentInProgress;
    private boolean signedInUser;
    private ConnectionResult mConnectionResult;
    private SignInButton signinButton;
    private ImageView image;
    private TextView username, emailLabel;
    private LinearLayout profileFrame, signinFrame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);
        signinButton = (SignInButton) findViewById(R.id.signin);
        signinButton.setOnClickListener(this);
        image = (ImageView) findViewById(R.id.image);
        username = (TextView) findViewById(R.id.username);
        emailLabel = (TextView) findViewById(R.id.email);
        profileFrame = (LinearLayout) findViewById(R.id.profileFrame);
        signinFrame = (LinearLayout) findViewById(R.id.signinFrame);
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Plus.API, Plus.PlusOptions.builder().build()).addScope(Plus.SCOPE_PLUS_LOGIN).build();
    }
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
            return;
        }
        if (!mIntentInProgress) {
// store mConnectionResult
            mConnectionResult = result;
            if (signedInUser) {
                resolveSignInError();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        switch (requestCode) {
            case RC_SIGN_IN:
                if (responseCode == RESULT_OK) {
                    signedInUser = false;
                }
                mIntentInProgress = false;
                if (!mGoogleApiClient.isConnecting()) {
                    mGoogleApiClient.connect();
                }
                break;
        }
    }
    @Override
    protected void onResume() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if ((sharedPreferences.contains("personName"))&& (sharedPreferences.contains("email"))){
            getProfileInformation();
            profileFrame.setVisibility(View.VISIBLE);
        }
        else{
            signinFrame.setVisibility(View.VISIBLE);
        }
        super.onResume();
    }
    @Override
    public void onConnected(Bundle arg0) {
        signedInUser = false;
        Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
        getProfileInformation();
    }
    public void updateProfile(boolean isSignedIn) {
        if (isSignedIn) {
            Intent i = new Intent(this,TestActivity.class);
            startActivity(i);
            signinFrame.setVisibility(View.GONE);
            profileFrame.setVisibility(View.VISIBLE);
        } else {
            signinFrame.setVisibility(View.VISIBLE);
            profileFrame.setVisibility(View.GONE);
        }
    }
    public void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                personName = currentPerson.getDisplayName();
                personPhotoUrl = currentPerson.getImage().getUrl();
                System.out.println(personPhotoUrl);
                email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("personName",personName);
                editor.putString("email",email);
                editor.putString("personPhotoUrl",personPhotoUrl);
                editor.commit();
                username.setText(personName);
                emailLabel.setText(email);
                new LoadProfileImage(image).execute(personPhotoUrl);
// update profile frame with new info about Google Account
// profile
                updateProfile(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
        updateProfile(false);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin:
                googlePlusLogin();
                break;
        }
    }
    public void signIn(View v) {
        googlePlusLogin();
    }
    public void logout(View v) {
        googlePlusLogout();
    }
    private void googlePlusLogin() {
        if (!mGoogleApiClient.isConnecting()) {
            signedInUser = true;
            resolveSignInError();
        }
    }
    private void googlePlusLogout() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
            updateProfile(false);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
//moveTaskToBack(true);
//Welcome.this.finish();
        }
    }
    // download Google Account profile image, to complete profile
    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
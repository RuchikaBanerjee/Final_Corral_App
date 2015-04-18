package com.example.ruchika.corral_client.Additional_Features;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.corral_client.R;

import java.io.InputStream;
public class quit extends ActionBarActivity {
    private ImageView image;
    private TextView username, emailLabel;
    private LinearLayout profileFrame;
    SharedPreferences.Editor editor;
    private static final String personName="personName";
    private static final String email ="email";
    private static final String personPhotoUrl="personPhotoUrl";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quit);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        image = (ImageView) findViewById(R.id.image);
        username = (TextView) findViewById(R.id.username);
        emailLabel = (TextView) findViewById(R.id.email);
        profileFrame = (LinearLayout) findViewById(R.id.profileFrame);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String rPersonName = sharedPreferences.getString("personName", "");
        String rEmail = sharedPreferences.getString("email", "");
        String rPersonPhotoUrl = sharedPreferences.getString("personPhotoUrl", "");
        username.setText(rPersonName);
        emailLabel.setText(rEmail);
        new LoadProfileImage(image).execute(rPersonPhotoUrl);
    }
    public void exit (View view)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
//editor.clear();
//editor.commit();
        moveTaskToBack(true);
        quit.this.finish();
//Intent i=new Intent(this,MainActivity.class);
//startActivity(i);
    }
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quit, menu);
        return true;
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
        return super.onOptionsItemSelected(item);
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        public PlaceholderFragment() {
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_quit, container, false);
            return rootView;
        }
    }
}
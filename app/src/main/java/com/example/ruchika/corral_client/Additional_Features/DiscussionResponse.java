package com.example.ruchika.corral_client.Additional_Features;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.corral_client.R;
import com.example.ruchika.corral_client.Chat.GCMNotificationIntentService;
import com.example.ruchika.corral_client.Chat.MessageSender;
import com.example.ruchika.corral_client.Database.MessageObject;
import com.example.ruchika.corral_client.Database.MessageOperations;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.List;

public class DiscussionResponse extends ActionBarActivity {

    ListView lVreply;
    TextView txtmsg;
    String tname;
    private static Context context;
    EditText editreply;
    Button btnreply;
    String replymsg,sender_name;
    Long tsLong;
    MessageSender messageSender;
    GoogleCloudMessaging gcm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_response);
        lVreply = (ListView) findViewById(R.id.lvreply);
        txtmsg=(TextView)findViewById(R.id.txtmsg);
        btnreply = (Button) findViewById(R.id.btnReply);
        editreply = (EditText) findViewById(R.id.editreply);


        Intent intent=getIntent();
        String val= intent.getStringExtra("val");
        tname=intent.getStringExtra("tname");
        txtmsg.setText(val);

        context=getApplicationContext();
        intent = new Intent(this, GCMNotificationIntentService.class);
        registerReceiver(broadcastReceiver, new IntentFilter("replybroad"));
        messageSender = new MessageSender();


        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
        updateUI();

    }

    public void SendResponse(View v){


        sender_name = loadingsharedvariables();
        replymsg = editreply.getText().toString();
        tsLong = System.currentTimeMillis()/1000;

        Bundle dataBundle = new Bundle();
        dataBundle.putString("ACTION", "REPLY");
        dataBundle.putString("Sender_Name", sender_name);
        dataBundle.putString("replymsg", replymsg);
        dataBundle.putString("Timestamp", tsLong.toString());
        dataBundle.putString("TimerName", tname);

        System.out.println("bhejne wali " + tsLong);

        //Log.i("Discussion Name", name);
        messageSender.sendMessage(dataBundle, gcm);


        //addResponsetoDB(sender_name,replymsg,tname,tsLong);


    }
    public List listFromMessageDB(){

        MessageOperations MsgDBoperation;
        MsgDBoperation = new MessageOperations(getApplicationContext());
        MsgDBoperation.open();
        List<MessageObject> list = MsgDBoperation.getAllResponses(tname);

        return list;

    }

    private void updateUI() {

        List<MessageObject> list = listFromMessageDB();
        MessageObject [] objs= new MessageObject[list.size()];
        for(int i=0;i<list.size();i++ ){

            objs[i] = list.get(i);

        }
        ArrayAdapter<MessageObject> adapter = new ArrayAdapter<MessageObject>(this, android.R.layout.simple_list_item_1, objs);
        lVreply.setAdapter(adapter);

    }


    public void addResponsetoDB(String sender_name, String reply_msg, String timer, Long timestamp){


        MessageOperations msgDBoperation;
        msgDBoperation = new MessageOperations(context);
        msgDBoperation.open();
        msgDBoperation.addResponse(sender_name,timer,reply_msg,timestamp);
        msgDBoperation.close();


    }
    public static void deleteReply(String timername)

    {

        MessageOperations MsgDBOperation = new MessageOperations(context);
        MsgDBOperation.open();
        MsgDBOperation.deleteResponse(timername);

        MsgDBOperation.close();
        System.out.println("Deleted");

    }

    public String loadingsharedvariables(){

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String rPersonName = sharedPreferences.getString("personName", "");
        String rEmail = sharedPreferences.getString("email", "");
        String rPersonPhotoUrl = sharedPreferences.getString("personPhotoUrl", "");
        String regId = sharedPreferences.getString("regId","");


        return rPersonName;

    }

    @Override
    protected void onResume() {

        updateUI();
        super.onResume();


    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("From Response", "onReceive: " + intent.getStringExtra("TimerName"));

            String s_name = intent.getStringExtra("Sender_Name");
            String reply_message = intent.getStringExtra("replymsg");
            String tim_name = intent.getStringExtra("TimerName");
            Long timestamp = Long.valueOf(intent.getStringExtra("Timestamp")).longValue();
            System.out.println("Yahn wali time" + timestamp);
            addResponsetoDB(s_name,reply_message,tim_name,timestamp);
            updateUI();

            //updateUI(intent.getStringExtra("USERLIST"));

            //sendNotification("You have received discussion");
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_discussion_zone, menu);
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
}

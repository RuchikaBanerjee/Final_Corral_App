package com.example.ruchika.corral_client.Additional_Features;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.example.corral_client.R;
import com.example.ruchika.corral_client.Chat.GCMNotificationIntentService;
import com.example.ruchika.corral_client.Chat.MessageSender;
import com.example.ruchika.corral_client.Chat.UserListActivity;
import com.example.ruchika.corral_client.Database.DiscussionOperations;
import com.example.ruchika.corral_client.Database.MyCountDownTimer;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Discussion extends ActionBarActivity {

    SharedPreferences.Editor editor;
    private static final String personName="personName";
    private static final String email ="email";
    private static final String personPhotoUrl="personPhotoUrl";
    private Spinner spinner;
    private AutoCompleteTextView autoTvPlace,autoTvType,autoTvOrder;
    private ArrayAdapter place,type,order;
    private Intent intent;
    MessageSender messageSender;
    GoogleCloudMessaging gcm;
    String dbplace=null,dbtype=null,dbname=null,dborder=null,dbtimername=null,name=null,tname=null;
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    private final long duration = 100 * 1000;
    private final long interval = 1*1000;
    private static Context context;

    NotificationCompat.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        loadingsharedvariables();
        context =getApplicationContext();
        //addItemsOnSpinner();
        //addListenerOnButton();
        //addListenerOnSpinnerItemSelection();
        //initPlaceAutocomplete();
        initTypeAutocomplete();
        initPlaceAutocomplete();
        initMinOrderAutoComplete();
        intent = new Intent(this, GCMNotificationIntentService.class);
        registerReceiver(broadcastReceiver, new IntentFilter("discbroadcast"));
        messageSender = new MessageSender();


        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());


    }
    public void initPlaceAutocomplete() {

        autoTvPlace = (AutoCompleteTextView) findViewById(R.id.PlaceSelectionBox);

        addPlacesToAutoView();

        autoTvPlace.setThreshold(0);
        autoTvPlace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ((AutoCompleteTextView) view).showDropDown();
            }
        });
    }

    public void initMinOrderAutoComplete() {

        autoTvOrder = (AutoCompleteTextView) findViewById(R.id.Min_Order);

        addMinOrderToAutoView();

        autoTvOrder.setThreshold(0);
        autoTvOrder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ((AutoCompleteTextView) view).showDropDown();
            }
        });
    }

    public void initTypeAutocomplete() {

        autoTvType = (AutoCompleteTextView) findViewById(R.id.Cuisine_TypeSelectBox);

        addTypeToAutoView();

        autoTvType.setThreshold(0);
        autoTvType.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ((AutoCompleteTextView) view).showDropDown();
            }
        });
    }

    public void addPlacesToAutoView(){


        List<String> list = new ArrayList<String>();

        list.add("Dominos");
        list.add("Kathi Roll");
        list.add("Pizza Hut");
        list.add("Khidmat");
        list.add("Tandoori Concepts");
        list.add("Yo! China");
        list.add("Kolkata Biriyani House");


        place = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, list);
        place.notifyDataSetChanged();
        autoTvPlace.setAdapter(place);
    }

    public void addTypeToAutoView(){


        List<String> listtype = new ArrayList<String>();

        listtype.add("North Indian");
        listtype.add("Chinese");
        listtype.add("Italian");
        listtype.add("Continental");
        listtype.add("Cakes");

        type = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, listtype);
        type.notifyDataSetChanged();
        autoTvType.setAdapter(type);
    }

    public void addMinOrderToAutoView(){


        List<String> listtype = new ArrayList<String>();

        listtype.add("150");
        listtype.add("200");
        listtype.add("300");
        listtype.add("500");
        listtype.add(">500");

        order = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, listtype);
        order.notifyDataSetChanged();
        autoTvOrder.setAdapter(order);
    }

    public void Post(View v){

        initPlaceAutocomplete();
        initMinOrderAutoComplete();
        initTypeAutocomplete();

        name = loadingsharedvariables();
        /*dbplace = autoTvPlace.getText().toString();
        dbtype = autoTvType.getText().toString();
        dborder = autoTvOrder.getText().toString();*/

        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;
        dbtimername = "timer" + m;
        Log.i("New timer name:", dbtimername);
        //String tname=dbtimername;
        //Log.i("tname:", tname);



        //adddiscussiontoDB(name, dbplace, dbtype, dborder, tname);

        Log.i("Place:", autoTvPlace.getText().toString());
        Log.i("Type:",autoTvType.getText().toString());
        Log.i("Order:",autoTvOrder.getText().toString());
        //Log.i("Name:",name);



        Bundle dataBundle = new Bundle();
        dataBundle.putString("ACTION", "DISCUSSION");
        dataBundle.putString("Place", autoTvPlace.getText().toString());
        dataBundle.putString("Type", autoTvType.getText().toString());
        dataBundle.putString("Order", autoTvOrder.getText().toString());
        dataBundle.putString("Name", name);
        dataBundle.putString("TimerName",dbtimername);
        //Log.i("Discussion Name", name);
        messageSender.sendMessage(dataBundle, gcm);

    }

    public static void adddiscussiontoDB(String name, String dbplace, String dbtype, String dborder, String dbtimername){


        DiscussionOperations discDBoperation;
        discDBoperation = new DiscussionOperations(context);
        discDBoperation.open();
        discDBoperation.addDiscussion(name,dbplace,dbtype,dborder,dbtimername);
        //List list = discDBoperation.getAllUsers();
        //Log.d("User List",list.toString());
        discDBoperation.close();
        //listdiscussionFromDB();



    }
    public static void del(String timername)

    {

        DiscussionOperations DiscussionDBOperation = new DiscussionOperations(context);
        DiscussionDBOperation.open();
        DiscussionDBOperation.deleteDiscussion(timername);

        DiscussionDBOperation.close();
        System.out.println("Deleted");
    }
    /*public static void listdiscussionFromDB(){


        DiscussionOperations DiscussionDBOperation = new DiscussionOperations(context);
        DiscussionDBOperation.open();
        DiscussionObject discobj;
                List<DiscussionObject> list = DiscussionDBOperation.getAllDiscussions();
       // discobj=list.get(1);
        Log.i("timer chalooo", String.valueOf(discobj.getTimername()));
        if(discobj!= null) {

            Toast.makeText(context, String.valueOf(discobj.getName())
                    + String.valueOf(discobj.getName())
                    + String.valueOf(discobj.getPlace())
                    + String.valueOf(discobj.getOrder())
                    + String.valueOf(discobj.getType())
                    + String.valueOf(discobj.getTimername()), Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(context, "Nothing to show",
                    Toast.LENGTH_LONG).show();
        }

    }*/

    @Override
    protected void onPause() {
       // unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("From Discussion", "onReceive: " + intent.getStringExtra("Place"));

            name = intent.getStringExtra("Name");
            dbplace = intent.getStringExtra("Place");
            dbtype = intent.getStringExtra("Type");
            dborder = intent.getStringExtra("Order");
            tname = intent.getStringExtra("TimerName");

            /*Bundle dataBundle = new Bundle();
            dataBundle.putString("Place",intent.getStringExtra("Place"));
            dataBundle.putString("Type",intent.getStringExtra("Type"));
            dataBundle.putString("Name",intent.getStringExtra("Name"));
            dataBundle.putString("Order",intent.getStringExtra("Order"));
            discussionzone.putExtras(dataBundle);*/
            //Random random = new Random();
            //int m = random.nextInt(9999 - 1000) + 1000;
            //dbtimername = "timer" + m;
            //Log.i("New timer name:", dbtimername);
            //String tname=dbtimername;
            //Log.i("tname:", tname);
            dbtimername = tname;
            CountDownTimer tname = new MyCountDownTimer(duration,interval,dbtimername);
            tname.start();

            adddiscussiontoDB(name,dbplace,dbtype,dborder,dbtimername);
            //listdiscussionFromDB();
            Intent discussionzone = new Intent(getApplicationContext(),DiscussionZone.class);
            startActivity(discussionzone);


            //updateUI(intent.getStringExtra("USERLIST"));

            //sendNotification("You have received discussion");
        }
    };

    public void Clear(View v){

        initPlaceAutocomplete();
        initMinOrderAutoComplete();
        initTypeAutocomplete();

        autoTvPlace.getText().clear();
        autoTvType.getText().clear();
        autoTvOrder.getText().clear();

    }
    private void sendNotification(String msg) {
        Log.d("Plzzzzz", "Preparing to send notification...: " + msg);
        mNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, UserListActivity.class), 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(R.drawable.gcm_cloud)
                .setContentTitle("GCM XMPP Message")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        Log.d("chalo plssss", "Notification sent successfully.");
    }
   /* public void addItemsOnSpinner() {

        spinner = (Spinner) findViewById(R.id.spinner_cuisine);
        List<String> list = new ArrayList<String>();
        list.add("North Indian");
        list.add("Chinese");
        list.add("Continental");
        list.add("Italian");
        list.add("Cakes");
        list.add("Others");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }*/

   /* public void addListenerOnSpinnerItemSelection() {
        spinner = (Spinner) findViewById(R.id.spinner_cuisine);
        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }*/

    // get the selected dropdown list value
    /*public void addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        /*btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(MyAndroidAppActivity.this,
                        "OnClickListener : " +
                                "\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()) +
                                "\nSpinner 2 : "+ String.valueOf(spinner2.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }

        });
    }*/

    public String loadingsharedvariables(){

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String rPersonName = sharedPreferences.getString("personName", "");
        String rEmail = sharedPreferences.getString("email", "");
        String rPersonPhotoUrl = sharedPreferences.getString("personPhotoUrl", "");
        String regId = sharedPreferences.getString("regId","");


        return rPersonName;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_discussion, menu);
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

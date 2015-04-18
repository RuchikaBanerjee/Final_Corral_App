package com.example.ruchika.corral_client.Chat;

/**
 * Created by ruchika on 4/11/15.
 */

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.corral_client.R;
import com.example.ruchika.corral_client.Additional_Features.DiscussionZone;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GCMNotificationIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    public static int count = 0;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    public GCMNotificationIntentService() {
        super("GcmIntentService");
    }
    public static final String TAG = "GCMIntentService";
    private final long duration = 10 * 1000;
    private final long interval = 1*1000;
    String dbtimername=null;
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent " + intent.getDataString());
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);
        if (extras != null) {
            if (!extras.isEmpty()) {
                if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                        .equals(messageType)) {
                    sendNotification("Send error: " + extras.toString());
                } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                        .equals(messageType)) {
                    sendNotification("Deleted messages on server: "
                            + extras.toString());
                } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                        .equals(messageType)) {
                    if("USERLIST".equals(extras.get("SM"))){
                        Log.d(TAG, "onHandleIntent - USERLIST ");
//update the userlist view
                        Intent userListIntent = new Intent("com.example.corral_client.Chat.UserListActivity");
                        String userList = extras.get("USERLIST").toString();
                        Log.d(TAG,userList);
                        userListIntent.putExtra("USERLIST",userList);
                        //sendNotification("You have received a notification");
                        sendBroadcast(userListIntent);

                    } else if("CHAT".equals(extras.get("SM"))){
                        Log.d(TAG, "onHandleIntent - CHAT ");
                        sendNotification("You have received a notification");
                        Intent chatIntent = new Intent("com.example.corral_client.Chat.ChatMessage");
                        chatIntent.putExtra("CHATMESSAGE",extras.get("CHATMESSAGE").toString());
                        sendBroadcast(chatIntent);
                    }
                    else if("DISCUSSION".equals(extras.get("SM"))) {
                        Log.d(TAG, "onHandleIntent - DISCUSSION ");
                        sendNotification("New Discussion on Corral ");
                        Intent discussionIntent = new Intent("discbroadcast");
                        //Log.i("Place aaaaa",extras.get("Place").toString());
                        String n = extras.get("Name").toString();
                        Log.i("Intent Service",n);
                        String dbplace = extras.get("Place").toString();
                        String dbtype = extras.get("Type").toString();
                        String dborder = extras.get("Order").toString();


                        discussionIntent.putExtra("Place", extras.get("Place").toString());
                        discussionIntent.putExtra("Type", extras.get("Type").toString());
                        discussionIntent.putExtra("Order", extras.get("Order").toString());
                        discussionIntent.putExtra("Name",extras.get("Name").toString());
                        discussionIntent.putExtra("TimerName",extras.get("TimerName").toString());
                        /*Random random = new Random();
                        int m = random.nextInt(9999 - 1000) + 1000;
                        dbtimername = "timer" + m;

                        Log.i("New timer name:", dbtimername);
                        String tname=dbtimername;
                        Log.i("tname:", tname);

                        CountDownTimer dbtimername = new MyCountDownTimer(duration,interval,tname);
                        dbtimername.start();
                        Log.i("Timer kab chaloge", "plzzz");
                        //dbtimername.onTick(duration);
                        adddiscussiontoDB(n, dbplace, dbtype, dborder, tname);
                        //Discussion.listdiscussionFromDB();*/

                        sendBroadcast(discussionIntent);

                    }else if("REPLY".equals(extras.get("SM"))) {
                        Log.d(TAG, "onHandleIntent - RESPONSE ");
                        //sendNotification("New Discussion on Corral ");
                        Intent replyIntent = new Intent("replybroad");
                        //Log.i("Place aaaaa",extras.get("Place").toString());
                        String n = extras.get("Timestamp").toString();
                       // Log.i("time stamp Intent Service",n.toString());
                        //String dbplace = extras.get("Place").toString();
                        //String dbtype = extras.get("Type").toString();
                        //String dborder = extras.get("Order").toString();

                        replyIntent.putExtra("Sender_Name", extras.get("Sender_Name").toString());
                        replyIntent.putExtra("replymsg", extras.get("replymsg").toString());
                        replyIntent.putExtra("Timestamp", extras.get("Timestamp").toString());
                        replyIntent.putExtra("TimerName",extras.get("TimerName").toString());

                        sendBroadcast(replyIntent);

                    }
                     Log.i(TAG, "SERVER_MESSAGE: " + extras.toString());
                }
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }
   /* public void adddiscussiontoDB(String name, String dbplace, String dbtype, String dborder, String dbtimername){


        DiscussionOperations discDBoperation;
        discDBoperation = new DiscussionOperations(getApplicationContext());
        discDBoperation.open();
        discDBoperation.addDiscussion(name,dbplace,dbtype,dborder,dbtimername);
        //List list = discDBoperation.getAllUsers();
        //Log.d("User List",list.toString());
        discDBoperation.close();
        //listdiscussionFromDB();



    }*/

    private void sendNotification(String msg) {
        Log.d(TAG, "Preparing to send notification...: " + msg);
        mNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, DiscussionZone.class), 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(R.drawable.gcm_cloud)
                .setContentTitle("GCM XMPP Message")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);
        mBuilder.setContentIntent(contentIntent);
        //Random random = new Random();
        //int m = random.nextInt(9999 - 1000) + 1000;
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        Log.d(TAG, "Notification sent successfully.");

    }
}

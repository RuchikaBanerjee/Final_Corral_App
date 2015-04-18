package com.example.ruchika.corral_client.Database;

import android.content.Context;
import android.os.CountDownTimer;

import com.example.ruchika.corral_client.Additional_Features.Discussion;
import com.example.ruchika.corral_client.Additional_Features.DiscussionResponse;

/**
 * Created by ruchika on 4/17/15.
 */
public class MyCountDownTimer extends CountDownTimer {


    String dbtimername;
    Context context;
    @Override
    public void onFinish() {

        Discussion.del(dbtimername);
        DiscussionResponse.deleteReply(dbtimername);

    }

    @Override
    public void onTick(long millisUntilFinished) {

        //System.out.println("time: " + millisUntilFinished);
        //System.out.println("Timer: here I am");
    }

    public MyCountDownTimer(long duration, long interval, String dbtimername){

        super(duration,interval);
        this.dbtimername = dbtimername;


    }

}

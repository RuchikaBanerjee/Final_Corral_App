package com.example.ruchika.corral_client.Database;

/**
 * Created by ruchika on 4/18/15.
 */
public class MessageObject {

    String Sender_name;
    String msg_value;
    String timer_value;
    long timestamp;


    public MessageObject() {
    }


    public MessageObject(String sender_name, String msg_value, String timer_value, long timestamp) {
        Sender_name = sender_name;
        this.msg_value = msg_value;
        this.timer_value = timer_value;
        this.timestamp = timestamp;
    }

    public String getSender_name() {
        return Sender_name;
    }

    public void setSender_name(String sender_name) {
        Sender_name = sender_name;
    }

    public String getMsg_value() {
        return msg_value;
    }

    public void setMsg_value(String msg_value) {
        this.msg_value = msg_value;
    }

    public String getTimer_value() {
        return timer_value;
    }

    public void setTimer_value(String timer_value) {
        this.timer_value = timer_value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return Sender_name +" replied : " + msg_value;
    }
}

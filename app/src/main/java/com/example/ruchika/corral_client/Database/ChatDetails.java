package com.example.ruchika.corral_client.Database;

/**
 * Created by ruchika on 4/13/15.
 */
public class ChatDetails {

    private int id;
    private String name;
    private String regId;
    private String Message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getregId(){

        return this.regId;
    }
    public void setRegId(String regId){

        this.regId = regId;
    }

    public String getMessage(){

        return this.Message;
    }
    public void setMessage(String Message)
    {
        this.regId = Message;
    }

    @Override
    public String toString() {
        return name;
    }
}

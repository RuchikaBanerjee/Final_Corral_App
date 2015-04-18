package com.example.ruchika.corral_client.Database;

/**
 * Created by ruchika on 4/13/15.
 */
public class UserDetails {

    private int id;
    private String name;
    private String regId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
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


    @Override
    public String toString() {
        return name;
    }
}

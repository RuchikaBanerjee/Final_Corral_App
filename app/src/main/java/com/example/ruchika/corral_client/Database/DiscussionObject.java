package com.example.ruchika.corral_client.Database;

/**
 * Created by ruchika on 4/17/15.
 */
public class DiscussionObject {

    String place;
    String name;
    String order;
    String type;

    public DiscussionObject() {
    }

    String timername;

    public DiscussionObject(String name, String place, String type, String order, String timername) {
        this.place = place;
        this.name = name;
        this.order = order;
        this.type = type;
        this.timername = timername;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimername() {
        return timername;
    }

    public void setTimername(String timername) {
        this.timername = timername;
    }


    @Override
    public String toString() {
        return this.getName()+ "wants to order a" + this.type + " type of meal from " + this.place +" having a minimum order of" +
                this.order + " !" ;
    }
}

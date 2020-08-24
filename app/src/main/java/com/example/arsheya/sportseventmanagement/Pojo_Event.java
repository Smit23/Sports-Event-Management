package com.example.arsheya.sportseventmanagement;

import java.io.Serializable;

public class Pojo_Event implements Serializable {

    String event_id,event_name,category,setplayers,s_date,e_date,settime,eventdescription,setaddress,setfees,l_id,setimage;

    public String getEvent_id() {
        return event_id;
    }

    public String getEventdescription() { return eventdescription;   }

    public void setEventdescription(String eventdescription) { this.eventdescription = eventdescription;}

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSetplayers() {
        return setplayers;
    }

    public void setSetplayers(String setplayers) {
        this.setplayers = setplayers;
    }

    public String getS_date() {
        return s_date;
    }

    public void setS_date(String s_date) {
        this.s_date = s_date;
    }

    public String getE_date() {
        return e_date;
    }

    public void setE_date(String e_date) {
        this.e_date = e_date;
    }

    public String getSettime() {
        return settime;
    }

    public void setSettime(String settime) {
        this.settime = settime;
    }

    public String getSetaddress() {
        return setaddress;
    }

    public void setSetaddress(String setaddress) {
        this.setaddress = setaddress;
    }

    public String getSetfees() {
        return setfees;
    }

    public void setSetfees(String setfees) {
        this.setfees = setfees;
    }

    public String getL_id() {
        return l_id;
    }

    public void setL_id(String l_id) {
        this.l_id = l_id;
    }

    public String getSetimage() {
        return setimage;
    }

    public void setSetimage(String setimage) {
        this.setimage = setimage;
    }
}


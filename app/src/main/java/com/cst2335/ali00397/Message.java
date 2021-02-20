package com.cst2335.ali00397;

import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Message {

    private String firstName,lastName;
    private long id;



    public Message(String fn,String ln,long i){
        firstName = fn;
        lastName = ln;
        id = i;


    }




    public void update(String fn,String ln){
        firstName = fn;
        lastName = ln;



    }

    public Message(String fn, String ln) { this(fn, ln, 0);}



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    public String getLastName() {

        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

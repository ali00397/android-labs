package com.cst2335.ali00397;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Message {
//attributs, string, id, boolean: to see whether or not it's send message or receive message
    private String message;
    private boolean isSend;
    private int id;



    public Message(String message,Boolean isSend,int id){
       this.message = message;
       this.isSend = isSend;
       this.id = id;


    }

    public Message(String message,boolean isSend){
        this(message,isSend,0);


    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSend() {
        return isSend;
    }


    public void setSend(boolean send) {
        isSend = send;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

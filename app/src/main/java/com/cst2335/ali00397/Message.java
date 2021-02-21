package com.cst2335.ali00397;

import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Message {

    private String Message;
    private boolean isSend;
    private long id;

    //is it's send btn get clicked, isReceived value should be false

    // if it's rec btn clicked, isR = true


    public Message(String message,boolean isSend,long i){
        this.Message= message;
        this.isSend = isSend;
        id = i;


    }






    public void update(String message){
        this.Message = message;



    }

    public Message(String message, boolean isSend) { this(message, isSend, 0);}


    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

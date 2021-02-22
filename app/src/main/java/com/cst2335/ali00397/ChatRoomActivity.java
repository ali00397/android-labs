package com.cst2335.ali00397;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    private ArrayList<Message> list = new ArrayList<>();


   MyListAdapter ourAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);


        ListView lists = findViewById(R.id.list_item);
        lists.setAdapter(ourAdapter);
        Button addbutton = findViewById(R.id.send);
        Button addbuttons = findViewById(R.id.received);

        addbutton.setOnClickListener(bts ->{
            Message ourMessage = new Message("",true);
            ourMessage.isSend();
            ourAdapter.notifyDataSetChanged();
        });

        addbuttons.setOnClickListener(clk ->{
            Message myMessage = new Message("",false);
            myMessage.isSend();
            ourAdapter.notifyDataSetChanged();
        });

        lists.setOnItemLongClickListener((parents, view, positions, ids) -> {

            AlertDialog.Builder alertbox = new AlertDialog.Builder(ChatRoomActivity.this);
            alertbox.setTitle("Chat")

                    .setMessage("Do you want to delete this?")

                    .setPositiveButton("ADD", (click, arg) -> {


                        ourAdapter.notifyDataSetChanged();




                    })


                    .setNegativeButton("Remove", (bts, arg) -> {
                        ourAdapter.notifyDataSetChanged();


                    })

                    .setView(getLayoutInflater().inflate(R.layout.activity_chat_room, null))

                    .create().show();


            return false;
        });


        Log.e("ACTIVITY_NAME", "In function: onCreate properly");


    }





    public  class MyListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View old = null;
            View newView = old;

            LayoutInflater inflater = getLayoutInflater();


            //making a new view
            if(newView == null){

                newView = inflater.inflate(R.layout.activity_row_send,parent,false);
                newView = inflater.inflate(R.layout.activity_received_image,parent,false);


            }




            return null;
        }

    }


    }

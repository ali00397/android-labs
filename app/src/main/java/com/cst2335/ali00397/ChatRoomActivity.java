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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    private ArrayList<Message> list = new ArrayList<>();


   MyListAdapter ourAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);


        ListView lists = findViewById(R.id.list_item);
        lists.setAdapter(ourAdapter = new MyListAdapter());
        Button addbutton = findViewById(R.id.send);
        Button addbuttons = findViewById(R.id.received);

        EditText et = findViewById(R.id.theText);

        addbutton.setOnClickListener(bts ->{
            //get what was typed in
            Message ourMessage = new Message(et.getText().toString(),true);
            ourMessage.isSend();
            list.add(ourMessage);
            et.setText("");
            ourAdapter.notifyDataSetChanged();
        });

        addbuttons.setOnClickListener(clk ->{
            Message myMessage = new Message(et.getText().toString(),false);
            myMessage.isSend();
            list.add(myMessage);
            et.setText("");
            ourAdapter.notifyDataSetChanged();
        });

        lists.setOnItemLongClickListener((parents, view, positions, ids) -> {

            AlertDialog.Builder alertbox = new AlertDialog.Builder(ChatRoomActivity.this);
            alertbox.setTitle("Chat")

                    .setMessage("Do you want to delete this?")

                    .setPositiveButton("YES", (click, arg) -> {

                        list.remove(positions);



                        ourAdapter.notifyDataSetChanged();




                    })


                    .setNegativeButton("NO", (bts, arg) -> {
                        ourAdapter.notifyDataSetChanged();


                    })

                  //  .setView(getLayoutInflater().inflate(R.layout.activity_chat_room, null))

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

            Message thisMessage = list.get(position);
            //making a new view
            if(thisMessage.isSend()){

                newView = inflater.inflate(R.layout.activity_row_send,parent,false);



            }
            else
            {
                newView = inflater.inflate(R.layout.activity_received_image,parent,false);
            }
        TextView theText = newView.findViewById(R.id.theText);
        theText.setText(thisMessage.getMessage());


            return  newView;
        }

    }


    }

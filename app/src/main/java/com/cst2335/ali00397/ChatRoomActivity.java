package com.cst2335.ali00397;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    private ArrayList<Message> list = new ArrayList<>();
    MyListAdapter ourAdapter;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);


        ListView lists = findViewById(R.id.list_item);
        lists.setAdapter(ourAdapter);

        Button sendbutton = findViewById(R.id.send);
        Button receivedbutton = findViewById(R.id.received);

        sendbutton.setOnClickListener(bts ->{


            Message oldMessage = new Message("",true);
            oldMessage.isSend();
            ourAdapter.notifyDataSetChanged();
        });

        receivedbutton.setOnClickListener(clk ->{
            Message newMessage = new Message("",false);
            newMessage.isSend();
            ourAdapter.notifyDataSetChanged();
        });

        lists.setOnItemLongClickListener((parents, view, positions, ids) -> {

            AlertDialog.Builder alertbox = new AlertDialog.Builder(ChatRoomActivity.this);
            alertbox.setTitle("Chat")

                    .setMessage("Do you want to delete this?")

                    .setPositiveButton("No", (click, arg) -> {
                        ourAdapter.notifyDataSetChanged();

                    })


                    .setNegativeButton("Yes", (bts, arg) -> {
                        ourAdapter.notifyDataSetChanged();


                    })

                    .setView(getLayoutInflater().inflate(R.layout.activity_chat_room, null))

                    .create().show();


            return false;
        });


        Log.e("ACTIVITY_NAME", "In function: onCreate properly");


    }

    public void loadDataFromDatabase(){




    }


    public class MyOpener extends SQLiteOpenHelper{

        protected final static String DATABASE_NAME ="ContactsInfoDB";
        protected final static int VERSION_NUM = 1;
        private final static String TABLE_NAME ="CONTACTS";
        private final static String COL_FIRST_NAME = "FIRSTNAME";
        private final static String COL_LAST_NAME = "LASTNAME";
        private final static String COL_ID = "_id";




        public MyOpener(Context ctx)
        {
            super(ctx, DATABASE_NAME, null, VERSION_NUM);


    }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_FIRST_NAME + " text,"
                    + COL_LAST_NAME + " text);");  // add or remove columns
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

            //Create the new table:
            onCreate(db);

        }

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

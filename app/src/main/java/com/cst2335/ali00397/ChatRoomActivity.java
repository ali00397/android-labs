package com.cst2335.ali00397;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
import android.widget.TextView;

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
        lists.setAdapter(ourAdapter = new MyListAdapter());
        Button sendbutton = findViewById(R.id.send);
        Button receivedbutton = findViewById(R.id.received);
        EditText et = findViewById(R.id.theText);


        sendbutton.setOnClickListener(bts ->{


            Message oldMessage = new Message(et.getText().toString(),true);
            oldMessage.isSend();
            list.add(oldMessage);
            et.setText("");
            ourAdapter.notifyDataSetChanged();
        });

        receivedbutton.setOnClickListener(clk ->{
            Message newMessage = new Message(et.getText().toString(),false);
            newMessage.isSend();
            list.add(newMessage);
            et.setText("");
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
                        list.remove(positions);
                        ourAdapter.notifyDataSetChanged();


                    })

                    .setView(getLayoutInflater().inflate(R.layout.activity_chat_room, null))

                    .create().show();


            return false;
        });
        loadDataFromDatabase();

        Log.e("ACTIVITY_NAME", "In function: onCreate properly");


    }

    public void loadDataFromDatabase(){

        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();

    }

    public void printCursor(Cursor cursor , int version){
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();


        String [] columns = {MyOpener.COL_MESSAGE};
        //query all the results from the database:
        Cursor results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        //Now the results object has rows of results that match the query.
        //find the column indices:
        int emailColumnIndex = results.getColumnIndex(MyOpener.COL_MESSAGE);
        int nameColIndex = results.getColumnIndex(MyOpener.COL_ISSEND);
        int idColIndex = results.getColumnIndex(MyOpener.COL_ID);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext())
        {
            String name = results.getString(nameColIndex);
            String email = results.getString(emailColumnIndex);
            long id = results.getLong(idColIndex);

            Log.e("printCursor","working perfectly o not");
            Log.e("Database version:","Version is "+ version);
            Log.e("Number of rows:","Results are :" + cursor.getCount());
            Log.e("Column names",cursor.getColumnNames().toString());

    }


    }


    public class MyOpener extends SQLiteOpenHelper{


        protected final static String DATABASE_NAME ="ContactsInfoDB";
        protected final static int VERSION_NUM = 1;
        private final static String TABLE_NAME ="CONTACTS";
        private final static String COL_MESSAGE = "MESSAGE";
        private final static String COL_ISSEND = "ISSEND";
        private final static String COL_ID = "_id";




        public MyOpener(Context ctx)
        {
            super(ctx, DATABASE_NAME, null, VERSION_NUM);


    }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_MESSAGE + " text,"
                    + COL_ISSEND+ "text)");
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
                return list.get(position).getId();
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




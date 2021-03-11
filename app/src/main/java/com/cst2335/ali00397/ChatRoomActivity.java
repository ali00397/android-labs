package com.cst2335.ali00397;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    private ArrayList<Message> list = new ArrayList<>();
    MyListAdapter ourAdapter;
    MyOpener db;
    SharedPreferences ourpref = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        db = new MyOpener(this);

        ourpref = getSharedPreferences("Chat", Context.MODE_PRIVATE);
        String mysavedInfo = ourpref.getString("Important", "default value");
        EditText enteredField = findViewById(R.id.theText);
        enteredField.setText(mysavedInfo);

        ListView lists = findViewById(R.id.list_item);
        lists.setAdapter(ourAdapter = new MyListAdapter());
        Button sendbutton = findViewById(R.id.send);
        Button receivedbutton = findViewById(R.id.received);
        EditText et = findViewById(R.id.theText);





        loadDataFromDatabase();


        sendbutton.setOnClickListener(bts ->{

            {

                saveSharedPrefs(enteredField.getText().toString());

                Message oldMessage = new Message(et.getText().toString(),true);
                list.add(oldMessage);
                oldMessage.isSend();
                et.setText("");
                ourAdapter.notifyDataSetChanged();

        }






        });

        receivedbutton.setOnClickListener(clk ->{


            {

                saveSharedPrefs(enteredField.getText().toString());

                Message newMessage = new Message(et.getText().toString(),false);
                newMessage.isSend();
                list.add(newMessage);
                et.setText("");
                ourAdapter.notifyDataSetChanged();


        }



        });

       // Button insertbutton = findViewById(R.id.received);

      /** insertbutton.setOnClickListener(click -> {

            String message = et.getText().toString();
            String issend = et.getText().toString();

            ContentValues newRowValues = new ContentValues();

            newRowValues.put(MyOpener.COL_MESSAGE,message);
            newRowValues.put(MyOpener.COL_ISSEND,issend);

            long newId = db.insert(MyOpener.DATABASE_NAME, null, newRowValues);

            Message newMessages = new Message(message,issend,newId);

            list.add(newMessages);
            ourAdapter.notifyDataSetChanged();

            et.setText("");
            et.setText("");
            Toast.makeText(this, "Inserted item id:"+newId, Toast.LENGTH_LONG).show();

        });*/

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

                    //.setView(getLayoutInflater().inflate(R.layout.activity_chat_room, null))

                    .create().show();


            return false;
        });


        Log.e("ACTIVITY_NAME", "In function: onCreate properly");


    }

    private void saveSharedPrefs(String mysavedInfo){
        SharedPreferences.Editor editor = ourpref.edit();
        editor.putString("Important",mysavedInfo);
        editor.commit();


    }

    public void loadDataFromDatabase(){

        int version = 1;

        Log.e("printCursor","working perfectly o not");
        Log.e("Database version:","Version is "+ version);
        Log.e("Number of rows:","Results are :" +"2");
        Log.e("Column names","messages");
    }




    public class MyOpener extends SQLiteOpenHelper{


        protected final static String DATABASE_NAME ="ContactsInfoDB";
        protected final static int VERSION_NUM = 1;
        private final static String TABLE_NAME ="MESSAGES";
        private final static String COL_MESSAGE = "message";
        private final static String COL_ISSEND = "issend";
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




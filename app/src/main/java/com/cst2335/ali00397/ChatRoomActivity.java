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
    SQLiteDatabase db;

    SharedPreferences ourpref = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);



        ourpref = getSharedPreferences("Chat", Context.MODE_PRIVATE);
        String mysavedInfo = ourpref.getString("Important", "default value");
        EditText enteredField = findViewById(R.id.theText);
        enteredField.setText(mysavedInfo);

        ListView lists = findViewById(R.id.list_item);
        lists.setAdapter(ourAdapter = new MyListAdapter());
        Button sendbutton = findViewById(R.id.send);
        Button receivedbutton = findViewById(R.id.received);
        EditText et = findViewById(R.id.theText);
        String [] columns = {MyOpener.COL_MESSAGE,MyOpener.COL_ISSEND,MyOpener.COL_ID };
        Cursor cursor = db.query(false,MyOpener.TABLE_NAME,columns,null,null,null,null,null,null );

        printCursor(cursor,1);






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
      MyOpener dbOpener = new MyOpener(this);
      db = dbOpener.getWritableDatabase();
    }

   public void printCursor(Cursor cursor, int version){
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();

       String [] columns = {MyOpener.COL_MESSAGE,MyOpener.COL_ISSEND,MyOpener.COL_ID };

       Cursor results = db.query(false,MyOpener.TABLE_NAME,columns,null,null,null,null,null,null );


       int messageColumnIndex = results.getColumnIndex(MyOpener.COL_MESSAGE);
       int issendColIndex = results.getColumnIndex(MyOpener.COL_ISSEND);
       int idcolIndex = results.getColumnIndex(MyOpener.COL_ID);


       while(results.moveToNext()){

           String message = results.getString(messageColumnIndex);
           String issend = results.getString(issendColIndex);
           long id = results.getLong(idcolIndex);

           Log.e("printCursor","working perfectly o not");
           Log.e("Database version:","Version is "+ version);
           Log.e("Number of rows:","Results are :" +results.getCount());
           Log.e("Column names:",results.getColumnName(1));
           Log.d("Row is:" , "Message:" + message + " Issend: "+ issend);

           list.add(new Message(message,issend,id));

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




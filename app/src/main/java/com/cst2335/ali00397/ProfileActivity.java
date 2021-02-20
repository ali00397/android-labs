package com.cst2335.ali00397;

import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class ProfileActivity extends AppCompatActivity {
    private static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    ImageButton mImageButton;


    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME, "In function: onPaused ran without any issues");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.e(ACTIVITY_NAME, "In function: onStop ran without any issues");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME, "In function: onStart ran without any difficult");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //ImageButton mImageButton = findViewById(R.id.click);
            mImageButton.setImageBitmap(imageBitmap);


            Log.e(ACTIVITY_NAME, "In function: onActivityResult ran without any difficult");

        }
    }


    Button goToChat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snapshot_main);
        ImageButton mImageButton = findViewById(R.id.click);

        Intent fromMain = getIntent();
        fromMain.getStringExtra("Email");
        EditText emailEdittext = findViewById(R.id.edit2);
        emailEdittext.setText(fromMain.getStringExtra("EmailAddress"));
        Intent goTochatroom = new Intent(this, ChatRoomActivity.class);
        Button gotoChat = findViewById(R.id.goToChat);
        gotoChat.setOnClickListener(v -> {
            startActivity(goTochatroom);
        });


    }


    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onResume() {
        super.onResume();

        Log.e(ACTIVITY_NAME, "In function: onResume ran without any issues");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "In function: onDestroy ran without any issues");
    }


    private void dispatchTakePictureIntent() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);


        }


    }
}
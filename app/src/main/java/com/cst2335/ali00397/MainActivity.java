package com.cst2335.ali00397;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity {



    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME, "In function: onPaused ran without any issues");


        SharedPreferences mypref = getSharedPreferences("EmailAddress", MODE_PRIVATE);
        String savedText = mypref.getString("important","default string");
        EditText inputText = findViewById(R.id.edit);
        inputText.setText(savedText);
        Button loginInfo = findViewById(R.id.button3);
        loginInfo.setOnClickListener(clk -> onPause(inputText.getText().toString()));

    }

    private void onPause(String savedInfo) {
        SharedPreferences mypref = getSharedPreferences("EmailAddress", MODE_PRIVATE);
        SharedPreferences.Editor editors = mypref.edit();
        editors.putString("EmailAddress", savedInfo);
        editors.commit();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        Log.e(ACTIVITY_NAME, "In function: onCreate ran without any issues");

        Intent goToprofile = new Intent(this, ProfileActivity.class);

        Button loginInfo = findViewById(R.id.button3);
        loginInfo.setOnClickListener(bts -> {

            goToprofile.putExtra("Email", "EmailAddress");
            startActivity(goToprofile);


        });
    }
}
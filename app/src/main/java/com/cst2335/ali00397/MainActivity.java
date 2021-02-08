package com.cst2335.ali00397;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText input;

    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME,"In function: onPaused ran without any issues");


    SharedPreferences mypreferences = getSharedPreferences("EmailAddress",MODE_PRIVATE);
        SharedPreferences.Editor editors = mypreferences.edit();
        editors.putString("EmailAdress", input.getText().toString());
        editors.commit();

        input = findViewById(R.id.edit);
        Button loginInfo = findViewById(R.id.button3);

        loginInfo.setOnClickListener(clk-> onPause(input.getText().toString()));

    }

    private void onPause(String toString) {
        SharedPreferences mypref = getSharedPreferences("EmailAddress",MODE_PRIVATE);
        SharedPreferences.Editor editors = mypref.edit();
        editors.putString("EmailAdress", input.getText().toString());
        editors.commit();
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        Log.e(ACTIVITY_NAME,"In function: onCreate ran without any issues");

        Intent goToprofile = new Intent(this, ProfileActivity.class);

        Button loginInfo = findViewById(R.id.button3);
        loginInfo.setOnClickListener(bts ->goToprofile.putExtra("Email","EmailAddress"));
        startActivity(goToprofile);

        onPause();
        {

        };


    }
}
package com.cst2335.ali00397;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import static android.os.Build.VERSION_CODES.M;

public class MainActivity extends AppCompatActivity {
    EditText input;


    @Override
    protected void onPause() {
        super.onPause();

    SharedPreferences mypref = getSharedPreferences("EmailAddress",MODE_PRIVATE);
        SharedPreferences.Editor editors = mypref.edit();
        editors.putString("EmailAdress", input.getText().toString());
        editors.commit();

        input = findViewById(R.id.edit);
        Button loginInfo = findViewById(R.id.button3);

        loginInfo.setOnClickListener(clk-> onPause(input.getText().toString()));


       // String savedinfo = mypref.getString("important","initial String");
       // EditText input = findViewById(R.id.edit);
       // input.getText(savedinfo);
      //  Intent nextpages = new Intent(this,Activity_Second.class);
      //  nextpages.putExtra("EmailAdress","email");


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


        onPause();
        {
            









        };









    }
}
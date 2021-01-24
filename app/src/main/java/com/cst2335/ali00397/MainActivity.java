package com.cst2335.ali00397;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import static android.os.Build.VERSION_CODES.M;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gridlayout);
        getResources().getString(R.string.toast_message);
        TextView yourtext = findViewById(R.id.textView);
        Button btn = findViewById(R.id.button);
        CheckBox cb =  findViewById(R.id.checkBox);
        cb.setChecked(true);
        Switch cb1 = findViewById(R.id.switch1);
        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton cb1, boolean isChecked) {

               Snackbar.make(findViewById(R.id.button),"this pop message",Snackbar.LENGTH_LONG)
                       .setAction("Undo", click ->cb1.setChecked(false));


            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setText(R.string.toast_message);
                Toast.makeText(getApplicationContext(),R.string.hello_message,Toast.LENGTH_LONG).show();

            }
        });




    }
}
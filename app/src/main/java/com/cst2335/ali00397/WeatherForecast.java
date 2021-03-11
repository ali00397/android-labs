package com.cst2335.ali00397;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);


        ProgressBar progressBar = findViewById(R.id.weather);
        progressBar.setVisibility(View.VISIBLE);

        ForecastQuery qr = new ForecastQuery();
        qr.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");

    }


 private class ForecastQuery extends AsyncTask<String,Integer,String> {
        private String uv;
        private String min;
        private String max;
        private String current;
        private Bitmap image;



     @Override
     public String doInBackground(String... arg) {

         try {

             //create a URL object of what server to contact:
             URL url = new URL(arg[0]);

             //open the connection
             HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

             //wait for data:
             InputStream response = urlConnection.getInputStream();



             //From part 3: slide 19
             XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
             factory.setNamespaceAware(false);
             XmlPullParser xpp = factory.newPullParser();
             xpp.setInput( response  , "UTF-8");



             //From part 3, slide 20
             String parameter = null;

             int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT

             while(eventType != XmlPullParser.END_DOCUMENT)
             {

                 if(eventType == XmlPullParser.START_TAG)
                 {
                     //If you get here, then you are pointing at a start tag
                     if(xpp.getName().equals("Temperature"))
                     {
                         //If you get here, then you are pointing to a <Weather> start tag

                        min = xpp.getAttributeValue(null,    "min");
                         max = xpp.getAttributeValue(null, "max");
                          current = xpp.getAttributeValue(null,"value");
                     }else if(){
                         eventType = xpp.next(); //move to the next xml event and store it in a variable

                     }

                 }
                 }
         }
         catch (Exception e)
         {

         }

         publishProgress(25);
         publishProgress(50);
         publishProgress(75);

         return "result";
     }



     public void onProgressUpdate(Integer... arg){


     }


     public void onPostExecute(String fromDoInBackground){

         Log.e("ForecastQuery",fromDoInBackground);
     }
 }


 
 }
}
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
        myForecast cast = new myForecast();
        cast.execute("http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");
        myBitmapAsync bt = new myBitmapAsync();
        bt.execute("https://openweathermap.org/img/w/01d.png");
    }


 private class ForecastQuery extends AsyncTask<String,Integer,String> {


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

                         String outlook = xpp.getAttributeValue(null,    "min");
                         String windy = xpp.getAttributeValue(null, "max");
                         String value = xpp.getAttributeValue(null,"value");
                     }else{
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


 public class myForecast extends AsyncTask<String,Integer,String>{


     @Override
     protected String doInBackground(String... args) {

         try {

             URL url = new URL(args[0]);
             //open the connection
             HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

             //wait for data:
             InputStream response = urlConnection.getInputStream();

             //JSON reading:   Look at slide 26
             //Build the entire string response:
             BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
             StringBuilder sb = new StringBuilder();

             String line = null;
             while ((line = reader.readLine()) != null)
             {
                 sb.append(line + "\n");
             }
             String result = sb.toString(); //result is the whole string


             // convert string to JSON: Look at slide 27:
             JSONObject jObject = new JSONObject(result);

             //get the float associated with "value"
             float value = (float) jObject.getDouble("value");

             Log.i("MainActivity", "The uv is now: " + value) ;

         }catch (Exception e){
         }
         publishProgress(25);
         publishProgress(50);
         publishProgress(75);
         return "reply";

     }


     public void onProgressUpdate(Integer...args){


     }


     public void onPostExecute(String fromDoInBackgrounds){


     }



 }

 private Bitmap bitmap = null;
 private  Bitmap image = null;
 private String urlString = null;



 public class myBitmapAsync extends AsyncTask<String, Integer, String> {




     @Override
     protected String doInBackground(String... arg) {

         try {

             HttpURLConnection connection;
             URL url = new URL(urlString);
             connection = (HttpURLConnection) url.openConnection();
             connection.connect();
             int responseCode = connection.getResponseCode();
             if (responseCode == 200) {
                 image = BitmapFactory.decodeStream(connection.getInputStream());


             }

             FileOutputStream outputStream = openFileOutput( "01d" + ".png", Context.MODE_PRIVATE);
             image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
             outputStream.flush();
             outputStream.close();



         }catch (Exception e){



         }
         publishProgress(25);
         publishProgress(50);
         publishProgress(75);


         return "png";
     }

     public boolean fileExistance(String fname){
         File file = getBaseContext().getFileStreamPath(fname);
         return file.exists();
     }



     public void onProgressUpdate(Integer... arg){
         ProgressBar progressBar = findViewById(R.id.weather);
         progressBar.setVisibility(View.VISIBLE);
         progressBar.setVisibility(arg[0]);


     }



     public void onPostExecute(String fromDoInBackground){
         ProgressBar progressBar = findViewById(R.id.weather);
         progressBar.setVisibility(View.INVISIBLE);
         Log.e("ForecastQuery",fromDoInBackground);

     }
 }
}
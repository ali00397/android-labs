package com.cst2335.ali00397;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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


        ProgressBar bar3 = findViewById(R.id.progression1);
        bar3.setVisibility(View.VISIBLE);

        ForecastQuery qr = new ForecastQuery();
        qr.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");

    }

    public boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();   }



    private class ForecastQuery extends AsyncTask<String,Integer,String> {
        private String utralviolet;
        private String mininum;
        private String maxinum;
        private String current;
        private Bitmap image;

        @Override
        public String doInBackground(String... arg) {


            try {


                URL url = new URL(arg[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(response, "UTF-8");




                int eventType = xpp.getEventType();//The parser is currently at START_DOCUMENT
                while (eventType != XmlPullParser.END_DOCUMENT) {

                    if (eventType == XmlPullParser.START_TAG) {
                        //If you get here, then you are pointing at a start tag
                        if (xpp.getName().equals("Temperature")) {
                            //If you get here, then you are pointing to a <Weather> start tag

                            mininum = xpp.getAttributeValue(null, "min");
                            publishProgress(25);
                            maxinum = xpp.getAttributeValue(null, "max");
                            publishProgress(50);
                            current = xpp.getAttributeValue(null, "value");
                            publishProgress(75);
                        } else if (xpp.getName().equals("weather")) {
                            String iconName = xpp.getAttributeValue(null, "icon");
                            if (fileExistance(iconName + ".png")) {
                                FileInputStream fis = null;
                                try {
                                    fis = openFileInput(iconName + ".png");
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                image = BitmapFactory.decodeStream(fis);
                                publishProgress(100);


                                //eventType = xpp.next(); //move to the next xml event and store it in a variable

                            } else {
                                image = null;
                                String urlString = "http://openweathermap.org/img/w/" + iconName + ".png";
                                URL urlIcons = new URL(urlString);
                                HttpURLConnection Iconconnection = (HttpURLConnection) urlIcons.openConnection();
                                Iconconnection.connect();
                                int responseCode = Iconconnection.getResponseCode();
                                if (responseCode == 200) {
                                    image = BitmapFactory.decodeStream(Iconconnection.getInputStream());
                                    publishProgress(100);
                                }


                            }

                        }
                    }
                }
                eventType = xpp.next();
            } catch (Exception e) {
                e.printStackTrace();

            }

            try {

                String UVStrs = "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389";

                URL UVUrls = new URL(UVStrs);
                HttpURLConnection UVSConnection = (HttpURLConnection) UVUrls.openConnection();
                InputStream response = UVSConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString();
                JSONObject JeopardyObject = new JSONObject(result);
                utralviolet = JeopardyObject.getDouble("value") + "";


            } catch (Exception e) {

                e.printStackTrace();



            }


            return "done";

    }


        public void onProgressUpdate(Integer ... values){
            super.onProgressUpdate(values);
            ProgressBar bar = findViewById(R.id.progression1);
            bar.setVisibility(values[0]);
        }


        public void onPostExecute(String s){
            super.onPostExecute(s);
            TextView mininfo = findViewById(R.id.mins);
            mininfo.setText(getText(R.string.min) +": "+ mininum);
            TextView maxInfo = findViewById(R.id.maxs);
            maxInfo.setText(getText(R.string.max)+":  "+ maxinum);
            TextView currentInfo= findViewById(R.id.currents);
            currentInfo.setText(getText(R.string.current)+": "+ current);
            TextView UVInfo = findViewById(R.id.uvs);
            UVInfo.setText(getText(R.string.uv)+": "+ utralviolet);
            ImageView img = findViewById(R.id.imageView4);
            img.setImageBitmap(image);
            ProgressBar bar2 = findViewById(R.id.progression1);
            bar2.setVisibility(View.INVISIBLE);
        }







    }







}
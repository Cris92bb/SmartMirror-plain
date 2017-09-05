package com.example.sviluppo1.smartmirror_plain;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by sviluppo1 on 04/09/2017.
 */

public class WeatherGetter extends AsyncTask<String,Void,String>{


    private Exception exception;



    protected String doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try{
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                return WriteStream(in);
            }finally {
                urlConnection.disconnect();
            }
        }catch (Exception e){
            Log.v("DEBUG","Exception while getting response from REST "+e );
            return "";
        }
    }
    private String WriteStream(InputStream is){
        String response="";
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            String s=br.readLine();
            while (s!=null){
                response+=s;
                s=br.readLine();
            }
        }catch (Exception e){
            Log.v("ERROR", "Error while reading streeam "+e);
        }
        Log.v("REST","read: "+response);
        onPostExecute(response);
        return  response;
    }


    public static JSONObject retrievedObject;

    protected void onPostExecute(String feed) {
        // TODO: check this.exception
        // TODO: do something with the feed
        try {
            retrievedObject = new JSONObject(feed);
        }catch (Exception e){

        }
    }

}

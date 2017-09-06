package com.example.sviluppo1.smartmirror_plain;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sviluppo1 on 04/09/2017.
 */

public class WeatherGetter extends AsyncTask<Object,Void,String>{


    public static JSONObject retrievedObject;
    private Exception exception;

    protected String doInBackground(Object... urls) {
        try {
            URL url = new URL(urls[0].toString());
            MirrorActivity activity =(MirrorActivity) urls[1];
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try{
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                return WriteStream(in,activity);
            }finally {
                urlConnection.disconnect();
            }
        }catch (Exception e){
            Log.v("DEBUG","Exception while getting response from REST "+e );
            return "";
        }
    }

    private String WriteStream(InputStream is,MirrorActivity activity){
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
        try {
            retrievedObject = new JSONObject(response);
        }catch (Exception e){

        }
        activity.UpdateInfo();
        return  response;
    }

    protected void onPostExecute(String feed) {
        // TODO: check this.exception
        // TODO: do something with the feed
        try {
            retrievedObject = new JSONObject(feed);
        }catch (Exception e){

        }
    }

}

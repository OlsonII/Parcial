package com.olsonlabs.parcial;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class WebService {
    private final String URL = "https://api.deezer.com/playlist/93489551/tracks";
    private URL dezzerUrl = new URL(URL);
    private HttpsURLConnection connection;
    private boolean conected = false;
    private ArrayList allSings;

    public WebService() throws MalformedURLException {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    connection = (HttpsURLConnection) dezzerUrl.openConnection();
                    connection.setRequestProperty("User-Agent", "rest-app-v0.1");
                    if(connection.getResponseCode() == 200)
                        conected = true;
                    Get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ArrayList getAllSings() {
        return allSings;
    }

    public void Get() throws IOException {
        allSings = new ArrayList();
        connection.setRequestMethod("GET");
        InputStream responseBody = connection.getInputStream();
        InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
        JsonReader jsonReader = new JsonReader(responseBodyReader);
        allSings = parseSingJson(jsonReader);
    }

    public boolean isConected() {
        return conected;
    }

    public ArrayList parseSingJson(JsonReader jsonReader) throws IOException {
        Gson gson = new Gson();
        ArrayList<Sing> sings = new ArrayList<>();
        Sing sing;
        jsonReader.beginObject();
        while (jsonReader.hasNext()){
            String data = jsonReader.nextName();

            if(data.equals("data")){
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    sing = new Sing();
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()){
                        String name = jsonReader.nextName();
                        if(name.equalsIgnoreCase("title")){
                            sing.setTitle(jsonReader.nextString());
                            Log.d("sing Title", sing.getTitle());
                        }else if (name.equalsIgnoreCase("duration")){
                            sing.setDuration(Integer.parseInt(jsonReader.nextString()));
                        }else{
                            jsonReader.skipValue();
                        }
                        sings.add(sing);
                    }
                    jsonReader.endObject();
                }
                jsonReader.endArray();
            }else{
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return sings;
    }
}

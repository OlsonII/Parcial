package com.olsonlabs.parcial;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class WebServiceSearch extends AsyncTask<String, String, Sing> {

    private String URL = "https://api.deezer.com/search?q=";
    private java.net.URL dezzerUrl = new URL(URL);
    private HttpsURLConnection connection;
    private boolean conected = false;
    private Sing sing;



    public WebServiceSearch(String singName) throws MalformedURLException {
        URL = URL+singName;
        dezzerUrl = new URL(URL);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    connection = (HttpsURLConnection) dezzerUrl.openConnection();
                    connection.setRequestProperty("User-Agent", "rest-app-v0.1");
                    if(connection.getResponseCode() == 200)
                        conected = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected Sing doInBackground(String... strings) {
        try {
            sing = Get();
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        return sing;
    }

    @Override
    protected void onPostExecute(Sing sing) {
        super.onPostExecute(sing);
    }

    public Sing Get() throws ProtocolException {
        sing = new Sing();
        connection.setRequestMethod("GET");
        InputStream responseBody = null;
        try {
            responseBody = connection.getInputStream();
            InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
            JsonReader jsonReader = new JsonReader(responseBodyReader);
            sing = parseSingJson(jsonReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  sing;
    }

    public boolean isConected() {
        return conected;
    }

    public Sing parseSingJson(JsonReader jsonReader) throws IOException {
        ArrayList<Sing> sings = new ArrayList<>();
        jsonReader.beginObject();
        while (jsonReader.hasNext()){
            String data = jsonReader.nextName();
            if(data.equals("data")){
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    jsonReader.beginObject();
                    sing = new Sing();
                    while (jsonReader.hasNext()){
                        String name = jsonReader.nextName();
                        if(name.equalsIgnoreCase("title")){
                            sing.setTitle(jsonReader.nextString());
                            Log.d("sing Title", sing.getTitle());
                        }else if (name.equalsIgnoreCase("duration")){
                            sing.setDuration(Integer.parseInt(jsonReader.nextString()));
                        }else if(name.equalsIgnoreCase("artist")){
                            jsonReader.beginObject();
                            while (jsonReader.hasNext()){
                                String artistName = jsonReader.nextName();
                                if(artistName.equalsIgnoreCase("name")){
                                    sing.setSinger(jsonReader.nextString());
                                    return sing;
                                }else{
                                    jsonReader.skipValue();
                                }
                            }
                            jsonReader.endObject();
                        }
                        else {
                            jsonReader.skipValue();
                        }
                    }
                    jsonReader.endObject();
                }
                jsonReader.endArray();
            }else{
                jsonReader.skipValue();
            }
        }
        jsonReader.endObject();
        return sing;
    }
}

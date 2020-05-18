package com.olsonlabs.parcial;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class WebService extends AsyncTask<String, Object, ArrayList> {
    private final String URL = "https://api.deezer.com/playlist/93489551/tracks";
    private URL dezzerUrl = new URL(URL);
    private HttpsURLConnection connection;
    private boolean conected = false;
    private ArrayList allSings;



    public WebService() throws MalformedURLException {
        allSings = new ArrayList();
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
    protected ArrayList doInBackground(String... strings) {
        try {
            allSings = Get();
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        return allSings;
    }

    @Override
    protected void onPostExecute(ArrayList arrayList) {
        super.onPostExecute(arrayList);
//        Adapter adapter = new Adapter(arrayList);
        /*MainActivity mainActivity = new MainActivity();
        mainActivity.rv.setAdapter(new Adapter(arrayList));
        mainActivity.rv.setLayoutManager(new LinearLayoutManager(mainActivity));*/
    }

    public ArrayList Get() throws ProtocolException {
        allSings = new ArrayList();
        connection.setRequestMethod("GET");
        InputStream responseBody = null;
        try {
            responseBody = connection.getInputStream();
            InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
            JsonReader jsonReader = new JsonReader(responseBodyReader);
            allSings = parseSingJson(jsonReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  allSings;
    }

    public boolean isConected() {
        return conected;
    }

    public ArrayList<Sing> parseSingJson(JsonReader jsonReader) throws IOException {
        ArrayList<Sing> sings = new ArrayList<>();
        Sing sing;
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
                            sings.add(sing);
                        }else {
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
        return sings;
    }
}

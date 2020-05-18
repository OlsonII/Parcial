package com.olsonlabs.parcial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    public static ArrayList staticSings = new ArrayList<>();
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        try {
            WebService webService = new WebService();
            ArrayList list = webService.execute("").get();
            Log.d("size", String.valueOf(list.size()));
            adapter = new Adapter(list);
            rv.setAdapter(adapter);
            rv.setLayoutManager(new LinearLayoutManager(this));
            if(webService.isConected()) {
                Toast.makeText(this, "Conectado", Toast.LENGTH_SHORT);
            }
            else{
                Toast.makeText(this,  "Error", Toast.LENGTH_SHORT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

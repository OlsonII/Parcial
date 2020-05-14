package com.olsonlabs.parcial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            WebService webService = new WebService();
//            webService.Get();
            if(webService.isConected()) {
                Toast.makeText(this, "Conectado", Toast.LENGTH_SHORT);
            }
            else{
                Toast.makeText(this,  "Error", Toast.LENGTH_SHORT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

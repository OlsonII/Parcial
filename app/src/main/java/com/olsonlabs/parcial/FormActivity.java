package com.olsonlabs.parcial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

public class FormActivity extends AppCompatActivity {

    EditText titleEditText;
    TextView singerEditText;
    TextView durationEditText;
    Sing sing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        titleEditText = findViewById(R.id.edit_sing_title);
        singerEditText = findViewById(R.id.edit_sing_artist);
        durationEditText = findViewById(R.id.edit_sing_duration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                if(sing != null){
                    insertSing();
                    finish();
                }else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void searchSing(View view) throws MalformedURLException, ExecutionException, InterruptedException {
        WebServiceSearch webServiceSearch = new WebServiceSearch(titleEditText.getText().toString());
        sing = webServiceSearch.execute("").get();
        titleEditText.setText(sing.getTitle());
        singerEditText.setText(sing.getSinger());
        durationEditText.setText(sing.getDuration()+"");
    }



    public void insertSing(){
        Log.d("size new", MainActivity.singLists.size()+"");
        MainActivity.singLists.add(sing);
        Log.d("size new", MainActivity.singLists.size()+"");
    }
}

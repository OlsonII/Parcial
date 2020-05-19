package com.olsonlabs.parcial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class FormActivity extends AppCompatActivity {

    EditText titleEditText;
    EditText singerEditText;
    EditText durationEditText;

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
                insertSing();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void insertSing(){
        Log.d("size new", MainActivity.singLists.size()+"");
        Sing sing = new Sing();
        sing.setTitle(titleEditText.getText().toString());
        sing.setSinger(singerEditText.getText().toString());
        sing.setDuration(Integer.parseInt(durationEditText.getText().toString()));
        MainActivity.singLists.add(sing);
        Log.d("size new", MainActivity.singLists.size()+"");
    }
}

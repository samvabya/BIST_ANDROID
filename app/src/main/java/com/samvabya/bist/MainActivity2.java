package com.samvabya.bist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button btn = findViewById(R.id.button5);
        DatabaseHandler db = new DatabaseHandler(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Contact> contacts = db.getAllContacts();
                for (Contact cn : contacts) {
                    db.deleteCourse(cn.getName());
                }
                Toast.makeText(getApplicationContext(),"All Data Deleted!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
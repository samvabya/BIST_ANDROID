package com.samvabya.bist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView t,t4,t5;
    private EditText e;
    private Button b1,b2,b3,b4;
    private DatabaseHandler db;
    private String S4,S5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHandler(this);
        t = findViewById(R.id.textView);
        t4 = findViewById(R.id.textView4);
        t5 = findViewById(R.id.textView5);
        e = findViewById(R.id.editTextNumber);
        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);

        String date;
        Calendar calendar;
        SimpleDateFormat simpleDateFormat;

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = simpleDateFormat.format(calendar.getTime()).toString();
        write(date);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                S4 = "";
                S5 = "";
                String T = e.getText().toString();
                if (T.isEmpty()) {
                    write("Data cannot be empty!");
                    return;
                }
                List<Contact> contacts = db.getAllContacts();
                int r = 0;
                for (Contact cn : contacts) {
                    if (cn.getName().equals(date)) {
                        r++;
                    }
                }
                if (r==0) {
                    db.addContact(new Contact(date, T));
                    write("Data Added!");
                }
                else {
                    write("Data Already Exists!");
                }
                display();
                e.setText("");
                hide();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int total=0;
                List<Contact> contacts = db.getAllContacts();
                for (Contact cn : contacts) {
                    total+= Integer.parseInt(cn.getPhoneNumber());
                }
                write("Total = "+total);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int r = 0;
                List<Contact> contacts = db.getAllContacts();
                for (Contact cn : contacts) {
                    if (cn.getName().equals(date)) {
                        r++;
                    }
                }
                if (r!=0) {
                    db.deleteCourse(date);
                    write("Data Deleted!");
                }
                else {
                    write("Data Does Not Exist!");
                }
                display();
                hide();
            }
        });
    }

    public void display() {
        List<Contact> contacts = db.getAllContacts();
        S4 = ""; S5 = "";

        for (Contact cn : contacts) {
            S4 += cn.getName()+"\n";
            S5 += cn.getPhoneNumber()+"\n";
        }
        t4.setText(S4);
        t5.setText(S5);
    }
    public void write(String p) {
        Toast.makeText(getApplicationContext(),p, Toast.LENGTH_SHORT).show();
        t.setText(p);
    }
    public void hide() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
package com.example.arsheya.sportseventmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class Event_Details extends AppCompatActivity {
TextView name, category, startdate, enddate,address,fees,time,eventdescription;
ImageView backimg;
CircleImageView imgview2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event__details);
        name = findViewById(R.id.name);
        category = findViewById(R.id.category);
        startdate = findViewById(R.id.startdate);
        enddate = findViewById(R.id.enddate);
        address = findViewById(R.id.address);
        fees = findViewById(R.id.fees);
        time = findViewById(R.id.time);
        eventdescription = findViewById(R.id.eventdescription);
        backimg = findViewById(R.id.backimg);
        imgview2 = findViewById(R.id.imgview2);
        Intent i = getIntent();
        Pojo_Event obj = new Pojo_Event();
        obj = (Pojo_Event) i.getSerializableExtra("obj");
        name.setText(obj.getEvent_name().toString());
        category.setText(obj.getCategory().toString());
        startdate.setText(obj.getS_date().toString());
        enddate.setText(obj.getE_date().toString());
        address.setText(obj.getSetaddress().toString());
        fees.setText(obj.getSetfees().toString());
        time.setText(obj.getSettime().toString());
        eventdescription.setText(obj.getEventdescription().toString());

        Commonclass co = new Commonclass();

        URL url = co.getUrl(obj.getSetimage());
        Glide.with(Event_Details.this)
             .load(url)
              .into(imgview2);

        Glide.with(Event_Details.this)
                .load(url)
                .into(backimg);
        backimg.setAlpha(100);
    }

}

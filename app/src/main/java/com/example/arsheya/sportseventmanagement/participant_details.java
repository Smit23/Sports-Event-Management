package com.example.arsheya.sportseventmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class participant_details extends AppCompatActivity {
    TextView name, category, firstname, lastname,email,phonenumber,eventname;
    ImageView backimg;
    CircleImageView imgview2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_details);
        name = findViewById(R.id.name);
        category = findViewById(R.id.category);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        eventname = findViewById(R.id.eventname);


        backimg = findViewById(R.id.backimg);
        imgview2 = findViewById(R.id.imgview2);
        phonenumber = findViewById(R.id.phonenumber);
        Intent i = getIntent();
        Pojo_Participant_List obj = new Pojo_Participant_List();
        obj = (Pojo_Participant_List) i.getSerializableExtra("obj");
        name.setText(obj.getFirstName().toString());
        category.setText(obj.getCategory().toString());
        firstname.setText(obj.getFirstName().toString());
        lastname.setText(obj.getLastName().toString());
        email.setText(obj.getEmail().toString());
        phonenumber.setText(obj.getPhoneNumber().toString());
        eventname.setText(obj.getEvent_name().toString());


        Commonclass co = new Commonclass();

        URL url = co.getUrl(obj.getImage());
        Glide.with(participant_details.this)
                .load(url)
                .into(imgview2);

        Glide.with(participant_details.this)
                .load(url)
                .into(backimg);
        backimg.setAlpha(100);
    }


}

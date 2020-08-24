package com.example.arsheya.sportseventmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyEventDetail extends AppCompatActivity {
    Pojo_searchevent obj;
    TextView name, category, startdate, enddate,address,fees,time;
    String pid,eolid,eid,status,no;
    SharedPreferences sp;
    ImageView backimg;
    CircleImageView imgview2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event_detail); sp   = getSharedPreferences("Mypref",MODE_PRIVATE);
        pid = sp.getString("lid",null);
        name = findViewById(R.id.name);
        category = findViewById(R.id.category);
        startdate = findViewById(R.id.startdate);
        enddate = findViewById(R.id.enddate);
        address = findViewById(R.id.address);
        fees = findViewById(R.id.fees);
        time = findViewById(R.id.time);
        backimg = findViewById(R.id.backimg);
        imgview2 = findViewById(R.id.imgview2);
        obj = new Pojo_searchevent();
        Intent i = getIntent();
        obj = (Pojo_searchevent) i.getSerializableExtra("obj");
        eolid = obj.getL_id();
        eid=obj.getEvent_id();
        no = obj.getPhoneNumber();

        name.setText(obj.getEvent_name().toString());
        category.setText(obj.getCategory_search().toString());
        startdate.setText(obj.getS_date().toString());
        enddate.setText(obj.getE_date().toString());
        address.setText(obj.getAddress_search().toString());
        fees.setText(obj.getFees_seacrh().toString());
        time.setText(obj.getTime_search().toString());

        Commonclass co = new Commonclass();

        URL url = co.getUrl(obj.getEimage());
        Glide.with(MyEventDetail.this)
                .load(url)
                .into(imgview2);

        Glide.with(MyEventDetail.this)
                .load(url)
                .into(backimg);
        backimg.setAlpha(100);
    }
    public void onchat(View v)
    {
        String url = "https://api.whatsapp.com/send?phone="+"+91"+no;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}

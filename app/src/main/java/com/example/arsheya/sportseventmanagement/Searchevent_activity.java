package com.example.arsheya.sportseventmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Searchevent_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchevent_activity);

    }
    public void oncricket(View view)
    {
        Intent i=new Intent(Searchevent_activity.this,Search_event_display_activity.class);
        i.putExtra("category","Cricket");
        startActivity(i);
    }
    public void onfootball(View view)
    {
        Intent i=new Intent(Searchevent_activity.this,Search_event_display_activity.class);
        i.putExtra("category","Football");
        startActivity(i);

    }
    public void onvolleyball(View view)
    {
        Intent i=new Intent(Searchevent_activity.this,Search_event_display_activity.class);
        i.putExtra("category","Volleyball");
        startActivity(i);

    }
    public void onkarate(View view)
    {
        Intent i=new Intent(Searchevent_activity.this,Search_event_display_activity.class);
        i.putExtra("category","Karate");
        startActivity(i);
    }


}


package com.example.arsheya.sportseventmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class Eventdetails_players extends AppCompatActivity {
    Pojo_searchevent obj;
    TextView name, category, startdate, enddate,address,fees,time;
    String pid,eolid,eid,status,no;
    SharedPreferences sp ;
    ImageView backimg;
    CircleImageView imgview2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventdetails_players);
        sp   = getSharedPreferences("Mypref",MODE_PRIVATE);
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
        Glide.with(Eventdetails_players.this)
                .load(url)
                .into(imgview2);

        Glide.with(Eventdetails_players.this)
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
//
    public void Participate(View view)
    {
        myclass myclass =new myclass();
        myclass.execute();
    }
    class myclass extends AsyncTask<Void,Void,String>
    {
        String msg;

        JSONObject jsonObject;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            jsonObject=new JSONObject();

            try {
                jsonObject.put("plid",pid);
                jsonObject.put("eid",eid);
                jsonObject.put("eolid",eolid);
                jsonObject.put("status",status);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(Void... voids) {


            try
            {
                //URL url = new URL("http://192.168.0.104:8080/PhpProject1/book.php");
                // commonurl commonurl=new commonurl();
                //  String u=commonurl.geturl("productsdelete.php");
                //  URL url=new URL("http://10.0.0.4;/PhpProject2/login.php");
                Commonclass cm = new Commonclass();
                URL url = cm.getUrl("participant.php");
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type","application/json");
                httpURLConnection.setRequestProperty("Accept","application/json");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();


                //The number of bytes written to the data output stream so far
                DataOutputStream outputStream=new DataOutputStream(httpURLConnection.getOutputStream());

                // the specified byte array starting at offset off to the underlying output stream
                outputStream.write(jsonObject.toString().getBytes());

                int code=httpURLConnection.getResponseCode();
                if(code==200)
                {
                    //Reads text from a character-input stream, buffering characters so as to provide for the efficient reading of characters, arrays, and lines.
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    //r objects are like String objects, except that they can be modified
                    //not thread safe mutable
                    StringBuilder stringBuilder=new StringBuilder();
                    String s;
                    //Reads a line of text.
                    while ((s=bufferedReader.readLine()) !=null)
                    {
                        stringBuilder.append(s);

                    }
                    JSONObject object=new JSONObject(stringBuilder.toString());
                   msg =object.getString("res");

                    //
                }


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


            return msg;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();

        }


    }

}

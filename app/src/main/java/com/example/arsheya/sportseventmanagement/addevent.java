package com.example.arsheya.sportseventmanagement;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.content.Intent;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class addevent extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    EditText eventname ,players ,sdate ,edate ,time ,address ,fees,eventdescription;
    Spinner spinner;
    Button add;
    ArrayList<String> Role = new ArrayList<>();
    Uri uri = null;
    String name;
String uid;
    SharedPreferences sp ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addevent);
        eventname = findViewById(R.id.eventname);
        players = findViewById(R.id.players);
        sdate = findViewById(R.id.sdate);
        edate = findViewById(R.id.edate);
        time = findViewById(R.id.time);
        eventdescription = findViewById(R.id.eventdescription);
       // edate = (EditText) findViewById(R.id.eedate);
        edate.setFocusableInTouchMode(false);
        sdate.setFocusableInTouchMode(false);
        edate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esetDatePicker(edate);
            }
        });
        sdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDatePicker(sdate);
            }
        });
        //time = (EditText) findViewById(R.id.etime);
        time.setFocusableInTouchMode(false);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimePicker(true, time);
            }
        });
        address = findViewById(R.id.address);
        fees = findViewById(R.id.fees);
        spinner = findViewById(R.id.spinner);
     sp   = getSharedPreferences("Mypref",MODE_PRIVATE);
     uid = sp.getString("lid",null);
        Role.add("Cricket");
        Role.add("FootBall");
        Role.add("VolleyBall");
        Role.add("Karate");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,Role);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }




    private void setTimePicker(boolean is24r, final EditText et) {
        Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("hh:mm");
        TimePickerDialog timePickerDialog = new TimePickerDialog(addevent.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar calNow = Calendar.getInstance();
                        Calendar calSet = (Calendar) calNow.clone();
                        calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calSet.set(Calendar.MINUTE, minute);
                        calSet.set(Calendar.SECOND, 0);
                        calSet.set(Calendar.MILLISECOND, 0);
                        time.setText("" + dateFormatter.format(calSet.getTime()));
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), is24r);
        timePickerDialog.setTitle("Set Time");
        timePickerDialog.show();
    }

    private void setDatePicker(final EditText et) {
        Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        DatePickerDialog datePickerDialog = new DatePickerDialog(addevent.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                sdate.setText(dateFormatter.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        datePickerDialog.setTitle("Select Date");
        datePickerDialog.show();
    }
    private void esetDatePicker(final EditText et) {
        Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        DatePickerDialog datePickerDialog = new DatePickerDialog(addevent.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                edate.setText(dateFormatter.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        datePickerDialog.setTitle("Select Date");
        datePickerDialog.show();
    }


    public void imageupload(View view) {


        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,1);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((data != null)&&(requestCode == 1) &&(resultCode == RESULT_OK))
        {
            try {
                uri= data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                ImageView imageView = (ImageView) findViewById(R.id.img);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void displayToastmsg(View view){
        myclass obj = new myclass();
        obj.execute();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        name = Role.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    class myclass extends AsyncTask<Void, Void, String>
    {
        JSONObject jsonObject=new JSONObject();
        String msg;
        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            try {
                jsonObject.put("event_name",eventname.getText().toString());
                jsonObject.put("players",players.getText().toString());
                jsonObject.put("s_date",sdate.getText().toString());
                jsonObject.put("e_date",edate.getText().toString());
                jsonObject.put("time",time.getText().toString());
                jsonObject.put("address",address.getText().toString());
                jsonObject.put("fees",fees.getText().toString());
                jsonObject.put("lid",uid);
                jsonObject.put("category",name);
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
                //URL url=new URL("http://10.0.0.12/PhpProject2/");
                Commonclass cm = new Commonclass();
                URL url = cm.getUrl("event.php");
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
                    msg=object.getString("res");
                    String lid = null;
                    lid = object.getString("lid");
                    imageupload_event imageUpload = new imageupload_event();
                    JSONObject jsonObject =new JSONObject();
                    jsonObject = imageUpload.callWS(uri,addevent.this,lid);

                    try {
                        String str = jsonObject.getString("msg");
                        Toast.makeText(addevent.this,str,Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


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
            Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
            eventname.setText("");

        }
    }



}



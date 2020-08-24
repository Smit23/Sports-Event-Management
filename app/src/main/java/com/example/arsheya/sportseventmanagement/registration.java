package com.example.arsheya.sportseventmanagement;

import android.app.DatePickerDialog;
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

public class registration extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    EditText etfirstname,etlastname,etemail,etphone,etbirthdate,etpassword,etcpassword;
    Spinner spinner;
    Button register;
    ArrayList<String> Role = new ArrayList<>();
    Uri uri = null;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        etfirstname = findViewById(R.id.etfirstname);
        etlastname = findViewById(R.id.etlastname);
        etemail = findViewById(R.id.etemail);
        etphone = findViewById(R.id.etphone);
        etbirthdate = findViewById(R.id.etbirthdate);
        etpassword = findViewById(R.id.etpassword);
        etcpassword = findViewById(R.id.etcpassword);
        spinner = findViewById(R.id.spinner);
        Role.add("Player");
        Role.add("Event Organizer");
        Role.add("Distributor");
        etbirthdate.setFocusableInTouchMode(false);
        etbirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDatePicker(etbirthdate);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,Role);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
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
    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();

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
String lid = null;




    private void setDatePicker(final EditText et) {
        Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        DatePickerDialog datePickerDialog = new DatePickerDialog(registration.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etbirthdate.setText(dateFormatter.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        datePickerDialog.setTitle("Select Date");
        datePickerDialog.show();
    }
    class myclass extends AsyncTask<Void, Void, String>
    {
        JSONObject jsonObject=new JSONObject();
        String msg;
        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            try {
                jsonObject.put("FirstName",etfirstname.getText().toString());
                jsonObject.put("LastName",etlastname.getText().toString());
                jsonObject.put("Email",etemail.getText().toString());
                jsonObject.put("PhoneNumber",etphone.getText().toString());
                jsonObject.put("Password",etpassword.getText().toString());
                jsonObject.put("BirthDate",etbirthdate.getText().toString());
                jsonObject.put("Role",name);
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
                URL url = cm.getUrl("registration.php");
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
lid = null;
lid = object.getString("lid");
                    imageupload imageUpload = new imageupload();
                    JSONObject jsonObject =new JSONObject();
                    jsonObject = imageUpload.callWS(uri,registration.this,lid);

                    try {
                        String str = jsonObject.getString("msg");
                        Toast.makeText(registration.this,str,Toast.LENGTH_SHORT).show();
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
            etfirstname.setText("");
            SharedPreferences sp = getSharedPreferences("Mypref",MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("lid",lid);
          //  String role =object.getString("role");
            editor.putString("role",name);
            editor.putBoolean("status",true);
            editor.commit();
         //   msg =object.getString("status");
            if(name.equals("Player"))
            {
                Intent i = new Intent(registration.this,player.class);
                startActivity(i);
                //player ni home screen ma redirect karvanu
            }
            else if(name.equals("Event Organizer"))
            {
                Intent i = new Intent(registration.this,organizer.class);
                startActivity(i);
            }
            else {

            }


        }
    }



}



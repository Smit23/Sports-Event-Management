package com.example.arsheya.sportseventmanagement;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText editText, editText2;
    Button button, button2;
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        textView2 = findViewById(R.id.textView2);
        SharedPreferences sp = getSharedPreferences("Mypref",MODE_PRIVATE);
boolean st = sp.getBoolean("status",false);
if(st)
{
    String r = sp.getString("role",null);
    if(r.equals("Player"))
    {
        Intent i = new Intent(MainActivity.this,player.class);
        startActivity(i);
        //player ni home screen ma redirect karvanu
    }
    else if(r.equals("Event Organizer"))
    {
        Intent i = new Intent(MainActivity.this,organizer.class);
        startActivity(i);
    }
    else {

    }

}
else
{

}
    }
    public void Onlogin(View view)
    {
        myclass myclassobj = new myclass();
        myclassobj.execute();

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
                jsonObject.put("email",editText.getText().toString());
                jsonObject.put("password",editText2.getText().toString());


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
URL url = cm.getUrl("login.php");
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
                    String lid =object.getString("lid");

                    SharedPreferences sp = getSharedPreferences("Mypref",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("lid",lid);
                    String role =object.getString("role");
                    editor.putString("role",role);
                    editor.putBoolean("status",true);
                    editor.commit();
                    msg =object.getString("status");
                    if(role.equals("Player"))
                    {
                        Intent i = new Intent(MainActivity.this,player.class);
                        startActivity(i);
                        //player ni home screen ma redirect karvanu
                    }
                    else if(role.equals("Event Organizer"))
                    {
                        Intent i = new Intent(MainActivity.this,organizer.class);
                        startActivity(i);
                    }
                    else {
                        Intent i = new Intent(MainActivity.this,distributorhome.class);
                        startActivity(i);

                    }
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

    public void but2 (View view)
    {
        Intent i = new Intent(MainActivity.this, registration.class);
        startActivity(i);
    }
}


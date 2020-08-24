package com.example.arsheya.sportseventmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class myevent extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ///step-1 arraylist

ArrayList<Pojo_Event> Arrcm  = new ArrayList<>();
ProgressDialog progressDialog;
String lid;
    ListView listView;
    SharedPreferences sp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myevent);
        sp   = getSharedPreferences("Mypref",MODE_PRIVATE);
        lid = sp.getString("lid",null);
         listView =findViewById(R.id.lvview);
         listView.setOnItemClickListener(this);
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(myevent.this,Event_Details.class);
        i.putExtra("obj",Arrcm.get(position));
        startActivity(i);

    }

    public class MyAsyncTask extends AsyncTask<String, Void, String> {
        JSONObject jsonObject;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            jsonObject = new JSONObject();
            try {
                jsonObject.put("lid",lid);
            } catch (Exception e) {
                e.printStackTrace();
            }
            progressDialog = new ProgressDialog(myevent.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Commonclass cm = new Commonclass();
                ///chng name
                URL url = cm.getUrl("eventview.php");
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


                int res = httpURLConnection.getResponseCode();
                if (res == 200) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                    String line = "";
                    StringBuilder response = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    JSONObject jsonObject1 = new JSONObject(response.toString());
                    JSONArray prodsJsonArr = jsonObject1.getJSONArray("response");
                    for (int i = 0; i < prodsJsonArr.length(); i++) {
                        JSONObject jsonObject2 = prodsJsonArr.getJSONObject(i);
//3event_id,event_name,category,setplayers,s_date,e_date,settime,setaddress,setfees,l_id,setimage;
                        Pojo_Event arr = new Pojo_Event();
                        arr.setEvent_name(jsonObject2.getString("event_name").toString());
                        arr.setEvent_id(jsonObject2.getString("event_id").toString());
                        arr.setSetimage(jsonObject2.getString("image").toString());
                        arr.setCategory(jsonObject2.getString("category").toString());
                        arr.setE_date(jsonObject2.getString("e_date").toString());
                        arr.setSettime(jsonObject2.getString("time").toString());
                        arr.setL_id(jsonObject2.getString("l_id").toString());
                        arr.setSetplayers(jsonObject2.getString("players").toString());
                        arr.setS_date(jsonObject2.getString("s_date").toString());
                        arr.setSetaddress(jsonObject2.getString("address").toString());
                        arr.setSetfees(jsonObject2.getString("fees").toString());
arr.setEventdescription(jsonObject2.getString("eventdescription").toString());
                        Arrcm.add(arr);

                    }

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String pname) {

            ///class custom gallery
            CustomEvent custom_gallery = new CustomEvent(getApplicationContext(), Arrcm);
            listView.setAdapter(custom_gallery);
            progressDialog.dismiss();
        }
    }
}
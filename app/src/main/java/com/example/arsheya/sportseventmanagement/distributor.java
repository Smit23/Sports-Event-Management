package com.example.arsheya.sportseventmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class distributor extends AppCompatActivity implements AdapterView.OnItemClickListener ,Deleget{

    ///step-1 arraylist

    ArrayList<Pojo_Distributor> Arrcm  = new ArrayList<>();
    ProgressDialog progressDialog;
    String lid;
    ListView listView;
    SharedPreferences sp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor);
        sp   = getSharedPreferences("Mypref",MODE_PRIVATE);
        lid = sp.getString("lid",null);
        listView =findViewById(R.id.lvview);
        listView.setOnItemClickListener(this);
       MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent i = new Intent(distributor.this,Event_Details.class);
//        i.putExtra("obj",Arrcm.get(position));
//        startActivity(i);

    }

    @Override
    public void onclicke(Pojo_Distributor p) {

        String url = "https://api.whatsapp.com/send?phone="+"+91"+p.getPno();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);

    }

    public class MyAsyncTask extends AsyncTask<String, Void, String> {
        JSONObject jsonObject;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            jsonObject = new JSONObject();
            try {
                //jsonObject.put("lid",lid);
            } catch (Exception e) {
                e.printStackTrace();
            }
            progressDialog = new ProgressDialog(distributor.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Commonclass cm = new Commonclass();
                ///chng name
                URL url = cm.getUrl("ViewDistributor.php");
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
               // outputStream.write(jsonObject.toString().getBytes());


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

//{"response":[{"id":"3","fname":"bansi","lname":"sojitra","email":"bansi@gmail.com","pno":"9429482915","image":""},{"id":"4","fname":"smit","lname":"mehta","email":"s@gmail.com","pno":"000123","image":"Registration\/4.png"}]}
                        Pojo_Distributor arr = new Pojo_Distributor();
                        arr.setImage(jsonObject2.getString("image").toString());
                        arr.setId(jsonObject2.getString("id").toString());
                        arr.setEmail(jsonObject2.getString("email").toString());
arr.setLname(jsonObject2.getString("lname").toString());
arr.setFname(jsonObject2.getString("fname").toString());
arr.setPno(jsonObject2.getString("pno").toString());
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
            CustomDistribute custom_gallery = new CustomDistribute(getApplicationContext(), Arrcm,distributor.this);
            listView.setAdapter(custom_gallery);
            progressDialog.dismiss();
        }
    }
    //Intent i = new Intent(distributor.this, .class);
//        startActivity(i);
}


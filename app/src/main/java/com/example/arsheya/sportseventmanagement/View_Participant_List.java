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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class View_Participant_List extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ///step-1 arraylist

    ArrayList<Pojo_Participant_List> Arrcm  = new ArrayList<>();
    ProgressDialog progressDialog;
    String lid;
    ListView listView;
    SharedPreferences sp ;
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__participant__list);
        Intent i = getIntent();
        category = i.getStringExtra("category");
        sp   = getSharedPreferences("Mypref",MODE_PRIVATE);
        lid = sp.getString("lid",null);
        listView =findViewById(R.id.lvview);
        listView.setOnItemClickListener(this);
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


       Intent i = new Intent(View_Participant_List.this,participant_details.class);
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
                jsonObject.put("category",category);
            } catch (Exception e) {
                e.printStackTrace();
            }
            progressDialog = new ProgressDialog(View_Participant_List.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Commonclass cm = new Commonclass();
                ///chng name
                URL url = cm.getUrl("Eventorgnaztionviewparticipatelist.php");
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


                      //  $rowArr = array("ID"=>$ID,"First Name"=>$FirstName,"Last Name"=>$lastname,"partiid"=>$partiid,
                        // "datetime"=>$datetime,"Email"=>$email,"image"=>$image,"event_id"=>$eventid,
                        //
                        // "event_name"=>$eventname,"category"=>$category,"s_date"=>$s_date,"e_date"=>$e_date,"fees"=>$fees);

                        Pojo_Participant_List arr = new Pojo_Participant_List();
                        arr.setId(jsonObject2.getString("ID").toString());
                        arr.setFirstName(jsonObject2.getString("First Name").toString());
                        arr.setLastName(jsonObject2.getString("Last Name").toString());
                        arr.setPartiid(jsonObject2.getString("partiid").toString());
                        arr.setDatetime(jsonObject2.getString("datetime").toString());
                        arr.setEmail(jsonObject2.getString("Email").toString());
                        arr.setImage(jsonObject2.getString("image").toString());
                        arr.setEvent_id(jsonObject2.getString("event_id").toString());
                        arr.setEvent_name(jsonObject2.getString("event_name").toString());
                        arr.setCategory(jsonObject2.getString("category").toString());
                        arr.setPhoneNumber(jsonObject2.getString("Phone Number"));
                        arr.setS_date(jsonObject2.getString("s_date").toString());
                        arr.setE_date(jsonObject2.getString("e_date").toString());
                        arr.setFees(jsonObject2.getString("fees").toString());
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
            Custom_Participant_List custom_gallery = new Custom_Participant_List(getApplicationContext(), Arrcm);
            listView.setAdapter(custom_gallery);
            progressDialog.dismiss();
        }
    }
}



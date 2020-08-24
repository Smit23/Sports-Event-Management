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

public class Search_event_display_activity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String category;
    ArrayList<Pojo_searchevent> Arrcm = new ArrayList<>();
    ProgressDialog progressDialog;
    String lid;
    ListView listView;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event_display_activity);
        Intent i = getIntent();
        category = i.getStringExtra("category");

        sp = getSharedPreferences("Mypref", MODE_PRIVATE);
        lid = sp.getString("lid", null);
        listView = findViewById(R.id.lvview);
        listView.setOnItemClickListener(this);
        MyAsyncTask myAsyncTask = new
                MyAsyncTask();
        myAsyncTask.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         Intent i = new Intent(Search_event_display_activity.this,Eventdetails_players.class);
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
                jsonObject.put("category", category);
            } catch (Exception e) {
                e.printStackTrace();
            }
            progressDialog = new ProgressDialog(Search_event_display_activity.this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Commonclass cm = new Commonclass();
                ///chng name
                URL url = cm.getUrl("searchevent.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();


                //The number of bytes written to the data output stream so far
                DataOutputStream outputStream = new DataOutputStream(httpURLConnection.getOutputStream());

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
                        Pojo_searchevent arr = new Pojo_searchevent();
                        //$rowArr = array("First Name"=>$firstname,"Last Name"=>$lastname,"Email"=>$email,"Phone Number"=>$phonenumber,"Birth Date"=>$birthdate,"Role"=>$role,"rimage"=>$rimage,"event_id"=>$eventid,"event_name"=>$eventname,"category"=>$category,"players"=>$players,
                        //        "s_date"=>$sdate,"e_date"=>$edate,"time"=>$time,"address"=>$address,"fees"=>$fees,"l_id"=>$lid,"image"=>$image,"status"=>$status );
                        arr.setID(jsonObject2.getString("ID").toString());
                        arr.setFirstName(jsonObject2.getString("First Name").toString());
                        arr.setLastName(jsonObject2.getString("Last Name").toString());
                        arr.setEmail(jsonObject2.getString("Email").toString());
                        arr.setPhoneNumber(jsonObject2.getString("Phone Number").toString());
                        arr.setBirthDate(jsonObject2.getString("Birth Date").toString());
                        arr.setRole(jsonObject2.getString("Role").toString());
                        arr.setRimage(jsonObject2.getString("rimage").toString());


                        arr.setEvent_name(jsonObject2.getString("event_name").toString());
                        arr.setEvent_id(jsonObject2.getString("event_id").toString());
                        arr.setEimage(jsonObject2.getString("image").toString());
                        arr.setCategory_search(jsonObject2.getString("category").toString());
                        arr.setE_date(jsonObject2.getString("e_date").toString());
                        arr.setTime_search(jsonObject2.getString("time").toString());
                        arr.setL_id(jsonObject2.getString("l_id").toString());
                        arr.setPlayers_serch(jsonObject2.getString("players").toString());
                        arr.setS_date(jsonObject2.getString("s_date").toString());
                        arr.setAddress_search(jsonObject2.getString("address").toString());
                        arr.setFees_seacrh(jsonObject2.getString("fees").toString());

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
            Custom_seacrh_event_player custom_gallery = new Custom_seacrh_event_player(getApplicationContext(), Arrcm);
            listView.setAdapter(custom_gallery);
            progressDialog.dismiss();
        }
    }
}

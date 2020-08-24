package com.example.arsheya.sportseventmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    Button btn_send_email;
    EditText et_email;
    URL url;
    HttpURLConnection httpURLConnection;
    ProgressDialog progressDialog;
    JSONObject j;
    String k;
    String otp,s_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        et_email=(EditText)findViewById(R.id.et_email);

        btn_send_email = (Button) findViewById(R.id.btn_sendemail);

        btn_send_email.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btn_send_email) {

            if(!et_email.getText().toString().isEmpty()) {
                AsyncHttpTask asyncHttpTask = new AsyncHttpTask();
                asyncHttpTask.execute();
            }
            else
            {
                Toast.makeText(ForgotPassword.this, "Please Enter Your Email Id..", Toast.LENGTH_SHORT).show();
            }


        }

    }

    private class AsyncHttpTask extends AsyncTask<String,Void,JSONObject>
    {
        String email=et_email.getText().toString();

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(ForgotPassword.this);
            progressDialog.setMessage("Loading....");
            progressDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                url = new URL("http://hoven-differences.000webhostapp.com/email/email.php");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();
                DataOutputStream out = new DataOutputStream(httpURLConnection.getOutputStream());

                JSONObject jsoninput = new JSONObject();
                jsoninput.put("email",email);
                out.write(jsoninput.toString().getBytes());

                int responsecode=httpURLConnection.getResponseCode();

                if(responsecode==httpURLConnection.HTTP_OK)
                {
                    StringBuffer sb=new StringBuffer();
                    BufferedReader br=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String m="";

                    while((m=br.readLine())!=null)
                    {
                        sb.append(m);
                    }
                    j=new JSONObject(sb.toString());
                    // k=j.getString("otp");
                }
            }
            catch (Exception e)
            {
                Log.e("Exception",e.toString());
            }
            return j;

        }
        @Override
        protected void onPostExecute(JSONObject s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            //Toast.makeText(getApplication(),"Mail Sent",Toast.LENGTH_LONG).show();
            try {
                k = s.getString("msg");
                Toast.makeText(getApplication(),k,Toast.LENGTH_LONG).show();
               /* Snackbar sn=Snackbar.make(f_r,k,Snackbar.LENGTH_LONG);
                sn.show();*/

                if (k!=null) {
                    otp=s.getString("otp");
                }


                SharedPreferences.Editor shared=getSharedPreferences("MyPref",MODE_PRIVATE).edit();
                shared.putString("s_email",email);
                shared.putString("otp",otp);
                shared.commit();

                Intent intent=new Intent(getApplication(),OtpMatch.class);
                startActivity(intent);

            }
            catch (Exception e)
            {
                Log.e("Exception",e.toString());
                e.printStackTrace();
            }
        }
    }
}
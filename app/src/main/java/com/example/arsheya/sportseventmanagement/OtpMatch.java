package com.example.arsheya.sportseventmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class OtpMatch extends AppCompatActivity implements View.OnClickListener {
    String shared_otp,entered_otp;
    EditText otp;
    Button btn_chk_otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_match);
        otp= (EditText) findViewById(R.id.otp);
        btn_chk_otp= (Button) findViewById(R.id.done);
        SharedPreferences shared=getSharedPreferences("MyPref",MODE_PRIVATE);
        shared_otp=shared.getString("otp","00000");


        btn_chk_otp.setOnClickListener(this);
    }

    public void verify()
    {
        entered_otp=otp.getText().toString();

        if(!entered_otp.isEmpty() && entered_otp.equals(shared_otp) )
        {
            Intent intent=new Intent(this,Changepassword.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this,"Invalid OTP",Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onClick(View v) {
        if(v==btn_chk_otp)
        {
            verify();

        }
    }
}
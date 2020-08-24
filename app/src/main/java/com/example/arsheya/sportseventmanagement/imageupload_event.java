package com.example.arsheya.sportseventmanagement;

import android.content.Context;
import android.net.Uri;
import android.os.StrictMode;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



/**
 * Created by admin on 11/28/2017.
 */
public class  imageupload_event {
    String lineEnd="\r\n";
    String twoHyphens="--";
    String boundry="*****";
    int bytesRead,bytesAvailable,bufferSize;
    int maxBufferSize=1*1024*1024;
    byte buffer[];

    JSONObject jsonObject=null;
    public JSONObject callWS(Uri uri, Context context, String lid)
    {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try
        {
            Commonclass cm = new Commonclass();
            String name = "event_imageupload.php?id=";
            URL url = cm.getUrl(name+lid);

            //    URL url=new URL("http://10.0.0.12/PhpProject2/imageupload.php?id="+lid);

            InputStream stream=context.getContentResolver().openInputStream(uri);


            String rename=lid+".png";

            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            httpURLConnection.setRequestProperty("Content-Type","multipart/form-data; boundary="+boundry);
            httpURLConnection.setRequestProperty("ENCTYPE","multipart/form-data");
            httpURLConnection.setRequestProperty("Accept","application/json");
            httpURLConnection.setRequestProperty("Connection","Keep-Alive");
            httpURLConnection.connect();

            DataOutputStream dataOutputStream=new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.writeBytes(twoHyphens+boundry+lineEnd);
            dataOutputStream.writeBytes("Content-Disposition:form-data;name=\"fileUpload\";filename=\""+rename+"\""+lineEnd);
            dataOutputStream.writeBytes(lineEnd);


            bytesAvailable=stream.available();
            bufferSize= Math.min(bytesAvailable,maxBufferSize);
            buffer=new byte[bufferSize];
            bytesRead=stream.read(buffer,0,bufferSize);
            while (bytesRead>0)
            {
                dataOutputStream.write(buffer,0,bufferSize);
                bytesAvailable=stream.available();
                bufferSize= Math.min(bytesAvailable,maxBufferSize);
                buffer=new byte[bufferSize];
                bytesRead=stream.read(buffer,0,bufferSize);
            }
            dataOutputStream.writeBytes(lineEnd);
            dataOutputStream.writeBytes(twoHyphens+boundry+twoHyphens+lineEnd);

            int c=httpURLConnection.getResponseCode();

            stream.close();
            dataOutputStream.close();
            if (c==200)
            {
                StringBuilder stringBuilder=new StringBuilder();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String s;

                while ((s=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(s);

                }
                jsonObject=new JSONObject(stringBuilder.toString());

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }


        return  jsonObject;
    }

}

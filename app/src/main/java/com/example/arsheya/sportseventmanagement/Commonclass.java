package com.example.arsheya.sportseventmanagement;

import java.net.MalformedURLException;
import java.net.URL;

public class Commonclass {

    URL url;

    public URL getUrl(String name) {

    try {
            url = new URL("http://192.168.1.63/PhpProject2/" + name);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  url;
  }
}

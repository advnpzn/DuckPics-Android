package com.adenosinetp10.duckpics;

import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DuckPics {

    private static HttpURLConnection conn;
    public void sendPics(View view){
        BufferedReader reader;
        StringBuffer response = new StringBuffer();
        String line;
        try {
            URL url = new URL("https://random-d.uk/api/v2/random");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);

            int status = conn.getResponseCode();

            if (status > 299){
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));

            }else {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }
            while((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            System.out.println("Status : " + status);
            System.out.println("Response : " + conn.getResponseMessage());
            System.out.println("JSON Response : " + response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

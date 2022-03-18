package com.adenosinetp10.duckpics;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.kevinsawicki.http.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.quack_btn);
        String url = getResources().getString(R.string.uri_api);
        ImageView img_of_duck = findViewById(R.id.duck_img);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    JSONObject json = new JSONReturn().execute(url).get();
                    String img_url = json.getString("url");
                    Bitmap bitmap = new ImageReturn().execute(img_url).get();
                    img_of_duck.setImageBitmap(bitmap);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private static class JSONReturn extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... urls) {
            HttpRequest request = HttpRequest.get(urls[0]);
            JSONObject json = null;
            if (request.ok()) {

                try {
                    json = new JSONObject(request.body());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
           return json;
        }
    }

    private static class ImageReturn extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... img_url) {

            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(img_url[0]).getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }

}

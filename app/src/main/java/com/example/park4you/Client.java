package com.example.park4you;

import android.util.Log;

import com.google.gson.Gson;

import java.util.Map;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.MediaType;

public class Client {

    final String TAG = "API";

    private OkHttpClient client = new OkHttpClient();

    public void sendGetRequest(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(callback);
        Log.d(TAG,"Sent GET request: " + request);
    }

    public void sendPostRequest(String url, Map<String, String> postData, Callback callback) {
        Gson gson = new Gson();
        String json = gson.toJson(postData);
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(json,JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(callback);
        Log.d(TAG,"Sent POST request: " + request);

    }
}

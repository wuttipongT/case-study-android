package com.example.sleeper.myapplication;

import android.util.Log;
import com.loopj.android.http.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class BusinessLayer {
    AsyncHttpClient client = new AsyncHttpClient();
    public void getData(JsonHttpResponseHandler responseHandler){

        client.get("https://randomuser.me/api/?results=50", responseHandler);
    }
//AsyncHttpResponseHandler responseHandler
    public void insertData(RequestParams params, JsonHttpResponseHandler responseHandler){
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://129.3.25.60:8000/android-api/index.php/insert", params, responseHandler);
    }

    public void getRegister( JsonHttpResponseHandler responseHandler ){
        client.get("http://129.3.25.60:8000/android-api/index.php/info", responseHandler);
    }
}

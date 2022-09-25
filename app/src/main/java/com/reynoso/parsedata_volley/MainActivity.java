package com.reynoso.parsedata_volley;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private RequestQueue queue;
    private String url ="https://google.com";
    private  String API ="https://jsonplaceholder.typicode.com/users/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        googleStringRequest();
        JsonArrayRequest jsonArrayRequest = getJsonArrayRequest();
        queue.add(googleStringRequest());
        queue.add(jsonArrayRequest);
    }

    @NonNull
    private JsonArrayRequest getJsonArrayRequest() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, API, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //response.length returns the number of objects stored
                for(int i=0;i<response.length();i++){
                    try {
                        //iterate through the jsonArray
                        JSONObject jsonObject = response.getJSONObject(i);
                        //on the object use the proper method type to get a value with its key
                        Log.d(TAG, "onResponse() returned: " + jsonObject.getString("name"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ",error );
            }
        });
        return jsonArrayRequest;
    }

    @NonNull
    private StringRequest googleStringRequest() {
        queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+response.substring(0,500));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ", error.fillInStackTrace() );
            }
        });
        return  stringRequest;
    }
}
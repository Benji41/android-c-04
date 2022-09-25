package com.reynoso.parsedata_volley;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

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
import com.reynoso.parsedata_volley.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;
    private String url ="https://google.com";
    private String API ="https://jsonplaceholder.typicode.com/users/";
    private String API_SINGLE_OBJECT = "https://jsonplaceholder.typicode.com/users/1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(MainActivity.this,R.layout.activity_main);
        //Creates an instance of mySingleton class, as well as a RequestQueue instance only passing as an argument the context.
        MySingleton mySingleton = MySingleton.getInstance(MainActivity.this);
        mySingleton.addToRequestQueue(getJsonArrayRequest());
        mySingleton.addToRequestQueue(getJsonObjectRequest());
        mySingleton.addToRequestQueue(googleStringRequest());
    }

    @NonNull
    private JsonObjectRequest getJsonObjectRequest() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, API_SINGLE_OBJECT, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse() returned: " + response);
                mainBinding.textViewGreeting.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ", error.fillInStackTrace());
            }
        });
        return jsonObjectRequest;
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
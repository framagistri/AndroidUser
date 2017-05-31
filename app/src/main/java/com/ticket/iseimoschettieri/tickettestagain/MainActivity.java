package com.ticket.iseimoschettieri.tickettestagain;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    Button loginButton = null;

    Button registerButton = null;

    RequestQueue requestQueue;

    String url = "http://10.87.227.233:8080/ticket/webapi/secured/user/login";

    public static final String EXTRA_MESSAGE1 = "com.example.myfirstapp.MESSAGE";

    public static final String EXTRA_MESSAGE2 = "com.example.myfirstapp.MESSAGE";

    private void interpretResponse(String response, String username) {
        if (response.equals("true")) {
            goToLoginActivity(username);
        } else {
            wrongCredentials();
        }
    }

    private void goToLoginActivity(String username) {
        Context context = getApplicationContext();
        CharSequence text = "logged in!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        Intent intent = new Intent(this, LoggedInActivity.class);

        toast.show();
        intent.putExtra(EXTRA_MESSAGE1, username);
        startActivity(intent);
    }

    private void wrongCredentials() {
        Context context = getApplicationContext();
        CharSequence text = "Wrong Credentials!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void goToRegisterActivity(View view) {

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = (Button) findViewById(R.id.button);
        registerButton = (Button) findViewById(R.id.button2);
        requestQueue = Volley.newRequestQueue(MainActivity.this.getApplicationContext());
        final EditText username = (EditText) findViewById(R.id.editText);
        final EditText password = (EditText) findViewById(R.id.editText3);
        loginButton.setOnClickListener(new Button.OnClickListener() {
                                           public void onClick(View v) {
                                               final StringBuilder stringBuilder = new StringBuilder();
                                               JsonObjectRequest myJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,

                                                       new Response.Listener<JSONObject>() {
                                                           @Override
                                                           public void onResponse(JSONObject response) {
                                                               try {
                                                                   interpretResponse(response.getString("data"), username.getText().toString());
                                                               } catch (JSONException e) {
                                                                   Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                                               }
                                                           }
                                                       },
                                                       new Response.ErrorListener() {
                                                           @Override
                                                           public void onErrorResponse(VolleyError error) {
                                                               Toast.makeText(MainActivity.this,"Something went wrong " + error.getMessage(), Toast.LENGTH_LONG).show();
                                                               username.setText(error.getMessage());
                                                           }
                                                       }
                                               ) {@Override
                                               public Map< String, String > getHeaders() throws AuthFailureError {
                                                   HashMap< String, String > headers = new HashMap < String, String > ();
                                                   stringBuilder.append(username.getText().toString().trim());
                                                   stringBuilder.append(":");
                                                   stringBuilder.append(password.getText().toString().trim());
                                                   String encodedCredentials = Base64.encodeToString(stringBuilder.toString().getBytes(), Base64.NO_WRAP);
                                                   headers.put("Authorization", "Basic " + encodedCredentials);

                                                   return headers;
                                               }
                                               };

                                               requestQueue.add(myJsonObjectRequest);
                                           }
                                       }
        );

        registerButton.setOnClickListener(new Button.OnClickListener() {
                                              public void onClick(View v) {
                                                  goToRegisterActivity(v);
                                              }
                                          }
        );
    }
}



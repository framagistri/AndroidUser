package com.ticket.iseimoschettieri.tickettestagain;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

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

public class RegisterActivity extends AppCompatActivity {

    Button registerButton = null;

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private static String url = null; /*DA DEFINIRE*/

    private void sendRegistration(View view, String json_url, final String username, final String password, final String surname, final String fiscalCode) throws ExecutionException, InterruptedException {

        final StringBuilder stringBuilder = new StringBuilder();

        /*DA MODIFICARE METODO DI AUTENTICAZIONE CON NUOVO PERCORSO*/

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, json_url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            interpretResponse(response.getString("data"), username);
                        } catch (JSONException e) {
                            Toast.makeText(RegisterActivity.this, "oRc: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                    }

                }
                ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError { //DA MODIFICARE
                HashMap<String, String> headers = new HashMap<String, String>();
                stringBuilder.setLength(0);
                stringBuilder.append(username.trim().toLowerCase());
                stringBuilder.append(":");
                stringBuilder.append(password.trim().toLowerCase());
                String encodedCredentials = Base64.encodeToString(stringBuilder.toString().getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", "Basic " + encodedCredentials);

                return headers;
            }
        };

        //MySingleton.getinstance(MainActivity.this).addToRequestqueue(jsObjRequest);
    }

    private void interpretResponse(String response, String username) {
        if (response.equals("true")) {
            goToMainActivity();
        } else {
            wrongRegistration();
        }
    }

    private void goToMainActivity() {
        Context context = getApplicationContext();
        CharSequence text = "Registration Successful!!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        Intent intent = new Intent(this, LoggedInActivity.class);

        toast.show();
        startActivity(intent);
    }

    private void wrongRegistration() {
        Context context = getApplicationContext();
        CharSequence text = "Wrong Registration!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerButton = (Button) findViewById(R.id.registerButton);
        final EditText username = (EditText) findViewById(R.id.usernameText);
        final EditText password = (EditText) findViewById(R.id.passwordText1);
        final EditText surname = (EditText) findViewById(R.id.surnameText);
        final EditText fiscalCode = (EditText) findViewById(R.id.codiceFiscaleText);
        registerButton.setOnClickListener(new Button.OnClickListener() {
                                              public void onClick(View v) {
                                                  try {
                                                      sendRegistration(v, url, username.getText().toString(), password.getText().toString(), surname.getText().toString(), fiscalCode.getText().toString());
                                                  } catch (ExecutionException e) {
                                                      e.printStackTrace();
                                                  } catch (InterruptedException e) {
                                                      e.printStackTrace();
                                                  }
                                              }
                                          }
        );
    }

}
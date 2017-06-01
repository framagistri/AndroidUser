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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

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

    RequestQueue requestQueue;

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private void interpretResponse(String response) {
        if (response.equals("USER CREATED")) {
            goToMainActivity();
        } else {
            wrongRegistration();
        }
    }

    private void goToMainActivity() {
        Context context = getApplicationContext();
        CharSequence text = "User Created!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        Intent intent = new Intent(this, MainActivity.class);

        toast.show();
        startActivity(intent);
    }

    private void wrongRegistration() {
        Context context = getApplicationContext();
        CharSequence text = "Creation Failed!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private boolean isEverythingCompiled(String name,String password,String surname,String fiscalCode,String username){
        if(name.equals("")|| password.equals("") || surname.equals("")|| fiscalCode.equals("")||username.equals("")){
            return false;
        }
        return true;
    }

    private boolean doesPasswordMatch(String password1,String password2){
        if(password1.equals(password2)){
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerButton = (Button) findViewById(R.id.registerButton);
        requestQueue = Volley.newRequestQueue(RegisterActivity.this.getApplicationContext());
        final StringBuilder stringBuilder = new StringBuilder();
        final EditText username = (EditText) findViewById(R.id.usernameVero);
        final EditText name = (EditText) findViewById(R.id.usernameText);
        final EditText password = (EditText) findViewById(R.id.passwordText1);
        final EditText password2 = (EditText) findViewById(R.id.passwordText2);
        final EditText surname = (EditText) findViewById(R.id.surnameText);
        final EditText fiscalCode = (EditText) findViewById(R.id.codiceFiscaleText);
        registerButton.setOnClickListener(new Button.OnClickListener() {
                                              public void onClick(View v) {
                                                  final String url = "http://10.87.251.254:7070/ticket/webapi/registration/" + name.getText().toString() + "/" + surname.getText().toString() + "/" + username.getText().toString() + "/" + fiscalCode.getText().toString() + "/" + password.getText().toString();
                                                  if (isEverythingCompiled(name.getText().toString(), password.getText().toString(), surname.getText().toString(),fiscalCode.getText().toString(),username.getText().toString())) {
                                                      if (doesPasswordMatch(password.getText().toString(), password2.getText().toString())) {
                                                          JsonObjectRequest myJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,

                                                                  new Response.Listener<JSONObject>() {
                                                                      @Override
                                                                      public void onResponse(JSONObject response) {
                                                                          try {
                                                                              interpretResponse(response.getString("data"));
                                                                          } catch (JSONException e) {
                                                                              Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                                                          }
                                                                      }
                                                                  },
                                                                  new Response.ErrorListener() {
                                                                      @Override
                                                                      public void onErrorResponse(VolleyError error) {
                                                                          Toast.makeText(RegisterActivity.this, "Something went wrong " + error.getMessage(), Toast.LENGTH_LONG).show();
                                                                      }
                                                                  }
                                                          );

                                                          requestQueue.add(myJsonObjectRequest);
                                                      }
                                                      else{
                                                          Toast.makeText(RegisterActivity.this,"Password 1 and 2 do not match!", Toast.LENGTH_LONG).show();
                                                      }
                                                  }
                                                  else{
                                                      Toast.makeText(RegisterActivity.this,"Compile all Fields!", Toast.LENGTH_LONG).show();
                                                  }
                                              }
                                          }
        );
    }

}
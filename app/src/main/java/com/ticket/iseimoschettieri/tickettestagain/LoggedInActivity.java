package com.ticket.iseimoschettieri.tickettestagain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoggedInActivity extends AppCompatActivity {

    private RequestQueue requestQueue;

    private void goToTicketSelection(){
        Intent intent1 = new Intent(this, TicketSelection.class);
        startActivity(intent1);
    }

    private void goToShowTickets(String data){
        Intent intent2 = new Intent(this, MyTickets.class);
        intent2.putExtra("Vector",data);
        Toast.makeText(LoggedInActivity.this, data, Toast.LENGTH_LONG).show();
        startActivity(intent2);
    }

    private void logoutActivity(){
        Intent intent3 = new Intent(this, MainActivity.class);
        UserInfoHandler.logout(this);
        startActivity(intent3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String usernameText = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);

        // Capture the layout's TextView and set the string as its text
        TextView loggedInAs = (TextView) findViewById(R.id.textView);
        requestQueue = Volley.newRequestQueue(LoggedInActivity.this);
        TextView username = (TextView) findViewById(R.id.username);
        Button buyTickets = (Button) findViewById(R.id.buyTicketsButton);
        Button logout = (Button) findViewById(R.id.logout);
        Button showTickets = (Button) findViewById(R.id.myTicketsButton);
        loggedInAs.setText("Logged in as:");
        username.setText(usernameText);

        buyTickets.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                goToTicketSelection();
            }

        }

        );

        showTickets.setOnClickListener(new Button.OnClickListener() {
                                          public void onClick(View v) {
                                              final String url = "http://10.87.251.254:7070/ticket/webapi/secured/user/mytickets/"+UserInfoHandler.getUsername(LoggedInActivity.this);
                                              final StringBuilder stringBuilder = new StringBuilder();
                                              JsonObjectRequest myJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,

                                                      new Response.Listener<JSONObject>() {
                                                          @Override
                                                          public void onResponse(JSONObject response) {
                                                               goToShowTickets(response.toString());
                                                              }
                                                      },
                                                      new Response.ErrorListener() {
                                                          @Override
                                                          public void onErrorResponse(VolleyError error) {
                                                              Toast.makeText(LoggedInActivity.this,"Something went wrong " + error.getMessage(), Toast.LENGTH_LONG).show();
                                                          }
                                                      }
                                              ) {@Override
                                              public Map< String, String > getHeaders() throws AuthFailureError {
                                                  HashMap< String, String > headers = new HashMap < String, String > ();
                                                  stringBuilder.append(UserInfoHandler.getUsername(LoggedInActivity.this));
                                                  stringBuilder.append(":");
                                                  stringBuilder.append(UserInfoHandler.getPassword(LoggedInActivity.this));
                                                  String encodedCredentials = Base64.encodeToString(stringBuilder.toString().getBytes(), Base64.NO_WRAP);
                                                  headers.put("Authorization", "Basic " + encodedCredentials);
                                                  return headers;
                                              }
                                              };

                                              requestQueue.add(myJsonObjectRequest);
                                          }
                                       }
        );

        showTickets.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                logoutActivity();

            }

        });


    }

}

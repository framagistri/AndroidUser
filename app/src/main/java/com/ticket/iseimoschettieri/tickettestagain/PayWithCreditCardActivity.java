package com.ticket.iseimoschettieri.tickettestagain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PayWithCreditCardActivity extends AppCompatActivity {

    private RequestQueue requestQueue;

    private void goToLoggedInActivity(){
        Intent intent1 = new Intent(this,LoggedInActivity.class);
        startActivity(intent1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_with_credit_card);
        Intent intent = getIntent();
        final String type = intent.getStringExtra("ticketType");
        final int duration = intent.getIntExtra("ticketDuration",0);
        final Date paymentDate = new Date();
        paymentDate.setHours(paymentDate.getHours()+duration);
        requestQueue = Volley.newRequestQueue(PayWithCreditCardActivity.this.getApplicationContext());
        Button buyButton = (Button)findViewById(R.id.buy);
        final EditText field1 = (EditText) findViewById(R.id.text1);
        final EditText field2 = (EditText) findViewById(R.id.text2);
        final EditText field3 = (EditText) findViewById(R.id.text3);
        final EditText field4 = (EditText) findViewById(R.id.text4);
        buyButton.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
                        final String url = "http://10.87.251.254:7070/ticket/webapi/secured/user/buy/"+UserInfoHandler.getUsername(PayWithCreditCardActivity.this)+"/"+field1.getText().toString()+field2.getText().toString()+field3.getText().toString()+field4.getText().toString()+"/"+DateOperations.getInstance().toString(paymentDate)+"/"+type;
                        final StringBuilder stringBuilder = new StringBuilder();
                        JsonObjectRequest myJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,

                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            Toast.makeText(PayWithCreditCardActivity.this, response.getString("Payement Successful!"), Toast.LENGTH_LONG).show();
                                            goToLoggedInActivity();
                                        } catch (JSONException e) {
                                            Toast.makeText(PayWithCreditCardActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(PayWithCreditCardActivity.this,"Something went wrong " + error.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                        ) {@Override
                        public Map< String, String > getHeaders() throws AuthFailureError {
                            HashMap< String, String > headers = new HashMap < String, String > ();
                            stringBuilder.append(UserInfoHandler.getUsername(PayWithCreditCardActivity.this));
                            stringBuilder.append(":");
                            stringBuilder.append(UserInfoHandler.getPassword(PayWithCreditCardActivity.this));
                            String encodedCredentials = Base64.encodeToString(stringBuilder.toString().getBytes(), Base64.NO_WRAP);
                            headers.put("Authorization", "Basic " + encodedCredentials);
                            return headers;
                        }
                        };

                        requestQueue.add(myJsonObjectRequest);
                    }
            });
    }
}

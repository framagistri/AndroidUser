package com.ticket.iseimoschettieri.tickettestagain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyTickets extends AppCompatActivity {

    JSONObject arrayJSON;
    JSONObject dataArray;
    JSONArray arrayVero;

    protected void createTicketButton(){

        LinearLayout linear = (LinearLayout) findViewById(R.id.linear);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for(int i = 0; i<arrayVero.length();i++){
            try {
                final JSONObject buff = arrayVero.getJSONObject(i);
                Button ticketbutton = new Button(this);
                ticketbutton.setId(i);
                ticketbutton.setText("Ticket"+i);
                final int id_ = ticketbutton.getId();
                ticketbutton.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Toast.makeText(MyTickets.this, buff.getString("id")+" "+buff.getString("expire")+" "+buff.getString("type"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                linear.addView(ticketbutton,params);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tickets);
        Intent intent = getIntent();
        String vettore = intent.getStringExtra("Vector");
        try {
            dataArray = new JSONObject(vettore);
            arrayVero = new JSONArray(dataArray.getString("DATA"));
            createTicketButton();
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}

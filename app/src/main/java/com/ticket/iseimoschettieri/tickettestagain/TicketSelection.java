package com.ticket.iseimoschettieri.tickettestagain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketSelection extends AppCompatActivity {

    Spinner types;
    RequestQueue requestQueue;
    private Map<String, Products> productMap;

    private void goToPayActivity(String type,double duration) {
        Intent intent = new Intent(this, PayWithCreditCardActivity.class);
        intent.putExtra("ticketType", type);
        intent.putExtra("ticketDuration", duration);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestQueue = Volley.newRequestQueue(TicketSelection.this.getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_selection);
        productMap = new HashMap();

        getTicketTypes();
        types = (Spinner) findViewById(R.id.types);

        Button buyTicketButton = (Button) findViewById(R.id.buy);
        buyTicketButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                String selectedType = types.getSelectedItem().toString();
                Products selectedProduct = null;
                for (Products p : productMap.values()) {
                    if (selectedType.contains(p.getDescription() + " ")) {
                        selectedProduct = p;

                        goToPayActivity(p.getType(),p.getDuration());
                    }
                }

            }
        });

    }

    private void getTicketTypes() {
        JsonObjectRequest myJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, UserInfoHandler.TYPES_API
                , null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseTickets(response);
                        populateSpinner();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TicketSelection.this, "Something went wrong " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                StringBuilder sBuilderUserInfo = new StringBuilder();
                sBuilderUserInfo.setLength(0);
                sBuilderUserInfo.append(UserInfoHandler.getUsername(getApplicationContext()));
                sBuilderUserInfo.append(":");
                sBuilderUserInfo.append(UserInfoHandler.getPassword(getApplicationContext()));
                String encodedCredentials = Base64.encodeToString(sBuilderUserInfo.toString().getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", "Basic " + encodedCredentials);

                return headers;
            }
        };
        requestQueue.add(myJsonObjectRequest);
    }

    private void parseTickets(JSONObject obj){
        try {
            JSONArray ticketArray = (JSONArray)obj.get("DATA");
            for(int i = 0; i < ticketArray.length();i++){
                JSONObject ticket = (JSONObject) ticketArray.get(i);

                int duration = (int)ticket.get("DURATION");
                String type = (String)ticket.get("TYPE");
                double cost = (Double)ticket.get("COST");
                String description = (String)ticket.get("DESCRIPTION");
                if(type.charAt(0) == 'T') {
                    productMap.put(type, new SimpleTicket(description, type, cost, duration));
                }
                else{
                    productMap.put(type, new SeasonTicket(description, type, cost, duration,new SimpleAlgorithm()));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void populateSpinner () {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item);
        types.setAdapter(adapter);
        List<CharSequence> list = new ArrayList<CharSequence>();
        for (Products p : productMap.values()) {
            list.add(p.getDescription() + " - " + p.getCost() + "€ - " + (int)(p.getDuration()) + " m");
        }
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
    }

}

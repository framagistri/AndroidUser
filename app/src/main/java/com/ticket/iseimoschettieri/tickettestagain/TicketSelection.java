package com.ticket.iseimoschettieri.tickettestagain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class TicketSelection extends AppCompatActivity {

    private ArrayList<TicketType> tickets = new ArrayList<TicketType>();

    private void goToPayActivity(String type){
        Intent intent = new Intent(this, PayWithCreditCardActivity.class);
        intent.putExtra("ticketType",type);
        startActivity(intent);
    }

    private void dynamicButtons(int n){
        LinearLayout linear = (LinearLayout)findViewById(R.id.linear);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        for(int i = 0; i<n; i++){
            Button btn = new Button(this);
            btn.setId(i);
            final int id_ = btn.getId();
            btn.setText("Biglietto " + tickets.get(i).getType());

            final int j = i;

            btn.setOnClickListener(new Button.OnClickListener() {
                                       public void onClick(View v) {
                                            goToPayActivity(tickets.get(j).getType());
                                       }
                                   });

            btn.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            linear.addView(btn,params);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tickets.add(new SingleType());
        tickets.add(new MultiType(2));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_selection);
        dynamicButtons(2);
    }
}

package com.ticket.iseimoschettieri.tickettestagain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class LoggedInActivity extends AppCompatActivity {

    private void goToTicketSelection(){
        Intent intent1 = new Intent(this, TicketSelection.class);
        startActivity(intent1);
    }

    /*private void goToShowTickets(){
        Intent intent2 = new Intent(this, activity_myticket.class);
        intent2.putExtra("jsonArray",new JSONObject());
        startActivity(intent2);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String usernameText = intent.getStringExtra(MainActivity.EXTRA_MESSAGE1);

        // Capture the layout's TextView and set the string as its text
        TextView loggedInAs = (TextView) findViewById(R.id.textView);
        TextView username = (TextView) findViewById(R.id.username);
        Button buyTickets = (Button) findViewById(R.id.buyTicketsButton);
        Button showTickets = (Button) findViewById(R.id.myTicketsButton);
        loggedInAs.setText("Logged in as:");
        username.setText(usernameText);

        buyTickets.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                goToTicketSelection();
            }

        }

        );

        /*showTickets.setOnClickListener(new Button.OnClickListener() {
                                          public void onClick(View v) {
                                              goToTicketSelection();
                                          }

                                      }

        );*/

    }

}

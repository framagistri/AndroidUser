package com.ticket.iseimoschettieri.tickettestagain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class LoggedInActivity extends AppCompatActivity {

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
        loggedInAs.setText("Logged in as:");
        username.setText(usernameText);

    }

}

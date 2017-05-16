package com.ticket.iseimoschettieri.tickettestagain;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity{

    Button myButton = null;

    Button myButton2 = null;

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    public void sendMessage(View view) throws ExecutionException, InterruptedException {

        Context context = getApplicationContext();
        CharSequence text = "logged in!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);

        String login = null;

        Socket clientSocket;

        Intent intent = new Intent(this, LoggedInActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        EditText editText2 = (EditText) findViewById(R.id.editText2);
        EditText editText3 = (EditText) findViewById(R.id.editText3);
        String message = editText.getText().toString();

        login = new JsonLogin().execute(editText.getText().toString(),editText3.getText().toString(),editText2.getText().toString()).get();

        if(login.equals("true")) {

            toast.show();

            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);

        }

        else{

            CharSequence textErr = "Wrong Credentials!";
            toast = Toast.makeText(context, textErr, duration);
            toast.show();

        }
    }

    public void goToRegisterActivity(View view){

        Intent intent = new Intent(this, RegisterActivity.class);
        EditText editText2 = (EditText) findViewById(R.id.editText2);
        String message = editText2.getText().toString();
        if(message.equals("")){

            message = "10.87.130.83";

        }
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myButton = (Button) findViewById(R.id.button);
        myButton2 = (Button) findViewById(R.id.button2);
        myButton.setOnClickListener(new Button.OnClickListener() {
                                        public void onClick(View v) {
                                            try {
                                                sendMessage(v);
                                            } catch (ExecutionException e) {
                                                e.printStackTrace();
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
        );

        myButton2.setOnClickListener(new Button.OnClickListener() {
                                        public void onClick(View v) {
                                            goToRegisterActivity(v);
                                        }
                                    }
        );


    }


    private class JsonLogin extends AsyncTask<String, Void, String> {

        private final String JsonMethod = "USERLOGIN";

        private String answerString = null;

        private String loginString = null;

        private String ip = null;

        JSONObject login = null;

        JSONObject data = null;

        JSONObject answer = null;

        String loginSuccess = null;

        Socket clientSocket = null;

        Socket serverSocket = null;

        Socket socket = null;

        PrintWriter out;

        BufferedReader in;

        @Override
        protected String doInBackground(String... args) {

            try {

                ip = args[2].toString();

                if(ip.equals("")){

                    ip = "10.87.130.83";

                }

                clientSocket = new Socket(ip, 5000);

                //clientSocket.isConnected();

                login = new JSONObject();

                login.put("method", JsonMethod);

                data = new JSONObject();

                data.put("username", args[0].toString());

                data.put("psw", args[1].toString());

                login.put("data", data);

                out = new PrintWriter(clientSocket.getOutputStream(), true);

                out.println(login.toString());

                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                //out.println(login.toString());

                answerString = in.readLine();

                answer = new JSONObject(answerString);

                Log.d("gigiTag",answer.toString());

                //clientSocket.close();

                if(answer.getString("data").equals("true")){

                    loginString = "true";

                }

                else{

                    loginString = "false";

                }

                return loginString;

            }

            catch(IOException i){

                Log.d("JsonTag",i.toString());

                System.err.println(i);

            }

            catch(JSONException j){

                System.err.println(j);

            }

            return null;
        }

    }

}
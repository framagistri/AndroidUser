package com.ticket.iseimoschettieri.tickettestagain;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

public class RegisterActivity extends AppCompatActivity {

    Button registerButton = null;

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    public void sendRegistration(View view,String ip) throws ExecutionException, InterruptedException {

        Context context = getApplicationContext();
        CharSequence text = "Registration successful!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);

        String register = null;

        Socket clientSocket;

        Intent intent = new Intent(this, MainActivity.class);
        EditText username = (EditText) findViewById(R.id.usernameText);
        EditText password = (EditText) findViewById(R.id.passwordText1);
        EditText surname = (EditText) findViewById(R.id.surnameText);
        EditText fiscalCode = (EditText) findViewById(R.id.codiceFiscaleText);

        register = new JsonRegister().execute(username.getText().toString(),password.getText().toString(),surname.getText().toString(),fiscalCode.getText().toString(),ip).get();

        if(register.equals("true")) {

            toast.show();
            startActivity(intent);

        }

        else{

            CharSequence textErr = "Registration failed!";
            toast = Toast.makeText(context, textErr, duration);
            toast.show();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent intent = getIntent();
        final String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new Button.OnClickListener() {
                                        public void onClick(View v) {
                                            try {
                                                sendRegistration(v,message);
                                            } catch (ExecutionException e) {
                                                e.printStackTrace();
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
        );
    }

    private class JsonRegister extends AsyncTask<String, Void, String> {

        private final String JsonMethod = "CREATEUSER";

        private String answerString = null;

        private String loginString = null;

        JSONObject login = null;

        JSONObject data = null;

        JSONObject answer = null;

        Socket clientSocket = null;

        PrintWriter out;

        BufferedReader in;

        @Override
        protected String doInBackground(String... args) {

            try {

                clientSocket = new Socket(args[4].toString(), 5000);

                login = new JSONObject();

                login.put("method", JsonMethod);

                data = new JSONObject();

                data.put("name", args[0].toString());

                data.put("surname", args[2].toString());

                data.put("username", args[0].toString());

                data.put("cf",args[3].toString());

                data.put("psw", args[1].toString());

                login.put("data", data);

                out = new PrintWriter(clientSocket.getOutputStream(), true);

                out.println(login.toString());

                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                answerString = in.readLine();

                answer = new JSONObject(answerString);

                Log.d("gigiTag",answer.toString());

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

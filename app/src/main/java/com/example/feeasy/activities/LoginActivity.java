package com.example.feeasy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feeasy.R;
import com.example.feeasy.Threads.Connection;
import com.example.feeasy.Threads.ServerHandler;
import com.example.feeasy.dataManagement.CurrentUser;
import com.example.feeasy.entities.ActionNames;
import com.example.feeasy.entities.LoggedInUser;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    ServerHandler serverHandler = new ServerHandler();
    Button button;
    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextView emailInput = findViewById(R.id.login_email);
        final TextView passwordInput = findViewById(R.id.login_password);
        final Connection connection = new Connection();
        TextView switcher = findViewById(R.id.login_button_sign_up);
        button = findViewById(R.id.login_button);
        bar = findViewById(R.id.login_loading);

        serverHandler.start();

        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                bar.setVisibility(View.VISIBLE);

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject
                            .put("email", email)
                            .put("password", password);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                connection.handleAction(ActionNames.SIGN_IN, jsonObject);
                waitForServer();
            }
        });
    }

    public void waitForServer(){

        // will wait flat amount of 1 sec

        serverHandler.handler.post(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                LoggedInUser logged = CurrentUser.getLoggedInUser();
                if(logged!=null){
                    Log.i("USER:", logged.name);
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    serverHandler.looper.quit();
                    bar.setVisibility(View.INVISIBLE);
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Wrong email or password", Toast.LENGTH_SHORT);
                    toast.show();
                    bar.setVisibility(View.INVISIBLE);
                }
            }
        });

    }
}
package com.example.feeasy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feeasy.R;
import com.example.feeasy.Threads.Connection;
import com.example.feeasy.Threads.ServerHandler;
import com.example.feeasy.dataManagement.CurrentUser;
import com.example.feeasy.dataManagement.GroupManager;
import com.example.feeasy.entities.ActionNames;
import com.example.feeasy.entities.LoggedInUser;
import com.example.feeasy.entities.User;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    ServerHandler serverHandler = new ServerHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final TextView name = findViewById(R.id.signup_display_name);
        final TextView  email = findViewById(R.id.signup_email);
        final TextView password = findViewById(R.id.signup_password);
        TextView switcher = findViewById(R.id.sign_up_switch);
        Button btn = findViewById(R.id.signup_button);
        serverHandler.start();

        final Connection connection = new Connection();

        final User user = new User("", "");

        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String displayName = name.getText().toString();
                String mail = email.getText().toString();
                String pw = password.getText().toString();
                user.setName(displayName);
                user.setMail(mail);

                JSONObject jsonObject = formJson(mail, pw, displayName);

                connection.handleAction(ActionNames.SIGN_UP, jsonObject);
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
                }
                else{
                   Toast toast = Toast.makeText(getApplicationContext(), "Registration not possible", Toast.LENGTH_SHORT);
                   toast.show();
                }
            }
        });
    }

    public JSONObject formJson(String email, String pw, String name){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject
                    .put("email", email)
                    .put("password", pw)
                    .put("name", name);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
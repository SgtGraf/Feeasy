package com.example.feeasy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.feeasy.R;
import com.example.feeasy.Threads.Connection;
import com.example.feeasy.entities.ActionNames;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextView emailInput = findViewById(R.id.login_email);
        final TextView passwordInput = findViewById(R.id.login_password);
        Button button = findViewById(R.id.login_button);
        final Connection connection = new Connection();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject
                            .put("email", email)
                            .put("password", password);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                connection.handleAction(ActionNames.SIGN_IN, jsonObject);
            }
        });
    }
}
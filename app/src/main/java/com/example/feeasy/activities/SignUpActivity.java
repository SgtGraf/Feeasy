package com.example.feeasy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.feeasy.R;
import com.example.feeasy.Threads.Connection;
import com.example.feeasy.dataManagement.GroupManager;
import com.example.feeasy.entities.ActionNames;
import com.example.feeasy.entities.User;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final TextView name = findViewById(R.id.signup_display_name);
        final TextView  email = findViewById(R.id.signup_email);
        final TextView password = findViewById(R.id.signup_password);
        Button btn = findViewById(R.id.signup_button);
        final Connection connection = new Connection();

        final User user = new User("", "");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String displayName = name.getText().toString();
                String mail = email.getText().toString();
                String pw = password.getText().toString();
                user.setName(displayName);
                user.setMail(mail);

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject
                        .put("email", mail)
                        .put("password", pw)
                        .put("name", displayName);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                connection.handleAction(ActionNames.SIGN_UP, jsonObject);

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}
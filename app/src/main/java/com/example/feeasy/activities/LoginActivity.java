package com.example.feeasy.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feeasy.R;
import com.example.feeasy.Threads.Connection;
import com.example.feeasy.dataManagement.AppDataManager;
import com.example.feeasy.entities.ActionNames;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    Button button;
    ProgressBar bar;

    Handler loginHandler = new LoginHandler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextView emailInput = findViewById(R.id.login_email);
        final TextView passwordInput = findViewById(R.id.login_password);
        TextView switcher = findViewById(R.id.login_button_sign_up);
        button = findViewById(R.id.login_button);
        bar = findViewById(R.id.login_loading);

        final Connection connection = new Connection();

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
                button.setEnabled(false); // TODO: fix
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                bar.setVisibility(View.VISIBLE);
                connection.handleAction(ActionNames.SIGN_IN, buildJSONObject(email,password));
            }
        });
    }

    public JSONObject buildJSONObject(String email, String pw){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email).put("password", pw);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppDataManager.getAppDataManager().setCurrentHandler(loginHandler);
    }

    @Override
    public void onBackPressed() {
    }

    private class LoginHandler extends Handler{

        public LoginHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.obj == ActionNames.SIGN_IN){
                if(msg.arg1 == 0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                        }
                    });
                }
                else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bar.setVisibility(View.INVISIBLE);
                            button.setEnabled(true);
                            Toast toast = Toast.makeText(getApplicationContext(), "Wrong email or password", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                }
            }
        }
    }
}
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.feeasy.R;
import com.example.feeasy.Threads.Connection;
import com.example.feeasy.dataManagement.AppDataManager;
import com.example.feeasy.entities.ActionNames;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    Button button;

    Handler signUpHandler = new SignUpHandler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final TextView name = findViewById(R.id.signup_display_name);
        final TextView  email = findViewById(R.id.signup_email);
        final TextView password = findViewById(R.id.signup_password);
        TextView switcher = findViewById(R.id.sign_up_switch);
        button = findViewById(R.id.signup_button);

        final Connection connection = new Connection();

        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setEnabled(false);
                String displayName = name.getText().toString();
                String mail = email.getText().toString();
                String pw = password.getText().toString();
                connection.handleAction(ActionNames.SIGN_UP, buildJSONObject(mail, pw, displayName));
            }


        });
    }

    public JSONObject buildJSONObject(String email, String pw, String name){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email).put("password", pw).put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppDataManager.getAppDataManager().setCurrentHandler(signUpHandler);
    }

    @Override
    public void onBackPressed() {
    }

    private class SignUpHandler extends Handler {

        public SignUpHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.obj == ActionNames.SIGN_UP){
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
                            button.setEnabled(true);
                            Toast toast = Toast.makeText(getApplicationContext(), "Registration failed.", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                }
            }
        }
    }
}
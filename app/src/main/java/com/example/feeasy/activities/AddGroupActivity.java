package com.example.feeasy.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.feeasy.R;
import com.example.feeasy.Threads.Connection;
import com.example.feeasy.dataManagement.AppDataManager;
import com.example.feeasy.entities.ActionNames;
import com.example.feeasy.entities.Group;

import org.json.JSONException;
import org.json.JSONObject;

public class AddGroupActivity extends AppCompatActivity {

    Button createButton;
    EditText input;
    Group group;

    boolean backAllowed = true;

    Handler handler = new AddGroupHandler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        createButton = findViewById(R.id.button_create_group);
        input = findViewById(R.id.input_add_group);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = input.getText().toString().trim();
                if(!name.isEmpty()){
                    createButton.setEnabled(false);
                    backAllowed = false;
                    Connection connection = new Connection();
                    connection.handleAction(ActionNames.CREATE_GROUP, buildJSONObject(name));
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please enter a group name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public JSONObject buildJSONObject(String name){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    protected void onResume() {
        AppDataManager.getAppDataManager().setCurrentHandler(handler);
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if(backAllowed){
            super.onBackPressed();
        }
    }

    private class AddGroupHandler extends Handler{

        public AddGroupHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.obj == ActionNames.CREATE_GROUP){
                backAllowed = true;
                if(msg.arg1 == 0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onBackPressed();
                        }
                    });
                }
                else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            createButton.setEnabled(true);
                            Toast.makeText(getApplicationContext(), "Could not create Group", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }
    }
}
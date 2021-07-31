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
import android.widget.TextView;
import android.widget.Toast;

import com.example.feeasy.R;
import com.example.feeasy.Threads.Connection;
import com.example.feeasy.dataManagement.AppDataManager;
import com.example.feeasy.dataManagement.DataManager;
import com.example.feeasy.dataManagement.GroupManager;
import com.example.feeasy.entities.ActionNames;
import com.example.feeasy.entities.Group;
import com.example.feeasy.entities.GroupMember;

import org.json.JSONException;
import org.json.JSONObject;

public class AddMemberActivity extends AppCompatActivity {

    EditText input;
    TextView groupName;
    Group group;
    String memberEmail;
    Button addButton;

    boolean backAllowed = true;

    Handler handler = new AddMemberHandler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        input = findViewById(R.id.add_member_input);
        groupName = findViewById(R.id.add_member_groupname);
        addButton = findViewById(R.id.add_member_button);
        group = DataManager.getDataManager().getGroup(getIntent().getIntExtra("groupId", -1));
        groupName.setText(group.groupName);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberEmail = input.getText().toString().trim();
                if(!memberEmail.isEmpty()){
                    addButton.setEnabled(false);
                    backAllowed = false;
                    Connection connection = new Connection();
                    connection.handleAction(ActionNames.ADD_TO_GROUP, buildJSONObject(group.id, memberEmail));
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please enter an email address.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public JSONObject buildJSONObject(int groupId, String email){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("group_id", groupId).put("email",email);
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

    private class AddMemberHandler extends Handler {

        public AddMemberHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.obj == ActionNames.ADD_TO_GROUP){
                if(msg.arg1 == 0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            backAllowed = true;
                            onBackPressed();
                        }
                    });
                }
                else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            backAllowed = true;
                            addButton.setEnabled(true);
                            Toast.makeText(getApplicationContext(),"User not found or already in group.",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }
    }
}
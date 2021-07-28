package com.example.feeasy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.feeasy.R;
import com.example.feeasy.Threads.Connection;
import com.example.feeasy.dataManagement.GroupManager;
import com.example.feeasy.entities.ActionNames;
import com.example.feeasy.entities.Group;

public class AddGroupActivity extends AppCompatActivity {

    EditText input;
    String name;
    Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        final Connection connection = new Connection();

        Button createButton = findViewById(R.id.button_create_group);
        input = findViewById(R.id.input_add_group);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = input.getText().toString().trim();
                if(!name.isEmpty()){
                    // TODO: thread

                    connection.threadCreateGroupValues(name);
                    connection.handleAction(ActionNames.CREATE_GROUP);

                    Group group = GroupManager.createGroup(name);
                    GroupManager.addGroupMember(GroupManager.getLoggedIn(), group);
                    onBackPressed();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please enter a group name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
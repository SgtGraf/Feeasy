package com.example.feeasy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feeasy.R;
import com.example.feeasy.dataManagement.GroupManager;
import com.example.feeasy.entities.Group;
import com.google.android.material.snackbar.Snackbar;

public class AddGroupActivity extends AppCompatActivity {

    EditText input;
    String name;
    Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        Button createButton = findViewById(R.id.button_create_group);
        input = findViewById(R.id.input_add_group);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = input.getText().toString().trim();
                if(!name.isEmpty()){
                    Group group = GroupManager.createGroup(name);
                    GroupManager.addGroupMember(GroupManager.getLoggedIn(), group);
                    Intent intent = new Intent(getApplicationContext(), GroupActivity.class);
                    intent.putExtra("groupId", group.id);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please enter a group name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
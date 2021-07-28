package com.example.feeasy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feeasy.R;
import com.example.feeasy.dataManagement.GroupManager;
import com.example.feeasy.entities.Group;
import com.example.feeasy.entities.GroupMember;

public class AddMemberActivity extends AppCompatActivity {

    EditText input;
    TextView groupName;
    Group group;
    String newMemberName;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        input = findViewById(R.id.add_member_input);
        groupName = findViewById(R.id.add_member_groupname);
        addButton = findViewById(R.id.add_member_button);
        final int groupId = getIntent().getIntExtra("groupId", -1);
        group = GroupManager.getGroupByID(groupId);
        groupName.setText(group.groupName);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newMemberName = input.getText().toString();
                // TODO: thread
                GroupMember newMember = GroupManager.getMemberByName(newMemberName);
                if(newMember != null){
                    if(!group.members.contains(newMember)){
                        // TODO: thread
                        GroupManager.addGroupMember(newMember, group);
                        onBackPressed();
                    }
                   else Toast.makeText(getApplicationContext(), "User already in group!", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getApplicationContext(), "User does not exist!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
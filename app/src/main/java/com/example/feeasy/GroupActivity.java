package com.example.feeasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        TextView groupNameView = findViewById(R.id.groupName);
        TextView totalAmtView = findViewById(R.id.groupTotal);


        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        Group group =  GroupManager.getGroupPerID(id);


        assert group != null;
        groupNameView.setText(group.groupName);
        totalAmtView.setText(Integer.toString(group.totalFees));

    }
}
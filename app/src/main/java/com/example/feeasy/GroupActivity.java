package com.example.feeasy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        TextView groupNameView = findViewById(R.id.groupName);
        TextView totalAmtView = findViewById(R.id.groupTotal);
        Button btn = findViewById(R.id.btn_new_fee);


        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        Group group =  GroupManager.getGroupPerID(id);


        assert group != null;
        groupNameView.setText(group.groupName);
        totalAmtView.setText(Float.toString(GroupManager.getFeesPerGroup(group)));

        Log.i("Fees:", Integer.toString(group.totalFees));

        assert GroupManager.getGroupPerID(id) != null;
        AdapterMembers adapter = new AdapterMembers(this, GroupManager.getGroupPerID(id).members, group);
        recyclerView = findViewById(R.id.group_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // TODO: open AddFee
            }
        });
    }
}
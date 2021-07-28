package com.example.feeasy.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.feeasy.dataManagement.GroupManager;
import com.example.feeasy.R;
import com.example.feeasy.adapters.AdapterHome;
import com.example.feeasy.entities.Group;
import com.example.feeasy.entities.GroupMember;
import com.example.feeasy.services.ServerService;
import com.example.feeasy.services.ServerThread;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterHome adapter;
    Thread serverThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.serverThread = new Thread(new ServerThread());
        this.serverThread.start();
        //createService();
        View buttonAddGroup = findViewById(R.id.button_addGroup);

        buttonAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddGroupActivity.class);
                startActivity(intent);
            }
        });

        /*
        *
        * Just for testing
        *
         */

        Group g1 = GroupManager.createGroup("Nice");
        Group g2 = GroupManager.createGroup("Not nice");

        GroupMember gm1 = GroupManager.createMember("Roman");
        GroupMember gm2 =  GroupManager.createMember("Fabi");
        GroupMember gm3 =  GroupManager.createMember("Leon");

        GroupManager.addGroupMember(gm1, g1);
        GroupManager.addGroupMember(gm2, g1);
        GroupManager.addGroupMember(gm1, g2);
        GroupManager.addGroupMember(gm3, g2);

        GroupManager.createFee("Too late", g1, gm1, 50, "29.06.2021");
        GroupManager.createFee("Missed Penalty", g1, gm1, 10, "29.06.2021");
        GroupManager.createFee("Lost Bet", g1, gm2, 20, "28.06.2021");
        GroupManager.createFee("Got rick rolled", g1, gm1, 800, "28.06.2021");
        GroupManager.createFee("Too late", g2, gm3, 30, "27.06.2021");
        GroupManager.createFee("Too late", g2, gm1, 69, "27.06.2021");

        GroupManager.createFeePreset("Too late", g1, 25);
        GroupManager.createFeePreset("Hit the ground to hard", g1, 50);

        GroupManager.setLoggedIn(gm1);
        /*
        *
         */

        adapter = new AdapterHome(this, GroupManager.getGroups());
        recyclerView = findViewById(R.id.main_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.item1:
                Toast.makeText(this, "ACCOUNT", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.item2:
                Toast.makeText(this, "PLACEHOLDER1", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(this, "PLACEHOLDER2", Toast.LENGTH_SHORT).show();
                return true;
            default: super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    public void createService(){
        Intent startServer = new Intent(this, ServerService.class);
        startServer.setAction(ServerService.START_SERVER);
        startService(startServer);
    }
}
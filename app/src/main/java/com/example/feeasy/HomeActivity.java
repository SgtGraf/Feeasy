package com.example.feeasy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        GroupManager gm = new GroupManager(new LinkedList<Group>());


        //List<Group> groups = new ArrayList<>();
        List<GroupMember> members = new ArrayList<>();



        // Placeholder members
        members.add(new GroupMember("Roman", true));
        members.add(new GroupMember("Fabi", false));
        members.add(new GroupMember("Herwig", false));
        members.add(new GroupMember("Luki", false));
        members.add(new GroupMember("Elena", false));

        gm.addGroup(new Group(0,"Gruppe 1", members, 0));
        gm.addGroup(new Group(1,"Gruppe 2", members, 0));
        gm.addGroup(new Group(2,"Gruppe 3", members, 0));
        gm.addGroup(new Group(3,"Gruppe 4", members, 0));


        // Placeholder Groups
        //groups.add(new Group("Sailing", members));



        AdapterHome adapter = new AdapterHome(this, gm.getGroups());
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
                intent = new Intent(this, Profile.class);
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
}
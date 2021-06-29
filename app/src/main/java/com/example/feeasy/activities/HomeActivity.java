package com.example.feeasy.activities;

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

import com.example.feeasy.dataManagement.GroupManager;
import com.example.feeasy.R;
import com.example.feeasy.adapters.AdapterHome;
import com.example.feeasy.entities.Fee;
import com.example.feeasy.entities.Group;
import com.example.feeasy.entities.GroupMember;

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

        /*
        *
        * Just for testing
        *
         */

        //List<Group> groups = new ArrayList<>();
        List<GroupMember> members = new ArrayList<>();
        List<Fee> fees = new ArrayList<>();
        List<Fee> presets = new ArrayList<>();
        Group group = new Group(1337, "Tolle Gruppe", members, fees, presets);
        Group group2 = new Group(69, "Nicht so tolle Gruppe", members, fees, presets);
        GroupMember member = new GroupMember("Roman", true, 1);
        GroupMember member2 = new GroupMember("Fabi", false, 2);
        GroupMember member3 = new GroupMember("Luki", false, 3);
        GroupMember member4 = new GroupMember("Leon", false, 4);
        GroupMember member5 = new GroupMember("Sandro", false, 5);
        GroupMember member6 = new GroupMember("Herwig", false, 6);


        Fee fee1 = new Fee("Too late",group, member, 5,"28.06.2021");
        Fee fee2 = new Fee("Lost challenge", group, member2, 10,"28.06.2021");
        Fee fee3 = new Fee("Got rickrolled", group, member4, 30,"27.06.2021");
        Fee fee4 = new Fee("Too late",group, member2, 2,"27.06.2021");
        Fee fee5 = new Fee("something", group, member3, 5,"26.06.2021");
        Fee fee6 = new Fee("whatever", group, member3, 10,"25.06.2021");
        Fee fee7 = new Fee("Did not show up", group, member4, 100,"25.06.2021");
        Fee fee8 = new Fee("Just deserves it", group, member, 15,"23.06.2021");
        Fee fee9 = new Fee("Inted", group, member, 29,"23.06.2021");


        fees.add(fee1);
        fees.add(fee2);
        fees.add(fee3);
        fees.add(fee4);
        fees.add(fee5);
        fees.add(fee6);
        fees.add(fee7);
        fees.add(fee8);
        fees.add(fee9);

        presets.add(fee1);
        presets.add(fee2);



        // Placeholder members
        members.add(member);
        members.add(member2);
        members.add(member3);
        members.add(member4);
        members.add(member5);
        members.add(member6);
        GroupManager.addGroup(group);
        GroupManager.addGroup(group2);

        /*
        *
         */


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
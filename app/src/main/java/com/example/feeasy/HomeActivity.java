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

import java.lang.reflect.Member;
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
        Group group = new Group(0, "Tolle Gruppe", members, fees);
        Group group2 = new Group(0, "Nicht so tolle Gruppe", members, fees);
        GroupMember member = new GroupMember("Roman", true, 1);
        GroupMember member2 = new GroupMember("Leon", false, 2);

        Fee fee1 = new Fee(group, member, 300,"28.06.2021");
        Fee fee2 = new Fee(group, member, 12,"28.06.2021");
        Fee fee3 = new Fee(group, member, 30330,"28.06.2021");
        Fee fee4 = new Fee(group, member2, 11,"28.06.2021");

        fees.add(fee1);
        fees.add(fee2);
        fees.add(fee3);
        fees.add(fee4);


        // Placeholder members
        members.add(member);
        members.add(member2);
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
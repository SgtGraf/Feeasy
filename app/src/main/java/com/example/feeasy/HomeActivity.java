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
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        List<Group> groups = new ArrayList<>();

        // Placeholder Groups
        groups.add(new Group("Sailing"));
        groups.add(new Group("Football"));
        groups.add(new Group("Uni"));
        groups.add(new Group("Mateys"));
        groups.add(new Group("Bar Night"));
        groups.add(new Group("School group"));
        groups.add(new Group("Never"));
        groups.add(new Group("Gonna"));
        groups.add(new Group("Give"));
        groups.add(new Group("You"));
        groups.add(new Group("Up"));


        Adapter adapter = new Adapter(this, groups);
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
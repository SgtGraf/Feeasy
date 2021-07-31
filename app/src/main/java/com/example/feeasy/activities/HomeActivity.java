package com.example.feeasy.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.feeasy.Threads.Connection;
import com.example.feeasy.dataManagement.AppDataManager;
import com.example.feeasy.dataManagement.DataManager;
import com.example.feeasy.R;
import com.example.feeasy.adapters.AdapterHome;
import com.example.feeasy.entities.ActionNames;
import com.example.feeasy.entities.Group;
import com.example.feeasy.entities.GroupMember;

import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterHome adapter;

    Handler groupListHandler = new GroupListHandler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        View buttonAddGroup = findViewById(R.id.button_addGroup);

        buttonAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddGroupActivity.class);
                startActivity(intent);
            }
        });

        adapter = new AdapterHome(this, DataManager.getDataManager().getGroupList());
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
        AppDataManager.getAppDataManager().setCurrentHandler(groupListHandler);
        // TODO: could be removed
        Connection connection = new Connection();
        connection.handleAction(ActionNames.ALL_GROUPS,  new JSONObject());
        //-------------------------------------------------------------------
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
    }

    private class GroupListHandler extends Handler {

        public GroupListHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.obj == ActionNames.ALL_GROUPS){
                if(msg.arg1 == 0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        }
    }


}
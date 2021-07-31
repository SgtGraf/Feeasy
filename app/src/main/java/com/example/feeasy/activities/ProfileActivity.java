package com.example.feeasy.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.feeasy.R;
import com.example.feeasy.Threads.Connection;
import com.example.feeasy.dataManagement.DataManager;
import com.example.feeasy.entities.ActionNames;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    Connection connection = new Connection();
    DataManager dataManager;
    TextView username;
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dataManager = DataManager.getDataManager();
        Button btn = findViewById(R.id.profile_done);
        username = findViewById(R.id.profile_displayname_input);
        username.setText(dataManager.getLoggedInUser().name);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("name", username.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                updateDisplayname(jsonObject);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.profile_menu_logout){
            openDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        dataManager.signOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void openDialog(){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Sign out?");
        b.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                signOut();
            }
        });
        b.setNegativeButton("CANCEL", null);
        b.show();
    }

    public void updateDisplayname(JSONObject jsonObject){
        connection.handleAction(ActionNames.UPDATE_MEMBER, jsonObject);
        username.setText(dataManager.getLoggedInUser().name);
    }
}
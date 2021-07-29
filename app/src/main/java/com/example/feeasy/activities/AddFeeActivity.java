package com.example.feeasy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feeasy.R;
import com.example.feeasy.Threads.Connection;
import com.example.feeasy.dataManagement.GroupManager;
import com.example.feeasy.entities.ActionNames;
import com.example.feeasy.entities.Group;
import com.example.feeasy.entities.GroupMember;

import org.json.JSONException;
import org.json.JSONObject;

public class AddFeeActivity extends AppCompatActivity {

    Group group;
    GroupMember member;

    CheckBox saveAsPreset;
    EditText feeName;
    EditText feeAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fee);

        group = GroupManager.getGroupByID(getIntent().getIntExtra("groupId",-1));
        member = GroupManager.getMemberFromGroupById(group,getIntent().getIntExtra("memberId",-1));

        TextView memberNameView = findViewById(R.id.add_fee_member_name);
        memberNameView.setText(member.name);

        saveAsPreset = findViewById(R.id.add_fee_save_preset);
        feeName = findViewById(R.id.add_fee_name);
        feeAmount = findViewById(R.id.add_fee_amount);
        Button addButton = findViewById(R.id.add_fee_button_confirm);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = feeName.getText().toString().trim();
                String amountValue = feeAmount.getText().toString().trim();
                if(name.length() > 0 && name.length() <= 100 && !amountValue.isEmpty()){
                    float amount = Float.parseFloat(amountValue);
                    if(amount > 0){
                        // TODO: thread
                        GroupManager.createFee(name,group,member,amount,"00.00.0000");

                        // Pass to Thread
                        Connection connection = new Connection();

                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject
                                    .put("name", name)
                                    .put("group_id", group.id)
                                    .put("user_id", member.id)
                                    .put("amount", amount);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        connection.handleAction(ActionNames.CREATE_FEE, jsonObject);

                        if(saveAsPreset.isChecked()){
                            // TODO: thread
                            connection.handleAction(ActionNames.SAVE_PRESET, jsonObject);
                            GroupManager.createFeePreset(name,group,amount);
                        }
                        onBackPressed();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Amount must be higher than 0.",Toast.LENGTH_SHORT).show();
                    }
                }
                else if(name.length() > 100){
                    Toast.makeText(getApplicationContext(),"Fee name cannot be longer than 100 characters.",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please enter fee name and amount.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
package com.example.feeasy.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feeasy.R;
import com.example.feeasy.Threads.Connection;
import com.example.feeasy.dataManagement.AppDataManager;
import com.example.feeasy.dataManagement.DataManager;
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
    Button addButton;

    boolean backAllowed = true;

    Handler handler = new AddFeeHandler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fee);

        group = DataManager.getDataManager().getGroup(getIntent().getIntExtra("groupId",-1));
        member = DataManager.getDataManager().getMemberOfGroup(group.id, getIntent().getIntExtra("memberId", -1));

        TextView memberNameView = findViewById(R.id.add_fee_member_name);
        memberNameView.setText(member.name);

        saveAsPreset = findViewById(R.id.add_fee_save_preset);
        feeName = findViewById(R.id.add_fee_name);
        feeAmount = findViewById(R.id.add_fee_amount);
        addButton = findViewById(R.id.add_fee_button_confirm);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = feeName.getText().toString().trim();
                String amountValue = feeAmount.getText().toString().trim();
                if(name.length() > 0 && name.length() <= 100 && !amountValue.isEmpty()){
                    float amount = Float.parseFloat(amountValue);
                    if(amount > 0){
                        Connection connection = new Connection();
                        connection.handleAction(ActionNames.CREATE_FEE, buildJSONObject1(name,group.id, member.id, amount));
                        if(saveAsPreset.isChecked()){
                            connection.handleAction(ActionNames.SAVE_PRESET, buildJSONObject2(name,group.id, amount));
                        }
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

    private JSONObject buildJSONObject1(String name, int groupId, int userId, float amount){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name).put("group_id", groupId).put("user_id", userId).put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private JSONObject buildJSONObject2(String name, int groupId, float amount){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name).put("group_id", groupId).put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    protected void onResume() {
        AppDataManager.getAppDataManager().setCurrentHandler(handler);
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if(backAllowed){
            super.onBackPressed();
        }
    }

    private class AddFeeHandler extends Handler{

        public AddFeeHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.obj == ActionNames.CREATE_FEE){
                if(msg.arg1 == 0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Fee added.",Toast.LENGTH_SHORT).show();
                            backAllowed = true;
                            onBackPressed();
                        }
                    });
                }
                else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            backAllowed = true;
                            addButton.setEnabled(true);
                            Toast.makeText(getApplicationContext(),"Fee could not be added.",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }
    }
}
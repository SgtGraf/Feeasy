package com.example.feeasy.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feeasy.R;
import com.example.feeasy.Threads.Connection;
import com.example.feeasy.activities.GroupActivity;
import com.example.feeasy.dataManagement.GroupManager;
import com.example.feeasy.entities.ActionNames;
import com.example.feeasy.entities.Fee;
import com.example.feeasy.entities.FeePreset;
import com.example.feeasy.entities.Group;
import com.example.feeasy.entities.GroupMember;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AdapterUserFees extends RecyclerView.Adapter<AdapterUserFees.ViewHolder> {

    Group group;
    List<FeePreset> presets;
    Context context;
    GroupMember groupMember;

    public AdapterUserFees(Context ct, List<FeePreset> presets, Group group, GroupMember groupMember) {
        context = ct;
        this.presets = presets;
        this.group = group;
        this.groupMember = groupMember;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView presetName;
        TextView presetAmount;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            presetName = itemView.findViewById(R.id.fee_preset_fee_name);
            presetAmount = itemView.findViewById(R.id.fee_preset_amount);
            constraintLayout = itemView.findViewById(R.id.preset_clickable);
        }
    }

    @NonNull
    @Override
    public AdapterUserFees.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fee_preset, parent, false);
        return new AdapterUserFees.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUserFees.ViewHolder holder, int position) {
        final FeePreset preset = group.presets.get(position);

        holder.presetName.setText(preset.name);
        holder.presetAmount.setText(preset.amount + "$");
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject
                            .put("name", preset.name)
                            .put("group_id", group.id)
                            .put("user_id", groupMember.id)
                            .put("amount", preset.amount);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Connection connection = new Connection();
                connection.handleAction(ActionNames.CREATE_FEE, jsonObject);

                GroupManager.createFee(preset.name, group, groupMember, preset.amount, "00.00.0000");

                Toast.makeText(context, "Added \"" + preset.name + "\" to " + groupMember.name, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return presets.size();
    }

}

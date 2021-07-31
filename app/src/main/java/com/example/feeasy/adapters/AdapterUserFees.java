package com.example.feeasy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feeasy.R;
import com.example.feeasy.Threads.Connection;
import com.example.feeasy.entities.ActionNames;
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
                Connection connection = new Connection();
                connection.handleAction(ActionNames.CREATE_FEE, buildJSONObject(preset.name,group.id, groupMember.id, preset.amount));
            }
        });
    }

    @Override
    public int getItemCount() {
        return presets.size();
    }

    private JSONObject buildJSONObject(String name, int groupId, int userId, float amount){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name).put("group_id", groupId).put("user_id", userId).put("amount", amount);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}

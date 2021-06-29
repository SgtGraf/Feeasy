package com.example.feeasy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feeasy.R;
import com.example.feeasy.entities.Fee;
import com.example.feeasy.entities.Group;

import java.util.List;

public class AdapterUserFees extends RecyclerView.Adapter<AdapterUserFees.ViewHolder> {

    Group group;
    List<Fee> presets;
    Context context;

    public AdapterUserFees(Context ct, List<Fee> presets, Group group) {
        context = ct;
        this.presets = presets;
        this.group = group;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView presetName;
        TextView presetAmount;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            presetName = itemView.findViewById(R.id.fee_preset_fee_name);
            presetAmount = itemView.findViewById(R.id.fee_preset_amount);
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
        Fee preset = group.presets.get(position);

        holder.presetName.setText(preset.name);
        holder.presetAmount.setText(preset.amount + "$");
    }

    @Override
    public int getItemCount() {
        return presets.size();
    }



}

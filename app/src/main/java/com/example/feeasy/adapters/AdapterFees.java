package com.example.feeasy.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.feeasy.entities.Fee;
import com.example.feeasy.entities.Group;
import com.example.feeasy.entities.GroupMember;
import com.example.feeasy.R;

import java.util.List;

public class AdapterFees extends RecyclerView.Adapter<AdapterFees.ViewHolder> {
    Group group;
    List<GroupMember> groupMembers;
    List<Fee> fees;
    Context context;


    public AdapterFees(Context ct, List<Fee> fees, Group group) {
        context = ct;
        this.fees = fees;
        this.group = group;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView memberName;
        TextView memberFees;
        View layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            memberName = itemView.findViewById(R.id.member_name_fee);
            memberFees = itemView.findViewById(R.id.member_fee_fee);
            layout = itemView.findViewById(R.id.group_member_layout);
        }
    }

    @NonNull
    @Override
    public AdapterFees.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fee_list_view, parent, false);
        return new AdapterFees.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final AdapterFees.ViewHolder holder, final int position) {
        holder.memberName.setText(fees.get(position).groupMember.name);
        holder.memberFees.setText(fees.get(position).amount + "$");
        holder.memberName.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View view) {
                Log.i("What", "Test");
            }
        });
    }

    @Override
    public int getItemCount() {
        return fees.size();
    }
}

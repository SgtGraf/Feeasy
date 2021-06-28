package com.example.feeasy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            memberName = itemView.findViewById(R.id.member_name_fee);
            memberFees = itemView.findViewById(R.id.member_fee_fee);
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

                Intent intent = new Intent(context, GroupActivity.class);
                //intent.putExtra("id", groupMembers.get(position).id);

                //context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fees.size();
    }
}

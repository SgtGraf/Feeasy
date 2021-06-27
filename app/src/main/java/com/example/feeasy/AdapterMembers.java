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

public class AdapterMembers extends RecyclerView.Adapter<AdapterMembers.ViewHolder>{

    List<GroupMember> groupMembers;
    Context context;

    public AdapterMembers(Context ct, List<GroupMember> members) {
        context = ct;
        groupMembers = members;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView memberName;
        TextView memberFees;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            memberName = itemView.findViewById(R.id.memberName);
            memberFees = itemView.findViewById(R.id.member_fee);
        }
    }

    @NonNull
    @Override
    public AdapterMembers.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.group_member, parent, false);
        return new AdapterMembers.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final AdapterMembers.ViewHolder holder, final int position) {
        holder.memberName.setText(groupMembers.get(position).name);
        holder.memberFees.setText(groupMembers.get(position).feeAmount + "$");
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
        return groupMembers.size();
    }
}

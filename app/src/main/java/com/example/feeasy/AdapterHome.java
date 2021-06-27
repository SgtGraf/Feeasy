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

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.ViewHolder>{

    List<Group> groupList;
    Context context;

    public AdapterHome(Context ct, List<Group> groups) {
        context = ct;
        groupList = groups;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView groupName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            groupName = itemView.findViewById(R.id.groupName);
        }
    }

    @NonNull
    @Override
    public AdapterHome.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterHome.ViewHolder holder, final int position) {
        holder.groupName.setText(groupList.get(position).groupName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, GroupActivity.class);
                intent.putExtra("id", groupList.get(position).id);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }
}

package com.example.feeasy.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.feeasy.R;
import com.example.feeasy.activities.AddFeeActivity;
import com.example.feeasy.adapters.AdapterUserFees;
import com.example.feeasy.dataManagement.ItemViewModel;
import com.example.feeasy.entities.Group;
import com.example.feeasy.entities.GroupMember;

public class UserFeesFragment extends Fragment {

    RecyclerView recyclerView;
    private ItemViewModel itemViewModel;
    int groupId;
    Group group;
    GroupMember groupMember;
    AdapterUserFees adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_user_fees, container, false);
        // Inflate the layout for this fragment

        itemViewModel = ViewModelProviders.of(getActivity()).get(ItemViewModel.class);
        itemViewModel.getMember().observe(getViewLifecycleOwner(), new Observer<GroupMember>() {
            @Override
            public void onChanged(GroupMember member) {
                groupMember = member;
            }
        });

        itemViewModel.getGroup().observe(getViewLifecycleOwner(), new Observer<Group>() {
            @Override
            public void onChanged(Group group) {
                adapter = new AdapterUserFees(getContext(), group.presets, group, groupMember);
                recyclerView = v.findViewById(R.id.recycler_group_member);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });

        ImageButton addFeeButton = v.findViewById(R.id.add_fee_button);
        addFeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddFeeActivity.class);
                intent.putExtra("groupId",itemViewModel.getGroup().getValue().id);
                intent.putExtra("memberId",itemViewModel.getMember().getValue().id);
                startActivity(intent);
            }
        });


        /*itemViewModel.getGroupId().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(CharSequence charSequence) {
                groupId = Integer.parseInt(charSequence.toString());
                group =  GroupManager.getGroupByID(groupId);
                assert GroupManager.getGroupByID(groupId) != null;
                Log.i("Group ID FEE FRAGMENT", charSequence.toString());
                AdapterUserFees adapter = new AdapterUserFees(getContext(), GroupManager.getGroupByID(groupId).presets, group);
                recyclerView = v.findViewById(R.id.recycler_group_member);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });*/
        return v;
    }

    @Override
    public void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }
}
package com.example.feeasy.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.feeasy.R;
import com.example.feeasy.adapters.AdapterFees;
import com.example.feeasy.dataManagement.GroupManager;
import com.example.feeasy.dataManagement.ItemViewModel;
import com.example.feeasy.entities.Group;
import com.example.feeasy.entities.GroupMember;

public class UserHistoryFragment extends Fragment {
    RecyclerView recyclerView;
    private AdapterFees adapter;
    private ItemViewModel itemViewModel;
    int groupId;
    Group group;
    GroupMember member;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_user_history, container, false);

        itemViewModel = ViewModelProviders.of(getActivity()).get(ItemViewModel.class);

        group = itemViewModel.getGroup().getValue();
        member = itemViewModel.getMember().getValue();
        adapter = new AdapterFees(getContext(),GroupManager.getFeesByMember(group,member),group);
        recyclerView = v.findViewById(R.id.feeRecycler_user_history);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        return v;
    }

    @Override
    public void onResume() {
        adapter.setDataSet(GroupManager.getFeesByMember(group,member),group);
        adapter.notifyDataSetChanged();
        super.onResume();
    }
}
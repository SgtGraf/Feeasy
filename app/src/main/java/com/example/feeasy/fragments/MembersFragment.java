package com.example.feeasy.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.feeasy.dataManagement.GroupManager;
import com.example.feeasy.dataManagement.ItemViewModel;
import com.example.feeasy.R;
import com.example.feeasy.adapters.AdapterMembers;
import com.example.feeasy.entities.Group;

public class MembersFragment extends Fragment {

    RecyclerView recyclerView;

    private ItemViewModel itemViewModel;
    int groupId;
    Group group;
    AdapterMembers adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_members, container, false);

        itemViewModel = ViewModelProviders.of(getActivity()).get(ItemViewModel.class);
        itemViewModel.getGroup().observe(getViewLifecycleOwner(), new Observer<Group>() {
            @Override
            public void onChanged(Group grp) {
                group = grp;
                adapter = new AdapterMembers(getContext(), group.members, group);
                recyclerView = v.findViewById(R.id.group_recycler);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
        /*itemViewModel.getGroupId().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(CharSequence charSequence) {
                groupId = Integer.parseInt(charSequence.toString());
                group =  GroupManager.getGroupByID(groupId);
                assert GroupManager.getGroupByID(groupId) != null;
                AdapterMembers adapter = new AdapterMembers(getContext(), GroupManager.getGroupByID(groupId).members, group);
                recyclerView = v.findViewById(R.id.group_recycler);
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
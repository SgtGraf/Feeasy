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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_members, container, false);

        itemViewModel = ViewModelProviders.of(getActivity()).get(ItemViewModel.class);
        itemViewModel.getText().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
            @Override
            public void onChanged(CharSequence charSequence) {
                groupId = Integer.parseInt(charSequence.toString());
                group =  GroupManager.getGroupPerID(groupId);
                assert GroupManager.getGroupPerID(groupId) != null;
                AdapterMembers adapter = new AdapterMembers(getContext(), GroupManager.getGroupPerID(groupId).members, group);
                recyclerView = v.findViewById(R.id.group_recycler);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
        return v;
    }

}
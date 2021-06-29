package com.example.feeasy.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.feeasy.R;
import com.example.feeasy.adapters.AdapterUserFees;
import com.example.feeasy.dataManagement.GroupManager;
import com.example.feeasy.dataManagement.ItemViewModel;
import com.example.feeasy.entities.Group;

public class UserFeesFragment extends Fragment {

    RecyclerView recyclerView;
    private ItemViewModel itemViewModel;
    int groupId;
    Group group;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_user_fees, container, false);
        // Inflate the layout for this fragment

        itemViewModel = ViewModelProviders.of(getActivity()).get(ItemViewModel.class);
        itemViewModel.getText().observe(getViewLifecycleOwner(), new Observer<CharSequence>() {
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
        });
        return v;
    }
}
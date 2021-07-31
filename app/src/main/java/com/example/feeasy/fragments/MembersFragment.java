package com.example.feeasy.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.feeasy.Threads.Connection;
import com.example.feeasy.dataManagement.AppDataManager;
import com.example.feeasy.dataManagement.GroupManager;
import com.example.feeasy.dataManagement.ItemViewModel;
import com.example.feeasy.R;
import com.example.feeasy.adapters.AdapterMembers;
import com.example.feeasy.entities.ActionNames;
import com.example.feeasy.entities.Group;

import org.json.JSONException;
import org.json.JSONObject;

public class MembersFragment extends Fragment {

    RecyclerView recyclerView;

    private ItemViewModel itemViewModel;
    int groupId;
    Group group;
    AdapterMembers adapter;

    Handler handler = new MemberFragmentHandler(Looper.getMainLooper());

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
        return v;
    }
    @Override
    public void onResume() {
        AppDataManager.getAppDataManager().setCurrentHandler(handler);
        Connection connection = new Connection();
        connection.handleAction(ActionNames.ALL_MEMBERS,buildJSONObject(group.id));
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    public JSONObject buildJSONObject(int groupId){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("group_id", groupId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private class MemberFragmentHandler extends Handler {

        public MemberFragmentHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.obj == ActionNames.ALL_MEMBERS){
                if(msg.arg1 == 0){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        }
    }
}
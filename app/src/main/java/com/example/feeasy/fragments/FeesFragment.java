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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.feeasy.Threads.Connection;
import com.example.feeasy.activities.GroupActivity;
import com.example.feeasy.dataManagement.AppDataManager;
import com.example.feeasy.dataManagement.ItemViewModel;
import com.example.feeasy.R;
import com.example.feeasy.adapters.AdapterFees;
import com.example.feeasy.entities.ActionNames;
import com.example.feeasy.entities.Group;

import org.json.JSONException;
import org.json.JSONObject;

public class FeesFragment extends Fragment {
    RecyclerView recyclerView;
    private AdapterFees adapter;
    private ItemViewModel itemViewModel;
    int groupId;
    Group group;

    Handler handler = new FeeFragmentHandler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_fees, container, false);

        itemViewModel = ViewModelProviders.of(getActivity()).get(ItemViewModel.class);
        itemViewModel.getGroup().observe(getViewLifecycleOwner(), new Observer<Group>() {
            @Override
            public void onChanged(Group grp) {
                group =  grp;
                adapter = new AdapterFees(getContext(), group.fees, group);
                recyclerView = v.findViewById(R.id.feeRecycler);
                recyclerView.setAdapter(adapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setReverseLayout(true);
                layoutManager.setStackFromEnd(true);
                recyclerView.setLayoutManager(layoutManager);
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        AppDataManager.getAppDataManager().setCurrentHandler(handler);
        Connection connection = new Connection();
        connection.handleAction(ActionNames.ALL_FEES_OF_GROUP,buildJSONObject(group.id));
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

    private class FeeFragmentHandler extends Handler{

        public FeeFragmentHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.obj == ActionNames.ALL_FEES_OF_GROUP){
                if(msg.arg1 == 0){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            ((GroupActivity)getActivity()).updateSum();
                        }
                    });
                }
            }
            if(msg.obj == ActionNames.SET_FEE_STATUS){
                if(msg.arg1 == 0){
                    Connection connection = new Connection();
                    connection.handleAction(ActionNames.ALL_FEES_OF_GROUP,buildJSONObject(group.id));
                }
            }
        }
    }
}
package com.example.feeasy.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.feeasy.R;
import com.example.feeasy.Threads.Connection;
import com.example.feeasy.activities.GroupActivity;
import com.example.feeasy.activities.GroupMemberActivity;
import com.example.feeasy.adapters.AdapterFees;
import com.example.feeasy.dataManagement.AppDataManager;
import com.example.feeasy.dataManagement.ItemViewModel;
import com.example.feeasy.entities.ActionNames;
import com.example.feeasy.entities.Group;
import com.example.feeasy.entities.GroupMember;

import org.json.JSONException;
import org.json.JSONObject;

public class UserHistoryFragment extends Fragment {
    RecyclerView recyclerView;
    private AdapterFees adapter;
    private ItemViewModel itemViewModel;
    int groupId;
    Group group;
    GroupMember member;

    Handler handler = new UserFeesHandler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_user_history, container, false);

        itemViewModel = ViewModelProviders.of(getActivity()).get(ItemViewModel.class);

        group = itemViewModel.getGroup().getValue();
        member = itemViewModel.getMember().getValue();
        adapter = new AdapterFees(getContext(),member.fees,group);
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
        AppDataManager.getAppDataManager().setCurrentHandler(handler);
        Connection connection = new Connection();
        connection.handleAction(ActionNames.FEES_OF_USER_IN_GROUP,buildJSONObject(group.id, member.id));
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    public JSONObject buildJSONObject(int groupId, int userId){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("group_id", groupId).put("user_id",userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private class UserFeesHandler extends Handler {

        public UserFeesHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.obj == ActionNames.FEES_OF_USER_IN_GROUP){
                if(msg.arg1 == 0){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
            if(msg.obj == ActionNames.SET_FEE_STATUS){
                if(msg.arg1 == 0){
                    Connection connection = new Connection();
                    connection.handleAction(ActionNames.FEES_OF_USER_IN_GROUP,buildJSONObject(group.id, member.id));
                }
            }
        }
    }
}
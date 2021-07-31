package com.example.feeasy.fragments;

import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.feeasy.R;
import com.example.feeasy.Threads.Connection;
import com.example.feeasy.activities.AddFeeActivity;
import com.example.feeasy.activities.GroupMemberActivity;
import com.example.feeasy.adapters.AdapterUserFees;
import com.example.feeasy.dataManagement.AppDataManager;
import com.example.feeasy.dataManagement.ItemViewModel;
import com.example.feeasy.entities.ActionNames;
import com.example.feeasy.entities.Group;
import com.example.feeasy.entities.GroupMember;

import org.json.JSONException;
import org.json.JSONObject;

public class UserFeesFragment extends Fragment {

    RecyclerView recyclerView;
    private ItemViewModel itemViewModel;
    int groupId;
    Group group;
    GroupMember groupMember;
    AdapterUserFees adapter;

    Handler handler = new UserPresetsHandler(Looper.getMainLooper());

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
            public void onChanged(Group grp) {
                group = grp;
                adapter = new AdapterUserFees(getContext(), grp.presets, grp, groupMember);
                recyclerView = v.findViewById(R.id.recycler_group_member);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });

        ImageView addFeeButton = v.findViewById(R.id.add_fee_button);
        addFeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddFeeActivity.class);
                intent.putExtra("groupId",group.id);
                intent.putExtra("memberId",groupMember.id);
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        AppDataManager.getAppDataManager().setCurrentHandler(handler);
        Connection connection = new Connection();
        connection.handleAction(ActionNames.ALL_PRESETS_OF_GROUP,buildJSONObject(group.id));
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

    private class UserPresetsHandler extends Handler{

        public UserPresetsHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.obj == ActionNames.ALL_PRESETS_OF_GROUP){
                if(msg.arg1 == 0){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
            if(msg.obj == ActionNames.CREATE_FEE){
                if(msg.arg1 == 0){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "Added fee to " + groupMember.name, Toast.LENGTH_SHORT).show();
                            Connection connection = new Connection();
                            connection.handleAction(ActionNames.ALL_FEES_OF_GROUP,buildJSONObject(group.id));
                        }
                    });
                }
            }
            if(msg.obj == ActionNames.SAVE_PRESET){
                if(msg.arg1 == 0){
                    Connection connection = new Connection();
                    connection.handleAction(ActionNames.ALL_PRESETS_OF_GROUP,buildJSONObject(group.id));
                }
            }
        }
    }
}
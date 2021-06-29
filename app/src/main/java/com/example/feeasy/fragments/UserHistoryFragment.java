package com.example.feeasy.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.feeasy.R;
import com.example.feeasy.entities.Group;

public class UserHistoryFragment extends Fragment {

    int groupId;
    Group group;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_history, container, false);
    }
}
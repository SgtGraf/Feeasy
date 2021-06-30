package com.example.feeasy.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.feeasy.fragments.UserFeesFragment;
import com.example.feeasy.fragments.UserHistoryFragment;

public class AdapterFragmentGroupMember extends FragmentStateAdapter {
    public AdapterFragmentGroupMember(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 1) {
            return new UserHistoryFragment();
        }
        return new UserFeesFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
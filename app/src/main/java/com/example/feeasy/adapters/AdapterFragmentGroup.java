package com.example.feeasy.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.feeasy.fragments.FeesFragment;
import com.example.feeasy.fragments.MembersFragment;

public class AdapterFragmentGroup extends FragmentStateAdapter {
    public AdapterFragmentGroup(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 1) {
            return new FeesFragment();
        }
        return new MembersFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

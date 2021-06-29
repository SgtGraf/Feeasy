package com.example.feeasy.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.feeasy.R;
import com.example.feeasy.adapters.AdapterFragmentGroup;
import com.example.feeasy.adapters.AdapterFragmentGroupMember;
import com.example.feeasy.dataManagement.GroupManager;
import com.example.feeasy.dataManagement.ItemViewModel;
import com.example.feeasy.entities.Group;
import com.google.android.material.tabs.TabLayout;

public class GroupMemberActivity extends AppCompatActivity {

    AdapterFragmentGroupMember fragmentAdapter;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    private ItemViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_member);
        getSupportActionBar().hide();
        tabLayout = findViewById(R.id.tab_layout_groupmember_activity);
        viewPager = findViewById(R.id.view_pager_groupmember_activity);

        tabManagment();

        // Get group ID from Intent
        Intent intent = getIntent();
        int id = intent.getIntExtra("groupId", -1);
        Group group =  GroupManager.getGroupPerID(id);

        // Pass group ID to Fragment
        Log.i("Group ID before passs", Integer.toString(id));
        viewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        viewModel.setText(Integer.toString(id));

    }

    public void tabManagment(){
        FragmentManager fm = getSupportFragmentManager();
        fragmentAdapter = new AdapterFragmentGroupMember(fm, getLifecycle());
        viewPager.setAdapter(fragmentAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("New Fee"));
        tabLayout.addTab(tabLayout.newTab().setText("History"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }
}
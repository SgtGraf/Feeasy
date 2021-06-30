package com.example.feeasy.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.feeasy.R;
import com.example.feeasy.adapters.AdapterFragmentGroupMember;
import com.example.feeasy.dataManagement.GroupManager;
import com.example.feeasy.dataManagement.ItemViewModel;
import com.example.feeasy.entities.Group;
import com.example.feeasy.entities.GroupMember;
import com.google.android.material.tabs.TabLayout;

public class GroupMemberActivity extends AppCompatActivity {

    AdapterFragmentGroupMember fragmentAdapter;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    private ItemViewModel viewModel;
    TextView membername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_member);
        tabLayout = findViewById(R.id.tab_layout_groupmember_activity);
        viewPager = findViewById(R.id.view_pager_groupmember_activity);
        membername = findViewById(R.id.activity_groupmember_name);

        // Get group/member ID from Intent
        Intent intent = getIntent();
        int groupId = intent.getIntExtra("groupId", -1);
        int memberId = intent.getIntExtra("memberId", -1);
        Group group = GroupManager.getGroupByID(groupId);
        GroupMember member = GroupManager.getMemberFromGroupById(group, memberId);


        // Pass group ID to Fragment
        viewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        viewModel.setGroupId(Integer.toString(groupId));
        viewModel.setGroup(group);
        viewModel.setMember(member);
        //viewModel.setMemberId(group);

        tabManagement();

        // set Views
        membername.setText(member.name);
    }

    public void tabManagement(){
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
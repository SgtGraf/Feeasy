package com.example.feeasy.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.feeasy.dataManagement.GroupManager;
import com.example.feeasy.dataManagement.ItemViewModel;
import com.example.feeasy.R;
import com.example.feeasy.adapters.FragmentAdapter;
import com.example.feeasy.entities.Group;
import com.google.android.material.tabs.TabLayout;

public class GroupActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    FragmentAdapter fragmentAdapter;
    private ItemViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        getSupportActionBar().hide();


        TextView groupNameView = findViewById(R.id.groupName);
        TextView totalAmtView = findViewById(R.id.groupTotal);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);


        tabManagment();

        // Get group id from intent
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        Group group =  GroupManager.getGroupPerID(id);

        // Set id values for fragments
        viewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        viewModel.setText(Integer.toString(id));

        // Set strings
        assert group != null;
        groupNameView.setText(group.groupName);
        totalAmtView.setText(Float.toString(GroupManager.getFeesPerGroup(group)));
    }

    //public void set

    public void tabManagment(){
        FragmentManager fm = getSupportFragmentManager();
        fragmentAdapter = new FragmentAdapter(fm, getLifecycle());
        viewPager.setAdapter(fragmentAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("Members"));
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
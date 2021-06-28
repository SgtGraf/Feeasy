package com.example.feeasy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class GroupActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    FragmentAdapter fragmentAdapter;
    private ItemViewModel viewModel;

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        getSupportActionBar().hide();


        TextView groupNameView = findViewById(R.id.groupName);
        TextView totalAmtView = findViewById(R.id.groupTotal);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        /*
            Everything for tabs
         */
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

        /*
        ---------
         */

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        Group group =  GroupManager.getGroupPerID(id);

        viewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        Log.i("GroupActivity ID:",Integer.toString(id));
        viewModel.setText(Integer.toString(id));

        assert group != null;
        groupNameView.setText(group.groupName);
        totalAmtView.setText(Float.toString(GroupManager.getFeesPerGroup(group)));

        Log.i("Fees:", Integer.toString(group.totalFees));

        assert GroupManager.getGroupPerID(id) != null;
        AdapterMembers adapter = new AdapterMembers(this, GroupManager.getGroupPerID(id).members, group);
        recyclerView = findViewById(R.id.group_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
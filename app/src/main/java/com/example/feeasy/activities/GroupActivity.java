package com.example.feeasy.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feeasy.dataManagement.GroupManager;
import com.example.feeasy.dataManagement.ItemViewModel;
import com.example.feeasy.R;
import com.example.feeasy.adapters.AdapterFragmentGroup;
import com.example.feeasy.entities.Group;
import com.google.android.material.tabs.TabLayout;

public class GroupActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    AdapterFragmentGroup fragmentAdapter;
    private ItemViewModel viewModel;
    TextView totalAmtView;

    Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        TextView groupNameView = findViewById(R.id.groupName);
        totalAmtView = findViewById(R.id.groupTotal);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        tabManagment();

        // Get group id from intent
        Intent intent = getIntent();
        int id = intent.getIntExtra("groupId", -1);
        // TODO: thread
        group = GroupManager.getGroupByID(id);

        // Set id values for fragments
        viewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        viewModel.setGroupId(Integer.toString(id));
        viewModel.setGroup(group);

        // Set strings
        assert group != null;
        groupNameView.setText(group.groupName);
        totalAmtView.setText(Float.toString(GroupManager.getFeeSumByGroup(group)));
    }

    //public void set

    public void tabManagment(){
        FragmentManager fm = getSupportFragmentManager();
        fragmentAdapter = new AdapterFragmentGroup(fm, getLifecycle());
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

    @Override
    protected void onResume() {
        totalAmtView.setText(Float.toString(GroupManager.getFeeSumByGroup(group)));
        super.onResume();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, AddMemberActivity.class);
        intent.putExtra("groupId", group.id);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }
}
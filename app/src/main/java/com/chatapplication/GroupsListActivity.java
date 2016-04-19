package com.chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class GroupsListActivity extends AppCompatActivity {

    private ListView groupsList;
    private ArrayAdapter groupsListAdapter;
    private ArrayList<String> groupNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_list);

        groupNames = new ArrayList<String>();
        groupNames.add("Group 1");
        groupNames.add("Group 2");

        groupsList = (ListView) findViewById(R.id.groupsList);
        groupsListAdapter = new ArrayAdapter<String>(this, R.layout.conversation_list_item, groupNames);
        groupsList.setAdapter(groupsListAdapter);

        groupsList.post(new Runnable() {
            @Override
            public void run() {
                groupsList.smoothScrollToPosition(0);
            }
        });

        groupsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GroupsListActivity.this, ConversationActivity.class);
                startActivity(intent);
            }
        });

    }

}

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

public class ConversationsListActivity extends AppCompatActivity {

    private ListView conversationsList;
    private ArrayAdapter conversationsListAdapter;
    private ArrayList<String> conversationsNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations_list);

        conversationsNames = new ArrayList<String>();
        conversationsNames.add("Friend 1");
        conversationsNames.add("Friend 2");

        conversationsList = (ListView) findViewById(R.id.conversationsList);
        conversationsListAdapter = new ArrayAdapter<String>(this, R.layout.conversation_list_item, conversationsNames);
        conversationsList.setAdapter(conversationsListAdapter);

        conversationsList.post(new Runnable() {
            @Override
            public void run() {
                conversationsList.smoothScrollToPosition(0);
            }
        });
        conversationsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ConversationsListActivity.this, ConversationActivity.class);
                startActivity(intent);
            }
        });

    }

}

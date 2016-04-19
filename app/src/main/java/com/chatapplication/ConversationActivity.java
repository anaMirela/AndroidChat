package com.chatapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ConversationActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Button sendButton;
    private ListView messagesList;
    private EditText messageText;
    private  ArrayAdapter conversationListAdapter;
    private ArrayList<String> messages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        toolbar = (Toolbar) findViewById(R.id.conversationToolbar);
        setSupportActionBar(toolbar);

        sendButton = (Button) findViewById(R.id.sendButton);
        messagesList = (ListView) findViewById(R.id.messagesList);
        messageText = (EditText) findViewById(R.id.messageToBeSent);

        // TODO - add old messages
        messages = new ArrayList<String>();
        messages.add("Hello");
        messages.add("Buna");

        conversationListAdapter = new ArrayAdapter<String>(this, R.layout.conversation_list_item, messages);
        messagesList.setAdapter(conversationListAdapter);

        sendButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        messages.add(messageText.getText().toString());
        messageText.setText("");
        conversationListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.conversation_menu, menu);
        return true;
    }
}

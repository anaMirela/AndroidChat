package com.chatapplication;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ConversationActivity extends AppCompatActivity implements View.OnClickListener {

    RequestFactory requestFactory;

    private Toolbar toolbar;
    private Button sendButton;
    private ListView messagesList;
    private EditText messageText;
    private  ArrayAdapter conversationListAdapter;
    private ArrayList<String> messages;

    private String senderId;
    private String receiverId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        toolbar = (Toolbar) findViewById(R.id.conversationToolbar);
        setSupportActionBar(toolbar);

        sendButton = (Button) findViewById(R.id.sendButton);
        messagesList = (ListView) findViewById(R.id.messagesList);
        messageText = (EditText) findViewById(R.id.messageToBeSent);

        requestFactory = new RequestFactory();
        messages = new ArrayList<String>();

        setSenderId(getIntent().getStringExtra("userId"));
        setReceiverId(getIntent().getStringExtra("receiverId"));

        Log.i("sender", getSenderId());
        Log.i("receiver", getReceiverId());
        new GetMessagesOperation().execute();

        conversationListAdapter = new ArrayAdapter<String>(this, R.layout.conversation_list_item, messages);
        messagesList.setAdapter(conversationListAdapter);

        sendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        messages.add(messageText.getText().toString());
        messageText.setText("");
        conversationListAdapter.notifyDataSetChanged();
        new SendMessageOperation().execute();
        Log.i("tag", "button clicked");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.conversation_menu, menu);
        return true;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    private class GetMessagesOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String query = "";
              if (getSenderId() != null && getReceiverId() != null) {
                 query = "?action=getmessages&senderid=" + getSenderId() + "&receiverid=" + getReceiverId();
            }
            InputStream is = requestFactory.createGetRequest(query);
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(is));
            try {
               // System.out.println(">>>>>>>>" + inputStream.readLine());
                String messagesJsonArray = inputStream.readLine();
                if (messagesJsonArray != null) {
                    System.out.println(">>>>>>>>" + messagesJsonArray);
                } else {
                    System.out.println(">>>>>>NULL");
                }

                JSONArray jsonArray = new JSONArray(messagesJsonArray);
                for (int i = 0; i < jsonArray.length(); i++) {
                    System.out.println(">>>>>>>>" + jsonArray.get(i).toString());
                    JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                    getMessages().add(jsonObject.getString("content"));
                    System.out.println(">>>>>>>>content " + jsonObject.getString("content"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
               e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }

    private class SendMessageOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            requestFactory.createSendMessageRequest(getSenderId(), getReceiverId(),
                    getMessages().get(getMessages().size() - 1));
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }

}

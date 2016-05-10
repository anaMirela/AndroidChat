package com.chatapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class FriendsListActivity extends AppCompatActivity {

    private TreeMap<String, String> friendsMap = new TreeMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        Intent intent = getIntent();
        String jsondata = intent.getStringExtra("jsondata");

        JSONArray friendslist;
        ArrayList<String> friends = new ArrayList<String>();
        try {
            friendslist = new JSONArray(jsondata);
            for (int i = 0; i < friendslist.length(); i++) {
               // friends.add(friendslist.getJSONObject(i).getString("name"));
                friendsMap.put(friendslist.getJSONObject(i).getString("id"),
                        friendslist.getJSONObject(i).getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, friends);
        FriendsListAdapter adapter = new FriendsListAdapter(friendsMap);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FriendsListActivity.this, ConversationActivity.class);
                intent.putExtra("userId", getIntent().getStringExtra("userId"));

                ArrayList<String> receiver =(ArrayList<String>) parent.getItemAtPosition(position);
                Log.i("object", receiver.get(0));
                intent.putExtra("receiverId", receiver.get(0));
                startActivity(intent);
            }
        });
    }

}

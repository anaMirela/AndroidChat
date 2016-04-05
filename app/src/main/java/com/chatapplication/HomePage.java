package com.chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;

import org.json.JSONArray;
import org.json.JSONException;

public class HomePage extends AppCompatActivity {

    private TextView info;
    private ImageView profileImgView;
    private Button friendsListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        info = (TextView) findViewById(R.id.info);
        profileImgView = (ImageView) findViewById(R.id.profile_img);
        friendsListButton = (Button) findViewById(R.id.friendsList);

        Profile profile = Profile.getCurrentProfile();
        info.setText("Welcome " + profile.getName());

        String profileImgUrl = "https://graph.facebook.com/" + profile.getId() + "/picture?type=large";
        Log.i("profile img", profileImgUrl);
        Glide.with(HomePage.this)
                .load(profileImgUrl)
                .into(profileImgView);

        friendsListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GraphRequestAsyncTask graphRequestAsyncTask = new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/me/friends",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                Intent intent = new Intent(HomePage.this, FriendsListActivity.class);
                                try {
                                    JSONArray rawName = response.getJSONObject().getJSONArray("data");
                                    intent.putExtra("jsondata", rawName.toString());
                                    startActivity(intent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).executeAsync();
            }
        });
        }

}

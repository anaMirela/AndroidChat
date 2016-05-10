package com.chatapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Mi on 4/4/2016.
 */
public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    RequestFactory requestFactory;
    String userLoggedId;
    String userLoggedName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookSDKInitialize();
        setContentView(R.layout.activity_main);

        requestFactory = new RequestFactory();

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        getLoginDetails(loginButton);
    }

    protected void getLoginDetails(LoginButton loginButton){

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile profile = Profile.getCurrentProfile();
                setUserLoggedId(profile.getId());
                setUserLoggedName(profile.getName());
                new ConnectOperation().execute();
                Log.i("tag", profile.getId());
                Intent intent = new Intent(MainActivity.this, HomePage.class);
                intent.putExtra("profileName", profile.getName());
                intent.putExtra("userId", profile.getId());
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                // code for cancellation
            }

            @Override
            public void onError(FacebookException exception) {
                //  code to handle error
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    protected void facebookSDKInitialize() {

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    public String getUserLoggedId() {
        return userLoggedId;
    }

    public String getUserLoggedName() {
        return userLoggedName;
    }

    public void setUserLoggedName(String userLoggedName) {
        this.userLoggedName = userLoggedName;
    }

    public void setUserLoggedId(String userLoggedId) {
        this.userLoggedId = userLoggedId;
    }

    private class ConnectOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String query = "";
            if (getUserLoggedId() != null) {
                try {
                    query = "?action=login&facebookid=" + getUserLoggedId() + "&name=" + URLEncoder.encode(getUserLoggedName(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            InputStream is = requestFactory.createGetRequest(query);
            //BufferedReader inputStream = new BufferedReader(new InputStreamReader(is));

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }


}

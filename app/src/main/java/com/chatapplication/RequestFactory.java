package com.chatapplication;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Mi on 5/8/2016.
 */
public class RequestFactory {

    public static final String SERVER_URL = "http://192.168.0.100/server_chat/index.php";

    public InputStream createGetRequest(String query) {
        HttpURLConnection con;
        try {
            con = (HttpURLConnection) new URL(SERVER_URL + query).openConnection();
            Log.i("tag", con.getResponseCode() + "");
            return con.getInputStream();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public InputStream createSendMessageRequest(String senderId, String receiverId, String msg) {
        HttpURLConnection con;
        String query = "?action=sendmsg";
        try {
            con = (HttpURLConnection) new URL(SERVER_URL + query).openConnection();
            con.setDoOutput(true);
           // con.setRequestMethod("POST");
            byte[] postDataBytes = createSendMessagePostBody(senderId, receiverId, msg);
            //con.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            con.getOutputStream().write(postDataBytes);

            return con.getInputStream();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] createSendMessagePostBody(String senderId, String receiverId, String msg) {
        Map<String,String> params = new LinkedHashMap<>();
        params.put("senderid", senderId);
        params.put("receiverid", receiverId);
        params.put("msg_content", msg);

        StringBuilder postData = new StringBuilder();
        try {
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (postData.length() != 0) {
                    postData.append('&');
                }
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));


            }
            return postData.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

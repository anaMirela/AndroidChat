package com.chatapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Mi on 5/10/2016.
 */
public class FriendsListAdapter extends BaseAdapter {

    private TreeMap<String, String> mData = new TreeMap<String, String>();
    private String[] mKeys;

    public FriendsListAdapter(TreeMap<String, String> data){
        mData  = data;
        mKeys = mData.keySet().toArray(new String[data.size()]);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        ArrayList<String> result = new ArrayList<String>();
        result.add(mKeys[position]);
        result.add(mData.get(mKeys[position]));
        return result;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        View result;

        String key = mKeys[pos];
        String value = getItem(pos).toString();
        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_listview, parent, false);
        } else {
            result = convertView;
        }
        ((TextView)result.findViewById(R.id.friend_item)).setText(value);

        return result;
    }


}

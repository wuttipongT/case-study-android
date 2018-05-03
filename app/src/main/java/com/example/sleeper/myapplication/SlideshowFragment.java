package com.example.sleeper.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.santalu.emptyview.EmptyView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class SlideshowFragment extends Fragment {
    ListView listView;
    EmptyView emptyView;
    public SlideshowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Register");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);
        listView = view.findViewById(R.id.example_list);//findViewById(R.id.example_list);
        emptyView = view.findViewById(R.id.empty_view);

        BusinessLayer businessLayer = new BusinessLayer();
        emptyView.showLoading();
        businessLayer.getRegister( new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.listview_ex);
                    int i = 0;
                    for( ; i < jsonArray.length() ; ++i){
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String concatStr = obj.get("name").toString() + " " +obj.get("lastname").toString() + " (" + obj.get("height_school").toString() + ")";
                        arrayAdapter.add(concatStr);
                    }

                    listView.setAdapter(arrayAdapter);
                    emptyView.showContent();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline

            }
        });

        return view;
    }
}

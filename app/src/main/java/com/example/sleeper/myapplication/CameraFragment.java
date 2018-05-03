package com.example.sleeper.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.Voice;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.santalu.emptyview.EmptyView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends Fragment {

    private String apiPath = "https://randomuser.me/api/?results=50";
    private ProgressDialog dialog;
    private JSONArray rsJsonArray;
    private int success = 0;
    private BusinessLayer businessLayer = new BusinessLayer();
    private ListView listView;
    private Activity mainActivity;
    EmptyView emptyView;
    public CameraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Service");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        listView = view.findViewById(R.id.example_list);//findViewById(R.id.example_list);
        emptyView = view.findViewById(R.id.empty_view);
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        listView = (ListView) getActivity().findViewById(R.id.example_list);//findViewById(R.id.example_list);
//        new ServiceStubAsyncTask(getActivity().getApplicationContext(), getActivity()).execute();


    }

    @Override
    public void onStart() {
        super.onStart();
        emptyView.showLoading();
        businessLayer.getData(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("asd", "---------------- this is response : " + response);
                String[] clubList = {"Arsenal", "Chelsea", "Everton",
                        "Liverpool", "Man City", "Man Utd", "Spurs" };
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    rsJsonArray = serverResp.getJSONArray("results");

                    ArrayAdapter listViewAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.listview_ex);
                    for (int i = 0; i< rsJsonArray.length(); ++i){
                        try {
                            JSONObject jsonObject = rsJsonArray.getJSONObject(i);
                            listViewAdapter.add(jsonObject.get("email"));
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    listView.setAdapter(listViewAdapter);
                    emptyView.showContent();

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline

            }
        });
    }

    private class ServiceStubAsyncTask extends AsyncTask<Void, Void, Void>{

        private Context mContext;
        private Activity mActivity;
        String response = "";
        HashMap<String, String> postDataParams;

        public ServiceStubAsyncTask(Context context, Activity activity){
            mContext = context;
            mActivity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(mContext);
            dialog.setMessage("Please Wai ...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            postDataParams = new HashMap<String, String>();
            postDataParams.put("HTTP_ACCEPT", "application/json");
            postDataParams.put("Access-Control-Allow-Origin", "*");

            HttpConnectionService service = new HttpConnectionService();
            response = service.sendRequest(apiPath, postDataParams);
            try {
                success = 1;
                JSONObject resultJsonObject = new JSONObject(response);
                rsJsonArray = resultJsonObject.getJSONArray("results");
            }catch (JSONException e){
                success = 0;
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(dialog.isShowing()){
                dialog.dismiss();
            }

            if(success == 1){
                if(null != rsJsonArray){
                    ArrayAdapter listViewAdapter = new ArrayAdapter<String>(mContext, R.layout.listview_ex);
                    for (int i = 0; i< rsJsonArray.length(); ++i){
                        try {
                            JSONObject jsonObject = rsJsonArray.getJSONObject(i);
                            listViewAdapter.add(jsonObject.get("email"));
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    listView.setAdapter(listViewAdapter);
                }
            }
        }
    }
}

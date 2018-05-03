package com.example.sleeper.myapplication;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment {


    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private Button btnSave;
    private ProgressDialog dialog;
    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Input Register");
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);
        editText2 = (EditText) v.findViewById(R.id.editText2);
        editText3 = (EditText) v.findViewById(R.id.editText3);
        editText4 = (EditText) v.findViewById(R.id.editText4);
        btnSave = (Button) v.findViewById(R.id.btnSave);

//        editText2.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                editText2.setFocusable(true);
//                editText2.setFocusableInTouchMode(true);
//                return false;
//            }
//
//        });
//        editText2.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {
//
//                    if(editText2.getText().toString().equals("bed")){
//                        editText2.clearFocus();
//                        editText4.requestFocus();
//                        return true;
//                    }
//                }
//
//                return false;
//            }
//        });

        editText2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if(editText2.getText().toString().equals("D0GF204ZA023")){
                        return true;
                    }
                }

                return false;
            }
        });

        editText3.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if(editText3.getText().toString().equals("bed")){
                        return true;
                    }
                }

                return false;
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(),"Saved!",Toast.LENGTH_SHORT).show();

//                new ServiceStubAsyncTask(getActivity(), getActivity()).execute();
                BusinessLayer businessLayer = new BusinessLayer();
                HashMap<String, String> paramMap = new HashMap<String, String>();
                paramMap.put("Access-Control-Allow-Origin", "*");
                paramMap.put("name", editText2.getText().toString());
                paramMap.put("lastname", editText3.getText().toString());
                paramMap.put("school", editText4.getText().toString());
                RequestParams params = new RequestParams(paramMap);

                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Please Wai ...");
                dialog.setCancelable(false);
                dialog.show();
                businessLayer.insertData(params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // If the response is JSONObject instead of expected JSONArray
                        Log.d("asd","success!");
                        dialog.dismiss();
                        editText2.setText("");
                        editText3.setText("");
                        editText4.setText("");
                        Toast.makeText(getActivity(),"Saved!", Toast.LENGTH_SHORT).show();
                        editText2.requestFocus();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                        // Pull out the first event on the public timeline
                        Log.d("asd","success!");
                    }
                });

            }
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private class ServiceStubAsyncTask extends AsyncTask<Void, Void, Void> {

        private Context mContext;
        private Activity mActivity;
        String response = "";
        HashMap<String, String> postDataParams;
        private BusinessLayer businessLayer = new BusinessLayer();

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

            HashMap<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("Access-Control-Allow-Origin", "*");
            paramMap.put("name", editText2.getText().toString());
            paramMap.put("lastname", editText3.getText().toString());
            paramMap.put("school", editText4.getText().toString());

            RequestParams params = new RequestParams(paramMap);
            businessLayer.getData(new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // If the response is JSONObject instead of expected JSONArray
                    Log.d("asd", "---------------- this is response : " + response);
                    String[] clubList = {"Arsenal", "Chelsea", "Everton",
                            "Liverpool", "Man City", "Man Utd", "Spurs" };
                    try {
                        JSONObject serverResp = new JSONObject(response.toString());


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

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
        }
    }

}

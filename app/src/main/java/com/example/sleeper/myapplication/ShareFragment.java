package com.example.sleeper.myapplication;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment extends Fragment {


    private TextView txtResult;
    private CheckBox chk;
    private String sex;
    private View view;
    RadioGroup radioSexGroup;
    ToggleButton toggleButton;
    Switch aSwitch;
    MultiAutoCompleteTextView multiAutoCompleteTextView;
    String[] fruits = {"Apple", "Banana", "Cherry", "Date", "Grape", "Kiwi", "Mango", "Pear"};

    public ShareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Component Example");

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_share, container, false);
        Button btn = view.findViewById(R.id.button);
        txtResult = view.findViewById(R.id.txtResult);
        chk = view.findViewById(R.id.checkBox2);
        radioSexGroup = ((RadioGroup) view.findViewById(R.id.radioSex));
        toggleButton = view.findViewById(R.id.toggleButton);
        aSwitch = view.findViewById(R.id.switch1);
        AutoCompleteTextView actv = view.findViewById(R.id.autoCompleteTextView);
        multiAutoCompleteTextView = view.findViewById(R.id.multiAutoCompleteTextViewEmail);

        // Set multiAutoCompleteTextView related attribute value in java code.
        multiAutoCompleteTextView.setPadding(15,15,15,15);
        multiAutoCompleteTextView.setBackgroundColor(getResources().getColor(R.color.colorBlue, null));
        multiAutoCompleteTextView.setTextColor(getResources().getColor(R.color.colorGreen, null));

        // Get the string array from strings.xml file.
        String emailArr[] = getResources().getStringArray(R.array.email_array);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.listview_ex, emailArr);
        // Connect the data source with AutoCompleteTextView through adapter.
        multiAutoCompleteTextView.setAdapter(arrayAdapter);

        // Must set tokenizer for MultiAutoCompleteTextView object, otherwise it will not take effect.
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.listview_ex, fruits);
        actv.setThreshold(1);//will start working form first charecter
        actv.setAdapter(adapter);
        actv.setTextColor(Color.RED);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = chk.isChecked();
                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                if(selectedId == -1){
                    Toast.makeText(getActivity(),
                            "Please your select choice!", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton radioButton = view.findViewById(selectedId);
                txtResult.setText(Boolean.toString(isChecked) + "\n" + Boolean.toString(toggleButton.isChecked()) + "\n"+ radioButton.getText() + "\n" + Boolean.toString(aSwitch.isChecked()));

                String userInputEmail = multiAutoCompleteTextView.getText().toString();

                AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
                dialog.setMessage(userInputEmail);
                dialog.show();
            }
        });



        return view;
    }
    public void onRadioButtonClicked(View v) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_male:
                if (checked)
                    // Pirates are the best
//                    sex = ((RadioButton) view).getText().toString();
                    break;
            case R.id.radio_female:
                if (checked)
                    // Ninjas rule
//                    sex = ((RadioButton) view).getText().toString();
                    break;
        }
    }
}

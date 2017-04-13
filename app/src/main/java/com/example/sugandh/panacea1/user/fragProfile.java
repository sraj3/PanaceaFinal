package com.example.sugandh.panacea1.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sugandh.panacea1.AsyncRequest;
import com.example.sugandh.panacea1.R;
import com.example.sugandh.panacea1.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * A simple {@link Fragment} subclass.
 */

public class fragProfile extends Fragment implements AsyncRequest.OnAsyncRequestComplete {
    Button b1;
    EditText et_name,et_house,et_locality,et_lMark,et_city,et_state,et_pincode;
    String name,house,locality,lMark,state,pincode;
    String address;
    String username;
    String json_string;

    public fragProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_frag_profile, container, false);
        username= User.getEmail();
        et_name=(EditText)view.findViewById(R.id.editText);
        et_house=(EditText)view.findViewById(R.id.editText2);
        et_lMark=(EditText)view.findViewById(R.id.editText3);
        et_locality=(EditText)view.findViewById(R.id.editText4);
        et_city=(EditText)view.findViewById(R.id.editText8);
        et_state=(EditText)view.findViewById(R.id.editText9);
        et_pincode=(EditText)view.findViewById(R.id.editText10);

        b1 = (Button) view.findViewById(R.id.button13);

        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(hasText(et_name) && hasText(et_house) && hasText(et_locality)
                        && hasText(et_lMark) && hasText(et_city) && hasText(et_state)&& hasText(et_pincode)) {

                    address = et_name.getText().toString()+"   " + et_house.getText().toString() +"   "+ et_locality.getText().toString()
                            +"      "+ et_city.getText().toString()+"      "+ et_state.getText().toString()
                            +"  "+ et_pincode.getText().toString();
                    pincode = et_pincode.getText().toString();
                    try {
                        executeBackgroundTask();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }
            }
        });


        return view;
    }

    public static boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        if (text.length() == 0) {
            editText.setError("fill out this field");
            return false;
        }
        return true;
    }

    private void executeBackgroundTask() throws UnsupportedEncodingException {

        String data_string=URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"+
                  URLEncoder.encode("address","UTF-8")+"="+URLEncoder.encode(address,"UTF-8")+"&"+
                  URLEncoder.encode("pincode","UTF-8")+"="+URLEncoder.encode(pincode,"UTF-8")+"&";
        String url ="http://apppanacea.000webhostapp.com/add_user_detail.php";
        AsyncRequest asyncRequest=new AsyncRequest(this,"POST",data_string);
        asyncRequest.execute(url);
    }

    @Override
    public void asyncResponse(String response) {
            Toast.makeText(getContext(),response, Toast.LENGTH_SHORT).show();
    }

}

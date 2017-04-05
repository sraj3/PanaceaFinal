package com.example.sugandh.panacea1;


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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragProfile extends Fragment {
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
        username=User.getEmail();
        et_name=(EditText)view.findViewById(R.id.editText);
        et_house=(EditText)view.findViewById(R.id.editText2);
        et_locality=(EditText)view.findViewById(R.id.editText3);
        et_lMark=(EditText)view.findViewById(R.id.editText4);
        et_city=(EditText)view.findViewById(R.id.editText8);
        et_state=(EditText)view.findViewById(R.id.editText9);
        et_pincode=(EditText)view.findViewById(R.id.editText10);

        b1 = (Button) view.findViewById(R.id.button13);

        SmsManager smsManager=SmsManager.getDefault();
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
                    new BackgroundTask().execute();
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


    class BackgroundTask extends AsyncTask<String,String,String>
    {
        String json_get_user_url;
        private ProgressDialog dialog = new ProgressDialog(getContext());

        @Override
        protected void onPreExecute() {
            json_get_user_url="http://utilties.netai.net/add_user_detail.php";
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url=new URL(json_get_user_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string=
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"+
                                URLEncoder.encode("address","UTF-8")+"="+URLEncoder.encode(address,"UTF-8")+"&"+
                                URLEncoder.encode("pincode","UTF-8")+"="+URLEncoder.encode(pincode,"UTF-8")+"&";
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();

                InputStream inputStream= httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder=new StringBuilder();
                while((json_string=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(json_string+"\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return json_string;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Error Occured";
        }
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Toast.makeText(getContext(),json_string, Toast.LENGTH_SHORT).show();
                //try {
                //    JSONObject jObj = new JSONObject(String.valueOf(jsonString));
                //    boolean error = jObj.getBoolean("error");
                //
                //    if (!error) {
                //        Intent i=new Intent(uspDetail2.this,homeActivity.class);
                //        i.putExtra("jsonData",result);
                //        startActivity(i);
                //    }
                //    else {
                //        Toast.makeText(getContext(),result, Toast.LENGTH_SHORT).show();
                //    }
                //}catch (JSONException e) {
                //    e.printStackTrace();
                //}
        }

    }
}

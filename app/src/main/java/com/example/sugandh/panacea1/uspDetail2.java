package com.example.sugandh.panacea1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.List;

public class uspDetail2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText et_title,et_exp,et_qualif,et_desc;
    Spinner s1,s2;
    Button button;
    String jsonString,json_string;
    String username,password;

    String address,pincode;
    String title,service,experience,qualification,desc;
    List<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usp_detail2);

        jsonString=getIntent().getExtras().getString("jsonData");
        try {
            JSONObject jObj = new JSONObject(String.valueOf(jsonString));
            boolean error = jObj.getBoolean("error");

            if (!error) {
                String name = jObj.getString("name");
                username = jObj.getString("email");
                password = jObj.getString("password");
                setTitle(name);
                Toast.makeText(getApplicationContext(),"Hello "+name, Toast.LENGTH_SHORT).show();

            }
            else {
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



        address=getIntent().getExtras().getString("address");
        pincode=getIntent().getExtras().getString("pincode");

        s1 = (Spinner) findViewById(R.id.spinner2);
        s2 = (Spinner) findViewById(R.id.spinner3);
        et_title= (EditText) findViewById(R.id.editText5);
        et_qualif= (EditText) findViewById(R.id.editText13);
        et_exp= (EditText) findViewById(R.id.editText14);
        et_desc= (EditText) findViewById(R.id.editText15);

        button = (Button) findViewById(R.id.button);
        s1.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        service = String.valueOf(s1.getSelectedItem());
//        if(service.contentEquals("select a service"))
//        {
//            Toast.makeText(getApplicationContext(),"select a service first",Toast.LENGTH_SHORT).show();
//        }
        list.clear();
        list.add("select service sub-type");
        if (service.contentEquals("Carpentry")) {
            list.add("Tap");
            list.add("Leakage");
            list.add("Water Supply");
        }
        if (service.contentEquals("Electrician")) {
            list.add("Tv");
            list.add("AC");
            list.add("Refrigerator");
        }

        if (service.contentEquals("Gas")) {
            list.add("Tap");
            list.add("Leakage");
            list.add("Water Supply");
        }
        if (service.contentEquals("Mason")) {
            list.add("Tap");
            list.add("Leakage");
            list.add("Water Supply");
        }
        if (service.contentEquals("Mechanic")) {
            list.add("Tap");
            list.add("Leakage");
            list.add("Water Supply");
        }
        if (service.contentEquals("Painting")) {
            list.add("Tap");
            list.add("Leakage");
            list.add("Water Supply");
        }
        if (service.contentEquals("Plumbing")) {
            list.add("Tap");
            list.add("Leakage");
            list.add("Water Supply");
        }
        if (service.contentEquals("Welding")) {
            list.add("Tap");
            list.add("Leakage");
            list.add("Water Supply");
        }
        setSpinnerAdapter();
    }


    private void setSpinnerAdapter() {

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        s2.setAdapter(dataAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void submitDetail(View view) {

        Toast.makeText(uspDetail2.this,
                "OnClickListener : " +
                        "\nSpinner 1 : "+ String.valueOf(s1.getSelectedItem()) +address+pincode+service,
                Toast.LENGTH_SHORT).show();


        if(hasText(et_title) && hasText(et_qualif) && hasText(et_exp)
                && hasText(et_desc)) {

            title = et_title.getText().toString();
            qualification = et_qualif.getText().toString();
            experience = et_exp.getText().toString();
            desc = et_desc.getText().toString();
            new BackgroundTask().execute();
        }
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
        private ProgressDialog dialog = new ProgressDialog(uspDetail2.this);

        @Override
        protected void onPreExecute() {
            json_get_user_url="http://utilties.netai.net/add_usp_detail.php";
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
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"+
                        URLEncoder.encode("address","UTF-8")+"="+URLEncoder.encode(address,"UTF-8")+"&"+
                        URLEncoder.encode("pincode","UTF-8")+"="+URLEncoder.encode(pincode,"UTF-8")+"&"+
                        URLEncoder.encode("service","UTF-8")+"="+URLEncoder.encode(service,"UTF-8")+
                        URLEncoder.encode("title","UTF-8")+"="+URLEncoder.encode(title,"UTF-8")+
                        URLEncoder.encode("qualification","UTF-8")+"="+URLEncoder.encode(qualification,"UTF-8")+"&"+
                        URLEncoder.encode("experience","UTF-8")+"="+URLEncoder.encode(experience,"UTF-8")+
                        URLEncoder.encode("desc","UTF-8")+"="+URLEncoder.encode(desc,"UTF-8");
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();

                InputStream inputStream= httpURLConnection.getInputStream();
//                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
//                StringBuilder stringBuilder=new StringBuilder();
//                while((json_string=bufferedReader.readLine())!=null)
//                {
//                    stringBuilder.append(json_string+"\n");
//                }
//
//                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return "Details Added";
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
            try {
                JSONObject jObj = new JSONObject(String.valueOf(jsonString));
                boolean error = jObj.getBoolean("error");

                if (!error) {
                    Intent i=new Intent(uspDetail2.this,homeActivity.class);
                    i.putExtra("jsonData",result);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}

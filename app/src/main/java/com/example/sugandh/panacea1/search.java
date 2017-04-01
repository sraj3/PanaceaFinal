package com.example.sugandh.panacea1;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.util.Calendar;
import java.util.List;

public class search extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {

    String jsonString;
    String json_string;
    String address;
    TextView textView;
    Spinner s1,s2;
    String service;
    String pincode;
    EditText et_date,et_time;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Button b1,b2;
    List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        jsonString = getIntent().getExtras().getString("jsonData");
        Toast.makeText(getApplicationContext(), "Hello " + jsonString, Toast.LENGTH_SHORT).show();

        try {
            JSONObject jObj = new JSONObject(String.valueOf(jsonString));
            boolean error = jObj.getBoolean("error");

            if (!error) {
                String name = jObj.getString("name");
                String email = jObj.getString("email");
                Boolean detailed = jObj.getBoolean("detailed");
                if(detailed) {
                    address = jObj.getString("address");
                    pincode = jObj.getString("pincode");
                    Toast.makeText(getApplicationContext(), "detailed", Toast.LENGTH_SHORT).show();
                }
                else{
                    address="Save your address first";
                }
                setTitle(name);
                Toast.makeText(getApplicationContext(), "Hello " + name, Toast.LENGTH_SHORT).show();
            }
            else {
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        textView = (TextView) findViewById(R.id.textView5);
        textView.setText(address);
        s1 = (Spinner) findViewById(R.id.spinner);
        s2 = (Spinner) findViewById(R.id.spinner01);
        s1.setOnItemSelectedListener(this);
        et_date= (EditText) findViewById(R.id.editText6);
        et_time= (EditText) findViewById(R.id.editText7);
        b1= (Button) findViewById(R.id.button17);
        b2= (Button) findViewById(R.id.button19);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);

        et_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                } else {
//                    Toast.makeText(getApplicationContext(), "lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    @Override
    public void onClick(View v) {

        if (v == b1) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            et_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();
        }
        if (v == b2) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            et_time.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

    public void findUsp(View view) {
        service=String.valueOf(s1.getSelectedItem());
        new BackgroundTask().execute();
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
    public void onNothingSelected(AdapterView<?> parent) {}


    class BackgroundTask extends AsyncTask<String, String, String> {
        String json_get_user_url;
        private ProgressDialog dialog = new ProgressDialog(search.this);

        @Override
        protected void onPreExecute() {
            json_get_user_url = "http://utilties.netai.net/find_usp.php";
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(json_get_user_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("pincode", "UTF-8") + "=" + URLEncoder.encode(pincode, "UTF-8") + "&" +
                        URLEncoder.encode("service", "UTF-8") + "=" + URLEncoder.encode(service, "UTF-8");
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((json_string = bufferedReader.readLine()) != null) {
                    stringBuilder.append(json_string + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
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
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            json_string=result;
            if(json_string==null)
            {
                Toast.makeText(getApplicationContext(), "No Service Provider Available", Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent=new Intent(getApplicationContext(),display_usp_list.class);
                intent.putExtra("json_data",json_string);
                startActivity(intent);
            }

        }
    }
}


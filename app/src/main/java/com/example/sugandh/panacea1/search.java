package com.example.sugandh.panacea1;

import  android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
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

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class search extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener,
        AsyncRequest.OnAsyncRequestComplete {

    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    private final static int PLACE_PICKER_REQUEST=1;
    //private final static LatLngBounds bounds=new LatLngBounds(new LatLng(51.5152192,-0.1321900),new LatLng(51.5166013,-0.1299262));

    String jsonString;
    String json_string;

    TextView tv_address;
    Spinner s1,s2;
    Button b1,b2;
    EditText et_date,et_time,et_desc;

    String service,pincode,address;
    private int mYear, mMonth, mDay, mHour, mMinute;
    List<String> list = new ArrayList<String>();
    Geocoder geocoder;
    List<android.location.Address> addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        requestPermission();
        geocoder= new Geocoder(this, Locale.getDefault());


        tv_address = (TextView) findViewById(R.id.textView5);
        s1 = (Spinner) findViewById(R.id.spinner);
        s2 = (Spinner) findViewById(R.id.spinner01);
        et_date= (EditText) findViewById(R.id.et_requestTime);
        et_time= (EditText) findViewById(R.id.et_requestTime);
        et_desc= (EditText) findViewById(R.id.et_description);
        b1= (Button) findViewById(R.id.button17);
        b2= (Button) findViewById(R.id.button19);

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
                    address="Your address not saved";
                }
                setTitle(name);
            }
            else {
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tv_address.setText(address);
        s1.setOnItemSelectedListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
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

     public void findUsp(View view) throws UnsupportedEncodingException {
        service=String.valueOf(s1.getSelectedItem());


        executeBackgroundTask();
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


    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSION_FINE_LOCATION:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "This app requires location permissin to be granted", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PLACE_PICKER_REQUEST){
            if (resultCode==RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getApplicationContext());
                LatLng coordinate=place.getLatLng();
                Double lat=coordinate.latitude;
                Double lon=coordinate.longitude;
                try {
                    addresses=geocoder.getFromLocation(lat,lon,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String pin=addresses.get(0).getPostalCode();
//                Toast.makeText(getApplicationContext(),place.getLatLng().toString()+" "+lat+" "+lon,Toast.LENGTH_SHORT).show();
                tv_address.setText((place.getName() + " " + place.getAddress()));
            }
        }
    }

    private void executeBackgroundTask() throws UnsupportedEncodingException {
        String data_string = URLEncoder.encode("pincode", "UTF-8") + "=" + URLEncoder.encode(pincode, "UTF-8") + "&" +
                URLEncoder.encode("service", "UTF-8") + "=" + URLEncoder.encode(service, "UTF-8");

        String url = "http://apppanacea.000webhostapp.com/find_usp.php";
        AsyncRequest asyncRequest=new AsyncRequest(this,"POST",data_string);
        asyncRequest.execute(url);
    }

    @Override
    public void asyncResponse(String result) {
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        json_string=result;
        if(json_string==null)
        {
            Toast.makeText(getApplicationContext(), "No Service Provider Available", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent=new Intent(getApplicationContext(),display_usp_list.class);
            intent.putExtra("json_data",json_string);
            intent.putExtra("service",service);
            intent.putExtra("serviceDesc",et_desc.getText().toString());
            intent.putExtra("requestDate",et_date.getText().toString());
            intent.putExtra("requestTime",et_time.getText().toString());
            intent.putExtra("address",address);
            intent.putExtra("pincode",pincode);

            startActivity(intent);
        }
    }

    public void selectLocationOnMap(View view) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        //builder.setLatLngBounds(bounds);
        try {
            Intent intent=builder.build(getApplicationContext());
            startActivityForResult(intent,PLACE_PICKER_REQUEST);
        }
        catch (GooglePlayServicesRepairableException e){
            e.printStackTrace();
        }catch (GooglePlayServicesNotAvailableException e){
            e.printStackTrace();
        }
    }
}


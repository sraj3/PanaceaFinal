package com.example.sugandh.panacea1.requestFlow;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sugandh.panacea1.AsyncRequest;
import com.example.sugandh.panacea1.R;
import com.example.sugandh.panacea1.display_usp_list;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;

public class ServiceSchedule extends AppCompatActivity implements View.OnClickListener,AsyncRequest.OnAsyncRequestComplete {
    TextView tv_serviceName;
    EditText et_requestDate,et_requestTime;
    Button b1,b2;
    String service,serviceDesc,requestDate,requestTime,address,pincode;
    private int mYear, mMonth, mDay, mHour, mMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_schedule);
        service = getIntent().getExtras().getString("service");
        serviceDesc = getIntent().getExtras().getString("serviceDesc");
        address = getIntent().getExtras().getString("address");
        pincode = getIntent().getExtras().getString("pincode");

        tv_serviceName= (TextView) findViewById(R.id.tv_serviceName);
        et_requestDate= (EditText) findViewById(R.id.et_requestDate);
        et_requestTime= (EditText) findViewById(R.id.et_requestTime);
        b1= (Button) findViewById(R.id.bt_selectDate);
        b2= (Button) findViewById(R.id.bt_selectTime);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        tv_serviceName.setText(service);
    }
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
                            et_requestDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
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

                            et_requestTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

    public void submit(View view) throws UnsupportedEncodingException {
                requestDate=et_requestDate.getText().toString();
                requestTime=et_requestTime.getText().toString();
        executeBackgroundTask();
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

            Intent intent=new Intent(getApplicationContext(),display_usp_list.class);
            intent.putExtra("json_data",result);
            intent.putExtra("service",service);
            intent.putExtra("serviceDesc",serviceDesc);
            intent.putExtra("requestDate",requestDate);
            intent.putExtra("requestTime",requestTime);
            intent.putExtra("address",address);
            intent.putExtra("pincode",pincode);

            startActivity(intent);
    }
}

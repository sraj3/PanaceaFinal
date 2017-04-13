package com.example.sugandh.panacea1.requestFlow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sugandh.panacea1.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServicePage extends AppCompatActivity {

     TextView et_serviceName,et_description;
     Spinner task;

    List<String> list = new ArrayList<String>();
    String[] subarray;
    String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_page);
        String service=getIntent().getExtras().getString("service");
        et_serviceName= (TextView) findViewById(R.id.tv_serviceName);
        et_description= (TextView) findViewById(R.id.et_description);

        task = (Spinner) findViewById(R.id.sp_task);
        et_serviceName.setText(service);

        if (service.contentEquals("Electrician")) {
            subarray=getApplication().getResources().getStringArray(R.array.Subtype_Electrician);
        }

        if (service.contentEquals("Plumber")) {
            subarray=getApplication().getResources().getStringArray(R.array.Subtype_Plumber);
        }
        if (service.contentEquals("Carpenter")) {
            subarray=getApplication().getResources().getStringArray(R.array.Subtype_carpenter);
        }
        if (service.contentEquals("Mason")) {
            subarray=getApplication().getResources().getStringArray(R.array.Subtype_Mason);
        }
        if (service.contentEquals("Painter")) {
            subarray=getApplication().getResources().getStringArray(R.array.Subtype_Painter);
        }
        if (service.contentEquals("Mechanic")) {
            subarray=getApplication().getResources().getStringArray(R.array.Subtype_Mechanic);
        }
        if (service.contentEquals("Cook")) {
            subarray=getApplication().getResources().getStringArray(R.array.Subtype_Cook);
        }
        if (service.contentEquals("Tailor")) {
            subarray=getApplication().getResources().getStringArray(R.array.Subtype_Tailor);
        }
        if (service.contentEquals("Welder")) {
            subarray=getApplication().getResources().getStringArray(R.array.Subtype_Welder);
        }
        list= Arrays.asList(subarray);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        task.setAdapter(dataAdapter);
    }

    public void submit(View view) {
        description=String.valueOf(task.getSelectedItem());
        description=description+" : "+et_description.getText().toString();
        Intent intent=new Intent(this,ServiceLocation.class);
        intent.putExtra("service",et_serviceName.getText().toString());
        intent.putExtra("serviceDesc",description);
        startActivity(intent);
    }
}

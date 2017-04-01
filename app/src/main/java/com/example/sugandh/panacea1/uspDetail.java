package com.example.sugandh.panacea1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class uspDetail extends AppCompatActivity {

    EditText et_name,et_house,et_locality,et_lMark,et_city,et_state,et_pincode;
    String name,house,locality,lMark,state,pincode;
    String jsonString,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usp_detail);

        jsonString=getIntent().getExtras().getString("jsonData");

        et_name=(EditText)findViewById(R.id.editText01);
        et_house=(EditText)findViewById(R.id.editText02);
        et_locality=(EditText)findViewById(R.id.editText03);
        et_lMark=(EditText)findViewById(R.id.editText04);
        et_city=(EditText)findViewById(R.id.editText05);
        et_state=(EditText)findViewById(R.id.editText06);
        et_pincode=(EditText)findViewById(R.id.editText07);
    }

    public void getExperience(View view)  {

        if(hasText(et_name) && hasText(et_house) && hasText(et_locality)
                && hasText(et_lMark) && hasText(et_city) && hasText(et_state)&& hasText(et_pincode)) {

            address = et_name.getText().toString()+"   " + et_house.getText().toString() +"   "+ et_locality.getText().toString()
                 +"      "+ et_city.getText().toString()+"      "+ et_state.getText().toString()
                    +"  "+ et_pincode.getText().toString();
            pincode = et_pincode.getText().toString();
            Intent i = new Intent(uspDetail.this, uspDetail2.class);

            i.putExtra("jsonData", jsonString);
            i.putExtra("address", address);
            i.putExtra("pincode", pincode);
            startActivity(i);
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
}

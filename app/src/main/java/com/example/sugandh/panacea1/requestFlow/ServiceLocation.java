package com.example.sugandh.panacea1.requestFlow;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sugandh.panacea1.R;
import com.example.sugandh.panacea1.User;
import com.example.sugandh.panacea1.UserDbHelper;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ServiceLocation extends AppCompatActivity {

    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    private final static int PLACE_PICKER_REQUEST=1;

    TextView et_serviceName,et_savedAddress,et_selectedAddress;
    Geocoder geocoder;
    List<Address> addresses;
    EditText et_name,et_house,et_lMark,et_pincode;
    String service,serviceDesc,address,pincode;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_location);
        service = getIntent().getExtras().getString("service");
        serviceDesc = getIntent().getExtras().getString("serviceDesc");

        et_serviceName= (TextView) findViewById(R.id.tv_serviceName);
        et_savedAddress= (TextView) findViewById(R.id.et_savedAddress);
        et_selectedAddress= (TextView) findViewById(R.id.et_selectedAddress);


        et_name=(EditText)findViewById(R.id.et_name);
        et_house=(EditText)findViewById(R.id.et_house);
        et_lMark=(EditText)findViewById(R.id.et_lMark);

        et_pincode= (EditText) findViewById(R.id.et_pincode);

        et_serviceName.setText(service);
        UserDbHelper userDbHelper=new UserDbHelper(this);
        user = userDbHelper.get_user();
        et_savedAddress.setText(user.getAddress());
    }

    public void selectLocationOnMap(View view) {

        requestPermission();
        geocoder= new Geocoder(this, Locale.getDefault());

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
                et_selectedAddress.setText(place.getAddress());
                String postalCode = addresses.get(0).getPostalCode();
                        et_pincode.setText(postalCode);
                Toast.makeText(getApplicationContext(),place.getName() + " " + place.getAddress(),Toast.LENGTH_LONG).show();
            }
        }
    }

    public void submit(View view) {
        if(view==et_savedAddress)
        {
            address=user.getAddress();
            pincode=user.getPincode();
        }
        else
        {
            address=et_name.getText().toString()+", " + et_house.getText().toString() +", "+et_lMark.getText().toString()+","+et_selectedAddress.getText().toString();
            pincode=et_pincode.getText().toString();
        }
        Intent intent=new Intent(this,ServiceSchedule.class);
        intent.putExtra("service",et_serviceName.getText().toString());
        intent.putExtra("serviceDesc",serviceDesc);
        intent.putExtra("address",address);
        intent.putExtra("pincode",pincode);
        startActivity(intent);
    }
}

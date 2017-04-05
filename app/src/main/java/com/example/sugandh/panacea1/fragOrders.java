package com.example.sugandh.panacea1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragOrders extends Fragment {
    Button button;
    TextView textView;


    TextView placeNameText;
    TextView placeAddressText;
    Button getPlaceButton;
    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    private final static int PLACE_PICKER_REQUEST=1;
    //private final static LatLngBounds bounds=new LatLngBounds(new LatLng(51.5152192,-0.1321900),new LatLng(51.5166013,-0.1299262));

    public fragOrders() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_orders, container, false);
        //textView = (TextView) view.findViewById(R.id.textView05);


            requestPermission();

            placeNameText = (TextView) view.findViewById(R.id.tvPlaceName);
            placeAddressText = (TextView)view. findViewById(R.id.tvPlaceAddress);
            getPlaceButton = (Button) view.findViewById(R.id.btGetPlace);
            getPlaceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    //builder.setLatLngBounds(bounds);
                    try {
                        Intent intent=builder.build(getContext());
                        startActivityForResult(intent,PLACE_PICKER_REQUEST);
                    }
                    catch (GooglePlayServicesRepairableException e){
                        e.printStackTrace();
                    }catch (GooglePlayServicesNotAvailableException e){
                        e.printStackTrace();
                    }

                }
            });
            return view;
}

    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                    Toast.makeText(getContext(), "This app requires location permissin to be granted", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PLACE_PICKER_REQUEST){
            if (resultCode==RESULT_OK){
                Place place=PlacePicker.getPlace(data, getContext());
                LatLng coordinate=place.getLatLng();
                Double lat=coordinate.latitude;
                Double lon=coordinate.longitude;
                Toast.makeText(getContext(),place.getLatLng().toString()+" "+lat+" "+lon,Toast.LENGTH_SHORT).show();
                placeNameText.setText((place.getName()));
                placeAddressText.setText(place.getAddress());

            }
        }
    }
}

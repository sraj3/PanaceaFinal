package com.example.sugandh.panacea1.user;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.digits.sdk.android.SessionListener;
import com.example.sugandh.panacea1.R;
import com.example.sugandh.panacea1.requestFlow.ServicePage;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragHome extends Fragment implements View.OnClickListener {

    private SessionListener sessionListener;
    Button b1,b2,b3,b4,b5,b6,b7,b8,b9;


    public fragHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_frag_home, container, false);

        b1= (Button) view.findViewById(R.id.bt_electrician);
        b2= (Button) view.findViewById(R.id.bt_plumber);
        b3= (Button) view.findViewById(R.id.bt_carpenter);
        b4= (Button) view.findViewById(R.id.bt_mason);
        b5= (Button) view.findViewById(R.id.bt_painter);
        b6= (Button) view.findViewById(R.id.bt_mechanic);
        b7= (Button) view.findViewById(R.id.bt_tailor);
        b8= (Button) view.findViewById(R.id.bt_cook);
        b9= (Button) view.findViewById(R.id.bt_welder);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Button b= (Button) v;
        Intent intent=new Intent(getContext(),ServicePage.class);
        intent.putExtra("service",b.getText().toString());
        startActivity(intent);
    }
}
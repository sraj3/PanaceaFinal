package com.example.sugandh.panacea1;

import android.app.Fragment;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class homeActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    String jsonString;
    String address;
    TextView textView;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sessionManager =new SessionManager(getApplicationContext());

        sessionManager.checkLogin();

        Toast.makeText(getApplicationContext(), "User Login Status: " + sessionManager.isLoggedIn(), Toast.LENGTH_LONG).show();

        jsonString=getIntent().getExtras().getString("jsonData");

            try {
                JSONObject jObj = new JSONObject(String.valueOf(jsonString));
                boolean error = jObj.getBoolean("error");

                if (!error) {
                    String name = jObj.getString("name");
                    String email = jObj.getString("email");
                    Boolean detailed = jObj.getBoolean("detailed");
                    if(detailed) {
                        address = jObj.getString("address");
                    }
                    else{
                        address="Save your address first0---";
                    }
                    setTitle(name);
                    Toast.makeText(getApplicationContext(), "Hello " + name, Toast.LENGTH_SHORT).show();
                }
                else {
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new fragHome(), "Home");
        viewPagerAdapter.addFragments(new fragOrders(), "Orders");
        fragProfile f=new fragProfile();
        viewPagerAdapter.addFragments(f, "Profile");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
//        Fragment frag = getFragmentManager().findFragmentById(f.getId());
//        View v=f.getView();

//        ((TextView) frag.getView().findViewById(R.id.textView)).setText(s);
//        android.support.v4.app.Fragment f1= viewPagerAdapter.getItem(1);
//        textView = (TextView)f1.getView().findViewById(R.id.textView05);
//        textView.setText("hello");


    }


    public void findUsp(View view) {    }

    public void submitUserDetail(View view) {    }

    public void search(View view) {

        Intent i = new Intent(homeActivity.this, search.class);
        i.putExtra("jsonData", jsonString);
        startActivity(i);
    }

    public void logout(View view) {
        sessionManager.logoutUser();
    }
}
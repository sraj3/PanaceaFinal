package com.example.sugandh.panacea1.user;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sugandh.panacea1.R;
import com.example.sugandh.panacea1.SessionManager;
import com.example.sugandh.panacea1.User;
import com.example.sugandh.panacea1.UserDbHelper;
import com.example.sugandh.panacea1.adapter.ViewPagerAdapter;
import com.example.sugandh.panacea1.search;

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
    boolean doubleBackToExitPressedOnce=false;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
//        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
//                R.string.app_name);
//        mDrawerLayout.setDrawerListener(mDrawerToggle);
//        mDrawerToggle.syncState();

        sessionManager =new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        Toast.makeText(getApplicationContext(), "User Login Status: " + sessionManager.isLoggedIn(), Toast.LENGTH_LONG).show();
        UserDbHelper userDbHelper;
        userDbHelper=new UserDbHelper(this);
        User user= (User) userDbHelper.get_user();
//
//        int  id;
//        String name1,email1,mobile,address,pincode;
//        if(cursor.moveToFirst()) {
//            id=cursor.getInt(0);
//            name1=cursor.getString(1);
//            email1=cursor.getString(2);
//            mobile=cursor.getString(3);
//            address=cursor.getString(4);
//            pincode=cursor.getString(5);

//        }while(cursor.moveToNext());
            Toast.makeText(getApplicationContext(), "id :"+user.getId()+" "+user.getMobile()+user.getAddress(), Toast.LENGTH_LONG).show();

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
//        textView = (TextView)f1.getView().findViewById(R.id.textView);
//        textView.setText("hello");


    }

    public void search(View view) {

        Intent i = new Intent(homeActivity.this, search.class);
        i.putExtra("jsonData", jsonString);
        startActivity(i);
    }

    public void logout(View view) {
        sessionManager.logoutUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce=true;
        Toast.makeText(getApplicationContext(),"Press again to exit",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 6000);

    }
}
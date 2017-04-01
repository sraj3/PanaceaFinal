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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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


    public void findUsp(View view) {

    }

    public void submitUserDetail(View view) {



    }

    public void search(View view) {

        Intent i = new Intent(homeActivity.this, search.class);
        i.putExtra("jsonData", jsonString);
        startActivity(i);
    }


///*
//
//    class BackgroundTask extends AsyncTask<String,String,String>
//    {
//        String json_get_user_url;
//        private ProgressDialog dialog = new ProgressDialog(homeActivity.this);
//
//        @Override
//        protected void onPreExecute() {
//            json_get_user_url="http://utilties.netai.net/login_user.php";
//            this.dialog.setMessage("Please wait");
//            this.dialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//                URL url=new URL(json_get_user_url);
//                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoOutput(true);
//                OutputStream outputStream=httpURLConnection.getOutputStream();
//                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
//                String data_string= URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"+
//                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&";
//                bufferedWriter.write(data_string);
//                bufferedWriter.flush();
//                bufferedWriter.close();
//
//
//
//                InputStream inputStream= httpURLConnection.getInputStream();
//                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
//                StringBuilder stringBuilder=new StringBuilder();
//                while((json_string=bufferedReader.readLine())!=null)
//                {
//                    stringBuilder.append(json_string+"\n");
//                }
//
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//                return stringBuilder.toString().trim();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return "Error Occured";
//        }
//        @Override
//        protected void onProgressUpdate(String... values) {
//            super.onProgressUpdate(values);
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            if (dialog.isShowing()) {
//                dialog.dismiss();
//            }
//            try {
//                JSONObject jObj = new JSONObject(String.valueOf(result));
//                boolean error = jObj.getBoolean("error");
//
//                if (!error) {
//                    Intent i=new Intent(MainActivity.this,homeActivity.class);
//                    i.putExtra("jsonData",result);
//                    startActivity(i);
//                }
//                else {
//                    Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }*/
}
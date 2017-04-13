package com.example.sugandh.panacea1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class verifyFirst extends AppCompatActivity implements AsyncRequest.OnAsyncRequestComplete {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_first);
    }

    public void resendConfirmation(View view) throws UnsupportedEncodingException {
        executeBackgroundTask();

    }


    private void executeBackgroundTask() throws UnsupportedEncodingException {

        String data_string= URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(User.getEmail(),"UTF-8");

        String url="http://apppanacea.000webhostapp.com/resendConfirmation.php";
        AsyncRequest asyncRequest=new AsyncRequest(this,"POST",data_string);
        asyncRequest.execute(url);


    }

    @Override
    public void asyncResponse(String response) {
        Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
    }
}

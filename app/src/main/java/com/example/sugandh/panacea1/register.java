package com.example.sugandh.panacea1;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Pattern;

public class register extends AppCompatActivity implements AsyncRequest.OnAsyncRequestComplete {
    Button b1;
    TextView t1;
    RadioGroup radioGroup;

    EditText et_name,et_email,et_mobile,et_password,et_confirmPass;
    String name,email,mobile,password,confirmPass;
    int user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        b1=(Button)findViewById(R.id.submit);
        t1=(TextView) findViewById(R.id.message);

        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected())
        {
            t1.setVisibility(View.INVISIBLE);
        }
        else
        {
            b1.setEnabled(false);
        }

    }

    public void add_user(View view) throws UnsupportedEncodingException {
        et_name=(EditText)findViewById(R.id.et_uname);
        et_email=(EditText)findViewById(R.id.et_uemail);
        et_mobile=(EditText)findViewById(R.id.et_umobile);
        et_password=(EditText)findViewById(R.id.et_upassword);
        et_confirmPass=(EditText)findViewById(R.id.et_confirmPass);
        radioGroup= (RadioGroup) findViewById(R.id.rGroup);

        name=et_name.getText().toString();
        email=et_email.getText().toString();
        mobile=et_mobile.getText().toString();
        password=et_password.getText().toString();
        confirmPass=et_confirmPass.getText().toString();
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId == -1) {
            // No item selected
            Toast.makeText(getApplicationContext(),"Select a user type",Toast.LENGTH_SHORT).show();
        }
        else{
            user=0;
            if (checkedRadioButtonId == R.id.rb_user) {
                // Do something with the button
                user=1;
            }
//            BackgroundTask backgroundTask=new BackgroundTask();
//            backgroundTask.execute(name,email,mobile,password);
            if(hasText(et_name) && hasText(et_email) && hasText(et_mobile)&&hasText(et_password)&&hasText(et_confirmPass)) {
                if(isValidName(et_name) && isValidEmail(et_email) &&isValidMobile(et_mobile) &&isValidPass(et_password) && isSame(et_confirmPass,password)) {
                    executeBackgroundTask();
                }
            }
        }
//        finish();
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
    boolean isValidName(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);
        if(text.matches("[a-zA-Z][a-zA-Z ]*"))
        {
            return true;
        } else {
            editText.setError("Invalid Name!");
            return false;
        }

    }
    boolean isValidEmail(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editText.setError("invalid Email!");
            return false;
        }
        return true;
    }
    boolean isValidMobile(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);
        if(text.matches("[0-9]{10}"))
        {
            return true;
            // if(phone.length() != 10) {
        } else {
            editText.setError("Invalid Mobile!");
            return false;
        }

    }
    boolean isValidPass(EditText editText) {
        String text = editText.getText().toString().trim();
        editText.setError(null);

        if (text.length() < 6) {
            editText.setError("Not Valid Password..Atleast 6 digits required");
            return false;
        }
        return true;
    }
    boolean isSame(EditText editText, String password) {
        String text = editText.getText().toString().trim();
        editText.setError(null);

        if (!text.equals(password)) {
            editText.setError("Password did not match!");
            return false;
        }
        return true;
    }



    private void executeBackgroundTask() throws UnsupportedEncodingException {

        String data_string= URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                URLEncoder.encode("mobile","UTF-8")+"="+URLEncoder.encode(mobile,"UTF-8")+"&"+
                URLEncoder.encode("password","UTF-8")+"="+ URLEncoder.encode(password,"UTF-8");

        String add_info_url;
        if (user==1)
            add_info_url="http://utilties.netai.net/add_user.php";
        else
            add_info_url="http://utilties.netai.net/add_usp.php";
        AsyncRequest asyncRequest=new AsyncRequest(this,"POST",data_string);
        asyncRequest.execute(add_info_url);


    }

    @Override
    public void asyncResponse(String response) {
        Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
    }

//    class BackgroundTask extends AsyncTask<String,String,String>
//    {
//        private ProgressDialog dialog = new ProgressDialog(register.this);
//
//        String add_info_url;
//        @Override
//        protected void onPreExecute() {
//            this.dialog.setMessage("Please wait");
//            this.dialog.show();
//            if (user==1)
//                add_info_url="http://utilties.netai.net/add_user.php";
//            else
//                add_info_url="http://utilties.netai.net/add_usp.php";
//        }
//        @Override
//        protected String doInBackground(String... args) {
//            String name,email,password;
//            name=args[0];
//            email=args[1];
//            mobile=args[2];
//            password=args[3];
//            try {
//                URL url=new URL(add_info_url);
//                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
//                httpURLConnection.setRequestMethod("POST");
//                httpURLConnection.setDoOutput(true);
//                OutputStream outputStream=httpURLConnection.getOutputStream();
//                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
//                String data_string= URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
//                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
//                        URLEncoder.encode("mobile","UTF-8")+"="+URLEncoder.encode(mobile,"UTF-8")+"&"+
//                        URLEncoder.encode("password","UTF-8")+"="+ URLEncoder.encode(password,"UTF-8");
//                bufferedWriter.write(data_string);
//                bufferedWriter.flush();
//                bufferedWriter.close();
//                InputStream inputStream= httpURLConnection.getInputStream();
//                inputStream.close();
//                httpURLConnection.disconnect();
//                return "One row inserted";
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return "Error occurred";
//        }
//
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
//            Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
//        }
//
//    }
}

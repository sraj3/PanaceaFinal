package com.example.sugandh.panacea1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    EditText et_username,et_password;
    String username,password;
    String json_string;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login_user(View view) {
        et_username=(EditText)findViewById(R.id.et_username01);
        et_password=(EditText)findViewById(R.id.et_password01);


        username=et_username.getText().toString();
        password=et_password.getText().toString();
        if(hasText(et_username) && hasText(et_password)) {
            if(isValidUname(et_username) && isValidPass(et_password))
            new BackgroundTask().execute();
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

    boolean isValidUname(EditText editText) {
         username=et_username.getText().toString();
       if(android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches() || android.util.Patterns.PHONE.matcher(username).matches())
           return true;
         else{
           editText.setError("invalid username!");
           return false;
       }
    }

     boolean isValidPass(EditText editText) {
        String text = editText.getText().toString().trim();
        editText.setError(null);

        if (editText.getText().toString().length()<3) {
            editText.setError("invalid password!");
            return false;
        }
        return true;
    }

    public static boolean validText(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        if (editText.getText().toString().length()<3) {
            editText.setError("invalid username!");
            return false;
        }
        return true;
    }

    public void login_sp(View view) {
        startActivity(new Intent(MainActivity.this,usp_login.class));
    }

    public void register(View view) {
        startActivity(new Intent(MainActivity.this,register.class));
    }

    class BackgroundTask extends AsyncTask<String,String,String>
    {
        String json_get_user_url;
        private ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            json_get_user_url="http://utilties.netai.net/login_user.php";
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url=new URL(json_get_user_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string= URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&";
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();



                InputStream inputStream= httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder=new StringBuilder();
                while((json_string=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(json_string+"\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Error Occured";
        }
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            try {
                JSONObject jObj = new JSONObject(String.valueOf(result));
                boolean error = jObj.getBoolean("error");
                if (!error) {
                    String name = jObj.getString("name");
                    String email = jObj.getString("email");
                    User.setEmail(email);
//                    String password = jObj.getString("password");
//                    String mobile = jObj.getString("mobile");

                    Boolean detailed = jObj.getBoolean("detailed");
                    int verified = jObj.getInt("verified");

                    if(detailed) {
                        String address= jObj.getString("address");
                        String pincode = jObj.getString("pincode");
                    }

//                    User user=new User(name,username,password,mobile);
                    if(verified==1) {
                        Intent i = new Intent(MainActivity.this, homeActivity.class);
                        i.putExtra("jsonData", result);
                        startActivity(i);
                    }
                    else
                    {
                        Intent i = new Intent(MainActivity.this, verifyFirst.class);
                        i.putExtra("jsonData", result);
                        startActivity(i);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}

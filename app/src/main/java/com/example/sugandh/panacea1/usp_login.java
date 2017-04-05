package com.example.sugandh.panacea1;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class usp_login extends AppCompatActivity implements AsyncRequest.OnAsyncRequestComplete{

    EditText et_username,et_password;
    String username,password;
    String json_string;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usp_login);



        sessionManager=new SessionManager(getApplicationContext());
        Toast.makeText(this,"Login Status"+sessionManager.isLoggedIn(),Toast.LENGTH_SHORT).show();;

        et_username=(EditText)findViewById(R.id.et_username01);
        et_password=(EditText)findViewById(R.id.et_password01);
    }

    public void login_usp(View view) throws UnsupportedEncodingException {
        username=et_username.getText().toString();
        password=et_password.getText().toString();

        if(hasText(et_username) && hasText(et_password)) {
            if(isValidUname(et_username) && isValidPass(et_password))
//                new BackgroundTask().execute();

            executeBackgroundTask();
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

    public void register(View view)  {
        startActivity(new Intent(usp_login.this,register.class));
    }

    private void executeBackgroundTask() throws UnsupportedEncodingException {

        String data_string= URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"+
                URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&";

        String url;
        url="http://utilties.netai.net/login_usp.php";
        AsyncRequest asyncRequest=new AsyncRequest(this,"POST",data_string);
        asyncRequest.execute(url);
    }


    @Override
    public void asyncResponse(String result) {
            try {
                JSONObject jObj = new JSONObject(String.valueOf(result));
                boolean error = jObj.getBoolean("error");
                if (!error) {
                    boolean detailed = jObj.getBoolean("detailed");

                    if (!detailed) {
                        Intent i = new Intent(usp_login.this, uspDetail.class);
                        i.putExtra("jsonData", result);
                        startActivity(i);
                    } else
                    {
                        String id = jObj.getString("id");
                        String email = jObj.getString("email");
                        sessionManager.createLoginSession(id, email);
                        Intent i = new Intent(usp_login.this, homeActivity.class);
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

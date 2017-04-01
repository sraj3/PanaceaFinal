package com.example.sugandh.panacea1;

/**
 * Created by sugandh on 3/24/2017.
 */

import android.os.AsyncTask;
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
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;


public class AsyncRequest extends AsyncTask<String, Integer, String> {

    OnAsyncRequestComplete caller;
    Context context;
    String method = "POST";
    String parameters = null;
    ProgressDialog pDialog = null;
     String json_string;

    // Three Constructors
    public AsyncRequest(Activity a, String m, String p) {
            caller = (OnAsyncRequestComplete) a;
            context = a;
            method = m;
            parameters = p;
        }

    public AsyncRequest(Activity a, String m) {
        caller = (OnAsyncRequestComplete) a;
        context = a;
        method = m;
        }

    public AsyncRequest(Activity a) {
        caller = (OnAsyncRequestComplete) a;
        context = a;
        }

    // Interface to be implemented by calling activity
        public interface OnAsyncRequestComplete {
            public void asyncResponse(String response);
            }


        public String doInBackground(String... urls) {
            // get url pointing to entry point of API

            try {
                URL url=new URL(urls[0].toString());
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

//
//                String data_string= URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"+
//                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&";


                bufferedWriter.write(parameters);
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

            public void onPreExecute() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading data.."); // typically you will define such
        // strings in a remote file.
                 pDialog.show();
        }

            public void onProgressUpdate(Integer... progress) {
        // you can implement some progressBar and update it in this record
        // setProgressPercent(progress[0]);

                super.onProgressUpdate(progress);
        }

        public void onPostExecute(String response) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
                }
            caller.asyncResponse(response);
            }

        protected void onCancelled(String response) {
//            if (pDialog != null && pDialog.isShowing()) {
//                pDialog.dismiss();
//                }
            caller.asyncResponse(response);
            }
}
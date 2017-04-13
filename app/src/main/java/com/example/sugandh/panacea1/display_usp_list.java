package com.example.sugandh.panacea1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sugandh.panacea1.adapter.selectUspAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class display_usp_list extends AppCompatActivity  implements selectUspAdapter.customButtonListener,AsyncRequest.OnAsyncRequestComplete{
        String json_string;
        JSONObject jsonObject;
        JSONArray jsonArray;
        selectUspAdapter uspAdapter;
        ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_usp_list);

        listView= (ListView) findViewById(R.id.listview);
        uspAdapter=new selectUspAdapter(this,R.layout.usp_row_layout);
        uspAdapter.setCustomButtonListner(this);
        listView.setAdapter(uspAdapter);
        json_string=getIntent().getExtras().getString("json_data");

        //creating usp listview with server response
        try {
            jsonObject=new JSONObject(json_string);
            jsonArray=jsonObject.getJSONArray("server_response");
            int uspId;
            String name,email,address,service,pincode,experience,mobile;

            int count=0;
            while(count<jsonArray.length())
            {
                JSONObject jo=jsonArray.getJSONObject(count);
                uspId=jo.getInt("id");
                name=jo.getString("name");
                email=jo.getString("email");
                address=jo.getString("address");
                service=jo.getString("service");
                pincode=jo.getString("pincode");
                mobile=jo.getString("mobile");

                selectUsp usp=new selectUsp(uspId,name,email,address,service,pincode,mobile);
                uspAdapter.add(usp);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {

                selectUsp usp= (selectUsp) parent.getItemAtPosition(position);
                Toast.makeText(display_usp_list.this, "Button click " + usp.getEmail(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onButtonClickListner(int position,final selectUsp usp)
    {


        AlertDialog.Builder builder= new AlertDialog.Builder(display_usp_list.this);

        builder.setTitle("Confirm");
        builder.setMessage(" Are you sure you want to Book this service provider? SMS charges may apply...!!");
        builder.setCancelable(true);

        builder .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                String s= usp.getName();
                String no=usp.getMobile();
                String id= String.valueOf(usp.getId());
                try {
                    executeBackgroundTask(id);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

//                       /* try
//                        {
//                            String SENT      = "SMS_SENT";
//                            PendingIntent sentPI = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(SENT), 0);
//
//                            getApplicationContext().registerReceiver(new BroadcastReceiver()
//                            {
//                                @Override
//                                public void onReceive(Context arg0, Intent arg1)
//                                {
//                                    int resultCode = getResultCode();
//                                    switch (resultCode)
//                                    {
//                                        case Activity.RESULT_OK:                      Toast.makeText(getBaseContext(), "SMS sent",Toast.LENGTH_LONG).show();
//                                            break;
//                                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:  Toast.makeText(getBaseContext(), "Generic failure",Toast.LENGTH_LONG).show();
//                                            break;
//                                        case SmsManager.RESULT_ERROR_NO_SERVICE:       Toast.makeText(getBaseContext(), "No service",Toast.LENGTH_LONG).show();
//                                            break;
//                                        case SmsManager.RESULT_ERROR_NULL_PDU:         Toast.makeText(getBaseContext(), "Null PDU",Toast.LENGTH_LONG).show();
//                                            break;
//                                        case SmsManager.RESULT_ERROR_RADIO_OFF:        Toast.makeText(getBaseContext(), "Radio off",Toast.LENGTH_LONG).show();
//                                            break;
//                                    }
//                                }
//                            }, new IntentFilter(SENT));
//
//                            SmsManager smsMgr = SmsManager.getDefault();
//                            smsMgr.sendTextMessage(no, null,  "hey, "+s+" We need your service....Please check your notification for details", sentPI, null);
//
//                        }
//                        catch (Exception e)
//                        {
//                            Toast.makeText(getApplicationContext(), e.getMessage()+"!\n"+"Failed to send SMS", Toast.LENGTH_LONG).show();
//                            e.printStackTrace();
//                        }
//*/
                Uri uri = Uri.parse("smsto:"+no);
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", "hey, "+s+" We need your service....Please check your notification for details");
                startActivity(it);
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog=builder.create();
        dialog.show();

    }


    private void executeBackgroundTask(String uspId) throws UnsupportedEncodingException {
        UserDbHelper userDbHelper=new UserDbHelper(this);
        User user= (User) userDbHelper.get_user();
        String id= String.valueOf(user.getId());
        String serviceName=getIntent().getExtras().getString("service");
        String serviceDesc=getIntent().getExtras().getString("serviceDesc");
        String requestDate=getIntent().getExtras().getString("requestDate");
        String requestTime=getIntent().getExtras().getString("requestTime");
        String address=getIntent().getExtras().getString("address");
        String pincode=getIntent().getExtras().getString("pincode");

        Long tsLong = System.currentTimeMillis()/1000;
        String timestamp = tsLong.toString();

        String data_string= URLEncoder.encode("uid","UTF-8")+"="+URLEncoder.encode(id,"UTF-8")+"&"+
                URLEncoder.encode("serviceName","UTF-8")+"="+URLEncoder.encode(serviceName,"UTF-8")+"&"+
                URLEncoder.encode("serviceDesc","UTF-8")+"="+URLEncoder.encode(serviceDesc,"UTF-8")+"&"+
                URLEncoder.encode("orderTimestamp","UTF-8")+"="+URLEncoder.encode(timestamp,"UTF-8")+"&"+
                URLEncoder.encode("requestDate","UTF-8")+"="+URLEncoder.encode(requestDate,"UTF-8")+"&"+
                URLEncoder.encode("requestTime","UTF-8")+"="+URLEncoder.encode(requestTime,"UTF-8")+"&"+
                URLEncoder.encode("address","UTF-8")+"="+URLEncoder.encode(address,"UTF-8")+"&"+
                URLEncoder.encode("pincode","UTF-8")+"="+URLEncoder.encode(pincode,"UTF-8")+"&"+
                URLEncoder.encode("uspId","UTF-8")+"="+URLEncoder.encode(uspId,"UTF-8");

        String url;
        url="http://apppanacea.000webhostapp.com/user_request.php";
        AsyncRequest asyncRequest=new AsyncRequest(this,"POST",data_string);
        asyncRequest.execute(url);
    }

    @Override
    public void asyncResponse(String result) {
        Toast.makeText(getApplicationContext(), "Your request is sent to the selected service provide.You willbe informed about the confirmation soon..", Toast.LENGTH_SHORT).show();
    }


}

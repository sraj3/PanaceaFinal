package com.example.sugandh.panacea1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class display_usp_list extends AppCompatActivity  implements selectUspAdapter.customButtonListener{
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

        try {
            jsonObject=new JSONObject(json_string);
            jsonArray=jsonObject.getJSONArray("server_response");
            String name,email,password,address,service,pincode,experience,mobile;

            int count=0;
            while(count<jsonArray.length())
            {
                JSONObject jo=jsonArray.getJSONObject(count);
                name=jo.getString("name");
                email=jo.getString("email");
                address=jo.getString("address");
                service=jo.getString("service");
                pincode=jo.getString("pincode");
                mobile=jo.getString("mobile");

                selectUsp usp=new selectUsp(name,email,address,service,pincode,mobile);
                uspAdapter.add(usp);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder= new AlertDialog.Builder(display_usp_list.this);

                        builder.setTitle("Confirm");
                builder.setMessage(" Are you sure you want to Book this service provider? SMS charges may apply...!!");
                builder.setCancelable(true);
                builder .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        selectUsp usp= (selectUsp) parent.getItemAtPosition(position);

                       String s= usp.getName();
                        String no=usp.getMobile();

//                       /* try
//                        {
//
//                            String SENT      = "SMS_SENT";
//
//
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
        });

    }

    @Override
    public void onButtonClickListner(int position,selectUsp usp) {
        Toast.makeText(display_usp_list.this, "Button click " + usp.getEmail(),
                Toast.LENGTH_SHORT).show();
    }
}

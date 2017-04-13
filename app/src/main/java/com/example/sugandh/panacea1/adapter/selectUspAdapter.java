package com.example.sugandh.panacea1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.sugandh.panacea1.R;
import com.example.sugandh.panacea1.selectUsp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sugandh on 3/21/2017.
 */

public class selectUspAdapter extends ArrayAdapter {
    List list=new ArrayList();
    public selectUspAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(selectUsp object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row;
        row=convertView;
        selectUspHolder UspHolder;
        if(row==null)
        {
            LayoutInflater layoutInflater= (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.usp_row_layout,parent,false);
            UspHolder=new selectUspHolder();
            UspHolder.tv_name= (TextView) row.findViewById(R.id.textView3);
            UspHolder.tv_email= (TextView) row.findViewById(R.id.textView4);
            UspHolder.tv_address= (TextView) row.findViewById(R.id.textView6);
            UspHolder.button = (Button) row.findViewById(R.id.bt_selectUsp);
            row.setTag(UspHolder);
        }
        else
        {
            UspHolder= (selectUspHolder) row.getTag();
        }
        selectUsp Usp= (selectUsp) this.getItem(position);
        UspHolder.tv_name.setText(Usp.getName());
        UspHolder.tv_email.setText(Usp.getEmail());
        UspHolder.tv_address.setText(Usp.getAddress());


        final selectUsp temp = (selectUsp) getItem(position);
        UspHolder.button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (customListner != null) {
                    customListner.onButtonClickListner(position,temp);
                }

            }
        });
        return row;
    }
    static  class selectUspHolder
    {
        TextView tv_name,tv_email,tv_address;
        Button button;
    }
    customButtonListener customListner;

    public interface customButtonListener {
        public void onButtonClickListner(int position,selectUsp usp);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }
}

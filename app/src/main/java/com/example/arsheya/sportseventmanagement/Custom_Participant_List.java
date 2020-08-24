package com.example.arsheya.sportseventmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import com.bumptech.glide.Glide;

import java.net.URL;
import java.util.ArrayList;

public class Custom_Participant_List extends BaseAdapter
    {
        Context context;
        ///chng  arrlist
        ArrayList<Pojo_Participant_List> cm;
        //Delegate delegate;
////chnge
    public Custom_Participant_List(Context context, ArrayList<Pojo_Participant_List> cm) {
        this.context = context;
        this.cm = cm;
        //this.delegate = delegate;
    }

        @Override
        public int getCount() {
        return cm.size();
    }

        @Override
        public Object getItem(int position) {
        return null;
    }

        @Override
        public long getItemId(int position) {
        return 0;
    }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            try {


                final viewholder viewholder;

                if(convertView==null) {
                    viewholder= new viewholder();
                    LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    ///xml file name
                    convertView = layoutInflater.inflate(R.layout.row_participant_list, null);
                    //findview
                    viewholder.tvplayername = (TextView) convertView.findViewById(R.id.tvplayername);
                    viewholder.tveventname = convertView.findViewById(R.id.tveventname);
                    convertView.setTag(position);

                    //  viewholder.btn.setTag(position);
                }
                else
                {
                    viewholder=(viewholder)convertView.getTag();
                }
                viewholder.imgname = (ImageView) convertView.findViewById(R.id.img);
/////set value
                viewholder.tvplayername.setText(cm.get(position).getFirstName());
                viewholder.tveventname.setText(cm.get(position).getEvent_name());

                Commonclass co = new Commonclass();
                URL url = co.getUrl(cm.get(position).getImage());
                Glide.with(context)
                        .load(url)
                        .into(viewholder.imgname);
                //  viewholder.imgname.setAlpha(50);

            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return convertView;
        }
        public  class viewholder{
            TextView tvplayername, tveventname;
            ImageView imgname;
        }

    }


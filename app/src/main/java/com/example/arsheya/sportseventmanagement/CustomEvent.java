package com.example.arsheya.sportseventmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URL;
import java.util.ArrayList;

public class CustomEvent extends BaseAdapter
{
    Context context;
    ///chng  arrlist
    ArrayList<Pojo_Event> cm;
    //Delegate delegate;
////chnge
    public CustomEvent(Context context, ArrayList<Pojo_Event> cm) {
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
                viewholder=new viewholder();
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                ///xml file name
                convertView = layoutInflater.inflate(R.layout.row_event, null);
                //findview
                viewholder.tvName = (TextView) convertView.findViewById(R.id.tvname);
                viewholder.tvcategory = convertView.findViewById(R.id.tvcategory);
                convertView.setTag(position);

              //  viewholder.btn.setTag(position);
            }
            else
            {
                viewholder=(viewholder)convertView.getTag();
            }
              viewholder.imgname = (ImageView) convertView.findViewById(R.id.img);
/////set value
            viewholder.tvName.setText(cm.get(position).getEvent_name());
            viewholder.tvcategory.setText(cm.get(position).getCategory());
            
            Commonclass co = new Commonclass();
            URL url = co.getUrl(cm.get(position).getSetimage());
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
        TextView tvName, tvcategory;
        ImageView imgname;


    }
}
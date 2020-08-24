package com.example.arsheya.sportseventmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.net.URL;
import java.util.ArrayList;

public class CustomDistribute extends BaseAdapter
{
    Context context;
    ///chng  arrlist
    ArrayList<Pojo_Distributor> cm;
    Deleget deleget;
    //Delegate delegate;
////chnge
    public CustomDistribute(Context context, ArrayList<Pojo_Distributor> cm,Deleget deleget) {
        this.context = context;
        this.cm = cm;
        this.deleget = deleget;
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
                convertView = layoutInflater.inflate(R.layout.row_distributor, null);
                //findview
                viewholder.tvName = (TextView) convertView.findViewById(R.id.name);
                viewholder.tvlname = convertView.findViewById(R.id.lname);
                viewholder.tvemail = convertView.findViewById(R.id.email);
                viewholder.tvno = convertView.findViewById(R.id.no);
                viewholder.btnchat = convertView.findViewById(R.id.btnchat);

                //viewholder.tvcategory = convertView.findViewById(R.id.tvcategory);
                convertView.setTag(position);

                 viewholder.btnchat.setTag(position);
            }
            else
            {
                viewholder=(viewholder)convertView.getTag();
            }
            viewholder.imgname = (ImageView) convertView.findViewById(R.id.img);
/////set value
            viewholder.tvName.setText(cm.get(position).getFname());
            viewholder.tvno.setText(cm.get(position).getPno());
            viewholder.tvemail.setText(cm.get(position).getEmail());
            viewholder.tvlname.setText(cm.get(position).getLname());
           // viewholder.tvcategory.setText(cm.get(position).getCategory());

            Commonclass co = new Commonclass();
            URL url = co.getUrl(cm.get(position).getImage());
            Glide.with(context)
                    .load(url)
                    .into(viewholder.imgname);



            viewholder.btnchat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=(Integer)viewholder.btnchat.getTag();
                    Toast.makeText(context, "pos::" + pos, Toast.LENGTH_SHORT).show();


                    deleget.onclicke(cm.get(pos));
                }
            });
            //  viewholder.imgname.setAlpha(50);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return convertView;
    }
    public  class viewholder{
        TextView tvName, tvlname,tvemail,tvno;
        ImageView imgname;
Button btnchat;

    }

}

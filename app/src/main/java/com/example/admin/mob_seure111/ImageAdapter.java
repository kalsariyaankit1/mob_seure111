package com.example.admin.mob_seure111;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private final String[] web;
    private final int[] Imageid;
    LayoutInflater inflater;

    public ImageAdapter(Context c,String[] web,int[] Imageid ) {
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return web.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View rootview = inflater.inflate(R.layout.activity_grid_single,null);

        TextView textView = (TextView) rootview.findViewById(R.id.grid_text);
        ImageView imageView = (ImageView)rootview.findViewById(R.id.grid_image);
        textView.setText(web[position]);
        imageView.setImageResource(Imageid[position]);


        return rootview;
    }

}


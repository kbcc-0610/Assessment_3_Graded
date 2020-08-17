package com.example.assignment_3.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.assignment_3.Model.Box;
import com.example.assignment_3.R;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private Box[] boxes;
    private static LayoutInflater inflater = null;
    private int size;

    public ImageAdapter(Context context, Box[] boxes, int size) {
        this.context = context;
        this.size = size;
        this.boxes = boxes;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return boxes.length;
    }

    @Override
    public Object getItem(int position) {
        return boxes[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        itemView = (itemView == null) ? inflater.inflate(R.layout.grid_item, null) : itemView;
        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_gridItem);
        Box selectedBox = boxes[position];
        imageView.setImageResource(R.color.colorGrey);
        imageView.setTag("Grey");
        imageView.setLayoutParams(new ConstraintLayout.LayoutParams(size, size));
        return itemView;
    }
}

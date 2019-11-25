package com.nslb.twipee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter{
    Button btn_board_comment;

    private ArrayList<CustomDTO> listCustom = new ArrayList<>();

    @Override
    public int getCount() {
        return listCustom.size();
    }

    @Override
    public Object getItem(int i) {
        return listCustom.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.triptalk_list, null, false);

            holder = new CustomViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            holder.textTitle = (TextView) convertView.findViewById(R.id.text_title);
            holder.textContent_0 = (TextView) convertView.findViewById(R.id.text_content_0);
            holder.textContent_1=(TextView)convertView.findViewById(R.id.text_content_1);

            convertView.setTag(holder);
        } else {
            holder = (CustomViewHolder) convertView.getTag();
        }

        CustomDTO dto = listCustom.get(position);

        holder.imageView.setImageResource(dto.getResId());
        holder.textTitle.setText(dto.getTitle());
        holder.textContent_0.setText(dto.getContent());
        holder.textContent_1.setText(dto.getContent());

        return convertView;
    }

    public void addItem(CustomDTO dto) {
        listCustom.add(dto);
    }


    class CustomViewHolder {
        ImageView imageView;
        TextView textTitle;
        TextView textContent_0;
        TextView textContent_1;
    }
}

package com.nslb.twipee.User;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nslb.twipee.R;

import java.util.ArrayList;

public class CourseAdapter extends BaseAdapter{
    private ArrayList<CourseDTO>listCourse=new ArrayList<>();
    @Override
    public int getCount() {
        return listCourse.size();
    }

    @Override
    public Object getItem(int i) {
        return listCourse.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position , View convertView , ViewGroup parent) {
        CourseViewHolder holder;
        if (convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_course,null,false);
            holder=new CourseViewHolder();
            holder.imagePosition=(ImageView)convertView.findViewById(R.id.tourist_image);
            holder.textPosition=(TextView)convertView.findViewById(R.id.cou_position);
            holder.textPositionDetail=(TextView)convertView.findViewById(R.id.cou_pos_detail);
            holder.textPhoneNumber=(TextView)convertView.findViewById(R.id.phone_number);
            holder.textTime=(TextView)convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        }else {
            holder=(CourseViewHolder)convertView.getTag();
        }
        CourseDTO dto=listCourse.get(position);
        holder.imagePosition.setImageResource(dto.getResld_positionimage());
        holder.textPosition.setText(dto.getCou_position());
        holder.textPositionDetail.setText(dto.getCou_pos_detail());
        holder.textPhoneNumber.setText(dto.getPhone_number());
        holder.textTime.setText(dto.getTime());
        return convertView;

    }
    public void addItem(CourseDTO dto){listCourse.add(dto);}
    class CourseViewHolder{
        ImageView imagePosition;
        TextView textPosition;
        TextView textPositionDetail;
        TextView textPhoneNumber;
        TextView textTime;
    }
}

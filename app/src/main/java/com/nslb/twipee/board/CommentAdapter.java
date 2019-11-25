package com.nslb.twipee.board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nslb.twipee.R;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter{
    private ArrayList<CommentDTO>listComment=new ArrayList<>();

    @Override
    public int getCount() {
        return listComment.size();
    }

    @Override
    public Object getItem(int i) {
        return listComment.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position , View convertView , ViewGroup parent) {
        CommentViewHolder holder;
        if (convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.triptalk_boardlist,null,false);

            holder=new CommentAdapter.CommentViewHolder();
            holder.imageViewUSer=(ImageView)convertView.findViewById(R.id.imageUser);
            holder.textUsername=(TextView)convertView.findViewById(R.id.username_board);
            holder.textTime=(TextView)convertView.findViewById(R.id.time);
            holder.textComment=(TextView)convertView.findViewById(R.id.board_text);

            convertView.setTag(holder);
        }else{
            holder=(CommentAdapter.CommentViewHolder)convertView.getTag();
        }
        CommentDTO dto=listComment.get(position);
        holder.imageViewUSer.setImageResource(dto.getImageUser());
        holder.textUsername.setText(dto.getUser_comment());
        holder.textTime.setText(dto.getTime());
        holder.textComment.setText(dto.getComment());

        return convertView;
    }

    public void addItem(CommentDTO dto){listComment.add(dto);}


    class CommentViewHolder{
    ImageView imageViewUSer;
    TextView textUsername;
    TextView textTime;
    TextView textComment;

}

}

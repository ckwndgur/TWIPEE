package com.nslb.twipee.board;

import android.media.Image;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nslb.twipee.R;

import java.util.ArrayList;

public class BoardAdapter extends BaseAdapter {
    private ArrayList<BoardDTO> listBoard = new ArrayList<>();

    @Override
    public int getCount() {
        return listBoard.size();
    }

    @Override
    public Object getItem(int i) {
        return listBoard.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position , View convertView , ViewGroup parent) {
        BoardViewHolder holder;
        if (convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.triptalk_boardlist,null,false);

            holder=new BoardViewHolder();
            holder.imageViewUSer=(ImageView)convertView.findViewById(R.id.imageUser);
            holder.textUsername=(TextView)convertView.findViewById(R.id.username_board);
            holder.textDay=(TextView)convertView.findViewById(R.id.day);
            holder.textContentBoard=(TextView)convertView.findViewById(R.id.board_text);

            holder.imageViewContent=(ImageView)convertView.findViewById(R.id.board_image);
            convertView.setTag(holder);
        }else{
            holder=(BoardViewHolder)convertView.getTag();
        }

        BoardDTO dto=listBoard.get(position);
        holder.imageViewUSer.setImageResource(dto.getResld_user_image());
        holder.textUsername.setText(dto.getUser_name());
        holder.textDay.setText(dto.getDay());
        holder.textContentBoard.setText(dto.getContent_board());
        //Glide.with(parent).load(dto.getResld_content_image()).into(holder.imageViewContent);

        String url = dto.getResld_content_image();
        Glide.with(parent).load(url).into(holder.imageViewContent);

        return convertView;
    }
    public void addItem(BoardDTO dto){listBoard.add(dto);}
    class BoardViewHolder{
        ImageView imageViewUSer;
        TextView textUsername;
        TextView textDay;
        TextView textContentBoard;
        ImageView imageViewContent;
    }

}

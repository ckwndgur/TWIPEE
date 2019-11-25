package com.nslb.twipee.triptalk;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nslb.twipee.R;

import java.util.ArrayList;
import java.util.List;


public class MyChatAdapter extends ArrayAdapter {

    private List<ChatMessage> messages = new ArrayList();
    TextView msgText_send = null;
    TextView msgText_received = null;

    public MyChatAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public void add(ChatMessage object){
        messages.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public ChatMessage getItem(int index) {
        return (ChatMessage) messages.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ChatMessage msg = (ChatMessage) messages.get(position);
         LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // inflator를 생성하여, chatting_message_send.xml을 읽어서 View객체로 생성한다.
        if(msg.getBChecked()){
            row = inflater.inflate(R.layout.chatting_message_received, parent, false);
            msgText_received = (TextView) row.findViewById(R.id.text_message_body2);
            msgText_received.setText(msg.getMessage());
            msgText_received.setTextColor(Color.parseColor("#000000"));
        }else {
            row = inflater.inflate(R.layout.chatting_message_send, parent, false);
            msgText_send = (TextView) row.findViewById(R.id.text_message_body1);
            msgText_send.setText(msg.getMessage());
            msgText_send.setTextColor(Color.parseColor("#000000"));
        }
        // Array List에 들어 있는 채팅 문자열을 읽어
        return row;
    }
}

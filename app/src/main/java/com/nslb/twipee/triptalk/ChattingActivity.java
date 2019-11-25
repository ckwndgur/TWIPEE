package com.nslb.twipee.triptalk;

import androidx.appcompat.app.AppCompatActivity;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import com.nslb.twipee.R;

public class ChattingActivity extends AppCompatActivity {

    private MyChatAdapter myChatAdapter;
    private Button sendButton;
    private EditText etMsg;
    private Switch userChangeBtn;
    private boolean bChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        myChatAdapter = new MyChatAdapter(this.getApplicationContext(), R.layout.chatting_message_send);
        final ListView listView = (ListView) findViewById(R.id.reyclerview_message_list);
        listView.setAdapter(myChatAdapter);
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        sendButton = (Button) findViewById(R.id.button_chatbox_send);
        etMsg = (EditText) findViewById(R.id.edittext_chatbox);
        userChangeBtn = (Switch) findViewById(R.id.switch3);

        sendButton.setEnabled(false);

        myChatAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(myChatAdapter.getCount() - 1);
            }
        });


        sendButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strMsg = (String) etMsg.getText().toString();
                myChatAdapter.add(new ChatMessage(strMsg, bChecked));
                etMsg.setText(null);
            }
        });

        etMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //입력 변화 있을 때
                if(etMsg.getText().length() > 0){
                    sendButton.setEnabled(true);
                }else{
                    sendButton.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        userChangeBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    bChecked = true;
                } else {
                    bChecked = false;
                }
            }
        });
    }
}

package com.nslb.twipee.near;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nslb.twipee.GUIParameter.HandlerParameter;
import com.nslb.twipee.R;
import java.util.ArrayList;


public class Near_Tourist extends AppCompatActivity {
    private ListView mListView;
    private TextView mTVTitle;
    private NearTOAdapter mAdapter;

    private ArrayList<TourInformation> mListTourInfo = new ArrayList<>();
    private HandlerParameter param = new HandlerParameter();
    private Handler mURIParsingHandler;

    private String mURI = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.near_tourist);
        mAdapter = new NearTOAdapter();
        mListView = (ListView)findViewById(R.id.list_tourist);
        mTVTitle = (TextView)findViewById(R.id.tv_title);

        if(getIntent().getExtras() != null) {
            Intent intent = getIntent();
            mURI = intent.getExtras().getString("URI");
            mTVTitle.setText(intent.getExtras().getString("ContentType"));
        }

        MessageEvent();
        startXMLParser();
    }

    private void MessageEvent(){
        mURIParsingHandler = new Handler(){
            public void handleMessage(Message msg){
                if(msg.arg1 == param.XMLTOURINFO){
                    mListTourInfo = (ArrayList<TourInformation>)msg.obj;
                    //imageURI.setBitmapImage(mListTourInfo);
                    setListviewData();
                    mListView.setAdapter(mAdapter);
                    mListTourInfo.clear();
                }
            }
        };
    }

    private void startXMLParser()
    {
        Thread thread = new Thread(new XMLParser(mURIParsingHandler, mURI));
        thread.start();
    }

    private void setListviewData(){

        for (int i = 0 ; i < mListTourInfo.size() ; i++){
            NearToDTO dto = new NearToDTO();
            TourInformation ti = mListTourInfo.get(i);
            ImageURI imageURI = new ImageURI(ti.elements[ti.FIRSTIMAGE]);
            Thread thread = new Thread(imageURI);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dto.setResld_tourist(imageURI.bitmapImage);
            dto.setNearposition(ti.elements[ti.TITLE]);
            dto.setNearposition_detail(ti.elements[ti.ADDR1]);
            dto.setDistance(ti.elements[ti.DIST]);
            mAdapter.addItem(dto);
        }
    }
}

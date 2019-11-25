package com.nslb.twipee.User;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nslb.twipee.R;

public class PostCostPlus extends AppCompatActivity implements View.OnClickListener {
    TextView tv_costPlus, tv_title;
    ImageView backArrow;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cost_plus);

        initView();

    }

    private void initView() {

        backArrow = (ImageView) findViewById(R.id.backArrow);
        tv_title = (TextView)findViewById(R.id.setTitle);
        tv_costPlus = (TextView)findViewById(R.id.tvNext);

        //뒤로가기
        backArrow.setOnClickListener(this);

        //Title 설정 : 비용 항목 추가
        tv_title.setText(getResources().getString(R.string.title_costPlus));

        //추가
        tv_costPlus.setText(getResources().getString(R.string.btn_Plus));
        tv_costPlus.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backArrow:
                finish();
                break;
            case R.id.tvNext:
                //TODO : 비용 항목 추가
                break;
            default:
                break;
        }
    }

}
package com.nslb.twipee.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nslb.twipee.R;
import com.nslb.twipee.board.BoardWrite;

public class PostCost extends AppCompatActivity implements View.OnClickListener {
    Button btn_postcost_plus;
    TextView tv_review_cost, tv_title;
    ImageView backArrow;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_cost);

        initView();

    }

    private void initView() {

        btn_postcost_plus=findViewById(R.id.btn_postcost_plus);
        backArrow = (ImageView) findViewById(R.id.backArrow);
        tv_title = (TextView)findViewById(R.id.setTitle);
        tv_review_cost = (TextView)findViewById(R.id.tvNext);

        //뒤로가기
        backArrow.setOnClickListener(this);

        //Title 설정 : 여행 비용
        tv_title.setText(getResources().getString(R.string.title_review_cost));

        //작성하기
        tv_review_cost.setText(getResources().getString(R.string.btn_check));
        tv_review_cost.setOnClickListener(this);

        btn_postcost_plus.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backArrow:
                finish();
                break;
            case R.id.tvNext:
                //TODO : 확인
                break;
            case R.id.btn_postcost_plus:
                Intent intent=new Intent(getApplicationContext(), PostCostPlus.class);
                startActivity(intent);
                break;
            default:
                break;

        }
    }
}

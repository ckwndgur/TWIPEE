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

public class PostWrite extends AppCompatActivity implements View.OnClickListener{
    Button btn_ps_review,travel_plan;
    TextView tv_title;
    ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_1);

        initView();

    }

    private void initView() {

        backArrow = (ImageView) findViewById(R.id.backArrow);
        tv_title = (TextView)findViewById(R.id.setTitle);
        btn_ps_review=findViewById(R.id.btn_ps_review);
        travel_plan=findViewById(R.id.travel_plan);

        //뒤로가기
        backArrow.setOnClickListener(this);

        //Title 설정 : 게시물 작성
        tv_title.setText(getResources().getString(R.string.title_post_write));

        btn_ps_review.setOnClickListener(this);
        travel_plan.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backArrow:
                finish();
                break;
            case R.id.btn_ps_review:
                Intent intent=new Intent(getApplicationContext(), PostReview.class);
                startActivity(intent);
                break;
            case R.id.travel_plan:
                Intent intent1=new Intent(getApplicationContext(), PlanWrite.class);
                startActivity(intent1);
                break;
            default:
                break;
        }

    }
}

package com.nslb.twipee.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nslb.twipee.R;

public class PlanCost extends AppCompatActivity implements View.OnClickListener {
    Button btn_plancost_puls;
    TextView tv_plancost_puls, tv_title;
    ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.plan_cost);

    initView();

}

    private void initView() {

        backArrow = (ImageView) findViewById(R.id.backArrow);
        tv_title = (TextView) findViewById(R.id.setTitle);
        tv_plancost_puls = (TextView) findViewById(R.id.tvNext);
        btn_plancost_puls=findViewById(R.id.btn_plancost_puls);

        //뒤로가기
        backArrow.setOnClickListener(this);

        //Title 설정 : 여행 비용 계획
        tv_title.setText(getResources().getString(R.string.title_plancost_puls));

        //확인
        tv_plancost_puls.setText(getResources().getString(R.string.btn_check));
        tv_plancost_puls.setOnClickListener(this);

        btn_plancost_puls.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backArrow:
                finish();
                break;
            case R.id.tvNext:
                //TODO : 확인
                break;
            case R.id.btn_plancost_puls:
                Intent intent=new Intent(getApplicationContext(), PlanCostPlus.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}

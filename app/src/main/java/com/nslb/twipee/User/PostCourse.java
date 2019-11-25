package com.nslb.twipee.User;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nslb.twipee.R;
import com.nslb.twipee.board.BoardWrite;

public class PostCourse extends AppCompatActivity implements View.OnClickListener {
    private CourseAdapter adapter;
    private ListView listView;
    TextView tv_course, tv_title;
    ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_course);

        initView();
    }

    private void initView() {

        backArrow = (ImageView) findViewById(R.id.backArrow);
        tv_title = (TextView)findViewById(R.id.setTitle);
        tv_course = (TextView)findViewById(R.id.tvNext);

        //뒤로가기
        backArrow.setOnClickListener(this);

        //Title 설정 : 여행 코스 추가
        tv_title.setText(getResources().getString(R.string.title_course));

        //작성하기
        tv_course.setText(getResources().getString(R.string.btn_check));
        tv_course.setOnClickListener(this);


        adapter = new CourseAdapter();
        listView = (ListView) findViewById(R.id.list_course);
        setData();
        listView.setAdapter(adapter);

    }

    private void setData(){
        TypedArray ResldPositionimage=getResources().obtainTypedArray(R.array.resld_positionimage);
        String[] cou_position=getResources().getStringArray(R.array.cou_position);
        String[] cou_pos_detail=getResources().getStringArray(R.array.cou_pos_detail);
        String[] phone_number=getResources().getStringArray(R.array.phone_number);
        String[] time=getResources().getStringArray(R.array.time);
        for (int i=0;i<((TypedArray)ResldPositionimage).length();i++){
            CourseDTO dto=new CourseDTO();
            dto.setResld_positionimage(ResldPositionimage.getResourceId(i,0));
            dto.setCou_position(cou_position[i]);
            dto.setCou_pos_detail(cou_pos_detail[i]);
            dto.setPhone_number(phone_number[i]);
            dto.setTime(time[i]);
            adapter.addItem(dto);
        }

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
            default:
                break;
        }
    }
}

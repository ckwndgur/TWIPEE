package com.nslb.twipee.board;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.nslb.twipee.R;
import com.nslb.twipee.User.OtherUserInfo;

public class BoardComment extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private CommentAdapter adapter;
    ImageView otherimage;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_comment);
        adapter=new CommentAdapter();
        listView=findViewById(R.id.list_comment);
        setData();
        listView.setAdapter(adapter);

    }
    private void setData(){
        TypedArray UserImage=getResources().obtainTypedArray(R.array.imageUser);
        String[] user_comment=getResources().getStringArray(R.array.username_comment);
        String[] time=getResources().getStringArray(R.array.time);
        String[] comment=getResources().getStringArray(R.array.comment);
        for (int i=0; i<((TypedArray)UserImage).length();i++){
            CommentDTO dto=new CommentDTO();
            dto.setImageUser(UserImage.getResourceId(i,0));
            dto.setUser_comment(user_comment[i]);
            dto.setTime(time[i]);
            dto.setComment(comment[i]);
            adapter.addItem(dto);

        }

    }

    @Override
    public void onClick(View v) {  switch (v.getId()){
        case R.id.otherimage:
            Intent intent=new Intent(getApplicationContext(), OtherUserInfo.class);
            startActivity(intent);
            break;
        default:
            break;
    }
    }

}

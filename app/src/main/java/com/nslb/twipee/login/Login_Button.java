package com.nslb.twipee.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.nslb.twipee.MainActivity;
import com.nslb.twipee.R;

import java.util.ArrayList;


public class Login_Button extends AppCompatActivity {

    private Intent intent;
    private ArrayList<String> mKeywordStrArray;
    private String [] mUserInfos;
    private Button btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_button);


        if(getIntent().getExtras() != null) {
            intent = getIntent();
            mUserInfos = new String[4];
            mUserInfos = intent.getExtras().getStringArray("LoginInfo");
        }
        mKeywordStrArray = new ArrayList<>();
    }




    public void onKeywordBtnClick(View view)
    {
        if (view.getId() == R.id.done) {
            intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("KeywordStrArray", mKeywordStrArray);
            intent.putExtra("LoginInfo", mUserInfos);
            startActivity(intent);
            finish();
        }
        else {
            Button btn = (Button) findViewById(view.getId());
            mKeywordStrArray.add(btn.getText().toString());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
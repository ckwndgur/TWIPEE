package com.nslb.twipee.User;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nslb.twipee.R;
import com.nslb.twipee.Utils.Permissions;
import com.nslb.twipee.ui.MyEventListener;
import com.nslb.twipee.ui.main.PhotoAdapter;

public class PostGetImage extends AppCompatActivity {

    private TabLayout tabs;
    private ViewPager viewPager;
    private PhotoAdapter photoAdapter;
    private static final int ACTIVITY_NUM = 2;
    private static final int VERIFY_PERMISSIONS_REQUEST = 1;
    public static final int FMGALLERY = 0;
    public static final int FMPHOTO = 1;

    private Handler mHandler;
    private MyEventListener myEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_get_image);

        if(checkPermissionsArray(Permissions.PERMISSIONS)){
            tabs = findViewById(R.id.tab_trevelPhoto);
            viewPager = findViewById(R.id.vp_trevelPhoto);

            mHandler = new Handler();
            photoAdapter = new PhotoAdapter(this.getSupportFragmentManager(),2, this);

            tabs.addTab(tabs.newTab().setText("Gallery"));
            tabs.addTab(tabs.newTab().setText("Camera"));
            tabs.setTabGravity(tabs.GRAVITY_FILL);
            viewPager.setAdapter(photoAdapter);
            tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
            tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    myEventListener.onTabSelected(tab);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }else{
            verifyPermissions(Permissions.PERMISSIONS);
        }
    }

    public void onTabSelected(MyEventListener listener){
        this.myEventListener = listener;
    }

    public boolean checkPermissionsArray(String[] permissions){

        for(int i = 0; i< permissions.length; i++){
            String check = permissions[i];
            if(!checkPermissions(check)){
                return false;
            }
        }
        return true;
    }

    public void verifyPermissions(String[] permissions){
        ActivityCompat.requestPermissions(
                PostGetImage.this,
                permissions,
                VERIFY_PERMISSIONS_REQUEST
        );
    }

    public boolean checkPermissions(String permission){

        int permissionRequest = ActivityCompat.checkSelfPermission(PostGetImage.this, permission);

        if(permissionRequest != PackageManager.PERMISSION_GRANTED){
            return false;
        }
        else{
            return true;
        }
    }

}

package com.nslb.twipee;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nslb.twipee.User.MyStepService;
import com.nslb.twipee.User.PostWrite;
import com.nslb.twipee.User.SetUp;
import com.nslb.twipee.ui.main.CircularImageView;
import com.nslb.twipee.ui.main.PostAapter;
import com.nslb.twipee.ui.main.UserMap;
import com.nslb.twipee.ui.main.UserPost;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class UserInfoView extends Fragment{
    private ViewPager viewPager;
    public UserPost mUserPost = null;
    public UserMap mUserMAp = null;
    public ImageView imageView;
    Button ps_write;
    private SensorManager sensorManager;
    private Sensor stepDetectorSensor;
    TextView tvStepDetector;
    private int mStepDetector;
    private MyStepService myStepService;
    ToggleButton btn_toggle1;
    private MyStepService mService;
    Intent MyStepService;
    BroadcastReceiver receiver;
    boolean flag=true;
    Toast toast;
    String serviceData;
    ImageButton btn_setup;

    @Override
    public View onCreateView(final LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userinfoview , container , false);


//        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
//        stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
//        if (stepDetectorSensor == null) {
//            Toast.makeText(getActivity() , "No Step Detect Sensor" , Toast.LENGTH_SHORT).show();
//        }

        TabLayout tabs = view.findViewById(R.id.tabs_userinfo);
        tabs.addTab(tabs.newTab().setIcon(R.drawable.ic_apps_black_24dp));
        tabs.addTab(tabs.newTab().setIcon(R.drawable.ic_location_on2_black_24dp));
        tabs.setTabGravity(tabs.GRAVITY_FILL);
        final ViewPager viewPager = view.findViewById(R.id.userview);
        final PostAapter postAapter = new PostAapter(getActivity().getSupportFragmentManager() , 2);
        viewPager.setAdapter(postAapter);

        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        ImageView imageView = (ImageView) view.findViewById(R.id.userimage);
        GradientDrawable drawable =
                (GradientDrawable) getContext().getDrawable(R.drawable.image_circle);
        imageView.setBackground(new ShapeDrawable(new OvalShape()));
        if (Build.VERSION.SDK_INT > 21) {
            imageView.setClipToOutline(true);

        }
        ps_write = (Button) view.findViewById(R.id.ps_write);
        ps_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity() , PostWrite.class);
                startActivity(intent);
            }

        });
        btn_setup=(ImageButton)view.findViewById(R.id.btn_setup);
        btn_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SetUp.class);
                startActivity(intent);
            }
        });
        MyStepService=new Intent(getActivity(),MyStepService.class);
        receiver=new MyMainLocalReceiver();

        tvStepDetector = (TextView) view.findViewById(R.id.tvStepDetector);

        btn_toggle1=(ToggleButton)view.findViewById(R.id.btn_toggle1);
        btn_toggle1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton , boolean flag) {
                if(flag){
                    btn_toggle1.setText("중지");
                                        try{
                        getActivity().unregisterReceiver(receiver);
                        getActivity().stopService(MyStepService);
                        Toast.makeText(getActivity(),"여행을 다녀왔습니다",Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }else {
                    btn_toggle1.setText("가자");
                                        try{
                        IntentFilter mainFilter=new IntentFilter(
                                "com.nslb.twipee.User.MyStepService"
                        );

                        getActivity().registerReceiver(receiver,mainFilter);
                        getActivity().startService(MyStepService);
                        Toast.makeText(getActivity(),"여행을 시작합니다",Toast.LENGTH_SHORT).show();

                    }catch (Exception e){
                        Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
        return view;
    }
    public SensorEventListener mySensorListener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            tvStepDetector.setText(Float.toString(event.values[0]));
            Log.i("TAG",Float.toString(event.values[0]));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor , int accuracy) {

        }
    };


    class MyMainLocalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context , Intent intent) {
            int a =intent.getIntExtra("serviceData", -1);
            serviceData = String.valueOf(a);
            tvStepDetector.setText(serviceData);
            Toast.makeText(getActivity(),"Walking",Toast.LENGTH_SHORT).show();
        }






//여기서부터 서비스와 액티비티 연결
    }
//    private ServiceConnection mConnection=new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName className , IBinder service) {
//            MyStepService.MyStepServiceBinder binder=(MyStepService.MyStepServiceBinder)service;
//            mService= (com.nslb.twipee.User.MyStepService) binder.getService();
//            mService.registerCallback(mCallback);
//        }
//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//            mService=null;
//
//        }
//    };
//    private MyStepService.ICallback mCallback=new MyStepService.ICallback(){
//        public void recvData(){
//
//        }
//    };
//    public void startServiceMethod(View v){
//        Intent Service=new Intent(getActivity(),MyStepService.class);
//        bindService(Service,mConnection,Context.BIND_AUTO_CREATE);
//    }
//    mService.myStepServiceFunc();
//기존에 있었던 것,
//    @Override
//    public void onResume() {
//        super.onResume();
//        sensorManager.registerListener(this, stepDetectorSensor, SensorManager.SENSOR_DELAY_UI);
//    }
//    @Override
//    public void onPause() {
//        super.onPause();
//        sensorManager.unregisterListener(this);
//    }
//    @Override
//    public void onSensorChanged(SensorEvent event) {
//        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
//            if(event.values[0] == 1.0f) {
//                mStepDetector++;
//                tvStepDetector.setText(String.valueOf(mStepDetector));
//            }
//        }
//    }
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//    }


}
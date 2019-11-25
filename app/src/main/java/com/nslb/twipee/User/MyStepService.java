package com.nslb.twipee.User;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nslb.twipee.R;
import com.nslb.twipee.UserInfoView;

public class MyStepService extends Service implements SensorEventListener {
    int count = values.Step;
    private long lastTime;
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;
    private float x, y, z;
    private static final int SHAKE_THRESHOLD = 800;
//    private static final int DATA_X = SensorManager.DATA_X;
////    private static final int DATA_Y = SensorManager.DATA_Y;
////    private static final int DATA_Z = SensorManager.DATA_Z;
    private SensorManager sensorManager;
    private Sensor accelerormeterSensor;



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



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    //추가해본것(액티비티랑 서비스 연결=실패
//    public class MyStepServiceBinder extends Binder {
//        MyStepService getService() {
//            return MyStepService.this;
//        }
//    }
//
//    private final IBinder mBinder = new MyStepServiceBinder();
//    public interface ICallback {
//        public void recvData();
//    }
//
//    private ICallback mCallback;
//
//    public void registerCallback(ICallback cb) {
//        mCallback = cb;
//    }
//
//    public void myStepServiceFunc() {
//
//    }
//    mCallback.recvData();

    @Override
    public void onCreate(){
        super.onCreate();
        Log.i("onCreate","IN");
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        accelerormeterSensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    @Override
    public int onStartCommand(Intent intent,int flags,int startld) {
        super.onStartCommand(intent , flags, startld);
        Log.i("onSTartCommand" , "IN");
        if (accelerormeterSensor != null)
            sensorManager.registerListener(this , accelerormeterSensor , sensorManager.SENSOR_DELAY_GAME);
        return super.onStartCommand(intent,flags,startld);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i("onDestroy","IN");
        if (sensorManager !=null)
            sensorManager.unregisterListener(this);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor , int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            long currentTime=System.currentTimeMillis();
            long gabOfTime=(currentTime-lastTime);

            if(gabOfTime>100){
                lastTime=currentTime;
                x=event.values[0];
                y=event.values[1];
                z=event.values[2];
                speed=Math.abs(x=y+z-lastX-lastY-lastZ)/gabOfTime*10000;
                if (speed>SHAKE_THRESHOLD){
                    Log.e("Step","SHAKE");
                    Intent myFilteredResponse=new Intent("com.nslb.twipee.User.MyStepService");
                    values.Step=count++;
                    int tvStepDetector = values.Step/2;
                    myFilteredResponse.putExtra("serviceData",tvStepDetector);
                    sendBroadcast(myFilteredResponse);
                }
                lastX=event.values[0];
                lastY=event.values[1];
                lastZ=event.values[2];
            }
        }

    }


}


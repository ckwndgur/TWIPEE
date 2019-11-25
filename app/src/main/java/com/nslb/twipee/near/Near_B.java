package com.nslb.twipee.near;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.nslb.twipee.R;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Near_B extends AppCompatActivity {

    public double latitude; //현위치위도
    public double longitude; //현위치경도
    private ListView listView;
    public TextView n_address;

    public double latitudeX; //검색위도
    public double longitudeY;//검색경도
    public String name;//검색이름
    public String address;//현위치

    private ArrayList<String> mArrayMarkerID;
    private static int mMarkerID;
    public TMapView tmapview;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.near_b);

        mArrayMarkerID = new ArrayList<>();
        mMarkerID = 0;

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.map_view);
        n_address = (TextView) findViewById(R.id.n_address);
        tmapview = new TMapView(this);

        tmapview.setSKTMapApiKey("f14d574a-63eb-409b-8a59-8f895318bcdb");
//        tmapview.setCompassMode(true);
        tmapview.setZoomLevel(15);
        tmapview.setMapType(TMapView.MAPTYPE_STANDARD);
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);
        tmapview.setTrackingMode(true);
        tmapview.setSightVisible(true);
        linearLayout.addView(tmapview);

        getCurrentPosition();
        tmapview.setCenterPoint(longitude, latitude); //->현재위치 = centerpoint
        tmapview.setLocationPoint(longitude, latitude);

        makegeocoder();

        if(address != null) {
            findAllPoi(address + "화장실"); //-> 화장실 검색
        }

    }

    public void getCurrentPosition() {
        final LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        } else {
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000,
                    1,
                    gpsLocationListener);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    1000,
                    1,
                    gpsLocationListener);
        }
    }

    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

    public void findAllPoi(String strData) {

        TMapData tmapdata = new TMapData();
        tmapdata.findAllPOI(strData, new TMapData.FindAllPOIListenerCallback() {
            @Override
            public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {
                for (int i = 0; i < poiItem.size(); i++) {
                    TMapPOIItem item = poiItem.get(i);
                    name = item.getPOIName();
                    latitudeX = item.getPOIPoint().getLatitude();
                    longitudeY = item.getPOIPoint().getLongitude();

                    showmarker(poiItem);
                }
            }
        });
    }

    public void showmarker(ArrayList<TMapPOIItem> poiItem) {

        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.i_location);

        for (int i = 0; i < poiItem.size(); i++) {

            TMapMarkerItem markerItem1 = new TMapMarkerItem();
            markerItem1.setIcon(bitmap);
            markerItem1.setTMapPoint(poiItem.get(i).getPOIPoint());
            tmapview.addMarkerItem("markerItem" + i, markerItem1);
            String strID = String.format("pmarker%d", mMarkerID++);
            mArrayMarkerID.add(strID);

        }
    }
    //
    public void makegeocoder(){
        final Geocoder geocoder = new Geocoder(this);
        List<Address> list = null;
        try
        {
            list = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    5);
        } catch(IOException e)
        {
            e.printStackTrace();
            Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
        }
        if(list !=null)
        {
            if (list.size() == 0) {
                n_address.setText("해당되는 주소 정보는 없습니다");
            } else {
                n_address.setText(list.get(0).toString());
                String cut[] = list.get(0).toString().split(" ");
                for (int i = 0; i < cut.length; i++) {
                    System.out.println("cut[" + i + "] : " + cut[i]);
                }
                n_address.setText(cut[2] + " ");
                address = n_address.getText().toString();
            }
        }
    }
//
}

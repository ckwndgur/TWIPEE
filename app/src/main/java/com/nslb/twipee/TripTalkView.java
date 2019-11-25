package com.nslb.twipee;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.nslb.twipee.triptalk.ChattingActivity;
import com.nslb.twipee.ui.main.TripTalkUserView;

import java.io.IOException;
import java.util.List;

public class TripTalkView extends Fragment {
    private CustomAdapter adapter;
    private ListView listView;
    private Context mContext;
    SharedPreferences pref;
    ToggleButton btn_choice;
    public double latitude;
    public double longitude;
    public double altitude;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container ,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_triptalkview , container , false);

        final TextView n_address = (TextView) view.findViewById(R.id.n_address);

        /*final LocationManager lm = (android.location.LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        }
        else{
            Location location = lm.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER);
            String provider = location.getProvider();
            longitude = location.getLongitude();
            latitude = location.getLatitude();


            lm.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER,
                    1000,
                    1,
                    gpsLocationListener);
            lm.requestLocationUpdates(android.location.LocationManager.NETWORK_PROVIDER,
                    1000,
                    1,
                    gpsLocationListener);
        }

        final Geocoder geocoder = new Geocoder(getContext());

        List<Address> list = null;
        try {
            list = geocoder.getFromLocation(
                    latitude, // 위도
                    longitude, // 경도
                    5); // 얻어올 값의 개수
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
        }
        if (list != null) {
            if (list.size()==0) {
                n_address.setText("해당되는 주소 정보는 없습니다");
            } else {
                n_address.setText(list.get(0).toString());
                String cut[] = list.get(0).toString().split(" ");
                for(int i=0; i<cut.length; i++){
                    System.out.println("cut["+i+"] : " + cut[i]);
                }
                n_address.setText(cut[2] + " " );
            }
        }*/
        adapter = new CustomAdapter();
        listView = (ListView) view.findViewById(R.id.listview_trip);
        setData();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView , View v , int position , long Id) {
                switch (position)
                {
                    case 0:
                        Intent intent = new Intent(getActivity(), ChattingActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getActivity(), TripTalkUserView.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(getActivity(), TripTalkBoard.class);
                        startActivity(intent2);
                        break;
                    default:
                        break;
                }

            }
        });
        return view;
    }

    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            String provider = location.getProvider();
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            altitude = location.getAltitude();
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };


    private void setData() {
        TypedArray arrResId = getResources().obtainTypedArray(R.array.resId);
        String[] titles = getResources().getStringArray(R.array.title);
        String[] contents = getResources().getStringArray(R.array.content);
        for (int i = 0; i < ((TypedArray) arrResId).length(); i++) {
            CustomDTO dto = new CustomDTO();
            dto.setResId(arrResId.getResourceId(i , 0));
            dto.setTitle(titles[i]);
            dto.setContent(contents[i]);
            adapter.addItem(dto);
        }
    }
}
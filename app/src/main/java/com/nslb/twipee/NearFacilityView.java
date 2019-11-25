package com.nslb.twipee;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.nslb.twipee.near.NearAdapter;
import com.nslb.twipee.near.NearDTO;
import com.nslb.twipee.near.Near_B;
import com.nslb.twipee.near.Near_M;
import com.nslb.twipee.near.Near_P;
import com.nslb.twipee.near.Near_S;
import com.nslb.twipee.near.Near_Search;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class NearFacilityView extends Fragment implements View.OnClickListener {
    private NearAdapter adapter;
    private ListView listView;
    private Button mBtnNearSearch;
    public double latitude;
    public double longitude;
    public double altitude;

    public NearFacilityView() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nearfacilityview, container, false);

        //lm선언
        /*final LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        mBtnNearSearch = (Button)view.findViewById(R.id.btn_near);
        mBtnNearSearch.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        }
        else{
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
        }*/

        adapter=new NearAdapter();
        listView=(ListView)view.findViewById(R.id.listview_near);
        setData();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i)
                {
                    case 0:
                        Intent intent4 = new Intent(getActivity(), Near_B.class);
                        startActivity(intent4);
                        break;
                    case 1:
                        Intent intent5 = new Intent(getActivity(), Near_P.class);
                        startActivity(intent5);
                        break;
                    case 2:
                        Intent intent6 = new Intent(getActivity(), Near_S.class);
                        startActivity(intent6);
                        break;

                    case 3:
                        Intent intent7 = new Intent(getActivity(), Near_M.class);
                        startActivity(intent7);
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

    private void setData(){
        TypedArray Resldenear=getResources().obtainTypedArray(R.array.resId_near);
        String[] position=getResources().getStringArray(R.array.position);
        TypedArray Resldposition=getResources().obtainTypedArray(R.array.resld_position);

        for (int i=0; i<((TypedArray) Resldenear).length();i++){
            NearDTO dto=new NearDTO();
            dto.setResId_near(Resldenear.getResourceId(i,0));
            dto.setPosition(position[i]);
            dto.setResld_position(Resldposition.getResourceId(i,2));

            adapter.addItem(dto);

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_near:
                String strnlo = Double.toString(longitude);
                String strnla = Double.toString(latitude);

                Intent intent=new Intent(getActivity(), Near_Search.class);
                intent.putExtra("N_longitude", strnlo );
                intent.putExtra("N_latitude",strnla);

                startActivity(intent);
                break;
            default:
                break;
        }
    }
}

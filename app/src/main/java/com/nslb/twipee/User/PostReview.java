package com.nslb.twipee.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.nslb.twipee.R;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

public class PostReview extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {
    Button btn_cost;
    ImageButton btn_course_add, btn_TravelPhoto;
    TextView tv_review_write, tv_title;
    ImageView backArrow;
    ViewPager viewPager;
    SelectedImageAdapter selectedImageAdapter;
    PostSelectedImage postSelectedImage;
    Spinner spin_travelType, spin_itinerary, spin_travelMember, spin_transport;
    ArrayAdapter<String> arrAdap_trevelType, arrAdap_itinerary, arrAdap_travelMember, arrAdap_transport;

    // 임시로 Fragment로 넘길 Image Resource
    ArrayList<Integer> listImage;

    String[] travelTypeList = {"자유여행", "패키지", "여행타입"};
    String[] itineraryList = {"당일치기", "1박2일", "2박3일", "3박4일", "4박5일", "5박이상", "장기여행", "여행일정"};
    String[] travelMemberList = {"1인", "2인", "3인", "4인", "5~10인", "단체", "여행인원"};
    String[] transportList = {"자동차", "대중교통", "자전거", "버스대절", "항공", "선박", "이동수단"};

    public TextView n_address;
    public TMapView tmapview;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_2);

        initView();

    }

    private void initView() {

        backArrow = (ImageView) findViewById(R.id.backArrow);
        tv_title = (TextView)findViewById(R.id.setTitle);
        tv_review_write = (TextView)findViewById(R.id.tvNext);

        btn_cost = findViewById(R.id.btn_cost);
        btn_course_add = findViewById(R.id.btn_course_add);
        btn_TravelPhoto = findViewById(R.id.btn_addTrevelPhoto);
        viewPager = findViewById(R.id.vp_reviewPhoto);
        selectedImageAdapter = new SelectedImageAdapter(this.getSupportFragmentManager());

        //image viewpager 임시 데이터
        listImage = new ArrayList<>();
        listImage.add(R.drawable.toystory);
        listImage.add(R.drawable.bathroom);
        listImage.add(R.drawable.smoking);
        listImage.add(R.drawable.parking);

        spin_travelType = findViewById(R.id.spin_travelType);
        spin_itinerary = findViewById(R.id.spin_itinerary);
        spin_travelMember = findViewById(R.id.spin_travelMember);
        spin_transport = findViewById(R.id.spin_transport);
        n_address = (TextView) findViewById(R.id.n_address);

        //뒤로가기
        backArrow.setOnClickListener(this);

        //Title 설정 : 게시판
        tv_title.setText(getResources().getString(R.string.title_review_write));

        //작성하기
        tv_review_write.setText(getResources().getString(R.string.btn_write));
        tv_review_write.setOnClickListener(this);

        //티맵
        tmapview = new TMapView(this);
        linearLayout = (LinearLayout) findViewById(R.id.map_review);
        tmapview.setSKTMapApiKey("f14d574a-63eb-409b-8a59-8f895318bcdb");
        tmapview.setZoomLevel(15);
        tmapview.setMapType(TMapView.MAPTYPE_STANDARD);
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);
        tmapview.setTrackingMode(true);
        tmapview.setSightVisible(true);
        linearLayout.addView(tmapview);

        //ImageView pager
        viewPager.setAdapter(selectedImageAdapter);

        if(listImage.size()!=0)
        {
            viewPager.setVisibility(View.VISIBLE);
            for (int i = 0; i < listImage.size(); i++) {
                postSelectedImage = new PostSelectedImage();
                Bundle bundle = new Bundle();
                bundle.putInt("imgRes", listImage.get(i));
                postSelectedImage.setArguments(bundle);
                selectedImageAdapter.addImage(postSelectedImage);
            }
            selectedImageAdapter.notifyDataSetChanged();
        }
        else
        {
            viewPager.setVisibility(View.GONE);
        }

        //스피너
        arrAdap_trevelType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, travelTypeList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }
        };
        arrAdap_itinerary = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itineraryList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }
        };
        arrAdap_travelMember = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, travelMemberList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }
        };
        arrAdap_transport = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, transportList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }
        };


        //스피너 어뎁터 연결
        spin_travelType.setAdapter(arrAdap_trevelType);
        spin_travelType.setSelection(arrAdap_trevelType.getCount());
        spin_itinerary.setAdapter(arrAdap_itinerary);
        spin_itinerary.setSelection(arrAdap_itinerary.getCount());
        spin_travelMember.setAdapter(arrAdap_travelMember);
        spin_travelMember.setSelection(arrAdap_travelMember.getCount());
        spin_transport.setAdapter(arrAdap_transport);
        spin_transport.setSelection(arrAdap_transport.getCount());

        //클릭 리스너 연결
        btn_cost.setOnClickListener(this);
        btn_course_add.setOnClickListener(this);
        btn_TravelPhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backArrow:
                finish();
                break;
            case R.id.tvNext:
                //TODO : 게시물 올리기
                break;
            case R.id.btn_cost:
                Intent intent = new Intent(getApplicationContext(), PostCost.class);
                startActivity(intent);
                break;
            case R.id.btn_course_add:
                Intent intent1 = new Intent(getApplicationContext(), PostCourse.class);
                startActivity(intent1);
                break;
            case R.id.btn_addTrevelPhoto:
                Intent intent2 = new Intent(getApplicationContext(), PostGetImage.class);
                intent2.putExtra(getString(R.string.activity_name), getString(R.string.post_review));
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        switch (view.getId()) {
            case R.id.spin_travelType:
                //to_do
                break;
            case R.id.spin_itinerary:
                //to_do
                break;
            case R.id.spin_travelMember:
                //to_do
                break;
            case R.id.spin_transport:
                //to_do
                break;
            default:
                break;
        }
    }
}

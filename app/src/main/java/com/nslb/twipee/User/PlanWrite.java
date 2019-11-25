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

import com.nslb.twipee.R;
import com.nslb.twipee.board.BoardWrite;
import com.skt.Tmap.TMapView;

public class PlanWrite extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    Button btn_cost_plan;
    ImageButton btn_course_add_plan;
    TextView tv_plan_write, tv_title;
    ImageView backArrow;
    Spinner spin_travelType, spin_itinerary, spin_travelMember, spin_transport;
    ArrayAdapter<String> arrAdap_trevelType, arrAdap_itinerary, arrAdap_travelMember, arrAdap_transport;
    String[] travelTypeList = {"자유여행","패키지","여행타입"};
    String[] itineraryList = {"당일치기","1박2일","2박3일","3박4일","4박5일","5박이상","장기여행","여행일정"};
    String[] travelMemberList = {"1인","2인","3인","4인","5~10인","단체","여행인원"};
    String[] transportList = {"자동차","대중교통","자전거","버스대절","항공","선박","이동수단"};

    public TextView n_address;
    public TMapView tmapview;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_write);

        initView();

    }

    private void initView() {

        backArrow = (ImageView) findViewById(R.id.backArrow);
        tv_title = (TextView)findViewById(R.id.setTitle);
        tv_plan_write = (TextView)findViewById(R.id.tvNext);

        btn_cost_plan=findViewById(R.id.btn_cost_plan);
        btn_course_add_plan=findViewById(R.id.btn_course_add_plan);
        spin_travelType = findViewById(R.id.spin_travelType);
        spin_itinerary = findViewById(R.id.spin_itinerary);
        spin_travelMember = findViewById(R.id.spin_travelMember);
        spin_transport = findViewById(R.id.spin_transport);

        //뒤로가기
        backArrow.setOnClickListener(this);

        //Title 설정 : 게시판
        tv_title.setText(getResources().getString(R.string.title_plan_write));

        //작성하기
        tv_plan_write.setText(getResources().getString(R.string.btn_write));
        tv_plan_write.setOnClickListener(this);

        //티맵
        tmapview = new TMapView(this);
        linearLayout = (LinearLayout)findViewById(R.id.map_plan);


        //스피너
        arrAdap_trevelType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,travelTypeList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View v = super.getView(position,convertView,parent);

                return v;
            }

            @Override
            public int getCount(){
                return super.getCount()-1;
            }
        };
        arrAdap_itinerary = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,itineraryList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View v = super.getView(position,convertView,parent);

                return v;
            }

            @Override
            public int getCount(){
                return super.getCount()-1;
            }
        };
        arrAdap_travelMember = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,travelMemberList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View v = super.getView(position,convertView,parent);

                return v;
            }

            @Override
            public int getCount(){
                return super.getCount()-1;
            }
        };
        arrAdap_transport = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,transportList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View v = super.getView(position,convertView,parent);

                return v;
            }

            @Override
            public int getCount(){
                return super.getCount()-1;
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

        //티맵
        tmapview.setSKTMapApiKey("f14d574a-63eb-409b-8a59-8f895318bcdb");
        tmapview.setZoomLevel(15);
        tmapview.setMapType(TMapView.MAPTYPE_STANDARD);
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);
        tmapview.setTrackingMode(true);
        tmapview.setSightVisible(true);
        linearLayout.addView(tmapview);

        //클릭 리스너 연결
        btn_cost_plan.setOnClickListener(this);
        btn_course_add_plan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backArrow:
                finish();
                break;
            case R.id.tvNext:
                //TODO : 게시물 올리기
                break;
            case R.id.btn_cost_plan:
                Intent intent=new Intent(getApplicationContext(), PlanCost.class);
                startActivity(intent);
                break;
            case R.id.btn_course_add_plan:
                Intent intent1=new Intent(getApplicationContext(), PostCourse.class);
                startActivity(intent1);
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

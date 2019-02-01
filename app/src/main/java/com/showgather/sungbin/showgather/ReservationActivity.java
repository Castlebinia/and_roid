package com.showgather.sungbin.showgather;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.BaseSliderView;
import com.glide.slider.library.SliderTypes.DefaultSliderView;
import com.glide.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;


@SuppressWarnings("deprecation")
public class ReservationActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,ViewPagerEx.OnPageChangeListener {
    private SliderLayout mSlider;
    public double lat;
    public double lon;
    public String name;
    public String url;
    public String url1;
    public String id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        mSlider = findViewById(R.id.res_image_slider);
        ArrayList<String> listUrl = new ArrayList<>();

        Button reservate = (Button)findViewById(R.id.res_reservate);
        Button review = (Button)findViewById(R.id.res_review);
        Bundle extras = getIntent().getExtras();
        lat=extras.getDouble("res_lat");
        lon=extras.getDouble("res_lon");
        name=extras.getString("res_name");
        id=extras.getString("res_id");
        url=extras.getString("res_url");
        url1=extras.getString("res_url1");
        listUrl.add(url);
        listUrl.add(url1);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();
        for (int i = 0; i < listUrl.size(); i++) {
            DefaultSliderView sliderView = new DefaultSliderView(this);
            // if you want show image only / without description text use DefaultSliderView instead
            // initialize SliderLayout
            sliderView
                    .image(listUrl.get(i))
                    .setRequestOption(requestOptions)
                    .setBackgroundColor(Color.WHITE)
                    .setProgressBarVisible(true)
                    .setOnSliderClickListener(this);

            //add your extra information
            sliderView.bundle(new Bundle());
            mSlider.addSlider(sliderView);
        }
        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);

        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);
        mSlider.addOnPageChangeListener(this);
        TextView textView = (TextView)findViewById(R.id.res_res_name);
        textView.setText(name);
        final ViewPager viewPager;
        viewPager=findViewById(R.id.res_content);
        viewPager.setAdapter(new pageAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);

        TabLayout tabs=(TabLayout)findViewById(R.id.res_tabs);
        tabs.addTab(tabs.newTab().setText("정보"));
        tabs.addTab(tabs.newTab().setText("메뉴"));
        tabs.addTab(tabs.newTab().setText("리뷰"));
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReservationActivity.this,ReviewWriteActivity.class);
                intent.putExtra("res_name",name);
                intent.putExtra("res_id",id);
                finish();
                startActivity(intent);
            }
        });
    }

    private class pageAdapter extends FragmentStatePagerAdapter {
        public pageAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int i){
            switch (i){
                case 0:return new Res_info_fragment();
                case 1:return new Res_menu_fragment();
                case 2:return new Review_fragment();
                default:return null;
            }
        }
        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView baseSliderView) {

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}

package com.codeit.rajat.vidbuddy;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.codeit.rajat.utilities.MpagerAdapter;
import com.codeit.rajat.utilities.PreferenceManager;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager mPager;
    private int []layouts = {R.layout.intro_slide_1,R.layout.intro_slide_2,R.layout.intro_slide_3};
    private MpagerAdapter mpagerAdapter;
    private LinearLayout dotsLayout;
    private ImageView[] dots;
    private Button btnSkip;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(new PreferenceManager(getApplicationContext()).checkPreferences()){
            loadLogInActivity();
        }

        if(Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_welcome);

        mPager = (ViewPager)findViewById(R.id.view_pager);

        mpagerAdapter = new MpagerAdapter(layouts,getApplicationContext());

        mPager.setAdapter(mpagerAdapter);

        dotsLayout = (LinearLayout)findViewById(R.id.dots_layout);

        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnNext.getBackground().setAlpha(0);
        btnSkip.getBackground().setAlpha(0);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            loadNextSlide();
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLogInActivity();
                new PreferenceManager(getApplicationContext()).writePreferences();
            }
        });

        createDots(0);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);
                if(position == (layouts.length-1)){
                    btnNext.setText("Start");
                    btnSkip.setVisibility(View.INVISIBLE);
                }else{
                    btnNext.setText("Next");
                    btnSkip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void loadLogInActivity() {
        finish();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }

    private void loadNextSlide(){
        int nextSlide = mPager.getCurrentItem() + 1;
        if(nextSlide < layouts.length){
            mPager.setCurrentItem(nextSlide);
        }else {
            loadLogInActivity();
            new PreferenceManager(getApplicationContext()).writePreferences();
        }
    }

    private  void createDots(int currentPosition){
        if(dotsLayout!=null)
            dotsLayout.removeAllViews();

        dots = new ImageView[layouts.length];
        for (int i=0;i<layouts.length;i++){
            dots[i] = new ImageView(getApplicationContext());
            if(i == currentPosition){
                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dots));
            }else{
                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.default_dots));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);
            dotsLayout.addView(dots[i],params);
        }
    }
}

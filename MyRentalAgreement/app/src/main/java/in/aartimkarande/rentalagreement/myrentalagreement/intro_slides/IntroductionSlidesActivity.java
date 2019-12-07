package in.aartimkarande.rentalagreement.myrentalagreement.intro_slides;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.activity.LoginActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.activity.UserMainActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.adapter.SliderAdapter;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.config.MyAppSharedPreferences;

public class IntroductionSlidesActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout linearLayout;

    private SliderAdapter sliderAdapter;

    private TextView[] textViewDots;

    private Button btnNext;
    private Button btnBack;

    private MyAppSharedPreferences prefManager;

    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checking for first time launch - before calling setContentView()
        prefManager = new MyAppSharedPreferences(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_introduction_slides);

        viewPager = findViewById(R.id.sliderViewPager);
        linearLayout = findViewById(R.id.linear_layout);

        btnNext = findViewById(R.id.btnNext);
        btnBack = findViewById(R.id.btnBack);

        sliderAdapter = new SliderAdapter(this);

        changeStatusBarColor();

        viewPager.setAdapter(sliderAdapter);

        dotIndicators(0);

        viewPager.addOnPageChangeListener(pageChangeListener);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btnNext.getText().toString().equalsIgnoreCase("View Clip")){

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //launchHomeScreen();
                            prefManager.setFirstTimeLaunch(false);
                            startActivity(new Intent(IntroductionSlidesActivity.this, YouTubeClipActivity.class));
                            finish();
                        }
                    },800);

                }else{
                    viewPager.setCurrentItem(currentPage+1);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(currentPage-1);
            }
        });

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(IntroductionSlidesActivity.this, UserMainActivity.class));
        finish();
    }

    private void dotIndicators(int position){
        textViewDots = new TextView[5];
        linearLayout.removeAllViews();

        for(int i=0; i<textViewDots.length; i++){
            textViewDots[i] = new TextView(this);
            textViewDots[i].setText(Html.fromHtml("&#8226"));
            textViewDots[i].setTextSize(35);
            textViewDots[i].setTextColor(getResources().getColor(R.color.colorPrimary));

            linearLayout.addView(textViewDots[i]);
        }

        if (textViewDots.length > 0){
            textViewDots[position].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            textViewDots[position].setTextSize(36);
        }

    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            dotIndicators(i);
            currentPage = i;

            if(i == 0){
                btnNext.setEnabled(true);
                btnNext.setText("Next");

                btnBack.setEnabled(false);
                btnBack.setVisibility(View.INVISIBLE);
                btnBack.setText(FinalValues.EMPTY_STR);

            }else if(i == textViewDots.length-1){

                btnNext.setEnabled(true);
                btnNext.setText("View Clip");

                btnBack.setEnabled(true);
                btnBack.setVisibility(View.VISIBLE);
                btnBack.setText("Back");

            }else {
                btnNext.setEnabled(true);
                btnNext.setText("Next");

                btnBack.setEnabled(true);
                btnBack.setVisibility(View.VISIBLE);
                btnBack.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

}

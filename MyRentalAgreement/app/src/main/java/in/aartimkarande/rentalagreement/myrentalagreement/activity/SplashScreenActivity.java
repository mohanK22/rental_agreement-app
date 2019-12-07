package in.aartimkarande.rentalagreement.myrentalagreement.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;
import in.aartimkarande.rentalagreement.myrentalagreement.intro_slides.IntroductionSlidesActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private LinearLayout linearLayoutInner;
    private RelativeLayout relativeLayout;

    private ProgressBar progressBar;

    private Animation animationUpToDown;
    private Animation animationBottomToCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        relativeLayout = findViewById(R.id.relative_layout);
        linearLayoutInner = findViewById(R.id.linear_layout_inner);
        progressBar = findViewById(R.id.progress_circular);

        /*changeStatusBarColor();*/

        animationUpToDown = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.uptodown);
        relativeLayout.setAnimation(animationUpToDown);

        animationBottomToCenter = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.bottomtocenter);
        linearLayoutInner.setAnimation(animationBottomToCenter);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);

                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashScreenActivity.this, IntroductionSlidesActivity.class));
                    }
                }, 1500);

            }
        }, 1800);

    }


    /*private void changeStatusBarColor() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

    }*/


}

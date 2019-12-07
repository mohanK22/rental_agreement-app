package in.aartimkarande.rentalagreement.myrentalagreement.intro_slides;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import in.aartimkarande.rentalagreement.myrentalagreement.R;
import in.aartimkarande.rentalagreement.myrentalagreement.activity.UserMainActivity;
import in.aartimkarande.rentalagreement.myrentalagreement.config.FinalValues;

public class YouTubeClipActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView youTubePlayerView;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_clip);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        youTubePlayerView = findViewById(R.id.youtube_clip);
        youTubePlayerView.initialize(new FinalValues().getYoutubeAPI(), this);

        btnLogin = findViewById(R.id.btnNext);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(YouTubeClipActivity.this, UserMainActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(YouTubeClipActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onLoaded(String s) {

            }

            @Override
            public void onAdStarted() {

            }

            @Override
            public void onVideoStarted() {

            }

            @Override
            public void onVideoEnded() {

            }

            @Override
            public void onError(YouTubePlayer.ErrorReason errorReason) {

            }
        });


        youTubePlayer.setPlaybackEventListener(new YouTubePlayer.PlaybackEventListener() {
            @Override
            public void onPlaying() {

            }

            @Override
            public void onPaused() {

            }

            @Override
            public void onStopped() {

            }

            @Override
            public void onBuffering(boolean b) {

            }

            @Override
            public void onSeekTo(int i) {

            }
        });

        if(!b){
            youTubePlayer.cueVideo(new FinalValues().getVideoID());
        }

    }


}

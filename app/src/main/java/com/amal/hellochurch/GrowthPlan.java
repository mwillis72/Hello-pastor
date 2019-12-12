package com.amal.hellochurch;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

//ads


public class GrowthPlan extends AppCompatActivity implements View.OnClickListener {

    private static final String URL_HOPE= "https://www.biblestudytools.com/topical-verses/hope-bible-verses/";
    private static final String URL_FAITH = "https://www.google.com/search?rlz=1C1CHZL_enUG837UG837&sxsrf=ACYBGNRf0v0HSo3QDLxXotkeP6djBrWL2g%3A1570187533716&ei=DSmXXa-uK8zy5gKwubmgBA&q=faith+td+jakes&oq=faith+td&gs_l=psy-ab.1.0.0j0i22i10i30j0i22i30j0i22i10i30j0i22i30l6.25733.27941..30520...0.2..0.466.1258.3-1j2....3..0....1..gws-wiz.......0i71j35i39j0i67j0i131i20i263.sdFm1Y7uOgo";
    private static final String URL_END_TIME= "https://www.prophecyupdate.com/the-watchman.html";
    private static final String URL_PRAYER = "https://www.winnerschapelny.org/submit-a-prayer-request/";
    private static final String URL_RELIGION = "https://crossexamined.org/christian-apologetics/";

    //Controls
    Button Hope;
    Button Faith;
    Button End_Time;
    Button Prayer;
    Button Religion;

    //ads
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setAnimation();
        setContentView(R.layout.growth_layout);
        Hope = findViewById(R.id.hope);
        Hope.setOnClickListener(this);
        Faith = findViewById(R.id.faith);
        Faith.setOnClickListener(this);
        End_Time = findViewById(R.id.endtime);
        End_Time.setOnClickListener(this);
        Prayer = findViewById(R.id.prayer);
        Prayer.setOnClickListener(this);
        Religion = findViewById(R.id.religion);
        Religion.setOnClickListener(this);

        //interstitial
        MobileAds.initialize(this,"ca-app-pub-9129589479375926~8012249019");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9129589479375926/9456821495");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }


    }
    public void setAnimation(){
        if(Build.VERSION.SDK_INT>20) {
            Slide slide = new Slide();
            slide.setSlideEdge(Gravity.LEFT);
            slide.setDuration(400);
            slide.setInterpolator(new DecelerateInterpolator());
            getWindow().setExitTransition(slide);
            getWindow().setEnterTransition(slide);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.hope:
                openURL(URL_HOPE);
                break;


            case R.id.endtime:
                openURL(URL_END_TIME);
                break;

//            case R.id.text_view_author_2_g_plus:
//                openURL(URL_AUTHOR_2_G_PLUS);
//                break;

            case R.id.faith:
                openURL(URL_FAITH);
                break;


            case R.id.prayer:
                openURL(URL_PRAYER);
                break;

            case R.id.religion:
                openURL(URL_RELIGION);
                break;
        }
    }

    private void openURL(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}

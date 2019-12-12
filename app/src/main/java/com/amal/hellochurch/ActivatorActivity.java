package com.amal.hellochurch;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Locale;

public class ActivatorActivity extends AppCompatActivity implements View.OnClickListener {
    Button START_BOT;
    Button GROWTH_PLAN;
    Button YOUR_PREACHER;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private static final String ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110";

    private Button refresh;
    private CheckBox startVideoAdsMuted;
    private TextView videoStatus;
    private UnifiedNativeAd nativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activatorlayout);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        MobileAds.initialize(this,"ca-app-pub-9129589479375926~8012249019");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9129589479375926/9456821495");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        //Native Ad
        refresh = findViewById(R.id.btn_refresh);
        startVideoAdsMuted = findViewById(R.id.cb_start_muted);
        videoStatus = findViewById(R.id.tv_video_status);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View unusedView) {
                refreshAd();
            }
        });

        refreshAd();

        START_BOT=findViewById(R.id.startbot);
        //START_BOT.setOnClickListener(this);
        START_BOT.setOnClickListener(this);
        GROWTH_PLAN=findViewById(R.id.startverses);
        GROWTH_PLAN.setOnClickListener(this);

        YOUR_PREACHER=findViewById(R.id.go_to_preacher);
        YOUR_PREACHER.setOnClickListener(this);
        //toolbar
        Toolbar toolbar = findViewById(R.id.toobar);
        setSupportActionBar(toolbar);

//        mAdView = findViewById(R.id.adView1);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//        mAdView.setAdListener(new AdListener(){
//            public void onAdLoaded(){
//                mAdView.setVisibility(View.VISIBLE);
//            }
//        });

    }
    private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {
        // Set the media view.
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = nativeAd.getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {
            videoStatus.setText(String.format(Locale.getDefault(),
                    "Video status: Ad contains a %.2f:1 video asset.",
                    vc.getAspectRatio()));

            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    refresh.setEnabled(true);
                    videoStatus.setText("Video status: Video playback has ended.");
                    super.onVideoEnd();
                }
            });
        } else {
            videoStatus.setText("Video status: Ad does not contain a video asset.");
            refresh.setEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activatormenu, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.share_app:
                //logic for sharing the app link goes here
                Intent share_intent = new Intent(Intent.ACTION_SEND);
                share_intent.setType("text/plain");
                share_intent.putExtra(Intent.EXTRA_SUBJECT, "HELLO PASTOR APP. CLICK LINK BELOW TO DOWNLOAD FIRST VERSION");
                share_intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.amal.hellochurch");
                startActivity(Intent.createChooser(share_intent, "choose one"));

                //code for exiting the app goes here
            case R.id.exit:
                finish();

        }
        return true;
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.startbot:
                Intent intent1=new Intent(this,MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.go_to_preacher:
                Intent intent2=new Intent(this,YourPreacher.class);
                startActivity(intent2);
                break;
            case R.id.startverses:

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                Intent i = new Intent(this,GrowthPlan.class);
                if(Build.VERSION.SDK_INT>20){
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(this);
                    startActivity(i,options.toBundle());
                }else {
                    startActivity(i);

                }
                break;

        }
    }
    /**
     * Creates a request for a new native ad based on the boolean parameters and calls the
     * corresponding "populate" method when one is successfully returned.
     *
     */
    private void refreshAd() {
        refresh.setEnabled(false);

        AdLoader.Builder builder = new AdLoader.Builder(this, ADMOB_AD_UNIT_ID);

        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            // OnUnifiedNativeAdLoadedListener implementation.
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                // You must call destroy on old ads when you are done with them,
                // otherwise you will have a memory leak.
                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = unifiedNativeAd;
                FrameLayout frameLayout =
                        findViewById(R.id.fl_adplaceholder);
                UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater()
                        .inflate(R.layout.ad_unified, null);
                populateUnifiedNativeAdView(unifiedNativeAd, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);
            }

        });

        VideoOptions videoOptions = new VideoOptions.Builder()
                .setStartMuted(startVideoAdsMuted.isChecked())
                .build();

        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                refresh.setEnabled(true);
                Toast.makeText(ActivatorActivity.this, "Failed to load native ad: "
                        + errorCode, Toast.LENGTH_LONG).show();
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());

        videoStatus.setText("");
    }

    @Override
    protected void onDestroy() {
        if (nativeAd != null) {
            nativeAd.destroy();
        }
        super.onDestroy();
    }


}

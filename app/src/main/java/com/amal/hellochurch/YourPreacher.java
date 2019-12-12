package com.amal.hellochurch;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple activity showing the use of {@link AdView} ads in
 * a {@link RecyclerView} widget.
 * <p>The {@link RecyclerView} widget is a more advanced and flexible version of
 * ListView. This widget helps simplify the display and handling of large data sets
 * by allowing the layout manager to determine when to reuse (recycle) item views that
 * are no longer visible to the user. Recycling views improves performance by avoiding
 * the creation of unnecessary views or performing expensive findViewByID() lookups.</p>
 */

public class YourPreacher extends AppCompatActivity  {
    // A banner ad is placed in every 8th position in the RecyclerView.
    public static final int ITEMS_PER_AD = 8;

    private static final String AD_UNIT_ID = "ca-app-pub-9129589479375926/8092140064";

    // The RecyclerView that holds and displays banner ads and menu items.
    private RecyclerView recyclerView;

    // List of banner ads and Preachers that populate the RecyclerView.
    private List<Object> recyclerViewItems = new ArrayList<>();
//    private RecyclerViewAdapter.AdapterListener mListener =new RecyclerViewAdapter.AdapterListener() {
//        @Override
//        public void afterAdapterItemClicked(int adapterPosition) {
//
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_preacher);

        recyclerView = findViewById(R.id.recycler_view);

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView.
        recyclerView.setHasFixedSize(true);

        // Specify a linear layout manager.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Update the RecyclerView item's list with menu items and banner ads.
        addPreachersFromJson();
        addBannerAds();
        loadBannerAds();

        // Specify an adapter.
        RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = new RecyclerViewAdapter(this,
                recyclerViewItems);
        recyclerView.setAdapter(adapter);
    }
//    @Override
//    void afterAdapterItemClicked(int adapterPosition){
//        switch (adapterPosition){
//            case 0:
//                //
//                break;
//            case 1:
//                //
//        }
//    }
    @Override
    protected void onResume() {
        for (Object item : recyclerViewItems) {
            if (item instanceof AdView) {
                AdView adView = (AdView) item;
                adView.resume();
            }
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        for (Object item : recyclerViewItems) {
            if (item instanceof AdView) {
                AdView adView = (AdView) item;
                adView.pause();
            }
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        for (Object item : recyclerViewItems) {
            if (item instanceof AdView) {
                AdView adView = (AdView) item;
                adView.destroy();
            }
        }
        super.onDestroy();
    }

    /**
     * Adds banner ads to the items list.
     */
    private void addBannerAds() {
        // Loop through the items array and place a new banner ad in every ith position in
        // the items List.
        for (int i = 0; i <= recyclerViewItems.size(); i += ITEMS_PER_AD) {
            final AdView adView = new AdView(YourPreacher.this);
            adView.setAdSize(AdSize.BANNER);
            adView.setAdUnitId(AD_UNIT_ID);
            recyclerViewItems.add(i, adView);
        }
    }

    /**
     * Sets up and loads the banner ads.
     */
    private void loadBannerAds() {
        // Load the first banner ad in the items list (subsequent ads will be loaded automatically
        // in sequence).
        loadBannerAd(0);
    }

    /**
     * Loads the banner ads in the items list.
     */
    private void loadBannerAd(final int index) {

        if (index >= recyclerViewItems.size()) {
            return;
        }

        Object item = recyclerViewItems.get(index);
        if (!(item instanceof AdView)) {
            throw new ClassCastException("Expected item at index " + index + " to be a banner ad"
                    + " ad.");
        }

        final AdView adView = (AdView) item;

        // Set an AdListener on the AdView to wait for the previous banner ad
        // to finish loading before loading the next ad in the items list.
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                // The previous banner ad loaded successfully, call this method again to
                // load the next ad in the items list.
                loadBannerAd(index + ITEMS_PER_AD);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // The previous banner ad failed to load. Call this method again to load
                // the next ad in the items list.
                Log.e("MainActivity", "The previous banner ad failed to load. Attempting to"
                        + " load the next banner ad in the items list.");
                loadBannerAd(index + ITEMS_PER_AD);
            }
        });

        // Load the banner ad.
        adView.loadAd(new AdRequest.Builder().build());
    }

    /**
     * Adds {@link Preacher}'s from a JSON file.
     */
    private void addPreachersFromJson() {
        try {
            String jsonDataString = readJsonDataFromFile();
            JSONArray preachersJsonArray = new JSONArray(jsonDataString);

            for (int i = 0; i < preachersJsonArray.length(); ++i) {

                JSONObject preacherObject = preachersJsonArray.getJSONObject(i);

                String preacherName = preacherObject.getString("name");
                String preacherDescription = preacherObject.getString("description");
                String preacherMinistry = preacherObject.getString("ministry");
                String preacherOnlinechannel = preacherObject.getString("onlinechannel");
                String preacherImageName = preacherObject.getString("photo");

                Preacher preacher = new Preacher(preacherName, preacherDescription, preacherMinistry,
                        preacherOnlinechannel, preacherImageName);
                recyclerViewItems.add(preacher);
            }
        } catch (IOException | JSONException exception) {
            Log.e(YourPreacher.class.getName(), "Unable to parse JSON file.", exception);
        }
    }

    /**
     * Reads the JSON file and converts the JSON data to a {@link String}.
     *
     * @return A {@link String} representation of the JSON data.
     * @throws IOException if unable to read the JSON file.
     */
    private String readJsonDataFromFile() throws IOException {

        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {
            String jsonDataString = null;
            inputStream = getResources().openRawResource(R.raw.menu_items_json);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            while ((jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return new String(builder);
    }

}


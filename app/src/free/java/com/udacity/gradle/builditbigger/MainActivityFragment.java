package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.jokefactory.JokeActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    String TAG = "freeDebug";

    public boolean testFlag = false;

    ProgressBar loadingBar = null;

    public String fetchedJoke = null;

    InterstitialAd mInterstitialAd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Set up for pre-fetching interstitial ad request
        mInterstitialAd = new InterstitialAd(getContext());

        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_test_ad_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();

                //process the joke Request
                loadingBar.setVisibility(View.VISIBLE);
                fetchJoke();

                //pre-fetch the next ad
                requestNewInterstitial();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);

                Log.i(TAG, "onAdFailedToLoad: ad Failed to load. Reloading...");

                //pre-fetch the next ad
                requestNewInterstitial();

            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.i(TAG, "onAdLoaded: interstitial is ready!");
            }
        });

        //Kick off the fetch
        requestNewInterstitial();

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);

        //Set onclickListener for the button
        Button jokeBtn = (Button) root.findViewById(R.id.joke_button);
        jokeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show the interstitial if it's ready
                if (mInterstitialAd.isLoaded()) {
                    Log.i(TAG, "onClick: interstitial was ready");
                    mInterstitialAd.show();
                }
                else {
                    Log.i(TAG, "onClick: interstitial was not ready.");
                    loadingBar.setVisibility(View.VISIBLE);
                    fetchJoke();
                }
            }
        });

        loadingBar = (ProgressBar) root.findViewById(R.id.joke_loading_spinner);
        loadingBar.setVisibility(View.GONE);

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);


        return root;
    }

    public void fetchJoke() {
        new EndpointsAsyncTask().execute(this);
    }

    public void launchJokeActivity() {
        if (!testFlag) {
            Context context = getActivity();

            //Create a new Intent to launch the new JokeFactory Activity
            Intent jokeIntent = new Intent(context, JokeActivity.class);

            //Set a String Extra for the joke
            jokeIntent.putExtra(context.getString(R.string.jokeID), fetchedJoke);

            //start the Activity
            context.startActivity(jokeIntent);

            //let's try here
            loadingBar.setVisibility(View.GONE);
        }
    }

    private void requestNewInterstitial() {
        Log.i(TAG, "requestNewInterstitial: Fetching interstitial...");
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .build();
        mInterstitialAd.loadAd(adRequest);
    }
}



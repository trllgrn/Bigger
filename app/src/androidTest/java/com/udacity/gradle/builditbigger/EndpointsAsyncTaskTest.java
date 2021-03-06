package com.udacity.gradle.builditbigger;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

/**
 * Created by greent on 3/1/16.
 */
@RunWith(AndroidJUnit4.class)
public class EndpointsAsyncTaskTest {

    final String TAG = "GCETest";

    @Test
    public void testDoInBackground() throws Exception {
        //Create the fragment
        MainActivityFragment fragment = new MainActivityFragment();
        //Set the test flag so that the Joke Activity won't attempt to launch
        fragment.testFlag = true;
        //Launch the AsyncTask
        new EndpointsAsyncTask().execute(fragment);
        //The execution speed will vary on different machines, need at least 6 seconds, since
        //there is a 5s delay in the AsyncTask
        Thread.sleep(10000);
        Log.i(TAG, "testDoInBackground: " + fragment.fetchedJoke);
        assertTrue( "Error: fetchedJoke is : " + fragment.fetchedJoke,fragment.fetchedJoke.contains("snowman"));
    }
}
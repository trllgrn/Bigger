package com.example.jokefactory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        TextView theJokeTextView = (TextView) findViewById(R.id.joke_textview);

        //Retrieve the joke from the Intent Extras
        String passedJoke = null;
        //the Intent that started us
        Intent intent = getIntent();
        passedJoke = intent.getStringExtra(getString(R.string.jokeID));

        if (passedJoke != null) {
            //Set the textView to display the joke that was passed in
            theJokeTextView.setText(passedJoke);
        } else {
            //Something went wrong, set a snarky message
            theJokeTextView.setText("The jokes on you, pal!");
        }
    }
}

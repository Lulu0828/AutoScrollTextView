package com.lulu.autoscrolltextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private AutoScrollTextView auto_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auto_tv = (AutoScrollTextView) findViewById(R.id.auto_textview);

        auto_tv.setText("AutoScrollTextView");
        auto_tv.init(getWindowManager());
        auto_tv.startScroll();
    }
}

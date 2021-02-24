package com.koroutechnologies.projectkrt.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.koroutechnologies.projectkrt.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    public void slideFromLeftToRight() {
        //slide from left to right - back press
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
    }

    public void slideFromRightToLeft() {
        //slide from right to left
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    protected void slideFromBottomToTop() {
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
    }

    protected void slideFromTopToBottom() {
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
    }


}

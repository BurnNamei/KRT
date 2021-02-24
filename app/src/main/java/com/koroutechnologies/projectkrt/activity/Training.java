package com.koroutechnologies.projectkrt.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.koroutechnologies.projectkrt.R;

import java.util.ArrayList;

public class Training extends BaseActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = "TutorialActivity";
    ViewPager viewPager;
    PagerAdapter adapter;

    TextView tvSkip, tvNext;
    View[] tDots = new View[5];
    //View d1,d2,d3,d4,d5;

    int[] t_page = {
            R.drawable.z1_intro,
            R.drawable.z2_filter,
            R.drawable.z3_exclusive,
            R.drawable.z4_multiple,
            R.drawable.z5_sorting};

    private int verCode;
    //private ArrayList<Music> musicList;
    private boolean isFromHelpPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        verCode = getIntent().getIntExtra("version_code", 0);
        //musicList = (ArrayList<Music>) getIntent().getSerializableExtra("musics");
        isFromHelpPage= getIntent().getBooleanExtra("is_from_help_page",false);
        //AppPreference.saveToAppPreference(getApplicationContext(), SyncStateContract.Constants.IS_INSTALLED_FIRST2,false);

        // Locate the ViewPager in viewpager_main.xml
        viewPager = (ViewPager) findViewById(R.id.pager);
        // Pass results to ViewPagerAdapter Class
        adapter = new CustomPagerAdapter(this);
        // Binds the Adapter to the ViewPager
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(this);

        tvSkip = (TextView) findViewById(R.id.tv_skip);
        tvNext = (TextView) findViewById(R.id.tv_next);

        tDots[0] = findViewById(R.id.d1);
        tDots[1] = findViewById(R.id.d2);
        tDots[2] = findViewById(R.id.d3);
        tDots[3] = findViewById(R.id.d4);
        tDots[4] = findViewById(R.id.d5);

        tDots[0].setBackgroundResource(R.drawable.dote_dark);
    }


    public void tutorial_click(View v) {
        switch (v.getId()) {
            case R.id.tv_next:
                int currentIndex = viewPager.getCurrentItem();
                //Log.i(TAG,String.valueOf(currentIndex));
                if (currentIndex != t_page.length - 1) {
                    viewPager.setCurrentItem(currentIndex + 1);
                } else {
                    goToNextAct();
                }

                break;

            case R.id.tv_skip:
                goToNextAct();
                break;
        }
    }

    private void goToNextAct() {
        if(isFromHelpPage){
            finish();
            slideFromRightToLeft();
            return;
        }
        Intent i = new Intent(this, HomePage.class);
        i.putExtra("version_no", verCode);
        //i.putExtra("musics", musicList);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        slideFromRightToLeft();
        Log.i(TAG, "Activity Finish");

    }


    //------------------------------
    boolean lastPage;
    int pageIndex;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //Log.w(TAG,"On PageScrolled: "+String.valueOf(position));
    }

    @Override
    public void onPageSelected(int position) {
        //Log.i(TAG,"On onPageSelected: "+String.valueOf(position));
        pageIndex = position;
        manageDote(position);
        lastPage = false;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //Log.v(TAG,"On PageScrollState:  "+String.valueOf(state));
        if (state == 0 && lastPage ==true && pageIndex!= 0) {
            goToNextAct();
            //Log.e(TAG,"END");
        }else{
            lastPage = true;
        }
    }


    //----------
    private void manageDote(int position){
        tDots[position].setBackgroundResource(R.drawable.dote_dark);
        if (position != 0) {
            tDots[position - 1].setBackgroundResource(R.drawable.dote_light);
        }
        if (position != t_page.length - 1) {
            tDots[position + 1].setBackgroundResource(R.drawable.dote_light);
            tvNext.setText("NEXT");
        } else {
            tvNext.setText("FINISH");
        }
    }

    //---------------------------------------------------------------------------------------------

    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return t_page.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.tutorial_slide_block, container, false);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageView.setImageResource(t_page[position]);
            container.addView(itemView);
            //container.setBackgroundResource(t_page[position]);
            //tDots[position].setBackgroundResource(R.drawable.dote_dark);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
            //tDots[position].setBackgroundResource(R.drawable.dote_light);
        }


    }
}

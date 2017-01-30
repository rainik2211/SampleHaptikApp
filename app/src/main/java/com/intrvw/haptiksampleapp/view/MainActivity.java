package com.intrvw.haptiksampleapp.view;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.intrvw.haptiksampleapp.R;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private ViewPager viewPager;

    private TextView conversation;

    private TextView chatList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(this);

        conversation = (TextView) findViewById(R.id.conversation_tv);
        conversation.setTypeface(Typeface.DEFAULT_BOLD);
        conversation.setOnClickListener(this);

        chatList = (TextView) findViewById(R.id.chat_list_tv);
        chatList.setOnClickListener(this);


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        switch (position){
            case 0:
                conversation.setTypeface(Typeface.DEFAULT_BOLD);
                chatList.setTypeface(Typeface.DEFAULT);
                break;

            case 1:
                chatList.setTypeface(Typeface.DEFAULT_BOLD);
                conversation.setTypeface(Typeface.DEFAULT);
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {

    }


    class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            if (position == 0){
                fragment =  ConversationFragment.getInstance();

            }else {
                fragment =  ChatListFragment.getInstance();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


}

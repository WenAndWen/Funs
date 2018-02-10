package com.wen.design.funs.activity;

import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wen.design.funs.R;
import com.wen.design.funs.fragment.HomeFragment;
import com.wen.design.funs.fragment.MeiziFragment;
import com.wen.design.funs.util.BottomNavigationViewHelper;
import com.wen.design.funs.util.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private LinearLayout friendNotice;
    private BottomNavigationView mNavigationView;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    mViewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };
    private NoScrollViewPager mViewPager;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mViewPager = (NoScrollViewPager) findViewById(R.id.view_pager);

        mViewPager.setOffscreenPageLimit(3);
        initView(mViewPager);

        mSectionsPagerAdapter = (SectionsPagerAdapter) mViewPager.getAdapter();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //MenuItem menuItem = navigationView.getMenu().findItem(R.id.nav_about);
        //menuItem.setVisible(false); // true 为显示，false 为隐藏
        //添加小红点
        friendNotice = (LinearLayout) navigationView.getMenu().findItem(R.id.nav_friend).getActionView();
        TextView msg= (TextView) friendNotice.findViewById(R.id.msg);
        msg.setText("9");

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            navigationView.getMenu().removeItem(R.id.nav_about);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_person) {
            //个人资料

        } else if (id == R.id.nav_friend) {
            //人脉

        } else if (id == R.id.nav_collection) {
            //收藏

        } else if (id == R.id.nav_settings) {
            //设置

        } else if (id == R.id.nav_feedback) {
            //反馈

        } else if (id == R.id.nav_help) {
            //帮助

        } else if (id == R.id.nav_share) {
            //推荐

        } else if (id == R.id.nav_about) {
            //关于

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initView(ViewPager viewPager) {

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new HomeFragment(),""); //回调采用Android Studio自带的回调接口
        adapter.addFragment(new HomeFragment(),""); //回调采用自己实现的回调接口，代码更为简洁
        adapter.addFragment(new MeiziFragment(),"");

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(pageChangeListener);
//        viewPager.setPageTransformer(true, new MyPageTransformer());

        mNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        BottomNavigationViewHelper.disableShiftMode(mNavigationView);

    }
    //第一个Fragment必须在整个界面加载完成时在获取Fragment对象
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        //ViewPager加载完成，得到同一对象的Fragment
        HomeFragment aFragment = (HomeFragment) mSectionsPagerAdapter.getFragment(0);
        aFragment.setScrollListener(new HomeFragment.ScrollListener() {
            @Override
            public void listener(int scroll) {
                if (scroll == 0){
                    ObjectAnimator.ofFloat(mNavigationView,"translationY",0,mNavigationView.getHeight()+mNavigationView.getHeight()+mNavigationView.getPaddingBottom()).setDuration(200).start();
                }else if (scroll == 1){
                    ObjectAnimator.ofFloat(mNavigationView,"translationY",mNavigationView.getHeight()+mNavigationView.getHeight()+mNavigationView.getPaddingBottom(),0).setDuration(200).start();
                }
            }
        });
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    mNavigationView.setSelectedItemId(R.id.navigation_home);
                    //ViewPager加载完成，得到同一对象的Fragment
                    HomeFragment homeFragment = (HomeFragment) mSectionsPagerAdapter.getFragment(0);
                    homeFragment.setScrollListener(new HomeFragment.ScrollListener() {
                        @Override
                        public void listener(int scroll) {
                            if (scroll == 0){
                                ObjectAnimator.ofFloat(mNavigationView,"translationY",0,mNavigationView.getHeight()+mNavigationView.getHeight()+mNavigationView.getPaddingBottom()).setDuration(200).start();
                            }else if (scroll == 1){
                                ObjectAnimator.ofFloat(mNavigationView,"translationY",mNavigationView.getHeight()+mNavigationView.getHeight()+mNavigationView.getPaddingBottom(),0).setDuration(200).start();
                            }
                        }
                    });
                    break;
                case 1:
                    mNavigationView.setSelectedItemId(R.id.navigation_dashboard);
                    //ViewPager加载完成，得到同一对象的Fragment
                    HomeFragment Fragment = (HomeFragment) mSectionsPagerAdapter.getFragment(1);
                    Fragment.setScrollListener(new HomeFragment.ScrollListener() {
                        @Override
                        public void listener(int scroll) {
                            if (scroll == 0){
                                ObjectAnimator.ofFloat(mNavigationView,"translationY",0,mNavigationView.getHeight()+mNavigationView.getHeight()+mNavigationView.getPaddingBottom()).setDuration(200).start();
                            }else if (scroll == 1){
                                ObjectAnimator.ofFloat(mNavigationView,"translationY",mNavigationView.getHeight()+mNavigationView.getHeight()+mNavigationView.getPaddingBottom(),0).setDuration(200).start();
                            }
                        }
                    });

                    break;
                case 2:
                    mNavigationView.setSelectedItemId(R.id.navigation_notifications);
                    //ViewPager加载完成，得到同一对象的Fragment

                    MeiziFragment homeFragment2 = (MeiziFragment) mSectionsPagerAdapter.getFragment(2);
                    homeFragment2.setScrollListener(new MeiziFragment.ScrollListener() {
                        @Override
                        public void listener(int scroll) {
                            if (scroll == 0){
                                ObjectAnimator.ofFloat(mNavigationView,"translationY",0,mNavigationView.getHeight()+mNavigationView.getHeight()+mNavigationView.getPaddingBottom()).setDuration(200).start();
                            }else if (scroll == 1){
                                ObjectAnimator.ofFloat(mNavigationView,"translationY",mNavigationView.getHeight()+mNavigationView.getHeight()+mNavigationView.getPaddingBottom(),0).setDuration(200).start();
                            }
                        }
                    });
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public Fragment currentFragment;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            this.currentFragment= (Fragment) object;
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFragment(Fragment fragment,String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        public Fragment getFragment(int position) {
            return mFragmentList.get(position);
        }
        public Fragment getCurrentFragment() {
            return currentFragment;
        }
    }


}



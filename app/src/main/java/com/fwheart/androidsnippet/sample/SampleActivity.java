package com.fwheart.androidsnippet.sample;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fwheart.androidsnippet.R;
import com.fwheart.androidsnippet.helper.AssetHelper;
import com.fwheart.androidsnippet.helper.StatusBarHelper;
import com.fwheart.androidsnippet.widget.tab.ASTabBar;
import com.fwheart.androidsnippet.widget.tab.ASTabPage;


public class SampleActivity extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private ListView mDrawerList;
    ViewPager pager;
    private String[] titles = new String[]{"Sample Tab 1", "Sample Tab 2", "Sample Tab 3", "Sample Tab 4"
            , "Sample Tab 5", "Sample Tab 6", "Sample Tab 7", "Sample Tab 8"};
    private String[] titles2 = new String[]{"Sample Tab1","Sample Tab2"};
    private Toolbar toolbar;

    ASTabBar slidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navdrawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_ab_drawer);
        }

        final ASTabPage asTabPage = (ASTabPage) findViewById(R.id.asTabPage);
        ASTabPage.TextIconItem item1 = new ASTabPage.TextIconItem("List",R.mipmap.ic_launcher,R.drawable.ic_launcher,TestFragment.newInstance(0));
        ASTabPage.TextIconItem item2 = new ASTabPage.TextIconItem("Dialog",R.mipmap.ic_launcher,R.drawable.ic_launcher,TestFragment.newInstance(1));
        ASTabPage.TextIconItem item3 = new ASTabPage.TextIconItem("Other",R.mipmap.ic_launcher,R.drawable.ic_launcher,TestFragment.newInstance(2));

        asTabPage.init(this,item1,item2,item3);

        drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(drawerToggle);
        String[] values = new String[]{
                "DEFAULT", "RED", "BLUE", "MATERIAL GREY"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        mDrawerList.setAdapter(adapter);
        StatusBarHelper.translucent(this);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int color = 0;
                switch (position) {
                    case 0:
                        color = AssetHelper.getColor(getApplication(),R.color.material_deep_teal_500);
                        break;
                    case 1:
                        color = AssetHelper.getColor(getApplication(),R.color.red);
                        break;
                    case 2:
                        color = AssetHelper.getColor(getApplication(),R.color.blue);
                        break;
                    case 3:
                        color = AssetHelper.getColor(getApplication(),R.color.material_blue_grey_800);
                        break;
                }
                asTabPage.setTabBackground(color);
                mDrawerList.setBackgroundColor(color);
                toolbar.setBackgroundColor(color);
                mDrawerLayout.closeDrawer(Gravity.START);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

}

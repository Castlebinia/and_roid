package com.showgather.sungbin.showgather;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class LocationActivity extends AppCompatActivity{

    private BottomNavigationView bottomNavigationView;
    private Intent intent;
    final Context context = LocationActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        TabLayout tabs=(TabLayout)findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("지도"));
        tabs.addTab(tabs.newTab().setText("지하철"));
        getSupportFragmentManager().beginTransaction().add(R.id.location_fragment,new fragment1()).commit();
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if(position==0){
                    selected = new fragment1();
                }else if(position==1){
                    selected = new fragment2();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.location_fragment, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.mainactivity_bottomnavigationview);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.home:
                                intent = new Intent(context, MainActivity.class);
                                break;
                            case R.id.location:
                                return true;
                            case R.id.free:
                                intent = new Intent(context, FreeActivity.class);
                                break;
                            case R.id.settings:
                                intent = new Intent(context, SettingsActivity.class);
                                break;
                        }
                        finish();
                        startActivity(intent);
                        return true;
                    }
                });

    }
}

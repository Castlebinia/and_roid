package com.showgather.sungbin.showgather;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;

import com.showgather.sungbin.showgather.Locations.gps_location;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;


public class LocationActivity extends FragmentActivity{

    private BottomNavigationView bottomNavigationView;
    private Intent intent;
    final Context context = LocationActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        getFragmentManager().beginTransaction().replace(R.id.fragment1,new fragment1());

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

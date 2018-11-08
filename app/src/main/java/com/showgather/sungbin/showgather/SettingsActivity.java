package com.showgather.sungbin.showgather;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SettingsActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Intent intent;
    final Context context = SettingsActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.mainactivity_bottomnavigationview);
        Menu menu =bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.home:
                                intent = new Intent(context,MainActivity.class);
                                break;
                            case R.id.location:
                                intent = new Intent(context,LocationActivity.class);
                                break;
                            case R.id.free:
                                intent = new Intent(context,FreeActivity.class);
                                break;
                            case R.id.settings:
                                return true;
                        }
                        finish();
                        startActivity(intent);
                        return true;
                    }
                });
    }
}

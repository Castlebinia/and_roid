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


public class LocationActivity extends FragmentActivity implements MapView.MapViewEventListener,MapView.POIItemEventListener{

    private BottomNavigationView bottomNavigationView;
    private Intent intent;
    final Context context = LocationActivity.this;
    final private static String DAUM_API_KEY = "d53fec04369ea0b054c1b221253f2cca";
    double latitude;    //내 위도(초기 gps기준)
    double longitude;   //내 경도(초기 gps기준)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        MapView mapView = new MapView(this);
        mapView.setDaumMapApiKey(DAUM_API_KEY);


        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.location_map_view);
        mapViewContainer.addView(mapView);
        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);

        onMapViewInitialized(mapView);

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

    @Override
    public void onMapViewInitialized(MapView mapView) {
        gps_location gps = new gps_location(LocationActivity.this);
        if(gps.isGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        }else{
            gps.showSettingsAlert();
        }
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(latitude,longitude);
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("자리있니?");
        marker.setTag(1);
        mapView.setMapCenterPoint(mapPoint, true);
        marker.setMapPoint(mapPoint);
        mapView.setShowCurrentLocationMarker(true);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker);
        mapView.setZoomLevel(3, true);
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {
        mapView.removeAllPOIItems();
        MapPOIItem mapPOIItem = new MapPOIItem();
        mapPOIItem.setItemName("자리있니?");
        mapPOIItem.setTag(1);
        mapView.setMapCenterPoint(mapPoint, true);
        mapPOIItem.setMapPoint(mapPoint);
        mapView.setShowCurrentLocationMarker(true);
        mapPOIItem.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        mapPOIItem.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(mapPOIItem);
    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {
    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(final MapView mapView, final MapPOIItem mapPOIItem) {
        final double lat = mapView.getMapCenterPoint().getMapPointGeoCoord().latitude;  //marker lat
        final double lon = mapView.getMapCenterPoint().getMapPointGeoCoord().longitude; //marker lon
        Intent intent = new Intent(LocationActivity.this,ShowObectActivity.class);
        finish();
        intent.putExtra("marker_lat",lat);
        intent.putExtra("marker_lon",lon);
        startActivity(intent);
    }

    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
}

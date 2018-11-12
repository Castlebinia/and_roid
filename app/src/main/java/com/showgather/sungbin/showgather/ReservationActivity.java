package com.showgather.sungbin.showgather;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.w3c.dom.Text;

@SuppressWarnings("deprecation")
public class ReservationActivity extends AppCompatActivity implements MapView.MapViewEventListener,MapView.POIItemEventListener{
    final private static String DAUM_API_KEY = "d53fec04369ea0b054c1b221253f2cca";
    public double lat;
    public double lon;
    public String name;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        Bundle extras = getIntent().getExtras();
        lat=extras.getDouble("res_lat");
        lon=extras.getDouble("res_lon");
        name=extras.getString("res_name");
        TextView textView = (TextView)findViewById(R.id.res_res_name);
        textView.setText(name);
        MapView mapView = new MapView(this);
        mapView.setDaumMapApiKey(DAUM_API_KEY);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.reservation_map);
        mapViewContainer.addView(mapView);
        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);
        onMapViewInitialized(mapView);
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
        mapView.removeAllPOIItems();
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(lat,lon);
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(name);
        marker.setTag(4);
        mapView.setMapCenterPointAndZoomLevel(mapPoint,1, true);
        marker.setMapPoint(mapPoint);
        mapView.setShowCurrentLocationMarker(true);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setShowCalloutBalloonOnTouch(false);
        mapView.addPOIItem(marker);
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
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
}

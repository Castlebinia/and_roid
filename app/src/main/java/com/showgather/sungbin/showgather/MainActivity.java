package com.showgather.sungbin.showgather;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.showgather.sungbin.showgather.Locations.gps_location;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements MapView.MapViewEventListener, MapView.POIItemEventListener {
    private BottomNavigationView bottomNavigationView;
    private Intent intent;
    final Context context = MainActivity.this;
    final private static String DAUM_API_KEY = "d53fec04369ea0b054c1b221253f2cca";
    double latitude = 0;    //내 위도(초기 gps기준)
    double longitude = 0;   //내 경도(초기 gps기준)
    String feed_url = "http://claor123.cafe24.com/Callin.php";
    private static final String TAG = "claor123";
    private String mJsonString;
    MapView mapView;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = new MapView(this);
        final ViewGroup mapViewContainer = findViewById(R.id.location_map_view);
        mapViewContainer.addView(mapView);
        mapView.setDaumMapApiKey(DAUM_API_KEY);
        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);
        onMapViewInitialized(mapView);

        final EditText number = (EditText) findViewById(R.id.main_number);
        final EditText phone = (EditText) findViewById(R.id.main_phone);
        Button call = (Button) findViewById(R.id.main_call);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetData task = new GetData();
                task.execute(feed_url, String.valueOf(latitude), String.valueOf(longitude), phone.getText().toString(), number.getText().toString());
                Intent intent = new Intent(MainActivity.this, LodingReservateActivity.class);
                intent.putExtra("user_id", phone.getText().toString());
                intent.putExtra("m_lat", latitude);
                intent.putExtra("m_lon", longitude);
                finish();
                startActivity(intent);
            }
        });

        bottomNavigationView = findViewById(R.id.mainactivity_bottomnavigationview);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.home:
                                return true;
                            case R.id.location:
                                intent = new Intent(context, LocationActivity.class);
                                mapViewContainer.removeAllViews();
                                break;
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

    public class GetData extends AsyncTask<String, Void, String> {
        //ProgressDialog progressDialog;
        String target;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // progressDialog = ProgressDialog.show(ShowObectActivity.this,
            //      "Please Wait", null, true, true);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(10000);

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                String mlat = params[1];
                String mlon = params[2];
                String mid = params[3];
                String mnumber = params[4];
                String data = "m_lat=" + mlat + "&" + "m_lon=" + mlon + "&" + "m_id=" + mid + "&" + "m_number=" + mnumber;
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(data.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
                //int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, data);

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                return sb.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "DOIN", e);
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            progressDialog.dismiss();
            Log.d(TAG, result);
            mJsonString = result;
            if (mJsonString == null) Log.d(TAG, "null!!!");
            showResult();
        }
    }

    public void showResult() {
        String TAG_JSON = "response";
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

            }
        } catch (JSONException e) {
            e.printStackTrace();
            //     Log.d(TAG, "showResult : ", e);
        }
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
        gps_location gps = new gps_location(this);
        if (gps.isGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        } else {
            gps.showSettingsAlert();
        }
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude);
        MapPOIItem marker = new MapPOIItem();
        marker.setTag(1);
        marker.setItemName("여기");
        mapView.setMapCenterPoint(mapPoint, true);
        marker.setMapPoint(mapPoint);
        mapView.setShowCurrentLocationMarker(true);
        marker.setMarkerType(MapPOIItem.MarkerType.RedPin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker);
        mapView.setZoomLevel(4, true);
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
        mapPOIItem.setItemName("여기");
        mapPOIItem.setTag(1);
        mapView.setMapCenterPoint(mapPoint, true);
        mapPOIItem.setMapPoint(mapPoint);
        mapView.setShowCurrentLocationMarker(true);
        mapPOIItem.setMarkerType(MapPOIItem.MarkerType.RedPin); // 기본으로 제공하는 BluePin 마커 모양.
        mapPOIItem.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(mapPOIItem);
        latitude = mapView.getMapCenterPoint().getMapPointGeoCoord().latitude;
        longitude = mapView.getMapCenterPoint().getMapPointGeoCoord().longitude;
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

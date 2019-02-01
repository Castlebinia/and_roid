package com.showgather.sungbin.showgather;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.showgather.sungbin.showgather.Locations.gps_location;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class fragment1 extends Fragment implements MapView.MapViewEventListener,MapView.POIItemEventListener{
    final private static String DAUM_API_KEY = "d53fec04369ea0b054c1b221253f2cca";
    double latitude;    //내 위도(초기 gps기준)
    double longitude;   //내 경도(초기 gps기준)
    String feed_url ="http://claor123.cafe24.com/Location.php";
    private static final String TAG = "claor123";
    private String mJsonString;
    MapView mapView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.fragment1,container,false);
        mapView = new MapView(getActivity());
        ViewGroup mapViewContainer = (ViewGroup)view.findViewById(R.id.location_map_view);
        mapViewContainer.addView(mapView);
        mapView.setDaumMapApiKey(DAUM_API_KEY);
        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);
        onMapViewInitialized(mapView);
        return view;
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
        gps_location gps = new gps_location(getActivity());
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
        marker.setMarkerType(MapPOIItem.MarkerType.RedPin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker);
        mapView.setZoomLevel(4, true);
        GetData task = new GetData();
        task.execute(feed_url,String.valueOf(latitude),String.valueOf(longitude));
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
        mapPOIItem.setMarkerType(MapPOIItem.MarkerType.RedPin); // 기본으로 제공하는 BluePin 마커 모양.
        mapPOIItem.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(mapPOIItem);
        GetData task = new GetData();
        task.execute(feed_url,String.valueOf(mapView.getMapCenterPoint().getMapPointGeoCoord().latitude),String.valueOf(mapView.getMapCenterPoint().getMapPointGeoCoord().longitude));
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
        if(mapPOIItem.getTag()==1) {
            Intent intent = new Intent(getActivity(), ShowObectActivity.class);
            intent.putExtra("marker_lat", lat);
            intent.putExtra("marker_lon", lon);
            startActivity(intent);
        }
    }

    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
    public class GetData extends AsyncTask<String,Void,String> {
        //ProgressDialog progressDialog;
        String target;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // progressDialog = ProgressDialog.show(ShowObectActivity.this,
            //      "Please Wait", null, true, true);
        }

        @Override
        protected String doInBackground(String ...params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(10000);

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                String mlat=params[1];
                String mlon=params[2];
                String data = "m_lat="+mlat+"&"+"m_lon="+mlon;
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
                    sb.append(line+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                return sb.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG,"DOIN",e);
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
            Log.d(TAG,  result);
            mJsonString = result;
            if(mJsonString==null)Log.d(TAG,"null!!!");
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

                String name = item.getString("name");
                String address = item.getString("address");
                String lat = item.getString("lat");
                String lon = item.getString("lon");
                String phone = item.getString("phone");
                String image_url =item.getString("image_url");
                MapPOIItem res_marker = new MapPOIItem();
                res_marker.setTag(3);
                res_marker.setItemName(name);
                MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(Double.parseDouble(lat),Double.parseDouble(lon));
                res_marker.setMapPoint(mapPoint);
                res_marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                res_marker.setShowDisclosureButtonOnCalloutBalloon(false);
                res_marker.setSelectedMarkerType(MapPOIItem.MarkerType.BluePin);
                mapView.addPOIItem(res_marker);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            //     Log.d(TAG, "showResult : ", e);
        }
    }
}

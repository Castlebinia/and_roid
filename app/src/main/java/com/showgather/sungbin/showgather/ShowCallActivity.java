package com.showgather.sungbin.showgather;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.renderscript.Double2;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.showgather.sungbin.showgather.Locations.LocationDistance;
import com.showgather.sungbin.showgather.model.ResModel;
import com.showgather.sungbin.showgather.model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ShowCallActivity extends AppCompatActivity  {

    private static final String TAG = "claor123";
    private String mJsonString;
    private ArrayList<ResModel> mArrayList;
    private MyAdapter myAdapter;
    private String feed_url;
    private RecyclerView mRecyclerView;
    private Bundle extras;
    private double lat2;
    private double lon2;
    static String ret1="";
    final Context context = ShowCallActivity.this;
    private BottomNavigationView bottomNavigationView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_obect);
        //Navigation
        bottomNavigationView = findViewById(R.id.mainactivity_bottomnavigationview);
        Menu menu =bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
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
                                return true;
                            case R.id.free:
                                intent = new Intent(context,FreeActivity.class);
                                break;
                            case R.id.settings:
                                intent = new Intent(context,SettingsActivity.class);
                                break;
                        }
                        startActivity(intent);
                        return true;
                    }
                });
        feed_url ="http://claor123.cafe24.com/Location.php";
        extras = getIntent().getExtras();
        lat2=extras.getDouble("marker_lat");
        lon2=extras.getDouble("marker_lon");
        ret1= extras.getString("res_id_1");
        Log.d("claor222",ret1);

        RecyclerView.LayoutManager mLayoutManager;

        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mArrayList = new ArrayList<>();
        myAdapter = new MyAdapter(mArrayList);

        mRecyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        GetData task = new GetData();
        task.execute(feed_url,String.valueOf(lat2),String.valueOf(lon2));
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ShowCallActivity.this,MainActivity.class);
        startActivity(intent);
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
                            String id = item.getString("id");
                            String image_url =item.getString("image_url");
                            String image_url1=item.getString("image_url1");

                            LocationDistance locationDistance = new LocationDistance();
                            double lat1=Double.parseDouble(lat);
                            double lon1=Double.parseDouble(lon);
                            double mark_diff = locationDistance.distance(lat1,lon1,lat2,lon2,"meter");
                            String diff = String.format("%.0f",mark_diff);
                            if(ret1.contains(id)) {
                    ResModel resModel = new ResModel(name, address, lat, lon,  diff+"m", "☎ " + phone, image_url, id,image_url1);
                    mArrayList.add(resModel);
                    myAdapter.notifyDataSetChanged();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            //     Log.d(TAG, "showResult : ", e);
        }
    }

}




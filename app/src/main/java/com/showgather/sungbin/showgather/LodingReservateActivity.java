package com.showgather.sungbin.showgather;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.showgather.sungbin.showgather.Locations.LocationDistance;
import com.showgather.sungbin.showgather.model.ResModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

public class LodingReservateActivity extends AppCompatActivity {
    private static final String TAG = "claor123";
    private String mJsonString;
    private ArrayList<ResModel> mArrayList;
    private MyAdapter myAdapter;
    String feed_url ="http://claor123.cafe24.com/Usercheck.php";
    private RecyclerView mRecyclerView;
    private Bundle extras;
    private Intent intent;
    public boolean check=false;
    static String m_ret="";
    int wait=0;
    public String m_user_id;
    private Timer timer;
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            try {
                wait++;
                if(wait==20){
                        extras = getIntent().getExtras();
                        GetData task = new GetData();
                        m_user_id = extras.getString("user_id");
                        task.execute(feed_url, m_user_id);
                        sleep(1000);
                    if(!check) {
                            intent = new Intent(LodingReservateActivity.this, ShowCallActivity.class);
                            intent.putExtra("res_id_1", m_ret);
                            intent.putExtra("marker_lat", extras.getDouble("m_lat"));
                            intent.putExtra("marker_lon", extras.getDouble("m_lon"));
                            finish();
                            startActivity(intent);
                            check=true;
                        }
                    }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loding_reservate);
        timer = new Timer();
        timer.schedule(task,1000,1000);

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

                String user_id=params[1];
                String data = "m_used="+user_id;
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(data.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
                //int responseStatusCode = httpURLConnection.getResponseCode();

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
            }
            return null;
        }
        @Override
        public void onProgressUpdate(Void... values) {super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            progressDialog.dismiss();
            Log.d(TAG,result);
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
                m_ret+=item.getString("res_id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "showResult : ", e);
        }
    }
}

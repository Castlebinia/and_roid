package com.showgather.sungbin.showgather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReviewWriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_write);
        TextView write_name = findViewById(R.id.res_write_name);
        //TextView Title = findViewById(R.id.review_title);
        //TextView Content = findViewById(R.id.review_content);

        Bundle extras = getIntent().getExtras();
        String name = extras.getString("res_name");
        String id = extras.getString("res_id");
        write_name.setText(name);

        Button register = findViewById(R.id.review_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(ReviewWriteActivity.this,ReservationActivity.class);
            startActivity(intent);
            }
        });
    }
}

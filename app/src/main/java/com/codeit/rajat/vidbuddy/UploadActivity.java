package com.codeit.rajat.vidbuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.VideoView;

public class UploadActivity extends AppCompatActivity {

    ScrollView uploadScrollView;
    Button uploadVideoBtn;
    VideoView uploadVideoView;
    EditText uploadVideoTitle;
    EditText uploadVideoDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        uploadScrollView = findViewById(R.id.upload_scrollView);
        uploadVideoBtn = findViewById(R.id.upload_video_btn);
        uploadVideoView = findViewById(R.id.upload_video_view);
        uploadVideoTitle = findViewById(R.id.upload_video_title);
        uploadVideoDesc = findViewById(R.id.upload_video_desc);



    }
}

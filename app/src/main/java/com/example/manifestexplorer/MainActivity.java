package com.example.manifestexplorer;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.util.Log;
import android.view.Window;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<AppliInfo> appInfoList;
    RecyclerView rv;
    Bitmap bitmap;
    public Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                appInfoList = (ArrayList<AppliInfo>) msg.obj;
                AppRVAdapter rvAdapter = new AppRVAdapter(MainActivity.this, appInfoList);
                rv = findViewById(R.id.rv);
                rv.setAdapter(rvAdapter);
                rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }else{
                super.handleMessage(msg);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Apply activity transition
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            //getWindow().setAllowReturnTransitionOverlap(true);
            //getWindow().setAllowEnterTransitionOverlap(true);
            //getWindow().setEnterTransition(new Fade());
            //getWindow().setExitTransition(new Fade());
            getWindow().setSharedElementExitTransition(new ChangeBounds());
        }
        setContentView(R.layout.activity_main);
        AppListGenerator generator = new AppListGenerator(this, mHandler);
        generator.start();//start a thread to generate recycler view
    }
}

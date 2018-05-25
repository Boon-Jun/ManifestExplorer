package com.example.manifestexplorer;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<AppliInfo> appInfoList;
    RecyclerView rv;
    ProgressBar pb;
    public Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                appInfoList = (ArrayList<AppliInfo>) msg.obj;
                AppRVAdapter rvAdapter = new AppRVAdapter(MainActivity.this, appInfoList);
                rv.setAdapter(rvAdapter);
                rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                rv.animate().alpha(1).setDuration(500);
                pb.setVisibility(View.INVISIBLE);
            }else if(msg.what == 2){
                pb.setProgress((int)msg.obj);
                super.handleMessage(msg);
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
        rv = findViewById(R.id.rv);
        rv.setAlpha(0);
        pb = findViewById(R.id.progressBar);
        pb.setIndeterminate(false);
        pb.setMax(100);
        pb.setProgress(0);
        pb.setVisibility(View.VISIBLE);
        AppListGenerator generator = new AppListGenerator(this, mHandler);
        generator.start();//start a thread to generate recycler view
    }
}

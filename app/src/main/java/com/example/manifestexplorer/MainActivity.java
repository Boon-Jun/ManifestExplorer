package com.example.manifestexplorer;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<AppliInfo> appInfoList = new ArrayList<>();
    RecyclerView rv;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<ApplicationInfo> apps = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA | PackageManager.GET_SHARED_LIBRARY_FILES);//return list of Applications. Not to be messed up with AppliInfo as AppliInfo only contains relevant Info.
        for (int x = 0; x < apps.size(); x++) {
            ApplicationInfo someApp = apps.get(x);
            String appName = someApp.loadLabel((getPackageManager())).toString();
            String dir = someApp.publicSourceDir;
            Drawable appIcon = someApp.loadIcon(getPackageManager());
            if (appIcon instanceof BitmapDrawable) {//
                bitmap = ((BitmapDrawable) appIcon).getBitmap();
            } else {//else is adaptiveIcon
                bitmap = Bitmap.createBitmap(appIcon.getIntrinsicWidth(), appIcon.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                final Canvas canvas = new Canvas(bitmap);
                appIcon.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                appIcon.draw(canvas);
            }
            appInfoList.add(new AppliInfo(appName, bitmap, dir));
        }
        AppRVAdapter rvAdapter = new AppRVAdapter(this, appInfoList);
        rv = findViewById(R.id.rv);
        rv.setAdapter(rvAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
}

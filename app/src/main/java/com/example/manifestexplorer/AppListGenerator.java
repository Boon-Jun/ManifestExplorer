package com.example.manifestexplorer;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppListGenerator extends Thread {
    private PackageManager mPM;
    private Handler mHandler;

    AppListGenerator(Context context, Handler handler) {
        mPM = context.getPackageManager();
        mHandler = handler;
    }

    @Override
    public void run() {
        Bitmap bitmap;
        ArrayList<AppliInfo> appInfoList = new ArrayList<>();
        List<ApplicationInfo> apps = mPM.getInstalledApplications(PackageManager.GET_META_DATA | PackageManager.GET_SHARED_LIBRARY_FILES);//return list of Applications. Not to be messed up with AppliInfo as AppliInfo only contains relevant Info.
        for (int x = 0; x < apps.size(); x++) {
            ApplicationInfo someApp = apps.get(x);
            String appName = someApp.loadLabel(mPM).toString();
            String dir = someApp.publicSourceDir;
            Drawable appIcon = someApp.loadIcon(mPM);
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
        Collections.sort(appInfoList);
        Message message = mHandler.obtainMessage(1, appInfoList);
        message.sendToTarget();
    }
}

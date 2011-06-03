package com.cssweb.android.user.track;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class TrackService extends Service {

    @Override
    public void onStart(Intent intent, int startId) {
        System.out.println(this.getClass().getName() + "."
                + new Exception().getStackTrace()[0].getMethodName() + "()");
        super.onStart(intent, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println(this.getClass().getName() + "."
                + new Exception().getStackTrace()[0].getMethodName() + "()");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        System.out.println(this.getClass().getName() + "."
                + new Exception().getStackTrace()[0].getMethodName() + "()");
        super.onRebind(intent);
    }

    public class TrackBinder extends Binder {
        public TrackService getService() {
            return TrackService.this;

        }
    }

    @Override
    public void onCreate() {
        System.out.println(this.getClass().getName() + "."
                + new Exception().getStackTrace()[0].getMethodName() + "()");
        HandleActivity.handleStack();
        
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println(this.getClass().getName() + "."
                + new Exception().getStackTrace()[0].getMethodName() + "()");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        System.out.println(this.getClass().getName() + "."
                + new Exception().getStackTrace()[0].getMethodName() + "()");
    }

    private final IBinder mBinder = new TrackBinder();

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println(this.getClass().getName() + "."
                + new Exception().getStackTrace()[0].getMethodName() + "()");
        return mBinder;
    }
}

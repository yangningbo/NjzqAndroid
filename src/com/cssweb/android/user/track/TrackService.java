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

//
//    // 间隔6秒监听栈顶
//    private void listenActivityData() {
//        System.out.println(this.getClass().getName() + "."
//                + new Exception().getStackTrace()[0].getMethodName() + "()");
//        Timer checktimer = new Timer(true);
//        TimerTask checktimerTask = new TimerTask() {
//
//            @Override
//            public void run() {
//                System.out.println(this.getClass().getName() + "."
//                        + new Exception().getStackTrace()[0].getMethodName()
//                        + "()");
//                int currentsize = activityStack.size();
//
//                System.out.println(currentsize);
//                if (currentsize > 0) {
//                    if (activityStack.get(currentsize - 1) != null) {
//                        if (activityStack.peek().equals(
//                                activityStack.get(currentsize - 1))) {
//                            System.out.println("不发送");
//                            activityStack.removeAllElements();
//                        } else {
//                            System.out.println("发送地址和参数");
//                            activityStack.removeAllElements();
//                        }
//                        // } else if (currentsize == 1) {
//                        // System.out.println("初始化界面不发送");
//                        // }
//                        // for(int i=0;i<currentsize;i++) {
//                        // System.out.println(activityStack.get(i).getClassName());
//                    }
//                } else {
//                    System.out.println("堆栈为空,还没来得及赋值");
//                }
//            }
//        };
//        checktimer.scheduleAtFixedRate(checktimerTask, 0, 6000);// 6秒执行一次
//        Thread thr = new Thread(null, checktimerTask, "usertrackurl");
//        thr.start();
//
//    }
//
//    // 实时处理activiy信息存入堆栈,间隔5秒存入栈
//    private void getDataFromActivity() {
//        System.out.println(this.getClass().getName() + "."
//                + new Exception().getStackTrace()[0].getMethodName() + "()");
//        activityStack = new Stack<ComponentName>();
//        timer = new Timer(true);
//        timetask = new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println(this.getClass().getName() + "."
//                        + new Exception().getStackTrace()[0].getMethodName()
//                        + "()");
//                context = CssApplication.getInstance();
//                ActivityManager am = (ActivityManager) context
//                        .getSystemService(Context.ACTIVITY_SERVICE);
//                ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
//                activityStack.push(cn);
//                // System.out.println(cn.getClassName());
//            }
//
//            private void sendUrl() {
//                System.out.println(this.getClass().getName() + "."
//                        + new Exception().getStackTrace()[0].getMethodName()
//                        + "()");
//
//            }
//        };
//        timer.scheduleAtFixedRate(timetask, 0, 5000);// 5秒执行一次
//        Thread thr = new Thread(null, timetask, "user track");
//        thr.start();
//
//    }

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
    //
    // private Stack getActivityStack() {
    // System.out.println(this.getClass().getName() + "."
    // + new Exception().getStackTrace()[0].getMethodName() + "()");
    // timer = new Timer(true);
    // timetask = new TimerTask() {
    //
    // @Override
    // public void run() {
    // System.out.println(this.getClass().getName() + "."
    // + new Exception().getStackTrace()[0].getMethodName()
    // + "()");
    // context=CssApplication.getInstance();
    // ActivityManager am =
    // (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
    // ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
    // System.out.println(cn.getClassName());
    // }
    // };
    // timer.scheduleAtFixedRate(timetask, 0, 15000);// 5秒执行一次
    // Thread thr = new Thread(null, timetask, "user track");
    // thr.start();
    //
    // return null;
    //
    // }
}

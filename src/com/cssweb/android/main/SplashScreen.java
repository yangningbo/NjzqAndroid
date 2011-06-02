package com.cssweb.android.main;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cssweb.android.common.Config;
import com.cssweb.android.common.CssIniFile;
import com.cssweb.android.common.DateTool;
import com.cssweb.android.common.Global;
import com.cssweb.android.connect.ConnService;
import com.cssweb.android.service.AutoLoadAllStock;
import com.cssweb.android.util.ActivityUtil;
import com.cssweb.android.util.CssSystem;
import com.cssweb.quote.util.StockInfo;
import com.cssweb.quote.util.Utils;

import dalvik.system.VMRuntime;

public class SplashScreen extends Activity {
    private Activity a = this;

    private ProgressBar pBar;

    private AlarmManager alarmManager;

    private PendingIntent pendingIntent;

    private TextView tiptextView;

    private int filetype = 0;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            filetype = bundle.getInt("filetype");

        // 强制分配大内存,增强程序堆内存的处理效率
        VMRuntime.getRuntime().setMinimumHeapSize(CssSystem.CWJ_HEAP_SIZE);
        VMRuntime.getRuntime().setTargetHeapUtilization(
                CssSystem.TARGET_HEAP_UTILIZATION);

        ActivityUtil.setNoTitle(SplashScreen.this);

        setContentView(R.layout.zr_welcome);

        pBar = (ProgressBar) this.findViewById(R.id.progressBar);
        pBar.setIndeterminate(false);
        tiptextView = (TextView) findViewById(R.id.texttip);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AutoLoadAllStock.class);
        intent.putExtra("filetype", filetype);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.cancel(pendingIntent);

        initConfig();

   
            // Detach our existing connection.
//            unbindService(mConnection);
//            mIsBound = false;
        

    }

 
    @Override
    protected void onResume() {
        Log.i("##########", ">>>>>>onResume>>>>>>>" + filetype);
        super.onResume();
        ActivityUtil.ALARM_RECORED = -1;
        if (CssSystem.getHardPixes(a)) {
            System.gc();
            if (filetype == Global.QUOTE_HKSTOCK)
                loadAllHKStock();
            else {
                loadAllStock();
            }
        } else {
            showDialog(1);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initConfig() {
        new Config(SplashScreen.this);
    }

    private void loadAllStock() {
        new AsyncTask<Void, Void, Boolean>() {
            /**
             * 此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间
             */
            @Override
            protected Boolean doInBackground(Void... arg0) {
                boolean flag = true;
                JSONObject quoteData = null;
                try {
                    if (filetype == 0 && StockInfo.allStock != null) {
                        flag = true;
                    } else {
                        StockInfo.clearData();
                        if (DateTool.isNotStartLoadStockTime()) {
                            String jsonObject = CssIniFile
                                    .loadStockData(
                                            SplashScreen.this,
                                            CssIniFile
                                                    .GetFileName(CssIniFile.UserStockFile));
                            if (jsonObject != null) {
                                Log.i("==9:20之前本地有文件，开定时器并且初始化数据==", ">>>>>>");
                                quoteData = new JSONObject(jsonObject);
                                flag = initAllStock(quoteData, 5);
                            } else {
                                Log.i("==9:20之前本地没有文件先取服务器，在开定时器==", ">>>>>>");
                                quoteData = ConnService.getAllStock();
                                flag = initAllStock(quoteData, 2);
                            }
                        } else if (DateTool.isLoadStockTime()) {
                            String jsonObject = CssIniFile
                                    .loadStockData(
                                            SplashScreen.this,
                                            CssIniFile
                                                    .GetFileName(CssIniFile.UserStockFile));
                            if (jsonObject != null) {
                                quoteData = new JSONObject(jsonObject);
                                JSONObject jMD5 = ConnService.getStockFileMD5();
                                if (Utils.isHttpStatus(jMD5)) {
                                    String serMd5code = jMD5.getJSONObject(
                                            "data").getString("allstock");
                                    if (!quoteData.getString("md5code").equals(
                                            serMd5code)) {
                                        Log.i("==9:20之后9:40之前获取MD5不一样==",
                                                ">>>>>>");
                                        quoteData = ConnService.getAllStock();
                                        flag = initAllStock(quoteData, 2);
                                    } else {
                                        Log.i("==9:20之后9:40之前获取MD5一样==",
                                                ">>>>>>");
                                        flag = initAllStock(quoteData, 5);
                                    }
                                } else {
                                    Log.i("==9:20之后9:40之前获取MD5失败==", ">>>>>>");
                                    flag = initAllStock(quoteData, 5);
                                }
                            } else {
                                Log.i("==9:20之后9:40之前本地没有文件先取服务器，在开定时器==",
                                        ">>>>>>");
                                quoteData = ConnService.getAllStock();
                                flag = initAllStock(quoteData, 2);
                            }
                        } else if (filetype != 0) {// 重启应用
                            Log.i("==重新启动应用==", ">>>>>>");
                            quoteData = ConnService.getAllStock();
                            flag = initAllStock(quoteData, 1);
                        } else {
                            String jsonObject = CssIniFile
                                    .loadStockData(
                                            SplashScreen.this,
                                            CssIniFile
                                                    .GetFileName(CssIniFile.UserStockFile));
                            if (jsonObject != null) {
                                Log.i("==9:40分之后没有数据取本地ALLSTOCK成功==", ">>>>>>");
                                quoteData = new JSONObject(jsonObject);
                                JSONObject jsonstr = CssIniFile
                                        .getStockInfoByKlineURL(
                                                SplashScreen.this,
                                                "loadallstock");
                                if (jsonstr != null) {
                                    if (DateTool.checkTodayIsLoadOrnot(jsonstr
                                            .getString("time"))) {
                                        flag = initAllStock(quoteData, 6);
                                    } else {
                                        JSONObject jMD5 = ConnService
                                                .getStockFileMD5();
                                        if (Utils.isHttpStatus(jMD5)) {
                                            String serMd5code = jMD5
                                                    .getJSONObject("data")
                                                    .getString("allstock");
                                            Log.i("==BroadcastReceiver==",
                                                    "9:40分之后取MD5码比较:"
                                                            + quoteData
                                                                    .getString("md5code")
                                                            + ">>>>>>" + jMD5);
                                            if (!quoteData.getString("md5code")
                                                    .equals(serMd5code)) {
                                                quoteData = ConnService
                                                        .getAllStock();
                                            }
                                            flag = initAllStock(quoteData, 1);
                                        } else
                                            flag = false;
                                    }
                                } else
                                    flag = initAllStock(quoteData, 6);

                            } else {
                                Log.i("==9:40分之后本地没有数据取服务器==", ">>>>>>");
                                quoteData = ConnService.getAllStock();
                                flag = initAllStock(quoteData, 1);
                            }
                        }
                    }
                    if (flag) {
                        publishProgress(); // 调用publishProgress（）更新进度
                        for (int i = 1; i <= 100; i++) {
                            Thread.sleep(30);
                            pBar.setProgress(i);
                        }
                    } else {
                        CssIniFile.delIniWithAPPEND(SplashScreen.this,
                                CssIniFile.UserStockFile, "loadallstock");
                    }
                } catch (JSONException e) {
                    flag = Boolean.FALSE;
                } catch (Exception e) {
                    flag = Boolean.FALSE;
                }
                return flag;
            }

            /**
             * 此方法在主线程执行，用于显示任务执行的进度
             */
            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
                tiptextView.setText(getResources().getString(
                        R.string.splashloadtip));
            }

            /**
             * 当任务执行之前开始调用此方法，可以在这里显示进度对话框
             */
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                tiptextView.setText(getResources().getString(
                        R.string.splashreadtip));
            }

            /**
             * 此方法在主线程执行，任务执行的结果作为此方法的参数返回
             */
            protected void onPostExecute(Boolean result) {
                if (result != Boolean.TRUE) {
                    openDialog(R.string.network_error);
                } else {
                    gotoMainActivity();
                }
            }
        }.execute();
    }

    private void loadAllHKStock() {
        new AsyncTask<Void, Void, Boolean>() {
            /**
             * 此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间
             */
            @Override
            protected Boolean doInBackground(Void... arg0) {
                boolean flag = true;
                JSONObject quoteData = null;
                try {
                    if (StockInfo.hashMap.isEmpty()) {
                        if (DateTool.isLoadHKStockTime()) {// 首先判断当前时间是否9:50之前
                            String jsonObject = CssIniFile
                                    .loadStockData(
                                            SplashScreen.this,
                                            CssIniFile
                                                    .GetFileName(CssIniFile.HKStockFile));
                            if (jsonObject != null) {
                                Log.i("==loadAllHKStock9:40之前本地有文件，开定时器==",
                                        ">>>>>>");
                                quoteData = new JSONObject(jsonObject);
                                JSONObject jMD5 = ConnService.getStockFileMD5();
                                if (Utils.isHttpStatus(jMD5)) {
                                    String serMd5code = jMD5.getJSONObject(
                                            "data").getString("allstockex");
                                    Log.i("==loadAllHKStock==",
                                            "9:10分之后取MD5码比较:"
                                                    + quoteData
                                                            .getString("md5code")
                                                    + ">>>>>>" + jMD5);
                                    if (!quoteData.getString("md5code").equals(
                                            serMd5code)) {
                                        quoteData = ConnService.getAllHKStock();
                                    }
                                }
                                flag = initAllStock(quoteData, 4);
                            } else {
                                Log.i("==loadAllHKStock9:40之前本地没有文件先取服务器，在开定时器==",
                                        ">>>>>>");
                                quoteData = ConnService.getAllHKStock();
                                flag = initAllStock(quoteData, 4);
                            }
                        } else if (filetype == Global.QUOTE_HKSTOCK) {// 重启应用
                            Log.i("==loadAllHKStock重新启动应用==", ">>>>>>");
                            quoteData = ConnService.getAllHKStock();
                            flag = initAllStock(quoteData, 3);
                        } else {
                            Log.i("==loadAllHKStock9:40分之后没有数据先取本地ALLSTOCK==",
                                    ">>>>>>");
                            String jsonObject = CssIniFile
                                    .loadStockData(
                                            SplashScreen.this,
                                            CssIniFile
                                                    .GetFileName(CssIniFile.HKStockFile));
                            if (jsonObject != null) {
                                Log.i("==loadAllHKStock9:40分之后没有数据取本地ALLSTOCK成功==",
                                        ">>>>>>");
                                quoteData = new JSONObject(jsonObject);
                                JSONObject jMD5 = ConnService.getStockFileMD5();
                                if (Utils.isHttpStatus(jMD5)) {
                                    String serMd5code = jMD5.getJSONObject(
                                            "data").getString("allstockex");
                                    Log.i("==BroadcastReceiver==",
                                            "9:40分之后取MD5码比较:"
                                                    + quoteData
                                                            .getString("md5code")
                                                    + ">>>>>>" + jMD5);
                                    if (!quoteData.getString("md5code").equals(
                                            serMd5code)) {
                                        quoteData = ConnService.getAllHKStock();
                                    }
                                }
                                flag = initAllStock(quoteData, 3);
                            } else {
                                Log.i("==loadAllHKStock9:40分之后没有数据取本地ALLSTOCK未成功==",
                                        ">>>>>>");
                                quoteData = ConnService.getAllHKStock();
                                flag = initAllStock(quoteData, 3);
                            }
                        }
                    } else {
                        flag = true;
                    }

                    if (flag) {
                        publishProgress(); // 调用publishProgress（）更新进度
                        for (int i = 1; i <= 100; i++) {
                            Thread.sleep(30);
                            pBar.setProgress(i);
                        }
                    } else {
                        CssIniFile.delIniWithAPPEND(SplashScreen.this,
                                CssIniFile.HKStockFile, "loadallhkstock");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    flag = Boolean.FALSE;
                } catch (Exception e) {
                    e.printStackTrace();
                    flag = Boolean.FALSE;
                }
                return flag;
            }

            /**
             * 此方法在主线程执行，用于显示任务执行的进度
             */
            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
                tiptextView.setText(getResources().getString(
                        R.string.splashloadtip));
            }

            /**
             * 当任务执行之前开始调用此方法，可以在这里显示进度对话框
             */
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                tiptextView.setText(getResources().getString(
                        R.string.splashreadtip));
            }

            /**
             * 此方法在主线程执行，任务执行的结果作为此方法的参数返回
             */
            protected void onPostExecute(Boolean result) {
                if (result != Boolean.TRUE) {
                    openDialog(R.string.network_error);
                } else {
                    gotoMainActivity();
                }
            }
        }.execute();
    }

    protected void openDialog(int msg) {
        if (a.hasWindowFocus()) {
            new AlertDialog.Builder(SplashScreen.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.alert_dialog_about)
                    .setMessage(
                            getApplicationContext().getResources().getText(msg))
                    .setPositiveButton(R.string.alert_dialog_ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                        int whichButton) {
                                    gotoMainActivity();
                                }
                            })
                    .setNegativeButton(R.string.alert_dialog_cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                        int whichButton) {
                                    isExitSystem();
                                }
                            }).show();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 按下的如果是BACK，同时没有重复
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            isExitSystem();
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean initAllStock(JSONObject quoteData, int type)
            throws JSONException {
        if (Utils.isHttpStatus(quoteData)) {
            if (type == 1) {
                CssIniFile.saveAllStockData(SplashScreen.this,
                        CssIniFile.UserStockFile, quoteData.toString());
                StockInfo.initAllStock(quoteData);
                StockInfo.allStock = quoteData.getJSONArray("data");
            } else if (type == 2) {
                CssIniFile.saveAllStockData(SplashScreen.this,
                        CssIniFile.UserStockFile, quoteData.toString());
                alarmManager.setRepeating(AlarmManager.RTC, 0, 60 * 1000,
                        pendingIntent);
                StockInfo.initAllStock(quoteData);
                StockInfo.allStock = quoteData.getJSONArray("data");
            } else if (type == 5) {
                alarmManager.setRepeating(AlarmManager.RTC, 0, 60 * 1000,
                        pendingIntent);
                StockInfo.initAllStock(quoteData);
                StockInfo.allStock = quoteData.getJSONArray("data");
            } else if (type == 6) {
                StockInfo.initAllStock(quoteData);
                StockInfo.allStock = quoteData.getJSONArray("data");
            } else if (type == 3) {
                CssIniFile.saveAllHKStockData(SplashScreen.this,
                        CssIniFile.HKStockFile, quoteData.toString());
                StockInfo.initAllHKStock(quoteData);
            } else if (type == 4) {
                CssIniFile.saveAllHKStockData(SplashScreen.this,
                        CssIniFile.HKStockFile, quoteData.toString());
                // alarmManager.setRepeating(AlarmManager.RTC, 0, 60 * 1000,
                // pendingIntent);
                StockInfo.initAllHKStock(quoteData);
            }
            if (type != 3 && type != 4) {
                String jsonObject = CssIniFile.loadStockData(SplashScreen.this,
                        CssIniFile.GetFileName(CssIniFile.HKStockFile));
                if (jsonObject != null) {
                    JSONObject hkJSON = new JSONObject(jsonObject);
                    StockInfo.allHKStock = hkJSON.getJSONArray("data");
                }
            } else {
                StockInfo.allHKStock = quoteData.getJSONArray("data");
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case 1:
            return new AlertDialog.Builder(SplashScreen.this)
                    .setTitle(R.string.alert_dialog_about)
                    .setMessage(R.string.alert_system_not_support)
                    .setPositiveButton(R.string.alert_dialog_ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                        int whichButton) {
                                    isExitSystem();
                                }
                            }).create();
        }
        return null;
    }

    private void gotoMainActivity() {
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    
    }

    public void isExitSystem() {
        int version = CssSystem.getSDKVersionNumber();
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        if (version < 8) {// 2.2以下版本
            manager.restartPackage(getPackageName());
        } else {
            // manager.killBackgroundProcesses(getPackageName());
            // manager.restartPackage(getPackageName());
            System.exit(0);
        }
    }
}
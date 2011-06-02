/**
 * Copyright 2010 CssWeb Microsystems, Inc. All rights reserved.
 * CssWeb PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @(#)QHHQActivity.java 上午09:14:56 2011-4-17
 */
package com.cssweb.android.quote;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.cssweb.android.base.GridViewActivity;
import com.cssweb.android.common.CssIniFile;
import com.cssweb.android.common.DateTool;
import com.cssweb.android.common.Global;
import com.cssweb.android.connect.ConnService;
import com.cssweb.android.custom.MyGrid;
import com.cssweb.android.main.R;
import com.cssweb.android.session.TradeUser;
import com.cssweb.quote.util.StockInfo;
import com.cssweb.quote.util.Utils;

/**
 * 期货行情九宫格界面
 *
 * @author hujun
 * @version 1.0
 * @see
 * @since 1.0
 */
public class QHHQActivity extends GridViewActivity {
	private MyGrid mGrid;

//	private AlarmManager alarmManager;
//	private PendingIntent pendingIntent;
	
	@Override  
	public void onCreate(Bundle savedInstanceState) {    
		super.onCreate(savedInstanceState);  
		setContentView(R.layout.njzq_gridview);
        initPopupWindow();
		mGrid = (MyGrid) findViewById(R.id.mainmenu_grid);
		Bundle bundle = getIntent().getExtras();
		menuId = bundle.getInt("menu_id");
		menuName = "期货行情";
		initMenuName(menuId, -1);
		initTitle(R.drawable.njzq_title_left_back, 0, menuName);
		
//		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//		Intent intent = new Intent(this, AutoLoadAllStock.class);
//		intent.putExtra("filetype", Global.QUOTE_HKSTOCK);
//		pendingIntent = PendingIntent.getBroadcast(this, 0,
//				intent, 0);
//    	alarmManager.cancel(pendingIntent);
	}
	
	private void initMenuName(int id, int pos) {
		initGrid(mGrid, menuId);
		if(pos>-1) {
			
		}
	}
	
	@Override
	protected void openChild(int pos, int position) {
		switch(pos){
			case Global.NJZQ_HQBJ_QHHQ:
				loadAllHKStock(position);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		 changeBG();
		 String userType = TradeUser.getInstance().getUserType();
		 if("serv".equals(userType)) 
			initToolBar(Global.BAR_IMAGE_1, Global.BAR_TAG_2);
		 else
			initToolBar(Global.BAR_IMAGE_2, Global.BAR_TAG);
	}
	
	private void loadAllStock(final int position) {
		Intent localIntent = new Intent();
		if(position == 0){
			localIntent.setClass(QHHQActivity.this, ZJS.class);
			Bundle extras = new Bundle();
			extras.putInt("stocktype", 201);
			extras.putString("market", "cffex");
			extras.putString("exchange", "cf");
			extras.putString("title", "股指期货");
			localIntent.putExtras(extras);
			
		}else if(position == 1){
			localIntent.setClass(QHHQActivity.this, SQS.class);
			
			Bundle extras = new Bundle();
			extras.putInt("stocktype", 201);
			extras.putString("market", "sfe");
			extras.putString("exchange", "sf");
			extras.putString("title", "上期所");
			extras.putInt("type", R.array.sqs_type_menu);
			localIntent.putExtras(extras);
		}else if(position == 2){
			localIntent.setClass(QHHQActivity.this, DSS.class);
			
			Bundle extras = new Bundle();
			extras.putInt("stocktype", 201);
			extras.putString("market", "dce");
			extras.putString("exchange", "dc");
			extras.putString("title", "大商所");
			extras.putInt("type", R.array.dss_type_menu);
			localIntent.putExtras(extras);
		}else if(position == 3){
			localIntent.setClass(QHHQActivity.this, ZSS.class);
			
			Bundle extras = new Bundle();
			extras.putInt("stocktype", 201);
			extras.putString("market", "czce");
			extras.putString("exchange", "cz");
			extras.putString("title", "郑商所");
			extras.putInt("type", R.array.zss_type_menu);
			localIntent.putExtras(extras);
		}else if(position == 4){
			localIntent.setClass(QHHQActivity.this, QQSP.class);
		}
		startActivity(localIntent);
	}
	
	private void loadAllHKStock(final int position) {
		if(StockInfo.HKINDEX.isEmpty())
			openProgress();
    	new AsyncTask<Void, Void, Boolean>() {
    		/**
    		 * 此方法在后台线程执行，完成任务的主要工作，通常需要较长的时间
    		 */
			@Override
			protected Boolean doInBackground(Void... arg0) {
				boolean flag = true;
				JSONObject quoteData = null;
				try {
					if(StockInfo.HKINDEX.isEmpty()) {
						if(DateTool.isLoadHKStockTime()) {//首先判断当前时间是否9:50之前
							String jsonObject = CssIniFile.loadStockData(QHHQActivity.this, CssIniFile.GetFileName(CssIniFile.HKStockFile));
							if(jsonObject!=null) {
								Log.i("==loadAllHKStock9:40之前本地有文件，开定时器==", ">>>>>>");
								quoteData = new JSONObject(jsonObject);
								JSONObject jMD5 = ConnService.getStockFileMD5();
								if(Utils.isHttpStatus(jMD5)) {
									String serMd5code = jMD5.getJSONObject("data").getString("allstockex");
									Log.i("==loadAllHKStock==", "9:10分之后取MD5码比较:"+quoteData.getString("md5code")+">>>>>>" +jMD5);
									if(!quoteData.getString("md5code").equals(serMd5code)) {
										quoteData = ConnService.getAllHKStock();
									}
								}
								flag = initAllStock(quoteData, 4);
							}
							else {
								Log.i("==loadAllHKStock9:40之前本地没有文件先取服务器，在开定时器==", ">>>>>>");
								quoteData = ConnService.getAllHKStock();	
								flag = initAllStock(quoteData, 4);	
							}
						}
	 					else {
							Log.i("==loadAllHKStock9:40分之后没有数据先取本地ALLSTOCK==", ">>>>>>");
							String jsonObject = CssIniFile.loadStockData(QHHQActivity.this, CssIniFile.GetFileName(CssIniFile.HKStockFile));
							if(jsonObject!=null) {
								Log.i("==loadAllHKStock9:40分之后没有数据取本地ALLSTOCK成功==", ">>>>>>");
								quoteData = new JSONObject(jsonObject);
								JSONObject jMD5 = ConnService.getStockFileMD5();
								if(Utils.isHttpStatus(jMD5)) {
									String serMd5code = jMD5.getJSONObject("data").getString("allstockex");
									Log.i("==loadAllHKStock==", "9:40分之后取MD5码比较:"+quoteData.getString("md5code")+">>>>>>" +jMD5);
									if(!quoteData.getString("md5code").equals(serMd5code)) {
										quoteData = ConnService.getAllHKStock();
									}
								}
								flag = initAllStock(quoteData, 3);
							}
							else {
								Log.i("==loadAllHKStock9:40分之后没有数据取本地ALLSTOCK未成功==", ">>>>>>");
								quoteData = ConnService.getAllHKStock();	
								flag = initAllStock(quoteData, 3);
							}
						}
					}
				} catch (JSONException e) {
					flag = Boolean.FALSE;
				} catch (Exception e) {
					flag = Boolean.FALSE;
				}
				return flag;
			}
			
			/**
			 * 此方法在主线程执行，任务执行的结果作为此方法的参数返回
			 */
			protected void onPostExecute(Boolean result) {
				hiddenProgress();
				if (result != Boolean.TRUE) {
					toast(R.string.load_data_error);
	            }
				else {
					loadAllStock(position);
				}
			}
    	}.execute();
    }
	
	private boolean initAllStock(JSONObject quoteData, int type) throws JSONException {
		if(Utils.isHttpStatus(quoteData)) {
			if(type == 3) {
				CssIniFile.saveAllHKStockData(QHHQActivity.this, CssIniFile.HKStockFile, quoteData.toString());
				StockInfo.initAllHKStock(quoteData);
			}
			else if(type == 4) {
				CssIniFile.saveAllHKStockData(QHHQActivity.this, CssIniFile.HKStockFile, quoteData.toString());
//				alarmManager.setRepeating(AlarmManager.RTC, 0, 60 * 1000, pendingIntent);
				StockInfo.initAllHKStock(quoteData);
			}
			return true;
		}
		else {
			return false;
		}
    }
}

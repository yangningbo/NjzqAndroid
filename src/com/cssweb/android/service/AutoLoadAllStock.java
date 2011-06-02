/**
 * Copyright 2010 CssWeb Microsystems, Inc. All rights reserved.
 * CssWeb PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @(#)AutoLoadAllStock.java 上午11:30:01 2011-3-28
 */
package com.cssweb.android.service;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.cssweb.android.common.CssIniFile;
import com.cssweb.android.common.DateTool;
import com.cssweb.android.common.Global;
import com.cssweb.android.connect.ConnService;
import com.cssweb.android.main.RestartDialog;
import com.cssweb.quote.util.Utils;

/**
 * 
 *
 * @author hujun
 * @version 1.0
 * @see
 * @since 1.0
 */
public class AutoLoadAllStock extends BroadcastReceiver {
	private int filetype = 1;
	private String jsonObject = null;
	private boolean isOpen = true;
	
	@Override
	public void onReceive(final Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
        if(bundle!=null)
        	filetype = bundle.getInt("filetype");
        if(filetype==Global.QUOTE_HKSTOCK) {
        	
        }
        else {
        	jsonObject = CssIniFile.loadStockData(context, CssIniFile.GetFileName(CssIniFile.UserStockFile));
			//处理allstock
			loadAllStock(context, intent);
		}
	}
	
	private void loadAllStock(final Context context, final Intent intent) {
    	new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... arg0) {
				boolean flag = true;
				int end = Integer.parseInt(DateTool.getLoadAllStockEndTime());
				int star = Integer.parseInt(DateTool.getLoadAllStockStarTime());
				int now = Integer.parseInt(DateTool.getLocalCurrentDate());
				Log.i("==BroadcastReceiver==", star+">>>>>>"+now+">>>>>>" + end);
				if(now<=end&&now>=star) {
					if(jsonObject!=null) {
						try {
							JSONObject localData = new JSONObject(jsonObject);
							String locMd5code = localData.getString("md5code");
							JSONObject jMD5 = ConnService.getStockFileMD5();
							if(Utils.isHttpStatus(jMD5)) {
								String serMd5code = jMD5.getJSONObject("data").getString("allstock");
								Log.i("==BroadcastReceiver==", "9:10分之后取MD5码比较:"+locMd5code+">>>>>>" +serMd5code);
								Log.i("==BroadcastReceiver==", "9:10分之后取MD5码比较结果:"+locMd5code.equals(serMd5code));
								if(!locMd5code.equals(serMd5code)) {
									CssIniFile.DeletFilePath(CssIniFile.UserStockFile);
									flag = false;
								}
								else {
									flag = true;
								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					else {
						jsonObject = CssIniFile.loadStockData(context, CssIniFile.GetFileName(CssIniFile.UserStockFile));
					}
				}
				else if(now>end) {
					context.stopService(intent);
				}
				return flag;
			}
    		
			protected void onPostExecute(Boolean result) {
				if (result == Boolean.TRUE) {
	            }
				else {
					if(isOpen) {
						isOpen = false;
						intent.setClass(context, RestartDialog.class);   
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						//intent.putExtra("filetype", filetype);
						intent.putExtra("filetype", 1);
						context.startActivity(intent);
					}
				}
			}
    	}.execute();
	}
}

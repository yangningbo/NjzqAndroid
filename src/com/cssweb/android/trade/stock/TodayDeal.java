package com.cssweb.android.trade.stock;

import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cssweb.android.common.CssLog;
import com.cssweb.android.common.Global;
import com.cssweb.android.connect.ConnPool;
import com.cssweb.android.main.R;
import com.cssweb.android.trade.util.TradeUtil;
import com.cssweb.quote.util.GlobalColor;

/**
 * 当日成交查询
 * 
 * @author wangsheng
 *
 */
public class TodayDeal extends TradeQueryBase {
	private static final String DEBUG_TAG = "TodayDeal";
	private int btnTag = -1;
	private Thread thread = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initTitle(R.drawable.njzq_title_left_back, 0, "当日成交");
		
		String[] toolbarname = {Global.TOOLBAR_DETAIL, Global.TOOLBAR_SHANGYE, Global.TOOLBAR_XIAYIYE,Global.TOOLBAR_REFRESH};		
		initToolBar(toolbarname, Global.BAR_TAG);
		super.enabledToolBarfalse();
		colsName = getResources().getStringArray(R.array.zr_trade_stock_query_drcj_name);
		colsIndex = getResources().getStringArray(R.array.zr_trade_stock_query_drcj_index);
		digitColsIndex = new HashSet<Integer>();
		digitColsIndex.add(5);
		digitColsIndex.add(6);
		digitColsIndex.add(8);
		digitColsIndex.add(9);
		digitColsIndex.add(10);
		
		handlerData();
	}
	
    protected void initTitle(int resid1, int resid2, String str) {
    	super.initTitle(resid1, resid2, str);
    	changeTitleBg();
    }
	
	@Override
	protected void init(final int type) {
		this.type = type;
		r = new Runnable() {			
			public void run() {
				if(btnTag == -1 || btnTag == 3){
					if(type == 1){
						try {
							//查询当日成交
							quoteData = ConnPool.sendReq("GET_TODAY_DEAL", "304110", "");
							String res = TradeUtil.checkResult(quoteData);
							if(res==null){
								allRecords = new JSONArray();
								JSONArray jsonArr = (JSONArray)quoteData.getJSONArray("item");
								jsonArr = filterResult(jsonArr);
								for(int i=0,size=jsonArr.length(); i<size; i++){
									JSONObject jsonObj = (JSONObject)jsonArr.get(i);
									allRecords.put(formatJSONObject(jsonObj));
								}
								totalRecordCount = allRecords.length();
							}
						} catch (JSONException e) {
							Log.e(DEBUG_TAG, e.toString());
						}
					}
				}
				mHandler.sendEmptyMessage(0);
			}
		};
		thread = new Thread(r);
		thread.start();
	}
	
	@Override
	protected void handlerData() {
		if(quoteData == null){
			fillNullCurrentPageContent(this);
			hiddenProgressToolBar();
			if (type == 1 || btnTag == 3) {// 进去页面请求 或 刷新
				Toast.makeText(TodayDeal.this, "读取数据失败！请刷新或者重新设置网络。。", Toast.LENGTH_LONG).show();
				setToolBar(0, false, R.color.zr_dimgray);
				setToolBar(1, false, R.color.zr_dimgray);
				setToolBar(2, false, R.color.zr_dimgray);
			}
			return;
		}
		try {
			String res = TradeUtil.checkResult(quoteData);
			if(res!=null){
				if(!("-1").equals(res))
					toast(res);
				hiddenProgressToolBar();
				return;
			}
			
		}catch (Exception e) {
			CssLog.e(DEBUG_TAG, e.toString());
			hiddenProgressToolBar();
		}
		//填充当前页的内容
		fillCurrentPageContent(this);
		hiddenProgressToolBar();
		setBtnStatus();
	}
	
	/**
	 * 根据成交的撤销标志过滤结果集
	 * @param array
	 * @return
	 */
	private JSONArray filterResult(JSONArray array) {
		JSONArray result = new JSONArray();
		try {
			for(int i=0,size=array.length()-1; i<size; i++){
				JSONObject obj = array.getJSONObject(i);
				if(!"W".equals(obj.get("FID_CXBZ"))) {
					result.put(obj);	
				}
			}
		} catch (JSONException e) {
			Log.e(DEBUG_TAG, e.toString());
		}
		return result;
	}	
	@Override
	protected JSONObject formatJSONObject(JSONObject jsonObj)
			throws JSONException {
		JSONObject formatJsonObj = new JSONObject();
		int color = GlobalColor.colorPriceEqual;
		String mmbz = TradeUtil.getFlagName(jsonObj.getInt("FID_WTLB"),jsonObj.getString("FID_CXBZ"));
		if ("买入".equals(mmbz)) {
			color = GlobalColor.colorpriceUp;
		}else if ("卖出".equals(mmbz)) {
			color = getResources().getColor(R.color.zr_sail);
		}
		for(int i=0; i<colsIndex.length; i++) {
//			if(i==0) {
//				color = GlobalColor.colorStockName;
//				formatJsonObj.put(colsIndex[i], jsonObj.getString(colsIndex[i]) + "|" + color);
//			}else 
			if(i==4){
//				color = GlobalColor.colorPriceEqual;
//				String mmbz = TradeUtil.getFlagName(jsonObj.getInt("FID_WTLB"),jsonObj.getString("FID_CXBZ"));
//				if ("买入".equals(mmbz)) {
//					color = GlobalColor.colorpriceUp;
//				}else if ("卖出".equals(mmbz)) {
//					color = GlobalColor.colorPriceDown;
//				}
				formatJsonObj.put(colsIndex[i],mmbz+"|"+color);
			}else if(i == 5){
				//color = GlobalColor.colorPriceEqual;
				formatJsonObj.put(colsIndex[i],TradeUtil.formatNum(jsonObj.getString(colsIndex[i]),3)+"|"+color);
			}else if(i == 7){
				//color = GlobalColor.colorPriceEqual;
				formatJsonObj.put(colsIndex[i],TradeUtil.formatNum(jsonObj.getString(colsIndex[i]),3)+"|"+color);
			}else {
				//color = GlobalColor.colorPriceEqual;
				formatJsonObj.put(colsIndex[i], jsonObj.getString(colsIndex[i]) + "|" + color);
			}
		}
		return formatJsonObj; 
	}
	/**
	 * 处理详细事件
	 */
	protected void displayDetails() {
		if(allRecords.length() == 0)
			return;
		forwardDetails(TodayDeal.this);
	}
	
	@Override
	protected void onPause() {
		mHandler.removeCallbacks(r);
		super.onPause();
	}
	@Override
	protected void onResume() {
		super.onResume();
//		handlerData();
		setToolBar();
	}
	@Override
	protected void toolBarClick(int tag, View v) {
		btnTag = tag;
		 switch(tag) {
			case 0:
				displayDetails();
				break;
			case 1: 
				super.onPageUp();
				break;
			case 2:
				super.onPageDown();
				break;
			case 3: 
				setToolBar();
				break;
			default:
				cancelThread();
				break;
		 }
	}
	protected void cancelThread() {
		if(thread!=null) {
			thread.interrupt();
		}
		mHandler.removeCallbacks(r);
		hiddenProgressToolBar();
	}
	private void setBtnStatus(){
		if (totalRecordCount == 0) {
			setToolBar(0, false, R.color.zr_dimgray);
			setToolBar(1, false, R.color.zr_dimgray);
			setToolBar(2, false, R.color.zr_dimgray);
		}else{
			setToolBar(0, true, R.color.zr_white);
			if(endRowId >= totalRecordCount-1){
				setToolBar(2, false, R.color.zr_dimgray);
			}else {
				setToolBar(2, true, R.color.zr_white);
			}
			if(currentPageId == 0){
				setToolBar(1, false, R.color.zr_dimgray);
			}else {
				setToolBar(1, true, R.color.zr_white);
			}
		}
	}
}

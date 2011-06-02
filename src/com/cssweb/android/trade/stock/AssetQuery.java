package com.cssweb.android.trade.stock;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cssweb.android.base.CssBaseActivity;
import com.cssweb.android.common.CssLog;
import com.cssweb.android.common.Global;
import com.cssweb.android.connect.ConnPool;
import com.cssweb.android.main.R;
import com.cssweb.android.trade.util.TradeUtil;

public class AssetQuery extends CssBaseActivity {
	private static final String DEBUG_TAG = "AssetQuery";


	private LinearLayout assetLayout;
	private TextView rmbTextView;
	private TextView gbTextView;
	private TextView dollersTextView;
	private TextView yue;
	private TextView keyong;
	private TextView zichan;
	private TextView cankaoshizhi;
	private TextView yingkui;
	private Map<String, Map<String, String>> fundMap = null;
	private double rmbyk = 0l;
	private double myyk = 0l;
	private double gbyk = 0l;
	private double rmbsz = 0l;
	private double mysz = 0l;
	private double gbsz = 0l;
	
	private Thread thread = null;
	private JSONObject positionData = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.zr_trade_asset_list);
		initTitle(R.drawable.njzq_title_left_back, 0, "账户资产");
		String[] toolbarNames = { "", "", "", "", "", "刷新" };
		initToolBar(toolbarNames, Global.BAR_TAG);
		setBtnStatus();

		initPopupWindow();
		
		assetLayout = (LinearLayout) findViewById(R.id.asset_bg);
		rmbTextView = (TextView) findViewById(R.id.rmb);
		gbTextView = (TextView) findViewById(R.id.gb);
		dollersTextView = (TextView) findViewById(R.id.dollers);
		
		rmbTextView.setOnClickListener(listener);
		gbTextView.setOnClickListener(listener);
		dollersTextView.setOnClickListener(listener);
		
		
		yue = (TextView) findViewById(R.id.yue);
		keyong = (TextView) findViewById(R.id.keyong);
		zichan = (TextView) findViewById(R.id.zichan);
		cankaoshizhi = (TextView) findViewById(R.id.cankaoshizhi);
		yingkui = (TextView) findViewById(R.id.yingkui);
		
		// listView = (ListView)findViewById(R.id.zr_rt_listview);

		showProgress();
	}
	
	private View.OnClickListener listener = new View.OnClickListener() {
		
		public void onClick(View v) {
			
			Map<String, String> map = null;
			int len = 2;
			if(v.getId() == R.id.rmb){
				assetLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.zr_asset_rmb));
				if(fundMap==null || fundMap.size()<=0){
					setFundText(len);
					return;
				}
				map = fundMap.get("RMB");
				yingkui.setText(TradeUtil.formatNum(rmbyk+"", len));
				cankaoshizhi.setText(TradeUtil.formatNum(rmbsz+"", len));
			}else if(v.getId() == R.id.gb){
				assetLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.zr_asset_gb));
				if(fundMap==null || fundMap.size()<=0){
					setFundText(len);
					return;
				}
				map = fundMap.get("HKD");
				yingkui.setText(TradeUtil.formatNum(gbyk+"", len));
				cankaoshizhi.setText(TradeUtil.formatNum(gbsz+"", len));
			}else if(v.getId() == R.id.dollers){
				assetLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.zr_asset_dollers));
				len =3;
				if(fundMap==null || fundMap.size()<=0){
					setFundText(len);
					return;
				}
				map = fundMap.get("USD");
				yingkui.setText(TradeUtil.formatNum(myyk+"", len));
				cankaoshizhi.setText(TradeUtil.formatNum(mysz+"", len));
			}
			
			if(map!=null){
				yue.setText(TradeUtil.formatNum(map.get("FID_ZHYE"), len));
				keyong.setText(TradeUtil.formatNum(map.get("FID_KYZJ"), len));
				zichan.setText(TradeUtil.formatNum(map.get("FID_ZZC"), len));
			}else{
				setFundText(len);
			}
			
			
		}
	};

	private void setFundText(int length){
		if(length==2){
			yue.setText("0.00");
			keyong.setText("0.00");
			zichan.setText("0.00");
			cankaoshizhi.setText("0.00");
			yingkui.setText("0.00");
		}else if(length==3){
			yue.setText("0.000");
			keyong.setText("0.000");
			zichan.setText("0.000");
			cankaoshizhi.setText("0.000");
			yingkui.setText("0.000");
		}
	}
	
	protected void toolBarClick(int tag, View v) {
		switch (tag) {
		case 5:
			showProgress();
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

	protected void onPause() {
		mHandler.removeCallbacks(r);
		super.onPause();
	}

	protected void initTitle(int resid1, int resid2, String str) {
    	super.initTitle(resid1, resid2, str);
    	changeTitleBg();
    }
	
	
	@Override
	protected void init(int type) {
		r = new Runnable() {
			public void run() {
				try {
					StringBuffer bf = new StringBuffer();
					bf.append("FID_EXFLG=1");
					quoteData = ConnPool.sendReq("GET_FUNDS", "303002", bf.toString());
					String res = TradeUtil.checkResult(quoteData);
					if(res==null) {
						positionData = ConnPool.sendReq("GET_POSITION", "304101", bf.toString());
						res = TradeUtil.checkResult(positionData);
						if (res==null) {
							JSONArray jsonArr = positionData.getJSONArray("item");
							double rmbyk1 = 0l;
							double myyk1 = 0l;
							double gbyk1 = 0l;
							
							double rmbsz1 = 0l;
							double mysz1 = 0l;
							double gbsz1 = 0l;
							for(int i=0; i<jsonArr.length()-1; i++){
								JSONObject jsonobj = jsonArr.getJSONObject(i);
								String bz = jsonobj.getString("FID_BZ");
								double yk = Double.parseDouble(jsonobj.getString("FID_TBFDYK"));
								double sz = Double.parseDouble(jsonobj.getString("FID_ZXSZ"));
								if("RMB".equals(bz)){
									rmbyk1 += yk;
									rmbsz1 += sz;
								}else if("HKD".equals(bz)){
									gbyk1+= yk;
									gbsz1+=sz;
								}else if("USD".equals(bz)){
									myyk1+=yk;
									mysz1+=sz;
								}
								
							}
							quoteData.put("rmbyk", rmbyk1);
							quoteData.put("gbyk", gbyk1);
							quoteData.put("myyk", myyk1);
							quoteData.put("rmbsz", rmbsz1);
							quoteData.put("gbsz", gbsz1);
							quoteData.put("mysz", mysz1);
							
							JSONArray jarr = (JSONArray) quoteData.getJSONArray("item");
							fundMap = new HashMap<String, Map<String,String>>();
							for(int i=0; i<jarr.length()-1; i++){
								JSONObject jsonobj = jarr.getJSONObject(i);
								Map<String, String> map = new HashMap<String, String>();
								map.put("FID_ZHYE", jsonobj.getString("FID_ZHYE"));//余额
								map.put("FID_KYZJ", jsonobj.getString("FID_KYZJ"));//可用
								map.put("FID_ZZC", jsonobj.getString("FID_ZZC"));//资产
								fundMap.put(jsonobj.getString("FID_BZ"), map);
							}
							rmbyk = Double.parseDouble(quoteData.getString("rmbyk"));
							myyk = Double.parseDouble(quoteData.getString("myyk"));
							gbyk = Double.parseDouble(quoteData.getString("gbyk"));
							rmbsz = Double.parseDouble(quoteData.getString("rmbsz"));
							gbsz = Double.parseDouble(quoteData.getString("gbsz"));
							mysz = Double.parseDouble(quoteData.getString("mysz"));
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
					CssLog.e(DEBUG_TAG, e.toString());
				}
				mHandler.sendEmptyMessage(0);
			}
		};
		thread = new Thread(r);
		thread.start();
	}
	
	@Override
	protected void handlerData() {
		try {
			String res = TradeUtil.checkResult(quoteData);
			if (res != null) {
				if (res.equals("-1")){
					toast("网络连接失败！");
				}
				else {
					toast(res);
				}
				hiddenProgress();
				return;
			}
			res = TradeUtil.checkResult(positionData);
			if (res != null) {
				if (res.equals("-1")){
					toast("网络连接失败！");
				}
				else {
					toast(res);
				}
				hiddenProgress();
				return;
			}
			assetLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.zr_asset_rmb));
			if(fundMap==null || fundMap.size()<=0){
				setFundText(2);
				return;
			}
			Map<String, String> m1 = fundMap.get("RMB");
			if(m1!=null){
				yue.setText(TradeUtil.formatNum(m1.get("FID_ZHYE"), 2));
				keyong.setText(TradeUtil.formatNum(m1.get("FID_KYZJ"), 2));
				zichan.setText(TradeUtil.formatNum(m1.get("FID_ZZC"), 2));
				cankaoshizhi.setText(TradeUtil.formatNum(rmbsz+"", 2));
				yingkui.setText(TradeUtil.formatNum(rmbyk+"", 2));
			}else{
				setFundText(2);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			CssLog.e(DEBUG_TAG, e.toString());
		}
		hiddenProgress();
	}
	private void setBtnStatus(){
		setToolBar(0, false, R.color.zr_dimgray);
		setToolBar(1, false, R.color.zr_dimgray);
		setToolBar(2, false, R.color.zr_dimgray);
		setToolBar(3, false, R.color.zr_dimgray);
		setToolBar(4, false, R.color.zr_dimgray);
	}
}

/**
 * Copyright 2010 CssWeb Microsystems, Inc. All rights reserved.
 * CssWeb PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @(#)WebViewDisplay.java 下午01:09:17 2010-9-20
 */
package com.cssweb.android.web;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.cssweb.android.base.GridViewActivity;
import com.cssweb.android.common.Config;
import com.cssweb.android.common.Global;
import com.cssweb.android.main.R;
import com.cssweb.android.service.ValidationService;
import com.cssweb.android.session.TradeUser;
import com.cssweb.android.trade.service.FundService;
import com.cssweb.android.trade.util.TradeUtil;

/**
 * 资讯展示页面
 * 
 * @author hujun
 * @version 1.0
 * @see
 * @since 1.0
 */
public class WebViewDisplay extends GridViewActivity {

	private WebView webview;
	private String url = "";
	private int pos;
	private int position;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(1);
		setContentView(R.layout.webview);

		initPopupWindow();


		Bundle bundle = getIntent().getExtras();
		pos = bundle.getInt("pos");
		position = bundle.getInt("position");
		url = bundle.getString("url");

		if (url != null) {
			showPage(true);
		} else {
			initView(pos, position);
		}

	}

	private void initView(int pos, int position) {
		if (pos == Global.NJZQ_ZXHD_EGHT) {
			switch (position) {
			// 理财
			case 0:
				// 赛事公告
				url = Config.roadMncg
						+ "mncg/touch/index/affiche.jsp?serviceTime="
						+ ValidationService.getServiceTime();
				break;
			case 1:
				// 查看比赛
				url = Config.roadMncg
						+ "mncg/touch/index/chaKan.jsp?serviceTime="
						+ ValidationService.getServiceTime();
				// url =
				// "http://172.16.2.184:9999/mncg/touch/index/chaKan.jsp?serviceTime="
				// + ValidationService.getServiceTime();
				break;
			case 2:
				// 排行榜
				url = Config.roadMncg
						+ "mncg/touch/index/rank.jsp?serviceTime="
						+ ValidationService.getServiceTime();
				break;
			case 3:
				// 常见问题
				url = Config.roadMncg
						+ "mncg/touch/index/problem.jsp?serviceTime="
						+ ValidationService.getServiceTime();
				break;
			case 4:
				// 注册
				if (TradeUser.getInstance().getLoginType() == 4
						|| TradeUser.getInstance().getLoginType() == 1|| TradeUser.getInstance().getLoginType() == 2) {
					url = Config.roadMncg
							+ "mncg/touch/index/myroom.jsp?serviceTime="
							+ ValidationService.getServiceTime();
				         	// + "mncg/touch/index/authority.jsp?serviceTime="
					// } else if (TradeUser.getInstance().getLoginType() == 1
					// || TradeUser.getInstance().getLoginType() == 2) {
					// url = Config.roadMncg
					// //+ "mncg/touch/index/myroom.jsp?serviceTime="
					// + ValidationService.getServiceTime();
				} else {
					url = Config.roadMncg + "mncg/touch/login/register.jsp";
				}
				break;
			case 5:
				// 登录
				if (TradeUser.getInstance().getLoginType() == 4) {
					url = Config.roadMncg
							+ "mncg/touch/login/updatePwd.jsp?serviceTime="
							+ ValidationService.getServiceTime();
				} else if (TradeUser.getInstance().getLoginType() == 1
						|| TradeUser.getInstance().getLoginType() == 2) {
					url = Config.roadMncg
							+ "mncg/touch/index/authority.jsp?serviceTime="
							+ ValidationService.getServiceTime();
				} else {
					url = Config.roadMncg + "mncg/touch/login/login.jsp";
				}

				break;
			}
		} else if (pos == Global.NJZQ_NZBD_JXZQC) {// 精选证券池
			switch (position) {
			case 0:
				// 优选池
				activityKind = 0;
				url = Config.roadZixun
						+ "iphone/nzbd/stockPool/bond.jsp?serviceTime="
						+ ValidationService.getServiceTime();
				break;
			case 1:
				// 核心池
				activityKind = 1;
				url = Config.roadZixun
						+ "iphone/nzbd/stockPool/bond_hx.jsp?serviceTime="
						+ ValidationService.getServiceTime();
				break;
			}
		} else if (pos == Global.NJZQ_NZBD_TZZH) {// 设资组合
			url = Config.roadZixun
					+ "iphone/nzbd/nzFutures_tz.jsp?serviceTime="
					+ ValidationService.getServiceTime();

		} else if (pos == Global.NJZQ_NZBD_ZQYJ) {// 证券研究
			url = Config.roadZixun + "iphone/nzbd/analyst.jsp?serviceTime="
					+ ValidationService.getServiceTime();

		} else if (pos == Global.NJZQ_NZBD_QHYJ) {// 期货研究
			url = Config.roadZixun + "iphone/nzbd/nzFutures.jsp?serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_NZBD_CFPD) {// 财富频道
			switch (position) {
			case 0:
				// 每日解盘
				url = Config.roadZixun
						+ "iphone/nzbd/wealth.jsp?category=mrjp_Android&serviceTime="
						+ ValidationService.getServiceTime();
				break;
			case 1:
				// 投资讲坛
				url = Config.roadZixun
						+ "iphone/nzbd/wealth.jsp?category=tzlt_Android&serviceTime="
						+ ValidationService.getServiceTime();
				break;
			case 2:
				// 专题培训
				url = Config.roadZixun
						+ "iphone/nzbd/wealth.jsp?category=ztpx_Android&serviceTime="
						+ ValidationService.getServiceTime();
				break;
			}
		} else if (pos == Global.NJZQ_NZFC_NZDT) {// 宁证动态
			url = Config.roadZixun + "iphone/nzfc/nzDongtai.html?serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_NZFC_ZJNZ) {// 走进宁证
			url = Config.roadZixun
					+ "iphone/nzfc/stepNz_intro.html?serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_NZFC_JYH) {// 精英汇
			url = Config.roadZixun + "iphone/nzfc/pithyTeam.html?serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_NZFC_NZFCSP) {// 宁证风采视频
			url = Config.roadZixun
					+ "iphone/nzbd/wealth.jsp?category=nzfc&serviceTime="
					+ ValidationService.getServiceTime();
		}

		else if (pos == Global.NJZQ_ZXLP_CPBD) {// 操盘必读
			url = Config.roadZixun + "iphone/zxlp/notice.html?serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_ZXLP_YWZJ) {// 要闻直击
			url = Config.roadZixun + "iphone/zxlp/news.html?serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_ZXLP_PMHH) {// 盘面护航
			url = Config.roadZixun + "iphone/zxlp/quotation.jsp?serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_ZXLP_TGLC) {// 特供内参
			url = Config.roadZixun + "iphone/zxlp/referInfo.jsp?serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_ZXLP_ZJLX) {// 资金流向
			url = Config.roadZixun + "iphone/zxlp/fundFlow.jsp?serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_ZXLP_GG) {// 股歌gugle
			Bundle bundle = getIntent().getExtras();
			String stockcode = bundle.getString("stockcode");

			String whereStockcode = "?";
			if (stockcode != null && !"".equals(stockcode)) {
				whereStockcode = stockcode.equals("") ? "?" : "?stock_id="
						+ stockcode + "&";
			}

			url = Config.roadZixun + "iphone/zxlp/guGe.jsp" + whereStockcode
					+ "serviceTime=" + ValidationService.getServiceTime() + "&fromad=android";
		}

		else if (pos == Global.NJZQ_ZSYYT_YYKH) {// 预约开户
			url = Config.roadZixun + "iphone/zsyyt/jumpToYykh.jsp?serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_ZSYYT_YYTGG) {// 营业厅公告
			url = Config.roadZixun + "iphone/zsyyt/yytgg.jsp?serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_ZSYYT_YYWD) {// 营业网点
			url = Config.roadZixun + "iphone/zsyyt/place.jsp?serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_ZSYYT_YWZX) {// 业务中心
			url = Config.roadWsyyt + "service/common/jump.jsp?serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_ZSYYT_TZZJY) {// 投资者教育
			url = Config.roadZixun
					+ "iphone/zsyyt/investor_read.jsp?system=android&serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_ZSYYT_TJKH) {// 推荐开户
			url = Config.roadZixun + "iphone/zsyyt/tjkh.jsp?serviceTime="
					+ ValidationService.getServiceTime();
		}

		else if (pos == Global.NJZQ_JLP_WDZQTAG) {// 我的专区
			url = Config.roadZixun + "iphone/main/trade_my.jsp?serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_JLP_YYKHTAG) {// 预约开户
			url = Config.roadZixun + "iphone/zsyyt/jumpToYykh.jsp?serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_JLP_TYKTAG) {// 体验卡
			url = Config.roadZixun
					+ "iphone/main/expCustomer_my.jsp?serviceTime="
					+ ValidationService.getServiceTime();
		}

		else if (pos == Global.NJZQ_JFSC) {// 积分乐园
			url = Config.roadZixun
					+ "iphone/jfsc/exchange_eventnotice.jsp?serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.OPENHREF) {
			try {
				Bundle bundle = getIntent().getExtras();
				url = bundle.getString("url");
			} catch (Exception e) {
				Log.e("tag", e.toString());
				url = "";
			}
		}

		else if (pos == Global.NJZQ_ZXG_GONGGAO) {
			Bundle bundle = getIntent().getExtras();
			String stockcode = bundle.getString("stockcode");

			String whereStockcode = "?";
			if (stockcode != null && !"".equals(stockcode)) {
				whereStockcode = stockcode.equals("") ? "?" : "?stock_id="
						+ stockcode + "&";
			}

			url = Config.roadZixun + "iphone/zxlp/guGe_gonggao.jsp"
					+ whereStockcode + "serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_ZXG_DIANPIN) {
			Bundle bundle = getIntent().getExtras();
			String stockcode = bundle.getString("stockcode");
			String whereStockcode = "?";
			if (stockcode != null && !"".equals(stockcode)) {
				whereStockcode = stockcode.equals("") ? "?" : "?stock_id="
						+ stockcode + "&";
			}

			url = Config.roadZixun + "iphone/zxlp/guGe_dianpin.jsp"
					+ whereStockcode + "serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_ZXG_QINGBAO) {
			Bundle bundle = getIntent().getExtras();
			String stockcode = bundle.getString("stockcode");
			String whereStockcode = "?";
			if (stockcode != null && !"".equals(stockcode)) {
				whereStockcode = stockcode.equals("") ? "?" : "?stock_id="
						+ stockcode + "&";
			}
			url = Config.roadZixun + "iphone/zxlp/guGe_qingbao.jsp"
					+ whereStockcode + "serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_ZXG_ZHENDUAN) {
			Bundle bundle = getIntent().getExtras();
			String stockcode = bundle.getString("stockcode");
			String whereStockcode = "?";
			if (stockcode != null && !"".equals(stockcode)) {
				whereStockcode = stockcode.equals("") ? "?" : "?stock_id="
						+ stockcode + "&";
			}

			url = Config.roadZixun + "iphone/zxlp/guGe_zdGupiao.jsp"
					+ whereStockcode + "serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_ZXG_JJ_ZHENDUAN) {// NJZQ_ZXG_JJ_QINGBAO
			Bundle bundle = getIntent().getExtras();
			String stockcode = bundle.getString("stockcode");
			String whereStockcode = "?";
			if (stockcode != null && !"".equals(stockcode)) {
				whereStockcode = stockcode.equals("") ? "?" : "?stock_id="
						+ stockcode + "&";
			}

			url = Config.roadZixun + "iphone/zxlp/guGe_zdJijin.jsp"
					+ whereStockcode + "serviceTime="
					+ ValidationService.getServiceTime();
		}

		else if (pos == Global.NJZQ_FIND_PASSWORD) {//
			// url = Config.roadWsyyt
			// + "service/main/serv_pwd_change1.jsp?serviceTime="
			// + ValidationService.getServiceTime();

			// service/main/trade_tyPwdZhaoTip.jsp
			url = Config.roadWsyyt
					+ "service/main/trade_tyPwdZhaoTip.jsp?serviceTime="
					+ ValidationService.getServiceTime();

		} else if (pos == Global.NJZQ_SQTYK) {//
			url = Config.roadZixun
					+ "iphone/main/expCustomer_myCard_ShenQing.jsp?serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_RESET_SERVIR_PASSWORD) {
			url = Config.roadWsyyt
					+ "service/main/serv_pwd_change1.jsp?serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_TYYH_FIND_PASSWORD) {// 体验卡用户登录密码找回
			url = Config.roadZixun
					+ "iphone/main/trade_TyPwdZhaoHui.jsp?serviceTime="
					+ ValidationService.getServiceTime();
			// TODO

		} else if (pos == Global.NJZQ_FUND_RISK_TEST) {// 交易场外基金风险测评
			url = Config.roadWsyyt + "service/interface/fxcp.jsp?fxdj="
					+ TradeUser.getInstance().getRiskLevel() + "&khh="
					+ TradeUser.getInstance().getCustid()
					+ "&operation=1&serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_WTJY_SZLC) {// 神州理财
			url = Config.roadZixun
					+ "iphone/zsyyt/szlcyw_cpjs.jsp?from=iphone&serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_WTJY_RZRQ) {// 融资融券
			url = Config.roadZixun
					+ "iphone/zsyyt/rzrq_jczs.jsp?from=iphone&serviceTime="
					+ ValidationService.getServiceTime();
		}

		// webcall
		else if (pos == Global.NJZQ_ZXHD_ZJJP) {
			url = Config.roadWebcall + "webcall/3G/zjjp.jsp?serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_ZXHD_TZGW) {
			url = Config.roadWebcall + "webcall/3G/tzgw.jsp?serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_ZXHD_KHJL) {
			url = Config.roadWebcall + "webcall/3G/khjl.jsp?serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_ZXHD_ZXKF) {
			url = Config.roadWebcall + "webcall/3G/zxkf.jsp?serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_ZXHD_KFRX) {
			url = Config.roadZixun + "iphone/zxhd/kefu_rexian.jsp?serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_ZXHD_GNLY) {
			url = Config.roadZixun
					+ "iphone/zxhd/tousu_jianyi.jsp?serviceTime="
					+ ValidationService.getServiceTime();
		} else if (pos == Global.NJZQ_ZXHD_CJWT) {
			url = Config.roadZixun
					+ "iphone/commonquestion/CommonQuestion/getCommList.do?type=ishot&serviceTime="
					+ ValidationService.getServiceTime();
		}

		if (url != null) {
			showPage(false);
		}
	}

	private void showPage(boolean fromWebViewFlag) {
		showProgress();

		webview = (WebView) findViewById(R.id.webview);
		WebSettings webSettings = webview.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webview.addJavascriptInterface(
				new JHMethodImpl(mHandler, this, webview), "cssweb");

		webview.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				webview.loadUrl(url);
				return true;
			}
		});

		webview.setWebChromeClient(new MyWebChromeClient() {
		});

		webview.clearCache(true);

		// webview.setHorizontalScrollBarEnabled(false);
		// webview.setVerticalScrollBarEnabled(false);

		if (fromWebViewFlag) {
			if (url.contains("?")) {
				url = url + "&serviceTime="
						+ ValidationService.getServiceTime();
			} else {
				url = url + "?serviceTime="
						+ ValidationService.getServiceTime();
			}
			// url = url + "?serviceTime=" +ValidationService.getServiceTime();
		}
		webview.loadUrl(url);

		webview.setWebChromeClient(new WebChromeClient() {
			// 设置网页加载的进度条
			public void onProgressChanged(WebView view, int newProgress) {

				if (newProgress == 50) {
					Log.i("tag", "load......50%");
				}

				if (newProgress == 100) {
					// hideProgressBar();
					hiddenProgress();
				}
				super.onProgressChanged(view, newProgress);
			}

			// 设置应用程序的标题
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
			}
		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (webview != null) {
			if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
				webview.goBack();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume() {
		super.onResume();
	
		String userType = TradeUser.getInstance().getUserType();
		if("serv".equals(userType)) 
			initToolBar(Global.BAR_IMAGE_1, Global.BAR_TAG_2);
		else
			initToolBar(Global.BAR_IMAGE_2, Global.BAR_TAG);
	}

	public class JHMethodImpl extends JHMethod {
		public JHMethodImpl(Handler mHandler, Activity context, WebView webview) {
			super(mHandler, context, webview);
		}

		@Override
		public void showMenuImpl() {
			openMenu();
		}

		@Override
		public void setFundRiskLevel(String risk_level, String zy) {
			try {
				JSONObject quoteData = FundService.setFundRiskLevel(risk_level,
						zy);
				String res = TradeUtil.checkResult(quoteData);
				if (res != null) {
					if (res.equals("-1")) {
						Toast.makeText(WebViewDisplay.this,
								"网络连接异常！请检查您的网络是否可用。", Toast.LENGTH_LONG)
								.show();
					} else {
						toast(res);
					}
					return;
				} else {
					// toast("风险等级设置成功!");
					TradeUser.getInstance().setRiskLevel(risk_level);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private class MyWebChromeClient extends WebChromeClient {
		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				final JsResult result) {
			AlertDialog.Builder b = new AlertDialog.Builder(WebViewDisplay.this);
			b.setTitle("Alert");
			b.setMessage(message);
			b.setPositiveButton(android.R.string.ok,
					new AlertDialog.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							result.confirm();
						}
					});
			b.setCancelable(false);
			b.create();
			b.show();
			return true;
		};

		@Override
		public boolean onJsConfirm(WebView view, String url, String message,
				final JsResult result) {
			AlertDialog.Builder b = new AlertDialog.Builder(WebViewDisplay.this);
			b.setTitle("Confirm");
			b.setMessage(message);
			b.setPositiveButton(android.R.string.ok,
					new AlertDialog.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							result.confirm();
						}
					});
			b.setNegativeButton(android.R.string.cancel,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							result.cancel();
						}
					});
			b.setCancelable(false);
			b.create();
			b.show();
			return true;
		};
	}
}

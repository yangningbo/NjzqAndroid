package com.cssweb.android.user.track;

import java.util.HashMap;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import com.cssweb.android.common.Base64Encoder;
import com.cssweb.android.connect.Conn;

/**
 * 用pc模型处理获得的Activity的数据 producer/consumer
 * 
 * @author oyqx
 * 
 */
public class HandleActivity {
    private static Timer timer;

    private static TimerTask task;


    private static Context context;

    private static Stack<ComponentName> activityStack;

    private static Boolean isSame;

    private static String url;

    private static String jsobString;

    private static String urlsourcename;

    private static String ram;

    private static String key;// 服务器返回key

    private static HashMap<String, String> hashmap;// 存数栏目访问状态

    private static UserTrackUrlBean urlBean;

    public static final String TRACKPATH = "http://172.16.1.24:8080/user_track?URL=";

    private static String classname;

    public static void handleStack() {
        System.out.println("."
                + new Exception().getStackTrace()[0].getMethodName() + "()");
        initMap();
        activityStack = new Stack<ComponentName>();
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if ((Gloable.getInstance().getIsLock()==false)&(Gloable.getInstance().getIsHome()==false)) {
                    // 内部线程运行5秒
                    pushStack();
                    if (compareStack()) { 
                        System.out.println("相同,更新状态,当前opera==>"
                                + Gloable.getInstance().getOpera());
                        sendUrl();
                    } else {
                        System.out.println("不同,发送地址,当前opera==>"
                                + Gloable.getInstance().getOpera());

                        sendUrl();
                    }
                    System.out
                            .println("======================================一次自动发送结束==========================================================");
                } else if(Gloable.getInstance().getIsLock()==true){
                    System.out
                            .println("屏幕关闭,服务暂停发送信息,屏幕关闭,服务暂停发送信息,屏幕关闭,服务暂停发送信息,屏幕关闭,服务暂停发送信息,屏幕关闭,服务暂停发送信息,屏幕关闭,服务暂停发送信息,屏幕关闭,服务暂停发送信息,");
                }else if(Gloable.getInstance().getIsHome()==true) {
                    System.out.println("切回主页了,服务暂停.切回主页了,服务暂停切回主页了,服务暂停切回主页了,服务暂停切回主页了,服务暂停切回主页了,服务暂停切回主页了,服务暂停切回主页了,服务暂停切回主页了,服务暂停切回主页了,服务暂停");
                    
                }
            }

            /**
             * 发送地址
             */
            private void sendUrl() {
                System.out.println(this.getClass().getName() + "."
                        + new Exception().getStackTrace()[0].getMethodName()
                        + "()");
                System.out.println(hashmap);
                handleUrlData(); 
                System.out.println(" sendUrl==>opera======>"
                        + Gloable.getInstance().getOpera());
                System.out.println(" sendUrl==>key========>"
                        + Gloable.getInstance().getKey());
                if (url != null) {
                    jsobString = Conn.execute(url) + "";
                    Gloable.getInstance().setJsonString(jsobString);
                    System.out.println("sendUrl===>服务器返回===>" + jsobString);
                    /*
                     * 返回 {"cssweb_code":"success","cssweb_type":"track","key":
                     * "24.RENEW.BS-20110601-13","cssweb_msg":""}
                     */
                } else {
                    System.out.println("发送失败了!");
                }

            }

            public String handleUrlData() {

                urlBean = UserTrackUrlBean.getInstance();

                urlBean.setCustID(UrlParams.setCustID());// 客户代码
                urlBean.setUrlID(UrlParams.setUrlID());// 当前访问的栏目代码
                urlBean.setOrgID(UrlParams.setOrgID());// 营业部编码
                urlBean.setOrgDesc(UrlParams.setOrgDesc());// 营业部名称
                urlBean.setUserType(UrlParams.setUserType());// 用户类型----------------------
                urlBean.setUserLevel(UrlParams.setUserLevel());// 用户等级
                urlBean.setRealName(UrlParams.setRealName());// 真实姓名
                urlBean.setSystemCode(UrlParams.setSystemCode());// 非空,//
                                                                 // 进行分析的系统编码
                urlBean.setSystemVer(UrlParams.setSystemVer());// 进行分析的系统版本
                urlBean.setSessionId(UrlParams.setSessionId());// 此次回话的ID
                urlBean.setHits(UrlParams.setHits());// 页面点击数

                urlBean.setOpera(UrlParams.setOpera());// 此次访问的操作类型

                // urlBean.setKey("23423");// 服务器端的唯一标识符
                urlBean.setTerminaltype(UrlParams.setTerminaltype());// 访问终端类型----------------
                urlBean.setVisitorID(UrlParams.setVisitorID());// 标示浏览用户身份的ID
                urlBean.setoS(UrlParams.setOS());// 操作系统及版本
                urlBean.setiSP(UrlParams.setISP());// 网络运营商----------------------
                urlBean.setNetworkType(UrlParams.setNetworkType());// 联网方式------------------
                urlBean.setResolu(UrlParams.setResolu());// 客户端分辨率-------------------
                urlBean.setOSCharacter(UrlParams.setOSCharacter());// 客户端语言---------------
                urlBean.setChannel(UrlParams.setChannel());// apk来源----------------------

                // http://172.16.1.24:8080/user_track?URL=Y3VzdElEPTEzMDQ5OTM4ODMxMDkmc3lzdGVtQ29kZT1XRUJTSVRFJnVybElEPXd0eXl0X3d0Z2cmb3JnSUQ9JmZ1bmRJRD0mZnVuZFR5cGU9JnVzZXJUeXBlPTMmcmVhbE5hbWU9JnNlc3Npb25JRD1YSVk1RzdLVlYxQTZNV0o1VVg3NkRLTFNIMkNDTUhONUEyQjE0SVBQNFVNU0JCQjFXQ0YzMTMwNDk5NDMzNTM3Ng
                // ==&urlSourceName=aHR0cDovLzU4LjIxMy4xMTMuMTExOjgwMDEvaXBob25lL2hhbGwvaW5kZXguanNwP3Vhbj13dHl5dF9zeQ
                // ==&currentURL=aHR0cDovLzU4LjIxMy4xMTMuMTExOjgwMDEvaXBob25lL2hhbGwvb2ZmQW5ub3VuY2VtZW50LmpzcD91YW49d3R5eXRfd3RnZw
                // ==&ram=3289.107436316079
                // URL非空
                if (Gloable.getInstance().getLastUid() == null) {
                    urlsourcename = ".splash";
                } else {
                    urlsourcename = UrlParams.setLastUID();
                }

                key = UrlParams.setKey();
                ram = Math.random() + "";
                try {

                    url = TRACKPATH
                            + Base64Encoder
                                    .encode(urlBean.toString(), "gb2312")
                            + "&urlSourceName="
                            + Base64Encoder.encode(urlsourcename, "gb2312")
                            + "&key=" + key + "&ram=" + ram;
                } catch (Exception e) {
                    // System.err.println(e);
                    e.printStackTrace();
                }
                return url;

            }

            /**
             * 存stack
             */
            private void pushStack() {
                System.out.println(this.getClass().getName() + "."
                        + new Exception().getStackTrace()[0].getMethodName()
                        + "()");
                
                for (int i = 0; i < 2; i++) {
                    context = CssApplication.getInstance();
                    ActivityManager am = (ActivityManager) context
                            .getSystemService(Context.ACTIVITY_SERVICE);
                    ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
                    classname = cn.getShortClassName();

                    System.out.println("short name===>查找并更新map==>" + classname);
                   
                    //判断是否切回主页
                    if(classname.contains("com.android")) {
                        System.out.println("有com===================================");
                        Gloable.getInstance().setIsHome(true);
                    }else {
                        System.out.println("无com.android===================================");
                        Gloable.getInstance().setIsHome(false);
                    }
                    // if (hashmap.get(classname) != null) {
                    // if (hashmap.get(classname) == "0")
                    // {//第一次检测map的value,如果value="0"发送一次,
                    // //获取key作为新的value输入map,并把opera设置为"1"
                    // Gloable.getInstance().setOpera("0");
                    // getKeyFromServer();
                    // } else if (!(hashmap.get(classname).equals("0"))) {
                    // Gloable.getInstance().setOpera("1");
                    // }
                    // }
                    if (hashmap.get(classname) != null) {
                        String urlkey = "";
                        if (hashmap.get(classname) == "") {// 无key
                            System.out
                                    .println("无key无key无key无key无key无key无key无key无key无key无key无key无key无key无key无key无key无key无key无key无key无key无key无key");

                            Gloable.getInstance().setOpera("0");
                            Gloable.getInstance().setKey("");
                            urlkey = getKeyFromServer();
                            hashmap.put(classname, urlkey);

                        } else {// 有key
                            // hashmap.get(classname);
                            System.out
                                    .println("有key有key有key有key有key有key有key有key有key有key有key有key有key有key有key有key有key有key有key有key有key有key有key有key");
                            Gloable.getInstance()
                                    .setKey(hashmap.get(classname));
                            Gloable.getInstance().setOpera("1");
                        }
                    }
                    activityStack.push(cn);
                    // 这一步单独处理key的问题,先取MAP判断有无对应的key
                    // 有则key设为global的key,并且url的opera设置为"1" ,无:opera设置为"0"发送一次url
                    //
                    try {
                        Thread.sleep(4999);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

            private String getKeyFromServer() {
                System.out.println(this.getClass().getName() + "."
                        + new Exception().getStackTrace()[0].getMethodName());
                String urlkeyString = "";
                handleUrlData();

                sendUrl();
                try {
                    if (Gloable.getInstance().getJsonString() != null) {
                        JSONObject jb = new JSONObject(Gloable.getInstance()
                                .getJsonString());
                        if (jb.getString("key") != null) {
                            urlkeyString = jb.getString("key");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("手动发送一次url===>" + "oprea="
                        + Gloable.getInstance().getOpera() + "=====" + "UID="
                        + classname + "===" + "key="
                        + Gloable.getInstance().getKey());
                System.out
                        .println("==============================一次手动发送结束===========================================");
                return urlkeyString;
            }

            /**
             * 比较栈顶
             * 
             * @return
             */
            private boolean compareStack() {
                System.out.println(this.getClass().getName() + "."
                        + new Exception().getStackTrace()[0].getMethodName()
                        + "()");
                if (activityStack.peek().equals(activityStack.get(0))) {
                    Gloable.getInstance().setLastUid(
                            activityStack.get(0).getShortClassName());
                    activityStack.removeAllElements();// 清空
                    isSame = true;

                } else {
                    System.out.println("比较不同设置lastuid"
                            + activityStack.get(0).getShortClassName());
                    Gloable.getInstance().setLastUid(
                            activityStack.get(0).getShortClassName());
                    activityStack.removeAllElements();// 清空
                    isSame = false;
                }

                return isSame;
            }
        };
        timer.scheduleAtFixedRate(task, 0, 10000);// 10秒执行一次
    }

    private static void initMap() {
        hashmap = new HashMap<String, String>();
        hashmap.put(".MainActivity", "");
        hashmap.put(".JlpActivity", "");

        hashmap.put(".RestartDialog", "");
        hashmap.put(".TranslucentMenuActivity", "");
        hashmap.put("com.cssweb.android.sms.SMSJHActivity", "");
        hashmap.put("com.cssweb.android.quote.SQS", "");
        hashmap.put("com.cssweb.android.quote.DSS", "");
        hashmap.put("com.cssweb.android.quote.ZSS", "");
        hashmap.put("com.cssweb.android.quote.ZJS", "");
        hashmap.put("com.cssweb.android.quote.QQSP", "");
        hashmap.put("com.cssweb.android.quote.QHSCGridActivity", "");
        hashmap.put("com.cssweb.android.quote.QHSCBaseActivity", "");
        hashmap.put("com.cssweb.android.quote.HSZS", "");
        hashmap.put("com.cssweb.android.quote.GlobalMarketActivity", "");
        hashmap.put("com.cssweb.android.quote.OTCFundActivity", "");
        hashmap.put("com.cssweb.android.quote.PaiMing", "");
        hashmap.put("com.cssweb.android.quote.DaPan", "");
        hashmap.put("com.cssweb.android.quote.FenLei", "");
        hashmap.put("com.cssweb.android.quote.QueryStock", "");
        hashmap.put("com.cssweb.android.quote.PersonalStock", "");
        hashmap.put("com.cssweb.android.quote.QuoteAlarm", "");
        hashmap.put("com.cssweb.android.quote.TrendActivity", "");
        hashmap.put("com.cssweb.android.quote.KLineActivity", "");
        hashmap.put("com.cssweb.android.quote.KLine2Activity", "");
        hashmap.put("com.cssweb.android.quote.QuotePrice", "");
        hashmap.put("com.cssweb.android.quote.QuoteDetail", "");
        hashmap.put("com.cssweb.android.quote.GetF10List", "");
        hashmap.put("com.cssweb.android.quote.GetF10Content", "");
        hashmap.put("com.cssweb.android.quote.GlobalMarket", "");
        hashmap.put("com.cssweb.android.quote.FLineActivity", "");
        hashmap.put("com.cssweb.android.quote.StockTypeFund", "");
        hashmap.put("com.cssweb.android.quote.FundQueryCondition", "");
        hashmap.put("com.cssweb.android.quote.FundQuery", "");
        hashmap.put("com.cssweb.android.quote.Sunpublic", "");
        hashmap.put("com.cssweb.android.quote.JingZhiQuery", "");
        hashmap.put("com.cssweb.android.quote.QuoteWarning", "");
        hashmap.put("com.cssweb.android.quote.QuoteSet", "");
        hashmap.put("com.cssweb.android.quote.QHHQActivity", "");
        hashmap.put("com.cssweb.android.quote.GGHQActivity", "");
        hashmap.put("com.cssweb.android.quote.HKMainboard", "");
        hashmap.put("com.cssweb.android.quote.GlobalHuiShi", "");
        hashmap.put("com.cssweb.android.quote.SunpublicQueryCondition", "");
        hashmap.put("com.cssweb.android.web.OpenHrefDisplay", "");
        hashmap.put("com.cssweb.android.web.WebViewDisplay", "");
        hashmap.put("com.cssweb.android.web.JxgpcActivity", "");
        hashmap.put("com.cssweb.android.web.CfpdActivity", "");
        hashmap.put("com.cssweb.android.web.OpenPdfDisplay", "");
        hashmap.put("com.cssweb.android.fzjy.VistualTrade", "");
        hashmap.put("com.cssweb.android.sz.ClearActivity", "");
        hashmap.put("com.cssweb.android.sz.Setting", "");
        hashmap.put("com.cssweb.android.sz.HelpActivity", "");
        hashmap.put("com.cssweb.android.sz.CustomSettingActivity", "");
        hashmap.put("com.cssweb.android.sz.HQRefreshSettingActivity", "");
        hashmap.put("com.cssweb.android.sz.ServerSettingActivity", "");
        hashmap.put("com.cssweb.android.sz.LockSettingActivity", "");
        hashmap.put("com.cssweb.android.tyyh.ExperienceUsers", "");
        hashmap.put("com.cssweb.android.video.CustomMediaPlayer", "");
        hashmap.put("com.cssweb.android.video.VideoPlayer", "");
        hashmap.put("com.cssweb.android.trade.CnjjActivity", "");
        hashmap.put("com.cssweb.android.trade.FundActivity", "");
        hashmap.put("com.cssweb.android.trade.BankActivity", "");
        hashmap.put("com.cssweb.android.trade.TransferFundsActivity", "");
        hashmap.put("com.cssweb.android.trade.user.AccountManage", "");
        hashmap.put("com.cssweb.android.trade.stock.GetDetails", "");
        hashmap.put("com.cssweb.android.trade.stock.GetDetailsH", "");
        hashmap.put("com.cssweb.android.trade.stock.StockTrading", "");
        hashmap.put("com.cssweb.android.trade.stock.GetPosition", "");
        hashmap.put("com.cssweb.android.trade.stock.StockCancel", "");
        hashmap.put("com.cssweb.android.trade.stock.AssetQuery", "");
        hashmap.put("com.cssweb.android.trade.stock.TodayEntrust", "");
        hashmap.put("com.cssweb.android.trade.stock.TodayDeal", "");
        hashmap.put("com.cssweb.android.trade.stock.ExchangeFund", "");
        hashmap.put("com.cssweb.android.trade.stock.StockWarrant", "");
        hashmap.put("com.cssweb.android.trade.stock.StockWarrant", "");
        hashmap.put("com.cssweb.android.util.DateRange", "");
        hashmap.put("com.cssweb.android.trade.stock.HistoryEntrust", "");
        hashmap.put("com.cssweb.android.trade.stock.HistoryDeal", "");
        hashmap.put("com.cssweb.android.trade.stock.Bill", "");
        hashmap.put("com.cssweb.android.trade.stock.NewStockMatch", "");
        hashmap.put("com.cssweb.android.trade.stock.ModifyPassword", "");
        hashmap.put("com.cssweb.android.trade.stock.StockList", "");
        hashmap.put("com.cssweb.android.trade.stock.ShareholderList", "");
        hashmap.put("com.cssweb.android.trade.login.LoginActivity", "");
        hashmap.put("com.cssweb.android.trade.bank.TransferQuery", "");
        hashmap.put("com.cssweb.android.trade.bank.BankBalanceQuery", "");
        hashmap.put("com.cssweb.android.trade.bank.BankFundTransfer", "");
        hashmap.put("com.cssweb.android.trade.bank.FundBankTransfer", "");
        hashmap.put("com.cssweb.android.trade.bank.TransferDateRange", "");
        hashmap.put("com.cssweb.android.trade.transferFunds.FundsDetails", "");
        hashmap.put("com.cssweb.android.trade.transferFunds.ZfTransfer", "");
        hashmap.put(
                "com.cssweb.android.trade.transferFunds.TransferFundsDateRange",
                "");
        hashmap.put(
                "com.cssweb.android.trade.transferFunds.TransferFundsQuery", "");
        hashmap.put("com.cssweb.android.trade.stock.ModifyContactInfo", "");
        hashmap.put("com.cssweb.android.trade.fund.FundTrading", "");
        hashmap.put("com.cssweb.android.trade.fund.TodayTrust", "");
        hashmap.put("com.cssweb.android.trade.fund.FundTransfer", "");
        hashmap.put("com.cssweb.android.trade.fund.FundMelonSet", "");
        hashmap.put("com.cssweb.android.trade.fund.FundPortio", "");
        hashmap.put("com.cssweb.android.trade.fund.FundAccount", "");
        hashmap.put("com.cssweb.android.trade.fund.FundRiskTest", "");
        hashmap.put("com.cssweb.android.trade.fund.HistoryConclusion", "");
        hashmap.put("com.cssweb.android.trade.fund.HistoryTrust", "");
        hashmap.put("com.cssweb.android.trade.fund.FundCompany", "");
        hashmap.put("com.cssweb.android.trade.fund.FundAccountForm", "");
    }

}

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

    private static HashMap<String, Integer> hashmap;// 存数栏目访问状态

    public static final String TRACKPATH = "http://172.16.1.24:8080/user_track?URL=";

    public static void handleStack() {
        System.out.println("."
                + new Exception().getStackTrace()[0].getMethodName() + "()");
        initMap();
        activityStack = new Stack<ComponentName>();
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
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

            }

            /**
             * 发送地址
             */
            private void sendUrl() {
                System.out.println(this.getClass().getName() + "."
                        + new Exception().getStackTrace()[0].getMethodName()
                        + "()");
                handleUrlData();
                if (url != null) {
                    System.out.println(new Exception().getStackTrace()[0]
                            .getMethodName() + "url报错可能=====>" + url);
                    jsobString = Conn.execute(url) + "";
                    // Gloable.getInstance().setJsonString(jsobString);
                    saveKey2Map();
                    System.out.println("服务器返回" + jsobString);
                    /*
                     * 返回 {"cssweb_code":"success","cssweb_type":"track","key":
                     * "24.RENEW.BS-20110601-13","cssweb_msg":""}
                     */
                }

            }

            private String handleUrlData() {

                UserTrackUrlBean urlBean = UserTrackUrlBean.getInstance();

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

                urlsourcename = UrlParams.setUrlID();

                key = UrlParams.setKey();
                ram = Math.random() + "";
                System.out.println("栏目地址==>" + urlBean.toString());
                System.out.println("上一个栏目-->" + urlsourcename);
                System.out.println("key===>" + key);
                System.out.println("ram ======>" + ram);

                try {
                    url = TRACKPATH
                            + Base64Encoder
                                    .encode(urlBean.toString(), "gb2312")
                            + "&urlSourceName="
                            + Base64Encoder.encode(UrlParams.setLastUID(),
                                    "gb2312") + "&key=" + key + "&ram=" + ram;
                } catch (Exception e) {
                    // TODO: handle exception
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
                    String classname = cn.getShortClassName();

                    System.out.println("short name===>查找并更新map==>" + classname);
                    // @SuppressWarnings("rawtypes")
                    // Iterator iter = hashmap.entrySet().iterator();
                    // while (iter.hasNext()) {
                    // @SuppressWarnings({ "rawtypes", "unchecked" })
                    // Map.Entry<String, Integer> entry = (Map.Entry)
                    // iter.next();
                    // String key = (String) entry.getKey();
                    // Integer val = (Integer) entry.getValue();
                    // if(cn.getShortClassName().equals(key)) {
                    // entry.setValue(val+1);
                    // }
                    // }
                    if (hashmap.get(classname) != null) {
                        if (hashmap.get(classname) == 0) {
                            Gloable.getInstance().setOpera("0");
                            try {
                                if (Gloable.getInstance().getJsonString() != null) {
                                    JSONObject jb = new JSONObject(Gloable
                                            .getInstance().getJsonString());
                                    if (!jb.getString("key").equals("")) {
                                        Gloable.getInstance().setKey(
                                                jb.getString("key"));
                                    }
                                }
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            // hashmap.put(cn.getShortClassName(), 1);
                            // 直接把获得的key给进去,根据key有无设置opera

                        } else if (!(hashmap.get(classname).equals("0"))) {
                            Gloable.getInstance().setOpera("1");
                        }
                    }
                    activityStack.push(cn);
                    try {
                        Thread.sleep(4999);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

            // /**
            // * 是否在线
            // * @return
            // */
            // private boolean isOnline() {
            // Boolean isonline=false;
            // if(compareStack()) {
            // isonline=true;
            // }
            // else {
            // isonline=false;
            // }
            // return isonline;
            // }

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

    protected static void saveKey2Map() {
        if (UserTrackUrlBean.getInstance().getOpera().equals("0")) {

        }
    }

    private static void initMap() {
        hashmap = new HashMap<String, Integer>();
        hashmap.put(".MainActivity", 1);
        hashmap.put(".RestartDialog", 0);
        hashmap.put(".TranslucentMenuActivity", 0);
        hashmap.put("com.cssweb.android.sms.SMSJHActivity", 0);
        hashmap.put("com.cssweb.android.quote.SQS", 0);
        hashmap.put("com.cssweb.android.quote.DSS", 0);
        hashmap.put("com.cssweb.android.quote.ZSS", 0);
        hashmap.put("com.cssweb.android.quote.ZJS", 0);
        hashmap.put("com.cssweb.android.quote.QQSP", 0);
        hashmap.put("com.cssweb.android.quote.QHSCGridActivity", 0);
        hashmap.put("com.cssweb.android.quote.QHSCBaseActivity", 0);
        hashmap.put("com.cssweb.android.quote.HSZS", 0);
        hashmap.put("com.cssweb.android.quote.GlobalMarketActivity", 0);
        hashmap.put("com.cssweb.android.quote.OTCFundActivity", 0);
        hashmap.put("com.cssweb.android.quote.PaiMing", 0);
        hashmap.put("com.cssweb.android.quote.DaPan", 0);
        hashmap.put("com.cssweb.android.quote.FenLei", 0);
        hashmap.put("com.cssweb.android.quote.QueryStock", 0);
        hashmap.put("com.cssweb.android.quote.PersonalStock", 0);
        hashmap.put("com.cssweb.android.quote.QuoteAlarm", 0);
        hashmap.put("com.cssweb.android.quote.TrendActivity", 0);
        hashmap.put("com.cssweb.android.quote.KLineActivity", 0);
        hashmap.put("com.cssweb.android.quote.KLine2Activity", 0);
        hashmap.put("com.cssweb.android.quote.QuotePrice", 0);
        hashmap.put("com.cssweb.android.quote.QuoteDetail", 0);
        hashmap.put("com.cssweb.android.quote.GetF10List", 0);
        hashmap.put("com.cssweb.android.quote.GetF10Content", 0);
        hashmap.put("com.cssweb.android.quote.GlobalMarket", 0);
        hashmap.put("com.cssweb.android.quote.FLineActivity", 0);
        hashmap.put("com.cssweb.android.quote.StockTypeFund", 0);
        hashmap.put("com.cssweb.android.quote.FundQueryCondition", 0);
        hashmap.put("com.cssweb.android.quote.FundQuery", 0);
        hashmap.put("com.cssweb.android.quote.Sunpublic", 0);
        hashmap.put("com.cssweb.android.quote.JingZhiQuery", 0);
        hashmap.put("com.cssweb.android.quote.QuoteWarning", 0);
        hashmap.put("com.cssweb.android.quote.QuoteSet", 0);
        hashmap.put("com.cssweb.android.quote.QHHQActivity", 0);
        hashmap.put("com.cssweb.android.quote.GGHQActivity", 0);
        hashmap.put("com.cssweb.android.quote.HKMainboard", 0);
        hashmap.put("com.cssweb.android.quote.GlobalHuiShi", 0);
        hashmap.put("com.cssweb.android.quote.SunpublicQueryCondition", 0);
        hashmap.put("com.cssweb.android.web.OpenHrefDisplay", 0);
        hashmap.put("com.cssweb.android.web.WebViewDisplay", 0);
        hashmap.put("com.cssweb.android.web.JxgpcActivity", 0);
        hashmap.put("com.cssweb.android.web.CfpdActivity", 0);
        hashmap.put("com.cssweb.android.web.OpenPdfDisplay", 0);
        hashmap.put("com.cssweb.android.fzjy.VistualTrade", 0);
        hashmap.put("com.cssweb.android.sz.ClearActivity", 0);
        hashmap.put("com.cssweb.android.sz.Setting", 0);
        hashmap.put("com.cssweb.android.sz.HelpActivity", 0);
        hashmap.put("com.cssweb.android.sz.CustomSettingActivity", 0);
        hashmap.put("com.cssweb.android.sz.HQRefreshSettingActivity", 0);
        hashmap.put("com.cssweb.android.sz.ServerSettingActivity", 0);
        hashmap.put("com.cssweb.android.sz.LockSettingActivity", 0);
        hashmap.put("com.cssweb.android.tyyh.ExperienceUsers", 0);
        hashmap.put("com.cssweb.android.video.CustomMediaPlayer", 0);
        hashmap.put("com.cssweb.android.video.VideoPlayer", 0);
        hashmap.put("com.cssweb.android.trade.CnjjActivity", 0);
        hashmap.put("com.cssweb.android.trade.FundActivity", 0);
        hashmap.put("com.cssweb.android.trade.BankActivity", 0);
        hashmap.put("com.cssweb.android.trade.TransferFundsActivity", 0);
        hashmap.put("com.cssweb.android.trade.user.AccountManage", 0);
        hashmap.put("com.cssweb.android.trade.stock.GetDetails", 0);
        hashmap.put("com.cssweb.android.trade.stock.GetDetailsH", 0);
        hashmap.put("com.cssweb.android.trade.stock.StockTrading", 0);
        hashmap.put("com.cssweb.android.trade.stock.GetPosition", 0);
        hashmap.put("com.cssweb.android.trade.stock.StockCancel", 0);
        hashmap.put("com.cssweb.android.trade.stock.AssetQuery", 0);
        hashmap.put("com.cssweb.android.trade.stock.TodayEntrust", 0);
        hashmap.put("com.cssweb.android.trade.stock.TodayDeal", 0);
        hashmap.put("com.cssweb.android.trade.stock.ExchangeFund", 0);
        hashmap.put("com.cssweb.android.trade.stock.StockWarrant", 0);
        hashmap.put("com.cssweb.android.trade.stock.StockWarrant", 0);
        hashmap.put("com.cssweb.android.util.DateRange", 0);
        hashmap.put("com.cssweb.android.trade.stock.HistoryEntrust", 0);
        hashmap.put("com.cssweb.android.trade.stock.HistoryDeal", 0);
        hashmap.put("com.cssweb.android.trade.stock.Bill", 0);
        hashmap.put("com.cssweb.android.trade.stock.NewStockMatch", 0);
        hashmap.put("com.cssweb.android.trade.stock.ModifyPassword", 0);
        hashmap.put("com.cssweb.android.trade.stock.StockList", 0);
        hashmap.put("com.cssweb.android.trade.stock.ShareholderList", 0);
        hashmap.put("com.cssweb.android.trade.login.LoginActivity", 0);
        hashmap.put("com.cssweb.android.trade.bank.TransferQuery", 0);
        hashmap.put("com.cssweb.android.trade.bank.BankBalanceQuery", 0);
        hashmap.put("com.cssweb.android.trade.bank.BankFundTransfer", 0);
        hashmap.put("com.cssweb.android.trade.bank.FundBankTransfer", 0);
        hashmap.put("com.cssweb.android.trade.bank.TransferDateRange", 0);
        hashmap.put("com.cssweb.android.trade.transferFunds.FundsDetails", 0);
        hashmap.put("com.cssweb.android.trade.transferFunds.ZfTransfer", 0);
        hashmap.put(
                "com.cssweb.android.trade.transferFunds.TransferFundsDateRange",
                0);
        hashmap.put(
                "com.cssweb.android.trade.transferFunds.TransferFundsQuery", 0);
        hashmap.put("com.cssweb.android.trade.stock.ModifyContactInfo", 0);
        hashmap.put("com.cssweb.android.trade.fund.FundTrading", 0);
        hashmap.put("com.cssweb.android.trade.fund.TodayTrust", 0);
        hashmap.put("com.cssweb.android.trade.fund.FundTransfer", 0);
        hashmap.put("com.cssweb.android.trade.fund.FundMelonSet", 0);
        hashmap.put("com.cssweb.android.trade.fund.FundPortio", 0);
        hashmap.put("com.cssweb.android.trade.fund.FundAccount", 0);
        hashmap.put("com.cssweb.android.trade.fund.FundRiskTest", 0);
        hashmap.put("com.cssweb.android.trade.fund.HistoryConclusion", 0);
        hashmap.put("com.cssweb.android.trade.fund.HistoryTrust", 0);
        hashmap.put("com.cssweb.android.trade.fund.FundCompany", 0);
        hashmap.put("com.cssweb.android.trade.fund.FundAccountForm", 0);
    }
}

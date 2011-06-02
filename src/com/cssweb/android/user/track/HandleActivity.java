package com.cssweb.android.user.track;

import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import com.cssweb.android.common.Base64Encoder;
import com.cssweb.android.connect.Conn;
import com.cssweb.android.session.TradeUser;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

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

    public static final String TRACKPATH = "http://172.16.1.24:8080/user_track?URL=";

    public static void handleStack() {
        System.out.println("."
                + new Exception().getStackTrace()[0].getMethodName() + "()");
        activityStack = new Stack<ComponentName>();
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                // 内部线程运行5秒
                pushStack();
                if (compareStack()) {
                    System.out.println("do nothing");
                    System.out.println("logintype"+TradeUser.getInstance().getLoginType());
                } else {
                    System.out.println("send url");
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
                // Intent i=new Intent();
                // i.setComponent(activityStack.peek());
                // context.startActivity(i);
                // String url=context.//获取context的参数
  //=============>              handleUrlData();
//                System.out.println(url);
//                if (url != null) {
//                    jsobString = Conn.execute(url).toString();
//                    System.out.println(jsobString);
//                    /*
//                     * 返回
//                     * {"cssweb_code":"success","cssweb_type":"track","key":
//                     * "24.RENEW.BS-20110601-13","cssweb_msg":""}
//                     */
//                }

            }

            private String handleUrlData() {

                UserTrackUrlBean urlBean = UserTrackUrlBean.getInstance();
                
                urlBean.setCustID("2222");// 客户代码
                urlBean.setUrlID("3333");// 当前访问的栏目代码
                urlBean.setOrgID("2");// 营业部编码
                urlBean.setOrgDesc("233");// 营业部名称
                urlBean.setUserType("2");// 用户类型----------------------
                urlBean.setUserLevel("3");// 用户等级
                urlBean.setRealName("wudilaoda");// 真实姓名
                urlBean.setSystemCode("1");// 非空,// 进行分析的系统编码
                urlBean.setSystemVer("android");// 进行分析的系统版本
                urlBean.setSessionId("asdfasd");// 此次回话的ID
                urlBean.setHits("3");// 页面点击数
                urlBean.setOpera("asdfa");// 此次访问的操作类型
                // urlBean.setKey("23423");// 服务器端的唯一标识符
                urlBean.setTerminaltype("1");// 访问终端类型----------------
                urlBean.setVisitorID("2312");// 标示浏览用户身份的ID
                urlBean.setoS("android");// 操作系统及版本
                urlBean.setiSP("233423");// 网络运营商----------------------
                urlBean.setNetworkType("wifi");// 联网方式------------------
                urlBean.setResolu("null");// 客户端分辨率-------------------
                urlBean.setOSCharacter("yingyu");//客户端语言---------------
                urlBean.setChannel("market");//apk来源----------------------

                // http://172.16.1.24:8080/user_track?URL=Y3VzdElEPTEzMDQ5OTM4ODMxMDkmc3lzdGVtQ29kZT1XRUJTSVRFJnVybElEPXd0eXl0X3d0Z2cmb3JnSUQ9JmZ1bmRJRD0mZnVuZFR5cGU9JnVzZXJUeXBlPTMmcmVhbE5hbWU9JnNlc3Npb25JRD1YSVk1RzdLVlYxQTZNV0o1VVg3NkRLTFNIMkNDTUhONUEyQjE0SVBQNFVNU0JCQjFXQ0YzMTMwNDk5NDMzNTM3Ng
                // ==&urlSourceName=aHR0cDovLzU4LjIxMy4xMTMuMTExOjgwMDEvaXBob25lL2hhbGwvaW5kZXguanNwP3Vhbj13dHl5dF9zeQ
                // ==&currentURL=aHR0cDovLzU4LjIxMy4xMTMuMTExOjgwMDEvaXBob25lL2hhbGwvb2ZmQW5ub3VuY2VtZW50LmpzcD91YW49d3R5eXRfd3RnZw
                // ==&ram=3289.107436316079
                // URL非空
                urlsourcename = "NJZQ_MNCG";
                ram = "";

                try {
                    url = TRACKPATH
                            + Base64Encoder
                                    .encode(urlBean.toString(), "gb2312")
                            + "==&urlSourceName="
                            + Base64Encoder.encode(urlsourcename, "gb2312")
                            + "==&ram=" + ram;
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
                    System.out.println("short name===>"+cn.getShortClassName());
                    activityStack.push(cn);
                    try {
                        Thread.sleep(4999);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

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

                    activityStack.removeAllElements();// 清空
                    isSame = true;

                } else {

                    activityStack.removeAllElements();// 清空
                    isSame = false;
                }

                return isSame;
            }
        };
        timer.scheduleAtFixedRate(task, 0, 10000);// 10秒执行一次
    }
}

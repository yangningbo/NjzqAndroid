package com.cssweb.android.user.track;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

public class UrlParams {
    private static Context context;

    /**
     * 客户代码。交易用户，传值：客户号（不支持客户号，传资金帐号）  体验用户，传值：体验卡号  浏览用户，传值：唯一值 
     * 注册用户，传值：用户ID或其它唯一标识符（比如：昵称）  内部用户，传值：员工号
     * 
     * 注： 浏览用户客户代码：  移动终端使用设备编号；
     * 
     * @return 客户代码
     */
    @SuppressWarnings("unused")
    private static String setCustID() {
        String cusid = null;
        // if(//浏览用户) {
        // cusid=//获取设备编号
        // }else if(交易用户) {
        // cusid=//客户号
        // }else if(体验用户) {
        // cusid= //体验卡号
        // }else if(注册用户) {
        // cusid=//用户ID
        // }else if(内部用户) {
        // cusid=//员工号
        // }
        return cusid;
    }

    /**
     * 根据包名不同获得Activity
     * 
     * @return 当前访问的栏目代码
     */
    @SuppressWarnings("unused")
    private static String setUrlID() {
        String urlID = null;
        String classname = null;
        context = CssApplication.getInstance();
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        classname = cn.getShortClassName();
        if (classname.equals(".MainActivity")) {
            urlID = "njzq_main";
        } else if (classname.equals(".JlpActivity")) {
            urlID = "njzq_jinluopan";
        } else if (classname.equals(".RestartDialog")) {
            urlID = "njzq_jinluopan";
        } else if (classname.equals(".TranslucentMenuActivity")) {
            urlID = "njzq_jinluopan";
        } else if (classname.equals("com.cssweb.android.sms.SMSJHActivity")) {
            urlID = "njzq_jinluopan";
        } else if (classname.equals("com.cssweb.android.quote.SQS")) {
            urlID = "njzq_jinluopan";
        } else if (classname.equals("com.cssweb.android.quote.DSS")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.ZSS")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.ZJS")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.QQSP")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.quote.QHSCGridActivity")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.quote.QHSCBaseActivity")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.HSZS")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.quote.GlobalMarketActivity")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.OTCFundActivity")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.PaiMing")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.DaPan")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.FenLei")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.QueryStock")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.PersonalStock")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.QuoteAlarm")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.TrendActivity")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.KLineActivity")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.KLine2Activity")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.QuotePrice")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.QuoteDetail")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.GetF10List")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.GetF10Content")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.GlobalMarket")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.FLineActivity")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.StockTypeFund")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.quote.FundQueryCondition")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.FundQuery")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.SunPrivate")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.JingZhiQuery")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.QuoteWarning")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.QuoteSet")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.QHHQActivity")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.GGHQActivity")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.HKMainboard")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.quote.GlobalHuiShi")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.quote.SunPrivateQueryCondition")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.web.OpenHrefDisplay")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.web.WebViewDisplay")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.web.JxgpcActivity")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.web.CfpdActivity")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.web.OpenPdfDisplay")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.fzjy.VistualTrade")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.sz.ClearActivity")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.sz.Setting")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.sz.HelpActivity")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.sz.CustomSettingActivity")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.sz.HQRefreshSettingActivity")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.sz.ServerSettingActivity")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.sz.LockSettingActivity")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.tyyh.ExperienceUsers")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.video.CustomMediaPlayer")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.video.VideoPlayer")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.trade.CnjjActivity")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.trade.FundActivity")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.trade.BankActivity")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.TransferFundsActivity")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.user.AccountManage")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.stock.GetDetails")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.stock.GetDetailsH")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.stock.StockTrading")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.stock.GetPosition")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.stock.StockCancel")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.stock.AssetQuery")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.stock.TodayEntrust")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.trade.stock.TodayDeal")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.stock.ExchangeFund")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.stock.StockWarrant")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.stock.StockWarrant")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.util.DateRange")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.stock.HistoryEntrust")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.stock.HistoryDeal")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.trade.stock.Bill")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.stock.NewStockMatch")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.stock.ModifyPassword")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.trade.stock.StockList")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.stock.ShareholderList")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.login.LoginActivity")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.bank.TransferQuery")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.bank.BankBalanceQuery")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.bank.BankFundTransfer")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.bank.FundBankTransfer")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.bank.TransferDateRange")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.transferFunds.FundsDetails")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.transferFunds.ZfTransfer")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.transferFunds.TransferFundsDateRange")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.transferFunds.TransferFundsQuery")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.stock.ModifyContactInfo")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.fund.FundTrading")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.trade.fund.TodayTrust")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.fund.FundTransfer")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.fund.FundMelonSet")) {
            urlID = "njzq_";
        } else if (classname.equals("com.cssweb.android.trade.fund.FundPortio")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.fund.FundAccount")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.fund.FundRiskTest")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.fund.HistoryConclusion")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.fund.HistoryTrust")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.fund.FundCompany")) {
            urlID = "njzq_";
        } else if (classname
                .equals("com.cssweb.android.trade.fund.FundAccountForm")) {
            urlID = "njzq_";
        } else {
            urlID = "";
        }

        return urlID;
    }

    /**
     * 用户登陆后可能有的数据
     * 
     * @return 营业部编码
     */
    @SuppressWarnings("unused")
    private static String setOrgID() {
        return null;
    }

    /**
     * @return 营业部名称
     */
    @SuppressWarnings("unused")
    private static String setOrgDesc() {
        return null;
    }

    /**
     * 1：交易用户 2：体验用户 3：浏览用户 4：注册用户 5：内部用户
     * 
     * @return 用户类型
     */
    @SuppressWarnings("unused")
    private static String setUserType() {
        return null;
    }

    /**
     * @return 用户等级
     */
    @SuppressWarnings("unused")
    private static String setUserLevel() {
        return null;
    }

    /**
     * @return 真实姓名
     */
    @SuppressWarnings("unused")
    private static String setRealName() {
        return null;
    }

    /**
     * JLP_ANDROID 金罗盘Android版
     * 
     * @return 进行分析的系统编码
     */
    @SuppressWarnings("unused")
    private static String setSystemCode() {
        return null;
    }

    /**
     * 1.0
     * 
     * @return 进行分析的系统版本
     */
    @SuppressWarnings("unused")
    private static String setSystemVer() {
        return null;
    }

    /**
     * 64位长度的唯一值
     * 
     * @return 此次回话的ID
     */
    @SuppressWarnings("unused")
    private static String setSessionId() {
        return null;
    }

    /**
     * 非Web应用传值：1
     * 
     * @return 页面点击数
     */
    @SuppressWarnings("unused")
    private static String setHits() {
        return null;
    }

    /**
     * 此次访问的操作类型。 1: 更新状态 0: 新增
     * 
     * @return 此次访问的操作类型
     */
    @SuppressWarnings("unused")
    private static String setOpera() {
        return null;
    }

    // private static String setKey() {
    // return null;
    // }

    /**
     * HTC Desire 等等
     * 
     * @return 访问终端类型
     */
    @SuppressWarnings("unused")
    private static String setTerminaltype() {
        return null;
    }

    /**
     * 同浏览用户的custID值。
     * 
     * @return 标示浏览用户身份的ID
     */
    @SuppressWarnings("unused")
    private static String setVisitorID() {
        return null;
    }

    /**
     * 当前操作系统版本
     * 
     * @return 操作系统及版本
     */
    @SuppressWarnings("unused")
    private static String setOS() {
        return null;
    }

    /**
     * 当手机使用非wifi方式上网时，可获取网络运营商。如： 中国移动、中国联通、中国电信等
     * 
     * @return 网络运营商
     * 
     */
    @SuppressWarnings("unused")
    private static String setISP() {
        return null;
    }

    /**
     * WI-FI EDGE/3G
     * 
     * @return 联网方式
     */
    @SuppressWarnings("unused")
    private static String setNetworkType() {
        return null;
    }

    /**
     * 形如：1440*900
     * 
     * @return 客户端分辨率
     */
    @SuppressWarnings("unused")
    private static String setResolu() {
        return null;
    }

    /**
     * @return 客户端语言
     */
    @SuppressWarnings("unused")
    private static String setOSCharacter() {

        return null;
    }

    /**
     * 应用发布来源
     * 
     * @return APK获取来源;
     */
    @SuppressWarnings("unused")
    private static String setChannel() {

        return "Google Market";
    }
    // private static String set() {
    // return null;
    // }
    // private static String set() {
    // return null;
    // }
}

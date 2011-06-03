package com.cssweb.android.trade.login;

/**
 * 用户登录信息
 * 
 * @author Ching
 */
public class LoginInfo {
    private String loginID;// 登录账号

    private String loginType;// 登录账号类型

    private String userType;// 用户类型

    private String userLevel;// 用户等级

    private String realName;// 真实姓名

    private String orgID;// 营业部编码

    @SuppressWarnings("unused")
    private String orgDesc;// 营业部名称

    private String systemCode;// 进行分析的系统编码

  //  private String loginModule;// 登录模块

    private String loginState;// 登录状态

    private String loginErrorDesc;// 登录错误信息

    private static LoginInfo INSTANCE = null;

    public static LoginInfo getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LoginInfo();
        }
        return INSTANCE;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getOrgID() {
        return orgID;
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }

    public String getOrgDesc() {
        return "";//你懂的
    }

    public void setOrgDesc(String orgDesc) {
        this.orgDesc = orgDesc;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

//    public String getLoginModule() {
//        return loginModule;
//    }
//
//    public void setLoginModule(String loginModule) {
//        this.loginModule = loginModule;
//    }

    public String getLoginState() {
        return loginState;
    }

    public void setLoginState(String loginState) {
        this.loginState = loginState;
    }

    public String getLoginErrorDesc() {
        return loginErrorDesc;
    }

    public void setLoginErrorDesc(String loginErrorDesc) {
        this.loginErrorDesc = loginErrorDesc;
    }
}

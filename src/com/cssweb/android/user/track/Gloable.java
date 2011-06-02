package com.cssweb.android.user.track;


public class Gloable {

    private String IMEI;// 移动设备编号

    private String sysCode;// 系统编码

    private String sysVer;// 系统版本

    private String sessionid;// 会话表示

    private int hits;// 点击数

    private String termianl;// 终端类型

    private String os;// 操作系统版本

    private String oschar;// 系统语言

    private String isp;// 运营商

    private String reso;// 分辨率

    private String netType;// 联网方式

    private static Gloable INSTANCE = null;

    public static Gloable getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Gloable();
        }
        return INSTANCE;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String iMEI) {

        IMEI = iMEI;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getSysVer() {
        return sysVer;
    }

    public void setSysVer(String sysVer) {
        this.sysVer = sysVer;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public String getTermianl() {
        return termianl;
    }

    public void setTermianl(String termianl) {
        this.termianl = termianl;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOschar() {
        return oschar;
    }

    public void setOschar(String oschar) {
        this.oschar = oschar;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getReso() {
        return reso;
    }

    public void setReso(String reso) {
        this.reso = reso;
    }

}

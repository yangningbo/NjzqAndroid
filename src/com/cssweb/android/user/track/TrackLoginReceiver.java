package com.cssweb.android.user.track;

import com.cssweb.android.common.Base64Encoder;
import com.cssweb.android.connect.Conn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class TrackLoginReceiver extends BroadcastReceiver {
    public static final String LOGINTRACK = "http://172.16.1.24:8080/user_login?loginfo=";

    private String url;

    private String jsobString;

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println(this.getClass().getName() + "."
                + new Exception().getStackTrace()[0].getMethodName() + "()");

        Bundle bundle = intent.getExtras();
        String getBroadcastString = (String) bundle.get("key");
        if (!getBroadcastString.equals("loginPressed")) {
            try {
                String base64url = Base64Encoder.encode(getBroadcastString,
                        "gb2312");
                if (url == null) {
                    url = LOGINTRACK + base64url+"==&ram=" + Math.random();
                    System.out.println(url);
                    sendHttpUrl();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendHttpUrl() {

        System.out.println(this.getClass().getName() + "."
                + new Exception().getStackTrace()[0].getMethodName() + "()");
        if (url != null) {
            jsobString = Conn.execute(url).toString();
            System.out.println(jsobString);
        }

    }
}

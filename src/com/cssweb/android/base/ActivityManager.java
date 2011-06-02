/**
 * Copyright 2010 CssWeb Microsystems, Inc. All rights reserved.
 * CssWeb PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @(#)ActivityManager.java 下午01:02:57 2010-11-28
 */
package com.cssweb.android.base;

import java.util.Stack;

import android.app.Activity;

/**
 * 建立Activity堆栈，便于统一管理后退键处理
 * 
 * @author hujun
 * @version 1.0
 * @see
 * @since 1.0
 */
public class ActivityManager {
	private static Stack<Object> activityStack;
	public static Activity g_htActivity;
	private static ActivityManager instance;
	private static String m_strPakage = "com.cssweb";

	private ActivityManager() {
		if (activityStack != null)
			return;
		activityStack = new Stack<Object>();
	}

	public static ActivityManager getActivityManager() {
		if (instance == null)
			instance = new ActivityManager();
		return instance;
	}

	public static String getPackageName() {
		return m_strPakage;
	}
}

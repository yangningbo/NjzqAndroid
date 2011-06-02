/**
 * Copyright 2010 CssWeb Microsystems, Inc. All rights reserved.
 * CssWeb PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @(#)RestartDialog.java 下午03:31:24 2011-3-29
 */
package com.cssweb.android.main;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.cssweb.android.util.ActivityUtil;

/**
 * 
 *
 * @author hujun
 * @version 1.0
 * @see
 * @since 1.0
 */
public class RestartDialog extends Activity {

	private int filetype = 1;
	private Button btn;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		requestWindowFeature(1);
		
        setContentView(R.layout.zr_restart_dialog);
        
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        	filetype = bundle.getInt("filetype");
        Log.i("#######RestartDialog########", filetype+">>>>>>>>>>>>>>");
        
        btn = (Button)findViewById(R.id.okButton);
        btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				ActivityUtil.restart(RestartDialog.this, filetype);
			}
		});
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 按下的如果是BACK，同时没有重复
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			ActivityUtil.restart(RestartDialog.this, filetype);
		}
		return super.onKeyDown(keyCode, event);
	}
}

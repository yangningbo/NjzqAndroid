package com.cssweb.android.video;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.cssweb.android.main.R;
/**
 * 视频播放类
 * @author hoho
 *
 */
public class VideoPlayer extends Activity {
	private VideoView videoView;
	private String url = null;
	private Dialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置成全屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        setContentView(R.layout.zr_mediaplayer2);
        Bundle bundle = getIntent().getExtras();
        url = bundle.getString("url");
        
        //创建进度条
		dialog=ProgressDialog.show(this, "进度提示", "视频正在加载中...");
        videoView = (VideoView) findViewById(R.id.videoView);
       
       //videoView.setVideoPath("/sdcard/music/test.mp4");
       //videoView.setVideoURI(Uri.parse("http://wangjun.easymorse.com/wp-content/video/mp4/tuzi.mp4"));
       
       videoView.setVideoURI(Uri.parse(url));
       
       MediaController mediaController = new MediaController(this);
       videoView.setMediaController(mediaController);
       //videoView.start();
       //videoView.requestFocus();
       
       videoView.setOnPreparedListener(new OnPreparedListener() {
			// 开始播放
			public void onPrepared(MediaPlayer mp) {
				//videoView.setBackgroundColor(Color.argb(0, 0, 255, 0));
				dialog.dismiss();
			}
		});
		//播放完毕
       videoView.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				dialog.dismiss();
				Toast.makeText(VideoPlayer.this, "播放完毕，谢谢观看!", Toast.LENGTH_LONG).show();
			}
		});
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	videoView.start();
    }
    
}
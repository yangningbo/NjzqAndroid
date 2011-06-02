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
 * ��Ƶ������
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//���ó�ȫ��ģʽ
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//ǿ��Ϊ����
        setContentView(R.layout.zr_mediaplayer2);
        Bundle bundle = getIntent().getExtras();
        url = bundle.getString("url");
        
        //����������
		dialog=ProgressDialog.show(this, "������ʾ", "��Ƶ���ڼ�����...");
        videoView = (VideoView) findViewById(R.id.videoView);
       
       //videoView.setVideoPath("/sdcard/music/test.mp4");
       //videoView.setVideoURI(Uri.parse("http://wangjun.easymorse.com/wp-content/video/mp4/tuzi.mp4"));
       
       videoView.setVideoURI(Uri.parse(url));
       
       MediaController mediaController = new MediaController(this);
       videoView.setMediaController(mediaController);
       //videoView.start();
       //videoView.requestFocus();
       
       videoView.setOnPreparedListener(new OnPreparedListener() {
			// ��ʼ����
			public void onPrepared(MediaPlayer mp) {
				//videoView.setBackgroundColor(Color.argb(0, 0, 255, 0));
				dialog.dismiss();
			}
		});
		//�������
       videoView.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				dialog.dismiss();
				Toast.makeText(VideoPlayer.this, "������ϣ�лл�ۿ�!", Toast.LENGTH_LONG).show();
			}
		});
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	videoView.start();
    }
    
}
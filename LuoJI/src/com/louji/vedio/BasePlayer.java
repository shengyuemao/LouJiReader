package com.louji.vedio;

import java.io.File;
import java.nio.Buffer;
import java.util.Timer;
import java.util.TimerTask;

import com.louji.util.Logger;
import com.louji.util.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.SeekBar;

@SuppressLint("HandlerLeak")
public abstract class BasePlayer implements OnBufferingUpdateListener,
		OnCompletionListener, MediaPlayer.OnPreparedListener,
		SurfaceHolder.Callback, OnVideoSizeChangedListener
{
	protected int videoWidth;
	protected int videoHeight;
	public MediaPlayer mediaPlayer ;
	public SurfaceHolder surfaceHolder;
	public SeekBar skbProgress;
	protected Timer mTimer = new Timer();
	public SurfaceView surfaceView;

	protected Activity activity;

	
	protected TimerTask mTimerTask = new TimerTask()
	{

		@Override
		public void run()
		{
			if (mediaPlayer == null)
				return;
			try
			{
				if (mediaPlayer.isPlaying() && skbProgress.isPressed() == false)
				{
					handleProgress.sendEmptyMessage(0);
				}
			} catch (IllegalStateException e)
			{
				Logger.e("error");
				e.printStackTrace();
			}

		}

	};

	protected Handler handleProgress = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			int position = mediaPlayer.getCurrentPosition();
			int duration = mediaPlayer.getDuration();

			if (duration > 0)
			{
				long pos = skbProgress.getMax() * position / duration;
				skbProgress.setProgress((int) pos);
			}
		}

	};

	@SuppressWarnings("deprecation")
	public BasePlayer(SurfaceView surfaceView, SeekBar skbProgress,
			Activity activity)
	{
		
		this.activity = activity;
		this.skbProgress = skbProgress;
		this.surfaceView = surfaceView;
		this.mediaPlayer = new MediaPlayer();
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mTimer.schedule(mTimerTask, 0, 1000);
	}

	/**
	 * @param鎾斁瑙嗛 鏈夊緟瀹屾垚
	 * @author 鐩涙湀鑼�
	 * 
	 */
	public abstract void play();

	/**
	 * 鎻愪緵缍茬怠鍦板潃瀹炵幇鎾斁
	 * 
	 * @param videoUrl
	 *            缃戠粶鍦板潃
	 */
	public void playUrl(String videoUrl)
	{
	};

	/**
	 * 鎻愪緵鏂囦欢鍦板潃瀹炵幇鎾斁
	 */
	public void playFile(String videoFile)
	{
	};

	/**
	 * 鎻愪緵璧勬簮鏂囦欢瀹炵幇鎾斁
	 */
	public void playRaw(int videoRaw)
	{
	};

	/**
	 * 鎻愪緵File缂撳瓨鏂囦欢瀹炵幇鎾斁
	 */
	public void playFile(File vedioFile)
	{
	};

	/**
	 * 鎻愪緵Buffer瀹炵幇鎾斁
	 */
	public void playBuffer(Buffer vedioBuffer)
	{
	};

	/**
	 * 鏆傚仠鎾斁
	 * 
	 * @param 鏈夊緟鏀瑰杽
	 */
	public abstract void pause();

	/**
	 * 鍋滄鎾斁
	 * 
	 * @param 鏈夊緟鏀瑰杽
	 */
	public abstract void stop();

	public void changeVideoSize( int width, int height)
	{

		videoWidth = width;
		videoHeight = height;
		
		int screenWidth = Util.getScreenWidth(activity);
		int screenHeight = Util.getScreenHeight(activity);
		
		float ar = (float)videoWidth / videoHeight;
		float ar1 = (float)videoHeight / videoWidth;
		
		if (videoHeight != 0 && videoWidth != 0)
		{

			if (screenWidth > screenHeight)
			{
				if (videoHeight >= screenHeight)
				{
					videoHeight = screenHeight;
				}

				videoWidth = (int) (videoHeight * ar);

			}

			if (screenHeight > screenWidth)
			{
				if (videoWidth >= screenWidth)
				{
					videoWidth = screenWidth;
				}

				videoHeight = (int) (videoWidth * ar1);
			}			
		}
		
		surfaceHolder.setFixedSize(videoWidth, videoHeight);
	}
}

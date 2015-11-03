package com.louji.vedio;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.louji.util.Logger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.SeekBar;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnCompletionListener;
import io.vov.vitamio.MediaPlayer.OnPreparedListener;
import io.vov.vitamio.MediaPlayer.OnVideoSizeChangedListener;

@SuppressLint("HandlerLeak")
public class PlayerHttpUrlWithVitamio implements OnBufferingUpdateListener,
		OnCompletionListener, OnPreparedListener, OnVideoSizeChangedListener,
		SurfaceHolder.Callback
{
	public static final String MEDIA = "media";
	private static final int LOCAL_VIDEO = 4;
	private static final int STREAM_VIDEO = 5;

	public MediaPlayer mediaPlayer;
	public SurfaceView surfaceView;
	public SurfaceHolder surfaceHolder;

	public int videoWidth;
	public int videoHeight;
	public SeekBar skbProgress;
	public String filePath;

	protected Timer mTimer = new Timer();

	private boolean isVideoSizeKnown = false;
	private boolean isVideoReadyToBePlayed = false;

	private Activity activity;

	/**
	 * 通过定时器和Handler来更新进度条
	 */
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
			long position = mediaPlayer.getCurrentPosition();
			long duration = mediaPlayer.getDuration();

			if (duration > 0)
			{
				long pos = skbProgress.getMax() * position / duration;
				skbProgress.setProgress((int) pos);
			}
		}

	};

	@SuppressWarnings("deprecation")
	public PlayerHttpUrlWithVitamio(SurfaceView surfaceView, SeekBar seekBar,
			Activity activity)
	{
		if (!LibsChecker.checkVitamioLibs(activity))
			return;
		// Create a new media player and set the listeners
		mediaPlayer = new MediaPlayer(activity);
		this.skbProgress = seekBar;
		this.activity = activity;
		this.surfaceView = surfaceView;
		this.surfaceHolder = surfaceView.getHolder();
		this.surfaceHolder.addCallback(this);
		this.surfaceHolder.setFormat(PixelFormat.RGB_888);
		this.surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mTimer.schedule(mTimerTask, 0, 1000);

	}

	public void onActivityPauseOrDestroy()
	{
		releaseMediaPlayer();
		doCleanUp();
	}

	private void releaseMediaPlayer()
	{
		if (mediaPlayer != null)
		{
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	private void startVideoPlayback()
	{
		Logger.i("width =" + videoWidth + "height =" + videoHeight);
		surfaceHolder.setFixedSize(videoWidth, videoHeight);
		mediaPlayer.start();
	}

	public void playVideo(Integer Media, String path)
	{
		doCleanUp();
		Logger.i("playVideo");
		try
		{
			switch (Media)
			{
			case LOCAL_VIDEO:
				/*
				 * TODO: Set the path variable to a local media file path.
				 */
				this.filePath = path;
				if (path == "")
				{
					return;
				}
				break;
			case STREAM_VIDEO:
				/*
				 * TODO: Set path variable to progressive streamable mp4 or 3gpp
				 * format URL. Http protocol should be used. Mediaplayer can
				 * only play "progressive streamable contents" which basically
				 * means: 1. the movie atom has to precede all the media data
				 * atoms. 2. The clip has to be reasonably interleaved.
				 */
				this.filePath = path;
				if (path == "")
				{
					// Tell the user to provide a media file URL.
					return;
				}

				break;
			}

			mediaPlayer.reset();
			mediaPlayer.setDataSource(filePath);
			mediaPlayer.prepare();// prepare之後自動播放
			Logger.i(filePath);
		} catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void pause()
	{
		if (mediaPlayer != null)
		{
			mediaPlayer.pause();
		}
	}

	public void stop()
	{
		doCleanUp();
		if (mediaPlayer != null)
		{
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
			mTimerTask.cancel();
			mTimer.cancel();

		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		Logger.i("surfaceCreated");
		
		try
		{
			mediaPlayer.setDisplay(surfaceHolder);
			mediaPlayer.setOnBufferingUpdateListener(this);
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setOnVideoSizeChangedListener(this);
			mediaPlayer.setOnCompletionListener(this);
			activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		} catch (Exception e)
		{

			Logger.e("error");

		}

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height)
	{
		Logger.i("surfaceChanged");

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height)
	{
		Logger.i("onVideoSizeChanged called");
		if (width == 0 || height == 0)
		{
			Logger.i("invalid video width(" + width + ") or height(" + height
					+ ")");
			return;
		}

		isVideoSizeKnown = true;
		videoWidth = width;
		videoHeight = height;
		if (isVideoReadyToBePlayed)
		{
			startVideoPlayback();
		}

	}

	@Override
	public void onPrepared(MediaPlayer mp)
	{
		Logger.i("onPrepared called");
		isVideoReadyToBePlayed = true;
		if (isVideoReadyToBePlayed && isVideoSizeKnown)
		{
			startVideoPlayback();
		}

	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent)
	{
		skbProgress.setSecondaryProgress(percent);

	}

	/**
	 * 清理界面
	 */
	public void doCleanUp()
	{
		videoWidth = 0;
		videoHeight = 0;
		isVideoReadyToBePlayed = false;
		isVideoSizeKnown = false;
	}

	@Override
	public void onCompletion(MediaPlayer mp)
	{
		// TODO Auto-generated method stub

	}

}

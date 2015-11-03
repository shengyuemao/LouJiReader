package com.louji.vedio;

import java.io.IOException;

import com.louji.util.Logger;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.SeekBar;

public class PlayerFilePath extends BasePlayer
{

	public PlayerFilePath(SurfaceView surfaceView, SeekBar skbProgress,
			Activity activity)
	{
		super(surfaceView, skbProgress, activity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void playFile(String videoFile)
	{
		try
		{

			mediaPlayer.reset();
			mediaPlayer.setDataSource(videoFile);
			mediaPlayer.prepare();
			mediaPlayer.start();
		} catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		} catch (IllegalStateException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent)
	{
		skbProgress.setSecondaryProgress(percent);

	}

	@Override
	public void onCompletion(MediaPlayer mp)
	{
		mp.stop();
		mp.release();
		// surfaceHolder.setFixedSize(0, 0);

	}

	@Override
	public void onPrepared(MediaPlayer mp)
	{
		if (videoHeight != 0 && videoWidth != 0)
		{
			mp.start();
		}
		Logger.e("onPrepared");
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		try
		{
			mediaPlayer.setDisplay(holder);
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnBufferingUpdateListener(this);
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setOnCompletionListener(this);

			mediaPlayer.setOnVideoSizeChangedListener(this);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Logger.e("surface created");
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height)
	{
		Logger.e("surfaceChanged");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		Logger.e("surfaceDestroyed");
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height)
	{
		changeVideoSize(width, height);
		Logger.e("width = " + width + ";" + "height = " + height);
	}

	@Override
	public void play()
	{
		// TODO Auto-generated method stub
		if (mediaPlayer != null)
		{
			mediaPlayer.start();
		}
	}

	/**
	 * 鏆傚仠鎾斁
	 * 
	 * @param 鏈夊緟鏀瑰杽
	 */
	public void pause()
	{
		if (mediaPlayer != null)
		{
			mediaPlayer.pause();
		}

	}

	public void stop()
	{
		if (mediaPlayer != null)
		{
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

}

package com.louji.vedio;

import java.io.IOException;

import com.louji.util.Logger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.SeekBar;

@SuppressLint(
{"HandlerLeak", "NewApi"})
public class PlayerHttpUrl extends BasePlayer
{

	public PlayerHttpUrl(SurfaceView surfaceView, SeekBar skbProgress,
			Activity activity)
	{
		super(surfaceView, skbProgress, activity);
		// TODO Auto-generated constructor stub
	}

	public void play()
	{
		if (mediaPlayer != null)
		{
			mediaPlayer.start();
		}

	}

	public boolean isPlaying()
	{
		return mediaPlayer.isPlaying();
	}

	public void playUrl(String videoUrl)
	{
		try
		{
			mediaPlayer.reset();
			mediaPlayer.setDataSource(videoUrl);
			mediaPlayer.prepare();
			mediaPlayer.start();
		} catch (IllegalArgumentException e)
		{
			Logger.e("error1");
			e.printStackTrace();
		} catch (IllegalStateException e)
		{
			Logger.e("error2");
			e.printStackTrace();
		} catch (IOException e)
		{
			Logger.e("error3");
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
		try
		{
			mediaPlayer.setDisplay(holder);
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnBufferingUpdateListener(this);
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setOnVideoSizeChangedListener(this);
			mediaPlayer.setOnCompletionListener(this);
		} catch (Exception e)
		{

			Logger.e("error");

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
	public void onPrepared(MediaPlayer mp)
	{

		mp.start();

		changeVideoSize(mp.getVideoWidth(), mp.getVideoHeight());

		Logger.e("onPrepared");
	}

	@Override
	public void onCompletion(MediaPlayer mp)
	{
		Logger.e("onCompletion");

	}

	@Override

	public void onBufferingUpdate(MediaPlayer mp, int percent)
	{
		skbProgress.setSecondaryProgress(percent);
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height)
	{
		changeVideoSize(width, height);
	}

}

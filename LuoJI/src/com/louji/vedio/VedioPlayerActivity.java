package com.louji.vedio;

import com.louji.base.R;
import com.louji.cartoon.BaseActivity;
import com.louji.util.Logger;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

public class VedioPlayerActivity extends BaseActivity
{

	private SurfaceView surfaceView;
	private ImageButton btnNext, btnPlayUrl;
	private SeekBar skbProgress;

	private PlayerHttpUrl player;

	private VedioListIterator vedioListIterator;
	private VedioList vedioList;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_vedio);

		initView();

	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);

		Logger.e("onSaveInstanceState");
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();

		Logger.e("onResume");
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		Logger.e("onConfigurationChanged");

	}

	private void initView()
	{

		surfaceView = (SurfaceView) findViewById(
				R.id.activity_vedio_surfaceView);

		btnPlayUrl = (ImageButton) findViewById(R.id.activity_vedio_play_pause);
		btnPlayUrl.setOnClickListener(new ClickEvent());

		btnNext = (ImageButton) findViewById(R.id.activity_vedio_play_next);
		btnNext.setOnClickListener(new ClickEvent());

		skbProgress = (SeekBar) findViewById(R.id.activity_vedio_skbProgress);
		skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());

		initProptrey();
		initData();
	}

	private void initProptrey()
	{
		player = new PlayerHttpUrl(surfaceView, skbProgress, this);

	}

	private void initData()
	{
		VedioModel vedioModel = new VedioModel();
		VedioModel vedioModel1 = new VedioModel();
		VedioModel vedioModel2 = new VedioModel();
		VedioModel vedioModel3 = new VedioModel();
		vedioList = new VedioList();
		vedioListIterator = new VedioListIterator(vedioList);

		vedioModel
				.setVedioUrl("http://maono-maonoimage.stor.sinaapp.com/de.mp4");
		vedioModel1
				.setVedioUrl("http://maono-maonoimage.stor.sinaapp.com/de.mp4");
		vedioModel2
				.setVedioUrl("http://maono-maonoimage.stor.sinaapp.com/de.mp4");
		vedioModel3
				.setVedioUrl("http://maono-maonoimage.stor.sinaapp.com/de.mp4");

		vedioListIterator.insertBefore(vedioModel3);
		vedioListIterator.insertBefore(vedioModel2);
		vedioListIterator.insertBefore(vedioModel1);
		vedioListIterator.insertBefore(vedioModel);

		play(vedioListIterator.getCurrent().getVedioUrl().getVedioUrl());
	}

	class ClickEvent implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{

				case R.id.activity_vedio_play_pause :
					Toast.makeText(
							v.getContext(), vedioListIterator.getCurrent()
									.getVedioUrl().getVedioUrl(),
							Toast.LENGTH_LONG).show();
					play(vedioListIterator.getCurrent().getVedioUrl()
							.getVedioUrl());

					break;
				case R.id.activity_vedio_play_next :

					vedioListIterator.nextLink();
					Toast.makeText(
							v.getContext(), vedioListIterator.getCurrent()
									.getVedioUrl().getVedioUrl(),
							Toast.LENGTH_LONG).show();
					player.playUrl(vedioListIterator.getCurrent().getVedioUrl()
							.getVedioUrl());

					break;

			}

		}

	}

	private void play(String path)
	{
		player.playUrl(path);
		if (!player.isPlaying())
		{
			btnPlayUrl.setImageResource(R.drawable.mediacontroller_pause);
		} else
		{
			player.pause();
			btnPlayUrl.setImageResource(R.drawable.mediacontroller_play);
		}
	}

	@Override
	protected void onPause()
	{

		super.onPause();
		Logger.e("onPause");
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		// player.onActivityPauseOrDestroy();
		Logger.e("onDestroy");
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		Logger.e("onStop");
	}

	@Override
	protected void onRestart()
	{
		super.onRestart();
		Logger.e("onRestart");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		Logger.e("onRestoreInstanceState");
	}

	class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener
	{

		long progress;
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser)
		{
			if (player.mediaPlayer != null)
			{
				this.progress = progress * player.mediaPlayer.getDuration()
						/ seekBar.getMax();
			}

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar)
		{

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar)
		{
			if (player.mediaPlayer != null)
			{
				player.mediaPlayer.seekTo((int) progress);
			}
			Logger.e("" + progress);

		}

	}

}

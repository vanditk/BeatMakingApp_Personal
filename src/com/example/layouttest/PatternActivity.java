package com.example.layouttest;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.layouttest.R.raw;

public class PatternActivity extends Activity {
	long timeAtStart = 0;
	long timeSinceStart = 0;
	long timeAtLastBar = 0;
	long timeSinceLastBar = 0;
	boolean runThread = false;
	int bpm = 120;
	int bars = 4;
	int beatInBar = 0;
	ArrayList<Sound> sounds = new ArrayList<Sound>();
	int soundIndex = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pattern_layout);
		timeAtStart = SystemClock.elapsedRealtime();
		timeSinceStart = 0;
		timeAtLastBar = timeAtStart;
		final Context context = this;

		final SoundPool sp00 = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
		final SoundPool sp01 = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
		final SoundPool sp02 = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
		final SoundPool sp03 = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
		final int id00 = sp00.load(this, raw.closedhat, 1);
		final int id01 = sp01.load(this, raw.cymbal, 1);
		final int id02 = sp02.load(this, raw.halfopenhat, 1);
		final int id03 = sp00.load(this, raw.hitom, 1);
		final int id10 = sp00.load(this, raw.kick, 1);
		final int id11 = sp00.load(this, raw.lowtom, 1);

		AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
		final float volume = (float) am
				.getStreamVolume(AudioManager.STREAM_MUSIC);

		final ProgressBar progBar = (ProgressBar) findViewById(R.id.progBar);
		progBar.setProgress(0);
		final Handler handler = new Handler();

		handler.post(new Runnable() {
			public void run() {
				long time = SystemClock.elapsedRealtime();
				timeSinceStart = time - timeAtStart;
				timeSinceLastBar = time - timeAtLastBar;
				if (runThread == true) {

					if (timeSinceStart >= (4 * 60000 * bars) / bpm) {
						timeAtStart = time;
						timeSinceStart = 0;
						progBar.setProgress(0);
					}
					if (timeSinceLastBar >= 60000 / bpm) {
						timeAtLastBar = time;
						timeSinceLastBar = 0;
						sp00.play(id00, volume, volume, 1, 0, (float) 1.0);
					}
					progBar.incrementProgressBy((4*60000*bars)/(8*100*bpm));

				}
				handler.postDelayed(this, 60000/(8*bpm));
			}
		});

		final Button trackButton = (Button) findViewById(R.id.track_button);
		trackButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent trackIntent = new Intent().setClass(context,
						TrackActivity.class);
				startActivity(trackIntent);
			}
		});
		final Button patternInfo = (Button) findViewById(R.id.pattern_info_button);
		patternInfo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final Dialog patternInfoDialog = new Dialog(context);
				patternInfoDialog.setContentView(R.layout.pattern_info_dialog);
				patternInfoDialog.setTitle("Pattern Information");

				Spinner spinner = (Spinner) patternInfoDialog
						.findViewById(R.id.pattern_spinner);

				ArrayAdapter<CharSequence> adapter = ArrayAdapter
						.createFromResource(patternInfoDialog.getContext(),
								R.array.pattern_array,
								android.R.layout.simple_spinner_item);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(adapter);
				patternInfoDialog.show();

			}
		});
		final ImageButton playButton = (ImageButton) findViewById(R.id.play_button);
		playButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				runThread = true;
			}
		});
		final ImageButton stopButton = (ImageButton) findViewById(R.id.stop_button);
		stopButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				runThread = false;

			}
		});
		final Button pad_00 = (Button) findViewById(R.id.pad_00);
		pad_00.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable(){
					public void run() {
						sounds.add(new Sound(SystemClock.elapsedRealtime()
								- timeAtStart, id00, sp00));
						handler.post(new Runnable(){

							public void run() {
								sp00.play(id00, volume, volume, 1, 0, (float) 1.0);
								
							}
							
						});
					}
				}).start();
			}
		});
		final Button pad_01 = (Button) findViewById(R.id.pad_01);
		pad_01.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new Thread(new Runnable(){
					public void run() {
						sounds.add(new Sound(SystemClock.elapsedRealtime()
								- timeAtStart, id01, sp01));
						handler.post(new Runnable(){

							public void run() {
								sp01.play(id01, volume, volume, 1, 0, (float) 1.0);
								
							}
							
						});
					}
				}).start();
			}
		});
		final Button pad_02 = (Button) findViewById(R.id.pad_02);
		pad_02.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new Thread(new Runnable(){
					public void run() {
						sounds.add(new Sound(SystemClock.elapsedRealtime()
								- timeAtStart, id02, sp02));
						handler.post(new Runnable(){

							public void run() {
								sp02.play(id02, volume, volume, 1, 0, (float) 1.0);
								
							}
							
						});
					}
				}).start();
			}
		});
		final Button pad_03 = (Button) findViewById(R.id.pad_03);
		pad_03.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				sp00.play(id03, volume, volume, 1, 0, (float) 1.0);
			}
		});
		final Button pad_10 = (Button) findViewById(R.id.pad_10);
		pad_10.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				sp00.play(id10, volume, volume, 1, 0, (float) 1.0);
			}
		});
		final Button pad_11 = (Button) findViewById(R.id.pad_11);
		pad_11.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// sp00.play(id11, volume, volume, 1, 0, (float) 1.0);
			}
		});
		final Button pad_12 = (Button) findViewById(R.id.pad_12);
		pad_12.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			}
		});
		final Button pad_13 = (Button) findViewById(R.id.pad_13);
		pad_13.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			}
		});
		final Button pad_20 = (Button) findViewById(R.id.pad_20);
		pad_20.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			}
		});
		final Button pad_21 = (Button) findViewById(R.id.pad_21);
		pad_21.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			}
		});
		final Button pad_22 = (Button) findViewById(R.id.pad_22);
		pad_22.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			}
		});
		final Button pad_23 = (Button) findViewById(R.id.pad_23);
		pad_23.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			}
		});
		final Button pad_30 = (Button) findViewById(R.id.pad_30);
		pad_30.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			}
		});
		final Button pad_31 = (Button) findViewById(R.id.pad_31);
		pad_31.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			}
		});
		final Button pad_32 = (Button) findViewById(R.id.pad_32);
		pad_32.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			}
		});
		final Button pad_33 = (Button) findViewById(R.id.pad_33);
		pad_33.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			}
		});
	}
}

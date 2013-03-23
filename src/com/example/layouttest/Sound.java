package com.example.layouttest;

import android.media.SoundPool;

public class Sound {
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private long time;
	private int id;
	private SoundPool pool;

	public SoundPool getPool() {
		return pool;
	}

	public void setPool(SoundPool pool) {
		this.pool = pool;
	}

	public Sound(long time, int id, SoundPool pool) {
		this.time = time;
		this.id = id;
		this.pool = pool;
	}
}

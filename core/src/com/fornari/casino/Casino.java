package com.fornari.casino;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Casino extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Carta carta = new Carta();
		img = new Texture(carta.path);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0,0,0,0);
		batch.begin();
		batch.draw(img,0,0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}

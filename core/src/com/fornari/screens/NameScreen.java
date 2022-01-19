package com.fornari.screens;

import com.badlogic.gdx.Screen;
import com.fornari.utils.Config;
import com.fornari.utils.Imagen;
import com.fornari.utils.Render;

public class NameScreen implements Screen{
	
	Imagen fondo;

	@Override
	public void show() {
		fondo = new Imagen("Fondos/fondo_menu.jpg");
		fondo.setSize(Config.anchoPantalla,Config.altoPantalla);
	}

	@Override
	public void render(float delta) {
		Render.batch.begin();
		fondo.dibujar();
		Render.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
		
	}

	@Override
	public void pause() {
		
		
	}

	@Override
	public void resume() {
		
		
	}

	@Override
	public void hide() {
		
		
	}

	@Override
	public void dispose() {
		
		
	}
	
}

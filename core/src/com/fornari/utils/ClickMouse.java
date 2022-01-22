package com.fornari.utils;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.fornari.screens.MenuScreen;

public class ClickMouse implements InputProcessor {
	private MenuScreen app;
	private int posicionX=0, posicionY=0;
	private boolean click=false;
	private boolean abajo = false;
	private boolean arriba = false;
	private boolean enter = false;
	
	public ClickMouse(MenuScreen app) {
		this.app = app;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		this.posicionX=screenX;
		this.posicionY=Config.altoPantalla-screenY;
		click = true;
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		click = false;
		return false;
	}
	
	public boolean isClick(){
		return this.click;
	}
	
	public boolean isAbajo() {
		return this.abajo;
	}
	
	public boolean isArriba() {
		return this.arriba;
	}
	
	public boolean isEnter() {
		return this.enter;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		this.posicionX=screenX;
		this.posicionY=Config.altoPantalla-screenY;
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}

	public int getPosicionX() {
		return posicionX;
	}

	public int getPosicionY() {
		return posicionY;
	}

	@Override
	public boolean keyDown(int keycode) {
		app.setTiempo(0f);
		if(keycode == Keys.DOWN) {
			abajo = true;
		}else if (keycode == Keys.UP) {
			arriba = true;
		}else if(keycode == Keys.ENTER) {
			enter = true;
		}
		return false;
		
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.DOWN) {
			abajo = false;
		}else if (keycode == Keys.UP) {
			arriba = false;
		}else if(keycode == Keys.ENTER) {
			enter = false;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}

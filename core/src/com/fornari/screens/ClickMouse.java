package com.fornari.screens;

import com.badlogic.gdx.InputProcessor;
import com.fornari.utils.Config;

public class ClickMouse implements InputProcessor {
	int posicionX=0, posicionY=0;
	boolean click=false;
	
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
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
	
	

}

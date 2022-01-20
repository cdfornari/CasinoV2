package com.fornari.casino;
import com.fornari.screens.*;
import com.fornari.utils.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Casino extends Game {
	private String userName;
	public static Casino ventana;
	
	@Override
	public void create () {
		ventana = this;
		Render.batch = new SpriteBatch();
		this.setUserName(cargarNombre());
		if(this.userName == "") {
			this.setScreen(new NameScreen()); //NameScreen();
		}else {
			this.setScreen(new MenuScreen());
		}
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		Render.batch.dispose();
	}
	
	private String cargarNombre() {
		return "";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}

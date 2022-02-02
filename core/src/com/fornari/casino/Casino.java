package com.fornari.casino;
import com.fornari.archivos.Archivo;
import com.fornari.screens.*;
import com.fornari.utils.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Casino extends Game {
	public static Casino ventana;
	
	@Override
	public void create () {
		ventana = this;
		Render.batch = new SpriteBatch();
		Config.userName = Archivo.nombreUsuario();
		if(Config.userName == "")
			this.setScreen(new NameScreen());
		else 
			this.setScreen(new MenuScreen());
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		Render.batch.dispose();
	}
	
}

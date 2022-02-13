package com.fornari.casino;
import com.fornari.archivos.Archivo;
import com.fornari.screens.*;
import com.fornari.utils.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/**
 * Esta clase invoca la pantalla de menu o nombre segun corresponda
 * @author Carlos Fornari, Sandro Portanova, Maria Porras
 *
 */
public class Casino extends Game {
	public static Casino ventana;
	
	/**
	 * Funcion que se llama al abrir el programa de 0
	 */
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
    /**
     * Se ejecuta una vez cada frame
     */
	@Override
	public void render () {
		super.render();
	}
	/**
	 * Se ejecuta al cerrar una pantalla
	 */
	@Override
	public void dispose () {
		Render.batch.dispose();
	}
	
}

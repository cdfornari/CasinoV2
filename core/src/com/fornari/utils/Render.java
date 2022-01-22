package com.fornari.utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.fornari.casino.Casino;

public class Render {
	public static SpriteBatch batch;
	public static Casino casino;
	
	public static void mostrarMensaje(Stage stage, String titulo, String texto, String textoBoton) {
		Skin skin = new Skin(Gdx.files.internal("shade/skin/uiskin.json"));
		Dialog error = new Dialog(titulo,skin);
		error.text(texto);
		error.button(textoBoton);
		error.scaleBy(0.4f);
		error.layout();
		error.show(stage);
	}
}

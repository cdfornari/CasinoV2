package com.fornari.utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.fornari.casino.Casino;
import com.fornari.screens.GameScreen;

public class Render {
	public static SpriteBatch batch;
	public static Casino casino;
	
	public static void mostrarMensaje(Stage stage, String titulo, String texto, String textoBoton) {
		Skin skin = new Skin(Gdx.files.internal("shade/skin/uiskin.json"));
		Dialog error = new Dialog(titulo,skin);
		if(texto.equals("Reparte el jugador") || texto.equals("Reparte la computadora"))
			error.setName("reparte");
		else
			error.setName("mensaje");
		error.text(texto);
		error.button(textoBoton);
		error.scaleBy(0.4f);
		error.layout();
		if(error.getName().equals("mensaje"))
		error.show(stage);
		else {
			error.setSize(227, 108);
			error.setPosition(778, 250);
			stage.addActor(error);
		}
	}

}

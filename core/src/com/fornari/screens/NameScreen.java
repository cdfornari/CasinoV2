package com.fornari.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.fornari.utils.Config;
import com.fornari.utils.Imagen;
import com.fornari.utils.Render;
import com.fornari.utils.Texto;

public class NameScreen implements Screen{
	
	Imagen fondo;
	Texto titulo;
	Texto texto;
	TextField userInput;
	Stage stage;

	@Override
	public void show() {
		fondo = new Imagen("Fondos/fondo_menu.jpg");
		fondo.setSize(Config.anchoPantalla,Config.altoPantalla);
		titulo = new Texto(Config.pathFuenteTitulo,72,Color.BLACK);
		texto = new Texto(Config.pathFuenteTexto,52,Color.BLACK);
		//userInput = new TextField("",new Skin(Gdx.files.internal("json/uiskin.json")));
		stage = new Stage();
	}

	@Override
	public void render(float delta) {
		Render.batch.begin();
		fondo.dibujar();
		titulo.dibujar("Bienvenido al juego Casino", 480, 900);
		texto.dibujar("Ingrese su nombre", 420, 680);
		//userInput.setPosition(420, 620);
		//userInput.setSize(200, 40);
		//userInput.draw(Render.batch, 100);
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

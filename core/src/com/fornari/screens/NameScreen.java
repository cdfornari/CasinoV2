package com.fornari.screens;
import com.fornari.utils.Render;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.fornari.archivos.Archivo;
import com.fornari.casino.Carta;
import com.fornari.casino.Casino;
import com.fornari.casino.Jugador;
import com.fornari.casino.Mazo;
import com.fornari.utils.Config;
import com.fornari.utils.Imagen;
import com.fornari.utils.Texto;

public class NameScreen implements Screen{
	
	private Imagen fondo;
	private Texto titulo;
	private Texto texto;
	private Skin skin;
	private TextField userInput;
	private Stage stage;
	private TextButton btnContinuar;
	private Archivo archivo=new Archivo();

	@Override
	public void show() {
		fondo = new Imagen("Fondos/fondo_menu.jpg","img");
		fondo.setSize(Config.anchoPantalla,Config.altoPantalla);
		titulo = new Texto(Config.pathFuenteTitulo,72,Color.BLACK);
		texto = new Texto(Config.pathFuenteTexto,46,Color.BLACK);
		skin = new Skin(Gdx.files.internal(Config.pathSkin));
		//textfield
		userInput = new TextField("",skin);
		userInput.setPosition(480, 480);
		userInput.setSize(840, 80);
		TextField.TextFieldStyle textFieldStyle = skin.get(TextField.TextFieldStyle.class);
		textFieldStyle.font.getData().scale(1.1f);
		//boton
		btnContinuar = new TextButton("Continuar",skin);
		btnContinuar.setPosition(765, 370);
		btnContinuar.setSize(250,50);
		btnContinuar.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				if(userInput.getText().length() > 0) {
					Config.setNombre(userInput.getText());
					Casino.ventana.setScreen(new MenuScreen());
					archivo.vaciarArchivo(new Mazo(), new ArrayList<Carta>(), new Jugador(), new Jugador(), new ArrayList<Carta>());
				}
				else
					Render.mostrarMensaje(stage,"Error","Ingresa un nombre valido","Ok");
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				return true;
			}
		});
		//stage
		stage = new Stage();
		stage.addActor(btnContinuar);
		stage.addActor(userInput);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Render.batch.begin();
		fondo.dibujar();
		titulo.dibujar("Bienvenido al juego Casino", 480, 900);
		texto.dibujar("Ingrese su nombre", 480, 680);
		stage.act(delta);
		stage.draw();
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
		stage.dispose();
		
	}
	
}

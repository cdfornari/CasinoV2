package com.fornari.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.fornari.casino.Casino;
import com.fornari.casino.Jugador;
import com.fornari.utils.Config;
import com.fornari.utils.Imagen;
import com.fornari.utils.Render;
import com.fornari.utils.Texto;
/**
 * Pantalla de Fin del juego
 * @author Carlos Fornari, Sandro Portanova, Maria Porras
 *
 */
public class EndScreen implements Screen{
	private Imagen fondo = new Imagen("Fondos/fondomesa.jpg","img");
	private Jugador jugador;
	private int puntajeJugador;
	private Jugador computadora;
	private int puntajeComputadora;
	private String mensaje;
	private Texto titulo = new Texto(Config.pathFuenteTitulo,72,Color.WHITE);
	private Texto textoJugador = new Texto(Config.pathFuenteTexto,46,Color.WHITE);
	private Texto textoComputadora = new Texto(Config.pathFuenteTexto,46,Color.WHITE);
	private TextButton btnNewGame;
	private TextButton btnSalir;
	private Stage stage = new Stage();
	private Skin skin = new Skin(Gdx.files.internal(Config.pathSkin));

	public EndScreen(String mensaje, Jugador jugador, Jugador computadora, int puntajeJugador, int puntajeComputadora) {
		this.jugador = jugador;
		this.computadora = computadora;
		this.mensaje = mensaje;
		this.puntajeJugador = puntajeJugador;
		this.puntajeComputadora = puntajeComputadora;
	}
	/**
	 * Se ejecuta cuando se muestra la pantalla
	 */
	@Override
	public void show() {
		fondo.setSize(Config.anchoPantalla, Config.altoPantalla);
		btnNewGame = new TextButton("Nueva partida",skin);
		btnNewGame.setSize(250,50);
		btnNewGame.setPosition(600, 100);
		btnNewGame.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				Casino.ventana.setScreen(new GameScreen(true));
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				return true;
			}
		});
		stage.addActor(btnNewGame);
		btnSalir = new TextButton("Salir",skin);
		btnSalir.setSize(250,50);
		btnSalir.setPosition(900, 100);
		btnSalir.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				Gdx.app.exit();
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				return true;
			}
		});
		stage.addActor(btnSalir);
		for(int i = 0; i < jugador.getCartasRecogidas().size(); i++) {
			jugador.getCartasRecogidas().get(i).setImagen(new Imagen(jugador.getCartasRecogidas().get(i).buildPath(),"img"));
			jugador.getCartasRecogidas().get(i).getImagen().setSize(100, 73);
		}
		for(int i = 0; i < computadora.getCartasRecogidas().size(); i++) {
			computadora.getCartasRecogidas().get(i).setImagen(new Imagen(computadora.getCartasRecogidas().get(i).buildPath(),"img"));
			computadora.getCartasRecogidas().get(i).getImagen().setSize(100, 73);
		}
		Gdx.input.setInputProcessor(stage);
	}
	/**
     * Se ejecuta en cada frame
     */
	@Override
	public void render(float delta) {
		Render.batch.begin();
		fondo.dibujar();
		titulo.dibujar(mensaje, (Config.anchoPantalla/2.25f)-(titulo.getAncho()/2), 900);
		textoJugador.dibujar(Config.userName + ": " + puntajeJugador,200,800);
		textoComputadora.dibujar("Computadora: " + puntajeComputadora, 200, 500);
		int xCartas = 200;
		int yCartas = 650;
		for(int i = 0; i < jugador.getCartasRecogidas().size(); i++) {
			jugador.getCartasRecogidas().get(i).getImagen().dibujar(xCartas,yCartas);
			xCartas += 100;
			if(xCartas > Config.anchoPantalla - 100) {
				yCartas = 550;
				xCartas = 200;
			}
		}
		xCartas = 200;
		yCartas = 350;
		for(int i = 0; i < computadora.getCartasRecogidas().size(); i++) {
			computadora.getCartasRecogidas().get(i).getImagen().dibujar(xCartas,yCartas);
			xCartas += 100;
			if(xCartas > Config.anchoPantalla - 100) {
				yCartas = 250;
				xCartas = 200;
			}
		}
		stage.act(delta);
		stage.draw();
		Render.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	/**
     * Limpia toda la pantalla
     */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
	}

}

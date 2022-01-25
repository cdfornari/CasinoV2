package com.fornari.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.fornari.casino.*;
import com.fornari.utils.Config;
import com.fornari.utils.Imagen;
import com.fornari.utils.Render;
import com.fornari.utils.Texto;

public class GameScreen implements Screen{
	private Mazo mazo = new Mazo();
	private Jugador jugador= new Jugador();
	private Jugador computadora = new Jugador();
	private ArrayList<Carta> mesa = new ArrayList<Carta>();
	private Imagen fondo = new Imagen("Fondos/fondomesa.jpg");
	private Texto contadorMazo = new Texto(Config.pathFuenteTexto,42,Color.WHITE);
	private Imagen imagenMazo = new Imagen("Cards/cardBack_red5.png");
	private Texto contadorRecogidasJugador = new Texto(Config.pathFuenteTexto,42,Color.WHITE);
	private Imagen recogidasJugador = new Imagen("Cards/cardBack_red5.png");
	private Texto contadorRecogidasComputadora = new Texto(Config.pathFuenteTexto,42,Color.WHITE);
	private Imagen recogidasComputadora = new Imagen("Cards/cardBack_red5.png");
	
	@Override
	public void show() {
		fondo.setSize(Config.anchoPantalla, Config.altoPantalla);
		mazo.repartir(this.jugador.getCartas());
		mazo.repartir(this.computadora.getCartas());
		mazo.repartir(mesa);
		for(int i = 0; i < 4; i++)
			computadora.getCartas().get(i).setImagen(new Imagen("Cards/cardBack_red5.png"));
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Render.batch.begin();
		fondo.dibujar();
		imagenMazo.dibujar(300, 415);
		contadorMazo.dibujar(""+mazo.getSize(), 300+(140/2)-(contadorMazo.getAncho()/2), 415+(190/2)+(contadorMazo.getAlto()/2));
		recogidasJugador.dibujar(1450,100);
		contadorRecogidasJugador.dibujar(""+jugador.getCartasRecogidas().size(), 1450+(140/2)-(contadorRecogidasJugador.getAncho()/2), 100+(190/2)+(contadorRecogidasJugador.getAlto()/2));
		recogidasComputadora.dibujar(1450,750);
		contadorRecogidasComputadora.dibujar(""+jugador.getCartasRecogidas().size(), 1450+(140/2)-(contadorRecogidasJugador.getAncho()/2), 750+(190/2)+(contadorRecogidasJugador.getAlto()/2));
		int xCartas = 600;
		for(int i = 0; i < computadora.getCartas().size(); i++) {
			computadora.getCartas().get(i).getImagen().dibujar(xCartas, 750);
			xCartas += 175;
		}
		xCartas = 600;
		for(int i = 0; i < jugador.getCartas().size(); i++) {
			jugador.getCartas().get(i).getImagen().dibujar(xCartas, 100);
			xCartas += 175;
		}
		xCartas = 600;
		for(int i = 0; i < mesa.size(); i++) {
			mesa.get(i).getImagen().dibujar(xCartas, 415);
			xCartas += 175;
		}
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

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}

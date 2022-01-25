package com.fornari.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Screen;
import com.fornari.casino.*;
import com.fornari.utils.Config;
import com.fornari.utils.Imagen;
import com.fornari.utils.Render;

public class GameScreen implements Screen{
	private Mazo mazo;
	private Jugador jugador;
	private Jugador computadora;
	private ArrayList<Carta> mesa;
	private Imagen fondo;
	
	@Override
	public void show() {
		fondo = new Imagen("Fondos/fondomesa.jpg");
		fondo.setSize(Config.anchoPantalla, Config.altoPantalla);
		mazo = new Mazo();
		jugador = new Jugador();
		computadora = new Jugador();
		mesa = new ArrayList<Carta>();
		mazo.repartir(this.jugador.getCartas());
		mazo.repartir(this.computadora.getCartas());
		mazo.repartir(mesa);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Render.batch.begin();
		fondo.dibujar();
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

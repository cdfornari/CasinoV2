package com.fornari.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.fornari.casino.Casino;
import com.fornari.utils.Config;
import com.fornari.utils.Imagen;
import com.fornari.utils.Render;
import com.fornari.utils.Texto;

public class MenuScreen implements Screen{
	private Imagen fondo;
	private Texto usuario;
	private Texto opciones[] = new Texto[3];
	private String textos[] = {"Jugar nueva partida","Continuar partida anterior","Salir"};
	private ClickMouse ClickMouse = new ClickMouse();
	private int espacio; //Espacio en y entre cada opcion del menu
	private int alto; //altura a la que comienzan las opciones del menu
	private int opcion;


	@Override
	public void show() {
		fondo = new Imagen("Fondos/fondo9.jpg");
		fondo.setSize(Config.anchoPantalla,Config.altoPantalla);
		usuario = new Texto(Config.pathFuenteTitulo, 80, Color.GOLD);
		Gdx.input.setInputProcessor(ClickMouse);
		for(int i = 0; i < opciones.length; i++) {
			if(i != 1 || existeArchivo()) {
				opciones[i] = new Texto(Config.pathFuenteTexto,56,Color.WHITE);
			}
		}
		if(existeArchivo()) 
			espacio = 150;
		else 
			espacio = 100;
	}

	@Override
	public void render(float delta) {
		Render.batch.begin();
		fondo.dibujar();
		usuario.dibujar("Bienvenido, " + Config.userName, 480, 900);
		if(existeArchivo()) 
			alto = 650;
		else 
			alto = 550;
		for(int i = 0; i < opciones.length; i++) {
			if(i != 1 || existeArchivo()) {
				opciones[i].dibujar(textos[i],(Config.anchoPantalla/2)-(opciones[i].getAncho()/2),alto);
				alto = alto - espacio;
			}
		}
		Render.batch.end();
		for(int i = 0; i < opciones.length; i++) {
			if(i != 1 || existeArchivo())
				if(ClickMouse.getPosicionX()>=opciones[i].getX() && ClickMouse.getPosicionX()<= opciones[i].getX() + opciones[i].getAncho()) 
					if(ClickMouse.getPosicionY() >= opciones[i].getY() + 1 * opciones[i].getAlto() && ClickMouse.getPosicionY() <= opciones[i].getY() + 2.5 * opciones[i].getAlto()) {
						opciones[i].setColor(Color.RED);
						opcion = i + 1;
					}
					else
						opciones[i].setColor(Color.WHITE);
		}
		for(int i = 0; i < opciones.length; i++) {
			if(i != 1 || existeArchivo())
				if(i+1 == opcion) 
					opciones[i].setColor(Color.RED);
				else
					opciones[i].setColor(Color.WHITE);
		}
		if(ClickMouse.isClick())
			if(opcion==1)
				Casino.ventana.setScreen(new GameScreen());
			else if(opcion == 2)
				Casino.ventana.setScreen(new GameScreen()); //TODO: cargar archivo
			else if(opcion == 3)
				Gdx.app.exit();
	}
	
	public boolean existeArchivo() {
		return true;
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

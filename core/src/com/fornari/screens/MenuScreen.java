package com.fornari.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.fornari.casino.Casino;
import com.fornari.utils.Config;
import com.fornari.utils.Imagen;
import com.fornari.utils.Render;
import com.fornari.utils.Texto;

public class MenuScreen implements Screen{
	Imagen fondo;
	Texto usuario,jugarPartida,salir,pos;
	ClicMouse clicMouse = new ClicMouse();
	int espacio=50; //Espacio entre cada opcion del menu
	int mitadAncho= Config.anchoPantalla/2;
	int opcion;


	@Override
	public void show() {
		fondo = new Imagen("Fondos/fondo9.jpg");
		fondo.setSize(Config.anchoPantalla,Config.altoPantalla);
		usuario = new Texto(Config.pathFuenteTitulo, 80, Color.GOLD);
		jugarPartida= new Texto(Config.pathFuenteTexto,65,Color.LIGHT_GRAY);
		salir = new Texto(Config.pathFuenteTexto,65,Color.LIGHT_GRAY);	
		Gdx.input.setInputProcessor(clicMouse);	
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Render.batch.begin();
		fondo.dibujar();
		usuario.dibujar("Bienvenido, usuario: ", mitadAncho-900/2, 1025);
		jugarPartida.dibujar("Jugar nueva partida", mitadAncho-(int)jugarPartida.getAncho()/2, Config.altoPantalla/2-(int)jugarPartida.getAlto()/2);
		salir.dibujar("Salir", (int)mitadAncho-(int)salir.getAncho()/2, (int)jugarPartida.getY()-(int)jugarPartida.getAlto()-espacio);
		Render.batch.end();
		
		if(clicMouse.getPosicionX()>=jugarPartida.getX() && clicMouse.getPosicionX()<= (jugarPartida.getX()+jugarPartida.getAncho()) ) {
			if( (clicMouse.getPosicionY() >= (jugarPartida.getY() - jugarPartida.getAlto() ) ) && (clicMouse.getPosicionY() <= jugarPartida.getY()) )
				cambiarColor(1);
			}
		
		if(clicMouse.getPosicionX()>=salir.getX() && clicMouse.getPosicionX()<= (salir.getX()+salir.getAncho()) ) {
			if( (clicMouse.getPosicionY() >= (salir.getY() - salir.getAlto() ) ) && (clicMouse.getPosicionY() <= salir.getY()) )
				cambiarColor(2);
		}
		
		if(clicMouse.isClick())
			if(opcion==1)
				Casino.ventana.setScreen(new GameScreen());
			else
				Gdx.app.exit();
	}
		
	
	public void cambiarColor(int opcion) {
		this.opcion=opcion;
		if(opcion==1) {
			jugarPartida.setColor(Color.RED);
			salir.setColor(Color.WHITE);
		} else {
			jugarPartida.setColor(Color.WHITE);
			salir.setColor(Color.RED);
		}
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

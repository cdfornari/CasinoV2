package com.fornari.screens;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.fornari.archivos.Archivo;
import com.fornari.casino.*;
import com.fornari.utils.Config;
import com.fornari.utils.Imagen;
import com.fornari.utils.Render;
import com.fornari.utils.SelectMovimiento;
import com.fornari.utils.Texto;

public class GameScreen implements Screen{
	private Mazo mazo = new Mazo();
	private Jugador jugador= new Jugador();
	private Jugador computadora = new Jugador();
	private ArrayList<Carta> mesa = new ArrayList<Carta>();
	private Imagen fondo = new Imagen("Fondos/fondomesa.jpg","img");
	private Texto contadorMazo = new Texto(Config.pathFuenteTexto,42,Color.WHITE);
	private Imagen imagenMazo = new Imagen("Cards/cardBack_red5.png","img");
	private Texto contadorRecogidasJugador = new Texto(Config.pathFuenteTexto,42,Color.WHITE);
	private Imagen recogidasJugador = new Imagen("Cards/cardBack_red5.png","img");
	private Texto contadorRecogidasComputadora = new Texto(Config.pathFuenteTexto,42,Color.WHITE);
	private Imagen recogidasComputadora = new Imagen("Cards/cardBack_red5.png","img");
	private Stage stage = new Stage();
	private Texto seleccionada = new Texto(Config.pathFuenteTitulo,82,Color.BLACK);
	private ArrayList<Carta> seleccionadas = new ArrayList<Carta>();
	private TextButton btnLanzar = new TextButton("Lanzar cartas",new Skin(Gdx.files.internal(Config.pathSkin)));
	private boolean turno;
	private boolean firstTime=true;
	private Archivo archivo=new Archivo();
	
	public GameScreen() {
		
		if(Archivo.existeArchivo()) {
			archivo.cargarArchivo(mazo, mesa, jugador, computadora, seleccionadas);
			mazo=archivo.getArbol().buscarNodoEnArbol("MAZO").getMazo();
			mesa=archivo.getArbol().buscarNodoEnArbol("MESA").getListaCarta();
			jugador=archivo.getArbol().buscarNodoEnArbol("JUGADOR").getJugador();
			computadora=archivo.getArbol().buscarNodoEnArbol("COMPUTADORA").getJugador();
			seleccionadas=archivo.getArbol().buscarNodoEnArbol("SELECCIONADAS").getListaCarta();
		}
	}
	
	static void removeAllListeners(Actor actor) {
        Array<EventListener> listeners = new Array<>(actor.getListeners());
        for (EventListener listener : listeners)
            actor.removeListener(listener);
    }
	
	public void updateGameState(boolean firstTime) {
		if(!firstTime) {
			seleccionadas.clear();
			jugador.unselectAll();
			for(int i = 0; i < mesa.size(); i++) {
				removeAllListeners(mesa.get(i).getImagen().getBtn());
				mesa.get(i).getImagen().getBtn().remove();
			}
			for(int i = 0; i < jugador.getCartas().size(); i++) {
				removeAllListeners(jugador.getCartas().get(i).getImagen().getBtn());
				jugador.getCartas().get(i).getImagen().getBtn().remove();
			}
		}
		for(int i = 0; i < computadora.getCartas().size(); i++) 
			computadora.getCartas().get(i).setImagen(new Imagen("Cards/cardBack_red5.png","img"));
		for(int i = 0; i < mesa.size(); i++) {
			mesa.get(i).getImagen().getBtn().addListener(new ClickListener() {
				@Override
				public void touchUp(InputEvent e, float x, float y, int point, int button) {
					if(turno) {
						if(seleccionadas.size() == 0) 
							Render.mostrarMensaje(stage,"Error","Primero selecciona cartas para hacer un movimiento","Ok");
						else {
							SelectMovimiento select = new SelectMovimiento(stage);
							if(select.getMovimiento() == "recoger") {
							
							}else if(select.getMovimiento() == "emparejar"){
								
							}else if(select.getMovimiento() == "doblar") {
								
							}
							turno = false;
						}
					}
				}
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					return true;
				}
			});
			mesa.get(i).getImagen().getBtn().setPosition(600 + i * 175, 415);
			mesa.get(i).getImagen().getBtn().setSize(140, 190);
			stage.addActor(mesa.get(i).getImagen().getBtn());
		}
		for(int i = 0; i < jugador.getCartas().size(); i++) {
			final int index = i;
			jugador.getCartas().get(i).getImagen().getBtn().addListener(new ClickListener() {
				private int i = index;
				@Override
				public void touchUp(InputEvent e, float x, float y, int point, int button) {
					if(turno) {
						jugador.getCartas().get(i).toggleSelected();
						if(jugador.getCartas().get(i).isSelected())
							seleccionadas.add(jugador.getCartas().get(i));
						else
							seleccionadas.remove(seleccionadas.indexOf(jugador.getCartas().get(i)));
					}
				}
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					return true;
				}
			});
			jugador.getCartas().get(i).getImagen().getBtn().setPosition(600 + i * 175, 100);
			jugador.getCartas().get(i).getImagen().getBtn().setSize(140, 190);
			stage.addActor(jugador.getCartas().get(i).getImagen().getBtn());
		}
	}
	
	@Override
	public void show() {
		fondo.setSize(Config.anchoPantalla, Config.altoPantalla);
		if(new Random(2).nextInt() == 0)
			this.turno = true;
		else
			this.turno = false;
		if((jugador.getCartas().size()==0 && computadora.getCartas().size()==0)) { //Reparte solo cuando ambos se queden sin cartas
			mazo.repartir(this.jugador.getCartas());
			mazo.repartir(this.computadora.getCartas());
		}
		if(!Archivo.existeArchivo()) //Solo se reparte a la mesa cuando no se haya jugado antes
			mazo.repartir(mesa);
		else
			firstTime=false;
		
		updateGameState(firstTime);
		btnLanzar.setPosition(300,170);
		btnLanzar.setSize(250,50);
		btnLanzar.addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				if(seleccionadas.size() == 1) {
					jugador.lanzarCarta(mesa, seleccionadas.get(0));
					seleccionadas.remove(0);
					updateGameState(false);
					System.out.println("PROBLEMA ");
					archivo.vaciarArchivo(mazo, mesa, jugador, computadora, seleccionadas);
				}
				else 
					Render.mostrarMensaje(stage, "Error", "Seleccione una y solo una carta para lanzar", "Ok");
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				return true;
			}
		});
		stage.addActor(btnLanzar);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
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
		for(int i = 0; i < jugador.getCartas().size(); i++) {
			if(jugador.getCartas().get(i).isSelected())
				seleccionada.dibujar("*", ((600 + i * 175) + (140/2) - (seleccionada.getAncho()/2)), 290 + seleccionada.getAlto()/2);
		}
		if(!turno) {
			//movimientos computadora
			//archivo.vaciarArchivo(mazo, mesa, jugador, computadora, seleccionadas);
			turno = true;
		}
		if(jugador.getCartas().size() == 0 && computadora.getCartas().size() == 0 && mazo.getSize() > 0) {
			mazo.repartir(this.jugador.getCartas());
			mazo.repartir(this.computadora.getCartas());
		}
		if(jugador.getCartas().size() == 0 && computadora.getCartas().size() == 0 && mazo.getSize() == 0) {
			//termina juego
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

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
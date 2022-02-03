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
	private boolean turno, nuevaPartida;
	private Archivo archivo=new Archivo();
	
	public GameScreen(boolean nuevaPartida) {
		this.nuevaPartida=nuevaPartida;
		if(Archivo.existeArchivo() && !nuevaPartida) {
			archivo.cargarArchivo(mazo, mesa, jugador, computadora, seleccionadas);
			mazo=archivo.getArbol().buscarNodoEnArbol("MAZO").getMazo();
			mesa=archivo.getArbol().buscarNodoEnArbol("MESA").getListaCarta();
			jugador=archivo.getArbol().buscarNodoEnArbol("JUGADOR").getJugador();
			computadora=archivo.getArbol().buscarNodoEnArbol("COMPUTADORA").getJugador();
			seleccionadas=archivo.getArbol().buscarNodoEnArbol("SELECCIONADAS").getListaCarta();
		}
	}
	
	private void removeAllListeners(Actor actor) {
        Array<EventListener> listeners = new Array<>(actor.getListeners());
        for (EventListener listener : listeners)
            actor.removeListener(listener);
    }
	
	private void clearActors() {
		jugador.unselectAll();
		for(int i = 0; i < mesa.size(); i++) {
			removeAllListeners(mesa.get(i).getImagen().getBtn());
			mesa.get(i).getImagen().getBtn().remove();
			if(mesa.get(i).isSelected())
				mesa.get(i).toggleSelected();
		}
		for(int i = 0; i < jugador.getCartas().size(); i++) {
			removeAllListeners(jugador.getCartas().get(i).getImagen().getBtn());
			jugador.getCartas().get(i).getImagen().getBtn().remove();
		}
	}
	public void updateGameState() {
		seleccionadas.clear();
		for(int i = 0; i < computadora.getCartas().size(); i++) 
			computadora.getCartas().get(i).setImagen(new Imagen("Cards/cardBack_red5.png","img"));
		for(int i = 0; i < mesa.size(); i++) {
			final int index = i;
			mesa.get(i).getImagen().getBtn().addListener(new ClickListener() {
				@Override
				public void touchUp(InputEvent e, float x, float y, int point, int button) {
					if(turno) {
						mesa.get(index).toggleSelected();
						if(mesa.get(index).isSelected())
							seleccionadas.add(mesa.get(index));
						else
							seleccionadas.remove(seleccionadas.indexOf(mesa.get(index)));
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
				@Override
				public void touchUp(InputEvent e, float x, float y, int point, int button) {
					if(turno) {
						final SelectMovimiento select = new SelectMovimiento(stage);
						select.addListener(new ClickListener() {
							@Override 
							public void touchUp(InputEvent e, float x, float y, int point, int button) {
								if(select.getMovimiento() == "lanzar") {
									if(seleccionadas.size() == 0) {
										if(jugador.getIdEmparejamiento() == "000") {
											clearActors();
											jugador.lanzarCarta(mesa, index);
											archivo.vaciarArchivo(mazo, mesa, jugador, computadora, seleccionadas);
											updateGameState();
											turno = false;
										}else
											Render.mostrarMensaje(stage, "Error", "No puedes lanzar con un emparejamiento activo", "Ok");
									}
									else {
										if(!jugador.getIdEmparejamiento().equals("000"))
										Render.mostrarMensaje(stage, "Error", "Tiene un emparejamiento activo", "Ok");	
										else if(seleccionadas.size()!=0)
											Render.mostrarMensaje(stage, "Error", "Tiene cartas en mesa seleccionadas", "Ok");	

									}
								}else if(select.getMovimiento() == "recoger") {
									if(seleccionadas.size() == 0) 
										Render.mostrarMensaje(stage,"Error","Primero selecciona cartas para hacer un movimiento","Ok");
									else {
										if(jugador.validarCartasRecoger(seleccionadas, jugador.getCartas().get(index))) {
											clearActors();
											jugador.recogerCarta(mesa, seleccionadas, jugador.getCartas().get(index),computadora);
											archivo.vaciarArchivo(mazo, mesa, jugador, computadora, seleccionadas);
											updateGameState();
											turno = false;
										}else 
											Render.mostrarMensaje(stage, "Error", "No puede recoger", "Ok");
									}
								}else if(select.getMovimiento() == "emparejar"){
									if(seleccionadas.size() == 0) 
										Render.mostrarMensaje(stage,"Error","Primero selecciona cartas para hacer un movimiento","Ok");
									else {
										if(jugador.validarCartasEmparejar(seleccionadas, jugador.getCartas().get(index))) {
											clearActors();
											jugador.emparejarCarta(mesa, seleccionadas, jugador.getCartas().get(index),computadora);
											archivo.vaciarArchivo(mazo, mesa, jugador, computadora, seleccionadas);
											updateGameState();
											turno = false;
										}else 
											Render.mostrarMensaje(stage, "Error", "No puede emparejar", "Ok");
									}
								}else if(select.getMovimiento() == "doblar") {
									if(seleccionadas.size() == 0) 
										Render.mostrarMensaje(stage,"Error","Primero selecciona cartas para hacer un movimiento","Ok");
									else {
										if(jugador.validarCartaDoblarse(seleccionadas, jugador.getCartas().get(index))) {
											clearActors();
											jugador.doblarCarta(mesa, seleccionadas, jugador.getCartas().get(index),computadora);
											archivo.vaciarArchivo(mazo, mesa, jugador, computadora, seleccionadas);
											updateGameState();
											turno = false;
										}else 
											Render.mostrarMensaje(stage, "Error", "No puede doblar", "Ok");
									}
								}
							}
							@Override
							public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
								return true;
							}
						});
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
		if(nuevaPartida)//Solo se reparte a la mesa cuando no se haya jugado antes
			mazo.repartir(mesa);
		updateGameState();
		archivo.vaciarArchivo(mazo, mesa, jugador, computadora, seleccionadas);
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
		contadorRecogidasComputadora.dibujar(""+computadora.getCartasRecogidas().size(), 1450+(140/2)-(contadorRecogidasJugador.getAncho()/2), 750+(190/2)+(contadorRecogidasJugador.getAlto()/2));
		int xCartas = 600;
		for(int i = 0; i < computadora.getCartas().size(); i++) {
			computadora.getCartas().get(i).getImagen().dibujar(xCartas, 750);
			xCartas += 175;
		}
		for(int i = 0; i < mesa.size(); i++) {
			if(mesa.get(i).isSelected())
				seleccionada.dibujar("*", ((600 + i * 175) + (140/2) - (seleccionada.getAncho()/2)), 605 + seleccionada.getAlto()/2);
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
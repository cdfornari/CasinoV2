package com.fornari.screens;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
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
import com.fornari.utils.Ventana;

public class GameScreen implements Screen{
	private Mazo mazo = new Mazo();
	private Jugador jugador= new Jugador();
	private Jugador computadora = new Jugador();
	private ArrayList<Carta> mesa = new ArrayList<Carta>();
	private Imagen fondo = new Imagen("Fondos/fondomesa.jpg","img");
	private Texto contadorMazo = new Texto(Config.pathFuenteTexto,42,Color.WHITE);
	private Imagen imagenMazo = new Imagen("Cards/cardBack_red5.png","img");
	private Texto contadorRecogidasJugador = new Texto(Config.pathFuenteTexto,42,Color.WHITE);
	private Texto contadorRecogidasComputadora = new Texto(Config.pathFuenteTexto,42,Color.WHITE);
	private Stage stage = new Stage();
	private Texto seleccionada = new Texto(Config.pathFuenteTitulo,82,Color.BLACK);
	private ArrayList<Carta> seleccionadas = new ArrayList<Carta>();
	private boolean nuevaPartida,mostrarRecogidas=true;
	private Archivo archivo=new Archivo();
	private Texto puntajeJugador = new Texto(Config.pathFuenteTexto,42,Color.WHITE);
	private Texto puntajeComputadora = new Texto(Config.pathFuenteTexto,42,Color.WHITE);
	private Ventana ventana;
	private Imagen ventanaRecogidasJugador = new Imagen("Cards/cardBack_red5.png","btn");
	private Imagen ventanaRecogidasComputadora = new Imagen("Cards/cardBack_red5.png","btn");
	private Imagen btnSalir = new Imagen("btn-salir.png","btn");
	private int loteCartas=0, maxCartas=3, contadorCartasMostrar=0;
	private Imagen flechaDerecha= new Imagen("Fondos/fled.png","btn");
	private Imagen flechaIzquierda= new Imagen("Fondos/flecha.png","btn");
	private Texto spriteBasura = new Texto(Config.pathFuenteTexto,42,Color.WHITE);
	//Para el archivo
	private boolean turno, reparte;
	private TipoJugador ultimoEnRecoger=TipoJugador.none, ultimoJugar=TipoJugador.none;
	
	//Funcion para crear la ventana emergente de recogidas
	public void crearVentanasRecogidas(Imagen ventanaRecogidas, int x, int y, final ArrayList<Carta> recogidas, final String tipoJugador) {
		ventanaRecogidas.getBtn().setPosition(x, y);
		ventanaRecogidas.getBtn().setSize(140, 190); 
		stage.addActor(ventanaRecogidas.getBtn());
		ventanaRecogidas.getBtn().addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				mostrarRecogidas=false;
				ventana=new Ventana(recogidas, tipoJugador);
				ventana.setPosition(Config.anchoPantalla/2, Config.altoPantalla/2);
				Button btnCerrar = new TextButton("CERRAR", new Skin(Gdx.files.internal(Config.pathSkin)));
				btnCerrar.setSize(500, 500);
				ventana.getDialog().getContentTable().add(btnCerrar).spaceTop(30).row();
				btnCerrar.addListener(new ClickListener() {
					@Override
					public void touchUp(InputEvent e, float x, float y, int point, int button) {
						stage.getRoot().removeActor(ventana.getDialog());
						mostrarRecogidas=true;
					}
					@Override
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
						return true;
					}
				});
				stage.addActor(ventana.getDialog());
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				return true;
			}
		});
	}

	public GameScreen(boolean nuevaPartida) {
		this.nuevaPartida=nuevaPartida;
		if(Archivo.existeArchivo() && !nuevaPartida) {
			archivo.cargarArchivo(mazo, mesa, jugador, computadora, seleccionadas, turno, reparte, ultimoEnRecoger, ultimoJugar);
			mazo=archivo.getArbol().buscarNodoEnArbol("MAZO").getMazo();
			mesa=archivo.getArbol().buscarNodoEnArbol("MESA").getListaCarta();
			jugador=archivo.getArbol().buscarNodoEnArbol("JUGADOR").getJugador();
			computadora=archivo.getArbol().buscarNodoEnArbol("COMPUTADORA").getJugador();
			seleccionadas=archivo.getArbol().buscarNodoEnArbol("SELECCIONADAS").getListaCarta();
			turno=archivo.getArbol().buscarNodoEnArbol("UPDATE").isTurno();
			reparte=archivo.getArbol().buscarNodoEnArbol("UPDATE").isReparte();
			ultimoEnRecoger=archivo.getArbol().buscarNodoEnArbol("UPDATE").getUltimoRecoger();
			ultimoJugar=archivo.getArbol().buscarNodoEnArbol("UPDATE").getUltimoJugar();
		}
	}

	public void mostarCartasComputadora() {
		System.out.print("COMPUTADORA:");
		for(int i = 0; i<computadora.getCartas().size(); i++)
			if(computadora.getCartas()!=null || computadora.getCartas().get(i)!=null)
				System.out.print("  "+computadora.getCartas().get(i).getValor()+" "+computadora.getCartas().get(i).getFigura());
		System.out.println("");
	}
	
	public static void removeAllListeners(Actor actor) {
        Array<EventListener> listeners = new Array<>(actor.getListeners());
        for (EventListener listener : listeners)
            actor.removeListener(listener);
    }
	
	private void clearActors(boolean eliminarIsSelected) {
		jugador.unselectAll();
		for(int i = 0; i < mesa.size(); i++) {
			removeAllListeners(mesa.get(i).getImagen().getBtn());
			mesa.get(i).getImagen().getBtn().remove();
			if(mesa.get(i).isSelected() && eliminarIsSelected)
				mesa.get(i).toggleSelected();
		}
		for(int i = 0; i < jugador.getCartas().size(); i++) {
			removeAllListeners(jugador.getCartas().get(i).getImagen().getBtn());
			jugador.getCartas().get(i).getImagen().getBtn().remove();
		}
		for(int i = 0; i < computadora.getCartas().size(); i++) 
			computadora.getCartas().get(i).getImagen().getBtn().remove();
	}
	public void updateGameState() {
		int espacio=0, ancho=140, contadorCartasMostrar=0; //Muestra 8 cartas. 
		mostarCartasComputadora();
		for(int i = 0; i < computadora.getCartas().size(); i++) {
			computadora.getCartas().get(i).setImagen(new Imagen("Cards/cardBack_red5.png","btn"));
			computadora.getCartas().get(i).getImagen().getBtn().setSize(140, 190);
			computadora.getCartas().get(i).getImagen().getBtn().setPosition(600 + i * 175, 750);
			stage.addActor(computadora.getCartas().get(i).getImagen().getBtn());
		}
		Jugador.ordenarEmparejamientos(mesa); //Ordena el mazo para que las cartas emparejadas queden de forma adyacente en la lista
		for(int i = loteCartas; i < mesa.size(); i++) {
			mesa.get(i).setImagen(new Imagen(mesa.get(i).buildPath(),"btn"));
			final int index = i;
			mesa.get(i).getImagen().getBtn().addListener(new ClickListener() {
				@Override
				public void touchUp(InputEvent e, float x, float y, int point, int button) {
					if(turno) {
						mesa.get(index).toggleSelected();
						if(mesa.get(index).isSelected() && !seleccionadas.contains(mesa.get(index)))
							seleccionadas.add(mesa.get(index));
						else 
							seleccionadas.remove(mesa.get(index));	
					}
				}
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					return true;
				}
			});
			//Funcion para imprimir emparejadas en la mesa
			if(i!=0 && (mesa.get(i).getIdEmparejamiento().equals(mesa.get(i-1).getIdEmparejamiento()) && mesa.get(i).getIdEmparejamiento().equals("000") ) ) //Si son cartas con mismo id, y no emparejadas
				espacio=35;
			else if (i!=0 && !mesa.get(i).getIdEmparejamiento().equals(mesa.get(i-1).getIdEmparejamiento()) && !mesa.get(i).getIdEmparejamiento().equals("000") && !mesa.get(i-1).getIdEmparejamiento().equals("000") )   //Son de emparejamientos distintos
				espacio=35;
			else if (i!=0 && mesa.get(i).getIdEmparejamiento().equals("000") && !mesa.get(i-1).getIdEmparejamiento().equals("000")) //La carta a poner no esta emparejada, pero la anterior si lo esta
				espacio=35;
			else
				espacio=0;
			if(i!=0 && (contadorCartasMostrar!=0) ) 
				mesa.get(i).getImagen().getBtn().setPosition(mesa.get(i-1).getImagen().getBtn().getX()+ancho+espacio, 415);
			else
				mesa.get(i).getImagen().getBtn().setPosition(600 + 0 *(ancho+espacio), 415); //Cambiar 0 por i
			mesa.get(i).getImagen().getBtn().setSize(140, 190);
			stage.addActor(mesa.get(i).getImagen().getBtn());	
			if(contadorCartasMostrar==maxCartas) break;
			++contadorCartasMostrar;
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
											clearActors(true);
											jugador.lanzarCarta(mesa, index);
											archivo.vaciarArchivo(mazo, mesa, jugador, computadora, seleccionadas, turno, reparte, ultimoEnRecoger, ultimoJugar);
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
											clearActors(true);
											jugador.recogerCarta(mesa, seleccionadas, jugador.getCartas().get(index),computadora);
											archivo.vaciarArchivo(mazo, mesa, jugador, computadora, seleccionadas, turno, reparte, ultimoEnRecoger, ultimoJugar);
											updateGameState();
											turno = false;
											ultimoEnRecoger = TipoJugador.jugador;
										}else 
											Render.mostrarMensaje(stage, "Error", "No puede recoger", "Ok");
									}
								}else if(select.getMovimiento() == "emparejar"){
									if(seleccionadas.size() == 0) 
										Render.mostrarMensaje(stage,"Error","Primero selecciona cartas para hacer un movimiento","Ok");
									else {
										if(jugador.validarCartasEmparejar(seleccionadas, jugador.getCartas().get(index))) {
											clearActors(true);
											jugador.emparejarCarta(mesa, seleccionadas, jugador.getCartas().get(index),computadora);
											archivo.vaciarArchivo(mazo, mesa, jugador, computadora, seleccionadas, turno, reparte, ultimoEnRecoger, ultimoJugar);
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
											clearActors(true);
											jugador.doblarCarta(mesa, seleccionadas, jugador.getCartas().get(index),computadora);
											archivo.vaciarArchivo(mazo, mesa, jugador, computadora, seleccionadas, turno, reparte, ultimoEnRecoger, ultimoJugar);
											updateGameState();
											turno = false;
										}else 
											Render.mostrarMensaje(stage, "Error", "No puede doblar", "Ok");
									}
								}
								if(turno==false) {
									seleccionadas.clear();
									if(jugador.getCartas().size() == 0 && computadora.getCartas().size() == 0 && mazo.getSize() > 0) {
										mazo.repartir(jugador.getCartas());
										mazo.repartir(computadora.getCartas());
									}
									if(jugador.getCartas().size() == 0 && computadora.getCartas().size() == 0 && mazo.getSize() == 0) {
										if(mesa.size() > 0) {
											if(ultimoEnRecoger==TipoJugador.jugador)
												jugador.asignarCartasSobrantes(mesa);
											else
												computadora.asignarCartasSobrantes(mesa);
										}
										PuntajeJugador puntJugador = jugador.contarPuntaje();
										PuntajeJugador puntCompu = computadora.contarPuntaje();
										if (puntJugador.tiene26() && puntCompu.tiene26()) {
											PuntajeJugador elegido = puntJugador.getCantEspadas() > 6 ? puntJugador : puntCompu;
											elegido.sumarPuntaje(3);
										}
										String mensaje = "";
										if (puntJugador.getPuntaje() > puntCompu.getPuntaje()) {
											mensaje = puntJugador.getMensajeGanador(false);
										} else if (puntJugador.getPuntaje() < puntCompu.getPuntaje()) {
											mensaje = puntCompu.getMensajeGanador(true);
										} else {
											mensaje = "Hubo un empate";
										}
										Casino.ventana.setScreen(new EndScreen(mensaje,jugador,computadora,puntJugador.getPuntaje(),puntCompu.getPuntaje()));
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
			jugador.getCartas().get(i).getImagen().getBtn().setPosition(600 + i * 175, 75);
			jugador.getCartas().get(i).getImagen().getBtn().setSize(140, 190);
			stage.addActor(jugador.getCartas().get(i).getImagen().getBtn());
		}
	}
	
	public void botonesMoverMesa() {
		flechaDerecha.getBtn().setSize(120, 120);
		flechaDerecha.getBtn().setPosition(1300, 450);
		flechaIzquierda.getBtn().setSize(120, 120);
		flechaIzquierda.getBtn().setPosition(450, 450);
		stage.addActor(flechaDerecha.getBtn());
		stage.addActor(flechaIzquierda.getBtn());
		flechaIzquierda.getBtn().addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) { //NOTA: El clear actors limpia los isSelected
				if(loteCartas-maxCartas-1>=0)
					loteCartas=loteCartas-maxCartas-1;
				clearActors(false);
				updateGameState();
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				return true;
			}
		});
		flechaDerecha.getBtn().addListener(new ClickListener() {
			@Override
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				if(loteCartas+maxCartas+1<mesa.size())
					loteCartas+=maxCartas+1;
				clearActors(false);
				updateGameState();
			}
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				return true;
			}
		});
	}
	
	@Override
	public void show() {
		fondo.setSize(Config.anchoPantalla, Config.altoPantalla);	
		//Creando los botones para mostrar los lotes de cartas
		botonesMoverMesa();
		fondo.setSize(Config.anchoPantalla, Config.altoPantalla);	
		btnSalir.getBtn().setPosition(100, 75);
		btnSalir.getBtn().setSize(100,100);
		btnSalir.getBtn().addListener(new ClickListener() {
			public void touchUp(InputEvent e, float x, float y, int point, int button) {
				Dialog dialog = new Dialog("Salir",new Skin(Gdx.files.internal(Config.pathSkin))){
					protected void result(Object object) {
						if((Boolean)object)
							Gdx.app.exit();
					}
				};
				dialog.text("Seguro quiere salir?");
				dialog.button("Si",true);
				dialog.button("No",false);
				dialog.scaleBy(0.5f);
				dialog.layout();
				dialog.show(stage);
			}
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				return true;
			}
		});
		stage.addActor(btnSalir.getBtn());
		if((jugador.getCartas().size()==0 && computadora.getCartas().size()==0)) { //Reparte solo cuando ambos se queden sin cartas
			mazo.repartir(this.jugador.getCartas());
			mazo.repartir(this.computadora.getCartas());
		}
		if(nuevaPartida)
			mazo.repartir(mesa);
		updateGameState();
		if(nuevaPartida) { //Solo se reparte a la mesa cuando no se haya jugado antes\
			if(Math.random() < 0.50) {
				this.turno = true;
				Render.mostrarMensaje(stage, "Informacion", "Reparte la computadora", "Ok");
			}else {
				this.turno = false;
				Render.mostrarMensaje(stage, "Informacion", "Reparte el jugador", "Ok");
			}
		}
		//Creando las ventanas emergentes recogidas
		crearVentanasRecogidas(ventanaRecogidasJugador,1450,75, jugador.getCartasRecogidas(), "Jugador");
		crearVentanasRecogidas(ventanaRecogidasComputadora,1450,750, computadora.getCartasRecogidas(), "Computadora");
		archivo.vaciarArchivo(mazo, mesa, jugador, computadora, seleccionadas, turno, reparte, ultimoEnRecoger, ultimoJugar);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Render.batch.begin();
		fondo.dibujar();
		imagenMazo.dibujar(300, 415);
		contadorMazo.dibujar(""+mazo.getSize(), 300+(140/2)-(contadorMazo.getAncho()/2), 415+(190/2)+(contadorMazo.getAlto()/2));
		puntajeJugador.dibujar(Config.userName + ": " + jugador.contarPuntaje().getPuntaje(), 100, 750);
		puntajeComputadora.dibujar("Computadora: " + computadora.contarPuntaje().getPuntaje(), 100, 800);
		spriteBasura.dibujar(" ", 0, 0);
		contadorCartasMostrar=0;
		for(int i = loteCartas; i < mesa.size(); i++) {
			if(mesa.get(i).isSelected()) 
				seleccionada.dibujar("*", mesa.get(i).getImagen().getBtn().getX()+50, mesa.get(i).getImagen().getBtn().getY()+220);
			if(contadorCartasMostrar==maxCartas) break;
			++contadorCartasMostrar;
		}
		if(!turno) {
			mostarCartasComputadora();
			clearActors(true);
			computadora.decidirMovimiento(mesa,jugador);
			if(jugador.getCartas().size() == 0 && computadora.getCartas().size() == 0 && mazo.getSize() > 0) {
				mazo.repartir(jugador.getCartas());
				mazo.repartir(computadora.getCartas());
			}
			if(jugador.getCartas().size() == 0 && computadora.getCartas().size() == 0 && mazo.getSize() == 0) {
				if(mesa.size() > 0) {
					if(ultimoEnRecoger==TipoJugador.jugador)
						jugador.asignarCartasSobrantes(mesa);
					else
						computadora.asignarCartasSobrantes(mesa);
				}
				PuntajeJugador puntJugador = jugador.contarPuntaje();
				PuntajeJugador puntCompu = computadora.contarPuntaje();
				if (puntJugador.tiene26() && puntCompu.tiene26()) {
					PuntajeJugador elegido = puntJugador.getCantEspadas() > 6 ? puntJugador : puntCompu;
					elegido.sumarPuntaje(3);
				}
				String mensaje = "";
				if (puntJugador.getPuntaje() > puntCompu.getPuntaje()) {
					mensaje = puntJugador.getMensajeGanador(false);
				} else if (puntJugador.getPuntaje() < puntCompu.getPuntaje()) {
					mensaje = puntCompu.getMensajeGanador(true);
				} else {
					mensaje = "Hubo un empate";
				}
				Casino.ventana.setScreen(new EndScreen(mensaje,jugador,computadora,puntJugador.getPuntaje(),puntCompu.getPuntaje()));
			}
			updateGameState();
			mostarCartasComputadora();
			turno = true;
			archivo.vaciarArchivo(mazo, mesa, jugador, computadora, seleccionadas, turno, reparte, ultimoEnRecoger, ultimoJugar);
		}
		stage.act(delta);
		stage.draw();
		Render.batch.end();
		Render.batch.begin();
		if(mostrarRecogidas) {
			contadorRecogidasJugador.dibujar(""+jugador.getCartasRecogidas().size(), 1450+(140/2)-(contadorRecogidasJugador.getAncho()/2), 75+(190/2)+(contadorRecogidasJugador.getAlto()/2));
			contadorRecogidasComputadora.dibujar(""+computadora.getCartasRecogidas().size(), 1450+(140/2)-(contadorRecogidasJugador.getAncho()/2), 750+(190/2)+(contadorRecogidasJugador.getAlto()/2));
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
		stage.dispose();
	}

}
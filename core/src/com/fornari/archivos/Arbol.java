package com.fornari.archivos;

import java.util.ArrayList;

import com.fornari.casino.Carta;
import com.fornari.casino.Jugador;
import com.fornari.casino.Mazo;

public class Arbol {
	private NodoArchivo raiz;
	
	public Arbol() {
		this.raiz=null;
	}
	
	public void insertarArbol(NodoArchivo nodo) {
		if(raiz==null) 
			raiz=nodo;
		else
			raiz.insertarNodo(nodo);
	}
	
	public void modificarMazo(Mazo mazo,ArrayList<Carta> mesa, ArrayList<Carta> seleccionadas ) {
		ArrayList<Carta> lista=new ArrayList<Carta>();
		NodoArchivo.transformarAListaArray(mazo, lista);
		buscarNodoEnArbol("MAZO").setListaCarta(lista);
		buscarNodoEnArbol("MESA").setListaCarta(mesa);
		buscarNodoEnArbol("SELECCIONADAS").setListaCarta(seleccionadas);
	}
		
	public void modificarJugador(Jugador jugador, String texto) {
		Jugador jugadorAuxiliar=new Jugador();
		jugador=buscarNodoEnArbol(texto).getJugador();
		jugadorAuxiliar.setClarezas(jugador.getClarezas());
		jugadorAuxiliar.setIdEmparejamiento(jugador.getIdEmparejamiento());
	}
	
	
	public void agregarArbol(Mazo mazo,ArrayList<Carta> mesa,Jugador jugador, Jugador computadora, ArrayList<Carta> seleccionadas) {
		if(raiz==null) { //Creo los nodos y los inserto en el arbol
			insertarArbol(new NodoArchivo(mazo, "MAZO", 1));
			insertarArbol(new NodoArchivo(mesa, "MESA", 2));
			insertarArbol(new NodoArchivo(jugador, "JUGADOR", 3));
			insertarArbol(new NodoArchivo(computadora, "COMPUTADORA", 4));
			insertarArbol(new NodoArchivo(seleccionadas, "SELECCIONADAS", 5));
		} else {//Modifico ya los nodos dentro del arbol
			modificarMazo(mazo,mesa,seleccionadas);
			modificarJugador(jugador, "JUGADOR");
			modificarJugador(computadora, "COMPUTADORA");
		}
	}
	
	public NodoArchivo buscarNodoEnArbol(String textoBuscar) {
		NodoArchivo nodoBuscar=null;
		if(raiz!=null) {
			if(raiz.getTexto()==textoBuscar)
				nodoBuscar=raiz;
			else
				nodoBuscar=raiz.buscarNodo(textoBuscar);			
		}
		return nodoBuscar;
	}
	
	public void imprimirArbol() {
		if(raiz!=null)
			raiz.escribirEnArchivo();
	}
	
	public int contarArbol() {
		if(this.raiz!=null)
			return raiz.contarNodos();
		else
			return 0;
	}
	
	public NodoArchivo getRaiz() {
		return raiz;
	}

	public void setRaiz(NodoArchivo raiz) {
		this.raiz = raiz;
	}
	
	
	
}

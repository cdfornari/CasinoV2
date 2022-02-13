package com.fornari.archivos;

import java.util.ArrayList;

import com.fornari.casino.Carta;
import com.fornari.casino.Jugador;
import com.fornari.casino.Mazo;
/**
 * Clase Arbol donde se guarda informacion de la partida dentro de un arbol ABB
 * @author Carlos Fornari, Sandro Portanova, Maria Porras
 *
 */
public class Arbol {
	private NodoArchivo raiz;

	/**
	 * Constructor de la clase
	 */
	public Arbol() {
		this.raiz=null;
	}
	
	//Metodos publicos
	
	/**
	 * Inserta un nodo con informacion del juego en el arbol segun corresponda
	 * @param nodo con la informacion a insertar
	 */
	public void insertarArbol(NodoArchivo nodo) {
		if(raiz==null) 
			raiz=nodo;
		else
			raiz.insertarNodo(nodo);
	}
	/**
	 * Actualiza en el arbol la informacion de todos los mazos del juego
	 * @param mazo
	 * @param mesa
	 * @param seleccionadas
	 */
	public void modificarMazo(Mazo mazo,ArrayList<Carta> mesa, ArrayList<Carta> seleccionadas ) {
		ArrayList<Carta> lista=new ArrayList<Carta>();
		NodoArchivo.transformarAListaArray(mazo, lista);
		buscarNodoEnArbol("MAZO").setListaCarta(lista);
		buscarNodoEnArbol("MESA").setListaCarta(mesa);
		buscarNodoEnArbol("SELECCIONADAS").setListaCarta(seleccionadas);
	}
	/**
	 * Inserta la informacion de los jugadores en el arbol
	 * @param jugador
	 * @param texto
	 */
	public void modificarJugador(Jugador jugador, String texto) {
		Jugador jugadorAuxiliar=new Jugador();
		jugador=buscarNodoEnArbol(texto).getJugador();
		jugadorAuxiliar.setClarezas(jugador.getClarezas());
		jugadorAuxiliar.setIdEmparejamiento(jugador.getIdEmparejamiento());
	}
	
	/**
	 * Genera el Arbol con la informacion del juego
	 * @param mazo
	 * @param mesa
	 * @param jugador
	 * @param computadora
	 * @param seleccionadas
	 */
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
	/**
	 * Busca un nodo determinado en el arbol
	 * @param textoBuscar
	 * @return Devuelve el nodo requerido
	 */
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
	/**
	 * Imprime toda la informacion contenida en cada nodo del arbol
	 */
	public void imprimirArbol() {
		if(raiz!=null)
			raiz.escribirEnArchivo();
	}
	/**
	 * 
	 * @return Devuelve el nodo raiz del arbol
	 */
	public NodoArchivo getRaiz() {
		return raiz;
	}
    /**
     * 
     * @param raiz
     */
	public void setRaiz(NodoArchivo raiz) {
		this.raiz = raiz;
	}
	
	
	
}

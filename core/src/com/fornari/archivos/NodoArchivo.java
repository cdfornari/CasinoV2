package com.fornari.archivos;

import java.util.ArrayList;
import com.fornari.casino.Carta;
import com.fornari.casino.Jugador;
import com.fornari.casino.Mazo;
import com.fornari.casino.TipoJugador;
/**
 * Clase para el manejo de archivos
 * nodo con informacion del juego
 * @author Carlos Fornari, Sandro Portanova, Maria Porras
 *
 */
public class NodoArchivo {
	//Atributos privados de la clase
	private int clave; 
	private String texto;
	private ArrayList<Carta> listaCarta;
	private Mazo mazo=null;
	private Jugador jugador;
	private boolean turno, reparte;
	private TipoJugador ultimoRecoger, ultimoJugar;
	private NodoArchivo hijoIzquierdo=null;
	private NodoArchivo hijoDerecho=null;
	
	/**
	 * Constructor de la clase
	 */
	public NodoArchivo() {
		
	}
	/**
	 * Recibe la informacion que se va a guardar en el nodo
	 * @param turno
	 * @param reparte
	 * @param ultimoRecoger
	 * @param ultimoJugar
	 * @param texto
	 * @param clave
	 */
	public NodoArchivo(boolean turno, boolean reparte, TipoJugador ultimoRecoger, TipoJugador ultimoJugar, String texto, int clave) {
		this.turno=turno;
		this.reparte=reparte;
		this.ultimoRecoger=ultimoRecoger;
		this.ultimoJugar=ultimoJugar;
		this.texto=texto;
		this.clave=clave;
	}
	/**
	 * Recibe la informacion que se va a guardar en el nodo
	 * @param mazo
	 * @param texto
	 * @param clave
	 */
	public NodoArchivo(Mazo mazo, String texto, int clave) {
		this.mazo=mazo;
		this.texto=texto;
		this.clave=clave;
		jugador=null;
	}
	/**
	 * Recibe la informacion que se va a guardar en el nodo
	 * @param listaCarta
	 * @param texto
	 * @param clave
	 */
	public NodoArchivo(ArrayList<Carta> listaCarta, String texto,int clave) {
		this.clave=clave;
		this.texto=texto;
		this.listaCarta=listaCarta;
		jugador=null;
	}
    /**
     * Recibe la informacion que se va a guardar en el nodo
     * @param jugador
     * @param texto
     * @param clave
     */
	public NodoArchivo(Jugador jugador, String texto, int clave) {
		this.clave=clave;
		this.texto=texto;
		this.listaCarta=null;
		this.jugador=jugador;
	}
	/**
	 * Traslada las cartas de tipo Mazo a un ArrayList de Carta
	 * @param mazo
	 * @param listaCarta
	 */
	public static void transformar(Mazo mazo, ArrayList<Carta> listaCarta) {
		Carta carta=null;
		if(!mazo.estaVacio()) {
			carta=mazo.deleteTope(); 
			listaCarta.add(carta);
			transformar(mazo, listaCarta);
			mazo.setTope(carta);
		}
	}
	/**
	 * Convierte un Mazo a uno de ArrayList de Carta
	 * @param mazo
	 * @param listaCarta
	 * @return Devuelve un ArrayList de Carta
	 */
	public static ArrayList<Carta> transformarAListaArray(Mazo mazo, ArrayList<Carta> listaCarta) {
		ArrayList<Carta> lista=new ArrayList<>();
		transformar(mazo, lista);
		return lista;
	}
	
	/**
	 * Inserta un nodo a algunos de sus nodos descendientes
	 * @param nodo
	 */
	public void insertarNodo(NodoArchivo nodo) { //Crear una clase especial que recoja clave, texto, etc.
		if(clave>nodo.getClave()) {
			if(hijoIzquierdo!=null)
				hijoIzquierdo.insertarNodo(nodo);
			else
				hijoIzquierdo=nodo;
		} else if(clave<nodo.getClave()) {
			if(hijoDerecho!=null)
				hijoDerecho.insertarNodo(nodo);
			else
				hijoDerecho=nodo;
		}
	}
	/**
	 * Busca en los nodos descendientes el nodo requerido dado un texto
	 * @param textoBuscar
	 * @return Devuelve el nodo requerido
	 */
	public NodoArchivo buscarNodo(String textoBuscar) {
		NodoArchivo nodoBuscar=null;
		if(this!=null) {
			if(this.texto==textoBuscar)
				return this;
			if(hijoIzquierdo!=null)
				nodoBuscar=hijoIzquierdo.buscarNodo(textoBuscar);
			if(nodoBuscar!=null && nodoBuscar.getTexto()==textoBuscar)
				return nodoBuscar;
			if(hijoDerecho!=null)
				nodoBuscar=hijoDerecho.buscarNodo(textoBuscar);
		}
		return nodoBuscar;

	}
	/**
	 * Elimina cada uno de los nodos hijos
	 * 
	 */
	public void eliminarNodos() { //Elimina todo el arbol
		if(this!=null) {
			hijoIzquierdo.eliminarNodos();
			hijoIzquierdo=null;
			hijoDerecho.eliminarNodos();
			hijoDerecho=null;
		}
	}
	/**
	 * Imprime toda la informacion de un nodo
	 * 
	 */
	public void imprimirNodo() {
		if(jugador==null) {
			System.out.println("NODO: "+texto);
			if(mazo==null) {
				for(Carta cartas: this.listaCarta)
					System.out.println("CARTA "+cartas.getValor() +"FIGURA: "+cartas.getFigura());
				System.out.println("\n\n");
			} else 
				mazo.imprimir(); //Imprimir
		}
		else {
			System.out.println("NODO: "+this.texto);
			for(Carta cartas: this.jugador.getCartas())
				System.out.println("CARTA "+cartas.getValor() +"FIGURA: "+cartas.getFigura());
			System.out.println("CLAREZAS:"+jugador.getClarezas());
			System.out.println("ID:"+jugador.getIdEmparejamiento());
			System.out.println("\n\n");
		}
	}
	/**
	 * Transforma dado un ArrayList de Carta a uno de tipo Mazo
	 * @param listaCartas
	 * @return Devuelve un Mazo
	 */
	public Mazo transformarMazo(ArrayList<Carta> listaCartas) {
		Mazo mazo=new Mazo(true);
		for(Carta carta: listaCartas) 
			mazo.setTope(carta);
		return mazo;
	}
	/**
	 * Imprime la informacion del nodo actual y de sus descendientes
	 * 
	 */
	public void escribirEnArchivo() {
		if(this!=null) {
			imprimirNodo();
			if(hijoIzquierdo!=null)
				hijoIzquierdo.escribirEnArchivo();
			if(hijoDerecho!=null)
				hijoDerecho.escribirEnArchivo();
		}
	}

	//Getters y Setters
	
	public int getClave() {
		return clave;
	}

	public void setClave(int clave) {
		this.clave = clave;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public ArrayList<Carta> getListaCarta() {
		return listaCarta;
	}

	public void setListaCarta(ArrayList<Carta> listaCarta) {
		this.listaCarta = listaCarta;
	}
	
	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public NodoArchivo getHijoIzquierdo() {
		return hijoIzquierdo;
	}

	public void setHijoIzquierdo(NodoArchivo hijoIzquierdo) {
		this.hijoIzquierdo = hijoIzquierdo;
	}

	public NodoArchivo getHijoDerecho() {
		return hijoDerecho;
	}

	public void setHijoDerecho(NodoArchivo hijoDerecho) {
		this.hijoDerecho = hijoDerecho;
	}

	public Mazo getMazo() {
		return mazo;
	}

	public void setMazo(Mazo mazo) {
		this.mazo = mazo;
	}

	public boolean isTurno() {
		return turno;
	}

	public void setTurno(boolean turno) {
		this.turno = turno;
	}

	public boolean isReparte() {
		return reparte;
	}

	public void setReparte(boolean reparte) {
		this.reparte = reparte;
	}

	public TipoJugador getUltimoRecoger() {
		return ultimoRecoger;
	}

	public void setUltimoRecoger(TipoJugador ultimoRecoger) {
		this.ultimoRecoger = ultimoRecoger;
	}

	public TipoJugador getUltimoJugar() {
		return ultimoJugar;
	}

	public void setUltimoJugar(TipoJugador ultimoJugar) {
		this.ultimoJugar = ultimoJugar;
	}


	
}

package com.fornari.archivos;

import java.util.ArrayList;

import com.fornari.casino.Carta;
import com.fornari.casino.Jugador;
import com.fornari.casino.Mazo;
import com.fornari.casino.TipoJugador;
public class NodoArchivo {
	private int clave; 
	private String texto;
	private ArrayList<Carta> listaCarta;
	private Mazo mazo=null;
	private Jugador jugador;
	private boolean turno, reparte;
	private TipoJugador ultimoRecoger, ultimoJugar;
	private NodoArchivo hijoIzquierdo=null;
	private NodoArchivo hijoDerecho=null;
	
	public NodoArchivo() {
		
	}
	
	public NodoArchivo(boolean turno, boolean reparte, TipoJugador ultimoRecoger, TipoJugador ultimoJugar, String texto, int clave) {
		this.turno=turno;
		this.reparte=reparte;
		this.ultimoRecoger=ultimoRecoger;
		this.ultimoJugar=ultimoJugar;
		this.texto=texto;
		this.clave=clave;
	}
	
	public NodoArchivo(Mazo mazo, String texto, int clave) {
		this.mazo=mazo;
		this.texto=texto;
		this.clave=clave;
		jugador=null;
	}
	
	public NodoArchivo(ArrayList<Carta> listaCarta, String texto,int clave) {
		this.clave=clave;
		this.texto=texto;
		this.listaCarta=listaCarta;
		jugador=null;
	}

	public NodoArchivo(Jugador jugador, String texto, int clave) {
		this.clave=clave;
		this.texto=texto;
		this.listaCarta=null;
		this.jugador=jugador;
	}
	
	public static void transformar(Mazo mazo, ArrayList<Carta> listaCarta) {
		Carta carta=null;
		if(!mazo.estaVacio()) {
			carta=mazo.deleteTope(); 
			listaCarta.add(carta);
			transformar(mazo, listaCarta);
			mazo.setTope(carta);
		}
	}
	
	public static ArrayList<Carta> transformarAListaArray(Mazo mazo, ArrayList<Carta> listaCarta) {
		ArrayList<Carta> lista=new ArrayList<>();
		transformar(mazo, lista);
		return lista;
	}
	
	
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

	public int contarNodos() { //Cuenta las nodos que hay actualmente en el arbol
		int x=0;
		return x;
	}
	
	
	public void eliminarNodos() { //Elimina todo el arbol
		if(this!=null) {
			hijoIzquierdo.eliminarNodos();
			hijoIzquierdo=null;
			hijoDerecho.eliminarNodos();
			hijoDerecho=null;
		}
	}
	
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
	
	public Mazo transformarMazo(ArrayList<Carta> listaCartas) {
		Mazo mazo=new Mazo(true);
		for(Carta carta: listaCartas) 
			mazo.setTope(carta);
		return mazo;
	}
	
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

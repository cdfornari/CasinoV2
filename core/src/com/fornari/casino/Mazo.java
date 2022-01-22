package com.fornari.casino;

public class Mazo {
	private Carta primera;
	private Carta ultima;
	
	public Mazo() {
		this.primera = this.ultima = null;
	}
	
	public Carta getPrimera() {
		return this.primera;
	}
	
	public Carta getUltima() {
		return this.ultima;
	}
	
	public boolean estaVacio() {
		return this.primera == null;
	}
	
	public void instertarCarta(Carta carta) {
		if(estaVacio()) {
			
		}else {
			
		}
	}
	
	public void eliminarCarta(Carta carta) {
		if(!estaVacio()) {
			
		}
	}
	
	public boolean contieneCarta(Carta carta) {
		return true;
	}
	
	public Carta buscarCartaPorIdEmparejamiento(String id) {
		return new Carta();
	}
	
	public Carta buscarCartaPorValor(int valor) {
		return new Carta();
	}
	
	public Carta buscarCartaPorPosicion(int posicion) {
		return new Carta();
	}
	
	public Carta buscarCartaPorFigura(Figuras figura) {
		return new Carta();
	}
	
	public int contarCartasPorValor() {
		return 0;
	}
	
	public void vaciarMazo() {
		
	}
	
	public void barajear() {
		
	}
	
	public void repartir() {
		
	}
	////Funciones de pila
	
	public boolean esVacia() {
		boolean vacia=false;
		if(primera==null)
			vacia=true;
		return vacia;
	}
	
	public void insertarCarta(Carta carta) {  //Apilar 
		carta.setSiguiente(primera);
		primera=carta;
	}
	
	public void desapilar() {
		if(!esVacia())
			primera=primera.getSiguiente();
	}
	
	public Carta tope() {
		return this.primera;
	}

	public boolean existeCarta(Carta carta) {
		Carta existe;
		boolean consigue;
		if(!esVacia()) {
			if(tope().getValor()==carta.getValor())
				return true;
			existe=tope();
			desapilar();
			consigue=existeCarta(carta);
			insertarCarta(existe);
			return consigue;
		}
		return false;
	}
	
	public Carta buscarCartaPorValor(Carta carta) { 
		Carta encontrada=new Carta(); //Set valor a -1
		Carta cartaTope;
		if(!esVacia()) {
			cartaTope=tope();
			if(cartaTope.getValor()==carta.getValor())
				return cartaTope;
			desapilar();
			encontrada=buscarCartaPorValor(carta);
			insertarCarta(cartaTope);
		}
		return encontrada;
	}

	
	public void vaciarPila() {
		if(!esVacia()) {
			desapilar();
			vaciarPila();
		}
	}
	
	public int contarCartas() {
		Carta cartaTope;
		int numero;
		if(!esVacia()) {
			cartaTope=tope();
			desapilar();
			numero=contarCartas();
			insertarCarta(cartaTope);
			return numero+1;
		}
		return 0;
	}
	
	public int contarCartasPorValor(int valor) {
		Carta cartaTope;
		int numero=0;
		if(!esVacia()) {
			cartaTope=tope();
			desapilar();
			numero=contarCartasPorValor(valor);
			if(cartaTope.getValor()==valor)
				++numero;
			insertarCarta(cartaTope);
		}
		return numero;
	}
	
	public void eliminarCartaPorValor(int valor) { 
		Carta cartaTope;
		if(!esVacia()) {
			cartaTope=tope();
			desapilar();
			eliminarCartaPorValor(valor);
			if(cartaTope.getValor()!=valor)
				insertarCarta(cartaTope);
		}
	}
	
}

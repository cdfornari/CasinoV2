package com.fornari.casino;

public class Pila {
	private Carta tope = null;

	public Carta getTope() {
		return this.tope;
	}
	
	public boolean estaVacio() {
		return this.tope == null;
	}
	
	public void apilar(Carta carta) {
		if(estaVacio())
			this.tope = carta;
		else {
			carta.setSiguiente(this.tope);
			this.tope = carta;
		}
	}
	
	public Carta desapilar() {
		if(!estaVacio()) {
			Carta carta = this.tope;
			this.tope = this.tope.getSiguiente();
			return carta;
		}
		return null;
	}
}

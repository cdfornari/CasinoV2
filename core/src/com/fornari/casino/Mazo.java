package com.fornari.casino;

public class Mazo {
	private Carta tope = null;
	
	public Mazo() {
		Carta carta;
		for(int i = 1; i <= 52; i++) {
			do {
				carta = new Carta();
			}while(!this.contieneCarta(carta));
			apilar(carta);
		}
	}
	
	public boolean estaVacio() {
		return this.tope == null;
	}
	
	private void apilar(Carta carta) {
		if(estaVacio())
			this.tope = carta;
		else {
			carta.setSiguiente(this.tope);
			this.tope = carta;
		}
	}
	
	private void desapilar() {
		if(!estaVacio()) 
			this.tope = this.tope.getSiguiente();
	}
	
	private boolean contieneCarta(Carta carta) {
		if(!estaVacio()) {
			if(this.tope == carta)
				return true;
			Carta cartaTope = this.tope;
			desapilar();
			contieneCarta(carta);
			apilar(cartaTope);
		}
		return false;
	}
	
	public void repartir(Carta[] cartas) {
		for(int i = 0; i < 4; i++) {
			cartas[i] = this.tope;
			this.desapilar();
		}
	}
}

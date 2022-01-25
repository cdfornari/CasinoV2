package com.fornari.casino;

import java.util.ArrayList;

public class Mazo {
	private Carta tope = null;
	private int size = 0;
	
	public Mazo() {
		Carta carta;
		for(int i = 1; i <= 52; i++) {
			do {
				carta = new Carta();
			}while(this.contieneCarta(carta));
			apilar(carta);
		}
	}
	
	public boolean estaVacio() {
		return this.tope == null;
	}
	
	public int getSize() {
		return this.size;
	}
	
	private void apilar(Carta carta) {
		if(estaVacio())
			this.tope = carta;
		else {
			carta.setSiguiente(this.tope);
			this.tope = carta;
		}
		this.size++;
	}
	
	private void desapilar() {
		if(!estaVacio()) {
			this.tope = this.tope.getSiguiente();
			this.size--;
		}
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
	
	public void repartir(ArrayList<Carta> cartas) {
		for(int i = 1; i <= 4; i++) {
			cartas.add(this.tope);
			this.desapilar();
		}
	}
}

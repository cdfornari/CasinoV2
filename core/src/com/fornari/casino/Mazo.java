package com.fornari.casino;

import java.util.ArrayList;

public class Mazo {
	private int size = 0;
	private Pila mazo = new Pila();
	
	public Mazo() {
		Carta carta;
		for(int i = 1; i <= 52; i++) {
			do {
				carta = new Carta();
			}while(this.contieneCarta(carta));
			this.mazo.apilar(carta);
			this.size++;
		}
	}
	
	public Mazo(boolean vacio) {
		
	}
	
	public boolean estaVacio() {
		return this.mazo.estaVacio();
	}
	
	public int getSize() {
		return this.size;
	}
	
	public void imprimir() {
		Carta carta;
		if(!mazo.estaVacio()) {
			carta = mazo.desapilar();
			System.out.println("CARTA "+carta.getValor() +"FIGURA: "+carta.getFigura());
			imprimir();
			mazo.apilar(carta);
		}
	}
	
	private boolean contieneCarta(Carta carta) {
		boolean contiene = false;
		if(!mazo.estaVacio()) {
			if(mazo.getTope().getValor() == carta.getValor() && mazo.getTope().getFigura() == carta.getFigura())
				return true;
			Carta cartaTope = mazo.desapilar();
			contiene = contieneCarta(carta);
			mazo.apilar(cartaTope);
		}
		return contiene;
	}
	
	public void repartir(ArrayList<Carta> cartas) {
		for(int i = 1; i <= 4; i++) {
			cartas.add(mazo.getTope());
			this.mazo.desapilar();
			this.size--;
		}
	}

	public Carta getTope() {
		return mazo.getTope();
	}

	public void setTope(Carta tope) {
		mazo.apilar(tope);
	}
	
	public Carta deleteTope() {
		return mazo.desapilar();
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	
}

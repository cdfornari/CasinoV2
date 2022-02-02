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
	
	public Mazo(boolean vacio) {
		
	}
	
	public boolean estaVacio() {
		return this.tope == null;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public void apilar(Carta carta) {
		if(estaVacio())
			this.tope = carta;
		else {
			carta.setSiguiente(this.tope);
			this.tope = carta;
		}
		this.size++;
	}
	
	public void imprimir() {
		Carta carta;
		if(!estaVacio()) {
			carta=desapilar();
			System.out.println("CARTA "+carta.getValor() +"FIGURA: "+carta.getFigura());
			imprimir();
			apilar(carta);
		}
	}
	
	public Carta desapilar() {
		if(!estaVacio()) {
			Carta carta = this.tope;
			this.tope = this.tope.getSiguiente();
			this.size--;
			return carta;
		}
		return null;
	}
	
	private boolean contieneCarta(Carta carta) {
		boolean contiene = false;
		if(!estaVacio()) {
			if(this.tope.getValor() == carta.getValor() && this.tope.getFigura() == carta.getFigura())
				return true;
			Carta cartaTope = desapilar();
			contiene = contieneCarta(carta);
			apilar(cartaTope);
		}
		return contiene;
	}
	
	public void repartir(ArrayList<Carta> cartas) {
		for(int i = 1; i <= 4; i++) {
			cartas.add(this.tope);
			this.desapilar();
		}
	}

	public Carta getTope() {
		return tope;
	}

	public void setTope(Carta tope) {
		this.tope = tope;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	
}

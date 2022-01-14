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
	
	public int contarCartas() {
		return 0;
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
}

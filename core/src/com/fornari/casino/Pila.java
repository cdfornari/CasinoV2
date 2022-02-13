package com.fornari.casino;
/**
 * Esta clase contiene las funciones necesarias para
 * el manejo de pilas
 * @author Carlos Fornari, Sandro Portanova, Maria Porras
 * 
 */
public class Pila {
    //Atributo de la clase 
	private Carta tope = null;
	/**
	 * Devuelve la primera carta de la pila
	 * @return devuelve una carta (el tope de la pila)
	 */
	public Carta getTope() {
		return this.tope;
	}	
	/**
	 * Verifica si la pila esta vacia 
	 * @return devuelve el booleano que identifica si la 
	 * pila esta vacia o no 
	 */
	public boolean estaVacio() {
		return this.tope == null;
	}	
	/**
	 * Funcion que inserta un nodo (cartas) en la pila
	 * @param carta Recibe un objeto tipo carta como parametro
	 */	
	public void apilar(Carta carta) {
		if(estaVacio())
			this.tope = carta;
		else {
			carta.setSiguiente(this.tope);
			this.tope = carta;
		}
	}	
	/**
	 * Funcion que elimina el primer nodo (carta) de la pila
	 * @return Devuelve la carta que se va a eliminar 
	 */
	public Carta desapilar() {
		if(!estaVacio()) {
			Carta carta = this.tope;
			this.tope = this.tope.getSiguiente();
			return carta;
		}
		return null;
	}
}

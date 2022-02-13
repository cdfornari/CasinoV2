package com.fornari.casino;

import java.util.ArrayList;

/**
 * Clase para la creacion del mazo
 * @author Carlos Fornari, Sandro Portanova, Maria Porras
 *
 */
public class Mazo {
	private int size = 0;
	private Pila mazo = new Pila();
	/**
	 * Funcion que crea el mazo 
	 */
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
	/**
	 * Funcion para verificar si el mazo esta o no vacio
	 * @return Devuelve booleano que identifica si la pila esta vacia 
	 */
	public boolean estaVacio() {
		return this.mazo.estaVacio();
	}
	/**
	 * Funcion que devuelve el tamano de la pila
	 * @return Devuelve un entero (tamano de la pila)
	 */
	public int getSize() {
		return this.size;
	}
	/**
	 * Funcion que imprime el mazo de cartas 
	 */
	public void imprimir() {
		Carta carta;
		if(!mazo.estaVacio()) {
			carta = mazo.desapilar();
			System.out.println("CARTA "+carta.getValor() +"FIGURA: "+carta.getFigura());
			imprimir();
			mazo.apilar(carta);
		}
	}
	
	/**
	 * Funcion para buscar un carta igual en el mazo 
	 * a la que entra por parametro
	 * @param carta
	 * @return retorna verdadero si la carta que entro por parametro 
	 * se encuentra en el mazo
	 */
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
	/**
	 * Funcion para reparatir las cartas del mazo ( 4 cartas), le resta 
	 * la cantidad de cartas repartidas al tamano del mazo
	 * @param cartas
	 */
	public void repartir(ArrayList<Carta> cartas) {
		for(int i = 1; i <= 4; i++) {
			cartas.add(mazo.getTope());
			this.mazo.desapilar();
			this.size--;
		}
	}
    /**
     * Funcion que permite ingresar a un atributo privado
     * para obtener el tope de la pila
     * @return retorna una carta (tope de la pila)
     */
	public Carta getTope() {
		return mazo.getTope();
	}
    /**
     * Funcion para cambiarle el valor al tope de la pila
     * @param tope
     */
	public void setTope(Carta tope) {
		mazo.apilar(tope);
	}
	/**
	 * Funcion para desapilar el tope de la pila
	 * @return ... 
	 */
	public Carta deleteTope() {
		return mazo.desapilar();
	}
    /**
     * Funcion para cambiarle el tamano a la pila (mazo)
     * @param size
     */
	public void setSize(int size) {
		this.size = size;
	}
	
	
}

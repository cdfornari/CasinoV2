package com.fornari.casino;

/**
 * Clase que evalua la puntuacion de la partida
 *  de acuerdo a las reglas establecidas
 * @author Carlos Fornari, Sandro Portanova, Maria Porras
 *
 */
public class PuntajeJugador {
	//Atributos privados
	private int cantCartas = 0;
	private int cantEspadas = 0;
	private int puntaje = 0;
    /**
     * Suma 3 puntos si la cantidad de cartas es mayor a 26
     * ademas suma las clarezas
     * @param cantCartas
     * @param clarezas
     */
	public PuntajeJugador(int cantCartas, int clarezas) {
		this.cantCartas = cantCartas;
		if (cantCartas > 26) {
			this.sumarPuntaje(3);
		}
		this.sumarPuntaje(clarezas);
	}
    /**
     * Funcion para obtener el numero de cartas
     * @return retorna la cantidad de cartas
     */
	public int getCantCartas() {
		return cantCartas;
	}
    /**
     * Funcion para obtener la cantidad de cartas de la figura espada
     * @return retorna la cantidad de cartas de la figura espada
     */
	public int getCantEspadas() {
		return cantEspadas;
	}
	/**
	 * Funcion para sumar 1 punto al jugador con mayor numero 
	 * de cartas de la figura espada
	 */
	public void addEspada() {
		cantEspadas++;
		if (cantEspadas >= 7) {
			sumarPuntaje(1);
		}
	}
    /**
     * Funcion para obtener el puntaje del jugador
     * @return retorna el puntaje obtenido por el jugador
     */
	public int getPuntaje() {
		return puntaje;
	}
	/**
	 * Funcion que suma num al puntaje ya obtenido
	 * @param num
	 */
	public void sumarPuntaje(int num) {
		puntaje+=num;
	}
	/**
	 * Funcion para verificar que la cantidad de cartas del jugador sea
	 * mayor a 25 
	 * @return devuelve verdadero si la cantidad de cartas es 
	 * mayor a 25
	 */
	public boolean tiene26() {
		return cantCartas > 25;
	}
	/**
	 * Funcion para anunciar ganador
	 * @param esCompu
	 * @return devuelve un string que anuncia el ganador
	 */
	public String getMensajeGanador(boolean esCompu) {
		if (esCompu) 
			return "Ha ganado la computadora con un puntaje de " + puntaje;
		return "Ha ganado el jugador con un puntaje de " + puntaje;
	}
} 

package com.fornari.casino;

public class PuntajeJugador {
	private int cantCartas = 0;
	private int cantEspadas = 0;
	private int puntaje = 0;

	public PuntajeJugador(int cantCartas, int clarezas) {
		this.cantCartas = cantCartas;
		if (cantCartas > 26) {
			this.sumarPuntaje(3);
		}
		this.sumarPuntaje(clarezas);
	}

	public int getCantCartas() {
		return cantCartas;
	}

	public int getCantEspadas() {
		return cantEspadas;
	}
	
	public void addEspada() {
		cantEspadas++;
		if (cantEspadas == 7) {
			sumarPuntaje(1);
		}
	}

	public int getPuntaje() {
		return puntaje;
	}
	
	public void sumarPuntaje(int num) {
		puntaje+=num;
	}
	
	public boolean tiene26() {
		return cantCartas > 25;
	}
	
	public String getMensajeGanador(boolean esCompu) {
		if (esCompu) 
			return "Ha ganado la computadora con un puntaje de " + puntaje;
		return "Ha ganado el jugador con un puntaje de " + puntaje;
	}
} 

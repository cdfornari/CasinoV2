package com.fornari.casino;
import java.util.ArrayList;

public class Jugador {
	private Carta[] cartas;
	private ArrayList<Carta> cartasRecogidas;
	private int clarezas;
	private String idEmparejamiento;
	
	public Jugador() {
		this.cartas = null;
		this.cartasRecogidas = new ArrayList<Carta>();
		this.clarezas = 0;
		this.idEmparejamiento = "000";
	}
	
	public Carta[] getCartas(){
		return this.cartas;
	}
	
	public ArrayList<Carta> getCartasRecogidas() {
		return this.cartasRecogidas;
	}
	
	public int getClarezas() {
		return this.clarezas;
	}
	
	public void addClareza() {
		this.clarezas++;
	}
	
	public String getIdEmparejamiento() {
		return idEmparejamiento;
	}
	
	public void setIdEmparejamiento(String id) {
		this.idEmparejamiento = id;
	}
	
	public int contarPuntaje() {
		return 0;
	}
	
	public void asignarCartasSobrantes() {
		
	}
	
	public void lanzarCarta(Mazo mesa, Carta cartaJugador) {
		
	}
	
	public void recogerCarta(Mazo mesa, Carta cartaARecoger, Carta cartaJugador) {
		
	}
	
	public void emparejarCarta(Mazo mesa, Carta cartaAEmparejar, Carta cartaJugador) {
		
	}
	
	public void doblarCarta(Mazo mesa, Carta cartaADoblar, Carta cartaJugador) {
		
	}

}

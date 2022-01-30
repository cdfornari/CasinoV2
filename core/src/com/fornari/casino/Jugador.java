package com.fornari.casino;
import java.util.ArrayList;

public class Jugador {
	private ArrayList<Carta> cartas;
	private ArrayList<Carta> cartasRecogidas;
	private int clarezas;
	private String idEmparejamiento;
	
	public Jugador() {
		this.cartas = new ArrayList<Carta>();
		this.cartasRecogidas = new ArrayList<Carta>();
		this.clarezas = 0;
		this.idEmparejamiento = "000";
	}
	
	public ArrayList<Carta> getCartas(){
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
	
	public void unselectAll() {
		for(int i = 0; i < cartas.size(); i++) {
			if(cartas.get(i).isSelected())
				cartas.get(i).toggleSelected();
		}
	}
	
	public int contarPuntaje() {
		return 0;
	}
	
	public void asignarCartasSobrantes() {
		
	}
	
	public void lanzarCarta(ArrayList<Carta> mesa, Carta cartaJugador) {
		cartas.remove(cartas.indexOf(cartaJugador));
		mesa.add(cartaJugador);
	}
	
	public void recogerCarta(Mazo mesa, Carta cartaARecoger, Carta cartaJugador) {
		
	}
	
	public void emparejarCarta(Mazo mesa, Carta cartaAEmparejar, Carta cartaJugador) {
		
	}
	
	public void doblarCarta(Mazo mesa, Carta cartaADoblar, Carta cartaJugador) {
		
	}

}

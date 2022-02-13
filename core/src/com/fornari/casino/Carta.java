package com.fornari.casino;

import java.util.UUID;

import com.fornari.utils.Imagen;

import java.util.Random;

/**
 * Esta clase permite definir todas las carateristicas de
 * las 52 cartas del juego
 * @author Carlos Fornari, Sandro Portanova, Maria Porras
 *
 */
public class Carta {
	
	//Atributos de la clase
	private int valor;
	private Figuras figura;
	private char representacion;
	private int puntaje;
	private String idEmparejamiento;
	private int sumaEmparejadas;
	private boolean doblada;
	private Carta siguiente;
	private Imagen imagen;
	private boolean selected;
	
    /**
     * Creacion de un constructor que le asigna a los atributos de la 
     * carta sus valores
     */
	public Carta() {
		this.selected = false;
		this.valor = new Random().nextInt(13) + 1;
		this.figura = generarFigura();
		this.idEmparejamiento = "000";
		this.sumaEmparejadas = 0;
		this.doblada = false;
		switch(this.valor) {
			case 1:
				this.representacion = 'A';
			break;
			case 11:
				this.representacion = 'J';
			break;
			case 12:
				this.representacion = 'Q';
			break;
			case 13:
				this.representacion = 'K';
			break;
			default:
				this.representacion = 'N';
		}
		if(valor == 10 && figura == Figuras.diamante) {
			this.puntaje = 2;
		}else if((valor == 2 && figura == Figuras.espada) || valor == 1) {
			this.puntaje = 1;
		}else {
			this.puntaje = 0;
		}
		this.setImagen(new Imagen(this.buildPath(),"btn"));
	}
	
	/**
	 * Construye el nombre de una imagen tipo png
	 * @return  
	 */
	public String buildPath() {
		String path = "Cards/card";
		switch(this.figura) {
			case espada:
				path += "Spades";
			break;
			case trebol:
				path += "Clubs";
			break;
			case corazon:
				path += "Hearts";
			break;
			case diamante:
				path += "Diamonds";
			break;
			case none:
			break;
		}
		if(this.representacion == 'N') {
			path += this.valor;
		}else {
			path += this.representacion;
		}
		path += ".png";
		return path;
	}
	/**
	 * Funcion que devuelve el valor de la carta
	 * @return un valor de tipo entero
	 */
	public int getValor(){
		return this.valor;
	}
	/**
	 * Funcion que devuelve la figura de la carta
	 * @return retorna el enum figura de la carta
	 */
	public Figuras getFigura() {
		return this.figura;
	}
	/**
	 * 
	 * @return Devuelve la representacion
	 */
	public char getRepresentacion() {
		return this.representacion;
	}
	/**
	 * 
	 * @return Retorna el puntaje
	 */
	public int getPuntaje() {
		return this.puntaje;
	}
	/**
	 * 
	 * @return Devuelve emparejamiento
	 */
	public String getIdEmparejamiento(){
		return this.idEmparejamiento;
	}
	
	public void setIdEmparejamiento(String id) {
		this.idEmparejamiento = id;
	}
	/**
	 * 
	 * @return Retorna doblar carta
	 */
	public boolean isDoblada() {
		return this.doblada;
	}
	
	public void toggleDoblada(){
		this.doblada = !doblada;
	}
	
	public int getsumaEmparejadas(){
		return this.sumaEmparejadas;
	}
	
	public void setSumaEmparejadas(int suma) {
		this.sumaEmparejadas = suma;
	}
	
	public Carta getSiguiente() {
		return this.siguiente;
	}
	
	public void setSiguiente(Carta carta) {
		this.siguiente = carta;
	}
	
	public String generarIdEmparejamiento() {
		return UUID.randomUUID().toString();
	}
	//funcion que genera las diversas figuras del juego
	/**
	 * Funcion que genera las figuras de las cartas del juego
	 * @return retorna las figuras de las cartas
	 */
	private Figuras generarFigura(){
		Figuras figura;
		int valor = new Random().nextInt(4) + 1;
		switch(valor) {
			case 1:
				figura = Figuras.espada;
			break;
			case 2:
				figura = Figuras.trebol;
			break;
			case 3:
				figura = Figuras.corazon;
			break;
			case 4:
				figura = Figuras.diamante;
			break;
			default:
				figura = Figuras.none;
		}
		return figura;
	}

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}
	
	public Imagen obtenerSprite() {
		Imagen imagen=new Imagen("Cards/cardBack_red5.png","img");
		return imagen;
	}

	public boolean isSelected() {
		return selected;
	}

	public void toggleSelected() {
		this.selected = !this.selected;
	}
	
	public int getSumaEmparejadas() {
		return sumaEmparejadas;
	}

	public void setFigura(Figuras figura) {
		this.figura = figura;
	}

	public void setRepresentacion(char representacion) {
		this.representacion = representacion;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}

	public void setDoblada(boolean doblada) {
		this.doblada = doblada;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	
}

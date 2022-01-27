package com.fornari.casino;
import java.util.UUID;

import com.fornari.utils.Imagen;

import java.util.Random;

public class Carta {
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
	
	public int getValor(){
		return this.valor;
	}
	
	public Figuras getFigura() {
		return this.figura;
	}
	
	public char getRepresentacion() {
		return this.representacion;
	}
	
	public int getPuntaje() {
		return this.puntaje;
	}
	
	public String getIdEmparejamiento(){
		return this.idEmparejamiento;
	}
	
	public void setIdEmparejamiento(String id) {
		this.idEmparejamiento = id;
	}
	
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

	public boolean isSelected() {
		return selected;
	}

	public void toggleSelected() {
		this.selected = !this.selected;
	}
}

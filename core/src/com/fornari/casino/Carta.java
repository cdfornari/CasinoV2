package com.fornari.casino;

public class Carta {
	private int valor;
	private Figuras figura;
	private char representacion;
	private int puntaje;
	private int idEmparejamiento;
	private int sumaEmparejadas;
	private boolean doblada;
	
	public Carta() {
		
	}
	
	public Carta(int valor, Figuras figura) {
		this.valor = valor;
		this.figura = figura;
		this.idEmparejamiento = 0;
		this.sumaEmparejadas = 0;
		this.doblada = false;
		switch(valor) {
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
	
	public int getIdEmparejamiento(){
		return this.idEmparejamiento;
	}
	
	public void setIdEmparejamiento(int id) {
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
}

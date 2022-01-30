package com.fornari.archivos;

public class Arbol {
	private NodoArchivo raiz;
	
	public Arbol() {
		this.raiz=null;
	}
	
	public void insertarArbol(NodoArchivo nodo) {
		if(raiz==null) 
			raiz=nodo;
		else
			raiz.insertarNodo(nodo);
	}
	
	public NodoArchivo buscarNodoEnArbol(String textoBuscar) {
		NodoArchivo nodoBuscar=null;
		if(raiz!=null) {
			if(raiz.getTexto()==textoBuscar)
				nodoBuscar=raiz;
			else
				nodoBuscar=raiz.buscarNodo(textoBuscar);			
		}
		return nodoBuscar;
	}
	
	public void imprimirArbol() {
		if(raiz!=null)
			raiz.escribirEnArchivo();
	}
	
	public int contarArbol() {
		if(this.raiz!=null)
			return raiz.contarNodos();
		else
			return 0;
	}
	
	public NodoArchivo getRaiz() {
		return raiz;
	}

	public void setRaiz(NodoArchivo raiz) {
		this.raiz = raiz;
	}
	
	
	
}

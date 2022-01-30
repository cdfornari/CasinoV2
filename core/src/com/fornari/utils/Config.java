package com.fornari.utils;

public class Config {
	public static final int anchoPantalla = 1920;
	public static final int altoPantalla = 1080;
	public static final String pathFuenteTitulo = "fuentes/BalsamiqSans-Regular.ttf";
	public static final String pathFuenteTexto = "fuentes/Montserrat-Bold.ttf";
	public static final String pathSkin = "shade/skin/uiskin.json";
	public static String userName = getNombre();
	
	private static String getNombre() {
		//TODO: buscar en archivo
		return "";
	}
	
	public static void setNombre(String nombre) {
		//TODO: guardar nombre en archivo
		userName = nombre;
	}
}

package com.fornari.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

import com.fornari.archivos.Archivo;
import com.fornari.casino.Casino;
import com.fornari.screens.MenuScreen;

public class Config {
	public static final int anchoPantalla = 1920;
	public static final int altoPantalla = 1080;
	public static final String pathFuenteTitulo = "fuentes/BalsamiqSans-Regular.ttf";
	public static final String pathFuenteTexto = "fuentes/Montserrat-Bold.ttf";
	public static final String pathSkin = "shade/skin/uiskin.json";
	public static String userName=getNombre();
	public static String pathArchivo="ARCHIVO.txt";
	
	
	private static String getNombre(){
	
		return "";
	}
	
	public static void setNombre(String nombre) {
		//TODO: guardar nombre en archivo
		userName = nombre;
	}
}

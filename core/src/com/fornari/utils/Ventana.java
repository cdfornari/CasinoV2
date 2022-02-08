package com.fornari.utils;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.fornari.casino.Carta;
import com.fornari.casino.Jugador;
import com.fornari.screens.GameScreen;

public class Ventana {
	final int ancho=140;
	final int alto=160;
	final int espacio=20;
	
	Skin skin = new Skin(Gdx.files.internal("shade/skin/uiskin.json"));
	private Window window;
	private String jugador="";
	private Dialog dialog;
	
	public Ventana(ArrayList<Carta> listaCartas, String jugador) {
		this.jugador=jugador;
		this.dialog=new Dialog("Cartas recogidas de "+jugador,skin);
		asignarCartas(listaCartas);
	}
	
	public void asignarCartas(ArrayList<Carta> listaCartas) {
		int x=1,y=1;
		for(int i=1; i<=listaCartas.size(); i++) {
			GameScreen.removeAllListeners(listaCartas.get(i-1).getImagen().getBtn());
			this.dialog.getContentTable().add(listaCartas.get(i-1).getImagen().getBtn()).width(ancho).height(alto).space(espacio);
			if(i % 11 == 0) {
				this.dialog.getContentTable().row();
				++y;
			}
			if(i<=11) ++x;
		}
		this.dialog.setPosition(Config.anchoPantalla/2/2, Config.altoPantalla/2);
		this.dialog.getContentTable().setSize(160*x, 210*y);
		this.dialog.setSize(160*x, 210*y);
	}
	
	public void setPosition(int x, int y) {
		this.dialog.getContentTable().setPosition(x, y);
	}

	
	public Dialog getDialog() {
		return dialog;
	}
	
}

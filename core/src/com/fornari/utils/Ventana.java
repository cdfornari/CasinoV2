package com.fornari.utils;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.fornari.casino.Carta;
import com.fornari.screens.GameScreen;

public class Ventana {
	final int ancho=140;
	final int alto=160;
	final int espacio=20;
	
	Skin skin = new Skin(Gdx.files.internal("shade/skin/uiskin.json"));
	private Dialog dialog;
	
	public Ventana(ArrayList<Carta> listaCartas, String jugador) {
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

package com.fornari.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Texto {
	private BitmapFont fuente;
	private FreeTypeFontGenerator generador;
	private FreeTypeFontParameter parametros;
	
	public Texto(String pathFuente, int size, Color color) {
		this.generador = new FreeTypeFontGenerator(Gdx.files.internal(pathFuente));
		this.parametros = new FreeTypeFontGenerator.FreeTypeFontParameter();
		this.parametros.size = size;
		this.parametros.color = color;
		this.fuente = this.generador.generateFont(this.parametros);
	}
	
	public void dibujar(String texto, int x, int y) {
		this.fuente.draw(Render.batch, texto, x , y);
	}
	
	public void setSize(int size) {
		this.parametros.size = size;
	}
	
	public void setColor(Color color) {
		this.parametros.color = color;
	}
}

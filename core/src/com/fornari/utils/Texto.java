package com.fornari.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Texto {
	private BitmapFont fuente;
	private FreeTypeFontGenerator generador;
	private FreeTypeFontParameter parametros;
	float x=0, y=0;	
	GlyphLayout rectangulo; //Genera un rectangulo con el texto para obtener facilmente la altura y ancho
	
	public Texto(String pathFuente, int size, Color color) {
		this.generador = new FreeTypeFontGenerator(Gdx.files.internal(pathFuente));
		this.parametros = new FreeTypeFontGenerator.FreeTypeFontParameter();
		this.parametros.size = size;
		this.parametros.color = color;
		this.fuente = this.generador.generateFont(this.parametros);
		rectangulo = new GlyphLayout();
	}
	
	public void dibujar(String texto, float x, float y) {
		this.fuente.draw(Render.batch, texto, x , y);
		this.x=x;
		this.y=y;
		this.rectangulo.setText(fuente, texto);
	}
	
	public void setColor(Color color) {
		this.fuente.setColor(color);
	}
	
	public void setSize(int size) {
		this.parametros.size = size;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}	
	
	public float getAncho() {
		return rectangulo.width;
	}
	
	public float getAlto() {
		return rectangulo.height;
	}
	
	
}

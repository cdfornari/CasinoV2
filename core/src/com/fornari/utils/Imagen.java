package com.fornari.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Imagen {
	private Texture textura;
	private Sprite sprite;
	
	public Imagen(String path) {
		this.textura = new Texture(path);
		this.sprite = new Sprite(this.textura);
	}
	
	public void dibujar() {
		this.sprite.draw(Render.batch);
	}
}

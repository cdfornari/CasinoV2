package com.fornari.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Imagen {
	private Texture textura;
	private Sprite sprite;
	private String type;
	private ImageButton btn; 
	
	public Imagen(String path, String type) {
		this.type = type;
		this.textura = new Texture(path);
		if(this.type == "img") 
			this.sprite = new Sprite(this.textura);
		else if(this.type == "btn") {
			Drawable drawable = new TextureRegionDrawable(new TextureRegion(textura));
			btn = new ImageButton(drawable);
		}else
			throw new Error("tipo de imagen invalido");
	}
	
	public void dibujar() {
		this.sprite.draw(Render.batch);
	}
	
	public void dibujar(float x, float y) {
		this.sprite.draw(Render.batch);
		sprite.setPosition(x, y);
	}
	
	public void setSize(int ancho, int alto) {
		sprite.setSize(ancho, alto);
	}
	
	public void setTransparencia(float transparencia) {
		sprite.setAlpha(transparencia);
	}

	public ImageButton getBtn() {
		return btn;
	}
}

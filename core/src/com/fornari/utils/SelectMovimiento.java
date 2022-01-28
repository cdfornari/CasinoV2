package com.fornari.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SelectMovimiento extends Dialog{
	
	public SelectMovimiento(Stage stage) {
		super("Selecciona", new Skin(Gdx.files.internal("shade/skin/uiskin.json")));
		this.text("Que movimiento deseas realizar?");
		this.button("Recoger","recoger");
		this.button("Emparejar","emparejar");
		this.button("Doblar","doblar");
		this.button("Salir","salir");
		this.scaleBy(0.5f);
		this.layout();
		this.show(stage);
	}	
	
	@Override
	protected void result(Object object) {
		super.result(object);
		if(object.equals("recoger"))
			System.out.print("recoger");
		else if(object.equals("emparejar"))
			System.out.print("emparejar");
		else if(object.equals("doblar"))
			System.out.print("doblar");
	}

}

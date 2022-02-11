package com.fornari.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SelectMovimiento extends Dialog{
	private String movimiento;
	
	public SelectMovimiento(Stage stage) {
		super("Selecciona", new Skin(Gdx.files.internal(Config.pathSkin)));
		this.text("Que movimiento deseas realizar?");
		this.button("Lanzar","lanzar");
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
		if(object.equals("lanzar"))
			this.movimiento = "lanzar";
		else if(object.equals("recoger"))
			this.movimiento = "recoger";
		else if(object.equals("emparejar"))
			this.movimiento = "emparejar";
		else if(object.equals("doblar"))
			this.movimiento = "doblar";
	}

	public String getMovimiento() {
		return this.movimiento;
	}

}

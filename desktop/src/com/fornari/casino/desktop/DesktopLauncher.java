package com.fornari.casino.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.fornari.casino.Casino;
import com.fornari.utils.Config;

public class DesktopLauncher {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Casino";
		config.width = Config.anchoPantalla;
	    config.height = Config.altoPantalla;
		new LwjglApplication(new Casino(), config);
	}
}

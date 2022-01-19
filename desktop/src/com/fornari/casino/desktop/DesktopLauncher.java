package com.fornari.casino.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.fornari.casino.Casino;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Casino";
		config.width = 1280;
	    config.height = 720;
		new LwjglApplication(new Casino(), config);
	}
}

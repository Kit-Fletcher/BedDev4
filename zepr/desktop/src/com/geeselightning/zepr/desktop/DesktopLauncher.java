package com.geeselightning.zepr.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.geeselightning.zepr.Zepr;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = true;
		config.width = 1366;
		config.height = 768;
		config.title = "ZEPR";
		new LwjglApplication(new Zepr(), config);
	}
}

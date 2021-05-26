package com.moonjew.breadthfirstsearch.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.moonjew.breadthfirstsearch.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Main.SCREEN_WIDTH;
		config.height = Main.SCREEN_HEIGHT;
		new LwjglApplication(new Main(), config);
	}

}

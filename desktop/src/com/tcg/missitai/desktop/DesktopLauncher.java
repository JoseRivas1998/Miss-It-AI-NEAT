package com.tcg.missitai.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglFiles;
import com.tcg.lichengine.managers.TCGValues;
import com.tcg.missitai.Game;

import static com.tcg.lichengine.TCGHelpers.worldHeight;
import static com.tcg.lichengine.TCGHelpers.worldWidth;

public class DesktopLauncher {
	public static void main (String[] arg) {
		TCGValues.load(new LwjglFiles());
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = worldWidth();
		config.height = worldHeight();
		new LwjglApplication(new Game(), config);
	}
}

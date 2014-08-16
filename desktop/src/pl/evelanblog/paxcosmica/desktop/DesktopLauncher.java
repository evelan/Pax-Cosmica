package pl.evelanblog.paxcosmica.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import pl.evelanblog.paxcosmica.PaxCosmica;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Pax Cosmica";
		config.width = 1280;
		config.height = 720;
		config.vSyncEnabled = true;
		config.resizable = false;
		config.fullscreen = false;
		new LwjglApplication(new PaxCosmica(), config);
	}
}
package pl.evelanblog.paxcosmica.desktop;

import pl.evelanblog.paxcosmica.PaxCosmica;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Pax Cosmica";
		config.width = 1920;
		config.height = 1080;
		config.vSyncEnabled = true;
		config.resizable = false;
		config.fullscreen = false;
		new LwjglApplication(new PaxCosmica(), config);
	}
}
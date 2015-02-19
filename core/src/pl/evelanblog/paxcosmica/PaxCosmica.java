package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import pl.evelanblog.paxcosmica.control.MousePointer;
import pl.evelanblog.utilities.GameManager;

public class PaxCosmica extends Game {

	private MousePointer mousePointer;
	private OrthographicCamera camera;
	private StretchViewport gameViewport, hudViewport;

	@Override
	public void create() {
		Assets.load();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Assets.worldWidth, Assets.worldHeight);
		mousePointer = new MousePointer();
		gameViewport = new StretchViewport(1920, 1080);
		hudViewport = new StretchViewport(1920, 1080);

		new GameManager(this);
		setScreen(GameManager.mainMenu);

	}

	public StretchViewport getGameViewport() {
		return gameViewport;
	}

	public StretchViewport getHudViewport() {
		return hudViewport;
	}

	public MousePointer getMouse() {
		return mousePointer;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}
}
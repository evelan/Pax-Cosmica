package pl.evelanblog.paxcosmica;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import pl.evelanblog.paxcosmica.control.MousePointer;
import pl.evelanblog.utilities.GameManager;

public class PaxCosmica extends Game {

	private MousePointer mousePointer;
	private OrthographicCamera camera;

	@Override
	public void create() {
		Assets.load();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Assets.worldWidth, Assets.worldHeight);
		mousePointer = new MousePointer();

		new GameManager(this);
		setScreen(GameManager.mainMenu);

	}

	public MousePointer getMouse() {
		return mousePointer;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}
}
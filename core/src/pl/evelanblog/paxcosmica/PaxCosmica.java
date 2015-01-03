package pl.evelanblog.paxcosmica;

import pl.evelanblog.paxcosmica.control.MousePointer;
import pl.evelanblog.scenes.GameStateManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PaxCosmica extends Game {

	private GameStateManager gsm;
	private MousePointer mousePointer;
	private Planet activePlanet;
	private SpriteBatch spriteBatch;
	private OrthographicCamera camera;

	@Override
	public void create() {
		Assets.load();
		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1920, 1080);
		mousePointer = new MousePointer();

		gsm = new GameStateManager(this);
		setScreen(GameStateManager.mainMenu);
	}

	public MousePointer getMouse()
	{
		return mousePointer;
	}

	public GameStateManager getGsm()
	{
		return gsm;
	}

	public Planet getActivePlanet() {
		return activePlanet;
	}

	public void setActivePlanet(Planet activePlanet) {
		this.activePlanet = activePlanet;
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

}
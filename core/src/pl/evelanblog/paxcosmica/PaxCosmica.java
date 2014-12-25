package pl.evelanblog.paxcosmica;

import pl.evelanblog.paxcosmica.control.MousePointer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class PaxCosmica extends Game {

	private OrthographicCamera camera;
	private Stage scene;
	private Stage hud;
	private GameStateManager gsm;
	private SpriteBatch batch;
	private MousePointer mousePointer;
	
	@Override
	public void create() {
		Assets.load();
		
		scene = new Stage();
		hud = new Stage();
		
		mousePointer = new MousePointer();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		
		gsm = new GameStateManager(this);
		setScreen(GameStateManager.mainMenu);
	}

	public MousePointer getMouse()
	{
		return mousePointer;
	}

	public SpriteBatch getBatch()
	{
		return batch;
	}

	public Camera getCamera()
	{
		return camera;
	}

	public GameStateManager getGsm()
	{
		return gsm;
	}

	public Stage getHud() {
		return hud;
	}

	public Stage getScene() {
		return scene;
	}
}
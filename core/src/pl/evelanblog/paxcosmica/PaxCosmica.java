package pl.evelanblog.paxcosmica;

import pl.evelanblog.paxcosmica.control.MousePointer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class PaxCosmica extends Game {

	private Stage mapScene, mapHud, gameHud, menu;
	private static Stage gameScene;
	private GameStateManager gsm;
	private MousePointer mousePointer;
	private Planet activePlanet;
	private SpriteBatch sprBatch;
	private OrthographicCamera cam;
	
	@Override
	public void create() {
		Assets.load();
		menu = new Stage();
		mapScene = new Stage();
		mapHud = new Stage();
		gameScene = new Stage();
		gameHud = new Stage();
		sprBatch = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 1920, 1080);
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

	public Stage getMapScene() {
		return mapScene;
	}
	public Stage getMenu()
	{
		return menu;
	}
	
	public static Stage getGameScene() {
		return gameScene;
	}

	public Planet getActivePlanet() {
		return activePlanet;
	}

	public void setActivePlanet(Planet activePlanet) {
		this.activePlanet = activePlanet;
	}

	public Stage getMapHud() {
		return mapHud;
	}

	public void setMapHud(Stage mapHud) {
		this.mapHud = mapHud;
	}

	public Stage getGameHud() {
		return gameHud;
	}

	public void setGameHud(Stage gameHud) {
		this.gameHud = gameHud;
		}

	public Batch getSprBatch() {
		return sprBatch;
	}
	public OrthographicCamera getCam(){
		return cam;
	}

}
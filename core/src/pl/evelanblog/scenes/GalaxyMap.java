package pl.evelanblog.scenes;

import java.util.ArrayList;

import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Button;
import pl.evelanblog.paxcosmica.MyText;
import pl.evelanblog.paxcosmica.PaxCosmica;
import pl.evelanblog.paxcosmica.Planet;
import pl.evelanblog.paxcosmica.Stats;
import pl.evelanblog.paxcosmica.control.MousePointer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GalaxyMap implements Screen, InputProcessor {

	private final PaxCosmica game;
	private Sprite dimScreen;
	private Image background;
	private Button attack, upgrade, exit, left, right;
	private MyText score, scrap, fuel, galaxyNumer;
	private Rectangle mousePointer;
	private float dimValue, moveValue;
	private ArrayList<Planet> planets;
	private Stage mapStage, mapHud;

	public GalaxyMap(final PaxCosmica game) {
		this.game = game;
		moveValue = 0;
		planets = new ArrayList<Planet>();
		mapStage = new Stage();
		mapHud = new Stage();

		background = new Image(Assets.mainmenu);
		mapHud.addActor(background);

		attack = new Button(Assets.attackButton);
		// store = new Button("buttons/storeButton.png");
		upgrade = new Button(false, 1520, 116, 400, 96, "buttons/upgradesButton.png");
		exit = new Button(false, 1520, 20, 400, 96, "buttons/exitButton.png");
		dimScreen = new Sprite(Assets.dim, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		left = new Button(0, 540, 128, "buttons/left.png");
		right = new Button(1792, 540, 128, "buttons/right.png");
		mapHud.addActor(left);
		mapHud.addActor(right);
		mapHud.addActor(upgrade);
		mapHud.addActor(exit);

		mousePointer = new MousePointer();
		mousePointer.setSize(1);

		score = new MyText("Score: " + Stats.score, 10, Gdx.graphics.getHeight() - 10);
		scrap = new MyText("Scrap: " + Stats.scrap, 210, Gdx.graphics.getHeight() - 10);
		fuel = new MyText("Fuel: " + Stats.fuel, 410, Gdx.graphics.getHeight() - 10);
		galaxyNumer = new MyText("Galaxy: " + moveValue, 610, Gdx.graphics.getHeight() - 10);

		mapHud.addActor(score);
		mapHud.addActor(scrap);
		mapHud.addActor(fuel);
		mapHud.addActor(galaxyNumer);

		// tworzenie planet
		planets.add(new Planet(200, 200, 1, 0.10f, true, false, "Ice", "planet/ice.png", 0.05f));
		planets.add(new Planet(600, 540, 1, 0.01f, false, false, "Fire", "planet/fire.png", 0.01f));
		planets.add(new Planet(-500, 540, 1, 0.02f, false, false, "Gold", "planet/gold.png", 0.01f));
		planets.add(new Planet(2200, 540, 1, 0.05f, true, false, "Purple", "planet/purple.png", 0.01f));
		for (Planet obj : planets)
			mapStage.addActor(obj);

		game.setActivePlanet(planets.get(0));

		mapStage.addActor(attack);
		// mapStage.addActor(move);
		// mapStage.addActor(store);
		attack.setVisible(false);
		// move.setVisible(false);
		// store.setVisible(false);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		for (Planet obj : planets)
			obj.update();

		mapHud.draw();
		mapStage.draw();
		mapStage.getBatch().begin();
		dimScreen(delta);
		mapStage.getBatch().end();

	}

	private void dimScreen(float delta) {
		if (dimValue > 0)
			dimValue -= delta;

		if (dimValue > 0)
			dimScreen.draw(mapStage.getBatch(), dimValue);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Assets.playSound(Assets.clickSfx);
		screenY = Gdx.graphics.getHeight() - screenY;
		game.getMouse().setPosition(screenX, screenY);

		if (game.getMouse().overlaps(attack, moveValue, screenX, screenY))
		{
			Assets.track1.stop();
			game.setScreen(GameStateManager.gameScreen);

		} else if (game.getMouse().overlaps(left)) {
			mapStage.getCamera().position.x -= Gdx.graphics.getWidth();
			moveValue--;
			galaxyNumer.setText("Galaxy: " + moveValue);
		}
		else if (game.getMouse().overlaps(right)) {
			mapStage.getCamera().position.x += Gdx.graphics.getWidth();
			moveValue++;
			galaxyNumer.setText("Galaxy: " + moveValue);
		}

		else if (game.getMouse().overlaps(upgrade))
		{
			game.setScreen(GameStateManager.upgradeScreen);
			dispose();
		} else if (game.getMouse().overlaps(exit))
		{
			game.setScreen(GameStateManager.mainMenu);
			dispose();
		}
		// Obsługa wyboru planet
		attack.setVisible(false);
		for (Planet obj : planets)
		{
			obj.reset();
			if (game.getMouse().overlaps(obj, moveValue, screenX, screenY))
			{
				attack.setCenterPosition(obj.getCenterX(), obj.getCenterY());
				attack.setVisible(true);
				obj.setHover();
				game.setActivePlanet(obj);
			}
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}

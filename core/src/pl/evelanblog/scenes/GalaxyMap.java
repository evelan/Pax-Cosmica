package pl.evelanblog.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import pl.evelanblog.GUI.Button;
import pl.evelanblog.GUI.CustomText;
import pl.evelanblog.paxcosmica.*;
import pl.evelanblog.paxcosmica.control.MousePointer;
import pl.evelanblog.utilities.GameManager;

import java.util.ArrayList;

public class GalaxyMap extends Stage implements Screen, InputProcessor {

	private final PaxCosmica game;
	MousePointer mousePointer;
	Background background;
	private Button attack, move, exit, left, right;
	private CustomText score, scrap, fuel, galaxyNumber;
	private float moveValue;
	private ArrayList<Planet> planets;

	public GalaxyMap(final PaxCosmica game) {
		super(new StretchViewport(1920, 1080));
		this.game = game;
		moveValue = 0;
		planets = new ArrayList<Planet>();

		background = new Background(new Sprite(Assets.mainmenu));
		addActor(background);

		attack = new Button(Assets.attackButton);
		move = new Button(Assets.moveButton);
		exit = new Button(1520, 20, 400, 96, Assets.exitButton);
		left = new Button(0, 540, 128, 128, Assets.leftArrow);
		right = new Button(1792, 540, 128, 128, Assets.rightArrow);

		addActor(left);
		addActor(right);
		addActor(exit);

		mousePointer = GameManager.getMouse();

		fuel = new CustomText("Fuel: " + Stats.fuel, 410, getViewport().getWorldHeight() - 10);
		galaxyNumber = new CustomText("Galaxy: " + moveValue, 610, getViewport().getWorldHeight() - 10);

		score = new CustomText("Score: " + Stats.score, 10, 1070);
		scrap = new CustomText("Scrap: " + Stats.scrap, 210, 1070);

		addActor(score);
		addActor(scrap);
		addActor(fuel);
		addActor(galaxyNumber);

		// tworzenie planet
		planets.add(new Planet(200, 200, 1, 0.10f, true, "Ice", "planet/ice.png", 0.05f, false));
		planets.add(new Planet(600, 540, 1, 0.01f, false, "Fire", "planet/fire.png", 0.01f, false));
		planets.add(new Planet(-600, 540, 1, 0.02f, false, "Gold", "planet/gold.png", 0.01f, false));
		planets.add(new Planet(2200, 540, 1, 0.05f, true, "Purple", "planet/purple.png", 0.01f, false));
		planets.add(new Planet(1320, 580, 1, 0.10f, true, "Sklep", "planet/shop.png", 0.005f, true));

		for (Planet obj : planets)
			addActor(obj);

		GameManager.setActivePlanet(planets.get(0));

		addActor(attack);
		addActor(move);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		for (Planet obj : planets)
			obj.update();

		draw();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);

		attack.setVisible(false);
		move.setVisible(false);

		score.setText("Score: " + Stats.score);
		scrap.setText("Scrap: " + Stats.scrap);
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
		screenX = (int) (screenX * getViewport().getWorldWidth() / Gdx.graphics.getWidth());
		screenY = (int) (screenY * getViewport().getWorldHeight() / Gdx.graphics.getHeight());
		GameManager.getMouse().setPosition(screenX, screenY);

		if (GameManager.getMouse().overlaps(attack, moveValue, screenX, screenY)) {
			Assets.track1.stop();
			game.setScreen(GameManager.gameScreen);
		} else if (GameManager.getMouse().overlaps(move, moveValue, screenX, screenY)) {
			Stats.save();
			game.setScreen(GameManager.upgradeScreen);
			dispose();
		} else if (GameManager.getMouse().overlaps(left)) {
			getCamera().position.x -= Assets.worldWidth;
			moveValue--;
			galaxyNumber.setText("Galaxy: " + moveValue);
		} else if (GameManager.getMouse().overlaps(right)) {
			getCamera().position.x += Assets.worldWidth;
			moveValue++;
			galaxyNumber.setText("Galaxy: " + moveValue);
		} else if (GameManager.getMouse().overlaps(exit)) {
			Stats.save();
			game.setScreen(GameManager.mainMenu);
			dispose();
		}
		// Obsługa wyboru planet
		attack.setVisible(false);
		move.setVisible(false);
		for (Planet obj : planets) {
			obj.reset();
			if (GameManager.getMouse().overlaps(obj, moveValue, screenX, screenY)) {
				if (!obj.isStore()) {
					attack.setPosition(obj.getX() + obj.getWidth() / 2 - attack.getWidth() / 2, obj.getY() + obj.getHeight() / 2 - attack.getHeight() / 2);
					attack.setVisible(true);
					obj.setHover();
					GameManager.setActivePlanet(obj);
				} else {
					move.setPosition(obj.getX() + obj.getWidth() / 2 - attack.getWidth() / 2, obj.getY() + obj.getHeight() / 2 - attack.getHeight() / 2);
					move.setVisible(true);
					obj.setHover();
					GameManager.setActivePlanet(obj);
				}
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

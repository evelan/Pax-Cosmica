package pl.evelanblog.scenes;

import java.util.ArrayList;

import pl.evelanblog.dynamicobjects.Player;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Button;
import pl.evelanblog.paxcosmica.GameStateManager;
import pl.evelanblog.paxcosmica.PaxCosmica;
import pl.evelanblog.paxcosmica.Planet;
import pl.evelanblog.paxcosmica.Stats;
import pl.evelanblog.paxcosmica.control.MousePointer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GalaxyMap implements Screen, InputProcessor {

	// TODO: Thank you for saveing, my people has something to offer to you...
	// it's not much but i might help you.
	// TODO: Crystal you can avoid

	private final PaxCosmica game;
	private Sprite background, player, dimScreen;
	private ArrayList<Planet> planets;
	private Button attack, move, store, upgrade, exit;
	private BitmapFont font;
	private Vector2 destiny;
	private Rectangle mousePointer;
	private float dimValue;
	private boolean portal = false;
	private int offsetX = 0, offsetY = 0;

	public GalaxyMap(final PaxCosmica game) {
		this.game = game;

		attack = new Button("buttons/attackButton.png");
		move = new Button("buttons/moveButton.png");
		store = new Button("buttons/storeButton.png");
		upgrade = new Button(1060, 20, "buttons/upgradesButton.png");
		exit = new Button(860, 20, "buttons/exitButton.png");
		dimScreen = new Sprite(Assets.dim, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		destiny = new Vector2(-1, -1);
		mousePointer = new MousePointer();
		mousePointer.setSize(1);

		planets = new ArrayList<Planet>();

		for (int i = 0; i < 10; i++) {
			planets.add(new Planet(MathUtils.random(20 + offsetX, 60 + offsetX),
					MathUtils.random(50 + offsetY, 600 + offsetY),
					MathUtils.random(0.6f, 1f),
					MathUtils.random(0.05f, 0.5f),
					MathUtils.randomBoolean(),
					MathUtils.randomBoolean(0.1f),
					"Planet "));
			offsetX += 100;
		}

		player = new Player(planets.get(0).getX(), planets.get(0).getY());
		player.setTexture(Assets.spaceship);
		player.setScale(0.7f);

		background = new Sprite(Assets.mainmenu);
		background.setBounds(0, 0, Assets.mainmenu.getWidth(), Assets.mainmenu.getHeight());

		font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		for (Planet obj : planets)
			obj.update();

		game.getCamera().update();

		game.getBatch().begin();
		background.draw(game.getBatch());
		for (Planet obj : planets)
		{
			obj.draw(game.getBatch(), 0);
			if (obj.isHover())
			{
				attack.setPosition(obj.getX(), obj.getY() + 60);
				attack.draw(game.getBatch());

				move.setPosition(obj.getX(), obj.getY() + 20);
				move.draw(game.getBatch());

				store.setPosition(obj.getX(), obj.getY() - 20);
				if (obj.isStore())
					store.draw(game.getBatch());
			}
		}

		player.draw(game.getBatch());

		upgrade.draw(game.getBatch());
		exit.draw(game.getBatch());

		font.draw(game.getBatch(), "Score: " + Stats.score, 100, 710);
		font.draw(game.getBatch(), "Scrap: " + Stats.scrap, 300, 710);
		font.draw(game.getBatch(), "Fuel: " + Stats.fuel, 500, 710);
		dimScreen(delta);
		game.getBatch().end();
	}

	private void dimScreen(float delta) {
		if (dimValue > 0)
			dimValue -= delta;

		if (dimValue > 0)
			dimScreen.draw(game.getBatch(), dimValue);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		if (destiny.x != -1)
			player.setPosition(destiny.x, destiny.y);
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

		for (Planet obj : planets)
		{
			obj.reset();
			if (game.getMouse().overlaps(obj.getBoundingRectangle()))
				obj.setHover();
		}

		if (game.getMouse().overlaps(attack.getBoundingRectangle()) && !portal)
		{

			for (Planet obj : planets)
				obj.reset();

			destiny.set(game.getMouse().x, game.getMouse().y);
			player.setPosition(destiny.x, destiny.y);
			game.setScreen(GameStateManager.gameScreen);

		} else if (game.getMouse().overlaps(move.getBoundingRectangle())) {

			for (Planet obj : planets)
				obj.reset();

			destiny.set(game.getMouse().x, game.getMouse().y);
			player.setPosition(destiny.x, destiny.y);
		} else if (game.getMouse().overlaps(store.getBoundingRectangle()))
		{
			// TODO: displayStore();
		} else if (game.getMouse().overlaps(upgrade.getBoundingRectangle()))
		{
			game.setScreen(GameStateManager.upgradeScreen);
			dispose();
		} else if (game.getMouse().overlaps(exit.getBoundingRectangle()))
		{
			game.setScreen(GameStateManager.mainMenu);
			dispose();
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

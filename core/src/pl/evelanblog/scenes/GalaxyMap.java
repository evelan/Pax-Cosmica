package pl.evelanblog.scenes;

import pl.evelanblog.galaxy.Planet;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Button;
import pl.evelanblog.paxcosmica.GameStateManager;
import pl.evelanblog.paxcosmica.PaxCosmica;
import pl.evelanblog.paxcosmica.Player;
import pl.evelanblog.paxcosmica.Stats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class GalaxyMap implements Screen, InputProcessor {

	// TODO: Thank you for saveing, my people has something to offer to you...
	// it's not much but i might help you.
	// TODO: Crystal you can avoid

	private final PaxCosmica game;
	private Sprite background, player;
	private Planet[] planets;
	private Button attack, move, store, upgrade, exit;
	private BitmapFont font;
	private Vector2 destiny = new Vector2();
	private float dimValue;
	private boolean portal = false;
	private int planetAmount;
	private int xPosition;

	public GalaxyMap(final PaxCosmica game) {
		this.game = game;
		
		attack = new Button("buttons/attackButton.png");
		move = new Button("buttons/moveButton.png");
		store = new Button("buttons/storeButton.png");
		upgrade = new Button(1060, 20, "buttons/upgradesButton.png");
		exit = new Button(860, 20, "buttons/exitButton.png");
		
		xPosition = 50;
		planetAmount = MathUtils.random(3, 8);
		planets = new Planet[planetAmount];

		for (int i = 0; i < planetAmount; i++) {

			planets[i] = new Planet(xPosition,
					MathUtils.random(50, 600),
					MathUtils.random(0.8f, 1.4f),
					MathUtils.random(0.01f, 1f),
					MathUtils.randomBoolean(),
					MathUtils.randomBoolean(0.1f),
					"Planet " + i);
			xPosition += Gdx.graphics.getWidth() / planetAmount + 1;
		}

		planets[planetAmount - 1].setAsPorta();
		player = new Player(planets[0].getX(), planets[0].getY());
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

		for (int i = 0; i < planetAmount; i++)
			planets[i].update();

		game.camera.update();

		game.batch.begin();
		background.draw(game.batch);
		for (int i = 0; i < planetAmount; i++)
		{
			planets[i].draw(game.batch, 0);
			if (planets[i].isHover())
			{
				attack.setPosition(planets[i].getX(), planets[i].getY() + 60);
				attack.draw(game.batch);

				move.setPosition(planets[i].getX(), planets[i].getY() + 20);
				move.draw(game.batch);

				store.setPosition(planets[i].getX(), planets[i].getY() - 20);
				if (planets[i].isStore())
					store.draw(game.batch);
			}
		}

		player.draw(game.batch);

		upgrade.draw(game.batch);
		exit.draw(game.batch);

		font.draw(game.batch, "Score: " + Stats.score, 100, 710);
		font.draw(game.batch, "Scrap: " + Stats.scrap, 300, 710);
		font.draw(game.batch, "Fuel: " + Stats.fuel, 500, 710);
		dimScreen(delta);
		game.batch.end();
	}

	private void dimScreen(float delta) {
		if (dimValue > 0)
			dimValue -= delta;

		if (dimValue > 0)
			game.dim.draw(game.batch, dimValue);
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
		game.mousePointer.setPosition(screenX, screenY);
		Assets.playSound(Assets.clickSfx);
		for (int i = 0; i < planetAmount; i++)
		{
			planets[i].reset();
			if (game.mousePointer.overlaps(planets[i].getBoundingRectangle()))
				planets[i].setHover();
		}

		if (game.mousePointer.overlaps(attack.getBoundingRectangle()) && !portal)
		{
			for(int i = 0; i < planetAmount; i++)
				planets[i].reset();
			destiny.set(game.mousePointer.x, game.mousePointer.y);
			game.setScreen(GameStateManager.gameScreen);

		} else if (game.mousePointer.overlaps(move.getBoundingRectangle())) {
			for(int i = 0; i < planetAmount; i++)
				planets[i].reset();
			destiny.set(game.mousePointer.x, game.mousePointer.y);
		} else if (game.mousePointer.overlaps(store.getBoundingRectangle()))
		{
			displayStore();
		} else if (game.mousePointer.overlaps(upgrade.getBoundingRectangle()))
		{
			game.setScreen(GameStateManager.upgradeScreen);
			dispose();
		} else if (game.mousePointer.overlaps(exit.getBoundingRectangle()))
		{
			game.setScreen(GameStateManager.mainMenu);
			dispose();
		}

		return true;
	}

	private void displayStore() {
		
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

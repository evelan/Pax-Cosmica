package pl.evelanblog.scenes;

import pl.evelanblog.galaxy.Planet;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Button;
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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GalaxyMap implements Screen, InputProcessor {

	// TODO: Thank you for saveing, my people has something to offer to you...
	// it's not much but i might help you.
	// TODO: Crystal you can avoid

	private final PaxCosmica game;
	private static Sprite background;
	private static int planetAmount;
	private static int xPosition;
	private static Planet[] planets;
	private static Rectangle mousePointer;
	private static Button attack, move, store, upgrade, exit;
	private static BitmapFont font;
	private static Player player;
	private boolean playerMove;
	private Vector2 destiny = new Vector2();
	private float speed = 200;
	private float dimValue;
	private static boolean portal = false;

	public GalaxyMap(final PaxCosmica game) {
		this.game = game;
	}

	public static void create()
	{
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

		attack = new Button("buttons/attackButton.png");
		move = new Button("buttons/moveButton.png");
		store = new Button("buttons/storeButton.png");
		upgrade = new Button(1060, 20, "buttons/upgradesButton.png");
		exit = new Button(860, 20, "buttons/exitButton.png");

		player = new Player(planets[0].getX(), planets[0].getY());

		background = new Sprite(Assets.mainmenu);
		background.setBounds(0, 0, Assets.mainmenu.getWidth(), Assets.mainmenu.getHeight());

		mousePointer = new Rectangle();
		mousePointer.setSize(1);

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

		if (playerMove) {
			Stats.fuel -= delta;
			if (player.getX() > destiny.x)
			{
				if (player.getX() != destiny.x)
					player.setSpaceshipPosition(player.getX() - speed * delta,
							player.getY());
			} else {
				if (player.getX() != destiny.x)
					player.setSpaceshipPosition(player.getX() + speed * delta,
							player.getY());
			}

			if (player.getY() > destiny.y) {
				if (player.getY() != destiny.y)
					player.setSpaceshipPosition(player.getX(), player.getY() - speed *
							delta);
			} else {
				if (player.getY() != destiny.y)
					player.setSpaceshipPosition(player.getX(), player.getY() + speed *
							delta);
			}

			if (Math.floor((player.getY())) == Math.floor(destiny.y) &&
					Math.floor(player.getX()) == Math.floor(destiny.x))
				playerMove =
						false;

			if (Math.floor((player.getY())) ==
					Math.floor(planets[planetAmount - 1].getY()) &&
					Math.floor(player.getX()) ==
					Math.floor(planets[planetAmount - 1].getX()))
				portal = true;
			else
				portal = false;
		}

		player.draw(game.batch, delta);

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
		if (portal)
			create();

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
		screenY = Gdx.graphics.getHeight() - screenY;
		mousePointer.setPosition(screenX, screenY);
		Assets.playSound(Assets.clickSfx);
		for (int i = 0; i < planetAmount; i++)
		{
			planets[i].reset();
			if (mousePointer.overlaps(planets[i].getBoundingRectangle()))
				planets[i].setHover();
		}

		if (mousePointer.overlaps(attack.getBoundingRectangle()) && !portal)
		{
			for(int i = 0; i < planetAmount; i++)
				planets[i].reset();
			playerMove = true;
			destiny.set(mousePointer.x, mousePointer.y);
			
			game.setScreen(GameStateManager.gameScreen);
			player.setSpaceshipPosition(attack.getX(), attack.getY());

		} else if (mousePointer.overlaps(move.getBoundingRectangle())) {
			for(int i = 0; i < planetAmount; i++)
				planets[i].reset();
			
			playerMove = true;
			destiny.set(mousePointer.x, mousePointer.y);
		} else if (mousePointer.overlaps(store.getBoundingRectangle()))
		{
			displayStore();
		} else if (mousePointer.overlaps(upgrade.getBoundingRectangle()))
		{
			game.setScreen(GameStateManager.upgradeScreen);
			dispose();
		} else if (mousePointer.overlaps(exit.getBoundingRectangle()))
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

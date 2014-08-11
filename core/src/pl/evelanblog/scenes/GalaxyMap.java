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

	// TODO: fuel

	private final PaxCosmica game;
	private Sprite background;
	private int planetAmount;
	private int xPosition;
	private Planet[] planets;
	private Rectangle mousePointer;
	private Button attack, move, store;
	private BitmapFont font;
	private Player player;
	private static float spaceX;
	private boolean playerMove;
	Vector2 destiny = new Vector2();

	public GalaxyMap(final PaxCosmica game) {
		this.game = game;
		background = new Sprite(Assets.mainmenu);
		background.setBounds(0, 0, Assets.mainmenu.getWidth(), Assets.mainmenu.getHeight());

		mousePointer = new Rectangle();
		mousePointer.setSize(2);

		attack = new Button("buttons/attackButton.png");
		move = new Button("buttons/moveButton.png");
		store = new Button("buttons/storeButton.png");

		font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.3f, 1f);
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

		if (playerMove)
		{
			if (player.getX() != destiny.x)
				player.setSpaceshipPosition(player.getX() + 1, player.getY());

			if (player.getY() != destiny.y)
				player.setSpaceshipPosition(player.getX(), player.getY() + 1);

			if (player.getY() == destiny.y && player.getX() == destiny.x)
				playerMove = false;
		}

		player.draw(game.batch, delta);

		font.draw(game.batch, "Score: " + Stats.score, 100, 760);
		font.draw(game.batch, "Scrap: " + Stats.scrap, 250, 760);
		font.draw(game.batch, "Fuel: " + Stats.fuel, 400, 760);

		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		xPosition = 50;
		planetAmount = MathUtils.random(3, 8);
		planets = new Planet[planetAmount];

		for (int i = 0; i < planetAmount; i++) {

			planets[i] = new Planet(xPosition,
					MathUtils.random(700),
					MathUtils.random(0.8f, 1.4f),
					MathUtils.random(0.01f, 1f),
					MathUtils.randomBoolean(),
					MathUtils.randomBoolean(0.1f),
					"Planet " + i);
			xPosition += Gdx.graphics.getWidth() / planetAmount;
		}

		player = new Player(planets[0].getX(), planets[0].getY());
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

		if (mousePointer.overlaps(attack.getBoundingRectangle()))
		{
			// player.setSpaceshipPosition(attack.getX(), attack.getY());
			playerMove = true;
			destiny.set(mousePointer.x, mousePointer.y);

			for (int i = 0; i < planetAmount; i++)
				planets[i].reset();
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

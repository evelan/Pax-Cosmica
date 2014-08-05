package pl.evelanblog.scenes;

import pl.evelanblog.enemy.fighter.Enemy;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.DynamicObject;
import pl.evelanblog.paxcosmica.PaxCosmica;
import pl.evelanblog.paxcosmica.control.Joystick;
import pl.evelanblog.world.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class GameScreen implements Screen {

	private final PaxCosmica game;
	private final World world;
	private final Joystick joystick;

	public GameScreen(final PaxCosmica game) {
		joystick = new Joystick();
		world = new World(game);
		this.game = game;
		Gdx.input.setInputProcessor(joystick);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (delta > 1.0f)
			delta = 0.0f;

		world.update(delta);

		game.batch.begin();

		game.batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);

		game.batch.draw(Assets.galaxy, 0, 0);
		game.batch.draw(Assets.stars, world.stars.getX(), world.stars.getY());
		game.batch.draw(Assets.stars2, world.stars2.getX(), world.stars2.getY());

		if (world.player.isAlive())
			world.player.draw(game.batch, delta);

		for (DynamicObject obj : World.objectArray)
		{
			// TODO: spróbowaæ przekazaæ deltê inaczej do funkcji draw w Enemy,
			// ¿eby móc daæ po prostu obj.draw(game.batch)
			if (obj instanceof Enemy)
				((Enemy) obj).draw(game.batch, delta);
			else
				obj.draw(game.batch);
		}

		Assets.hitEffect.draw(game.batch, delta);
		Assets.explosionEffect.draw(game.batch, delta);

		for (int i = 0; i < world.player.getHealth(); i++)
			game.batch.draw(Assets.hullBar, 35 * i, Gdx.graphics.getHeight() - Assets.hullBar.getHeight());

		for (int i = 0; i < world.player.getShield(); i++)
			game.batch.draw(Assets.hullBar, 35 * i + 300, Gdx.graphics.getHeight() - Assets.shieldBar.getHeight());

		// controls HUD
		game.batch.setColor(1.0f, 1.0f, 1.0f, 0.3f);
		game.batch.draw(Assets.knob, joystick.getX(), joystick.getY());
		game.batch.draw(Assets.button, Joystick.buttonA.getX(), Joystick.buttonA.getY());

		game.font.draw(game.batch, "Score: " + World.score, 10, Gdx.graphics.getHeight() - 30);
		game.font.draw(game.batch, "Objects: " + World.objectArray.size(), 10, Gdx.graphics.getHeight() - 50);
		game.font.draw(game.batch, "HP: " + world.player.getHealth(), 20, Gdx.graphics.getHeight() - 70);

		game.batch.end();

	}

	@Override
	public void pause() {
	}

	@Override
	public void resize(int width, int height) {
		game.camera.viewportHeight = height;
		game.camera.viewportWidth = width;
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}
package pl.evelanblog.scenes;

import java.util.ArrayList;

import pl.evelanblog.asteroid.Asteroid;
import pl.evelanblog.enemy.fighter.Enemy;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.DynamicObject;
import pl.evelanblog.paxcosmica.PaxCosmica;
import pl.evelanblog.paxcosmica.Stats;
import pl.evelanblog.paxcosmica.control.Controller;
import pl.evelanblog.paxcosmica.control.FloatingText;
import pl.evelanblog.world.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GameScreen implements Screen {

	private final PaxCosmica game;
	private final World world;
	private BitmapFont font;
	public static ArrayList<FloatingText> textArray = new ArrayList<FloatingText>();

	public GameScreen(final PaxCosmica game) {
		this.game = game;
		world = new World(game);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (!Controller.getPause())
			world.update(delta);

		game.batch.begin();

		world.background.draw(game.batch, delta);

		for (DynamicObject obj : World.objectArray)
		{
			// TODO: spróbowaæ przekazaæ deltê inaczej do funkcji draw w Enemy,
			// ¿eby móc daæ po prostu obj.draw(game.batch)
			if (obj instanceof Enemy)
				((Enemy) obj).draw(game.batch, delta);
			else if (obj instanceof Asteroid)
				((Asteroid) obj).draw(game.batch, delta);
			else
				obj.draw(game.batch);
		}

		if (World.player.isAlive())
			World.player.draw(game.batch, delta);

		Assets.hitEffect.draw(game.batch, delta);
		Assets.explosionEffect.draw(game.batch, delta);

		for (FloatingText text : textArray)
			text.draw(game.batch);

		for (int i = 0; i < World.player.getHealth(); i++)
			game.batch.draw(Assets.hullBar, 15 * i, Gdx.graphics.getHeight() - Assets.hullBar.getHeight());

		for (int i = 0; i < World.player.getShield(); i++)
			game.batch.draw(Assets.shieldBar, 15 * i, Gdx.graphics.getHeight() - (2 * Assets.shieldBar.getHeight()));

		// controls HUD
		Controller.knob.draw(game.batch, 0.3f);
		Controller.buttonA.draw(game.batch, 0.3f);
		Controller.buttonB.draw(game.batch, 0.3f);
		Controller.pauseButton.draw(game.batch, 0.3f);
		Controller.powerButton.draw(game.batch, 0.3f);

		if (Controller.getMenuGame()) {
			Controller.continueButton.draw(game.batch);
			Controller.exitButton.draw(game.batch);
		} 
		font.draw(game.batch, "Score: " + Stats.score, 5, Gdx.graphics.getHeight() - 100);
		font.draw(game.batch, "Scrap: " + Stats.scrap, 5, Gdx.graphics.getHeight() - 80);

		game.batch.end();

	}

	@Override
	public void pause() {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
		world.prepare(0);

		Assets.track2.play();
		Gdx.input.setInputProcessor(new Controller(game));
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
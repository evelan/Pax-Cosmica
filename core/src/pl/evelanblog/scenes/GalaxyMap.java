package pl.evelanblog.scenes;

import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Button;
import pl.evelanblog.paxcosmica.PaxCosmica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class GalaxyMap implements Screen, InputProcessor {

	// TODO: Thank you for saveing, my people has something to offer to you...
	// it's not much but i might help you.
	// TODO: Crystal you can avoid

	final PaxCosmica game;
	Button upgrades;
	Sprite background;
	int planetAmount;
	int xPosition;
	Sprite[] planets;
	Rectangle mousePointer;

	public GalaxyMap(final PaxCosmica game) {
		this.game = game;
		background = new Sprite(Assets.mainmenu);
		background.setBounds(0, 0, Assets.mainmenu.getWidth(), Assets.mainmenu.getHeight());

		mousePointer = new Rectangle();
		mousePointer.setSize(2);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.3f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.camera.update();

		game.batch.begin();
		background.draw(game.batch);
		for (int i = 0; i < planetAmount; i++)
			planets[i].draw(game.batch);

		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		xPosition = 50;
		planetAmount = MathUtils.random(3, 11);
		planets = new Sprite[planetAmount];

		for (int i = 0; i < planetAmount; i++) {
			planets[i] = new Sprite(Assets.galaxyPlanet);
			planets[i].setBounds(xPosition, MathUtils.random(150, 700), Assets.galaxyPlanet.getWidth(), Assets.galaxyPlanet.getHeight());
			planets[i].setOriginCenter();
			xPosition += Gdx.graphics.getWidth() / planetAmount;
		}

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

		for (int i = 0; i < planetAmount; i++) {
			if (mousePointer.overlaps(planets[i].getBoundingRectangle()))
			{
				game.setScreen(new GameScreen(game));
				dispose();
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

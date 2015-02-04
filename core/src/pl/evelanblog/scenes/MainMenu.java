package pl.evelanblog.scenes;

import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Button;
import pl.evelanblog.paxcosmica.PaxCosmica;
import pl.evelanblog.paxcosmica.Stats;
import pl.evelanblog.paxcosmica.control.MousePointer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MainMenu implements Screen, InputProcessor {

	private final PaxCosmica game;
	private Button play, options, credits, exit;
	private Sprite planet, background, paxCosmica, dim;
	private MousePointer mousePointer;
	private float count = 360.0f;
	private float dimValue;
	private Stage menuStage;

	public MainMenu(final PaxCosmica game) {
		this.game = game;
		menuStage = new Stage();
		dim = new Sprite(Assets.dim);
		mousePointer = new MousePointer();
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0, 0, 0, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (count < 0.0f)
			count = 360.0f;
		else
			count -= 0.01f;

		planet.setPosition(planet.getX() - 0.005f, planet.getY() - 0.05f);
		planet.setRotation(count);
		menuStage.act();
		menuStage.draw();
		menuStage.getBatch().begin();
		background.draw(menuStage.getBatch());
		planet.draw(menuStage.getBatch());
		paxCosmica.draw(menuStage.getBatch());
		play.draw(menuStage.getBatch(), 1);
		options.draw(menuStage.getBatch(), 1);
		credits.draw(menuStage.getBatch(), 1);
		exit.draw(menuStage.getBatch(), 1);
		dimScreen(delta);
		menuStage.getBatch().end();

	}

	private void dimScreen(float delta) {
		if (dimValue > 0)
			dimValue -= delta;

		if (dimValue > 0)
			dim.draw(menuStage.getBatch(), dimValue);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		screenY = Gdx.graphics.getHeight() - screenY;
		mousePointer.setPosition(screenX, screenY);
		Assets.playSound(Assets.clickSfx);

		// PLAY BUTTON
		if (mousePointer.overlaps(play)) {
			Stats.clear();
			game.setScreen(GameStateManager.galaxyMap);
			dispose();
		}

		// OPTIONS BUTTON
		else if (mousePointer.overlaps(options)) {
			Assets.track1.stop();
			dispose();
		}

		// CREDITS BUTTON
		else if (mousePointer.overlaps(credits)) {
			game.setScreen(new CreditsScreen(game));
		}

		// EXIT BUTTON
		else if (mousePointer.overlaps(exit))
			Gdx.app.exit();

		return true;
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		dimValue = 1f;

		background = new Sprite(Assets.mainmenu);
		background.setBounds(0, 0, Assets.mainmenu.getWidth(), Assets.mainmenu.getHeight());
		background.setOriginCenter();

		planet = new Sprite(Assets.planet);
		planet.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		planet.setBounds(0, 0, Assets.planet.getWidth(), Assets.planet.getHeight());
		planet.setOriginCenter();
		planet.setScale(0.90f);

		paxCosmica = new Sprite(Assets.paxCosmica);
		paxCosmica.setBounds(50, 150, Assets.paxCosmica.getWidth(), Assets.paxCosmica.getHeight());

		play = new Button(false, 1440, 602, 480, 144, Assets.playButton);
		options = new Button(false, 1440, 458, 480, 144, Assets.optionsButton);
		credits = new Button(false, 1440, 314, 480, 144, Assets.creditsButton);
		exit = new Button(false, 1440, 170, 480, 144, Assets.exitButton);

		Gdx.input.setInputProcessor(this);
		Assets.track1.play();
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
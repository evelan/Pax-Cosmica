package pl.evelanblog.scenes;

import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Button;
import pl.evelanblog.paxcosmica.GameStateManager;
import pl.evelanblog.paxcosmica.PaxCosmica;
import pl.evelanblog.paxcosmica.Stats;
import pl.evelanblog.paxcosmica.control.MousePointer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class MainMenu implements Screen, InputProcessor {

	private final PaxCosmica game;
	private Button play, options, credits, exit;
	private Sprite planet, background, paxCosmica, dim;
	private MousePointer mousePointer;
	private float count = 360.0f;
	private float dimValue;

	public MainMenu(final PaxCosmica game) {
		this.game = game;
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
		game.getMenu().act();
		game.getMenu().draw();
		game.getMenu().getBatch().begin();
		background.draw(game.getMenu().getBatch());
		planet.draw(game.getMenu().getBatch());
		paxCosmica.draw(game.getMenu().getBatch());
		play.draw(game.getMenu().getBatch(), 1);
		options.draw(game.getMenu().getBatch(), 1);
		credits.draw(game.getMenu().getBatch(), 1);
		exit.draw(game.getMenu().getBatch(), 1);
		dimScreen(delta);
		game.getMenu().getBatch().end();
		
	}

	private void dimScreen(float delta) {
		if (dimValue > 0)
			dimValue -= delta;

		if (dimValue > 0)
			dim.draw(game.getMenu().getBatch(), dimValue);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		screenY = Gdx.graphics.getHeight() - screenY;
		mousePointer.setPosition(screenX, screenY);
		Assets.playSound(Assets.clickSfx);
		
		//PLAY BUTTON
		if (mousePointer.overlaps(play)) {
			Stats.clear();
			game.setScreen(GameStateManager.galaxyMap);
			dispose();
		}
		
		//OPTIONS BUTTON
		else if (mousePointer.overlaps(options)) {
			Assets.track1.stop();
			dispose();
		}
		
		//CREDITS BUTTON
		else if (mousePointer.overlaps(credits)) {
			game.setScreen(new CreditsScreen(game));
		}
		
		//EXIT BUTTON
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
		
		play = new Button(1440, 602, 480, 144, "buttons/playButton.png");
		options = new Button(1440, 458, 480, 144, "buttons/optionsButton.png");
		credits = new Button(1440, 314, 480, 144, "buttons/creditsButton.png");
		exit = new Button(1440, 170, 480, 144, "buttons/exitButton.png");

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
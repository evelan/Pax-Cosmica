package pl.evelanblog.scenes;

import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Button;
import pl.evelanblog.paxcosmica.PaxCosmica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MainMenu implements Screen, InputProcessor {

	final PaxCosmica game;
	Button play, options, credits, exit;
	Sprite planet, background, paxCosmica;
	Rectangle mousePointer;
	private float count = 360.0f;

	public MainMenu(final PaxCosmica game) {
		this.game = game;
		background = new Sprite(Assets.mainmenu);
		background.setBounds(0, 0, Assets.mainmenu.getWidth(), Assets.mainmenu.getHeight());

		planet = new Sprite(Assets.planet);
		planet.setBounds(0, 0, Assets.planet.getWidth(), Assets.planet.getHeight());
		planet.setOriginCenter();
		planet.setScale(0.90f);

		paxCosmica = new Sprite(Assets.paxCosmica);
		paxCosmica.setBounds(50,150, Assets.paxCosmica.getWidth(), Assets.paxCosmica.getHeight());
		Gdx.input.setInputProcessor(this);
		Assets.track1.play();
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0, 0, 0.3f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (count < 0.0f)
			count = 360.0f;
		else
			count -= 0.03f;

		planet.setPosition(planet.getX() - 0.005f, planet.getY() - 0.05f);

		planet.setRotation(count);

		game.camera.update();

		game.batch.begin();
		background.draw(game.batch);
		planet.draw(game.batch);
		paxCosmica.draw(game.batch);
		play.draw(game.batch);
		options.draw(game.batch);
		credits.draw(game.batch);
		exit.draw(game.batch);
		game.batch.end();
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		screenY = Gdx.graphics.getHeight() - screenY;
		mousePointer.setPosition(screenX, screenY);
		Assets.playSound(Assets.clickSfx);
		if (play.getBoundingRectangle().overlaps(mousePointer)) {
			game.setScreen(new GameScreen(game));
			Assets.track1.stop();
			dispose();
		}
		else if (options.getBoundingRectangle().overlaps(mousePointer))
			Gdx.app.exit();
		else if (credits.getBoundingRectangle().overlaps(mousePointer))
			Gdx.app.exit();
		else if (exit.getBoundingRectangle().overlaps(mousePointer))
			Gdx.app.exit();

		return true;
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		game.camera = new OrthographicCamera();
		game.camera.setToOrtho(false, 1280, 768);

		game.batch = new SpriteBatch();

		game.font = new BitmapFont();
		mousePointer = new Rectangle();
		mousePointer.setSize(2);

		play = new Button(1000, 500, "buttons/playButton.png");
		options = new Button(1000, 400, "buttons/optionsButton.png");
		credits = new Button(1000, 300, "buttons/creditsButton.png");
		exit = new Button(1000, 200, "buttons/exitButton.png");
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
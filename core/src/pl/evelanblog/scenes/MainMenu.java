package pl.evelanblog.scenes;

import pl.evelanblog.paxcosmica.Button;
import pl.evelanblog.paxcosmica.PaxCosmica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MainMenu implements Screen, InputProcessor {

	final PaxCosmica game;
	Button play, options, credits, exit;
	Rectangle mousePointer;

	public MainMenu(final PaxCosmica game) {
		this.game = game;
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0, 0, 0.3f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.camera.update();

		game.batch.begin();
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
		if (play.getBoundingRectangle().overlaps(mousePointer))
			game.setScreen(new GameScreen(game));
		else if(options.getBoundingRectangle().overlaps(mousePointer))
			Gdx.app.exit();
		else if(credits.getBoundingRectangle().overlaps(mousePointer))
			Gdx.app.exit();
		else if(exit.getBoundingRectangle().overlaps(mousePointer))
			Gdx.app.exit();
		dispose();
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

		play = new Button(900, 500, "buttons/playButton.png");
		options = new Button(900, 400, "buttons/optionsButton.png");
		credits = new Button(900, 300, "buttons/creditsButton.png");
		exit = new Button(900, 200, "buttons/exitButton.png");
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
package pl.evelanblog.scenes;

import pl.evelanblog.paxcosmica.PaxCosmica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenu implements Screen {

	final PaxCosmica game;

	public MainMenu(final PaxCosmica game) {
		this.game = game;
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.camera.update();
		//game.batch.setProjectionMatrix(game.camera.combined);

		game.batch.begin();
		game.font.draw(game.batch, "Pax Cosmica", 600, 700);
		game.font.draw(game.batch, "Alpha 0.1", 10, 20);
		game.font.draw(game.batch, "Singleplayer", 480,500);
		game.font.draw(game.batch, "Multiplayer", 480,480);
		game.font.draw(game.batch, "Scoreboard", 480,460);
		game.font.draw(game.batch, "Credits", 480,440);
		game.font.draw(game.batch, "Exit", 480,420);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
		game.camera.viewportHeight = height;
		game.camera.viewportWidth = width;

	}

	@Override
	public void show() {
		game.camera = new OrthographicCamera();
		game.batch = new SpriteBatch();

		game.font = new BitmapFont();

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
package pl.evelanblog.scenes;

import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.PaxCosmica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class LoadingScreen implements Screen {

	private PaxCosmica game;
	int progress = 10;

	public LoadingScreen(final PaxCosmica game)
	{
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
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

}

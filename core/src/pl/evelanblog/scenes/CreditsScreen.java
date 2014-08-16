package pl.evelanblog.scenes;

import pl.evelanblog.paxcosmica.PaxCosmica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class CreditsScreen implements Screen {

	private BitmapFont font;
	private PaxCosmica game;
	private static float scroll = 200;
	private static float speed = 50;

	public CreditsScreen(final PaxCosmica game)
	{
		this.game = game;
		font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isTouched()) {
			game.setScreen(new MainMenu(game));
			dispose();
		}
		/*
		 * TODO: if (Gdx.input.isTouched()) speed = 250; else speed = 50;
		 */
		scroll += speed * delta;

		game.batch.begin();
		font.draw(game.batch, "Pax Cosmica", 200, scroll + 120);
		font.draw(game.batch, "Wykonanie", 200, scroll + 70);
		font.draw(game.batch, "Jakub Pomykala", 200, scroll + 50);
		font.draw(game.batch, "Umyj pazdzierz sukwo", 200, scroll);
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

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
		font.dispose();

	}

}

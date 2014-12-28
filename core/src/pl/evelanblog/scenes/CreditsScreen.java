package pl.evelanblog.scenes;

import pl.evelanblog.paxcosmica.Button;
import pl.evelanblog.paxcosmica.GameStateManager;
import pl.evelanblog.paxcosmica.PaxCosmica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class CreditsScreen implements Screen, InputProcessor {

	private BitmapFont font;
	private PaxCosmica game;
	private Button exit;
	private float scroll;
	private float speed = 50;
	

	public CreditsScreen(final PaxCosmica game)
	{
		this.game = game;
		exit = new Button(1230, 50, 640, 192, "buttons/exitButton.png");
		font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isTouched())
			speed = 250;
		else
			speed = 50;

		scroll += speed * delta;

		game.getSprBatch().begin();
		font.draw(game.getSprBatch(), "Pax Cosmica", 200, scroll + 80);
		font.draw(game.getSprBatch(), "Code creator: Jakub Pomykala", 200, scroll + 50);
		font.draw(game.getSprBatch(), "Code destroyer: Dave", 200, scroll + 20);
		exit.draw(game.getSprBatch());
		game.getSprBatch().end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		scroll = 200;
		Gdx.input.setInputProcessor(this);
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
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (game.getMouse().overlaps(screenX, screenY, exit))
		{
			game.setScreen(GameStateManager.mainMenu);
			dispose();
		}
		return true;
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

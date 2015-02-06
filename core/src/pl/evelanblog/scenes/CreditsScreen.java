package pl.evelanblog.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.scenes.scene2d.Stage;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Button;
import pl.evelanblog.paxcosmica.PaxCosmica;

public class CreditsScreen implements Screen, InputProcessor {

	private BitmapFont font;
	private PaxCosmica game;
	private Button exit;
	private float scroll;
	private float speed = 50;
    private Stage creditsStage;
	

	public CreditsScreen(final PaxCosmica game)
	{
		this.game = game;
        creditsStage = new Stage(new StretchViewport(1920, 1080));
		//exit = new Button(false, 1230, 50, 640, 192, "buttons/exitButton.png");
        exit = new Button(1500, 20, 400, 96, Assets.exitButton);
		font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"), false);

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

		game.getSpriteBatch().begin();
		font.draw(game.getSpriteBatch(), "Pax Cosmica", 200, scroll + 80);
		font.draw(game.getSpriteBatch(), "Code creator: Jakub Pomykala", 200, scroll + 50);
		font.draw(game.getSpriteBatch(), "Code destroyer: Dave", 200, scroll + 20);
		exit.draw(game.getSpriteBatch(), 1);
		game.getSpriteBatch().end();
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
		Assets.playSound(Assets.clickSfx);
		screenY = Gdx.graphics.getHeight() - screenY;
        screenX = (int) (screenX*creditsStage.getViewport().getWorldWidth()/Gdx.graphics.getWidth());
        screenY = (int) (screenY*creditsStage.getViewport().getWorldHeight()/Gdx.graphics.getHeight());
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

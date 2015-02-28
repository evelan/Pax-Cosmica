package pl.evelanblog.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import pl.evelanblog.GUI.Button;
import pl.evelanblog.GUI.CheckBox;
import pl.evelanblog.GUI.CustomText;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.PaxCosmica;
import pl.evelanblog.paxcosmica.PaxPrefs;
import pl.evelanblog.paxcosmica.control.MousePointer;
import pl.evelanblog.utilities.GameManager;

public class OptionsScreen implements Screen, InputProcessor {
	private PaxCosmica game;
	private Stage optionsStage;
	private Button exit;
	private CheckBox musicCheckbox, soundCheckbox;
	CustomText music, sfx;
	MousePointer mousePointer;

	public OptionsScreen(final PaxCosmica game) {
		this.game = game;
		mousePointer = GameManager.getMouse();
		optionsStage = new Stage(new StretchViewport(Assets.worldWidth, Assets.worldHeight));
		exit = new Button(1500, 20, 400, 96, Assets.exitButton);

		music = new CustomText("Music", 480, 540);
		sfx = new CustomText("SFX", 480, 440);

		musicCheckbox = new CheckBox(590, 575, 1.25f);
		soundCheckbox = new CheckBox(590, 375, 1.25f);

		optionsStage.addActor(music);
		optionsStage.addActor(musicCheckbox);
		optionsStage.addActor(soundCheckbox);
		optionsStage.addActor(sfx);
		optionsStage.addActor(exit);
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
		Assets.playSound(Assets.clickSfx);
		screenY = Gdx.graphics.getHeight() - screenY;
		screenX = (int) (screenX * optionsStage.getViewport().getWorldWidth() / Gdx.graphics.getWidth());
		screenY = (int) (screenY * optionsStage.getViewport().getWorldHeight() / Gdx.graphics.getHeight());
		GameManager.getMouse().setPosition(screenX, screenY);

		if (GameManager.getMouse().overlaps(musicCheckbox)) {
			musicCheckbox.not();

			PaxPrefs.putBoolean(PaxPrefs.MUSIC_ENABLED, musicCheckbox.getIsChecked());
			if (!musicCheckbox.getIsChecked()) {
				Assets.track1.stop();
			} else {
				Assets.play(Assets.track1);
			}
		}

		if (GameManager.getMouse().overlaps(soundCheckbox)) {
			soundCheckbox.not();
			PaxPrefs.putBoolean(PaxPrefs.SOUND_ENABLED, soundCheckbox.getIsChecked());
		}

		// EXIT BUTTON
		if (GameManager.getMouse().overlaps(exit)) {
			game.setScreen(GameManager.mainMenu);
			dispose();
		}
		return true;

	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		musicCheckbox.setIsChecked(PaxPrefs.getBoolean(PaxPrefs.MUSIC_ENABLED, true));
		soundCheckbox.setIsChecked(PaxPrefs.getBoolean(PaxPrefs.SOUND_ENABLED, true));
		optionsStage.draw();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}

	//TODO: mnimal, medium, high
	//TODO: save battery - less fps (30)
}

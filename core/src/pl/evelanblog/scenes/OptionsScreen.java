package pl.evelanblog.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import pl.evelanblog.paxcosmica.*;

public class OptionsScreen implements Screen, InputProcessor
{
    private PaxCosmica game;
    private CustomText music, sfx;
    private Stage optionsStage;
    private Button exit;
    private CheckBox musicCheckbox, soundCheckbox;

    public OptionsScreen(final PaxCosmica game)
    {
        this.game = game;
        optionsStage = new Stage(new StretchViewport(Assets.worldWidth, Assets.worldHeight));
        exit = new Button(1500, 20, 400, 96, Assets.exitButton);

        music = new CustomText("Music", 480, 540);
        sfx = new CustomText("SFX", 480, 440);

        musicCheckbox = new CheckBox(580, 500, 64, 64);
        soundCheckbox = new CheckBox(580, 400, 64, 64);

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
        if(PaxPreferences.getSoundEnabled())
            Assets.playSound(Assets.clickSfx);
        screenY = Gdx.graphics.getHeight() - screenY;
        screenX = (int)(screenX*optionsStage.getViewport().getWorldWidth()/Gdx.graphics.getWidth());
        screenY = (int)(screenY*optionsStage.getViewport().getWorldHeight()/Gdx.graphics.getHeight());
        game.getMouse().setPosition(screenX, screenY);

        if(game.getMouse().overlaps(musicCheckbox)) {
            musicCheckbox.not();
            PaxPreferences.setMusicEnabled(musicCheckbox.getIsChecked());
            if (!musicCheckbox.getIsChecked())
            {
                Assets.track1.stop();
                Assets.track2.stop();
            }
        }

        if(game.getMouse().overlaps(soundCheckbox))
        {
            soundCheckbox.not();
            PaxPreferences.setSoundEnabled(soundCheckbox.getIsChecked());
        }

        // EXIT BUTTON
        else if (game.getMouse().overlaps(exit)) {
            game.setScreen(GameStateManager.mainMenu);
            dispose();
        }
        return true;

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

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        musicCheckbox.setIsChecked(PaxPreferences.getMusicEnabled());
        soundCheckbox.setIsChecked(PaxPreferences.getSoundEnabled());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
	//TODO volume music, sfx

}

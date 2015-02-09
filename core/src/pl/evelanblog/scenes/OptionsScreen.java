package pl.evelanblog.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import pl.evelanblog.GUI.Button;
import pl.evelanblog.GUI.CheckBox;
import pl.evelanblog.GUI.Slider;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.GUI.CustomText;
import pl.evelanblog.paxcosmica.PaxCosmica;
import pl.evelanblog.paxcosmica.PaxPreferences;
import pl.evelanblog.paxcosmica.control.MousePointer;

public class OptionsScreen implements Screen, InputProcessor
{
    private PaxCosmica game;
    private CustomText music, sfx;
	private MousePointer mousePointer;
    private Stage optionsStage;
    private Button exit;
    private CheckBox musicCheckbox, soundCheckbox;
    private Slider musicVolume, soundVolume;
    private int temp = 0;

    public OptionsScreen(final PaxCosmica game)
    {
        this.game = game;
        mousePointer = game.getMouse();
        optionsStage = new Stage(new StretchViewport(Assets.worldWidth, Assets.worldHeight));
        exit = new Button(1500, 20, 400, 96, Assets.exitButton);

        music = new CustomText("Music", 480, 540);
        sfx = new CustomText("SFX", 480, 440);

        musicCheckbox = new CheckBox(590, 575, 1.25f);
        soundCheckbox = new CheckBox(590, 375, 1.25f);
        musicVolume = new Slider(800, 620, 2, PaxPreferences.getMusicVolume());
        soundVolume = new Slider(800, 420, 2, PaxPreferences.getSoundVolume());
        musicVolume.setIsEnabled(PaxPreferences.getMusicEnabled());
        soundVolume.setIsEnabled(PaxPreferences.getSoundEnabled());

        optionsStage.addActor(music);
        optionsStage.addActor(musicCheckbox);
        optionsStage.addActor(soundCheckbox);
        optionsStage.addActor(sfx);
        optionsStage.addActor(exit);
        optionsStage.addActor(musicVolume);
        optionsStage.addActor(soundVolume);
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
                musicVolume.setIsEnabled(false);
            }
            else
            {
                musicVolume.setIsEnabled(true);
                Assets.play(Assets.track1);
            }
        }

        if(game.getMouse().overlaps(soundCheckbox))
        {
            soundCheckbox.not();
            PaxPreferences.setSoundEnabled(soundCheckbox.getIsChecked());
            if(soundCheckbox.getIsChecked())
                soundVolume.setIsEnabled(true);
            else
                soundVolume.setIsEnabled(false);
        }

        // knob
        if (!musicVolume.isPressed() && musicVolume.isEnabled()) {
            musicVolume.setIsPressed(mousePointer.overlaps(musicVolume.getKnob()));
        }
        if(!soundVolume.isPressed() && soundVolume.isEnabled()) {
            soundVolume.setIsPressed(mousePointer.overlaps(soundVolume.getKnob()));
        }
        // EXIT BUTTON
        if (game.getMouse().overlaps(exit)) {
            game.setScreen(GameStateManager.mainMenu);
            dispose();
        }
        return true;

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (musicVolume.isPressed()) {
            musicVolume.setIsPressed(false);
        }
        if(soundVolume.isPressed()) {
            soundVolume.setIsPressed(false);
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        screenX = (int)(screenX*optionsStage.getViewport().getWorldWidth()/Gdx.graphics.getWidth());

        if (musicVolume.isPressed()) {

            if (screenX - musicVolume.getX() >= 0 && screenX - musicVolume.getX() <= musicVolume.getWidth()) {
                musicVolume.setValue((int) (screenX - musicVolume.getX()));
                PaxPreferences.setMusicVolume(musicVolume.getVolume());
                Assets.track1.setVolume(PaxPreferences.getMusicVolume());
                Assets.track2.setVolume(PaxPreferences.getMusicVolume());
            }
        }
        if(soundVolume.isPressed()) {
            if(screenX-soundVolume.getX() >= 0 && screenX-soundVolume.getX() <= soundVolume.getWidth()) {
                soundVolume.setValue((int) (screenX - soundVolume.getX()));
                PaxPreferences.setSoundVolume(soundVolume.getVolume());
            }
        }
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
        musicCheckbox.setIsChecked(PaxPreferences.getMusicEnabled());
        soundCheckbox.setIsChecked(PaxPreferences.getSoundEnabled());
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

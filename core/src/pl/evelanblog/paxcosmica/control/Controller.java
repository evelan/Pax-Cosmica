package pl.evelanblog.paxcosmica.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Button;
import pl.evelanblog.paxcosmica.PaxCosmica;
import pl.evelanblog.scenes.GameStateManager;

public class Controller implements InputProcessor {

	private PaxCosmica game;
	private Vector2 defKnobPos = new Vector2(96, 96);
	private Sprite knob;
	private Button buttonA, buttonB, pauseButton, powerButton, continueButton, exitButton;

	private float velX = 0, velY = 0;
	private boolean hit = false;
	private boolean knobPressed = false;
	private boolean pauseGame = false;
	private boolean menuGame = false;
	private boolean upgradeScreen = false;
	private int hitPointer = -1;
	private int knobPointer = -1;
/* DAFUQ ?! Czemu trzymasz metody, które nic nie robią :o BO MOGĘ! HUE HUE HUE*/
	public Controller(final PaxCosmica game) {

		this.game = game;
		knob = new Sprite(Assets.knob);
		knob.setSize(Assets.knob.getWidth(), Assets.knob.getHeight());
		knob.setPosition(defKnobPos.x, defKnobPos.y);

		buttonA = new Button(Assets.buttonA);
		buttonA.setPosition(1100, 160);

		buttonB = new Button(Assets.buttonB);
		buttonB.setPosition(920, 32);

		powerButton = new Button(Assets.powerButton);
		powerButton.setPosition(540, 20);

		pauseButton = new Button(Assets.pauseButton);
		pauseButton.setPosition(1200, Assets.worldHeight - 50);

		continueButton = new Button(Assets.continueButton);
		continueButton.setPosition(540, 312);

		exitButton = new Button(Assets.exitButton);
		exitButton.setPosition(540, 380);
	}

	public float getVelX()
	{
		return velX;
	}

	public float getVelY()
	{
		return velY;
	}

	public boolean getHit()
	{
		return hit;
	}

	public boolean getPause()
	{
		return pauseGame;
	}

	public boolean getUpgradeScreen()
	{
		return upgradeScreen;
	}

	public boolean getMenuGame()
	{
		return menuGame;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.A) {
			hit = true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.A)
			hit = false;
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		game.getMouse().setPosition(screenX, screenY);

		if (!hit && game.getMouse().overlaps(buttonA)) {
			hit = true;
			hitPointer = pointer;
		}

		if (game.getMouse().overlaps(pauseButton))
		{
			pauseButton=new Button(pauseGame ? Assets.pauseButton : Assets.unpauseButton);
			menuGame = menuGame ? false : true;
			pauseGame = pauseGame ? false : true;

		} else if (game.getMouse().overlaps(powerButton))
		{
			pauseButton=new Button(pauseGame ? Assets.pauseButton : Assets.unpauseButton);
			pauseGame = pauseGame ? false : true;
			upgradeScreen = upgradeScreen ? false : true;

		} else if (game.getMouse().overlaps(continueButton))
		{
			pauseButton=new Button(pauseGame ? Assets.pauseButton : Assets.unpauseButton);
			pauseGame = pauseGame ? false : true;
			menuGame = menuGame ? false : true;
		} else if (game.getMouse().overlaps(exitButton))
		{
			pauseGame = pauseGame ? false : true;
			game.setScreen(GameStateManager.mainMenu);
		}

		if (!knobPressed) {
			if (game.getMouse().overlaps(knob.getBoundingRectangle())) {
				knob.setPosition(screenX - (knob.getWidth() / 2), screenY - (knob.getHeight() / 2));
				knobPressed = true;
				knobPointer = pointer;
				velX = (((screenX - (knob.getWidth() / 2)) - defKnobPos.x)) / 64;
				velY = ((screenY - (knob.getHeight() / 2)) - defKnobPos.y) / 64;
			} else {
				knobPressed = false;
			}
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		if (knobPressed && knobPointer == pointer) {
			knobPressed = false;
			knob.setPosition(defKnobPos.x, defKnobPos.y);
			velX = 0;
			velY = 0;
			knobPointer = -1;
		}

		if (hit && hitPointer == pointer) {
			hit = false;
			hitPointer = -1;
		}

		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		screenY = Gdx.graphics.getHeight() - screenY;

		if (knobPressed && knobPointer == pointer) {
			if (screenX > defKnobPos.x + knob.getWidth())
				screenX = (int) (defKnobPos.x + knob.getWidth());
			else if (screenX < defKnobPos.x)
				screenX = (int) defKnobPos.x;

			if (screenY > defKnobPos.y + knob.getHeight())
				screenY = (int) (defKnobPos.y + knob.getHeight());
			else if (screenY < defKnobPos.y)
				screenY = (int) defKnobPos.y;

			knob.setPosition(screenX - (knob.getWidth() / 2), screenY - (knob.getHeight() / 2));
			velX = (((screenX - (knob.getWidth() / 2)) - defKnobPos.x)) / 64;
			velY = ((screenY - (knob.getHeight() / 2)) - defKnobPos.y) / 64;
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
	public boolean keyTyped(char character) {
		return false;
	}

}
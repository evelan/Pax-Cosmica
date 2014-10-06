package pl.evelanblog.paxcosmica.control;

import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Button;
import pl.evelanblog.paxcosmica.GameStateManager;
import pl.evelanblog.paxcosmica.PaxCosmica;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Controller implements InputProcessor {

	private PaxCosmica game;
	private static Vector2 defKnobPos = new Vector2(96, 96);
	private Rectangle mousePointer = new Rectangle(0, 0, 1, 1);
	public static Sprite knob;
	public static Button buttonA, buttonB, pauseButton, powerButton, continueButton, exitButton;

	private static float velX = 0, velY = 0;
	private static boolean hit = false;
	private static boolean knobPressed = false;
	private static boolean pauseGame = false;
	private static boolean menuGame = false;
	private static boolean upgradeScreen = false;
	private static int hitPointer = -1;
	private static int knobPointer = -1;

	public Controller(final PaxCosmica game) {

		this.game = game;
		knob = new Sprite(Assets.knob);
		knob.setSize(Assets.knob.getWidth(), Assets.knob.getHeight());
		knob.setPosition(defKnobPos.x, defKnobPos.y);

		buttonA = new Button(Assets.buttonA);
		buttonA.setPosition(1100, 160);

		buttonB = new Button(Assets.buttonB);
		buttonB.setPosition(920, 32);

		powerButton = new Button("buttons/powerButton.png");
		powerButton.setPosition(540, 20);

		pauseButton = new Button(Assets.pauseButton);
		pauseButton.setPosition(1200, Gdx.graphics.getHeight() - 50);

		continueButton = new Button("buttons/continueButton.png");
		continueButton.setPosition(540, 312);

		exitButton = new Button(Assets.exitButton);
		exitButton.setPosition(540, 380);
	}

	public static float getVelX()
	{
		return velX;
	}

	public static float getVelY()
	{
		return velY;
	}

	public static boolean getHit()
	{
		return hit;
	}

	public static boolean getPause()
	{
		return pauseGame;
	}

	public static boolean getUpgradeScreen()
	{
		return upgradeScreen;
	}

	public static boolean getMenuGame()
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
		screenY = Gdx.graphics.getHeight() - screenY;
		mousePointer.setPosition(screenX, screenY);

		if (!hit && mousePointer.overlaps(buttonA.getBoundingRectangle())) {
			hit = true;
			hitPointer = pointer;
		}

		if (mousePointer.overlaps(pauseButton.getBoundingRectangle()))
		{
			pauseButton.setTexture(pauseGame ? Assets.pauseButton : Assets.unpauseButton);
			menuGame = menuGame ? false : true;
			pauseGame = pauseGame ? false : true;

		} else if (mousePointer.overlaps(powerButton.getBoundingRectangle()))
		{
			pauseButton.setTexture(pauseGame ? Assets.pauseButton : Assets.unpauseButton);
			pauseGame = pauseGame ? false : true;
			upgradeScreen = upgradeScreen ? false : true;

		} else if (mousePointer.overlaps(continueButton.getBoundingRectangle()))
		{
			pauseButton.setTexture(pauseGame ? Assets.pauseButton : Assets.unpauseButton);
			pauseGame = pauseGame ? false : true;
			menuGame = menuGame ? false : true;
		} else if (mousePointer.overlaps(exitButton.getBoundingRectangle()))
		{
			pauseGame = pauseGame ? false : true;
			game.setScreen(GameStateManager.mainMenu);
		}

		if (!knobPressed) {
			if (mousePointer.overlaps(knob.getBoundingRectangle())) {
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
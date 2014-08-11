package pl.evelanblog.paxcosmica.control;

import pl.evelanblog.paxcosmica.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Controller implements InputProcessor {

	private static Vector2 defKnobPos = new Vector2(96, 96);
	public static Sprite knob;
	public static Sprite buttonA;
	public static Sprite buttonB;
	public static Sprite pauseButton;
	public static Sprite backgroundKnob;

	private Rectangle mousePointer = new Rectangle(0, 0, 1, 1);

	private static float velX = 0, velY = 0;
	private static boolean hit = false;
	private static boolean knobPressed = false;
	private static boolean pauseGame = false;
	private static int hitPointer = -1;
	private static int knobPointer = -1;

	public Controller() {
		knob = new Sprite(Assets.knob);
		knob.setSize(Assets.knob.getWidth(), Assets.knob.getHeight());
		knob.setPosition(defKnobPos.x, defKnobPos.y);
		
		backgroundKnob = new Sprite(Assets.backgroundKnob);
		backgroundKnob.setSize(Assets.backgroundKnob.getWidth(), Assets.backgroundKnob.getHeight());
		backgroundKnob.setPosition(32, 32);

		buttonA = new Sprite(Assets.buttonA);
		buttonA.setSize(Assets.buttonA.getWidth(), Assets.buttonA.getHeight());
		buttonA.setPosition(1150, 64);

		buttonB = new Sprite(Assets.buttonB);
		buttonB.setSize(Assets.buttonB.getWidth(), Assets.buttonB.getHeight());
		buttonB.setPosition(1000, 64);

		pauseButton = new Sprite(Assets.pauseButton);
		pauseButton.setSize(Assets.pauseButton.getWidth(), Assets.pauseButton.getHeight());
		pauseButton.setPosition(1200, 720);
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
			pauseGame = pauseGame ? false : true;
		}

		if (!knobPressed) {
			if (mousePointer.overlaps(knob.getBoundingRectangle())) {
				knob.setPosition(screenX - (knob.getWidth() / 2), screenY - (knob.getHeight() / 2));
				knobPressed = true;
				knobPointer = pointer;
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
		// ustawienie punktu 0,0 w lewym dolnym rogu
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
		// TODO Auto-generated method stub
		return false;
	}

}
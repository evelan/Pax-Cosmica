package pl.evelanblog.paxcosmica.control;

import pl.evelanblog.paxcosmica.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Joystick extends Rectangle implements InputProcessor {

	private static Vector2 defKnobPos = new Vector2(64, 64); // domyœlna pozycja
																// ga³ki
	private static Vector2 defButtonAPos = new Vector2(1200, 64); // domyœlna
																	// pozycja
																	// przycisku
																	// A
	private static Vector2 defButtonBPos = new Vector2(1000, 64); // domyœlna
																	// pozycja
																	// przycisku
																	// B

	public static Rectangle buttonA;
	public static Rectangle buttonB;
	private static float velX = 0, velY = 0;
	private static boolean hit = false;
	private static boolean knobPressed = false;
	private static int hitPointer = -1;
	private static int knobPointer = -1;

	public Joystick() {
		setHeight(Assets.knob.getHeight());
		setWidth(Assets.knob.getWidth());
		setPosition(defKnobPos.x, defKnobPos.y);

		buttonA = new Rectangle(defButtonAPos.x - Assets.button.getWidth(), defButtonAPos.y, Assets.button.getWidth(), Assets.button.getHeight());
		buttonB = new Rectangle(defButtonBPos.x - Assets.button.getWidth(), defButtonBPos.y, Assets.button.getWidth(), Assets.button.getHeight());
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

		if (!hit) {
			if (screenX > buttonA.x &&
					screenX < buttonA.x + buttonA.width &&
					screenY > buttonA.y &&
					screenY < buttonA.y + buttonA.height)
			{
				hit = true;
				hitPointer = pointer;
			}
		}

		if (!knobPressed) {
			if (screenX > defKnobPos.x &&
					screenX < defKnobPos.x + getWidth() &&
					screenY > defKnobPos.y &&
					screenY < defKnobPos.y + getHeight()) {

				setPosition(screenX - (getWidth() / 2), screenY - (getHeight() / 2));
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
			setPosition(defKnobPos.x, defKnobPos.y);
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
			if (screenX > defKnobPos.x + getWidth())
				screenX = (int) (defKnobPos.x + getWidth());
			else if (screenX < defKnobPos.x)
				screenX = (int) defKnobPos.x;

			if (screenY > defKnobPos.y + getHeight())
				screenY = (int) (defKnobPos.y + getHeight());
			else if (screenY < defKnobPos.y)
				screenY = (int) defKnobPos.y;

			setPosition(screenX - (getWidth() / 2), screenY - (getHeight() / 2));
			velX = (((screenX - (getWidth() / 2)) - defKnobPos.x)) / 64;
			Gdx.app.log("POS: ", velX + "");
			velY = ((screenY - (getHeight() / 2)) - defKnobPos.y) / 64;
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
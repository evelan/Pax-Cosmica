package pl.evelanblog.utilities;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import pl.evelanblog.GUI.Button;
import pl.evelanblog.GUI.CustomText;
import pl.evelanblog.enums.GameState;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Bar;
import pl.evelanblog.paxcosmica.PaxCosmica;
import pl.evelanblog.paxcosmica.Stats;
import pl.evelanblog.paxcosmica.control.MousePointer;
import pl.evelanblog.scenes.PowerManager;
import pl.evelanblog.world.World;

/**
 * Created by Evelan on 20-02-2015 - 03:18
 */
public class HUDController extends Stage implements InputProcessor {

	PaxCosmica game;
	private Button knob, buttonA, buttonB, pauseButton, powerButton, continueButton, exitButton, resumeButton;
	private CustomText scrap, score; // tekst  o ilośc złomu oraz punktów
	private Bar hpBar, shieldBar, bossHp, shieldReload;

	private static float velX, velY; // wychylenie gałki w osi X i Y
	private static boolean hit, knobPressed; // czy wciskamy gałkę
	private int knobPointer = -1, hitPointer = -1; // potrzebne do multitoucha
	private Vector2 defKnobPos; //domyślna pozycja z gałką
	private PowerManager powerManager; // powermanager, rozszerzony o Stage
	private MousePointer mousePointer; // przycsik myszki

	public HUDController(PaxCosmica game, Viewport viewport) {
		super(viewport);
		this.game = game;

		mousePointer = GameManager.getMouse();
		defKnobPos = new Vector2(96, 96);
		powerManager = new PowerManager();
		hpBar = new Bar(15, 1025, 200, 40, Assets.hullBar);
		shieldBar = new Bar(15, 980, 200, 40, Assets.shieldBar);
		shieldReload = new Bar(15, 930, 400, 10, Assets.shieldBar);
		bossHp = new Bar(390, 1025, 400, 40, Assets.bossHp);

		knob = new Button(defKnobPos.x, defKnobPos.y, 256, 256, Assets.knob);
		buttonA = new Button(1600, 256, 256, 256, Assets.buttonA);
		buttonB = new Button(1472, 0, 256, 256, Assets.buttonB);
		pauseButton = new Button(1750, 920, Assets.pauseButton);
		resumeButton = new Button(1750, 920, Assets.unpauseButton);
		continueButton = new Button(640, 540, 640, 192, Assets.continueButton);
		exitButton = new Button(640, 348, 640, 192, Assets.exitButton);
		powerButton = new Button(810, 20, Assets.powerButton);
		score = new CustomText("Score: " + Stats.score, 965, 1060);
		scrap = new CustomText("Scrap: " + Stats.scrap, 1200, 1060);

		if (Gdx.app.getType() == Application.ApplicationType.Android) { // jeśli odpalimy na PC to nie pokażą się knob i przyciski
			addActor(knob);
			addActor(buttonA);
			addActor(buttonB);
		}

		addActor(powerButton);
		addActor(pauseButton);
		addActor(resumeButton);
		addActor(continueButton);
		addActor(exitButton);
		addActor(score);
		addActor(scrap);
		addActor(hpBar);
		addActor(shieldBar);
		addActor(shieldReload);
		addActor(bossHp);
	}

	public void draw() {

		if (World.isBossFight()) {
			bossHp.setVisible(true);
			bossHp.setValue(World.getBoss().getHealth());
		}

		hpBar.setValue(World.getPlayer().getHealth());
		shieldBar.setValue(World.getPlayer().getShield());
		shieldReload.setValue(World.getPlayer().getReload());

		score.setText("Score: " + Stats.score);
		scrap.setText("Scrap: " + Stats.scrap);

		super.draw();
	}

	public void show() {
		velX = 0;
		velY = 0;
		hit = false;
		knobPressed = false;

		bossHp.setVisible(false);
		hpBar.setValue(World.getPlayer().getHealth(), World.getPlayer().getMaxHealth());
		shieldBar.setValue(World.getPlayer().getShield(), World.getPlayer().getMaxShield());
		shieldReload.setValue(World.getPlayer().getReload(), World.getPlayer().getMaxReload());
		bossHp.setValue(World.getBoss().getHealth(), World.getBoss().getMaxHealth());

		knobPointer = -1;
		hitPointer = -1;
		Gdx.input.setInputProcessor(this);
	}

	//pauzuje grę
	public void setPause() {
		pauseButton.setVisible(false);
		resumeButton.setVisible(true);
		continueButton.setVisible(true);
		exitButton.setVisible(true);
		knob.setVisible(false);
		buttonA.setVisible(false);
		buttonB.setVisible(false);
		powerButton.setVisible(false);
	}

	//gdy przegramy wyświetlamy tylko przycisk EXIT
	public void exitScreen() {
		exitButton.setVisible(true);
		powerButton.setVisible(false);
		pauseButton.setVisible(false);
		resumeButton.setVisible(false);
		knob.setVisible(false);
		buttonA.setVisible(false);
		buttonB.setVisible(false);
	}

	//przywraca grę
	public void setResume() {
		pauseButton.setVisible(true);
		resumeButton.setVisible(false);
		exitButton.setVisible(false);
		continueButton.setVisible(false);
		powerButton.setVisible(true);
		knob.setVisible(true);
		buttonA.setVisible(true);
		buttonB.setVisible(true);
	}

	//pokazuje power managera
	public void setPowerPanager() {
		pauseButton.setVisible(false);
		resumeButton.setVisible(true);

		powerManager.draw();
		knob.setVisible(false);
		buttonA.setVisible(false);
		buttonB.setVisible(false);
	}

	public static float getVelX() {
		return velX;
	}

	public static float getVelY() {
		return velY;
	}

	public static boolean getHit() {
		return hit;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		screenY = Gdx.graphics.getHeight() - screenY;

		screenX = (int) (screenX * getViewport().getWorldWidth() / Gdx.graphics.getWidth());
		screenY = (int) (screenY * getViewport().getWorldHeight() / Gdx.graphics.getHeight());

		mousePointer.setPosition(screenX, screenY);

		if (World.getState() == GameState.powermanager)
			powerManager.touchDown(mousePointer);


		if (!knobPressed)
			Assets.playSound(Assets.clickSfx);

		// A BUTTON
		if (!hit && mousePointer.overlaps(buttonA)) {
			hit = true;
			hitPointer = pointer;
		}

		//B BUTTON
		if (!hit && mousePointer.overlaps(buttonB)) {
			hit = true;
			hitPointer = pointer;
		}

		if (mousePointer.overlaps(pauseButton)) { // pause button
			World.setState(GameState.paused);
		} else if (mousePointer.overlaps(resumeButton)) { // resume button
			World.setState(GameState.ongoing);
		} else if (mousePointer.overlaps(powerButton)) { // powerManagerButton
			if (World.getState() == GameState.powermanager) {
				World.setState(GameState.ongoing);
			} else {
				World.setState(GameState.powermanager);
			}
		} else if (mousePointer.overlaps(continueButton)) { // continueButton
			World.setState(GameState.ongoing);
		} else if (mousePointer.overlaps(exitButton)) { // exitButton
			World.setState(GameState.defeat);
			Assets.track2.stop();
			Stats.save();
			game.setScreen(GameManager.mainMenu);
		}

		// knob
		if (!knobPressed) {
			if (mousePointer.overlaps(knob)) {
				knob.setPosition(screenX - (knob.getImageWidth() / 2),
						screenY - (knob.getImageHeight() / 2));
				knobPressed = true;
				knobPointer = pointer;
				velX = (((screenX - (knob.getImageWidth() / 2)) - defKnobPos.x)) / 64;
				velY = ((screenY - (knob.getImageHeight() / 2)) - defKnobPos.y) / 64;
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
			if (screenX > defKnobPos.x + knob.getImageWidth())
				screenX = (int) (defKnobPos.x + knob.getImageWidth());
			else if (screenX < defKnobPos.x)
				screenX = (int) defKnobPos.x;

			if (screenY > defKnobPos.y + knob.getImageHeight())
				screenY = (int) (defKnobPos.y + knob.getImageHeight());
			else if (screenY < defKnobPos.y)
				screenY = (int) defKnobPos.y;

			knob.setPosition(screenX - (knob.getImageWidth() / 2), screenY - (knob.getImageHeight() / 2));
			velX = (((screenX - (knob.getImageWidth() / 2)) - defKnobPos.x)) / 64;
			velY = ((screenY - (knob.getImageHeight() / 2)) - defKnobPos.y) / 64;
		}
		return true;
	}

	@Override
	public boolean keyDown(int keycode) {

		if (keycode == Input.Keys.ENTER)
			hit = true;

		if (keycode == Input.Keys.W)
			velY = 1;

		if (keycode == Input.Keys.S)
			velY = -1;

		if (keycode == Input.Keys.A)
			velX = -1;

		if (keycode == Input.Keys.D)
			velX = 1;

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.ENTER)
			hit = false;

		if (keycode == Input.Keys.W)
			velY = 0;

		if (keycode == Input.Keys.S)
			velY = 0;

		if (keycode == Input.Keys.A)
			velX = 0;

		if (keycode == Input.Keys.D)
			velX = 0;
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
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
}

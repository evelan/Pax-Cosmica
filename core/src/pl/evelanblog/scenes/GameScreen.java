package pl.evelanblog.scenes;

import pl.evelanblog.dynamicobjects.DynamicObject;
import pl.evelanblog.dynamicobjects.Player;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Background;
import pl.evelanblog.paxcosmica.Button;
import pl.evelanblog.paxcosmica.GameStateManager;
import pl.evelanblog.paxcosmica.PaxCosmica;
import pl.evelanblog.paxcosmica.Stats;
import pl.evelanblog.world.World;
import pl.evelanblog.world.World.GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen, InputProcessor {

	private final PaxCosmica game;
	private World world;
	private BitmapFont font;

	private Background background;
	private Vector2 defKnobPos = new Vector2(96, 96);
	private Button knob, buttonA, buttonB, pauseButton, powerButton, continueButton, exitButton, upPwr, downPwr;
	private Rectangle mousePointer;
	private Sprite dimScreen;

	private static float velX = 0;
	private static float velY = 0;
	private static boolean hit = false;
	private boolean knobPressed = false;

	private int knobPointer = -1;
	private int hitPointer = -1;
	private float hover = -1;
	private float power, hull, shield, weapon, engine;

	public GameScreen(final PaxCosmica game) {
		this.game = game;

		knob = new Button(true, defKnobPos.x, defKnobPos.y, 256, 256, "knob.png");
		buttonA = new Button(true, 1600, 256, 256, 256, "buttonA.png");
		buttonB = new Button(true, 1472, 0, 256, 256, "buttonB.png");
		powerButton = new Button(false, 860, 20, Assets.powerButton.getWidth(), Assets.powerButton.getHeight(), "buttons/powerButton.png");
		pauseButton = new Button(true, 1750, 920, Assets.pauseButton.getWidth(), Assets.pauseButton.getHeight(), "buttons/pauseButton.png");
		continueButton = new Button(false, 640, 540, 640, 192, "buttons/continueButton.png");
		exitButton = new Button(false, 640, 348, 640, 192, "buttons/exitButton.png");

		upPwr = new Button("up.png");
		downPwr = new Button("down.png");

		dimScreen = new Sprite(Assets.dim);

		mousePointer = new Rectangle(0, 0, 1, 1);
		font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
		background = new Background();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		
		// jeśli stan gry jest na ONGOING to update tła i reszty obiektów, jeśli nie to będziemy mieć efekt pauzy
		if (world.getState() == GameState.ongoing) {
			background.update(delta);
			world.update(delta);
		} else if (world.getState() == GameState.win) // wygrana i przechodzimy do mapy galaktyki
			game.setScreen(GameStateManager.galaxyMap);
		else if (world.getState() == GameState.defeat) // przegrana i wypierdlamy kolesia za bramę
			game.setScreen(GameStateManager.mainMenu);

		game.getBatch().begin();
		background.draw(game.getBatch(), delta);

		// działa tak jak chciałem, renderuje wszystkie obiekty w jednej pętli z jedną liniją
		for (DynamicObject obj : world.getObjects())
			obj.draw(game.getBatch(), delta);

		if (world.getPlayer().isAlive())
			world.getPlayer().draw(game.getBatch(), delta);

		Assets.hitEffect.draw(game.getBatch(), delta);
		Assets.explosionEffect.draw(game.getBatch(), delta);

		// te dwie pętle renderują paski osłony i HP
		for (int i = 0; i < world.getPlayer().getHealth(); i++)
			game.getBatch().draw(Assets.hullBar, 15 * i, Gdx.graphics.getHeight() - Assets.hullBar.getHeight());

		for (int i = 0; i < world.getPlayer().getShield(); i++)
			game.getBatch().draw(Assets.shieldBar, 15 * i, Gdx.graphics.getHeight() - (2 * Assets.shieldBar.getHeight()));

		font.draw(game.getBatch(), "Score: " + Stats.score, 5, Gdx.graphics.getHeight() - 100);
		font.draw(game.getBatch(), "Scrap: " + Stats.scrap, 5, Gdx.graphics.getHeight() - 80);

		// controls HUD
		knob.draw(game.getBatch(), 0.3f);
		buttonA.draw(game.getBatch(), 0.3f);
		buttonB.draw(game.getBatch(), 0.3f);

		if (world.getState() == GameState.powermanager || world.getState() == GameState.menu)
		{
			dimScreen.draw(game.getBatch(), 0.6f);
			pauseButton.setTexture(Assets.unpauseButton);
			pauseButton.draw(game.getBatch(), 0.9f);
			if (world.getState() == GameState.menu)
			{
				continueButton.draw(game.getBatch());
				exitButton.draw(game.getBatch());
			} else if (world.getState() == GameState.powermanager)
			{
				drawPwrManager();
				powerButton.draw(game.getBatch(), 0.9f);
			}
		} else
		{
			pauseButton.setTexture(Assets.pauseButton);
			pauseButton.draw(game.getBatch(), 0.3f);
			powerButton.draw(game.getBatch(), 0.3f);
		}

		game.getBatch().end();
	}

	private void drawPwrManager()
	{
		createBar(power, Player.powerGenerator, "Power " + (int) Player.powerGenerator + "  L" + (int) Player.powerLvl);
		createBar(hull, Player.hullPwr, "Hull " + (int) Player.hullPwr + "  L" + (int) Player.hullLvl);
		createBar(shield, Player.shieldPwr, "Shield " + (int) Player.shieldPwr + "  L" + (int) Player.shieldLvl);
		createBar(weapon, Player.weaponPwr, "Weapon " + (int) Player.weaponPwr + "  L" + (int) Player.weaponLvl);
		createBar(engine, Player.enginePwr, "Engine " + (int) Player.enginePwr + "  L" + (int) Player.engineLvl);
	}

	private void createBar(float x, float level, String name)
	{
		for (int i = 0; i < level; i++)
			game.getBatch().draw(Assets.upgradeBar, x, 200 + i * 30);

		if (hover != -1) {
			downPwr.setPosition(hover, 100);
			upPwr.setPosition(hover, 500);
			downPwr.draw(game.getBatch());
			upPwr.draw(game.getBatch());
		}

		font.draw(game.getBatch(), name, x + 10, 190);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		world = new World();

		//pozycje X w powermanagerze, tych pasków/stanów/poziomów ulepszeń
		power = 100;
		hull = 300;
		shield = 500;
		weapon = 700;
		engine = 900;
		dimScreen.setPosition(0, 0);

		Assets.track2.play();

		velX = 0;
		velY = 0;
		hit = false;
		knobPressed = false;
		knobPointer = -1;
		hitPointer = -1;
		hover = -1;

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		screenY = Gdx.graphics.getHeight() - screenY;
		mousePointer.setPosition(screenX, screenY);

		// A BUTTON
		if (!hit && mousePointer.overlaps(buttonA.getBoundingRectangle())) {
			hit = true;
			hitPointer = pointer;
		}

		// pause button
		if (mousePointer.overlaps(pauseButton.getBoundingRectangle()))
		{
			Assets.playSound(Assets.clickSfx);
			world.setState(world.getState() == GameState.menu ? GameState.ongoing : GameState.menu);

			// powerManagerButton
		} else if (mousePointer.overlaps(powerButton.getBoundingRectangle()))
		{
			Assets.playSound(Assets.clickSfx);
			world.setState(world.getState() == GameState.powermanager ? GameState.ongoing : GameState.powermanager);

			// continueButton
		} else if (mousePointer.overlaps(continueButton.getBoundingRectangle()))
		{
			Assets.playSound(Assets.clickSfx);
			world.setState(GameState.ongoing);
			// exitButton
		} else if (mousePointer.overlaps(exitButton.getBoundingRectangle()))
		{
			Assets.playSound(Assets.clickSfx);
			world.setState(GameState.defeat);
			game.setScreen(GameStateManager.mainMenu);
		}

		// knob
		if (!knobPressed) {
			if (mousePointer.overlaps(knob.getBoundingRectangle())) {
				knob.setPosition(screenX - (knob.getWidth() / 2), screenY -
						(knob.getHeight() / 2));
				knobPressed = true;
				knobPointer = pointer;
				velX = (((screenX - (knob.getWidth() / 2)) - defKnobPos.x)) / 64;
				velY = ((screenY - (knob.getHeight() / 2)) - defKnobPos.y) / 64;
			}
			else {
				knobPressed = false;
			}
		}

		// powerManager
		if (world.getState() == GameState.powermanager) {

			if (mousePointer.overlaps(upPwr.getBoundingRectangle()))
			{
				if (Player.powerGenerator > 0) {
					if (hover == hull && Player.hullLvl > Player.hullPwr)
					{
						Player.hullPwr++;
						Player.powerGenerator--;
					}
					else if (hover == weapon && Player.weaponLvl > Player.weaponPwr)
					{
						Player.weaponPwr++;
						Player.powerGenerator--;
					}
					else if (shield == hover && Player.shieldLvl > Player.shieldPwr)
					{
						Player.shieldPwr++;
						Player.powerGenerator--;
					}
					else if (engine == hover && Player.engineLvl > Player.enginePwr)
					{
						Player.enginePwr++;
						Player.powerGenerator--;
					}
				}

			} else if (mousePointer.overlaps(downPwr.getBoundingRectangle())) {

				if (hover == hull && Player.hullPwr > 0) {
					Player.hullPwr--;
					Player.powerGenerator++;
				}
				else if (hover == weapon && Player.weaponPwr > 0) {
					Player.weaponPwr--;
					Player.powerGenerator++;
				}
				else if (shield == hover && Player.shieldPwr > 0) {
					Player.shieldPwr--;
					Player.powerGenerator++;
				} else if (engine == hover && Player.enginePwr > 0) {
					Player.enginePwr--;
					Player.powerGenerator++;
				}
			}

			Assets.playSound(Assets.clickSfx);
			if (screenX > hull && screenX < hull + 200)
				hover = hull;
			else if (screenX > weapon && screenX < weapon + 200)
				hover = weapon;
			else if (screenX > shield && screenX < shield + 200)
				hover = shield;
			else if (screenX > engine && screenX < engine + 200)
				hover = engine;
			else
				hover = -1;
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
	public boolean keyDown(int keycode) {
		if (keycode == Keys.ENTER)
			hit = true;

		if (keycode == Keys.W)
			velY = 1;

		if (keycode == Keys.S)
			velY = -1;

		if (keycode == Keys.A)
			velX = -1;

		if (keycode == Keys.D)
			velX = 1;

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.ENTER)
			hit = false;

		if (keycode == Keys.W)
			velY = 0;

		if (keycode == Keys.S)
			velY = 0;

		if (keycode == Keys.A)
			velX = 0;

		if (keycode == Keys.D)
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

	public static float getVelX() {
		return velX;
	}

	public static float getVelY() {
		return velY;
	}

	public static boolean getHit() {
		return hit;
	}
}
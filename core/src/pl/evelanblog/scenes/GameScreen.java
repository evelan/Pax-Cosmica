package pl.evelanblog.scenes;

import java.util.ArrayList;

import pl.evelanblog.dynamicobjects.Player;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Background;
import pl.evelanblog.paxcosmica.Button;
import pl.evelanblog.paxcosmica.MyEffect;
import pl.evelanblog.paxcosmica.MySprite;
import pl.evelanblog.paxcosmica.MyText;
import pl.evelanblog.paxcosmica.PaxCosmica;
import pl.evelanblog.paxcosmica.Stats;
import pl.evelanblog.paxcosmica.control.MousePointer;
import pl.evelanblog.world.World;
import pl.evelanblog.world.World.GameState;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameScreen implements Screen, InputProcessor {

	private final PaxCosmica game;
	public static Stage gameStage;
	private Stage hudStage;
	private World world;

	private static Background background;
	private Vector2 defKnobPos = new Vector2(96, 96);

	private Button knob, buttonA, buttonB, pauseButton, powerButton, continueButton, exitButton, upPwr, downPwr, resumeButton;
	private ArrayList<Button> hp, shieldLevel;
	private MousePointer mousePointer;
	private MySprite dimScreen;
	private MyText scrap, score;
	private Player player;
	private static MyEffect explodeEff;
	private static MyEffect hitEff;
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

		gameStage = new Stage();
		hudStage = new Stage();
		background = new Background(game.getActivePlanet().getBackground());

		hp = new ArrayList<Button>();
		hp.add(new Button(Assets.hullBar, 0f, 1000));
		hp.add(new Button(Assets.hullBar, 15f, 1000));
		hp.add(new Button(Assets.hullBar, 30f, 1000));

		shieldLevel = new ArrayList<Button>();
		shieldLevel.add(new Button(Assets.shieldBar, 45f, 1000));
		shieldLevel.add(new Button(Assets.shieldBar, 60f, 1000));
		shieldLevel.add(new Button(Assets.shieldBar, 75f, 1000));

		knob = new Button(true, defKnobPos.x, defKnobPos.y, 256, 256, "buttons/knob.png");
		buttonA = new Button(true, 1600, 256, 256, 256, "buttons/buttonA.png");
		buttonB = new Button(true, 1472, 0, 256, 256, "buttons/buttonB.png");
		powerButton = new Button(false, 860, 20, Assets.powerButton.getWidth(), Assets.powerButton.getHeight(), "buttons/powerButton.png");
		pauseButton = new Button(true, 1750, 920, Assets.pauseButton.getWidth(), Assets.pauseButton.getHeight(), "buttons/pauseButton.png");
		resumeButton = new Button(true, 1750, 920, Assets.pauseButton.getWidth(), Assets.pauseButton.getHeight(), "buttons/unpauseButton.png");
		continueButton = new Button(false, 640, 540, 640, 192, "buttons/continueButton.png");
		exitButton = new Button(false, 640, 348, 640, 192, "buttons/exitButton.png");

		upPwr = new Button("buttons/up.png");
		downPwr = new Button("buttons/down.png");

		dimScreen = new MySprite(Assets.dim);

		mousePointer = game.getMouse();
		score = new MyText("Score: " + Stats.score, 100, 1000);
		scrap = new MyText("Scrap: " + Stats.scrap, 300, 1000);

		hitEff = new MyEffect(Assets.hitEffect);
		explodeEff = new MyEffect(Assets.explosionEffect);

		upPwr.setVisible(false);
		downPwr.setVisible(false);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// ustawienie backgroundu zawsze na środku
		background.getBackground().setY(gameStage.getCamera().position.y - gameStage.getHeight() / 2);

		// rysowanie stage'y
		gameStage.draw();
		hudStage.draw();

		// jeśli stan gry jest na ONGOING to update tła i reszty obiektów, jeśli nie to będziemy mieć efekt pauzy
		if (world.getState() == GameState.ongoing) {
			background.update(game.getActivePlanet().getRotationSpeed());
			world.update(delta);
		} else if (world.getState() == GameState.win) // wygrana i przechodzimy do mapy galaktyki
		{
			Assets.track2.stop();
			Assets.track1.play();
			game.setScreen(GameStateManager.galaxyMap);
		}
		else if (world.getState() == GameState.defeat) // przegrana i rysujemy przycisk do wypierdalania za bramę
		{
			exitButton.setVisible(true);
			player.setVisible(false);
		}

		// działa tak jak chciałem, renderuje wszystkie obiekty w jednej pętli z jedną liniją
		// for (DynamicObject obj : world.getObjects())
		// obj.draw(game.getSprBatch(), delta);
		// będę tęskinł za tymi linijami kodu :CCCCCCCCCCCCCCCC najpiękniejsze w całym kodzie

		if (world.getPlayer().isAlive())
			world.getPlayer().setVisible(true);
		hitEff.setVisible(true);
		explodeEff.setVisible(true);

		// te dwie pętle renderują paski osłony i HP
		for (int i = 3; i > world.getPlayer().getHealth(); i--)
			hp.get(i - 1).setVisible(false);

		// te dwie pętle renderują paski osłony i HP
		for (int i = 3; i > world.getPlayer().getShield(); i--)
			shieldLevel.get(i - 1).setVisible(false);

		score.setText("Score: " + Stats.score);
		scrap.setText("Scrap: " + Stats.scrap);

		if (world.getState() == GameState.powermanager || world.getState() == GameState.menu)
		{
			// dimScreen.draw(hudStage.getBatch(), 0.6f);
			pauseButton.setVisible(false);
			resumeButton.setVisible(true);
			if (world.getState() == GameState.menu)
			{
				continueButton.setVisible(true);
				exitButton.setVisible(true);
			} else if (world.getState() == GameState.powermanager)
			{
				// TODO: to trzeba zmienić, bez tych getbatch
				hudStage.getBatch().begin();
				drawPwrManager();
				powerButton.draw(hudStage.getBatch(), 0.9f);
				hudStage.getBatch().end();
			}
		} else
		{
			pauseButton.setVisible(true);
			resumeButton.setVisible(false);
			exitButton.setVisible(false);
			continueButton.setVisible(false);
			powerButton.setVisible(true);
			downPwr.setVisible(false);
			upPwr.setVisible(false);
		}
	}

	public static MyEffect getExplodeEff() {
		return explodeEff;
	}

	public void setExplodeEff(MyEffect explodeEff) {
		GameScreen.explodeEff = explodeEff;
	}

	public static MyEffect getHitEff() {
		return hitEff;
	}

	public void setHitEff(MyEffect hitEff) {
		GameScreen.hitEff = hitEff;
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
			hudStage.getBatch().draw(Assets.upgradeBar, x, 200 + i * 30);

		if (hover != -1) {
			downPwr.setPosition(hover, 100);
			upPwr.setPosition(hover, 500);
			downPwr.setVisible(true);
			upPwr.setVisible(true);
		}

		new MyText(name, x + 10, 190).draw(hudStage.getBatch(), 1);
		// font.draw(hudStage.getBatch(), name, );
	}

	@Override
	public void pause() {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		gameStage.getActors().clear();
		world = new World();
		player = world.getPlayer();

		// ADD SCENE ACTORS
		gameStage.addActor(background);
		gameStage.addActor(player);

		gameStage.addActor(hitEff);
		gameStage.addActor(explodeEff);
		gameStage.addActor(World.getObjects());

		// ADD HUD ACTORS
		if (Gdx.app.getType() == ApplicationType.Android) { // jeśli odpalimy na PC to nie pokażą się knob i przyciski
			hudStage.addActor(knob);
			hudStage.addActor(buttonA);
			hudStage.addActor(buttonB);
		}
		hudStage.addActor(powerButton);
		hudStage.addActor(pauseButton);
		hudStage.addActor(resumeButton);
		hudStage.addActor(continueButton);
		hudStage.addActor(exitButton);
		hudStage.addActor(upPwr);
		hudStage.addActor(downPwr);

		// hudStage.addActor(dimScreen);

		hudStage.addActor(score);
		hudStage.addActor(scrap);
		hudStage.addActor(hp.get(0));
		hudStage.addActor(hp.get(1));
		hudStage.addActor(hp.get(2));
		hudStage.addActor(shieldLevel.get(0));
		hudStage.addActor(shieldLevel.get(1));
		hudStage.addActor(shieldLevel.get(2));

		exitButton.setVisible(false);
		continueButton.setVisible(false);
		hitEff.setVisible(false);
		explodeEff.setVisible(false);

		// i = (int) world.getPlayer().getHealth();

		background.setBackground(game.getActivePlanet().getBackground());

		// pozycje X w powermanagerze, tych pasków/stanów/poziomów ulepszeń
		float temp_pos = ((Gdx.graphics.getWidth() + 100) / 5) - Assets.upgradeBar.getWidth(); // TODO usunąć to potem
		power = temp_pos;
		hull = temp_pos * 2;
		shield = temp_pos * 3;
		weapon = temp_pos * 4;
		engine = temp_pos * 5;
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
		if (!hit && mousePointer.overlaps(buttonA)) {
			hit = true;
			hitPointer = pointer;
		}

		// pause button
		if (mousePointer.overlaps(pauseButton))
		{
			Assets.playSound(Assets.clickSfx);
			world.setState(GameState.menu);
		}

		// resume button
		else if (mousePointer.overlaps(resumeButton))
		{
			Assets.playSound(Assets.clickSfx);
			world.setState(GameState.ongoing);
		}
		// powerManagerButton
		else if (mousePointer.overlaps(powerButton))
		{
			Assets.playSound(Assets.clickSfx);
			world.setState(world.getState() == GameState.powermanager ? GameState.ongoing : GameState.powermanager);

			// continueButton
		} else if (mousePointer.overlaps(continueButton))
		{
			pauseButton.setVisible(true);
			resumeButton.setVisible(false);
			continueButton.setVisible(false);
			exitButton.setVisible(false);
			Assets.playSound(Assets.clickSfx);
			world.setState(GameState.ongoing);
			// exitButton
		} else if (mousePointer.overlaps(exitButton))
		{
			Assets.playSound(Assets.clickSfx);
			world.setState(GameState.defeat);
			Assets.track2.stop();
			game.setScreen(GameStateManager.mainMenu);
		}

		// knob
		if (!knobPressed) {
			if (mousePointer.overlaps(knob)) {
				knob.setPosition(screenX - (knob.getImageWidth() / 2), screenY -
						(knob.getImageHeight() / 2));
				knobPressed = true;
				knobPointer = pointer;
				velX = (((screenX - (knob.getImageWidth() / 2)) - defKnobPos.x)) / 64;
				velY = ((screenY - (knob.getImageHeight() / 2)) - defKnobPos.y) / 64;
			}
			else {
				knobPressed = false;
			}
		}

		// powerManager
		if (world.getState() == GameState.powermanager) {

			if (mousePointer.overlaps(upPwr))
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

			} else if (mousePointer.overlaps(downPwr)) {

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

		if (Gdx.input.isKeyPressed(Keys.HOME)) {
			hit = true;
		}

		if (Gdx.input.isKeyPressed(Keys.BACK)) {
			hit = true;
		}

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

	public static Background getBackground() {
		return background;
	}
}
package pl.evelanblog.scenes;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import pl.evelanblog.dynamicobjects.Player;
import pl.evelanblog.paxcosmica.*;
import pl.evelanblog.paxcosmica.control.MousePointer;
import pl.evelanblog.world.World;
import pl.evelanblog.world.World.GameState;

public class GameScreen implements Screen, InputProcessor {

	private final PaxCosmica game;
	private static Stage gameStage; // scena na której znajdują się statki, asteroidy, pociski oraz pociski
	private static Stage hudStage; // przyciski, knob, tekst oraz informacja o stanie shieldPos i hp
	private Stage powerManager; //powerPos manager
	private World world; // świat

	private static Background background; // tło
	private static Button hpBar, shieldBar, hpBorder, shieldBorder;
	private Button knob, buttonA, buttonB, pauseButton, powerButton, continueButton, exitButton, upPwr, downPwr, resumeButton;
	private MousePointer mousePointer; // przycsik myszki
	private CustomText scrap, score; // tekst  o ilośc złomu oraz punktów
	private Player player; // gracz

	/* DO STEROWANIA */
	private static float velX; // wychylenie gałki w osi X
	private static float velY; // wychylenie gałki w osi Y
	private static boolean hit; // czy wciskamy przycisk A od strzelania
	private static boolean knobPressed; // czy wciskamy gałkę
	private int knobPointer = -1; // pointer gałki, potrzebne do multitoucha
	private int hitPointer = -1; // pointer przycisku A, potrzebne do multitoucha
	private Vector2 defKnobPos = new Vector2(96, 96); //domyślna pozycja z gałką
	private float hover = -1; // mówi o tym co wcisnęliśmy w powermanagerze aby pokazać przycisk upgrade

	private float powerLvl, hullLvl, shieldLvl, weaponLvl, engineLvl;
	private float powerPos, hullPos, shieldPos, weaponPos, enginePos; // wartości w pixelach gdzie mają być rysowane poszczególne paski w powermanagerze
	CustomParticleEffect explodeEffect, hitEffect;
	//private CustomSprite dimScreen; // od przyciemniania i rozjaśniania ekranu

	public GameScreen(final PaxCosmica game) {
		this.game = game;

		gameStage = new Stage(new StretchViewport(1920, 1080));
		hudStage = new Stage(new StretchViewport(1920, 1080));
		powerManager = new Stage(new StretchViewport(1920, 1080));
		background = new Background(game.getActivePlanet().getBackground());

		hpBar = new Button(15, 1025, 200, 40, Assets.hullBar);
		shieldBar = new Button(15, 982, 0, 40, Assets.shieldBar);
		hpBorder = new Button(15, 1025, 200, 40, Assets.barBorder);
		shieldBorder = new Button(15, 982, 0, 40, Assets.barBorder);

		knob = new Button(defKnobPos.x, defKnobPos.y, 256, 256, Assets.knob);
		buttonA = new Button(1600, 256, 256, 256, Assets.buttonA);
		buttonB = new Button(1472, 0, 256, 256, Assets.buttonB);

		pauseButton = new Button(1750, 920, Assets.pauseButton);
		resumeButton = new Button(1750, 920, Assets.unpauseButton);
		continueButton = new Button(640, 540, 640, 192, Assets.continueButton);
		exitButton = new Button(640, 348, 640, 192, Assets.exitButton);

		//do powerPos managera
		upPwr = new Button(0, 0, 200, 60, Assets.up);
		downPwr = new Button(0, 0, 200, 60, Assets.down);
		powerButton = new Button(810, 20, Assets.powerButton);

		//dimScreen = new CustomSprite(Assets.dim);

		mousePointer = game.getMouse();
		score = new CustomText("Score: " + Stats.score, 965, 1060);
		scrap = new CustomText("Scrap: " + Stats.scrap, 1200, 1060);

		hitEffect = new CustomParticleEffect(Assets.hitEffect);
		explodeEffect = new CustomParticleEffect(Assets.explosionEffect);
	}

	public static Button getHpBorder() {
		return hpBorder;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// ustawienie backgroundu zawsze na środku
		background.getBackground().setY(gameStage.getCamera().position.y - gameStage.getHeight() / 2);
		gameStage.draw(); //rysowanie stage z graczem, przeciwnikami i strzałami
		hudStage.draw(); // HUD, czyli wszystkie przycsiki i inne duperele

		// jeśli stan gry jest na ONGOING to update tła i reszty obiektów, jeśli nie to będziemy mieć efekt pauzy
		if (world.getState() == GameState.ongoing) {
			background.update(game.getActivePlanet().getRotationSpeed());
			world.update(delta);
		} else if (world.getState() == GameState.win) // wygrana i przechodzimy do mapy galaktyki
		{
			Assets.track2.stop();
			if (PaxPreferences.getMusicEnabled())
				Assets.track1.play();
			//TODO powinno pokazać jakiś ekran że wygraliśmy i opcję do do wyjścia z gry, lub przejscia do galaktyki
			Stats.save();
			game.setScreen(GameStateManager.galaxyMap);
		} else if (world.getState() == GameState.defeat) // przegrana i rysujemy przycisk do wypierdalania za bramę
		{
			player.setVisible(false); // przestajemy wyświetlać gracza bo już zginął
			exitButton.setVisible(true);
			player.setVisible(false);
		} else if(world.getState() == GameState.powermanager)
		{

		}

		//rysownie tekstu na ekranie
		score.setText("Score: " + Stats.score);
		scrap.setText("Scrap: " + Stats.scrap);

		//jeśli gracz wejdzie w powermanagera
		if (world.getState() == GameState.powermanager)
			setPowerPanager();
		else if (world.getState() == GameState.paused) // wciśniety przycisk pauzy
			setPause();
		else if (world.getState() == GameState.ongoing) // normalny stan, czyli gramy w grę po prostu
			setResume(); //TODO tutaj można by coś pomyśleć aby się ten kod ciągle nie wykonywał bo to bez sensu, tylko wykrywać kiedy nastąpi zmiana stanu gry
	}

	//pauzuje grę
	private void setPause() {
		pauseButton.setVisible(false);
		resumeButton.setVisible(true);
		continueButton.setVisible(true);
		exitButton.setVisible(true);
	}

	//przywraca grę
	private void setResume() {
		pauseButton.setVisible(true);
		resumeButton.setVisible(false);
		exitButton.setVisible(false);
		continueButton.setVisible(false);
		powerButton.setVisible(true);
		downPwr.setVisible(false);
		upPwr.setVisible(false);
	}

	//pokazuje powere managera
	private void setPowerPanager() {
		//TODO: SpriteBatch.begin must be called before draw.
		//dimScreen.draw(hudStage.getBatch(), 0.6f); // przyciemnia to co się dzieje w tle
		pauseButton.setVisible(false);
		resumeButton.setVisible(true);

		hudStage.getBatch().begin();
		drawPwrManager();
		powerButton.draw(hudStage.getBatch(), 0.9f);
		hudStage.getBatch().end();
	}

	public static Button getHpBar() {
		return hpBar;
	}

	public static Button getShieldBar() {
		return shieldBar;
	}

	private void drawPwrManager() {
		createBar(powerPos, Player.powerGenerator, "Power " + (int) Player.powerGenerator + "  L" + (int) Player.powerLvl);
		createBar(hullPos, Player.hullPwr, "Hull " + (int) Player.hullPwr + "  L" + (int) Player.hullLvl);
		createBar(shieldPos, Player.shieldPwr, "Shield " + (int) Player.shieldPwr + "  L" + (int) Player.shieldLvl);
		createBar(weaponPos, Player.weaponPwr, "Weapon " + (int) Player.weaponPwr + "  L" + (int) Player.weaponLvl);
		createBar(enginePos, Player.enginePwr, "Engine " + (int) Player.enginePwr + "  L" + (int) Player.engineLvl);
	}

	private void createBar(float x, float level, String name) {
		for (int i = 0; i < level; i++)
			hudStage.getBatch().draw(Assets.upgradeBar, x, 200 + i * 30);

		if (hover != -1) {
			downPwr.setPosition(hover, 100);
			upPwr.setPosition(hover, 500);
			downPwr.setVisible(true);
			upPwr.setVisible(true);
		}

		//new MyText(name, x + 10, 190).draw(hudStage.getBatch(), 1); ta linijka była jebutna i żre proca, bo tworzy ciagle nowe obiekty, bo bylem leniwy, przepraszam
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

		Stats.levelKills = 0;

		// ADD SCENE ACTORS
		gameStage.addActor(background);
		gameStage.addActor(player);

		gameStage.addActor(hitEffect);
		gameStage.addActor(explodeEffect);
		gameStage.addActor(World.getObjects());

		// ADD HUD ACTORS
		if (Gdx.app.getType() == Application.ApplicationType.Android) { // jeśli odpalimy na PC to nie pokażą się knob i przyciski
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

		//hudStage.addActor(dimScreen);

		hudStage.addActor(score);
		hudStage.addActor(scrap);
		hudStage.addActor(hpBar);
		hudStage.addActor(shieldBar);
		hudStage.addActor(hpBorder);
		hudStage.addActor(shieldBorder);

		background.setBackground(game.getActivePlanet().getBackground());

		upPwr.setVisible(false);
		downPwr.setVisible(false);

		// pozycje X w powermanagerze, tych pasków/stanów/poziomów ulepszeń
		powerPos = 100;
		hullPos = 410;
		shieldPos = 620;
		weaponPos = 830;
		enginePos = 1040;
		//dimScreen.setPosition(0, 0);

		velX = 0;
		velY = 0;
		hit = false;
		knobPressed = false;
		knobPointer = -1;
		hitPointer = -1;
		hover = -1;
		if (PaxPreferences.getMusicEnabled())
			Assets.track2.play();
		world.setState(GameState.ongoing); // już wszystko zostało ustawione wiec możemy startować z grą
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
		hudStage.dispose();
		powerManager.dispose();
		gameStage.dispose();

	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		screenY = Gdx.graphics.getHeight() - screenY;
		mousePointer.setPosition(screenX * gameStage.getViewport().getWorldWidth() / Gdx.graphics.getWidth(),
				screenY * gameStage.getViewport().getWorldHeight() / Gdx.graphics.getHeight());

		if (PaxPreferences.getSoundEnabled() && !knobPressed)
			Assets.playSound(Assets.clickSfx);

		// A BUTTON
		if (!hit && mousePointer.overlaps(buttonA)) {
			hit = true;
			hitPointer = pointer;
		}

		// pause button
		if (mousePointer.overlaps(pauseButton)) {
			world.setState(GameState.paused);
		}

		// resume button
		else if (mousePointer.overlaps(resumeButton)) {

			world.setState(GameState.ongoing);
		}
		// powerManagerButton
		else if (mousePointer.overlaps(powerButton)) {

			world.setState(world.getState() == GameState.powermanager ? GameState.ongoing : GameState.powermanager);

			// continueButton
		} else if (mousePointer.overlaps(continueButton)) {
			pauseButton.setVisible(true);
			resumeButton.setVisible(false);
			continueButton.setVisible(false);
			exitButton.setVisible(false);

			world.setState(GameState.ongoing);
			// exitButton
		} else if (mousePointer.overlaps(exitButton)) {

			world.setState(GameState.defeat);
			Assets.track2.stop();
			Stats.save();
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
			} else {
				knobPressed = false;
			}
		}

		// powerManager
		if (world.getState() == GameState.powermanager) {

			if (mousePointer.overlaps(upPwr)) {
				if (Player.powerGenerator > 0) {
					if (hover == hullPos && Player.hullLvl > Player.hullPwr) {
						Player.hullPwr++;
						Player.powerGenerator--;
					} else if (hover == weaponPos && Player.weaponLvl > Player.weaponPwr) {
						Player.weaponPwr++;
						Player.powerGenerator--;
					} else if (shieldPos == hover && Player.shieldLvl > Player.shieldPwr) {
						Player.shieldPwr++;
						Player.powerGenerator--;
					} else if (enginePos == hover && Player.engineLvl > Player.enginePwr) {
						Player.enginePwr++;
						Player.powerGenerator--;
					}
				}

			} else if (mousePointer.overlaps(downPwr)) {

				if (hover == hullPos && Player.hullPwr > 0) {
					Player.hullPwr--;
					Player.powerGenerator++;
				} else if (hover == weaponPos && Player.weaponPwr > 0) {
					Player.weaponPwr--;
					Player.powerGenerator++;
				} else if (shieldPos == hover && Player.shieldPwr > 0) {
					Player.shieldPwr--;
					Player.powerGenerator++;
				} else if (enginePos == hover && Player.enginePwr > 0) {
					Player.enginePwr--;
					Player.powerGenerator++;
				}
			}

			if (screenX > hullPos && screenX < hullPos + 200)
				hover = hullPos;
			else if (screenX > weaponPos && screenX < weaponPos + 200)
				hover = weaponPos;
			else if (screenX > shieldPos && screenX < shieldPos + 200)
				hover = shieldPos;
			else if (screenX > enginePos && screenX < enginePos + 200)
				hover = enginePos;
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

	public static Button getShieldBorder() {
		return shieldBorder;
	}

	public static Stage getGameStage() {
		return gameStage;
	}

	public static Stage getHudStage() {
		return hudStage;
	}
}
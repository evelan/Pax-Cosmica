package pl.evelanblog.scenes;

import java.util.ArrayList;

import pl.evelanblog.dynamicobjects.Player;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Background;
import pl.evelanblog.paxcosmica.GameStateManager;
import pl.evelanblog.paxcosmica.MyButton;
import pl.evelanblog.paxcosmica.MyEffect;
import pl.evelanblog.paxcosmica.MyFont;
import pl.evelanblog.paxcosmica.MySprite;
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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen, InputProcessor {

	private final PaxCosmica game;
	private World world;
	private BitmapFont font;

	private static Background background;
	private Vector2 defKnobPos = new Vector2(96, 96);
	private MyButton knob, buttonA, buttonB, pauseButton, powerButton, continueButton, exitButton, upPwr, downPwr;
	private Rectangle mousePointer;
	private MySprite dimScreen;
	private static MyEffect explodeEff;
	private static MyEffect hitEff;
	private ArrayList<MySprite> hp;
	private MyFont scrap, score;
	private static float velX = 0;
	private static float velY = 0;
	private static boolean hit = false;
	private boolean knobPressed = false;
	private Player player;
	int i;

	private int knobPointer = -1;
	private int hitPointer = -1;
	private float hover = -1;
	private float power, hull, shield, weapon, engine;

	public GameScreen(final PaxCosmica game) {
		this.game = game;
		background = new Background(game.getActivePlanet().getBackground());
		hp=new ArrayList<MySprite>();
		knob = new MyButton(defKnobPos.x, defKnobPos.y, 256, "knob.png");
		knob.getButton().setAlpha(0.3f);
		buttonA = new MyButton(1600, 256, 256, "buttonA.png");
		buttonB = new MyButton(1472, 0, 256, "buttonB.png");
		powerButton = new MyButton(860, 20, Assets.powerButton.getWidth(), Assets.powerButton.getHeight(), "buttons/powerButton.png");
		pauseButton = new MyButton(1750, 920, Assets.pauseButton.getWidth(), "pauseButton.png");
		continueButton = new MyButton(640, 540, 640, 192, "buttons/continueButton.png");
		exitButton = new MyButton(640, 348, 640, 192, "buttons/exitButton.png");

		upPwr = new MyButton("up.png");
		downPwr = new MyButton("down.png");

		dimScreen = new MySprite(Assets.dim);

		mousePointer = new Rectangle(0, 0, 1, 1);
		font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
		
		game.getGameHud().addActor(knob);
		game.getGameHud().addActor(buttonA);
		game.getGameHud().addActor(buttonB);
		game.getGameHud().addActor(powerButton);
		game.getGameHud().addActor(pauseButton);
		game.getGameHud().addActor(continueButton);
		game.getGameHud().addActor(exitButton);
		game.getGameHud().addActor(upPwr);
		game.getGameHud().addActor(downPwr);
		//game.getGameHud().addActor(dimScreen);
		
		hp.add(new MySprite(Assets.hullBar,0,800));
		hp.add(new MySprite(Assets.hullBar,15,800));
		hp.add(new MySprite(Assets.hullBar,30,800));
		
		game.getGameHud().addActor(hp.get(0));
		game.getGameHud().addActor(hp.get(1));
		game.getGameHud().addActor(hp.get(2));
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		background.getBackground().setY(PaxCosmica.getGameScene().getCamera().position.y-PaxCosmica.getGameScene().getHeight()/2);
		
		PaxCosmica.getGameScene().draw();
		game.getGameHud().draw();
		
		
		
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

		if (world.getPlayer().isAlive())
			world.getPlayer().setVisible(true);
		hitEff.setVisible(true);
		explodeEff.setVisible(true);
		// te dwie pętle renderują paski osłony i HP
		for (int i = 2; i > world.getPlayer().getHealth(); i--)
			hp.get(i).setVisible(false);

//		for (int i = 0; i < world.getPlayer().getShield(); i++)
//			game.getGameHud().getBatch().draw(Assets.shieldBar, 15 * i, Gdx.graphics.getHeight() - (2 * Assets.shieldBar.getHeight()));

		
		score = new MyFont(font, "Score: "+Stats.score, 100, 1000);
		scrap = new MyFont(font, "Scrap: "+Stats.scrap, 300, 1000);
		game.getGameHud().addActor(score);
		game.getGameHud().addActor(scrap);
		
		if (world.getState() == GameState.powermanager || world.getState() == GameState.menu)
		{
			//dimScreen.draw(game.getGameScene().getBatch(), 0.6f);
			pauseButton.getButton().setTexture(Assets.unpauseButton);
			pauseButton.setVisible(true);
			if (world.getState() == GameState.menu)
			{
				continueButton.setVisible(true);
				exitButton.setVisible(true);
			} else if (world.getState() == GameState.powermanager)
			{
				drawPwrManager();
				powerButton.draw(game.getGameHud().getBatch(), 0.9f);
			}
		} else
		{
			pauseButton.getButton().setTexture(Assets.pauseButton);
			pauseButton.setVisible(true);
			pauseButton.getButton().setAlpha(0.3f);
			powerButton.setVisible(true);
			powerButton.getButton().setAlpha(0.3f);
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
			game.getGameHud().getBatch().draw(Assets.upgradeBar, x, 200 + i * 30);

		if (hover != -1) {
			downPwr.setPosition(hover, 100);
			upPwr.setPosition(hover, 500);
			downPwr.setVisible(true);
			upPwr.setVisible(true);
		}

		//font.draw(game.getGameHud().getBatch(), name, x + 10, 190);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		i=0;
		world = new World();
		player=world.getPlayer();
		exitButton.setVisible(false);
		continueButton.setVisible(false);
		background = new Background(game.getActivePlanet().getBackground());
		
		hitEff=new MyEffect(Assets.hitEffect);
		explodeEff = new MyEffect (Assets.explosionEffect);
		hitEff.setVisible(false);
		explodeEff.setVisible(false);
		
		PaxCosmica.getGameScene().addActor(background);
		PaxCosmica.getGameScene().addActor(player);
		
		PaxCosmica.getGameScene().addActor(hitEff);
		PaxCosmica.getGameScene().addActor(explodeEff);
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
		if (!hit && mousePointer.overlaps(buttonA.getButton().getBoundingRectangle())) {
			hit = true;
			hitPointer = pointer;
		}

		// pause button
		if (mousePointer.overlaps(pauseButton.getButton().getBoundingRectangle()))
		{
			Assets.playSound(Assets.clickSfx);
			world.setState(world.getState() == GameState.menu ? GameState.ongoing : GameState.menu);

			// powerManagerButton
		} else if (mousePointer.overlaps(powerButton.getButton().getBoundingRectangle()))
		{
			Assets.playSound(Assets.clickSfx);
			world.setState(world.getState() == GameState.powermanager ? GameState.ongoing : GameState.powermanager);

			// continueButton
		} else if (mousePointer.overlaps(continueButton.getButton().getBoundingRectangle()))
		{
			pauseButton.getButton().setTexture(Assets.pauseButton);
			continueButton.setVisible(false);
			exitButton.setVisible(false);
			Assets.playSound(Assets.clickSfx);
			world.setState(GameState.ongoing);
			// exitButton
		} else if (mousePointer.overlaps(exitButton.getButton().getBoundingRectangle()))
		{
			Assets.playSound(Assets.clickSfx);
			world.setState(GameState.defeat);	
			Assets.track2.stop();
			game.setScreen(GameStateManager.mainMenu);
		}

		// knob
		if (!knobPressed) {
			if (mousePointer.overlaps(knob.getButton().getBoundingRectangle())) {
				knob.getButton().setPosition(screenX - (knob.getButton().getWidth() / 2), screenY -
						(knob.getButton().getHeight() / 2));
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

			if (mousePointer.overlaps(upPwr.getButton().getBoundingRectangle()))
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

			} else if (mousePointer.overlaps(downPwr.getButton().getBoundingRectangle())) {

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
			knob.getButton().setPosition(defKnobPos.x, defKnobPos.y);
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
			if (screenX > defKnobPos.x + knob.getButton().getWidth())
				screenX = (int) (defKnobPos.x + knob.getButton().getWidth());
			else if (screenX < defKnobPos.x)
				screenX = (int) defKnobPos.x;

			if (screenY > defKnobPos.y + knob.getButton().getHeight())
				screenY = (int) (defKnobPos.y + knob.getButton().getHeight());
			else if (screenY < defKnobPos.y)
				screenY = (int) defKnobPos.y;

			knob.getButton().setPosition(screenX - (knob.getButton().getWidth() / 2), screenY - (knob.getButton().getHeight() / 2));
			velX = (((screenX - (knob.getButton().getWidth() / 2)) - defKnobPos.x)) / 64;
			velY = ((screenY - (knob.getButton().getHeight() / 2)) - defKnobPos.y) / 64;
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

	public static Background getBackground() {
		return background;	
	}
}
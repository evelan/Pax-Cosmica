package pl.evelanblog.scenes;

import pl.evelanblog.dynamicobjects.Player;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Button;
import pl.evelanblog.paxcosmica.GameStateManager;
import pl.evelanblog.paxcosmica.PaxCosmica;
import pl.evelanblog.paxcosmica.Stats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;

public class UpgradeScreen implements Screen, InputProcessor {
	
	private final PaxCosmica game;
	private BitmapFont font;
	private Button apply, discard, upgrade;
	private Rectangle mousePointer;
	
	private float power, hull, shield, weapon, engine;
	private float powerLvl, hullLvl, shieldLvl, weaponLvl, engineLvl;
	private float hover = -1;
	private int cost = 5;
	private int scrap;
	float dimValue;

	public UpgradeScreen(final PaxCosmica game)
	{
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.getSprBatch().begin();
		game.getSprBatch().draw(new Texture(Gdx.files.internal("upgrade_background.png")), 0, 0);
		
		createBar(hull, hullLvl, "Hull: " + hullLvl);
		createBar(power, powerLvl, "Power: " + powerLvl);
		createBar(shield, shieldLvl, "Shield: " + shieldLvl);
		createBar(weapon, weaponLvl, "Weapon: " + weaponLvl);
		createBar(engine, engineLvl, "Engine: " + engineLvl);

		apply.draw(game.getSprBatch());
		discard.draw(game.getSprBatch());
		font.draw(game.getSprBatch(), "Scrap: " + scrap, 10, 710);
		game.getSprBatch().end();
		
	}

	public void createBar(float x, float level, String name)
	{
		for (int i = 0; i < level; i++) 
			game.getSprBatch().draw(Assets.upgradeBar, x, 200 + i * 50);

		font.draw(game.getSprBatch(), "Cost: " + cost, x, level * 50 + 250);

		if (hover != -1) {
			upgrade.setPosition(hover, 10);
			upgrade.draw(game.getSprBatch());
		}

		font.draw(game.getSprBatch(), name, x+10, 190);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {

		apply = new Button(1470, 196, 400, 96, "buttons/applyButton.png");
		discard = new Button(1470, 100, 400, 96, "buttons/discardButton.png");
		upgrade = new Button(1470, 0, 400, 100, "upgrade_button.png");

		power = 100;
		hull = 300;
		shield = 500;
		weapon = 700;
		engine = 900;

		hullLvl = Player.hullLvl;
		powerLvl = Player.powerLvl;
		shieldLvl = Player.shieldLvl;
		weaponLvl = Player.weaponLvl;
		engineLvl = Player.engineLvl;
		scrap = Stats.scrap;

		mousePointer = new Rectangle();
		mousePointer.setSize(1);

		font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
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
		screenY = Gdx.graphics.getHeight() - screenY;
		mousePointer.setPosition(screenX, screenY);
		Assets.playSound(Assets.clickSfx);

		if (apply.getBoundingRectangle().overlaps(mousePointer)) {
			Player.hullLvl = hullLvl;
			Player.engineLvl = engineLvl;
			Player.powerLvl = powerLvl;
			Player.weaponLvl = weaponLvl;
			Player.shieldLvl = shieldLvl;
			Stats.scrap = scrap;

			game.setScreen(GameStateManager.galaxyMap);
			dispose();
		}
		else if (discard.getBoundingRectangle().overlaps(mousePointer)) {
			game.setScreen(GameStateManager.galaxyMap);
			dispose();
		}
		else if (upgrade.getBoundingRectangle().overlaps(mousePointer))
		{
			if (scrap >= cost) {
				scrap -= cost;
				if (hover == power)
					powerLvl++;
				else if (hover == hull)
					hullLvl++;
				else if (hover == weapon)
					weaponLvl++;
				else if (shield == hover)
					shieldLvl++;
				else if (engine == hover)
					engineLvl++;
			}
		}

		if (screenX > power && screenX < power + 200)
			hover = power;
		else if (screenX > hull && screenX < hull + 200)
			hover = hull;
		else if (screenX > weapon && screenX < weapon + 200)
			hover = weapon;
		else if (screenX > shield && screenX < shield + 200)
			hover = shield;
		else if (screenX > engine && screenX < engine + 200)
			hover = engine;
		else
			hover = -1;

		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
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

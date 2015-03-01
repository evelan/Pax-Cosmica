package pl.evelanblog.dynamicobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.PaxPrefs;
import pl.evelanblog.scenes.GameScreen;
import pl.evelanblog.utilities.HUDController;

public class Player extends DynamicObject {
	private Sprite shieldSprite;
	float bulletSpeed = 1000f, shootFrequency = 0.2f, shieldReload = 0, maxShieldReload = 10f, x, y;

	public int powerLvl, shieldLvl, hullLvl, weaponLvl, engineLvl;
	public int powerPwr, hullPwr, shieldPwr, weaponPwr, enginePwr;

	public Player() {
		// pos x, pos y, speed , hp, shield, impactDamage, texture
		super(100, 300, 180f, 3 * PaxPrefs.getInt(PaxPrefs.HULL_PWR, 1), 0f, 100f, "player/spaceship.png");
		shieldSprite = new Sprite(Assets.bubbleShield);
		shieldSprite.setOriginCenter();
		getStats();
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		powerPwr = powerLvl - shieldPwr - hullPwr - weaponPwr - enginePwr;

		//jeśli w PwrMgr silnik nie ma żadnego paska to wyłączamy silniki
		if (enginePwr < 1)
			speed = 0;
		else
			speed = 180f + (enginePwr * 10);

		//jeśli osłona ma mniejszą wartość niż poziom naładowania w PwrMgr to ładujemy osłony
		if (shield < shieldPwr) {
			shieldReload += deltaTime;
			if (shieldReload > maxShieldReload) {
				shieldReload = 0;
				shield++;
			}
		}

		// standardowo pocisk ma predkosc 600f, z kazdym kolejnym poziomemw eaponPwr bedzie on przyspieszac razy 80
		bulletSpeed = 600f + (weaponPwr * 80);

		checkBounds(deltaTime);
		Assets.playerEngineEffect.setPosition(getX() + 10, getY() + (getHeight() / 2));
		shieldSprite.setPosition(getX() - 30, getY() - ((shieldSprite.getHeight() - getHeight()) / 2));
	}

	/**
	 * Rysujemy gracza, efekt silnika oraz oslone jesli {@link DynamicObject#shield} jest wieksze od 0
	 *
	 * @param batch SpriteBatch, wiadomo
	 * @param alpha poziom przeźroczystości
	 */
	@Override
	public void draw(Batch batch, float alpha) {
		if (enginePwr > 0)
			Assets.playerEngineEffect.draw(batch, Gdx.graphics.getDeltaTime());
		batch.draw(getSprite(), getX(), getY());
		if (shield > 0)
			shieldSprite.draw(batch);
	}

	/**
	 * Sprawdzamy czy gracz nie próbuje wylecieć poza ekran
	 */
	private void checkBounds(float deltaTime) {
		y = GameScreen.getCameraY() + (HUDController.getVelY() * deltaTime * speed);
		if (y > 0 && y < Assets.worldHeight - getHeight()) {
			GameScreen.setCameraY(y);
			setY(GameScreen.getCameraY());
		}

		x = getX() + (HUDController.getVelX() * deltaTime * speed);
		if (x > 0 && x < Assets.worldWidth - getWidth())
			setX(x);
	}

	/**
	 * Czyści do wartości domyślnych, stosować przy załadowaniu kolejnego poziomu
	 */
	public void clear() {
		live = true;
	}

	/**
	 * Odtwarza dzwiek strzalu z Assets
	 *
	 * @return zwraca obiekt Pocisku
	 */
	public Bullet shoot() {
		Assets.playSound(Assets.shootSfx);
		return new Bullet(getX() + getWidth() - 10, getY() + (getHeight() / 2) - 4, bulletSpeed, true, 1f);
	}

	public float getShootFrequency() {
		return shootFrequency;
	}

	/**
	 * Sprawdza czy gracz ma mozlowosc strzelania
	 *
	 * @return true jesli moze strzelic, w innym wypadku false
	 */
	public boolean ableToShoot() {
		return (weaponPwr > 0 && live);
	}

	@Override
	public void kill() {
		live = false;
		Assets.playSound(Assets.explosionSfx);
		Assets.explosionEffect.setPosition(getX() + (getWidth() / 2), getY() + (getHeight() / 2));
		Assets.explosionEffect.start();
	}

	public void setStats() {
		Gdx.app.log("PREFS", "engine lvl: " + engineLvl);

		PaxPrefs.putInt(PaxPrefs.POWER_LVL, powerLvl);
		PaxPrefs.putInt(PaxPrefs.POWER_PWR, powerPwr);

		PaxPrefs.putInt(PaxPrefs.ENGINE_LVL, engineLvl);
		PaxPrefs.putInt(PaxPrefs.ENGINE_PWR, enginePwr);

		PaxPrefs.putInt(PaxPrefs.HULL_LVL, hullLvl);
		PaxPrefs.putInt(PaxPrefs.HULL_PWR, hullPwr);

		PaxPrefs.putInt(PaxPrefs.SHIELD_LVL, shieldLvl);
		PaxPrefs.putInt(PaxPrefs.SHIELD_PWR, shieldPwr);

		PaxPrefs.putInt(PaxPrefs.WEAPON_LVL, weaponLvl);
		PaxPrefs.putInt(PaxPrefs.WEAPON_PWR, weaponPwr);
	}

	private void getStats() {

		powerLvl = PaxPrefs.getInt(PaxPrefs.POWER_LVL, 1);
		powerPwr = PaxPrefs.getInt(PaxPrefs.POWER_PWR, 1);
		engineLvl = PaxPrefs.getInt(PaxPrefs.ENGINE_LVL, 1);
		enginePwr = PaxPrefs.getInt(PaxPrefs.ENGINE_LVL, 1);
		hullLvl = PaxPrefs.getInt(PaxPrefs.HULL_LVL, 1);
		hullPwr = PaxPrefs.getInt(PaxPrefs.HULL_PWR, 1);
		shieldLvl = PaxPrefs.getInt(PaxPrefs.SHIELD_LVL, 1);
		shieldPwr = PaxPrefs.getInt(PaxPrefs.SHIELD_PWR, 1);
		weaponLvl = PaxPrefs.getInt(PaxPrefs.WEAPON_LVL, 1);
		weaponPwr = PaxPrefs.getInt(PaxPrefs.WEAPON_PWR, 1);
		maxShield = shieldPwr;
	}

	public float getReload() {
		return shieldReload;
	}

	public float getMaxReload() {
		return maxShieldReload;
	}
}
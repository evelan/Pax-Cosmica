package pl.evelanblog.dynamicobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.PaxPrefs;
import pl.evelanblog.scenes.GameScreen;
import pl.evelanblog.scenes.HUD;

public class Player extends DynamicObject {
	private float bulletSpeed = 1000f;
	private float shieldReloadLvl = 0;
	private Sprite shieldSprite;
	float shootFrequency = 0.2f;
	float temp_x, temp_y;

	public int powerLvl, shieldLvl, hullLvl, weaponLvl, engineLvl;
	public int powerPwr, hullPwr, shieldPwr, weaponPwr, enginePwr;

	public Player() {
		// pos x, pos y, speed , hp, shield, impactDamage, texture
		super(100, 300, 180f, 3 * PaxPrefs.getInt(PaxPrefs.HULL_PWR, 1), 0f, 100f, "player/spaceship.png");
		shieldSprite = new Sprite(Assets.bubbleShield);
		shieldSprite.setOriginCenter();
		getStats();
	}

	public void update(float deltaTime) {
		powerPwr = powerLvl - shieldPwr - hullPwr - weaponPwr - enginePwr;

		if (enginePwr < 1)
			speed = 0;
		else
			speed = 180f + (enginePwr * 10);

		if (shield < shieldPwr) {
			shieldReloadLvl += deltaTime;
			if (shieldReloadLvl > 10f) {
				shieldReloadLvl = 0;
				shield++;
				//				GameScreen.getShieldBar().setSize(GameScreen.getShieldBar().getWidth() + (200 / shieldLvl), GameScreen.getShieldBar().getHeight());
				//				GameScreen.getShieldBorder().setSize(200, GameScreen.getShieldBorder().getHeight());
			}
		}

		bulletSpeed = 600f + (weaponPwr * 80); // standardowo pocisk ma predkosc 600f, z kazdym kolejnym poziomem
		// weaponPwr bedzie on przyspieszac razy 80

		temp_y = GameScreen.getGameStage().getCamera().position.y + (HUD.getVelY() * deltaTime * speed);
		if (temp_y > 0 && temp_y < Assets.worldHeight - getHeight()) {
			GameScreen.getGameStage().getCamera().position.y = temp_y;
			setY(GameScreen.getGameStage().getCamera().position.y);
		}

		temp_x = getX() + (HUD.getVelX() * deltaTime * speed);
		if (temp_x > 0 && temp_x < Assets.worldWidth - getWidth())
			setX(temp_x);

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
	 * Czyści do wartości domyślnych, stosować przy załadowaniu kolejnego poziomu
	 */
	public void clear() {

	}

	/**
	 * resetuje wszystkie ustawienia i ulepszenia gracza
	 */
	public void reset() {

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
	public void hurt(float damage) {

		if (shield > 0) {
			shield -= damage;

			//			if (shield > 0)
			//				GameScreen.getShieldBar().setSize(200 * shield / shieldPwr, GameScreen.getShieldBar().getHeight());
			//			else {
			//				shield = 0;
			//				GameScreen.getShieldBorder().setSize(0, GameScreen.getShieldBar().getHeight());
			//				GameScreen.getShieldBar().setSize(0, GameScreen.getShieldBar().getHeight());
			//			}
		} else {
			hp -= damage;
			//			GameScreen.getHpBar().setSize(200 * hp / (hullPwr * 3), GameScreen.getHpBar().getHeight());
		}
		if (hp <= 0)
			kill();
	}

	@Override
	public void kill() {
		live = false;
		Assets.playSound(Assets.explosionSfx);
		Assets.explosionEffect.setPosition(getX() + (getWidth() / 2), getY() + (getHeight() / 2));
		Assets.explosionEffect.start();
		//		GameScreen.getShieldBorder().setSize(0, GameScreen.getShieldBar().getHeight());
		//		GameScreen.getShieldBar().setSize(0, GameScreen.getShieldBar().getHeight());
		//		GameScreen.getHpBar().setSize(0, GameScreen.getShieldBar().getHeight());
		//		GameScreen.getHpBorder().setSize(0, GameScreen.getShieldBar().getHeight());
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

	}

	public void setHp(float hp) {
		this.hp = hp;
	}
}
package pl.evelanblog.dynamicobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.PaxPreferences;
import pl.evelanblog.scenes.GameScreen;

public class Player extends DynamicObject {
	/* OGŁASZA SIĘ CO NASTĘPUJE */
	/*Tu nie może być żadnych statycznych zmiennych, bo potem bedzie drugi obiekt gracza i te zmienne będą współdzielone co byłoby bez sensu :) */

	private float bulletSpeed = 1000f;
	private float shieldReloadLvl = 0;
	private Sprite shieldSprite;
	float shootFrequency = 0.2f;
	float temp_x, temp_y;

	//public static float powerGenerator = PaxPreferences.getPowerGenerator();
	public float powerLvl = PaxPreferences.getPowerLvl();
	public float shieldLvl = PaxPreferences.getShieldLvl();
	public float hullLvl = PaxPreferences.getHullLvl();
	public float weaponLvl = PaxPreferences.getWeaponLvl();
	public float engineLvl = PaxPreferences.getEngineLvl();
	public int hullPwr = PaxPreferences.getHullPwr();
	public int shieldPwr = PaxPreferences.getShieldPwr();
	public int weaponPwr = PaxPreferences.getWeaponPwr();
	public int enginePwr = PaxPreferences.getEnginePwr();

	float temp = 0;

	public Player() {
		// pos x, pos y, speed , hp, shield, impactDamage, texture
		super(100, 300, 180f, 3 * PaxPreferences.getHullPwr(), 0f, 100f, "player/spaceship.png");
		shieldSprite = new Sprite(Assets.bubbleShield);
		shieldSprite.setOriginCenter();
	}

	public void update(float deltaTime) {
		//powerGenerator = powerLvl - shieldPwr - hullPwr - weaponPwr - enginePwr;

		if (enginePwr < 1)
			speed = 0;
		else
			speed = 180f + (enginePwr * 10);

		if (shield < shieldPwr) {
			shieldReloadLvl += deltaTime;
			if (shieldReloadLvl > 10f) {
				shieldReloadLvl = 0;
				shield++;
				GameScreen.getShieldBar().setSize(GameScreen.getShieldBar().getWidth() + (200 / shieldLvl), GameScreen.getShieldBar().getHeight());
				GameScreen.getShieldBorder().setSize(200, GameScreen.getShieldBorder().getHeight());
			}
		}

		bulletSpeed = 600f + (weaponPwr * 80); // standardowo pocisk ma predkosc 600f, z kazdym kolejnym poziomem
		// weaponPwr bedzie on przyspieszac razy 80

		temp_y = GameScreen.getGameStage().getCamera().position.y + (GameScreen.getVelY() * deltaTime * speed);
		if (temp_y > 0 && temp_y < Assets.worldHeight - getHeight()) {
			GameScreen.getGameStage().getCamera().position.y = temp_y;
			setY(GameScreen.getGameStage().getCamera().position.y);
		}

		temp_x = getX() + (GameScreen.getVelX() * deltaTime * speed);
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
	 * Odtwarza dzwiek strzalu z Assets
	 *
	 * @return zwraca obiekt Pocisku
	 */
	public Bullet shoot() {
		if (PaxPreferences.getSoundEnabled())
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
			temp = damage - shield;
			shield -= damage;
			System.out.println(shield);
			if (shield > 0)
				GameScreen.getShieldBar().setSize(200 * shield / shieldPwr, GameScreen.getShieldBar().getHeight());
			else {
				shield = 0;
				GameScreen.getShieldBorder().setSize(0, GameScreen.getShieldBar().getHeight());
				GameScreen.getShieldBar().setSize(0, GameScreen.getShieldBar().getHeight());
			}
		} else {
			hp -= damage;
			GameScreen.getHpBar().setSize(200 * hp / (hullPwr * 3), GameScreen.getHpBar().getHeight());
		}
		System.out.println(hp);
		if (hp <= 0)
			kill();
	}

	@Override
	public void kill() {
		live = false;
		Assets.playSound(Assets.explosionSfx);
		Assets.explosionEffect.setPosition(getX() + (getWidth() / 2), getY() + (getHeight() / 2));
		Assets.explosionEffect.start();
		GameScreen.getShieldBorder().setSize(0, GameScreen.getShieldBar().getHeight());
		GameScreen.getShieldBar().setSize(0, GameScreen.getShieldBar().getHeight());
		GameScreen.getHpBar().setSize(0, GameScreen.getShieldBar().getHeight());
		GameScreen.getHpBorder().setSize(0, GameScreen.getShieldBar().getHeight());
	}

	public void setStats() {
		//PaxPreferences.setPowerLvl(powerLvl);
		//PaxPreferences.setPowerGenerator(powerGenerator);
		PaxPreferences.setEngineLvl(engineLvl);
		PaxPreferences.setEnginePwr(enginePwr);
		PaxPreferences.setHullLvl(hullLvl);
		PaxPreferences.setHullPwr(hullPwr);
		PaxPreferences.setShieldLvl(shieldLvl);
		PaxPreferences.setShieldPwr(shieldPwr);
		PaxPreferences.setWeaponLvl(weaponLvl);
		PaxPreferences.setWeaponPwr(weaponPwr);
	}

	public void setHp(float hp) {
		this.hp = hp;
	}
}
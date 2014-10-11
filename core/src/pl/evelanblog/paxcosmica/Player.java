package pl.evelanblog.paxcosmica;

import pl.evelanblog.scenes.GameScreen;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends DynamicObject {
	private float bulletSpeed = 1000f;
	private float shootFrequency = 0.2f;
	private Sprite shieldSprite;
	public float shieldReloadLvl = 0;

	public static float powerLvl = 4;
	public static float shieldLvl = 1;
	public static float hullLvl = 1;
	public static float weaponLvl = 1;
	public static float engineLvl = 1;

	public static float powerGenerator;
	public static float shieldPwr = 1;
	public static float hullPwr = 1;
	public static float weaponPwr = 1;
	public static float enginePwr = 1;

	public Player() {
		// pos x, pos y, speed , hp, shield, impactDamage, texture
		super(100, 300, 180f, 3f, 0f, 100f, "spaceship.png");
		shieldSprite = new Sprite(Assets.bubbleShield);
		shieldSprite.setOriginCenter();
	}

	public Player(float x, float y)
	{
		super(x, y, 0, 0, 0, 0, "spaceship.png");
		setSpaceshipPosition(x, y);
		setScale(0.7f);
	}

	public void setSpaceshipPosition(float x, float y)
	{
		setPosition(x, y);
		Assets.playerEngineEffect.setPosition(getX() + 10, getY() + (getHeight() / 2));
	}

	public void update(float deltaTime) {
		powerGenerator = powerLvl - shieldPwr - hullPwr - weaponPwr - enginePwr;

		if (enginePwr < 1)
			speed = 0;
		else
			speed = 180f + (enginePwr * 10);

		if (shield < shieldPwr)
		{
			shieldReloadLvl += deltaTime;
			if (shieldReloadLvl > 10f){
				shieldReloadLvl = 0;
				shield++;
			}
		}

		bulletSpeed = 600f + (weaponPwr * 80);
		

		if (getX() >= 0)
		{
			setX(getX() + (GameScreen.getVelX() * deltaTime * speed));
			setY(getY() + (GameScreen.getVelY() * deltaTime * speed));
			Assets.playerEngineEffect.setPosition(getX() + 10, getY() + (getHeight() / 2));
		} else
			setX(0);
		shieldSprite.setPosition(getX() - 30, getY() - ((shieldSprite.getHeight() - getHeight()) / 2));
	}

	public void draw(SpriteBatch batch, float delta)
	{
		if (enginePwr > 0)
			Assets.playerEngineEffect.draw(batch, delta);
		draw(batch);
		if (shield > 0)
			shieldSprite.draw(batch);
	}

	public Bullet shoot() {
		Assets.playSound(Assets.shootSfx);
		return new Bullet(getX() + getWidth() - 10, getY() + (getHeight() / 2) - 4, bulletSpeed, true, 1f);
	}

	public float getShootFrequency() {
		return shootFrequency;
	}
	
	public boolean ableToShoot()
	{
		return (weaponPwr > 0 && live == true);
	}
	
	@Override
	public void kill()
	{
		live = false;
		Assets.playSound(Assets.explosionSfx);
		Assets.explosionEffect.setPosition(getX() + (getWidth() / 2), getY() + (getHeight() / 2));
		Assets.explosionEffect.start();
	}
}
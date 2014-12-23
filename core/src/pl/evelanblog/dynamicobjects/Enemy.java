package pl.evelanblog.dynamicobjects;

import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Stats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public abstract class Enemy extends DynamicObject {

	protected ParticleEffect engine;
	protected float bulletSpeed;
	protected float shootTime; // częstotliwość strzelania
	protected float lastShoot = 0f;
	protected float startY;
	protected float radians = 0;
	protected float radius = 0;
	public static float spawnTime; // co jaki czas ma się spawnować

	public Enemy(float y, float speed, float bulletSpeed, float hp, float shield, float impactDamage, String texture) {
		// DynamicObject(float x, float y, float speed, float hp, float shield, float impactDamage, String file)
		super(Gdx.graphics.getWidth(), y, speed, hp, shield, impactDamage, texture);
		this.bulletSpeed = bulletSpeed;
	}

	public void update(float deltaTime) {
		if (deltaTime > 0.1f)
			deltaTime = 0.1f;

		radians += (deltaTime);

		setX(getX() - speed * deltaTime);
		setY((MathUtils.sin(radians) * radius) + 50 + startY);

		engine.setPosition(getX() + getWidth() - 20, getY() + (getHeight() / 2));

		if (getX() + getWidth() < 0)
			live = false;
	}

	public void draw(SpriteBatch batch, float delta)
	{
		engine.draw(batch, delta);
		draw(batch);
	}

	public Bullet shoot() {
		Assets.playSound(Assets.shootSfx);
		// pos x, pos y, float speed, boolean direction, float damage
		return new Bullet(getX(), getY() + (getHeight() / 2) - 4, bulletSpeed, false, 1f);
	}

	public float getLastShoot() {
		return lastShoot;
	}

	public void setLastShoot(float value)
	{
		lastShoot = value;
	}

	public float getShootTime() {
		return shootTime;
	}

	@Override
	public void kill()
	{
		Stats.kills++;
		live = false;
		Assets.playSound(Assets.explosionSfx);
		Assets.explosionEffect.setPosition(getX() + (getWidth() / 2), getY() + (getHeight() / 2));
		Assets.explosionEffect.start();
		Stats.score += 10;
		Stats.scrap += 4;
	}

	public static float getSpawnTime() {
		return spawnTime;
	}
}

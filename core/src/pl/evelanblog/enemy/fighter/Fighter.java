package pl.evelanblog.enemy.fighter;

import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Bullet;
import pl.evelanblog.paxcosmica.DynamicObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Fighter extends DynamicObject {

	private static float bulletSpeed = 600f;
	private static float spawnTime = 6f;
	private float shootTime = 1f;
	private float lastShoot = 0f;
	private ParticleEffect engine;
	private float radians = 0;
	private float startY;
	private float radius = 0;

	public Fighter() {
		// pos x, pos y, speed , hp, shield, impactDamage, texture
		super(Gdx.graphics.getWidth(), 0, 80f + (MathUtils.random(6) *10), 3f, 0f, 150f, "enemy.png");
		shootTime += ((MathUtils.random(20)) / 10);
		startY = MathUtils.random(0, 720);
		radius = MathUtils.random(30, 100);
		engine = new ParticleEffect();
		engine.load(Gdx.files.internal("data/enemyEngine.p"), Gdx.files.internal(""));
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

	public static float getSpawnTime() {
		return spawnTime;
	}
}

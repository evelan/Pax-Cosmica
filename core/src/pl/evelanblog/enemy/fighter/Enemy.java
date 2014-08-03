package pl.evelanblog.enemy.fighter;

import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Bullet;
import pl.evelanblog.paxcosmica.DynamicObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.MathUtils;

public class Enemy extends DynamicObject {

	private static float bulletSpeed = 400f;
	private static float damage;
	private static float spawnTime = 5f;
	private static float scrap = 10f;
	private float shootTime = 4f;
	private float lastShoot = 0f;
	private ParticleEffect engine;

	public Enemy() {
		super(Gdx.graphics.getWidth(), MathUtils.random(0, 768 - 128), 60f, 3f, 150f, Assets.enemy.getWidth(), Assets.enemy.getHeight());
		engine = new ParticleEffect();
		getEngine().load(Gdx.files.internal("data/enemyEngine.p"), Gdx.files.internal(""));
	}

	public void update(float deltaTime) {
		if (deltaTime > 0.1f)
			deltaTime = 0.1f;
		setX(getX() - speed * deltaTime);
		getEngine().setPosition(getX() + getWidth() - 20, getY() + (getHeight() / 2));
		if (getX() + getWidth() < 0)
			live = false;
	}

	public Bullet shoot() {
		Assets.playSound(Assets.shoot);
		return new Bullet(getX() + getWidth() - 30, getY() + (getHeight() / 2) - 8, bulletSpeed, false, 1f);
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

	public ParticleEffect getEngine() {
		return engine;
	}
}

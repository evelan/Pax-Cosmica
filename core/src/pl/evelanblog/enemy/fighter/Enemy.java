package pl.evelanblog.enemy.fighter;

import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Bullet;
import pl.evelanblog.paxcosmica.DynamicObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Enemy extends DynamicObject {

	private static float bulletSpeed = 400f;
	private static float spawnTime = 5f;
	private static float scrap = 10f;
	private float shootTime = 4f;
	private float lastShoot = 0f;

	public Enemy() {
		super(Gdx.graphics.getWidth(), MathUtils.random(0, 768 - 128), 60f, 3f, 150f, "enemy.png");
	}

	public void update(float deltaTime) {
		if (deltaTime > 0.1f)
			deltaTime = 0.1f;
		setX(getX() - speed * deltaTime);
		Assets.enemyEngineEffect.setPosition(getX() + getWidth() - 20, getY() + (getHeight() / 2));
		if (getX() + getWidth() < 0)
			live = false;
	}

	public void draw(SpriteBatch batch, float delta)
	{
		Assets.enemyEngineEffect.draw(batch, delta);
		draw(batch);
	}

	public Bullet shoot() {
		Assets.playSound(Assets.shootSfx);
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
}

package pl.evelanblog.dynamicobjects;

import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Stats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.MathUtils;

public class Enemy extends DynamicObject {

	private ParticleEffect engine;
	private float bulletSpeed = 600f;
	private static float spawnTime = 4f;
	private float shootTime = 1f;
	private float lastShoot = 0f;
	private float startY;
	private float radians = 0;
	private float radius = 0;

	public Enemy() {
		// pos x, pos y, speed , hp, shield, impactDamage, texture
		super(1920, 0, 80f + (MathUtils.random(6) * 10), 3f, 0f, 150f, "enemy.png");
		shootTime += ((MathUtils.random(20)) / 10);
		radius = MathUtils.random(30, 100); // amplituda, maks wychylenie

		startY = MathUtils.random(0, Gdx.graphics.getHeight() - radius); // pozycja w Y
		// TODO maks apmlituda + pozycja Y nie moze by� wi�ksz/ mniejsza ni� pozycja ekrnu.

		engine = new ParticleEffect();
		engine.load(Gdx.files.internal("data/enemyEngine.p"), Gdx.files.internal(""));
	}

	public void update(float deltaTime) {
		if (deltaTime > 0.1f)
			deltaTime = 0.1f;

		radians += (deltaTime);

	this.setX(getX() - speed * deltaTime);
		this.setY((MathUtils.sin(radians) * radius) + 50 + startY);
		Assets.enemyEngineEffect.setPosition(this.getX() + this.getWidth() - 20, this.getY() + (this.getHeight() / 2));

		if (this.getX() + this.getWidth() < 0)
			live = false;
	}
	
	@Override
	public void draw(Batch batch, float alpha)
	{
		Assets.enemyEngineEffect.draw(batch, Gdx.graphics.getDeltaTime());
		batch.draw(getSprite(), getX(), getY());
	}

//	@Override
//	public void draw(SpriteBatch batch, float delta)
//	{
//		engine.draw(batch, delta);
//		draw(batch);
//	}

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

	
	@Override
	public void kill()
	{
		live = false;
		Assets.playSound(Assets.explosionSfx);
		Assets.explosionEffect.setPosition(this.getX() + (this.getWidth() / 2), this.getY() + (this.getHeight() / 2));
		Assets.explosionEffect.start();
		Stats.score += 10;
		Stats.scrap += 4;
	}
}

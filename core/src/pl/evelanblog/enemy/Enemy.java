package pl.evelanblog.enemy;

import pl.evelanblog.dynamicobjects.Bullet;
import pl.evelanblog.dynamicobjects.DynamicObject;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Stats;
import pl.evelanblog.world.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public abstract class Enemy extends DynamicObject {

	private static float SPAWN_TIME;
	private float time = 0;
	protected ParticleEffect engine; // efekt cząsteczkowy silnika
	protected float bulletSpeed; // prędkość pocisku
	protected float shootTime; // częstotliwość strzelania
	protected float startY; // "poziom zero" w sinusoidzie, na jakiej wysokości y ma latać
	protected float radians = 0; // radiany żeby latać po sinusoidzie :)
	protected float radius = 0; // jak mocno będzie się wychylać statek w pionie, to jest ustawiane w klasach które
								// dziedziczą po Enemy

	protected Enemy(float speed, float hp, float shield, float bulletSpeed, float impactDamage, float SPAWN_TIME, String texture) {
		super(Gdx.graphics.getWidth(), 0, speed, hp, shield, impactDamage, texture);

		Enemy.SPAWN_TIME = SPAWN_TIME;
		engine = new ParticleEffect();
		this.bulletSpeed = bulletSpeed;
	}

	public void update(float deltaTime) {
		radians += deltaTime;
		setX(getX() - speed * deltaTime);
		setY((MathUtils.sin(radians) * radius) + startY);

		engine.setPosition(getX() + getWidth() - 20, getY() + (getHeight() / 2));

		if (getX() + getWidth() < 0)
			live = false;

		if ((time += deltaTime) > shootTime)
		{
			World.getIterator().add(shoot());
			time = 0;
		}
	}

	public void draw(SpriteBatch batch, float delta)
	{
		engine.draw(batch, delta);
		super.draw(batch, delta);
	}

	public abstract Bullet shoot(); // każdy rodzaj wroga może strzelać

	public void kill()
	{
		Stats.score += 10;
		Stats.scrap += 4;
		Stats.kills++;
		live = false;
		Assets.playSound(Assets.explosionSfx);
		Assets.explosionEffect.setPosition(getX() + (getWidth() / 2), getY() + (getHeight() / 2));
		Assets.explosionEffect.start();
	}

	public static float getSpawnTime() {
		return SPAWN_TIME;
	}
}

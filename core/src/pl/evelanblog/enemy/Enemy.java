package pl.evelanblog.enemy;

import pl.evelanblog.dynamicobjects.DynamicObject;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Stats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

/**
 * Po niej dziedziczą wszyscy przeciwnicy, można uznać ją póki co za skończoną i piękną 
 * @author Evelan
 * @version 1.0
 */

public abstract class Enemy extends DynamicObject {

	protected float time = 0;
	protected ParticleEffect engine; // efekt cząsteczkowy silnika
	protected float bulletSpeed; // prędkość pocisku
	protected float shootTime; // częstotliwość strzelania
	protected float startY; // "poziom zero" w sinusoidzie, na jakiej wysokości y ma latać
	protected float radians = 0; // radiany żeby latać po sinusoidzie :)
	protected float radius = 0; // jak mocno będzie się wychylać statek w pionie, to jest ustawiane w klasach które
								// dziedziczą po Enemy

	protected Enemy(float speed, float hp, float shield, float bulletSpeed, float shootTime, float impactDamage, String texture) {
		super(Gdx.graphics.getWidth(), 0, speed, hp, shield, impactDamage, texture);

		engine = new ParticleEffect();
		this.bulletSpeed = bulletSpeed;
		this.shootTime = shootTime;
	}

	public void update(float deltaTime) {
		radians += deltaTime;
		setX(getX() - speed * deltaTime);
		setY((MathUtils.sin(radians) * radius) + startY);

		engine.setPosition(getX() + getWidth() - 20, getY() + (getHeight() / 2));

		if (getX() + getWidth() < 0)
			live = false;

		if ((time += deltaTime) > shootTime)
			shoot();
	}

	public void draw(SpriteBatch batch, float delta)
	{
		engine.draw(batch, delta); // najpierw rysujemey silnik
		super.draw(batch, delta); // a potem zasłąniamy jego część texturą statku
	}

	/**
	 * każdy rodzaj wroga może strzelać inaczej, np dawać dwa pociski w jednym czasie albo inny dźwięk pocisku czy coś
	 */
	public abstract void shoot();

	@Override
	public void kill()
	{
		super.kill();
		Stats.score += 10;
		Stats.scrap += 4;
		Stats.kills++;
		Assets.explosionEffect.setPosition(getX() + (getWidth() / 2), getY() + (getHeight() / 2));
		Assets.playSound(Assets.explosionSfx);
		Assets.explosionEffect.start();
	}
}

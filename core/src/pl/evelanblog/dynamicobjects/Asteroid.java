package pl.evelanblog.dynamicobjects;

import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Stats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Asteroid extends DynamicObject {

	private static float spawnTime = 6f; 
	private ParticleEffect particle;
	private boolean rotation;
	private float count = 360.0f;
	private int textureNum;
	private float radius;
	private float startY;
	private float radians = 0;

	public Asteroid() {
		// pos x, pos y, speed , hp, shield, impactDamage, texture
		super(Gdx.graphics.getWidth(), 0f, 40f + (MathUtils.random(10) * 10), 2f, 0f, 200f, "asteroid_1.png");

		startY = (MathUtils.random(0, Gdx.graphics.getHeight() - 64));

		particle = new ParticleEffect();
		particle.load(Gdx.files.internal("data/asteroid.p"), Gdx.files.internal(""));
		textureNum = MathUtils.random(2);
		rotation = MathUtils.randomBoolean();
		radius = MathUtils.random(5, 20);

		getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

		if (textureNum == 0)
			setTexture("asteroid_1.png");
		else if (textureNum == 1)
			setTexture("asteroid_2.png");
		else if (textureNum == 2)
			setTexture("asteroid_3.png");
	}

	public void update(float deltaTime) {
		radians += (deltaTime);
		
		setX(getX() - speed * deltaTime);
		setY((MathUtils.sin(radians) * radius) + 50 + startY);
		
		particle.setPosition(getX() + getWidth() - 20, getY() + (getHeight() / 2));

		if (count < 0.0f)
			count = 360.0f;
		else
		{
			if (rotation)
				count -= (speed / 70);
			else
				count += (speed / 70);
		}
		setRotation(count);
	}

	public void draw(SpriteBatch batch, float delta)
	{
		particle.draw(batch, delta);
		draw(batch);
	}

	public static float getSpawnTime()
	{
		return spawnTime;
	}

	@Override
	public void kill()
	{
		live = false;
		Assets.playSound(Assets.explosionSfx);
		Assets.explosionEffect.setPosition(getX() + (getWidth() / 2), getY() + (getHeight() / 2));
		Assets.explosionEffect.start();
		Stats.score += 10;
		Stats.scrap += 4;
	}
}
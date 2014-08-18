package pl.evelanblog.asteroid;

import java.util.Random;

import pl.evelanblog.paxcosmica.DynamicObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Asteroid extends DynamicObject {

	private static float spawnTime = 6f;
	private ParticleEffect particle;
	private static Random generator = new Random();
	private float count = 360.0f;

	public Asteroid() {
		super(Gdx.graphics.getWidth(), (MathUtils.random(0, Gdx.graphics.getHeight() - 64)), 10f, 2f, 0f, 200f, "asteroid_1.png");

		particle = new ParticleEffect();
		particle.load(Gdx.files.internal("data/asteroid.p"), Gdx.files.internal(""));
		int texture = generator.nextInt(2);

		getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

		if (texture == 0)
			setTexture("asteroid_1.png");
		else if (texture == 1)
			setTexture("asteroid_2.png");
		else if (texture == 2)
			setTexture("asteroid_3.png");
	}

	public void update(float deltaTime) {
		setX(getX() - speed * deltaTime);
		particle.setPosition(getX() + getWidth() - 20, getY() + (getHeight() / 2));

		if (count < 0.0f)
			count = 360.0f;
		else
			count--;

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
}
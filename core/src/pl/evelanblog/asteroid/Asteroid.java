package pl.evelanblog.asteroid;

import java.util.Random;

import pl.evelanblog.paxcosmica.DynamicObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.MathUtils;

public class Asteroid extends DynamicObject {

	private static float spawnTime = 4f;
	private int numberBitmap;
	ParticleEffect particle;
	Random generator = new Random();

	public Asteroid() {
		super(Gdx.graphics.getWidth(),(MathUtils.random(0, Gdx.graphics.getHeight() - 64)), 10f, 100f, 500f, 64, 64);
		
		particle = new ParticleEffect();
		particle.load(Gdx.files.internal("data/asteroid.p"), Gdx.files.internal(""));
		numberBitmap = generator.nextInt(2);
	}

	public void update(float deltaTime) {
		if (deltaTime > 0.1f)
			deltaTime = 0.1f;
		setX(getX() - speed * deltaTime);
		particle.setPosition(getX() + getWidth() - 20, getY() + (getHeight() / 2));
	}
	
	public static float getSpawnTime()
	{
		return spawnTime;
	}
	
	public int getNumberBitmap()
	{
		return numberBitmap;
	}

}
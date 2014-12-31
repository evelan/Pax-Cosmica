package pl.evelanblog.dynamicobjects;

import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.Stats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
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

		getSprite().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);

		if (textureNum == 0)
			getSprite().setTexture(new Texture(Gdx.files.internal("asteroid_1.png")));
		else if (textureNum == 1)
			getSprite().setTexture(new Texture(Gdx.files.internal("asteroid_2.png")));
		else if (textureNum == 2)
			getSprite().setTexture(new Texture(Gdx.files.internal("asteroid_3.png")));
	}

	public void update() {
		radians += (Gdx.graphics.getDeltaTime());
		
		this.setX(this.getX() - speed * Gdx.graphics.getDeltaTime());
		this.setY((MathUtils.sin(radians) * radius) + 50 + startY);
		
		particle.setPosition(this.getX() + this.getWidth() - 20, this.getY() + (this.getHeight() / 2));
		if (count < 0.0f)
			count = 360.0f;
		else
		{
			if (rotation)
				count -= (speed / 70);
			else
				count += (speed / 70);
		}		
	}
	
	
	@Override
	public void draw(Batch batch, float alpha)
	{
		particle.draw(batch, Gdx.graphics.getDeltaTime());
		batch.draw(getSprite(),this.getX(),this.getY(), this.getWidth()/2, this.getHeight()/2, this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), count);
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
		Assets.explosionEffect.setPosition(this.getX() + (this.getWidth() / 2), this.getY() + (this.getHeight() / 2));
		Assets.explosionEffect.start();
		Stats.score += 10;
		Stats.scrap += 4;
	}
}
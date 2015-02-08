package pl.evelanblog.dynamicobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.MathUtils;
import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.PaxPreferences;
import pl.evelanblog.paxcosmica.Stats;

public class Asteroid extends DynamicObject {

	public static float SPAWN_TIME = 4f;
	private ParticleEffect particle;
	private boolean rotation;
	private float count = 360.0f;
	private float radius;
	private float startY;
	private float radians = 0;

	public Asteroid() {
		// pos x, pos y, speed , hp, shield, impactDamage, texture
		super(Assets.worldWidth, 0f, 40f + (MathUtils.random(10) * 10), 2f, 0f, 200f, "asteroid/asteroid_" + (MathUtils.random(2) + 1) + ".png");

		startY = (MathUtils.random(0, Assets.worldHeight - 64));

		particle = new ParticleEffect();
		particle.load(Gdx.files.internal("data/asteroid.p"), Gdx.files.internal(""));
		rotation = MathUtils.randomBoolean(); // losuje boola aby raz się kręciły w jedną a raz w druga stronę
		radius = MathUtils.random(5, 20);

		getSprite().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}

	@Override
	public void draw(Batch batch, float alpha)
	{
		particle.draw(batch, Gdx.graphics.getDeltaTime());
		batch.draw(getSprite(), this.getX(), this.getY(), this.getWidth() / 2, this.getHeight() / 2, this.getWidth(), this.getHeight(), this.getScaleX(),
				this.getScaleY(), count);
	}

	@Override
	public void kill()
	{
		dispose();
		Stats.score += 10;
		Stats.scrap += 4;
		Assets.explosionEffect.setPosition(getX() + (getWidth() / 2), getY() + (getHeight() / 2));
        if(PaxPreferences.getSoundEnabled())
		    Assets.playSound(Assets.explosionSfx);
		Assets.explosionEffect.setPosition(this.getX() + (this.getWidth() / 2), this.getY() + (this.getHeight() / 2));
		Assets.explosionEffect.start();
	}

	public void update(float deltaTime) {
		super.update(deltaTime);
		radians += (deltaTime);

		this.setX(this.getX() - speed * deltaTime);
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
}
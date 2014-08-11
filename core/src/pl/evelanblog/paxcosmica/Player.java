package pl.evelanblog.paxcosmica;

import pl.evelanblog.paxcosmica.control.Controller;

import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends DynamicObject {
	private static float bulletSpeed = 1000f;
	private static float shootSpeed = 0.2f;
	private static Sprite shieldSprite;

	public Player() {
		// pos x, pos y, speed , hp, shield, impactDamage, texture
		super(100, 300, 180f, 10f, 2f, 100f, "spaceship.png");
		shieldSprite = new Sprite(Assets.bubbleShield);
		shieldSprite.setOriginCenter();
	}

	public void update(float deltaTime) {
		if (getX() >= 0)
		{
			setX(getX() + (Controller.getVelX() * deltaTime * speed));
			setY(getY() + (Controller.getVelY() * deltaTime * speed));
			Assets.playerEngineEffect.setPosition(getX() + 10, getY() + (getHeight() / 2));
		} else
		{
			setX(0);
		}
		shieldSprite.setPosition(getX() - 30, getY() - ((shieldSprite.getHeight() - getHeight()) / 2));
	}

	public void draw(SpriteBatch batch, float delta)
	{
		Assets.playerEngineEffect.draw(batch, delta);
		draw(batch);
		if (shield > 0)
			shieldSprite.draw(batch);
	}

	public Bullet shoot() {
		Assets.playSound(Assets.shootSfx);
		return new Bullet(getX() + getWidth() - 10, getY() + (getHeight() / 2) - 4, bulletSpeed, true, 1f);
	}

	public static float getShootSpeed() {
		return shootSpeed;
	}
}
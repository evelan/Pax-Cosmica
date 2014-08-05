package pl.evelanblog.paxcosmica;

import pl.evelanblog.paxcosmica.control.Joystick;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends DynamicObject {
	private static float bulletSpeed = 1000f;
	private static float shootSpeed = 0.2f;
	private static float shield = 0f;

	public Player() {
		super(100, 300, 150f, 10f, 100f, "spaceship.png");
	}

	public void update(float deltaTime) {
		if(getX() >= 0)
		{
			setX(getX() + (Joystick.getVelX() * deltaTime * speed));
			setY(getY() + (Joystick.getVelY() * deltaTime * speed));
			Assets.playerEngineEffect.setPosition(getX() + 20, getY() + (getHeight() / 2));
		} else
		{
			setX(0);
		}
		
	}
	
	public void draw(SpriteBatch batch, float delta)
	{
		Assets.playerEngineEffect.draw(batch, delta);
		draw(batch);
	}

	public Bullet shoot() {
		Assets.playSound(Assets.shootSfx);
		return new Bullet(getX() + getWidth() - 30, getY() + (getHeight() / 2) - 8, bulletSpeed, true, 100f);
	}
	
	public void heal()
	{
		hp = 100f;
	}
	
	public float getShield()
	{
		return shield;
	}
	
	public void shieldBoost()
	{
		
	}
	
	public void speedBoost()
	{
		speed = 4f;
	}
	
	public void shootBoost()
	{
		setShootSpeed(0.1f);
	}

	public static float getShootSpeed() {
		return shootSpeed;
	}

	public static void setShootSpeed(float shootSpeed) {
		Player.shootSpeed = shootSpeed;
	}
}
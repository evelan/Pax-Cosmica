package pl.evelanblog.paxcosmica;

import pl.evelanblog.paxcosmica.control.Joystick;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class Player extends DynamicObject {
	private static float bulletSpeed = 1000f;
	private static float shootSpeed = 0.2f;
	private static float shield = 0f;
	private static float oxygen = 100f;
	//hull pow³oka 
	//³adowanie broni
	//tlen na statku
	//

	private ParticleEffect engine;

	public Player() {
		super(100, Gdx.graphics.getHeight() / 2, 150f, 10f, 100f, Assets.spaceship.getWidth(), Assets.spaceship.getHeight());
		engine = new ParticleEffect();
		getEngine().load(Gdx.files.internal("data/engine.p"), Gdx.files.internal(""));
	}

	public void update(float deltaTime) {
		if(getX() >= 0)
		{
			setX(getX() + (Joystick.getVelX() * deltaTime * speed));
			setY(getY() + (Joystick.getVelY() * deltaTime * speed));
			getEngine().setPosition(getX() + 20, getY() + (getHeight() / 2));
		} else
		{
			setX(0);
		}
		
	}

	public Bullet shoot() {
		Assets.playSound(Assets.shoot);
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

	public ParticleEffect getEngine() {
		return engine;
	}


	public static float getShootSpeed() {
		return shootSpeed;
	}

	public static void setShootSpeed(float shootSpeed) {
		Player.shootSpeed = shootSpeed;
	}
}
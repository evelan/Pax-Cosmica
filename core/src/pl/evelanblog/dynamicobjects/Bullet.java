package pl.evelanblog.dynamicobjects;

import pl.evelanblog.paxcosmica.Assets;
import pl.evelanblog.paxcosmica.PaxPreferences;

public class Bullet extends DynamicObject {
	private boolean direction; // true - strzały gracza, false - strzały wroga

	public Bullet(float x, float y, float speed, boolean direction, float damage) {
		super(x, y, speed, 1, 0, damage, "enemy/bullet.png");
		this.direction = direction;
	}

	@Override
	public void update(float deltaTime) {
		if (getX() < 0 || getX() > Assets.worldWidth) // jak wyleci za ekran to umieramy szczała
			dispose();

		if (direction) // w którą stronę latajo szczały
			setX(getX() + speed * deltaTime);
		else
			setX(getX() - speed * deltaTime);

	}

	/**
	 * zwraca kierunek w którą stronę leci pocisk, true - strzały gracza, false - strzały wroga
	 */
	public boolean getDirection()
	{
		return direction;
	}

	@Override
	public void kill()
	{
		dispose();
        if(PaxPreferences.getSoundEnabled())
		    Assets.playSound(Assets.hitSfx);
		Assets.hitEffect.setPosition(getX(), getY() + (getHeight() / 2));
		Assets.hitEffect.start();
	}
}
